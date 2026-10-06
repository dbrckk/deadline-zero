#!/usr/bin/env python3
"""Validate generated Deadline: Zero GLB review assets without third-party packages."""
from __future__ import annotations
import argparse, json, struct, sys
from pathlib import Path

EXPECTED = {
    "dz_cargo_crate.glb": 12000,
    "dz_service_pillar.glb": 12000,
    "dz_pipe_rack.glb": 12000,
    "dz_bulkhead_panel.glb": 12000,
    "dz_floor_grate.glb": 12000,
    "dz_utility_cabinet.glb": 12000,
    "dz_wall_vent.glb": 12000,
    "dz_floor_hatch.glb": 12000,
    "dz_hazard_bollard.glb": 12000,
    "dz_junction_box.glb": 12000,
    "dz_emergency_light_bar.glb": 12000,
}
MAX_BYTES = 2 * 1024 * 1024

def read_glb(path: Path):
    data = path.read_bytes()
    if len(data) < 20:
        raise ValueError("too small")
    magic, version, total = struct.unpack_from("<4sII", data, 0)
    if magic != b"glTF" or version != 2 or total != len(data):
        raise ValueError("invalid GLB header")
    json_len, json_type = struct.unpack_from("<II", data, 12)
    if json_type != 0x4E4F534A:
        raise ValueError("first GLB chunk is not JSON")
    payload = data[20:20 + json_len].decode("utf-8").rstrip(" \t\r\n\0")
    return json.loads(payload), len(data)

def triangle_count(doc):
    accessors = doc.get("accessors", [])
    total = 0
    for mesh in doc.get("meshes", []):
        for prim in mesh.get("primitives", []):
            if prim.get("mode", 4) != 4:
                continue
            if "indices" in prim:
                total += int(accessors[prim["indices"]]["count"]) // 3
            else:
                pos = prim.get("attributes", {}).get("POSITION")
                if pos is not None:
                    total += int(accessors[pos]["count"]) // 3
    return total

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("directory", type=Path)
    args = ap.parse_args()
    errors = []
    for name, budget in EXPECTED.items():
        path = args.directory / name
        if not path.exists():
            errors.append(f"{name}: missing")
            continue
        try:
            doc, size = read_glb(path)
        except Exception as exc:
            errors.append(f"{name}: {exc}")
            continue
        tris = triangle_count(doc)
        material_count = len(doc.get("materials", []))
        mesh_count = len(doc.get("meshes", []))
        image_count = len(doc.get("images", []))
        if tris < 100:
            errors.append(f"{name}: suspiciously empty ({tris} triangles)")
        if tris > budget:
            errors.append(f"{name}: {tris} triangles > {budget}")
        if material_count > 2:
            errors.append(f"{name}: {material_count} materials > 2")
        if image_count != 0:
            errors.append(f"{name}: generator must not embed textures ({image_count} images)")
        if size > MAX_BYTES:
            errors.append(f"{name}: {size} bytes > {MAX_BYTES}")
        print(f"DZ_GLB_OK {name} triangles={tris} meshes={mesh_count} materials={material_count} bytes={size}")
    if errors:
        print("DZ_GLB_INVALID")
        for error in errors:
            print(" -", error)
        sys.exit(1)
    print(f"DZ_INDUSTRIAL_KIT_VALID assets={len(EXPECTED)}")

if __name__ == "__main__":
    main()
