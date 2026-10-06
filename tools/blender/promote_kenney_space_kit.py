#!/usr/bin/env python3
"""Promote curated Kenney CC0 source geometry into Deadline: Zero GLBs.

Run with Blender 5.2.2 LTS:
  blender -b --factory-startup -P tools/blender/promote_kenney_space_kit.py -- \
    --source art_sources/third_party/kenney/space_kit \
    --output build/kenney-space-kit

The source FBX files remain outside Godot. This script creates runtime-ready GLB
LOD0/LOD1 meshes with project-owned material grading and no embedded textures.
"""
from __future__ import annotations
import argparse
import math
import sys
from pathlib import Path
import bpy

ASSETS = {
    "console": "console.fbx",
    "console_screen": "console_screen.fbx",
    "metal_fence": "metal_fence.fbx",
    "metal_structure_cross": "metal_structure_cross.fbx",
    "pipe_straight": "pipe_straight.fbx",
    "pipe_corner": "pipe_corner.fbx",
    "pipe_opening": "pipe_opening.fbx",
    "satellite_dish": "satellite_dish.fbx",
    "barrel_large": "barrel_large.fbx",
    "station_module": "station_module.fbx",
}

def parse():
    argv = sys.argv[sys.argv.index("--")+1:] if "--" in sys.argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--source", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    return p.parse_args(argv)

def make_material(name, color, metallic, roughness, emission=None):
    mat = bpy.data.materials.get(name) or bpy.data.materials.new(name)
    mat.diffuse_color = (*color, 1.0)
    mat.metallic = metallic
    mat.roughness = roughness
    if emission is not None:
        mat.use_nodes = True
        bsdf = mat.node_tree.nodes.get("Principled BSDF")
        if bsdf is not None:
            if "Emission Color" in bsdf.inputs:
                bsdf.inputs["Emission Color"].default_value = (*emission, 1.0)
            if "Emission Strength" in bsdf.inputs:
                bsdf.inputs["Emission Strength"].default_value = 1.6
    return mat

def clear_scene():
    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)
    for datablocks in (bpy.data.meshes, bpy.data.curves, bpy.data.armatures):
        for block in list(datablocks):
            if block.users == 0:
                datablocks.remove(block)

def meshes():
    return [o for o in bpy.context.scene.objects if o.type == "MESH"]

def import_fbx(path: Path):
    bpy.ops.import_scene.fbx(filepath=str(path.resolve()), automatic_bone_orientation=False)

def bbox_diagonal(obj):
    corners = [obj.matrix_world @ mathutils.Vector(corner) for corner in obj.bound_box]
    xs=[p.x for p in corners]; ys=[p.y for p in corners]; zs=[p.z for p in corners]
    return math.sqrt((max(xs)-min(xs))**2 + (max(ys)-min(ys))**2 + (max(zs)-min(zs))**2)

def prepare_geometry(objs, steel, accent, lod):
    # Normalize source material count to two shared project materials.
    for obj in objs:
        original_names=[slot.material.name.lower() if slot.material else "" for slot in obj.material_slots]
        obj.data.materials.clear()
        obj.data.materials.append(steel)
        obj.data.materials.append(accent)
        # Preserve accent-like faces if the imported source exposed a dedicated light/glass material.
        for poly in obj.data.polygons:
            old = original_names[poly.material_index] if poly.material_index < len(original_names) else ""
            poly.material_index = 1 if any(t in old for t in ("light","glass","screen","portal","accent")) else 0
            poly.use_smooth = True

        # Small real bevels create stable specular edge definition from the gameplay camera.
        dims=max(obj.dimensions.x,obj.dimensions.y,obj.dimensions.z)
        if dims > 0.001:
            bevel=obj.modifiers.new("DZ_HardSurfaceBevel","BEVEL")
            bevel.width=min(0.025,max(0.004,dims*0.008))
            bevel.segments=2 if lod == 0 else 1
            bevel.limit_method="ANGLE"
            bpy.context.view_layer.objects.active=obj
            bpy.ops.object.modifier_apply(modifier=bevel.name)

        if lod == 1:
            # Source assets are already lightweight; only decimate meaningful meshes.
            if len(obj.data.polygons) > 180:
                dec=obj.modifiers.new("DZ_LOD1","DECIMATE")
                dec.ratio=0.62
                dec.use_collapse_triangulate=True
                bpy.context.view_layer.objects.active=obj
                bpy.ops.object.modifier_apply(modifier=dec.name)

        obj["dz_asset_role"]="environment_prop"
        obj["dz_source"]="kenney_space_kit_cc0"
        obj["dz_lod"]=lod

def export_asset(path: Path, objs):
    bpy.ops.object.select_all(action="DESELECT")
    for obj in objs:
        obj.select_set(True)
    bpy.context.view_layer.objects.active=objs[0]
    path.parent.mkdir(parents=True,exist_ok=True)
    bpy.ops.export_scene.gltf(
        filepath=str(path.resolve()),
        export_format="GLB",
        use_selection=True,
        export_apply=True,
        export_animations=False,
        export_extras=True,
    )

def main():
    a=parse()
    # Import mathutils lazily so the file remains inspectable outside Blender.
    global mathutils
    import mathutils

    a.output.mkdir(parents=True,exist_ok=True)
    for key,filename in ASSETS.items():
        source=a.source/filename
        if not source.exists():
            raise RuntimeError(f"Missing curated Kenney source: {source}")
        for lod in (0,1):
            clear_scene()
            import_fbx(source)
            objs=meshes()
            if not objs:
                raise RuntimeError(f"No mesh objects imported from {source}")

            steel=make_material("DZ_KenneyDarkSteel",(0.045,0.065,0.075),0.68,0.42)
            accent=make_material("DZ_KenneySystemAccent",(0.025,0.34,0.46),0.30,0.34,(0.01,0.18,0.28))
            prepare_geometry(objs,steel,accent,lod)
            export_asset(a.output/f"dz_kenney_{key}_lod{lod}.glb",objs)
            print(f"DZ_KENNEY_EXPORTED asset={key} lod={lod} meshes={len(objs)}")
    print(f"DZ_KENNEY_KIT_OK assets={len(ASSETS)} lods=2")

if __name__=="__main__":
    main()
