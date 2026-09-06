#!/usr/bin/env python3
"""Apply a deterministic premium Rex palette to an imported rigged actor.

The Meshy Lite source has no UV/material data. This pass uses stable spatial
regions and face orientation to recover the intended phone-scale identity:
near-black/navy armor, cyan visor/chest energy, restrained orange accents,
and a darker cape. It assigns materials per polygon without changing geometry,
weights, armature, or actions.
"""
from __future__ import annotations
import argparse, sys
from pathlib import Path
import bpy
from mathutils import Vector

def parse():
 a=sys.argv; a=a[a.index('--')+1:] if '--' in a else []
 p=argparse.ArgumentParser(); p.add_argument('--input',type=Path,required=True); p.add_argument('--output',type=Path,required=True); return p.parse_args(a)

def mat(name,color,metallic=.0,rough=.5,emit=None):
 m=bpy.data.materials.new(name); m.diffuse_color=(*color,1); m.use_nodes=True
 bs=m.node_tree.nodes.get('Principled BSDF'); bs.inputs['Base Color'].default_value=(*color,1); bs.inputs['Metallic'].default_value=metallic; bs.inputs['Roughness'].default_value=rough
 if emit:
  # Blender 3.x/4.x compatible emission sockets.
  for n in ('Emission Color','Emission'):
   if bs.inputs.get(n): bs.inputs[n].default_value=(*emit,1)
  if bs.inputs.get('Emission Strength'): bs.inputs['Emission Strength'].default_value=2.5
 return m

def main():
 a=parse(); bpy.ops.object.select_all(action='SELECT'); bpy.ops.object.delete(use_global=False); bpy.ops.import_scene.gltf(filepath=str(a.input.resolve()))
 meshes=[o for o in bpy.context.scene.objects if o.type=='MESH' and any(x.type=='ARMATURE' for x in o.modifiers)]; assert meshes
 mats=[mat('Rex_Navy',(0.025,0.045,0.075),.72,.27),mat('Rex_Black',(0.008,0.012,0.018),.55,.34),mat('Rex_Cape',(0.018,0.028,0.043),.12,.62),mat('Rex_Cyan',(0.01,0.42,0.60),.25,.22,(0.0,0.65,0.95)),mat('Rex_Orange',(0.72,0.16,0.025),.48,.30)]
 for o in meshes:
  for m in mats:o.data.materials.append(m)
  pts=[o.matrix_world@Vector(c) for c in o.bound_box]; lo=Vector(tuple(min(p[i] for p in pts) for i in range(3))); hi=Vector(tuple(max(p[i] for p in pts) for i in range(3))); h=hi.z-lo.z; w=hi.x-lo.x; d=hi.y-lo.y; cx=(lo.x+hi.x)/2; cy=(lo.y+hi.y)/2
  for poly in o.data.polygons:
   p=o.matrix_world@poly.center; zn=(p.z-lo.z)/h; xn=abs(p.x-cx)/w; yn=(p.y-cy)/d
   idx=0
   # Back-central cape.
   if yn>.08 and .30<zn<.91 and xn<.38: idx=2
   # Helmet visor/front face: cyan band high on head, front side.
   elif zn>.82 and yn<-.02 and xn<.22: idx=3
   # Chest energy/readability accent.
   elif .58<zn<.76 and yn<-.06 and xn<.18: idx=3
   # Restrained orange on outer shoulders / lower legs.
   elif (.68<zn<.82 and .20<xn<.48) or (.16<zn<.34 and .20<xn<.42): idx=4
   # Dark joints/undersuit.
   elif (.38<zn<.58 and xn>.24) or (.08<zn<.25 and xn<.18): idx=1
   poly.material_index=idx
 a.output.parent.mkdir(parents=True,exist_ok=True); bpy.ops.export_scene.gltf(filepath=str(a.output.resolve()),export_format='GLB',export_apply=False,export_animations=False)
 print('Styled Rex:',a.output)
if __name__=='__main__':main()
