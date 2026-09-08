#!/usr/bin/env python3
"""Blender-side renderer for Deadline Zero actor sprites."""
from __future__ import annotations

import argparse
import math
import os
import sys
from pathlib import Path

import bpy
from mathutils import Matrix, Vector

DIRECTIONS = [
    ("n", 180.0), ("ne", 225.0), ("e", 270.0), ("se", 315.0),
    ("s", 0.0), ("sw", 45.0), ("w", 90.0), ("nw", 135.0),
]
DEFAULT_COUNTS = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}


def parse_args() -> argparse.Namespace:
    argv = sys.argv
    argv = argv[argv.index("--") + 1 :] if "--" in argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--actor", required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--size", type=int, default=512)
    p.add_argument("--camera-distance", type=float, default=7.0)
    p.add_argument("--camera-height", type=float, default=4.2)
    p.add_argument("--ortho-scale", type=float, default=2.8)
    p.add_argument("--target-height", type=float, default=0.95)
    p.add_argument("--idle-action", default="idle")
    p.add_argument("--run-action", default="run")
    p.add_argument("--attack-action", default="attack")
    p.add_argument("--hit-action", default="hit")
    p.add_argument("--death-action", default="death")
    return p.parse_args(argv)


def find_armature():
    arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
    if len(arms) != 1:
        raise RuntimeError(f"Expected exactly one armature, found {len(arms)}")
    return arms[0]


def ensure_camera(args: argparse.Namespace):
    cam_obj = bpy.data.objects.get("DZ_Camera")
    if cam_obj is None:
        data = bpy.data.cameras.new("DZ_Camera")
        cam_obj = bpy.data.objects.new("DZ_Camera", data)
        bpy.context.collection.objects.link(cam_obj)
    cam_obj.data.type = "ORTHO"
    cam_obj.data.ortho_scale = args.ortho_scale
    bpy.context.scene.camera = cam_obj
    return cam_obj


def look_at(obj, point: Vector):
    direction = point - obj.location
    obj.rotation_euler = direction.to_track_quat("-Z", "Y").to_euler()


def select_eevee(scene) -> str:
    for engine in ("BLENDER_EEVEE_NEXT", "BLENDER_EEVEE"):
        try:
            scene.render.engine = engine
            return engine
        except TypeError:
            continue
    raise RuntimeError("No supported Eevee render engine found")


def configure_scene(args: argparse.Namespace):
    scene = bpy.context.scene
    engine = select_eevee(scene)
    scene.render.resolution_x = args.size
    scene.render.resolution_y = args.size
    scene.render.resolution_percentage = 100
    scene.render.image_settings.file_format = "PNG"
    scene.render.image_settings.color_mode = "RGBA"
    scene.render.image_settings.color_depth = "8"
    scene.render.film_transparent = True
    if hasattr(scene.render, "use_compositing"):
        scene.render.use_compositing = False
    if hasattr(scene.render, "use_sequencer"):
        scene.render.use_sequencer = False
    try:
        scene.view_settings.look = "AgX - Medium High Contrast"
    except TypeError:
        pass
    return engine


def ensure_preview_material_and_lights(armature):
    meshes = [o for o in bpy.context.scene.objects if o.type == "MESH" and any(m.type == "ARMATURE" and m.object == armature for m in o.modifiers)]
    fallback = bpy.data.materials.get("DZ_ActorPreview") or bpy.data.materials.new("DZ_ActorPreview")
    fallback.diffuse_color = (0.12, 0.16, 0.22, 1.0)
    for obj in meshes:
        if not obj.data.materials:
            obj.data.materials.append(fallback)
    if any(o.type == "LIGHT" for o in bpy.context.scene.objects):
        return
    for name, location, energy, size in (
        ("DZ_Key", (3.0, -4.0, 5.5), 950.0, 4.0),
        ("DZ_Fill", (-4.0, -1.0, 3.2), 500.0, 5.0),
        ("DZ_Rim", (1.0, 4.0, 4.5), 750.0, 3.0),
    ):
        data = bpy.data.lights.new(name, "AREA")
        data.energy = energy
        data.size = size
        obj = bpy.data.objects.new(name, data)
        bpy.context.collection.objects.link(obj)
        obj.location = Vector(location)
        look_at(obj, Vector((0.0, 0.0, 0.95)))


def _parse_vec(name: str, default: tuple[float, float, float]) -> Vector:
    raw = os.environ.get(name, "").strip()
    if not raw:
        return Vector(default)
    parts = [float(v) for v in raw.split(",")]
    if len(parts) != 3:
        raise RuntimeError(f"{name} must contain exactly three comma-separated numbers")
    return Vector(parts)


def _weapon_material(name, color, metallic, roughness, emission=None):
    mat = bpy.data.materials.new(name)
    mat.diffuse_color = (*color, 1.0)
    mat.use_nodes = True
    bsdf = mat.node_tree.nodes.get("Principled BSDF")
    if bsdf:
        bsdf.inputs["Base Color"].default_value = (*color, 1.0)
        bsdf.inputs["Metallic"].default_value = metallic
        bsdf.inputs["Roughness"].default_value = roughness
        if emission:
            slot = bsdf.inputs.get("Emission Color") or bsdf.inputs.get("Emission")
            if slot:
                slot.default_value = (*emission, 1.0)
            strength = bsdf.inputs.get("Emission Strength")
            if strength:
                strength.default_value = 1.2
    return mat


def _weapon_cube(name, loc, scale, material):
    bpy.ops.mesh.primitive_cube_add(size=1, location=loc)
    obj = bpy.context.object
    obj.name = name
    obj.scale = scale
    bpy.ops.object.transform_apply(location=False, rotation=False, scale=True)
    obj.data.materials.append(material)
    return obj


def attach_configured_weapon(armature):
    style = os.environ.get("DZ_WEAPON_STYLE", "").strip()
    if not style:
        return None
    if style != "compact-rifle":
        raise RuntimeError(f"Unsupported configured weapon style: {style}")
    bone_name = os.environ.get("DZ_WEAPON_BONE", "").strip()
    if not bone_name or armature.data.bones.get(bone_name) is None:
        available = ", ".join(b.name for b in armature.data.bones)
        raise RuntimeError(f"Configured weapon bone {bone_name!r} not found. Available: {available}")

    dark = _weapon_material("DZ_Weapon_Gunmetal", (0.018, 0.024, 0.032), .85, .24)
    body = _weapon_material("DZ_Weapon_Body", (0.035, 0.08, 0.12), .68, .28)
    accent = _weapon_material("DZ_Weapon_Accent", (0.02, .42, .58), .32, .20, (0.02, .38, .58))
    parts = [
        _weapon_cube("DZ_Rifle_Body", (.18, 0, .025), (.25, .052, .066), body),
        _weapon_cube("DZ_Rifle_Barrel", (.49, 0, .035), (.18, .024, .024), dark),
        _weapon_cube("DZ_Rifle_Stock", (-.12, 0, .028), (.13, .058, .072), dark),
        _weapon_cube("DZ_Rifle_Grip", (0, 0, -.070), (.035, .035, .080), dark),
        _weapon_cube("DZ_Rifle_Sight", (.20, 0, .095), (.065, .021, .018), accent),
    ]
    bpy.ops.object.select_all(action="DESELECT")
    for obj in parts:
        obj.select_set(True)
    bpy.context.view_layer.objects.active = parts[0]
    bpy.ops.object.join()
    weapon = bpy.context.object
    weapon.name = "DZ_ConfiguredWeapon"
    weapon["dz_role"] = "weapon"
    weapon["dz_style"] = style
    bpy.context.scene.cursor.location = Vector((0, 0, 0))
    bpy.ops.object.origin_set(type="ORIGIN_CURSOR", center="MEDIAN")

    pose_bone = armature.pose.bones[bone_name]
    bpy.context.view_layer.update()
    grip_world = armature.matrix_world @ pose_bone.matrix.translation
    forward = _parse_vec("DZ_WEAPON_FORWARD", (0.0, -1.0, 0.0)).normalized()
    offset = _parse_vec("DZ_WEAPON_GRIP_OFFSET", (0.0, 0.0, 0.0))
    desired = forward.to_track_quat("X", "Z").to_matrix().to_4x4()
    desired.translation = grip_world + offset
    weapon.parent = armature
    weapon.parent_type = "BONE"
    weapon.parent_bone = bone_name
    weapon.matrix_parent_inverse = Matrix.Identity(4)
    weapon.matrix_world = desired
    bpy.context.view_layer.update()
    if (weapon.matrix_world.translation - desired.translation).length > 1e-4:
        raise RuntimeError("Configured weapon grip placement drifted")
    print(f"Configured weapon attached: style={style} bone={bone_name} object={weapon.name}")
    return weapon


def set_action(armature, action_name: str):
    action = bpy.data.actions.get(action_name)
    if action is None:
        matches = [a for a in bpy.data.actions if a.name.lower() == action_name.lower()]
        if not matches:
            available = ", ".join(sorted(a.name for a in bpy.data.actions))
            raise RuntimeError(f"Missing Blender action: {action_name}. Available: {available}")
        action = matches[0]
    if armature.animation_data is None:
        armature.animation_data_create()
    armature.animation_data.action = action
    return action


def sample_frames(action, wanted: int) -> list[int]:
    start, end = action.frame_range
    start_i, end_i = int(round(start)), int(round(end))
    if wanted <= 1:
        return [start_i]
    span = max(1, end_i - start_i + 1)
    return [start_i + min(span - 1, math.floor(i * span / wanted)) for i in range(wanted)]


def main():
    args = parse_args()
    action_map = {"idle": args.idle_action, "run": args.run_action, "attack": args.attack_action, "hit": args.hit_action, "death": args.death_action}
    engine = configure_scene(args)
    armature = find_armature()
    weapon = attach_configured_weapon(armature)
    ensure_preview_material_and_lights(armature)
    cam = ensure_camera(args)
    root = args.output.resolve(); root.mkdir(parents=True, exist_ok=True)
    target = Vector((0.0, 0.0, args.target_height))
    scene = bpy.context.scene
    rendered = 0
    print(f"Action map for {args.actor}: {action_map}")
    for direction, degrees in DIRECTIONS:
        radians = math.radians(degrees)
        cam.location = Vector((args.camera_distance * math.sin(radians), -args.camera_distance * math.cos(radians), args.camera_height))
        look_at(cam, target)
        for animation, wanted in DEFAULT_COUNTS.items():
            action = set_action(armature, action_map[animation])
            frames = sample_frames(action, wanted)
            out_dir = root / animation / direction; out_dir.mkdir(parents=True, exist_ok=True)
            for index, frame in enumerate(frames):
                scene.frame_set(frame)
                scene.render.filepath = str(out_dir / f"{animation}_{index:02d}.png")
                bpy.ops.render.render(write_still=True)
                rendered += 1
    if rendered != 232:
        raise RuntimeError(f"Sprite contract requires 232 frames, rendered {rendered}")
    print(f"Rendered {rendered} {args.actor} frames to {root} using {engine}; configured_weapon={weapon.name if weapon else 'none'}")


if __name__ == "__main__":
    main()
