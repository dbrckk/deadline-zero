#!/usr/bin/env python3
"""Normalize and budget a game asset in Blender before Godot import.

Usage:
  blender -b --factory-startup -P tools/blender/optimize_game_asset.py -- \
    --input source.glb --output build/prop.glb --role environment_prop --lod 0

This is intentionally conservative: it never auto-rigs or invents materials.
"""
from __future__ import annotations
import argparse, json, sys
from pathlib import Path
import bpy

ROOT = Path(__file__).resolve().parents[2]
MANIFEST = ROOT / "godot/assets/asset_manifest.json"

def parse():
    argv = sys.argv[sys.argv.index("--")+1:] if "--" in sys.argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--role", required=True)
    p.add_argument("--lod", type=int, choices=[0,1], default=0)
    p.add_argument("--target-triangles", type=int)
    p.add_argument("--apply-decimate", action="store_true")
    return p.parse_args(argv)

def import_asset(path: Path):
    ext = path.suffix.lower()
    if ext in {".glb",".gltf"}:
        bpy.ops.import_scene.gltf(filepath=str(path.resolve()))
    elif ext == ".fbx":
        bpy.ops.import_scene.fbx(filepath=str(path.resolve()))
    elif ext == ".obj":
        bpy.ops.wm.obj_import(filepath=str(path.resolve()))
    elif ext == ".blend":
        bpy.ops.wm.open_mainfile(filepath=str(path.resolve()))
    else:
        raise RuntimeError(f"Unsupported source format: {ext}")

def meshes():
    return [o for o in bpy.context.scene.objects if o.type == "MESH"]

def triangles(obj):
    dg = bpy.context.evaluated_depsgraph_get()
    eval_obj = obj.evaluated_get(dg)
    mesh = eval_obj.to_mesh()
    mesh.calc_loop_triangles()
    count = len(mesh.loop_triangles)
    eval_obj.to_mesh_clear()
    return count

def main():
    a = parse()
    manifest = json.loads(MANIFEST.read_text())
    budget = manifest["budgets"]["mobile"].get(a.role)
    if budget is None:
        raise RuntimeError(f"Unknown budget role: {a.role}")
    key = "triangles_lod0" if a.lod == 0 else "triangles_lod1"
    target = a.target_triangles or budget.get(key)
    if not target:
        raise RuntimeError(f"Role {a.role} has no {key} budget")

    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)
    import_asset(a.input)
    objs = meshes()
    if not objs:
        raise RuntimeError("No mesh objects imported")

    for obj in objs:
        for poly in obj.data.polygons:
            poly.use_smooth = True
    before = sum(triangles(o) for o in objs)

    if a.apply_decimate and before > target:
        ratio = max(0.02, min(1.0, target / float(before)))
        for obj in objs:
            # Do not destructively decimate skinned actors; use authored/retopo LODs for them.
            if any(mod.type == "ARMATURE" for mod in obj.modifiers):
                continue
            mod = obj.modifiers.new(f"DZ_LOD{a.lod}_Decimate", "DECIMATE")
            mod.ratio = ratio
            mod.use_collapse_triangulate = True
            bpy.context.view_layer.objects.active = obj
            bpy.ops.object.modifier_apply(modifier=mod.name)

    after = sum(triangles(o) for o in objs)
    material_slots = sum(len(o.material_slots) for o in objs)
    if after > target * 1.10:
        print(f"WARN triangles {after} exceed target {target}; manual retopo required")
    if material_slots > int(budget.get("materials", 99)):
        print(f"WARN material slots {material_slots} exceed role budget {budget.get('materials')}")

    a.output.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.export_scene.gltf(
        filepath=str(a.output.resolve()),
        export_format="GLB",
        export_apply=True,
        export_animations=True,
    )
    print(f"DZ_ASSET_OPTIMIZED role={a.role} lod={a.lod} triangles={before}->{after} materials={material_slots} output={a.output}")

if __name__ == "__main__":
    main()
