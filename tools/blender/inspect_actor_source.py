#!/usr/bin/env python3
"""Inspect a Blender actor source without modifying it.

Usage:
  blender -b actor.blend -P tools/blender/inspect_actor_source.py -- \
    --json-out build/actor-source-inspection.json
"""
from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path

import bpy


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    parser = argparse.ArgumentParser()
    parser.add_argument("--json-out", type=Path, required=True)
    return parser.parse_args(argv)


def rounded(values):
    return [round(float(v), 6) for v in values]


def main() -> None:
    args = parse_args()
    scene = bpy.context.scene
    meshes = [obj for obj in scene.objects if obj.type == "MESH"]
    armatures = [obj for obj in scene.objects if obj.type == "ARMATURE"]
    actions = []
    for action in bpy.data.actions:
        start, end = action.frame_range
        actions.append({
            "name": action.name,
            "frame_start": round(float(start), 3),
            "frame_end": round(float(end), 3),
            "fcurves": len(action.fcurves) if hasattr(action, "fcurves") else None,
        })

    mesh_rows = []
    total_vertices = 0
    total_polygons = 0
    total_triangles = 0
    for obj in meshes:
        mesh = obj.data
        mesh.calc_loop_triangles()
        total_vertices += len(mesh.vertices)
        total_polygons += len(mesh.polygons)
        total_triangles += len(mesh.loop_triangles)
        mesh_rows.append({
            "name": obj.name,
            "vertices": len(mesh.vertices),
            "polygons": len(mesh.polygons),
            "triangles": len(mesh.loop_triangles),
            "dimensions": rounded(obj.dimensions),
            "location": rounded(obj.location),
            "materials": [slot.material.name if slot.material else None for slot in obj.material_slots],
            "armature_modifiers": [
                modifier.object.name if modifier.object else None
                for modifier in obj.modifiers if modifier.type == "ARMATURE"
            ],
            "vertex_groups": len(obj.vertex_groups),
        })

    armature_rows = []
    for obj in armatures:
        armature_rows.append({
            "name": obj.name,
            "bones": len(obj.data.bones),
            "bone_names": [bone.name for bone in obj.data.bones],
            "dimensions": rounded(obj.dimensions),
            "location": rounded(obj.location),
            "active_action": obj.animation_data.action.name
                if obj.animation_data and obj.animation_data.action else None,
        })

    images = []
    for image in bpy.data.images:
        images.append({
            "name": image.name,
            "filepath": image.filepath,
            "packed": image.packed_file is not None,
            "size": [int(image.size[0]), int(image.size[1])],
        })

    report = {
        "blender_version": bpy.app.version_string,
        "scene": scene.name,
        "objects": len(scene.objects),
        "mesh_count": len(meshes),
        "armature_count": len(armatures),
        "total_vertices": total_vertices,
        "total_polygons": total_polygons,
        "total_triangles": total_triangles,
        "meshes": mesh_rows,
        "armatures": armature_rows,
        "actions": sorted(actions, key=lambda row: row["name"].lower()),
        "materials": sorted(material.name for material in bpy.data.materials),
        "images": images,
    }

    args.json_out.parent.mkdir(parents=True, exist_ok=True)
    args.json_out.write_text(json.dumps(report, indent=2), encoding="utf-8")
    print(json.dumps(report, indent=2))

    if len(armatures) != 1:
        raise RuntimeError(f"Expected one actor armature, found {len(armatures)}")
    if not meshes:
        raise RuntimeError("Actor source contains no mesh objects")


if __name__ == "__main__":
    main()
