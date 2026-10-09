#!/usr/bin/env python3
"""Build Deadline: Zero's original industrial hard-surface kit.

Run with Blender 5.2 LTS:
  blender -b --factory-startup -P tools/blender/build_industrial_kit.py -- \
    --output build/industrial-kit

Outputs are project-owned source geometry. No third-party mesh or texture data is used.
"""
from __future__ import annotations
import argparse
import math
import sys
from pathlib import Path
import bpy

def args():
    argv = sys.argv[sys.argv.index("--") + 1:] if "--" in sys.argv else []
    p = argparse.ArgumentParser()
    p.add_argument("--output", type=Path, required=True)
    return p.parse_args(argv)

def material(name, color, metallic, roughness):
    mat = bpy.data.materials.get(name) or bpy.data.materials.new(name)
    mat.diffuse_color = (*color, 1.0)
    mat.metallic = metallic
    mat.roughness = roughness
    # glTF exports its PBR channels from the node graph. Updating viewport
    # diffuse_color alone can silently export bright white materials.
    mat.use_nodes = True
    bsdf = mat.node_tree.nodes.get("Principled BSDF")
    if bsdf is None:
        raise RuntimeError(f"Missing Principled BSDF for {name}")
    bsdf.inputs["Base Color"].default_value = (*color, 1.0)
    bsdf.inputs["Metallic"].default_value = metallic
    bsdf.inputs["Roughness"].default_value = roughness
    return mat

STEEL = None
DARK = None
HAZARD = None
CYAN = None
RUST = None

def apply_bevel(obj, width=0.025, segments=2):
    if width <= 0.0:
        return
    bpy.context.view_layer.objects.active = obj
    obj.select_set(True)
    mod = obj.modifiers.new("DZ_EdgeBevel", "BEVEL")
    mod.width = width
    mod.segments = segments
    mod.limit_method = "ANGLE"
    bpy.ops.object.modifier_apply(modifier=mod.name)
    obj.select_set(False)

def box(name, size, location, mat, bevel=0.025):
    bpy.ops.mesh.primitive_cube_add(location=location)
    obj = bpy.context.active_object
    obj.name = name
    obj.scale = (size[0] * 0.5, size[1] * 0.5, size[2] * 0.5)
    bpy.ops.object.transform_apply(location=False, rotation=False, scale=True)
    apply_bevel(obj, bevel)
    obj.data.materials.append(mat)
    return obj

def cylinder(name, radius, depth, location, rotation, mat, vertices=16):
    bpy.ops.mesh.primitive_cylinder_add(
        vertices=vertices, radius=radius, depth=depth,
        location=location, rotation=rotation
    )
    obj = bpy.context.active_object
    obj.name = name
    obj.data.materials.append(mat)
    return obj

def ring_bolts(prefix, radius, z, count, mat, bolt_radius=0.025, bolt_depth=0.018):
    result = []
    for i in range(count):
        angle = math.tau * i / count
        x = math.cos(angle) * radius
        y = math.sin(angle) * radius
        result.append(cylinder(
            f"{prefix}_{i:02d}", bolt_radius, bolt_depth,
            (x, y, z), (0.0, 0.0, 0.0), mat, vertices=8
        ))
    return result

def clear_scene():
    bpy.ops.object.select_all(action="SELECT")
    bpy.ops.object.delete(use_global=False)

def export_asset(path: Path, objects, role: str):
    bpy.ops.object.select_all(action="DESELECT")
    for obj in objects:
        obj.select_set(True)
        obj["dz_asset_role"] = role
        obj["dz_project_owned"] = True
    bpy.context.view_layer.objects.active = objects[0]
    path.parent.mkdir(parents=True, exist_ok=True)
    bpy.ops.export_scene.gltf(
        filepath=str(path.resolve()),
        export_format="GLB",
        use_selection=True,
        export_apply=True,
        export_animations=False,
        export_extras=True,
    )
    print(f"DZ_EXPORTED {path.name} objects={len(objects)} role={role}")
    clear_scene()

def build_cargo_crate(out: Path):
    objs = []
    objs.append(box("CrateBody", (1.36, 0.96, 0.76), (0, 0, 0.44), STEEL, 0.055))
    # Recessed front/back plates and corner armor.
    for side in (-1.0, 1.0):
        y = side * 0.49
        objs.append(box("CratePanelFront" if side < 0 else "CratePanelRear",
                        (0.94, 0.035, 0.48), (0, y, 0.44), STEEL, 0.018))
    for x in (-0.63, 0.63):
        for y in (-0.43, 0.43):
            objs.append(box(f"CornerRail_{x}_{y}", (0.095, 0.095, 0.84), (x, y, 0.44), STEEL, 0.018))
    for z in (0.08, 0.80):
        objs.append(box(f"FrameTop_{z}", (1.30, 0.10, 0.085), (0, -0.49, z), STEEL, 0.015))
        objs.append(box(f"FrameRear_{z}", (1.30, 0.10, 0.085), (0, 0.49, z), STEEL, 0.015))
    # Hazard identity is geometry, not a baked texture, so it remains sharp after atlas downsizing.
    for i, x in enumerate((-0.30, 0.0, 0.30)):
        objs.append(box(f"HazardTab_{i}", (0.19, 0.025, 0.095), (x, -0.515, 0.44), HAZARD, 0.008))
    export_asset(out / "dz_cargo_crate.glb", objs, "environment_prop")

def build_service_pillar(out: Path):
    objs = [
        box("PillarBase", (0.72, 0.72, 0.18), (0, 0, 0.09), STEEL, 0.045),
        box("PillarBody", (0.46, 0.46, 2.08), (0, 0, 1.18), STEEL, 0.055),
        box("PillarCap", (0.62, 0.62, 0.20), (0, 0, 2.29), STEEL, 0.040),
        box("ServicePanel", (0.30, 0.028, 0.64), (0, -0.245, 1.16), STEEL, 0.012),
        box("SignalStrip", (0.21, 0.030, 0.68), (0, -0.264, 1.55), CYAN, 0.010),
    ]
    # Four mechanical feet and cap bolts.
    for x in (-0.25, 0.25):
        for y in (-0.25, 0.25):
            objs.append(box(f"Foot_{x}_{y}", (0.13, 0.13, 0.16), (x, y, 0.16), STEEL, 0.015))
    objs += ring_bolts("CapBolt", 0.22, 2.40, 8, CYAN, 0.022, 0.025)
    export_asset(out / "dz_service_pillar.glb", objs, "environment_prop")

def build_pipe_rack(out: Path):
    objs = []
    for x in (-1.10, 1.10):
        objs.append(box(f"RackPost_{x}", (0.14, 0.44, 1.95), (x, 0, 1.0), STEEL, 0.025))
        objs.append(box(f"RackFoot_{x}", (0.42, 0.62, 0.12), (x, 0, 0.06), STEEL, 0.025))
    for z in (0.42, 0.92, 1.42):
        objs.append(cylinder(f"Pipe_{z}", 0.13, 2.02, (0, 0, z), (0, math.pi / 2, 0), RUST, vertices=16))
        for x in (-0.86, 0.86):
            objs.append(box(f"Clamp_{z}_{x}", (0.12, 0.40, 0.32), (x, 0, z), STEEL, 0.018))
    objs.append(box("RackTop", (2.34, 0.18, 0.16), (0, 0, 1.95), STEEL, 0.025))
    export_asset(out / "dz_pipe_rack.glb", objs, "environment_prop")

def build_bulkhead(out: Path):
    objs = [
        box("BulkheadShell", (3.00, 0.20, 2.42), (0, 0, 1.21), STEEL, 0.035),
        box("BulkheadInset", (2.42, 0.055, 1.70), (0, -0.125, 1.20), STEEL, 0.020),
    ]
    for x in (-1.42, 1.42):
        objs.append(box(f"VerticalFrame_{x}", (0.16, 0.32, 2.46), (x, 0, 1.22), STEEL, 0.025))
    for z in (0.13, 2.29):
        objs.append(box(f"HorizontalFrame_{z}", (2.92, 0.31, 0.15), (0, 0, z), STEEL, 0.025))
    for x in (-0.82, 0.0, 0.82):
        objs.append(box(f"PanelRib_{x}", (0.075, 0.035, 1.54), (x, -0.162, 1.20), STEEL, 0.012))
    for i, x in enumerate((-0.78, -0.26, 0.26, 0.78)):
        objs.append(box(f"HazardMarker_{i}", (0.28, 0.030, 0.075), (x, -0.172, 0.43), HAZARD, 0.008))
    export_asset(out / "dz_bulkhead_panel.glb", objs, "environment_prop")

def build_grate(out: Path):
    objs = [
        box("GrateRecess", (2.48, 1.26, 0.055), (0, 0, 0.028), DARK, 0.010),
    ]
    for y in (-0.60, 0.60):
        objs.append(box(f"SideRail_{y}", (2.56, 0.10, 0.095), (0, y, 0.075), STEEL, 0.015))
    for x in (-1.23, 1.23):
        objs.append(box(f"EndRail_{x}", (0.10, 1.18, 0.095), (x, 0, 0.075), STEEL, 0.015))
    for i in range(12):
        x = -1.02 + i * (2.04 / 11.0)
        objs.append(box(f"GrateSlat_{i:02d}", (0.075, 1.05, 0.075), (x, 0, 0.095), STEEL, 0.010))
    for i, y in enumerate((-0.34, 0.34)):
        objs.append(box(f"CrossBrace_{i}", (2.18, 0.055, 0.070), (0, y, 0.092), STEEL, 0.008))
    export_asset(out / "dz_floor_grate.glb", objs, "environment_prop")


def build_utility_cabinet(out: Path):
    objs = [
        box("CabinetBody", (0.86, 0.42, 1.72), (0, 0, 0.86), STEEL, 0.045),
        box("CabinetDoor", (0.68, 0.035, 1.34), (0, -0.228, 0.88), STEEL, 0.018),
        box("CabinetTop", (0.92, 0.48, 0.09), (0, 0, 1.76), STEEL, 0.022),
        box("StatusBar", (0.48, 0.028, 0.055), (0, -0.252, 1.48), CYAN, 0.008),
    ]
    for z in (0.42, 0.84, 1.20):
        objs.append(box(f"DoorRib_{z}", (0.56, 0.022, 0.045), (0, -0.257, z), STEEL, 0.006))
    export_asset(out / "dz_utility_cabinet.glb", objs, "environment_prop")

def build_wall_vent(out: Path):
    objs = [
        box("VentFrame", (1.28, 0.12, 0.88), (0, 0, 0.44), STEEL, 0.032),
        box("VentRecess", (1.04, 0.045, 0.66), (0, -0.082, 0.44), DARK, 0.015),
    ]
    for i in range(7):
        z = 0.18 + i * 0.085
        objs.append(box(f"VentSlat_{i:02d}", (0.88, 0.035, 0.038), (0, -0.112, z), STEEL, 0.006))
    export_asset(out / "dz_wall_vent.glb", objs, "environment_prop")

def build_floor_hatch(out: Path):
    objs = [
        box("HatchFrame", (1.62, 1.62, 0.08), (0, 0, 0.04), STEEL, 0.025),
        box("HatchPanel", (1.34, 1.34, 0.055), (0, 0, 0.095), STEEL, 0.020),
    ]
    for side in (-1.0, 1.0):
        objs.append(box(f"HatchHazard_{side}", (0.16, 1.05, 0.035), (side * 0.53, 0, 0.132), HAZARD, 0.006))
    for x in (-0.54, 0.54):
        for y in (-0.54, 0.54):
            objs.append(cylinder(f"HatchBolt_{x}_{y}", 0.032, 0.026, (x, y, 0.145), (0,0,0), STEEL, vertices=8))
    export_asset(out / "dz_floor_hatch.glb", objs, "environment_prop")

def build_hazard_bollard(out: Path):
    objs = [
        cylinder("BollardBody", 0.17, 1.08, (0,0,0.60), (0,0,0), STEEL, vertices=16),
        cylinder("BollardBase", 0.31, 0.12, (0,0,0.06), (0,0,0), STEEL, vertices=16),
        cylinder("BollardCap", 0.20, 0.10, (0,0,1.17), (0,0,0), HAZARD, vertices=16),
        box("BollardBandA", (0.34, 0.035, 0.10), (0,-0.175,0.48), HAZARD, 0.006),
        box("BollardBandB", (0.34, 0.035, 0.10), (0,-0.175,0.76), HAZARD, 0.006),
    ]
    export_asset(out / "dz_hazard_bollard.glb", objs, "environment_prop")

def build_junction_box(out: Path):
    objs = [
        box("JunctionShell", (0.72, 0.32, 0.92), (0,0,0.46), STEEL, 0.035),
        box("JunctionFace", (0.56, 0.035, 0.70), (0,-0.178,0.47), STEEL, 0.015),
        box("JunctionStatus", (0.32, 0.025, 0.055), (0,-0.201,0.72), CYAN, 0.006),
    ]
    for x in (-0.25, 0.25):
        objs.append(cylinder(f"CablePort_{x}", 0.065, 0.12, (x,0,0.08), (math.pi/2,0,0), STEEL, vertices=12))
    export_asset(out / "dz_junction_box.glb", objs, "environment_prop")

def build_light_bar(out: Path):
    objs = [
        box("LightBarHousing", (1.42, 0.22, 0.18), (0,0,0.09), STEEL, 0.028),
        box("LightBarLens", (1.08, 0.045, 0.075), (0,-0.132,0.09), CYAN, 0.012),
    ]
    for x in (-0.62, 0.62):
        objs.append(box(f"LightBarMount_{x}", (0.12, 0.30, 0.24), (x,0,0.02), STEEL, 0.016))
    export_asset(out / "dz_emergency_light_bar.glb", objs, "environment_prop")

def main():
    global STEEL, DARK, HAZARD, CYAN, RUST
    a = args()
    bpy.ops.wm.read_factory_settings(use_empty=True)
    STEEL = material("DZ_DarkSteel", (0.035, 0.055, 0.065), 0.72, 0.38)
    DARK = material("DZ_Recess", (0.008, 0.014, 0.018), 0.18, 0.84)
    HAZARD = material("DZ_HazardOrange", (0.64, 0.105, 0.012), 0.34, 0.48)
    CYAN = material("DZ_SystemCyan", (0.015, 0.32, 0.42), 0.28, 0.40)
    RUST = material("DZ_PipeRust", (0.22, 0.070, 0.025), 0.62, 0.50)
    a.output.mkdir(parents=True, exist_ok=True)
    build_cargo_crate(a.output)
    build_service_pillar(a.output)
    build_pipe_rack(a.output)
    build_bulkhead(a.output)
    build_grate(a.output)
    build_utility_cabinet(a.output)
    build_wall_vent(a.output)
    build_floor_hatch(a.output)
    build_hazard_bollard(a.output)
    build_junction_box(a.output)
    build_light_bar(a.output)
    print("DZ_INDUSTRIAL_KIT_OK output=", a.output)

if __name__ == "__main__":
    main()
