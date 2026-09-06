#!/usr/bin/env python3
"""Validate a rigged Rex asset in headless Blender and render neutral previews."""
from __future__ import annotations

import argparse
import json
import math
import sys
from pathlib import Path

import bpy
from mathutils import Vector


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--size", type=int, default=512)
    return p.parse_args(argv)


def reset_scene() -> None:
    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)


def import_asset(path: Path) -> None:
    suffix = path.suffix.lower()
    if suffix in {".glb", ".gltf"}:
        bpy.ops.import_scene.gltf(filepath=str(path.resolve()))
    elif suffix == ".fbx":
        bpy.ops.import_scene.fbx(filepath=str(path.resolve()))
    else:
        raise RuntimeError(f"Unsupported rig format: {suffix}")


def world_bounds(meshes):
    points = [obj.matrix_world @ Vector(corner) for obj in meshes for corner in obj.bound_box]
    mins = Vector((min(p.x for p in points), min(p.y for p in points), min(p.z for p in points)))
    maxs = Vector((max(p.x for p in points), max(p.y for p in points), max(p.z for p in points)))
    return mins, maxs


def look_at(obj, target: Vector) -> None:
    obj.rotation_euler = (target - obj.location).to_track_quat("-Z", "Y").to_euler()


def ensure_material(meshes) -> None:
    fallback = bpy.data.materials.get("DZ_RigPreview") or bpy.data.materials.new("DZ_RigPreview")
    fallback.diffuse_color = (0.12, 0.16, 0.22, 1.0)
    for obj in meshes:
        if not obj.data.materials:
            obj.data.materials.append(fallback)


def add_area(name: str, location: Vector, energy: float, size: float, target: Vector):
    data = bpy.data.lights.new(name, "AREA")
    data.energy = energy
    data.size = size
    obj = bpy.data.objects.new(name, data)
    bpy.context.collection.objects.link(obj)
    obj.location = location
    look_at(obj, target)


def select_eevee_engine(scene) -> str:
    for engine in ("BLENDER_EEVEE_NEXT", "BLENDER_EEVEE"):
        try:
            scene.render.engine = engine
            return engine
        except TypeError:
            continue
    raise RuntimeError("No supported Eevee render engine found")


def render_previews(meshes, output: Path, size: int) -> tuple[list[str], str]:
    scene = bpy.context.scene
    engine = select_eevee_engine(scene)
    scene.render.resolution_x = size
    scene.render.resolution_y = size
    scene.render.resolution_percentage = 100
    scene.render.image_settings.file_format = "PNG"
    scene.render.image_settings.color_mode = "RGBA"
    scene.render.film_transparent = True

    mins, maxs = world_bounds(meshes)
    center = (mins + maxs) * 0.5
    height = max(0.01, maxs.z - mins.z)
    radius = max(maxs.x - mins.x, maxs.y - mins.y, height) * 1.8

    cam_data = bpy.data.cameras.new("DZ_RigValidationCamera")
    cam_data.type = "ORTHO"
    cam_data.ortho_scale = height * 1.25
    cam = bpy.data.objects.new("DZ_RigValidationCamera", cam_data)
    bpy.context.collection.objects.link(cam)
    scene.camera = cam

    add_area("DZ_Key", Vector((3, -4, 6)), 1000.0, 4.0, center)
    add_area("DZ_Fill", Vector((-4, -1, 3)), 600.0, 5.0, center)
    add_area("DZ_Rim", Vector((1, 4, 5)), 800.0, 3.0, center)

    preview_dir = output / "preview"
    preview_dir.mkdir(parents=True, exist_ok=True)
    results = []
    for name, degrees in (("front", 0), ("right", 90), ("rear", 180), ("left", 270), ("three_quarter", 45)):
        angle = math.radians(degrees)
        cam.location = Vector((center.x + radius * math.sin(angle), center.y - radius * math.cos(angle), center.z + height * 0.12))
        look_at(cam, center)
        path = preview_dir / f"rex_{name}.png"
        scene.render.filepath = str(path.resolve())
        bpy.ops.render.render(write_still=True)
        results.append(str(path))
    return results, engine


def main() -> None:
    args = parse_args()
    args.output.mkdir(parents=True, exist_ok=True)
    reset_scene()
    import_asset(args.input)

    all_meshes = [o for o in bpy.context.scene.objects if o.type == "MESH"]
    armatures = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if not all_meshes:
        raise RuntimeError("Rig validation failed: no mesh objects found")
    if len(armatures) != 1:
        raise RuntimeError(f"Rig validation failed: expected 1 armature, found {len(armatures)}")

    armature = armatures[0]
    # GLTF import may create editor-only custom-shape meshes for bones. They are
    # not Rex geometry. Validate and render only meshes actually deformed by the
    # single imported armature.
    meshes = [
        obj for obj in all_meshes
        if any(mod.type == "ARMATURE" and mod.object == armature for mod in obj.modifiers)
    ]
    if not meshes:
        raise RuntimeError("Rig validation failed: no mesh is deformed by the armature")

    bones = list(armature.data.bones)
    triangles = 0
    total_vertices = 0
    weighted_vertices = 0
    vertex_groups = 0

    for obj in meshes:
        obj.data.calc_loop_triangles()
        triangles += len(obj.data.loop_triangles)
        vertex_groups += len(obj.vertex_groups)
        for vertex in obj.data.vertices:
            total_vertices += 1
            if any(group.weight > 1e-6 for group in vertex.groups):
                weighted_vertices += 1

    ratio = weighted_vertices / total_vertices if total_vertices else 0.0
    mins, maxs = world_bounds(meshes)
    ensure_material(meshes)
    previews, render_engine = render_previews(meshes, args.output, args.size)

    report = {
        "input": str(args.input),
        "scene_mesh_objects": len(all_meshes),
        "deforming_mesh_objects": len(meshes),
        "ignored_helper_meshes": [obj.name for obj in all_meshes if obj not in meshes],
        "armatures": len(armatures),
        "bones": len(bones),
        "bone_names": [b.name for b in bones],
        "vertices": total_vertices,
        "triangles": triangles,
        "vertex_groups": vertex_groups,
        "armature_modifier_meshes": len(meshes),
        "weighted_vertices": weighted_vertices,
        "weighted_vertex_ratio": round(ratio, 6),
        "bounds_min": [round(v, 6) for v in mins],
        "bounds_max": [round(v, 6) for v in maxs],
        "dimensions": [round(maxs[i] - mins[i], 6) for i in range(3)],
        "render_engine": render_engine,
        "preview_files": previews,
        "pass": len(bones) >= 20 and ratio >= 0.95 and len(meshes) >= 1,
    }
    report_path = args.output / "rig-report.json"
    report_path.write_text(json.dumps(report, indent=2), encoding="utf-8")
    print(json.dumps(report, indent=2))
    if not report["pass"]:
        raise RuntimeError("Rig structural quality gate failed")


if __name__ == "__main__":
    main()
