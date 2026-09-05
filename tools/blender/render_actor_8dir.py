#!/usr/bin/env python3
"""Blender-side renderer for Deadline Zero actor sprites.

Run with Blender, not regular Python:
  blender -b path/to/rex.blend -P tools/blender/render_actor_8dir.py -- \
    --actor rex --output build/blender_renders/rex

Requirements inside the .blend file:
- One armature object containing actions named: idle, run, attack, hit, death
- A camera named DZ_Camera (created automatically if absent)
- Actor centered near world origin, feet near Z=0

The script renders transparent PNG frames for the eight Deadline Zero directions.
It renders at 512x512 by default so frames can later be downsampled to the
contracted 96x96 production cells.
"""
from __future__ import annotations

import argparse
import math
import sys
from pathlib import Path

import bpy
from mathutils import Vector

DIRECTIONS = [
    ("n", 180.0),
    ("ne", 225.0),
    ("e", 270.0),
    ("se", 315.0),
    ("s", 0.0),
    ("sw", 45.0),
    ("w", 90.0),
    ("nw", 135.0),
]
DEFAULT_COUNTS = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--actor", required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--size", type=int, default=512)
    p.add_argument("--camera-distance", type=float, default=7.0)
    p.add_argument("--camera-height", type=float, default=4.5)
    p.add_argument("--ortho-scale", type=float, default=5.0)
    return p.parse_args(argv)


def find_armature():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if not arms:
        raise RuntimeError("No armature found in scene")
    return arms[0]


def ensure_camera(args: argparse.Namespace):
    cam_obj = bpy.data.objects.get("DZ_Camera")
    if cam_obj is None:
        data = bpy.data.cameras.new("DZ_Camera")
        cam_obj = bpy.data.objects.new("DZ_Camera", data)
        bpy.context.collection.objects.link(cam_obj)
    cam_obj.data.type = "ORTHO"
    cam_obj.data.ortho_scale = args.ortho_scale
    bpy.context.scene.camera = cam_obj
    return cam_obj


def look_at(obj, point: Vector):
    direction = point - obj.location
    obj.rotation_euler = direction.to_track_quat("-Z", "Y").to_euler()


def configure_scene(args: argparse.Namespace):
    scene = bpy.context.scene
    scene.render.engine = "BLENDER_EEVEE_NEXT"
    scene.render.resolution_x = args.size
    scene.render.resolution_y = args.size
    scene.render.resolution_percentage = 100
    scene.render.image_settings.file_format = "PNG"
    scene.render.image_settings.color_mode = "RGBA"
    scene.render.film_transparent = True
    scene.render.image_settings.color_depth = "8"
    scene.render.resolution_percentage = 100
    scene.view_settings.look = "AgX - Medium High Contrast"


def set_action(armature, action_name: str):
    action = bpy.data.actions.get(action_name)
    if action is None:
        matches = [a for a in bpy.data.actions if a.name.lower() == action_name.lower()]
        if not matches:
            raise RuntimeError(f"Missing Blender action: {action_name}")
        action = matches[0]
    if armature.animation_data is None:
        armature.animation_data_create()
    armature.animation_data.action = action
    return action


def sample_frames(action, wanted: int) -> list[int]:
    start, end = action.frame_range
    start_i, end_i = int(round(start)), int(round(end))
    if wanted <= 1:
        return [start_i]
    span = max(1, end_i - start_i + 1)
    return [start_i + min(span - 1, math.floor(i * span / wanted)) for i in range(wanted)]


def main():
    args = parse_args()
    configure_scene(args)
    armature = find_armature()
    cam = ensure_camera(args)
    root = args.output.resolve()
    root.mkdir(parents=True, exist_ok=True)

    target = Vector((0.0, 0.0, 1.25))
    scene = bpy.context.scene

    for direction, degrees in DIRECTIONS:
        radians = math.radians(degrees)
        cam.location = Vector((
            args.camera_distance * math.sin(radians),
            -args.camera_distance * math.cos(radians),
            args.camera_height,
        ))
        look_at(cam, target)

        for animation, wanted in DEFAULT_COUNTS.items():
            action = set_action(armature, animation)
            frames = sample_frames(action, wanted)
            out_dir = root / animation / direction
            out_dir.mkdir(parents=True, exist_ok=True)
            for index, frame in enumerate(frames):
                scene.frame_set(frame)
                scene.render.filepath = str(out_dir / f"{animation}_{index:02d}.png")
                bpy.ops.render.render(write_still=True)

    print(f"Rendered {args.actor} to {root}")


if __name__ == "__main__":
    main()
