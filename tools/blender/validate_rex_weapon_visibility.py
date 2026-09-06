#!/usr/bin/env python3
"""Validate that Rex's separate rifle is actually visible in sprite camera views.

Run inside Blender on the animated .blend after the rifle has been attached.
The check projects the rifle's evaluated mesh into the same orthographic camera
used by render_actor_8dir.py and verifies that representative poses keep a
meaningful weapon footprint on canvas. This catches successful-but-invisible
bone attachments before spending time judging 232 flattened PNGs manually.
"""
from __future__ import annotations

import argparse
import json
import math
import sys
from pathlib import Path

import bpy
from bpy_extras.object_utils import world_to_camera_view
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
ANIMATIONS = ("idle", "run", "attack", "hit", "death")


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--report", type=Path, required=True)
    p.add_argument("--size", type=int, default=512)
    p.add_argument("--camera-distance", type=float, default=7.0)
    p.add_argument("--camera-height", type=float, default=4.2)
    p.add_argument("--ortho-scale", type=float, default=2.8)
    p.add_argument("--target-height", type=float, default=0.95)
    p.add_argument("--min-long-axis-px", type=float, default=26.0)
    p.add_argument("--min-short-axis-px", type=float, default=5.0)
    p.add_argument("--min-visible-ratio", type=float, default=0.90)
    return p.parse_args(argv)


def find_armature():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if len(arms) != 1:
        raise RuntimeError(f"Expected exactly one armature, found {len(arms)}")
    return arms[0]


def find_rifle():
    exact = bpy.data.objects.get("Rex_Rifle")
    if exact and exact.type == "MESH":
        return exact
    matches = [o for o in bpy.context.scene.objects if o.type == "MESH" and "rifle" in o.name.lower()]
    if len(matches) != 1:
        raise RuntimeError(f"Expected one rifle mesh, found {[o.name for o in matches]}")
    return matches[0]


def ensure_camera(args: argparse.Namespace):
    cam = bpy.data.objects.get("DZ_Camera")
    if cam is None:
        data = bpy.data.cameras.new("DZ_Camera")
        cam = bpy.data.objects.new("DZ_Camera", data)
        bpy.context.collection.objects.link(cam)
    cam.data.type = "ORTHO"
    cam.data.ortho_scale = args.ortho_scale
    bpy.context.scene.camera = cam
    return cam


def look_at(obj, point: Vector) -> None:
    direction = point - obj.location
    obj.rotation_euler = direction.to_track_quat("-Z", "Y").to_euler()


def set_action(armature, name: str):
    action = bpy.data.actions.get(name)
    if action is None:
        matches = [a for a in bpy.data.actions if a.name.lower() == name.lower()]
        if not matches:
            raise RuntimeError(f"Missing Blender action: {name}")
        action = matches[0]
    if armature.animation_data is None:
        armature.animation_data_create()
    armature.animation_data.action = action
    return action


def representative_frame(action) -> int:
    start, end = action.frame_range
    return int(round((float(start) + float(end)) * 0.5))


def projected_metrics(scene, cam, obj, size: int):
    depsgraph = bpy.context.evaluated_depsgraph_get()
    evaluated = obj.evaluated_get(depsgraph)
    mesh = evaluated.to_mesh()
    try:
        coords = []
        for vertex in mesh.vertices:
            world = evaluated.matrix_world @ vertex.co
            ndc = world_to_camera_view(scene, cam, world)
            coords.append((float(ndc.x), float(ndc.y), float(ndc.z)))
        if not coords:
            raise RuntimeError("Rifle has no vertices")
        xs = [p[0] for p in coords]
        ys = [p[1] for p in coords]
        visible = [p for p in coords if 0.0 <= p[0] <= 1.0 and 0.0 <= p[1] <= 1.0 and p[2] >= 0.0]
        width = max(xs) - min(xs)
        height = max(ys) - min(ys)
        return {
            "bbox_ndc": [min(xs), min(ys), max(xs), max(ys)],
            "width_px": width * size,
            "height_px": height * size,
            "long_axis_px": max(width, height) * size,
            "short_axis_px": min(width, height) * size,
            "visible_ratio": len(visible) / len(coords),
            "vertex_count": len(coords),
        }
    finally:
        evaluated.to_mesh_clear()


def main() -> None:
    args = parse_args()
    scene = bpy.context.scene
    scene.render.resolution_x = args.size
    scene.render.resolution_y = args.size
    scene.render.resolution_percentage = 100
    arm = find_armature()
    rifle = find_rifle()
    cam = ensure_camera(args)
    target = Vector((0.0, 0.0, args.target_height))

    samples = []
    failures = []
    for direction, degrees in DIRECTIONS:
        radians = math.radians(degrees)
        cam.location = Vector((
            args.camera_distance * math.sin(radians),
            -args.camera_distance * math.cos(radians),
            args.camera_height,
        ))
        look_at(cam, target)
        for animation in ANIMATIONS:
            action = set_action(arm, animation)
            frame = representative_frame(action)
            scene.frame_set(frame)
            bpy.context.view_layer.update()
            metrics = projected_metrics(scene, cam, rifle, args.size)
            ok = (
                metrics["long_axis_px"] >= args.min_long_axis_px
                and metrics["short_axis_px"] >= args.min_short_axis_px
                and metrics["visible_ratio"] >= args.min_visible_ratio
            )
            sample = {
                "direction": direction,
                "animation": animation,
                "frame": frame,
                "pass": ok,
                **metrics,
            }
            samples.append(sample)
            if not ok:
                failures.append(sample)

    report = {
        "actor": "rex",
        "stage": "weapon-visibility-qa",
        "weapon_object": rifle.name,
        "sample_count": len(samples),
        "thresholds": {
            "min_long_axis_px": args.min_long_axis_px,
            "min_short_axis_px": args.min_short_axis_px,
            "min_visible_ratio": args.min_visible_ratio,
            "render_size": args.size,
        },
        "min_observed_long_axis_px": min(s["long_axis_px"] for s in samples),
        "min_observed_short_axis_px": min(s["short_axis_px"] for s in samples),
        "min_observed_visible_ratio": min(s["visible_ratio"] for s in samples),
        "failed_samples": failures,
        "samples": samples,
        "pass": not failures,
    }
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({k: report[k] for k in (
        "stage", "sample_count", "min_observed_long_axis_px",
        "min_observed_short_axis_px", "min_observed_visible_ratio", "pass"
    )}, indent=2))
    if failures:
        raise RuntimeError(f"Weapon visibility QA failed for {len(failures)} representative samples")


if __name__ == "__main__":
    main()
