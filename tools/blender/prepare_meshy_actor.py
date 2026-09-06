#!/usr/bin/env python3
"""Prepare a raw Meshy GLB as a Deadline Zero actor master.

Blender usage:
  blender -b --factory-startup -P tools/blender/prepare_meshy_actor.py -- \
    --input path/to/rex.glb --output build/rex_master.blend --actor Rex

This deliberately does NOT invent an armature. Automatic rigging without a
validated humanoid skeleton can silently produce bad deformation. Instead it
performs every safe deterministic preprocessing step and leaves a clean,
normalized master ready for rigging.
"""
from __future__ import annotations
import argparse
import sys
from pathlib import Path
import bpy
from mathutils import Vector


def args():
    a = sys.argv[sys.argv.index('--') + 1:] if '--' in sys.argv else []
    p = argparse.ArgumentParser()
    p.add_argument('--input', type=Path, required=True)
    p.add_argument('--output', type=Path, required=True)
    p.add_argument('--actor', default='Rex')
    p.add_argument('--target-height', type=float, default=2.0)
    p.add_argument('--decimate', type=float, default=0.0,
                   help='Optional target triangle count; 0 preserves master geometry')
    return p.parse_args(a)


def mesh_objects():
    return [o for o in bpy.context.scene.objects if o.type == 'MESH']


def world_bounds(objs):
    pts = [o.matrix_world @ Vector(corner) for o in objs for corner in o.bound_box]
    lo = Vector((min(p.x for p in pts), min(p.y for p in pts), min(p.z for p in pts)))
    hi = Vector((max(p.x for p in pts), max(p.y for p in pts), max(p.z for p in pts)))
    return lo, hi


def triangles(obj):
    deps = bpy.context.evaluated_depsgraph_get()
    m = obj.evaluated_get(deps).to_mesh()
    m.calc_loop_triangles(); n = len(m.loop_triangles)
    obj.evaluated_get(deps).to_mesh_clear()
    return n


def main():
    a = args()
    bpy.ops.object.select_all(action='SELECT'); bpy.ops.object.delete(use_global=False)
    bpy.ops.import_scene.gltf(filepath=str(a.input.resolve()))
    objs = mesh_objects()
    if not objs: raise RuntimeError('GLB contains no mesh')

    # Keep the HD source collection untouched by modifiers where possible.
    for o in objs:
        o.name = a.actor if len(objs) == 1 else f'{a.actor}_{o.name}'
        for p in o.data.polygons: p.use_smooth = True

    lo, hi = world_bounds(objs)
    h = hi.z - lo.z
    if h <= 1e-6: raise RuntimeError('Invalid actor height')
    scale = a.target_height / h
    center_xy = Vector(((lo.x + hi.x) * .5, (lo.y + hi.y) * .5, lo.z))
    for o in objs:
        o.location -= center_xy
        o.scale *= scale
    bpy.context.view_layer.update()

    # Apply transforms so downstream armatures/rendering get stable coordinates.
    for o in objs: o.select_set(True)
    bpy.context.view_layer.objects.active = objs[0]
    bpy.ops.object.transform_apply(location=False, rotation=False, scale=True)

    before = sum(triangles(o) for o in objs)
    if a.decimate and before > a.decimate:
        ratio = max(0.01, min(1.0, a.decimate / before))
        for o in objs:
            mod = o.modifiers.new('DZ_PreviewDecimate', 'DECIMATE'); mod.ratio = ratio
            bpy.context.view_layer.objects.active = o
            bpy.ops.object.modifier_apply(modifier=mod.name)
    after = sum(triangles(o) for o in objs)

    # Neutral material makes an untextured Meshy export readable while rigging.
    if not any(o.data.materials for o in objs):
        mat = bpy.data.materials.new('DZ_RiggingNeutral')
        mat.diffuse_color = (0.12, 0.14, 0.18, 1.0)
        mat.roughness = .55
        for o in objs: o.data.materials.append(mat)

    bpy.context.scene['dz_actor'] = a.actor
    bpy.context.scene['dz_source'] = str(a.input)
    bpy.context.scene['dz_triangles_source'] = before
    bpy.context.scene['dz_triangles_prepared'] = after
    a.output.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.wm.save_as_mainfile(filepath=str(a.output.resolve()))
    print(f'DZ_PREPARED actor={a.actor} triangles={before}->{after} output={a.output}')

if __name__ == '__main__': main()
