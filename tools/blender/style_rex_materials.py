#!/usr/bin/env python3
"""Apply a deterministic phone-scale Rex palette to an imported rigged actor.

The Meshy Lite source has no UV/material data. This pass therefore uses narrow,
stable spatial regions rather than broad body bands. The goal is readability at
96px: dark navy/black armor as the dominant mass, a small cyan visor/energy cue,
very restrained orange accents, and a dark cape. No geometry or weights change.
"""
from __future__ import annotations
import argparse, sys
from pathlib import Path
import bpy
from mathutils import Vector


def parse():
    a=sys.argv; a=a[a.index('--')+1:] if '--' in a else []
    p=argparse.ArgumentParser()
    p.add_argument('--input',type=Path,required=True)
    p.add_argument('--output',type=Path,required=True)
    return p.parse_args(a)


def mat(name,color,metallic=.0,rough=.5,emit=None,emit_strength=.0):
    m=bpy.data.materials.new(name)
    m.diffuse_color=(*color,1)
    m.use_nodes=True
    bs=m.node_tree.nodes.get('Principled BSDF')
    bs.inputs['Base Color'].default_value=(*color,1)
    bs.inputs['Metallic'].default_value=metallic
    bs.inputs['Roughness'].default_value=rough
    if emit:
        for n in ('Emission Color','Emission'):
            if bs.inputs.get(n):
                try: bs.inputs[n].default_value=(*emit,1)
                except TypeError: pass
        if bs.inputs.get('Emission Strength'):
            bs.inputs['Emission Strength'].default_value=emit_strength
    return m


def main():
    a=parse()
    bpy.ops.object.select_all(action='SELECT')
    bpy.ops.object.delete(use_global=False)
    bpy.ops.import_scene.gltf(filepath=str(a.input.resolve()))
    meshes=[o for o in bpy.context.scene.objects if o.type=='MESH' and any(x.type=='ARMATURE' for x in o.modifiers)]
    assert meshes

    mats=[
        mat('Rex_Navy',(0.018,0.032,0.055),.72,.30),
        mat('Rex_Black',(0.005,0.008,0.012),.45,.40),
        mat('Rex_Cape',(0.012,0.019,0.030),.08,.68),
        mat('Rex_Cyan',(0.00,0.18,0.28),.18,.28,(0.0,0.38,0.58),.32),
        mat('Rex_Orange',(0.34,0.055,0.008),.42,.34),
    ]

    for o in meshes:
        for m in mats: o.data.materials.append(m)
        pts=[o.matrix_world@Vector(c) for c in o.bound_box]
        lo=Vector(tuple(min(p[i] for p in pts) for i in range(3)))
        hi=Vector(tuple(max(p[i] for p in pts) for i in range(3)))
        h=hi.z-lo.z; w=hi.x-lo.x; d=hi.y-lo.y
        cx=(lo.x+hi.x)/2; cy=(lo.y+hi.y)/2

        for poly in o.data.polygons:
            p=o.matrix_world@poly.center
            zn=(p.z-lo.z)/h
            xn=abs(p.x-cx)/w
            yn=(p.y-cy)/d
            idx=0

            # Cape: keep it darker than the armor but not pitch black.
            if yn>.10 and .31<zn<.90 and xn<.36:
                idx=2
            # Cyan visor: deliberately narrow, front-most and high on the helmet.
            elif .865<zn<.925 and yn<-.17 and xn<.15:
                idx=3
            # Small chest energy marker only; avoid turning the breastplate white/cyan.
            elif .645<zn<.705 and yn<-.18 and xn<.075:
                idx=3
            # Restrained orange: tiny outer-shoulder tabs and shin tabs on the front half.
            elif ((.705<zn<.755 and .285<xn<.39 and yn<.05) or
                  (.205<zn<.265 and .255<xn<.34 and yn<.02)):
                idx=4
            # Dark undersuit/joints: elbows, inner legs, waist gaps.
            elif ((.44<zn<.60 and xn>.27) or
                  (.30<zn<.42 and xn<.17) or
                  (.10<zn<.21 and xn<.16)):
                idx=1
            else:
                idx=0
            poly.material_index=idx

    a.output.parent.mkdir(parents=True,exist_ok=True)
    bpy.ops.export_scene.gltf(filepath=str(a.output.resolve()),export_format='GLB',export_apply=False,export_animations=False)
    print('Styled Rex v2:',a.output)


if __name__=='__main__':
    main()
