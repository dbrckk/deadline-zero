#!/usr/bin/env python3
"""Add a deterministic separate sci-fi rifle to Rex.
All weapon geometry is built at the origin, joined, then attached with an explicit
bone-local grip transform. This avoids preserving accidental world transforms.
"""
import argparse, sys, math
from pathlib import Path
import bpy
from mathutils import Matrix

def argv():
 a=sys.argv; a=a[a.index('--')+1:] if '--' in a else []; p=argparse.ArgumentParser(); p.add_argument('--input',type=Path,required=True); p.add_argument('--output',type=Path,required=True); return p.parse_args(a)
def mat(name,color,metal=.5,rough=.32,emission=None):
 m=bpy.data.materials.new(name); m.diffuse_color=(*color,1); m.use_nodes=True; bs=m.node_tree.nodes.get('Principled BSDF'); bs.inputs['Base Color'].default_value=(*color,1); bs.inputs['Metallic'].default_value=metal; bs.inputs['Roughness'].default_value=rough
 if emission:
  e=bs.inputs.get('Emission Color') or bs.inputs.get('Emission')
  if e: e.default_value=(*emission,1)
  s=bs.inputs.get('Emission Strength')
  if s: s.default_value=.8
 return m
def cube(name,loc,scale,material):
 bpy.ops.mesh.primitive_cube_add(size=1,location=loc); o=bpy.context.object; o.name=name; o.scale=scale; bpy.ops.object.transform_apply(location=False,rotation=False,scale=True); o.data.materials.append(material); return o
def main():
 a=argv(); bpy.ops.object.select_all(action='SELECT'); bpy.ops.object.delete(use_global=False); bpy.ops.import_scene.gltf(filepath=str(a.input.resolve())); arms=[o for o in bpy.context.scene.objects if o.type=='ARMATURE']; assert len(arms)==1; arm=arms[0]
 bone='R_Wrist' if arm.data.bones.get('R_Wrist') else 'RightHand'; assert arm.data.bones.get(bone)
 dark=mat('Rifle_Gunmetal',(0.018,0.028,0.040),.85,.23); navy=mat('Rifle_Navy',(0.025,0.065,0.095),.7,.28); cyan=mat('Rifle_Cyan',(0.01,.32,.48),.35,.2,(0.01,.30,.46)); orange=mat('Rifle_Orange',(.50,.10,.02),.5,.3)
 # Weapon local coordinates: +X muzzle, origin = firing-hand grip.
 parts=[cube('Rifle_Body',(.18,0,.03),(.25,.055,.07),navy),cube('Rifle_Barrel',(.48,0,.045),(.18,.027,.027),dark),cube('Rifle_Stock',(-.13,0,.035),(.14,.06,.075),dark),cube('Rifle_Grip',(0,0,-.08),(.035,.04,.09),dark),cube('Rifle_Sight',(.20,0,.11),(.07,.025,.022),cyan),cube('Rifle_Accent',(.32,-.058,.035),(.05,.008,.022),orange)]
 bpy.ops.object.select_all(action='DESELECT'); [o.select_set(True) for o in parts]; bpy.context.view_layer.objects.active=parts[0]; bpy.ops.object.join(); rifle=bpy.context.object; rifle.name='Rex_Rifle'
 # Rebase joined mesh so object origin is the grip at (0,0,0).
 inv=rifle.matrix_world.inverted(); local_grip=inv @ Matrix.Translation((0,0,0)).translation
 for v in rifle.data.vertices: v.co-=local_grip
 rifle.location=(0,0,0); rifle.parent=arm; rifle.parent_type='BONE'; rifle.parent_bone=bone; rifle.matrix_parent_inverse=Matrix.Identity(4)
 # Conservative low-ready local orientation. Actor rotation supplies 8 directions.
 rifle.location=(.015,-.02,-.015); rifle.rotation_euler=(math.radians(5),math.radians(-12),math.radians(-18)); bpy.context.view_layer.update()
 assert rifle.parent is arm and rifle.parent_bone==bone
 a.output.parent.mkdir(parents=True,exist_ok=True); bpy.ops.export_scene.gltf(filepath=str(a.output.resolve()),export_format='GLB',export_apply=False,export_animations=False)
 print('Rex rifle attached:',bone,'joined_parts',len(parts))
if __name__=='__main__': main()
