#!/usr/bin/env python3
"""Verify that a packed libGDX TextureAtlas covers the final actor animation contract."""

from __future__ import annotations

import argparse
import json
import struct
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT_PATH = ROOT / "art_sources" / "final-sprite-layout.json"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"


def atlas_pages(path: Path) -> list[str]:
    pages: list[str] = []
    for raw in path.read_text(encoding="utf-8").splitlines():
        stripped = raw.strip()
        if ":" in stripped:
            continue
        lower = stripped.lower()
        if lower.endswith((".png", ".jpg", ".jpeg", ".webp")):
            pages.append(stripped.replace("\\", "/"))
    return pages


def png_dimensions(path: Path) -> tuple[int, int]:
    with path.open("rb") as handle:
        header = handle.read(24)
    if len(header) < 24 or header[:8] != PNG_SIGNATURE or header[12:16] != b"IHDR":
        raise ValueError(f"not a valid PNG with IHDR: {path}")
    return struct.unpack(">II", header[16:24])


def parse_atlas(path: Path) -> dict[str, set[int]]:
    regions: dict[str, set[int]] = {}
    current: str | None = None
    current_index = -1

    def commit() -> None:
        nonlocal current, current_index
        if current is not None:
            regions.setdefault(current, set()).add(current_index)
        current = None
        current_index = -1

    for raw in path.read_text(encoding="utf-8").splitlines():
        stripped = raw.strip()
        if not stripped:
            commit()
            continue
        if ":" not in stripped:
            lower = stripped.lower()
            if lower.endswith((".png", ".jpg", ".jpeg", ".webp")):
                commit()
                continue
            commit()
            current = stripped.replace("\\", "/").strip("/")
            continue
        if current is None:
            continue
        key, value = stripped.split(":", 1)
        if key.strip() == "index":
            try:
                current_index = int(value.strip())
            except ValueError as exc:
                raise ValueError(f"invalid atlas index for {current}: {value.strip()!r}") from exc
    commit()
    return regions


def expected_contract(layout: dict) -> dict[str, set[int]]:
    directions = layout["directions"]
    motions = layout["motionOrder"]
    expected: dict[str, set[int]] = {}
    for actor in layout["actors"]:
        counts = dict(layout["bossFrames"] if actor["profile"] == "boss" else layout["standardFrames"])
        if "runFrames" in actor:
            counts["run"] = actor["runFrames"]
        for direction in directions:
            for motion in motions:
                key = f"{actor['root']}/{direction}/{motion}"
                expected[key] = set(range(counts[motion]))
    return expected


def validate_pages(atlas: Path, layout: dict) -> list[tuple[str, int, int]]:
    names = atlas_pages(atlas)
    if not names:
        raise ValueError("atlas contains no texture pages")
    max_width, max_height = layout["packing"]["maxPage"]
    root = atlas.parent.resolve()
    seen: set[str] = set()
    result: list[tuple[str, int, int]] = []
    for name in names:
        if name in seen:
            raise ValueError(f"duplicate atlas texture page declaration: {name}")
        seen.add(name)
        if not name.lower().endswith(".png"):
            raise ValueError(f"final atlas page must be PNG: {name}")
        page = (atlas.parent / name).resolve()
        try:
            page.relative_to(root)
        except ValueError as exc:
            raise ValueError(f"atlas page escapes assets directory: {name}") from exc
        if not page.is_file():
            raise ValueError(f"atlas page missing: {page}")
        width, height = png_dimensions(page)
        if width <= 0 or height <= 0 or width > max_width or height > max_height:
            raise ValueError(
                f"atlas page dimensions invalid: {name} is {width}x{height}; max {max_width}x{max_height}"
            )
        result.append((name, width, height))
    return result


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--atlas", type=Path, default=ROOT / "assets" / "art" / "game.atlas")
    args = parser.parse_args()
    atlas = args.atlas.resolve()

    if not LAYOUT_PATH.is_file():
        print(f"ERROR: missing {LAYOUT_PATH.relative_to(ROOT)}", file=sys.stderr)
        return 1
    if not atlas.is_file():
        print(f"ERROR: final atlas missing: {atlas}", file=sys.stderr)
        return 1

    try:
        layout = json.loads(LAYOUT_PATH.read_text(encoding="utf-8"))
        pages = validate_pages(atlas, layout)
        actual = parse_atlas(atlas)
        expected = expected_contract(layout)
        missing_keys: list[str] = []
        bad_indices: list[str] = []
        for key, required_indices in expected.items():
            actual_indices = actual.get(key)
            if actual_indices is None:
                missing_keys.append(key)
                continue
            if actual_indices != required_indices:
                missing = sorted(required_indices - actual_indices)
                extra = sorted(actual_indices - required_indices)
                bad_indices.append(f"{key}: missing={missing}, extra={extra}")

        if missing_keys or bad_indices:
            print("ERROR: final atlas does not satisfy production animation coverage", file=sys.stderr)
            if missing_keys:
                print(f"missing animation keys: {len(missing_keys)}", file=sys.stderr)
                for key in missing_keys[:40]:
                    print(f"  - {key}", file=sys.stderr)
                if len(missing_keys) > 40:
                    print(f"  ... {len(missing_keys) - 40} more", file=sys.stderr)
            if bad_indices:
                print(f"frame index mismatches: {len(bad_indices)}", file=sys.stderr)
                for item in bad_indices[:40]:
                    print(f"  - {item}", file=sys.stderr)
                if len(bad_indices) > 40:
                    print(f"  ... {len(bad_indices) - 40} more", file=sys.stderr)
            return 2

        expected_frames = sum(len(indices) for indices in expected.values())
        print(
            f"final atlas coverage OK: {len(pages)} page(s), {len(expected)} directional animations, "
            f"{expected_frames} indexed frames"
        )
        return 0
    except (OSError, json.JSONDecodeError, ValueError, KeyError, TypeError) as exc:
        print(f"ERROR: unable to audit final atlas: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
