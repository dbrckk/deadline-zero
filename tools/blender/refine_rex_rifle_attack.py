#!/usr/bin/env python3
"""Refine Rex's generic attack QA action into a clearer rifle-firing motion.

This runs after build_rex_actions.py on the generated .blend. It only replaces
the armature's `attack` action, leaving idle/run/hit/death untouched.
"""
from __future__ import annotations

import argparse
import math
import sys
from pathlib import Path

import bpy


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--output", type=Path, required=True)
    return p.parse_args(argv)


def find_armature():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if len(arms) != 1:
        raise RuntimeError(f"Expected one armature, found {len(arms)}")
    return arms[0]


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
        if name not in arm.pose.bones:
            continue
        bone = arm.pose.bones[name]
        bone.rotation_mode = "XYZ"
        bone.rotation_euler = tuple(math.radians(v) for v in xyz_deg)
    for name, xyz in locations.items():
        if name in arm.pose.bones:
            arm.pose.bones[name].location = xyz


def key_pose(arm, frame: int) -> None:
    for bone in arm.pose.bones:
        bone.keyframe_insert("rotation_euler", frame=frame, group=bone.name)
        bone.keyframe_insert("location", frame=frame, group=bone.name)


def rebuild_attack(arm) -> None:
    old = bpy.data.actions.get("attack")
    if old is not None:
        if arm.animation_data and arm.animation_data.action == old:
            arm.animation_data.action = None
        bpy.data.actions.remove(old)

    action = bpy.data.actions.new("attack")
    action.use_fake_user = True
    if arm.animation_data is None:
        arm.animation_data_create()
    arm.animation_data.action = action

    # Stronger phone-scale silhouette: low-ready -> shoulder aim -> recoil -> settle.
    # Angles stay below extreme cape-stressing ranges used by early QA versions.
    poses = [
        (1,
         {"Spine3": (1, 0, 0), "L_Shoulder": (5, 0, -5), "R_Shoulder": (8, 0, 5),
          "L_Elbow": (24, 0, 0), "R_Elbow": (26, 0, 0)},
         {}),
        (5,
         {"Spine2": (1, 0, -2), "Spine3": (3, 0, -3),
          "L_Shoulder": (30, 0, -10), "R_Shoulder": (42, 0, 8),
          "L_Elbow": (58, 0, 0), "R_Elbow": (48, 0, 0)},
         {"Pelvis": (0, -0.010, 0)}),
        (9,
         {"Spine2": (2, 0, -3), "Spine3": (4, 0, -4),
          "L_Shoulder": (38, 0, -12), "R_Shoulder": (52, 0, 10),
          "L_Elbow": (66, 0, 0), "R_Elbow": (54, 0, 0)},
         {"Pelvis": (0, -0.018, 0)}),
        (11,
         {"Spine2": (-1, 0, 2), "Spine3": (-3, 0, 3),
          "L_Shoulder": (34, 0, -10), "R_Shoulder": (45, 0, 8),
          "L_Elbow": (62, 0, 0), "R_Elbow": (46, 0, 0),
          "Neck": (2, 0, 1)},
         {"Pelvis": (0, 0.012, -0.006)}),
        (14,
         {"Spine2": (1, 0, -2), "Spine3": (3, 0, -3),
          "L_Shoulder": (36, 0, -11), "R_Shoulder": (50, 0, 9),
          "L_Elbow": (64, 0, 0), "R_Elbow": (52, 0, 0)},
         {"Pelvis": (0, -0.012, 0)}),
        (18,
         {"Spine3": (1, 0, 0), "L_Shoulder": (5, 0, -5), "R_Shoulder": (8, 0, 5),
          "L_Elbow": (24, 0, 0), "R_Elbow": (26, 0, 0)},
         {}),
    ]

    for frame, rotations, locations in poses:
        zero_pose(arm)
        apply_pose(arm, rotations, locations)
        key_pose(arm, frame)

    action.frame_start = 1
    action.frame_end = 18
    for fcurve in action.fcurves:
        for point in fcurve.keyframe_points:
            point.interpolation = "BEZIER"

    arm.animation_data.action = None
    zero_pose(arm)


def main() -> None:
    args = parse_args()
    arm = find_armature()
    rebuild_attack(arm)
    if bpy.data.actions.get("attack") is None:
        raise RuntimeError("attack action missing after refinement")
    args.output.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.wm.save_as_mainfile(filepath=str(args.output.resolve()))
    print("Refined Rex rifle attack action")
    print("Saved:", args.output.resolve())


if __name__ == "__main__":
    main()
