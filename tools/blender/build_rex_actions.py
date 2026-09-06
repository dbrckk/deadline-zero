#!/usr/bin/env python3
"""Build deterministic Rex animation actions on a rigged SMPL22 GLB.

These actions are deformation/animation QA motions. They remain intentionally
conservative so a valid skin is stressed without forcing anatomically absurd
poses that would make a good rig look broken.
"""
from __future__ import annotations

import argparse
import math
import sys
from pathlib import Path

import bpy
from mathutils import Vector

REQUIRED_BONES = {
    "Pelvis", "Spine1", "Spine2", "Spine3", "Neck", "Head",
    "L_Shoulder", "R_Shoulder", "L_Elbow", "R_Elbow",
    "L_Hip", "R_Hip", "L_Knee", "R_Knee", "L_Ankle", "R_Ankle",
}


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
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


def armature_and_meshes():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if len(arms) != 1:
        raise RuntimeError(f"Expected one armature, found {len(arms)}")
    arm = arms[0]
    meshes = [
        o for o in bpy.context.scene.objects
        if o.type == "MESH" and any(m.type == "ARMATURE" and m.object == arm for m in o.modifiers)
    ]
    if not meshes:
        raise RuntimeError("No deforming Rex mesh found")
    missing = sorted(REQUIRED_BONES - set(arm.pose.bones.keys()))
    if missing:
        raise RuntimeError(f"Missing required SMPL22 bones: {missing}")
    return arm, meshes


def bounds(meshes):
    points = [obj.matrix_world @ Vector(corner) for obj in meshes for corner in obj.bound_box]
    mins = Vector((min(p.x for p in points), min(p.y for p in points), min(p.z for p in points)))
    maxs = Vector((max(p.x for p in points), max(p.y for p in points), max(p.z for p in points)))
    return mins, maxs


def ground_actor(arm, meshes) -> None:
    mins, maxs = bounds(meshes)
    shift = Vector((-(mins.x + maxs.x) * 0.5, -(mins.y + maxs.y) * 0.5, -mins.z))
    actor_objects = set(meshes) | {arm}
    roots = [o for o in actor_objects if o.parent not in actor_objects]
    for obj in roots:
        world = obj.matrix_world.copy()
        world.translation += shift
        obj.matrix_world = world
    bpy.context.view_layer.update()
    mins2, _ = bounds(meshes)
    if abs(mins2.z) > 1e-4:
        raise RuntimeError(f"Failed to ground Rex; min Z is {mins2.z}")


def clear_actions(arm) -> None:
    if arm.animation_data is None:
        arm.animation_data_create()
    arm.animation_data.action = None
    for action in list(bpy.data.actions):
        bpy.data.actions.remove(action)


def zero_pose(arm) -> None:
    for bone in arm.pose.bones:
        bone.rotation_mode = "XYZ"
        bone.rotation_euler = (0.0, 0.0, 0.0)
        bone.location = (0.0, 0.0, 0.0)
        bone.scale = (1.0, 1.0, 1.0)


def apply_pose(arm, rotations=None, locations=None) -> None:
    rotations = rotations or {}
    locations = locations or {}
    for name, xyz_deg in rotations.items():
        bone = arm.pose.bones[name]
        bone.rotation_mode = "XYZ"
        bone.rotation_euler = tuple(math.radians(v) for v in xyz_deg)
    for name, xyz in locations.items():
        arm.pose.bones[name].location = xyz


def key_pose(arm, frame: int) -> None:
    for bone in arm.pose.bones:
        bone.keyframe_insert("rotation_euler", frame=frame, group=bone.name)
        bone.keyframe_insert("location", frame=frame, group=bone.name)


def make_action(arm, name: str, frames: int, poses: list[tuple[int, dict, dict]]) -> None:
    action = bpy.data.actions.new(name=name)
    action.use_fake_user = True
    arm.animation_data.action = action
    zero_pose(arm)
    for frame, rotations, locations in poses:
        zero_pose(arm)
        apply_pose(arm, rotations, locations)
        key_pose(arm, frame)
    action.frame_start = 1
    action.frame_end = frames
    for fcurve in action.fcurves:
        for point in fcurve.keyframe_points:
            point.interpolation = "BEZIER"
    arm.animation_data.action = None
    zero_pose(arm)


def build_actions(arm) -> None:
    # Idle: subtle breathing/weight shift.
    idle_a = {
        "Spine2": (1.0, 0.0, -1.0), "Spine3": (-1.0, 0.0, 1.5),
        "L_Shoulder": (1.5, 0.0, 0.0), "R_Shoulder": (-1.5, 0.0, 0.0),
    }
    idle_b = {
        "Spine2": (-1.0, 0.0, 1.0), "Spine3": (1.5, 0.0, -1.5),
        "L_Shoulder": (-1.0, 0.0, 0.0), "R_Shoulder": (1.0, 0.0, 0.0),
    }
    make_action(arm, "idle", 24, [(1, idle_a, {}), (12, idle_b, {"Pelvis": (0, 0, 0.012)}), (24, idle_a, {})])

    # Run: moderate arm swing; avoids dragging cape-adjacent shoulder geometry.
    run_a = {
        "L_Hip": (22, 0, 0), "R_Hip": (-22, 0, 0),
        "L_Knee": (14, 0, 0), "R_Knee": (38, 0, 0),
        "L_Shoulder": (-15, 0, 0), "R_Shoulder": (15, 0, 0),
        "L_Elbow": (24, 0, 0), "R_Elbow": (18, 0, 0),
        "Spine3": (3, 0, -1.5),
    }
    run_b = {
        "L_Hip": (-22, 0, 0), "R_Hip": (22, 0, 0),
        "L_Knee": (38, 0, 0), "R_Knee": (14, 0, 0),
        "L_Shoulder": (15, 0, 0), "R_Shoulder": (-15, 0, 0),
        "L_Elbow": (18, 0, 0), "R_Elbow": (24, 0, 0),
        "Spine3": (3, 0, 1.5),
    }
    make_action(arm, "run", 24, [
        (1, run_a, {"Pelvis": (0, 0, 0.02)}),
        (7, {}, {"Pelvis": (0, 0, 0.065)}),
        (13, run_b, {"Pelvis": (0, 0, 0.02)}),
        (19, {}, {"Pelvis": (0, 0, 0.065)}),
        (24, run_a, {"Pelvis": (0, 0, 0.02)}),
    ])

    # Rifle attack: compact low-ready -> shoulder aim -> recoil -> recovery.
    # Negative shoulder X raises the firing arm on this SMPL22 rest pose. The
    # support arm follows without extreme cape-adjacent shoulder deformation.
    ready = {
        "Spine2": (1, 0, 0), "Spine3": (-1, 0, 2),
        "L_Shoulder": (-8, 0, -6), "R_Shoulder": (-12, 0, 6),
        "L_Elbow": (30, 0, 0), "R_Elbow": (28, 0, 0),
    }
    aim = {
        "Spine2": (-2, 0, 0), "Spine3": (-3, 0, 1),
        "Neck": (1.5, 0, 0),
        "L_Shoulder": (-24, 0, -8), "R_Shoulder": (-32, 0, 5),
        "L_Elbow": (42, 0, 0), "R_Elbow": (18, 0, 0),
    }
    recoil = {
        "Spine2": (1.5, 0, 0), "Spine3": (2.5, 0, 1),
        "Neck": (-1.5, 0, 0),
        "L_Shoulder": (-21, 0, -7), "R_Shoulder": (-27, 0, 5),
        "L_Elbow": (39, 0, 0), "R_Elbow": (23, 0, 0),
    }
    make_action(arm, "attack", 18, [
        (1, ready, {}),
        (5, aim, {"Pelvis": (0, -0.012, 0)}),
        (9, recoil, {"Pelvis": (0, 0.012, -0.006)}),
        (12, aim, {"Pelvis": (0, -0.006, 0)}),
        (18, ready, {}),
    ])

    # Hit: compact recoil.
    hit = {
        "Spine1": (-3, 0, 0), "Spine2": (-5, 0, 3), "Spine3": (-7, 0, 5),
        "L_Shoulder": (-10, 0, -5), "R_Shoulder": (-8, 0, 6),
        "Neck": (5, 0, -3),
    }
    make_action(arm, "hit", 10, [(1, {}, {}), (4, hit, {"Pelvis": (0, 0.025, -0.015)}), (10, {}, {})])

    # Death QA: controlled knee-collapse rather than stacking huge spine bends.
    fall_mid = {
        "Spine1": (5, 0, 3), "Spine2": (7, 0, 4), "Spine3": (9, 0, 5),
        "L_Hip": (-12, 0, 3), "R_Hip": (8, 0, -2),
        "L_Knee": (30, 0, 0), "R_Knee": (26, 0, 0),
        "L_Shoulder": (8, 0, -8), "R_Shoulder": (-6, 0, 8),
        "Neck": (-5, 0, -2),
    }
    fall_end = {
        "Spine1": (9, 0, 5), "Spine2": (12, 0, 5), "Spine3": (15, 0, 4),
        "Neck": (-10, 0, -4), "Head": (-6, 0, 0),
        "L_Hip": (-18, 0, 5), "R_Hip": (12, 0, -4),
        "L_Knee": (48, 0, 0), "R_Knee": (42, 0, 0),
        "L_Shoulder": (12, 0, -10), "R_Shoulder": (-8, 0, 10),
        "L_Elbow": (22, 0, 0), "R_Elbow": (20, 0, 0),
    }
    make_action(arm, "death", 30, [
        (1, {}, {}),
        (12, fall_mid, {"Pelvis": (0, 0.025, -0.12)}),
        (30, fall_end, {"Pelvis": (0, 0.08, -0.36)}),
    ])


def main() -> None:
    args = parse_args()
    reset_scene()
    import_asset(args.input)
    arm, meshes = armature_and_meshes()
    ground_actor(arm, meshes)
    clear_actions(arm)
    build_actions(arm)
    expected = {"idle", "run", "attack", "hit", "death"}
    missing = expected - set(bpy.data.actions.keys())
    if missing:
        raise RuntimeError(f"Failed to create actions: {sorted(missing)}")
    args.output.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.wm.save_as_mainfile(filepath=str(args.output.resolve()))
    print("Rex actions:", sorted(expected))
    print("Saved:", args.output.resolve())


if __name__ == "__main__":
    main()
