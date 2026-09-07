#!/usr/bin/env python3
"""Emit deterministic structural/action metadata for the currently opened .blend file."""
from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path

import bpy


def argv_after_separator() -> list[str]:
    return sys.argv[sys.argv.index("--") + 1 :] if "--" in sys.argv else []


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--output", required=True)
    args = parser.parse_args(argv_after_separator())

    actions = []
    for action in sorted(bpy.data.actions, key=lambda item: item.name.lower()):
        start, end = action.frame_range
        actions.append(
            {
                "name": action.name,
                "frame_start": float(start),
                "frame_end": float(end),
                "frame_span": float(max(0.0, end - start)),
                "fcurves": len(action.fcurves),
            }
        )

    armatures = []
    for obj in sorted((o for o in bpy.data.objects if o.type == "ARMATURE"), key=lambda item: item.name.lower()):
        armatures.append(
            {
                "name": obj.name,
                "bones": len(obj.data.bones),
                "pose_bones": len(obj.pose.bones) if obj.pose else 0,
            }
        )

    meshes = []
    for obj in sorted((o for o in bpy.data.objects if o.type == "MESH"), key=lambda item: item.name.lower()):
        meshes.append(
            {
                "name": obj.name,
                "vertices": len(obj.data.vertices),
                "polygons": len(obj.data.polygons),
                "materials": len(obj.data.materials),
                "vertex_groups": len(obj.vertex_groups),
            }
        )

    payload = {
        "blend": Path(bpy.data.filepath).name,
        "actions": actions,
        "action_names": [row["name"] for row in actions],
        "armatures": armatures,
        "meshes": meshes,
        "object_count": len(bpy.data.objects),
    }
    output = Path(args.output)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(payload, indent=2) + "\n", encoding="utf-8")
    print(json.dumps(payload, indent=2))


if __name__ == "__main__":
    main()
