#!/usr/bin/env python3
"""Build deterministic Rex animation actions on a rigged SMPL22 GLB.

This is the automated deformation/animation smoke stage. It is intentionally
conservative: the actions exercise shoulders, elbows, spine, hips and knees
without pretending to replace final hand-authored motion. The output .blend is
then consumed by render_actor_8dir.py.
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
    # Idle: restrained breathing and weight shift. Loop closes exactly.
    idle_a = {
        "Spine2": (1.0, 0.0, -1.0), "Spine3": (-1.0, 0.0, 1.5),
        "L_Shoulder": (1.5, 0.0, 0.0), "R_Shoulder": (-1.5, 0.0, 0.0),
    }
    idle_b = {
        "Spine2": (-1.0, 0.0, 1.0), "Spine3": (1.5, 0.0, -1.5),
        "L_Shoulder": (-1.0, 0.0, 0.0), "R_Shoulder": (1.0, 0.0, 0.0),
    }
    make_action(arm, "idle", 24, [(1, idle_a, {}), (12, idle_b, {"Pelvis": (0, 0, 0.012)}), (24, idle_a, {})])

    # Run: deliberately exercises hips/knees/shoulders and cape-adjacent torso.
    run_a = {
        "L_Hip": (24, 0, 0), "R_Hip": (-24, 0, 0),
        "L_Knee": (12, 0, 0), "R_Knee": (42, 0, 0),
        "L_Shoulder": (-22, 0, 0), "R_Shoulder": (22, 0, 0),
        "L_Elbow": (28, 0, 0), "R_Elbow": (18, 0, 0),
        "Spine3": (4, 0, -2),
    }
    run_b = {
        "L_Hip": (-24, 0, 0), "R_Hip": (24, 0, 0),
        "L_Knee": (42, 0, 0), "R_Knee": (12, 0, 0),
        "L_Shoulder": (22, 0, 0), "R_Shoulder": (-22, 0, 0),
        "L_Elbow": (18, 0, 0), "R_Elbow": (28, 0, 0),
        "Spine3": (4, 0, 2),
    }
    make_action(arm, "run", 24, [
        (1, run_a, {"Pelvis": (0, 0, 0.025)}),
        (7, {}, {"Pelvis": (0, 0, 0.075)}),
        (13, run_b, {"Pelvis": (0, 0, 0.025)}),
        (19, {}, {"Pelvis": (0, 0, 0.075)}),
        (24, run_a, {"Pelvis": (0, 0, 0.025)}),
    ])

    # Attack smoke: two-handed forward strike/aim gesture, useful for shoulder/elbow QA.
    windup = {
        "Spine3": (-4, 0, 8),
        "L_Shoulder": (-18, 0, -12), "R_Shoulder": (-12, 0, 14),
        "L_Elbow": (36, 0, 0), "R_Elbow": (28, 0, 0),
    }
    strike = {
        "Spine3": (8, 0, -8),
        "L_Shoulder": (42, 0, -6), "R_Shoulder": (38, 0, 8),
        "L_Elbow": (12, 0, 0), "R_Elbow": (8, 0, 0),
    }
    make_action(arm, "attack", 18, [(1, {}, {}), (6, windup, {}), (11, strike, {"Pelvis": (0, -0.035, 0)}), (18, {}, {})])

    # Hit reaction: compact torso recoil and asymmetric shoulder response.
    hit = {
        "Spine1": (-5, 0, 0), "Spine2": (-8, 0, 5), "Spine3": (-10, 0, 8),
        "L_Shoulder": (-16, 0, -8), "R_Shoulder": (-10, 0, 10),
        "Neck": (8, 0, -5),
    }
    make_action(arm, "hit", 10, [(1, {}, {}), (4, hit, {"Pelvis": (0, 0.045, -0.025)}), (10, {}, {})])

    # Death smoke: strong whole-body deformation; this is a QA motion, not final choreography.
    fall_mid = {
        "Spine1": (12, 0, 8), "Spine2": (18, 0, 10), "Spine3": (24, 0, 14),
        "L_Hip": (-18, 0, 6), "R_Hip": (10, 0, -5),
        "L_Knee": (34, 0, 0), "R_Knee": (22, 0, 0),
        "L_Shoulder": (16, 0, -20), "R_Shoulder": (-10, 0, 18),
    }
    fall_end = {
        "Spine1": (38, 0, 18), "Spine2": (42, 0, 14), "Spine3": (48, 0, 10),
        "Neck": (-18, 0, -8), "Head": (-12, 0, 0),
        "L_Hip": (-30, 0, 10), "R_Hip": (18, 0, -8),
        "L_Knee": (58, 0, 0), "R_Knee": (44, 0, 0),
        "L_Shoulder": (28, 0, -26), "R_Shoulder": (-18, 0, 24),
        "L_Elbow": (36, 0, 0), "R_Elbow": (32, 0, 0),
    }
    make_action(arm, "death", 30, [
        (1, {}, {}),
        (12, fall_mid, {"Pelvis": (0, 0.02, -0.18)}),
        (30, fall_end, {"Pelvis": (0, 0.08, -0.62)}),
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
