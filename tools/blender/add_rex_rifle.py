#!/usr/bin/env python3
"""Add a deterministic separate sci-fi rifle to Rex.

The rifle is authored around a grip origin, placed explicitly at the evaluated
right-wrist joint in the rest pose, then bone-parented while preserving that
world transform. This avoids Blender bone-parent tail offsets that previously
left the weapon visibly detached from the hand after GLB export/import.
"""
import argparse
import sys
from pathlib import Path

import bpy
from mathutils import Matrix, Vector


def argv():
    a = sys.argv
    a = a[a.index("--") + 1:] if "--" in a else []
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    return p.parse_args(a)


def mat(name, color, metal=.5, rough=.32, emission=None):
    m = bpy.data.materials.new(name)
    m.diffuse_color = (*color, 1)
    m.use_nodes = True
    bs = m.node_tree.nodes.get("Principled BSDF")
    bs.inputs["Base Color"].default_value = (*color, 1)
    bs.inputs["Metallic"].default_value = metal
    bs.inputs["Roughness"].default_value = rough
    if emission:
        e = bs.inputs.get("Emission Color") or bs.inputs.get("Emission")
        if e:
            e.default_value = (*emission, 1)
        s = bs.inputs.get("Emission Strength")
        if s:
            s.default_value = .9
    return m


def cube(name, loc, scale, material):
    bpy.ops.mesh.primitive_cube_add(size=1, location=loc)
    o = bpy.context.object
    o.name = name
    o.scale = scale
    bpy.ops.object.transform_apply(location=False, rotation=False, scale=True)
    o.data.materials.append(material)
    return o


def main():
    a = argv()
    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)
    bpy.ops.import_scene.gltf(filepath=str(a.input.resolve()))

    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    assert len(arms) == 1
    arm = arms[0]
    bone = "R_Wrist" if arm.data.bones.get("R_Wrist") else "RightHand"
    assert arm.data.bones.get(bone)

    dark = mat("Rifle_Gunmetal", (0.012, 0.020, 0.030), .88, .22)
    navy = mat("Rifle_Navy", (0.020, 0.060, 0.090), .72, .27)
    cyan = mat("Rifle_Cyan", (0.01, .36, .54), .35, .18, (0.01, .34, .52))
    orange = mat("Rifle_Orange", (.56, .11, .02), .5, .30)

    # Weapon-local coordinates: +X muzzle, origin = firing-hand grip.
    # Slightly exaggerated silhouette is deliberate for 96px readability.
    parts = [
        cube("Rifle_Body", (.19, 0, .035), (.27, .062, .078), navy),
        cube("Rifle_Barrel", (.52, 0, .050), (.20, .031, .031), dark),
        cube("Rifle_Stock", (-.14, 0, .040), (.15, .070, .085), dark),
        cube("Rifle_Grip", (0, 0, -.085), (.040, .045, .095), dark),
        cube("Rifle_Sight", (.21, 0, .125), (.080, .028, .025), cyan),
        cube("Rifle_Accent", (.34, -.066, .040), (.060, .010, .026), orange),
    ]

    bpy.ops.object.select_all(action="DESELECT")
    for o in parts:
        o.select_set(True)
    bpy.context.view_layer.objects.active = parts[0]
    bpy.ops.object.join()
    rifle = bpy.context.object
    rifle.name = "Rex_Rifle"

    # Move the joined object's origin to the authored grip at world (0,0,0)
    # without moving geometry.
    bpy.context.scene.cursor.location = Vector((0, 0, 0))
    bpy.ops.object.origin_set(type="ORIGIN_CURSOR", center="MEDIAN")

    # Explicit rest-pose world placement. Rex faces -Y in the Blender sprite
    # scene; +X points inward from his right hand toward the torso. A diagonal
    # forward/inward low-ready vector remains legible from all 8 cameras.
    pb = arm.pose.bones[bone]
    bpy.context.view_layer.update()
    wrist_world = arm.matrix_world @ pb.matrix.translation
    direction = Vector((0.42, -0.88, -0.22)).normalized()
    rot = direction.to_track_quat("X", "Z").to_matrix().to_4x4()
    desired = rot
    desired.translation = wrist_world + Vector((0.0, -0.012, 0.010))

    rifle.parent = arm
    rifle.parent_type = "BONE"
    rifle.parent_bone = bone
    rifle.matrix_parent_inverse = Matrix.Identity(4)
    rifle.matrix_world = desired
    bpy.context.view_layer.update()

    grip_error = (rifle.matrix_world.translation - desired.translation).length
    assert grip_error < 1e-4, f"Rifle grip placement drifted by {grip_error:.6f}m"
    assert rifle.parent is arm and rifle.parent_bone == bone

    a.output.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.export_scene.gltf(
        filepath=str(a.output.resolve()),
        export_format="GLB",
        export_apply=False,
        export_animations=False,
    )
    print(
        "Rex rifle attached:", bone,
        "joined_parts", len(parts),
        "wrist_world", tuple(round(v, 4) for v in wrist_world),
        "grip_error", round(grip_error, 6),
    )


if __name__ == "__main__":
    main()
