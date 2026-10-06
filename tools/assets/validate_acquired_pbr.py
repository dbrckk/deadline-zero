#!/usr/bin/env python3
"""Validate a staged Poly Haven PBR source pack."""
from __future__ import annotations
import argparse, json, struct, sys
from pathlib import Path

REQUIRED_MAPS = {"Diffuse", "nor_gl"}
MAX_DIMENSION = 2048

def jpeg_size(path: Path):
    data = path.read_bytes()
    if not data.startswith(b"\xff\xd8"):
        raise ValueError("not JPEG")
    i = 2
    while i + 9 < len(data):
        if data[i] != 0xFF:
            i += 1
            continue
        marker = data[i + 1]
        i += 2
        if marker in {0xD8, 0xD9}:
            continue
        if i + 2 > len(data):
            break
        length = struct.unpack(">H", data[i:i+2])[0]
        if marker in {0xC0,0xC1,0xC2,0xC3,0xC5,0xC6,0xC7,0xC9,0xCA,0xCB,0xCD,0xCE,0xCF}:
            if i + 7 > len(data):
                break
            height, width = struct.unpack(">HH", data[i+3:i+7])
            return width, height
        i += max(2, length)
    raise ValueError("JPEG size marker missing")

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("root", type=Path)
    args = ap.parse_args()
    errors = []
    assets = 0
    for source in sorted(args.root.glob("*/SOURCE.json")):
        assets += 1
        meta = json.loads(source.read_text())
        asset_id = meta.get("asset_id", source.parent.name)
        if meta.get("license") != "CC0-1.0":
            errors.append(f"{asset_id}: expected CC0-1.0")
        maps = {entry.get("map") for entry in meta.get("files", [])}
        if not REQUIRED_MAPS.issubset(maps):
            errors.append(f"{asset_id}: missing Diffuse/OpenGL normal maps")
        for entry in meta.get("files", []):
            path = source.parent / entry["file"]
            if not path.exists() or path.stat().st_size < 1024:
                errors.append(f"{asset_id}: missing/empty {entry['file']}")
                continue
            if path.suffix.lower() in {".jpg", ".jpeg"}:
                try:
                    width, height = jpeg_size(path)
                except Exception as exc:
                    errors.append(f"{asset_id}: {entry['file']} invalid JPEG ({exc})")
                    continue
                if width > MAX_DIMENSION or height > MAX_DIMENSION:
                    errors.append(f"{asset_id}: {entry['file']} {width}x{height} exceeds 2K source budget")
                print(f"DZ_PBR_FILE_OK {asset_id} {entry['map']} {width}x{height} bytes={path.stat().st_size}")
            else:
                errors.append(f"{asset_id}: unexpected staged format {path.suffix}")
    if assets < 4:
        errors.append(f"expected at least 4 staged PBR assets, got {assets}")
    if errors:
        print("DZ_PBR_PACK_INVALID")
        for error in errors:
            print(" -", error)
        sys.exit(1)
    print(f"DZ_PBR_PACK_OK assets={assets}")

if __name__ == "__main__":
    main()
