#!/usr/bin/env python3
"""Repair Rex auto-rig weight leaks before animation rendering.

Rex is exported as a triangle-soup style mesh, so topology-based cape isolation
is unreliable. The repair therefore combines two deterministic spatial gates:
1. remove arm weights from central vertices far from the arm chain;
2. lock the back-central cape slab to torso bones so shoulder motion cannot
   stretch it into the arms.
"""
from __future__ import annotations

import argparse
import json
import math
import sys
from pathlib import Path

import bpy
from mathutils import Vector

ARM_CHAINS = {
    "L": ["L_Shoulder", "L_Elbow", "L_Wrist"],
    "R": ["R_Shoulder", "R_Elbow", "R_Wrist"],
}
TORSO_BONES = ["Pelvis", "Spine1", "Spine2", "Spine3", "Neck"]


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--report", type=Path, required=True)
    p.add_argument("--arm-distance", type=float, default=0.115)
    p.add_argument("--central-x", type=float, default=0.23)
    p.add_argument("--cape-back", type=float, default=0.08,
                   help="Cape starts this fraction of actor depth behind center")
    p.add_argument("--cape-half-width", type=float, default=0.36,
                   help="Cape central half-width as fraction of actor width")
    return p.parse_args(argv)


def reset_scene() -> None:
    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)


def import_asset(path: Path) -> None:
    suffix = path.suffix.lower()
    if suffix in {".glb", ".gltf"}:
        bpy.ops.import_scene.gltf(filepath=str(path.resolve()))
    elif suffix == ".fbx":
        bpy.ops.import_scene.fbx(filepath=str(path.resolve()), use_anim=False)
    else:
        raise RuntimeError(f"Unsupported input: {suffix}")


def get_armature_and_meshes():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if len(arms) != 1:
        raise RuntimeError(f"Expected exactly one armature, found {len(arms)}")
    arm = arms[0]
    meshes = [
        o for o in bpy.context.scene.objects
        if o.type == "MESH" and any(m.type == "ARMATURE" and m.object == arm for m in o.modifiers)
    ]
    if not meshes:
        raise RuntimeError("No deforming mesh found")
    return arm, meshes


def world_bounds(meshes):
    pts = [obj.matrix_world @ Vector(c) for obj in meshes for c in obj.bound_box]
    lo = Vector((min(p.x for p in pts), min(p.y for p in pts), min(p.z for p in pts)))
    hi = Vector((max(p.x for p in pts), max(p.y for p in pts), max(p.z for p in pts)))
    return lo, hi


def segment_distance(p: Vector, a: Vector, b: Vector) -> float:
    ab = b - a
    denom = ab.length_squared
    if denom <= 1e-12:
        return (p - a).length
    t = max(0.0, min(1.0, (p - a).dot(ab) / denom))
    return (p - (a + ab * t)).length


def bone_segment_world(arm, name: str):
    bone = arm.data.bones.get(name)
    if bone is None:
        return None
    return arm.matrix_world @ bone.head_local, arm.matrix_world @ bone.tail_local


def chain_distance(p: Vector, arm, names: list[str]) -> float:
    vals = []
    for name in names:
        seg = bone_segment_world(arm, name)
        if seg:
            vals.append(segment_distance(p, seg[0], seg[1]))
    return min(vals) if vals else math.inf


def nearest_torso_bone(p: Vector, arm) -> str:
    best_name = None
    best_dist = math.inf
    for name in TORSO_BONES:
        seg = bone_segment_world(arm, name)
        if not seg:
            continue
        d = segment_distance(p, seg[0], seg[1])
        if d < best_dist:
            best_name, best_dist = name, d
    if best_name is None:
        raise RuntimeError("Rig has no usable torso bone")
    return best_name


def group_weight(vertex, group_index: int) -> float:
    for g in vertex.groups:
        if g.group == group_index:
            return g.weight
    return 0.0


def ensure_group(obj, name: str):
    return obj.vertex_groups.get(name) or obj.vertex_groups.new(name=name)


def replace_all_weights(obj, vertex, target_group) -> None:
    for g in list(vertex.groups):
        obj.vertex_groups[g.group].remove([vertex.index])
    target_group.add([vertex.index], 1.0, "REPLACE")


def repair_mesh(obj, arm, global_lo, global_hi, args):
    height = global_hi.z - global_lo.z
    width = global_hi.x - global_lo.x
    depth = global_hi.y - global_lo.y
    center_x = (global_lo.x + global_hi.x) * 0.5
    center_y = (global_lo.y + global_hi.y) * 0.5
    arm_limit = height * args.arm_distance
    central_limit = width * args.central_x
    cape_x_limit = width * args.cape_half_width
    cape_y_cut = center_y + depth * args.cape_back

    arm_groups = {
        side: [(name, obj.vertex_groups.get(name)) for name in names]
        for side, names in ARM_CHAINS.items()
    }
    torso_groups = {
        name: ensure_group(obj, name)
        for name in TORSO_BONES if arm.data.bones.get(name)
    }

    changed_vertices = set()
    removed_weight = 0.0
    suspicious_before = 0
    cross_side_removed = 0.0

    # Pass 1: remove obvious arm influence leaks.
    for v in obj.data.vertices:
        p = obj.matrix_world @ v.co
        x_rel = p.x - center_x
        for side, names in ARM_CHAINS.items():
            entries = [(n, g) for n, g in arm_groups[side] if g is not None]
            weights = [(n, g, group_weight(v, g.index)) for n, g in entries]
            total_arm = sum(w for _, _, w in weights)
            if total_arm <= 1e-7:
                continue
            d_arm = chain_distance(p, arm, names)
            wrong_side = (side == "L" and x_rel < -central_limit) or (side == "R" and x_rel > central_limit)
            leak = d_arm > arm_limit and abs(x_rel) <= central_limit
            if leak or wrong_side:
                suspicious_before += 1
                target = torso_groups[nearest_torso_bone(p, arm)]
                transfer = 0.0
                for _, group, weight in weights:
                    if weight > 0.0:
                        group.remove([v.index])
                        transfer += weight
                if transfer > 0.0:
                    existing = group_weight(v, target.index)
                    target.add([v.index], min(1.0, existing + transfer), "REPLACE")
                    removed_weight += transfer
                    if wrong_side:
                        cross_side_removed += transfer
                    changed_vertices.add(v.index)

    # Pass 2: Rex's cape is a back-central slab. Lock it to the torso so arm
    # bones cannot pull cape triangles outward. Limit the vertical range to
    # below the helmet and above the boots.
    cape_vertices = 0
    for v in obj.data.vertices:
        p = obj.matrix_world @ v.co
        z_norm = (p.z - global_lo.z) / height
        if not (0.30 <= z_norm <= 0.91):
            continue
        if p.y <= cape_y_cut:
            continue
        if abs(p.x - center_x) > cape_x_limit:
            continue
        target_name = nearest_torso_bone(p, arm)
        replace_all_weights(obj, v, torso_groups[target_name])
        changed_vertices.add(v.index)
        cape_vertices += 1

    # Normalize edited non-cape vertices. Cape vertices are already exactly 1.
    for vidx in changed_vertices:
        v = obj.data.vertices[vidx]
        weighted = [(g.group, g.weight) for g in v.groups if g.weight > 0]
        total = sum(w for _, w in weighted)
        if total <= 1e-8:
            torso_groups["Pelvis"].add([vidx], 1.0, "REPLACE")
        elif abs(total - 1.0) > 1e-5:
            for gidx, w in weighted:
                obj.vertex_groups[gidx].add([vidx], w / total, "REPLACE")

    suspicious_after = 0
    for v in obj.data.vertices:
        p = obj.matrix_world @ v.co
        x_rel = p.x - center_x
        if abs(x_rel) > central_limit:
            continue
        for side, names in ARM_CHAINS.items():
            total = sum(
                group_weight(v, group.index)
                for _, group in arm_groups[side] if group is not None
            )
            if total > 0.05 and chain_distance(p, arm, names) > arm_limit:
                suspicious_after += 1
                break

    return {
        "mesh": obj.name,
        "vertices": len(obj.data.vertices),
        "changed_vertices": len(changed_vertices),
        "cape_locked_vertices": cape_vertices,
        "removed_arm_weight": removed_weight,
        "cross_side_removed_weight": cross_side_removed,
        "suspicious_before": suspicious_before,
        "suspicious_after": suspicious_after,
        "arm_distance_limit": arm_limit,
        "cape_y_cut": cape_y_cut,
        "cape_x_limit": cape_x_limit,
    }


def export_glb(path: Path) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.export_scene.gltf(
        filepath=str(path.resolve()),
        export_format="GLB",
        export_apply=False,
        export_animations=False,
    )


def main() -> None:
    args = parse_args()
    reset_scene()
    import_asset(args.input)
    arm, meshes = get_armature_and_meshes()
    lo, hi = world_bounds(meshes)
    if hi.z <= lo.z or hi.x <= lo.x or hi.y <= lo.y:
        raise RuntimeError("Invalid actor bounds")

    reports = [repair_mesh(m, arm, lo, hi, args) for m in meshes]
    residual = sum(r["suspicious_after"] for r in reports)
    payload = {
        "actor": "rex",
        "stage": "weight-repair-v2",
        "height": hi.z - lo.z,
        "width": hi.x - lo.x,
        "depth": hi.y - lo.y,
        "meshes": reports,
        "residual_suspicious_vertices": residual,
        "pass": residual == 0,
    }
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(payload, indent=2) + "\n", encoding="utf-8")
    export_glb(args.output)
    print(json.dumps(payload, indent=2))
    if residual:
        raise RuntimeError(f"Weight repair left {residual} suspicious vertices")


if __name__ == "__main__":
    main()
