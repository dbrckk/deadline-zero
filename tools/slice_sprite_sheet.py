#!/usr/bin/env python3
"""Cut a deterministic Deadline Zero actor sprite sheet into atlas-ready PNG frames.

Requires Pillow (`python -m pip install pillow`). The sheet layout is intentionally simple:
- rows: 8 directions in N, NE, E, SE, S, SW, W, NW order
- columns: motion groups in idle, run, attack, hit, death order
- every source cell has the same dimensions

Example:
  python tools/slice_sprite_sheet.py \
      --sheet art_sources/rex.png \
      --root survivor/rex \
      --cell 96 \
      --output build/art_frames

The default frame counts match FinalArtContract for a standard actor: 4,8,6,3,8.
Use --boss for boss minimums: 6,8,8,4,10.
"""

from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Dict, Iterable, Tuple

from PIL import Image

DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
STANDARD = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
BOSS = {"idle": 6, "run": 8, "attack": 8, "hit": 4, "death": 10}


def parse_cell(value: str) -> Tuple[int, int]:
    token = value.lower().replace("x", ",")
    parts = [part.strip() for part in token.split(",") if part.strip()]
    if len(parts) == 1:
        size = int(parts[0])
        return size, size
    if len(parts) == 2:
        return int(parts[0]), int(parts[1])
    raise argparse.ArgumentTypeError("--cell must be N or WIDTHxHEIGHT")


def frame_counts(args: argparse.Namespace) -> Dict[str, int]:
    base = dict(BOSS if args.boss else STANDARD)
    for motion in base:
        override = getattr(args, motion)
        if override is not None:
            if override <= 0:
                raise ValueError(f"{motion} frame count must be > 0")
            base[motion] = override
    return base


def occupied_bbox(frame: Image.Image):
    alpha = frame.getchannel("A")
    return alpha.getbbox()


def validate_padding(frame: Image.Image, padding: int) -> bool:
    bbox = occupied_bbox(frame)
    if bbox is None:
        return False
    left, top, right, bottom = bbox
    width, height = frame.size
    return left >= padding and top >= padding and right <= width - padding and bottom <= height - padding


def export(sheet: Image.Image, root: str, output: Path, cell: Tuple[int, int], counts: Dict[str, int], padding: int):
    cell_w, cell_h = cell
    total_columns = sum(counts.values())
    required_w = total_columns * cell_w
    required_h = len(DIRECTIONS) * cell_h
    if sheet.width < required_w or sheet.height < required_h:
        raise ValueError(
            f"sheet is {sheet.width}x{sheet.height}, requires at least {required_w}x{required_h} "
            f"for {total_columns} columns x {len(DIRECTIONS)} rows"
        )

    root_path = Path(*root.split("/"))
    metadata = {
        "root": root,
        "cell": [cell_w, cell_h],
        "directions": list(DIRECTIONS),
        "motions": counts,
        "frames": [],
        "warnings": [],
    }

    for row, direction in enumerate(DIRECTIONS):
        column = 0
        for motion, count in counts.items():
            for frame_index in range(count):
                x = column * cell_w
                y = row * cell_h
                frame = sheet.crop((x, y, x + cell_w, y + cell_h))
                if occupied_bbox(frame) is None:
                    metadata["warnings"].append(f"empty: {root}/{direction}/{motion}[{frame_index}]")
                elif not validate_padding(frame, padding):
                    metadata["warnings"].append(
                        f"alpha padding < {padding}px: {root}/{direction}/{motion}[{frame_index}]"
                    )

                target_dir = output / root_path / direction
                target_dir.mkdir(parents=True, exist_ok=True)
                target = target_dir / f"{motion}_{frame_index:02d}.png"
                frame.save(target, "PNG", optimize=True)
                metadata["frames"].append({
                    "atlasKey": f"{root}/{direction}/{motion}",
                    "index": frame_index,
                    "file": str(target.relative_to(output)).replace("\\", "/"),
                    "sourceCell": [column, row],
                })
                column += 1

    return metadata


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--sheet", required=True, type=Path)
    parser.add_argument("--root", required=True, help="Atlas root, e.g. survivor/rex or boss/alpha")
    parser.add_argument("--cell", required=True, type=parse_cell)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--boss", action="store_true")
    parser.add_argument("--padding", type=int, default=2)
    for motion in STANDARD:
        parser.add_argument(f"--{motion}", type=int, default=None)
    args = parser.parse_args()

    if args.padding < 0:
        parser.error("--padding must be >= 0")
    if not args.sheet.is_file():
        parser.error(f"sheet not found: {args.sheet}")

    counts = frame_counts(args)
    with Image.open(args.sheet) as source:
        sheet = source.convert("RGBA")
        metadata = export(sheet, args.root.strip("/"), args.output, args.cell, counts, args.padding)

    args.output.mkdir(parents=True, exist_ok=True)
    manifest_name = args.root.strip("/").replace("/", "__") + ".frames.json"
    manifest_path = args.output / manifest_name
    manifest_path.write_text(json.dumps(metadata, indent=2) + "\n", encoding="utf-8")

    warnings = metadata["warnings"]
    print(f"exported {len(metadata['frames'])} frames -> {args.output}")
    print(f"manifest: {manifest_path}")
    if warnings:
        print(f"QA warnings: {len(warnings)}")
        for warning in warnings[:30]:
            print(f"  - {warning}")
        if len(warnings) > 30:
            print(f"  ... {len(warnings) - 30} more")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
