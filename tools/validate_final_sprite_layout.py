#!/usr/bin/env python3
"""Validate the final production sprite-sheet contract without external dependencies.

The validator always checks layout integrity and TexturePacker parity. If actor PNG
files are present in art_sources/, it also validates their PNG dimensions directly
from the IHDR chunk.
"""

from __future__ import annotations

import json
import struct
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT_PATH = ROOT / "art_sources" / "final-sprite-layout.json"
PACKER_PATH = ROOT / "tools" / "texturepacker-final.json"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"
EXPECTED_DIRECTIONS = ["n", "ne", "e", "se", "s", "sw", "w", "nw"]
EXPECTED_MOTIONS = ["idle", "run", "attack", "hit", "death"]


def fail(message: str) -> None:
    raise ValueError(message)


def positive_int(value, label: str) -> int:
    if isinstance(value, bool) or not isinstance(value, int) or value <= 0:
        fail(f"{label} must be a positive integer")
    return value


def validate_frame_map(value, label: str) -> dict[str, int]:
    if not isinstance(value, dict) or list(value.keys()) != EXPECTED_MOTIONS:
        fail(f"{label} must define motions in exact order {EXPECTED_MOTIONS}")
    return {motion: positive_int(value[motion], f"{label}.{motion}") for motion in EXPECTED_MOTIONS}


def png_dimensions(path: Path) -> tuple[int, int]:
    with path.open("rb") as handle:
        header = handle.read(24)
    if len(header) < 24 or header[:8] != PNG_SIGNATURE or header[12:16] != b"IHDR":
        fail(f"{path.relative_to(ROOT)} is not a valid PNG with an IHDR header")
    return struct.unpack(">II", header[16:24])


def validate_actor(actor: dict, standard: dict[str, int], boss: dict[str, int]) -> tuple[str, str, int, int, int]:
    if not isinstance(actor, dict):
        fail("every actor entry must be an object")
    actor_id = actor.get("id")
    root = actor.get("root")
    profile = actor.get("profile")
    cell = actor.get("cell")
    priority = actor.get("priority")

    if not isinstance(actor_id, str) or not actor_id or actor_id != actor_id.lower():
        fail(f"invalid actor id: {actor_id!r}")
    if any(ch not in "abcdefghijklmnopqrstuvwxyz0123456789_" for ch in actor_id):
        fail(f"actor id must be lowercase snake_case: {actor_id}")
    if not isinstance(root, str) or not root or root.startswith("/") or root.endswith("/") or "//" in root:
        fail(f"invalid atlas root for {actor_id}: {root!r}")
    if profile not in ("standard", "boss"):
        fail(f"{actor_id}.profile must be standard or boss")
    if not isinstance(cell, list) or len(cell) != 2:
        fail(f"{actor_id}.cell must contain width and height")
    cell_w = positive_int(cell[0], f"{actor_id}.cell[0]")
    cell_h = positive_int(cell[1], f"{actor_id}.cell[1]")
    if cell_w != cell_h:
        fail(f"{actor_id} must use square logical cells")
    expected_cell = 128 if profile == "boss" else 96
    if cell_w != expected_cell:
        fail(f"{actor_id} {profile} cell must be {expected_cell}x{expected_cell}, got {cell_w}x{cell_h}")
    priority = positive_int(priority, f"{actor_id}.priority")
    if priority > 5:
        fail(f"{actor_id}.priority must be in 1..5")

    counts = dict(boss if profile == "boss" else standard)
    if "runFrames" in actor:
        if profile == "boss":
            fail(f"{actor_id}: boss runFrames overrides are not part of the production contract")
        counts["run"] = positive_int(actor["runFrames"], f"{actor_id}.runFrames")
        if counts["run"] < standard["run"]:
            fail(f"{actor_id}.runFrames cannot be below the standard minimum")

    columns = sum(counts.values())
    return actor_id, root, columns * cell_w, len(EXPECTED_DIRECTIONS) * cell_h, priority


def validate_packing(layout_packing: dict) -> None:
    if not isinstance(layout_packing, dict):
        fail("packing must be an object")
    padding = positive_int(layout_packing.get("padding"), "packing.padding")
    if padding < 2:
        fail("packing.padding must be at least 2")
    if layout_packing.get("edgePadding") is not True or layout_packing.get("duplicatePadding") is not True:
        fail("edgePadding and duplicatePadding must remain enabled")
    if layout_packing.get("stripWhitespace") is not False or layout_packing.get("rotation") is not False:
        fail("stripWhitespace and rotation must remain disabled")
    max_page = layout_packing.get("maxPage")
    if max_page != [4096, 4096]:
        fail("packing.maxPage must remain [4096, 4096]")

    if not PACKER_PATH.is_file():
        fail(f"missing {PACKER_PATH.relative_to(ROOT)}")
    packer = json.loads(PACKER_PATH.read_text(encoding="utf-8"))
    parity = {
        "paddingX": padding,
        "paddingY": padding,
        "edgePadding": layout_packing["edgePadding"],
        "duplicatePadding": layout_packing["duplicatePadding"],
        "rotation": layout_packing["rotation"],
        "stripWhitespaceX": layout_packing["stripWhitespace"],
        "stripWhitespaceY": layout_packing["stripWhitespace"],
        "maxWidth": max_page[0],
        "maxHeight": max_page[1],
    }
    for key, expected in parity.items():
        if packer.get(key) != expected:
            fail(f"texturepacker-final.json {key}={packer.get(key)!r}; layout contract requires {expected!r}")
    if packer.get("ignoreBlankImages") is not False:
        fail("TexturePacker ignoreBlankImages must remain false so empty production frames fail visibly")
    if packer.get("useIndexes") is not True:
        fail("TexturePacker useIndexes must remain true for animation frame lookup")
    if packer.get("filterMin") != "Nearest" or packer.get("filterMag") != "Nearest":
        fail("final actor atlas filtering must remain Nearest/Nearest")


def main() -> int:
    if not LAYOUT_PATH.is_file():
        print(f"ERROR: missing {LAYOUT_PATH.relative_to(ROOT)}", file=sys.stderr)
        return 1

    try:
        layout = json.loads(LAYOUT_PATH.read_text(encoding="utf-8"))
        if layout.get("schema") != 1:
            fail("schema must be exactly 1")
        if layout.get("directions") != EXPECTED_DIRECTIONS:
            fail(f"directions must be exactly {EXPECTED_DIRECTIONS}")
        if layout.get("motionOrder") != EXPECTED_MOTIONS:
            fail(f"motionOrder must be exactly {EXPECTED_MOTIONS}")

        standard = validate_frame_map(layout.get("standardFrames"), "standardFrames")
        boss = validate_frame_map(layout.get("bossFrames"), "bossFrames")
        actors = layout.get("actors")
        if not isinstance(actors, list) or not actors:
            fail("actors must be a non-empty list")

        ids: set[str] = set()
        roots: set[str] = set()
        present = 0
        for actor in actors:
            actor_id, atlas_root, expected_w, expected_h, _ = validate_actor(actor, standard, boss)
            if actor_id in ids:
                fail(f"duplicate actor id: {actor_id}")
            if atlas_root in roots:
                fail(f"duplicate atlas root: {atlas_root}")
            ids.add(actor_id)
            roots.add(atlas_root)

            png = ROOT / "art_sources" / f"{actor_id}.png"
            if png.exists():
                present += 1
                actual_w, actual_h = png_dimensions(png)
                if (actual_w, actual_h) != (expected_w, expected_h):
                    fail(
                        f"{png.relative_to(ROOT)} is {actual_w}x{actual_h}; "
                        f"contract requires exactly {expected_w}x{expected_h}"
                    )

        validate_packing(layout.get("packing"))
        print(f"final sprite layout OK: {len(actors)} actors, {present} production PNG sheet(s) present, packer profile aligned")
        return 0
    except (OSError, json.JSONDecodeError, ValueError) as exc:
        print(f"ERROR: final sprite layout validation failed: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
