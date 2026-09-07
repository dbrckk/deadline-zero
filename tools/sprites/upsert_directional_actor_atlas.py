#!/usr/bin/env python3
"""Build or replace one directional actor page in the production LibGDX atlas.

The key property is *upsert*, not overwrite: existing atlas pages for other actors
are preserved. This makes validated actor publication composable as the roster
moves from Rex to Shambler and beyond.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path

from PIL import Image

DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
ANIMATIONS = (("idle", 4), ("run", 8), ("attack", 6), ("hit", 3), ("death", 8))
CELL = 96
EXPECTED_FRAMES = 232


def parse_args() -> argparse.Namespace:
    p = argparse.ArgumentParser()
    p.add_argument("--actor", required=True)
    p.add_argument("--root", required=True, help="Atlas root, e.g. enemy/shambler")
    p.add_argument("--input", type=Path, required=True, help="normalized96 directory")
    p.add_argument("--atlas", type=Path, required=True)
    p.add_argument("--png", type=Path, required=True)
    p.add_argument("--manifest", type=Path, required=True)
    p.add_argument("--source-manifest", type=Path)
    p.add_argument("--source-run-id", type=int)
    return p.parse_args()


def atlas_page_starts(lines: list[str]) -> list[int]:
    starts = []
    for i in range(len(lines) - 1):
        line = lines[i]
        if line and not line[0].isspace() and lines[i + 1].startswith("size:"):
            starts.append(i)
    return starts


def split_pages(text: str) -> list[list[str]]:
    if not text.strip():
        return []
    lines = text.splitlines()
    starts = atlas_page_starts(lines)
    if not starts:
        raise ValueError("Existing atlas has no recognizable page header")
    if starts[0] != 0:
        prefix = [line for line in lines[: starts[0]] if line.strip()]
        if prefix:
            raise ValueError(f"Unexpected content before first atlas page: {prefix[:2]}")
    pages = []
    for pos, start in enumerate(starts):
        end = starts[pos + 1] if pos + 1 < len(starts) else len(lines)
        page = lines[start:end]
        while page and not page[-1].strip():
            page.pop()
        pages.append(page)
    return pages


def build_sheet(sprite_root: Path, atlas_root: str) -> tuple[Image.Image, list[tuple[str, int, int, int]]]:
    columns = sum(count for _, count in ANIMATIONS)
    sheet = Image.new("RGBA", (columns * CELL, len(DIRECTIONS) * CELL), (0, 0, 0, 0))
    entries: list[tuple[str, int, int, int]] = []

    for row, direction in enumerate(DIRECTIONS):
        column = 0
        for animation, count in ANIMATIONS:
            for frame in range(count):
                path = sprite_root / animation / direction / f"{animation}_{frame:02d}.png"
                if not path.is_file():
                    raise FileNotFoundError(f"Missing normalized frame: {path}")
                with Image.open(path) as source:
                    image = source.convert("RGBA")
                    if image.size != (CELL, CELL):
                        raise ValueError(f"Invalid normalized frame size {image.size}: {path}")
                    x = column * CELL
                    y = row * CELL
                    sheet.alpha_composite(image, (x, y))
                entries.append((f"{atlas_root}/{direction}/{animation}", frame, x, y))
                column += 1

    if len(entries) != EXPECTED_FRAMES:
        raise ValueError(f"Expected {EXPECTED_FRAMES} atlas entries, got {len(entries)}")
    return sheet, entries


def page_lines(png_name: str, sheet: Image.Image, entries: list[tuple[str, int, int, int]]) -> list[str]:
    lines = [
        png_name,
        f"size: {sheet.width}, {sheet.height}",
        "format: RGBA8888",
        "filter: Linear,Linear",
        "repeat: none",
    ]
    for name, index, x, y in entries:
        lines.extend(
            [
                name,
                "  rotate: false",
                f"  xy: {x}, {y}",
                f"  size: {CELL}, {CELL}",
                f"  orig: {CELL}, {CELL}",
                "  offset: 0, 0",
                f"  index: {index}",
            ]
        )
    return lines


def main() -> None:
    args = parse_args()
    args.png.parent.mkdir(parents=True, exist_ok=True)
    args.atlas.parent.mkdir(parents=True, exist_ok=True)
    args.manifest.parent.mkdir(parents=True, exist_ok=True)

    sheet, entries = build_sheet(args.input, args.root.rstrip("/"))
    sheet.save(args.png, optimize=True)

    existing = split_pages(args.atlas.read_text()) if args.atlas.is_file() else []
    png_name = args.png.name
    kept = [page for page in existing if page and page[0].strip() != png_name]
    replaced = len(kept) != len(existing)
    kept.append(page_lines(png_name, sheet, entries))
    args.atlas.write_text("\n\n".join("\n".join(page) for page in kept) + "\n")

    source = {}
    if args.source_manifest and args.source_manifest.is_file():
        source = json.loads(args.source_manifest.read_text())

    metadata = {
        "actor": args.actor,
        "root": args.root.rstrip("/"),
        "published_from_run": args.source_run_id,
        "frame_count": len(entries),
        "cell": [CELL, CELL],
        "sheet": [sheet.width, sheet.height],
        "layout": "8 directions x 29 frames",
        "directions": list(DIRECTIONS),
        "animations": dict(ANIMATIONS),
        "png": png_name,
        "png_sha256": hashlib.sha256(args.png.read_bytes()).hexdigest(),
        "atlas_page_replaced": replaced,
        "atlas_page_count": len(kept),
        "source_stage": source.get("stage"),
        "source_production_ready": source.get("production_ready", False),
        "source_horizontal_anchor": source.get("horizontal_anchor"),
    }
    args.manifest.write_text(json.dumps(metadata, indent=2) + "\n")
    print(json.dumps(metadata, indent=2))


if __name__ == "__main__":
    main()
