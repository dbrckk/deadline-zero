#!/usr/bin/env python3
"""Add a deterministic stylized sci-fi rifle to Rex for sprite production.
The weapon is a separate rigid prop parented to the right wrist bone; no source mesh mutation.
"""
import argparse, sys
from pathlib import Path
import bpy
from mathutils import Vector

def argv():
 a=sys.argv; a=a[a.index('--')+1:] if '--' in a else []; p=argparse.ArgumentParser(); p.add_argument('--input',type=Path,required=True); p.add_argument('--output',type=Path,required=True); return p.parse_args(a)
def mat(name,color,metal=.5,rough=.32,emission=None):
 m=bpy.data.materials.new(name); m.diffuse_color=(*color,1); m.use_nodes=True; bs=m.node_tree.nodes.get('Principled BSDF'); bs.inputs['Base Color'].default_value=(*color,1); bs.inputs['Metallic'].default_value=metal; bs.inputs['Roughness'].default_value=rough
 if emission and 'Emission' in bs.inputs: bs.inputs['Emission'].default_value=(*emission,1); bs.inputs['Emission Strength'].default_value=.7
 return m
def cube(name,loc,scale,material,parent,bone):
 bpy.ops.mesh.primitive_cube_add(size=1,location=loc); o=bpy.context.object; o.name=name; o.scale=scale; bpy.ops.object.transform_apply(location=False,rotation=False,scale=True); o.data.materials.append(material); o.parent=parent; o.parent_type='BONE'; o.parent_bone=bone; return o
def main():
 a=argv(); bpy.ops.object.select_all(action='SELECT'); bpy.ops.object.delete(use_global=False); bpy.ops.import_scene.gltf(filepath=str(a.input.resolve())); arms=[o for o in bpy.context.scene.objects if o.type=='ARMATURE']; assert len(arms)==1; arm=arms[0]
 bone='R_Wrist' if arm.data.bones.get('R_Wrist') else 'RightHand'; assert arm.data.bones.get(bone)
 dark=mat('Rifle_Gunmetal',(0.018,0.028,0.040),.85,.23); navy=mat('Rifle_Navy',(0.025,0.065,0.095),.7,.28); cyan=mat('Rifle_Cyan',(0.01,.42,.62),.35,.2,(0.01,.35,.55)); orange=mat('Rifle_Orange',(.55,.12,.025),.5,.3)
 # Local bone-space construction: long, chunky bullpup silhouette readable at 96px.
 parts=[]
 parts.append(cube('Rifle_Body',(0,0,0),(0.26,.055,.075),navy,arm,bone)); parts.append(cube('Rifle_Barrel',(.28,0,.015),(.20,.027,.027),dark,arm,bone)); parts.append(cube('Rifle_Stock',(-.22,0,.015),(.12,.065,.085),dark,arm,bone)); parts.append(cube('Rifle_Grip',(-.04,0,-.10),(.035,.04,.09),dark,arm,bone)); parts.append(cube('Rifle_Sight',(.05,0,.095),(.075,.025,.025),cyan,arm,bone)); parts.append(cube('Rifle_Accent',(.17,-.058,.02),(.055,.008,.025),orange,arm,bone))
 # Parent-to-bone transforms are intentionally conservative; action QA will reveal orientation errors.
 for o in parts: o.rotation_euler[1]=0; o.rotation_euler[2]=0
 a.output.parent.mkdir(parents=True,exist_ok=True); bpy.ops.export_scene.gltf(filepath=str(a.output.resolve()),export_format='GLB',export_apply=False,export_animations=False)
 print('Rex rifle attached:',bone,'parts',len(parts))
if __name__=='__main__': main()
