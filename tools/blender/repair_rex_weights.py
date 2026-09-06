#!/usr/bin/env python3
"""Repair obvious auto-rig weight leaks on Rex before animation rendering.

The Motius template rig is structurally useful, but a single connected sci-fi
mesh can give shoulder/elbow groups influence over cape or torso armor. This
script removes arm influences from vertices that are geometrically far from the
corresponding arm chain and transfers that weight to the nearest torso bone.

It is deliberately conservative and deterministic: no remeshing, no topology
changes, no neural service, and no hand-authored vertex IDs.
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
    p.add_argument("--arm-distance", type=float, default=0.115,
                   help="Max rest-pose distance to arm segment as fraction of actor height")
    p.add_argument("--central-x", type=float, default=0.23,
                   help="Central torso half-width as fraction of actor width")
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
    distances = []
    for name in names:
        seg = bone_segment_world(arm, name)
        if seg:
            distances.append(segment_distance(p, seg[0], seg[1]))
    return min(distances) if distances else math.inf


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
        raise RuntimeError("Rig has no torso bone usable for reassignment")
    return best_name


def group_weight(vertex, group_index: int) -> float:
    for g in vertex.groups:
        if g.group == group_index:
            return g.weight
    return 0.0


def ensure_group(obj, name: str):
    return obj.vertex_groups.get(name) or obj.vertex_groups.new(name=name)


def repair_mesh(obj, arm, height: float, width: float, arm_distance_frac: float, central_x_frac: float):
    arm_limit = height * arm_distance_frac
    center_x = (world_bounds([obj])[0].x + world_bounds([obj])[1].x) * 0.5
    central_limit = width * central_x_frac

    arm_groups = {}
    for side, names in ARM_CHAINS.items():
        arm_groups[side] = [(name, obj.vertex_groups.get(name)) for name in names]
    torso_groups = {name: ensure_group(obj, name) for name in TORSO_BONES if arm.data.bones.get(name)}

    changed_vertices = set()
    removed_weight = 0.0
    suspicious_before = 0
    suspicious_after = 0
    cross_side_removed = 0.0

    for v in obj.data.vertices:
        p = obj.matrix_world @ v.co
        x_rel = p.x - center_x
        for side, names in ARM_CHAINS.items():
            entries = [(n, g) for n, g in arm_groups[side] if g is not None]
            if not entries:
                continue
            weights = [(n, g, group_weight(v, g.index)) for n, g in entries]
            total_arm = sum(w for _, _, w in weights)
            if total_arm <= 1e-7:
                continue

            d_arm = chain_distance(p, arm, names)
            wrong_side = (side == "L" and x_rel < -central_limit) or (side == "R" and x_rel > central_limit)
            central = abs(x_rel) <= central_limit
            leak = d_arm > arm_limit and central

            if leak or wrong_side:
                suspicious_before += 1
                target = nearest_torso_bone(p, arm)
                target_group = torso_groups[target]
                transfer = 0.0
                for _, group, weight in weights:
                    if weight > 0.0:
                        group.remove([v.index])
                        transfer += weight
                if transfer > 0.0:
                    existing = group_weight(v, target_group.index)
                    target_group.add([v.index], min(1.0, existing + transfer), "REPLACE")
                    removed_weight += transfer
                    if wrong_side:
                        cross_side_removed += transfer
                    changed_vertices.add(v.index)

    # Normalize weights on edited vertices only.
    for vidx in changed_vertices:
        v = obj.data.vertices[vidx]
        weighted = [(g.group, g.weight) for g in v.groups if g.weight > 0]
        total = sum(w for _, w in weighted)
        if total <= 1e-8:
            ensure_group(obj, "Pelvis").add([vidx], 1.0, "REPLACE")
            continue
        if abs(total - 1.0) > 1e-5:
            for gidx, w in weighted:
                obj.vertex_groups[gidx].add([vidx], w / total, "REPLACE")

    # Recount residual central arm leaks after repair.
    for v in obj.data.vertices:
        p = obj.matrix_world @ v.co
        x_rel = p.x - center_x
        if abs(x_rel) > central_limit:
            continue
        for side, names in ARM_CHAINS.items():
            total = 0.0
            for _, group in arm_groups[side]:
                if group is not None:
                    total += group_weight(v, group.index)
            if total > 0.05 and chain_distance(p, arm, names) > arm_limit:
                suspicious_after += 1
                break

    return {
        "mesh": obj.name,
        "vertices": len(obj.data.vertices),
        "changed_vertices": len(changed_vertices),
        "removed_arm_weight": removed_weight,
        "cross_side_removed_weight": cross_side_removed,
        "suspicious_before": suspicious_before,
        "suspicious_after": suspicious_after,
        "arm_distance_limit": arm_limit,
        "central_x_limit": central_limit,
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
    height = hi.z - lo.z
    width = hi.x - lo.x
    if height <= 0 or width <= 0:
        raise RuntimeError("Invalid actor bounds")

    reports = [repair_mesh(m, arm, height, width, args.arm_distance, args.central_x) for m in meshes]
    residual = sum(r["suspicious_after"] for r in reports)
    payload = {
        "actor": "rex",
        "stage": "weight-repair",
        "height": height,
        "width": width,
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
