#!/usr/bin/env python3
"""Assemble normalized directional actor frames into the canonical source-sheet layout.

Input layout:
  <input>/<motion>/<direction>/<motion>_<index>.png

Output layout:
  rows: n, ne, e, se, s, sw, w, nw
  columns: idle, run, attack, hit, death

The resulting sheet is deterministic and is intended to round-trip through
tools/slice_sprite_sheet.py without changing any pixel.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path

from PIL import Image

DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
MOTIONS = ("idle", "run", "attack", "hit", "death")
STANDARD_COUNTS = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}


def parse_args() -> argparse.Namespace:
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--cell", type=int, default=96)
    p.add_argument("--run", type=int, default=8)
    p.add_argument("--manifest", type=Path)
    return p.parse_args()


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def main() -> int:
    args = parse_args()
    if args.cell <= 0 or args.run <= 0:
        raise SystemExit("--cell and --run must be positive")

    counts = dict(STANDARD_COUNTS)
    counts["run"] = args.run
    columns = sum(counts[m] for m in MOTIONS)
    width = columns * args.cell
    height = len(DIRECTIONS) * args.cell
    sheet = Image.new("RGBA", (width, height), (0, 0, 0, 0))

    inputs: list[dict] = []
    for row, direction in enumerate(DIRECTIONS):
        column = 0
        for motion in MOTIONS:
            for index in range(counts[motion]):
                source = args.input / motion / direction / f"{motion}_{index:02d}.png"
                if not source.is_file():
                    raise SystemExit(f"missing frame: {source}")
                with Image.open(source) as image:
                    if image.format != "PNG":
                        raise SystemExit(f"frame is not PNG: {source}")
                    rgba = image.convert("RGBA")
                    if rgba.size != (args.cell, args.cell):
                        raise SystemExit(
                            f"frame has wrong size: {source} is {rgba.size}, "
                            f"expected {(args.cell, args.cell)}"
                        )
                    if rgba.getchannel("A").getbbox() is None:
                        raise SystemExit(f"frame is empty: {source}")
                    sheet.alpha_composite(rgba, (column * args.cell, row * args.cell))
                inputs.append({
                    "path": str(source.relative_to(args.input)).replace("\\", "/"),
                    "direction": direction,
                    "motion": motion,
                    "index": index,
                })
                column += 1

    args.output.parent.mkdir(parents=True, exist_ok=True)
    sheet.save(args.output, "PNG", optimize=True)

    if args.manifest:
        manifest = {
            "schema": 1,
            "directions": list(DIRECTIONS),
            "motionOrder": list(MOTIONS),
            "frameCounts": counts,
            "cell": [args.cell, args.cell],
            "sheet": {
                "path": str(args.output),
                "width": width,
                "height": height,
                "frameCount": len(inputs),
                "sha256": sha256(args.output),
            },
            "inputs": inputs,
        }
        args.manifest.parent.mkdir(parents=True, exist_ok=True)
        args.manifest.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")

    print(f"assembled {len(inputs)} frames -> {args.output} ({width}x{height})")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
