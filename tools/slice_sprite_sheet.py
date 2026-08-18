#!/usr/bin/env python3
"""Cut a deterministic Deadline Zero actor sprite sheet into atlas-ready PNG frames.

Requires Pillow (`python -m pip install pillow`). The sheet layout is intentionally simple:
- rows: 8 directions in N, NE, E, SE, S, SW, W, NW order
- columns: motion groups in idle, run, attack, hit, death order
- every source cell has the same dimensions

The cutter also records alpha bounds and catches unstable foot/center pivots before packing.
Death is excluded from pivot-stability checks because the authored silhouette is expected to fall.
"""

from __future__ import annotations

import argparse
import json
import statistics
from pathlib import Path
from typing import Dict, Tuple

from PIL import Image

DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
STANDARD = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
BOSS = {"idle": 6, "run": 8, "attack": 8, "hit": 4, "death": 10}
PIVOT_CHECKED_MOTIONS = frozenset(("idle", "run", "attack", "hit"))


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
    return frame.getchannel("A").getbbox()


def validate_padding(frame: Image.Image, padding: int) -> bool:
    bbox = occupied_bbox(frame)
    if bbox is None:
        return False
    left, top, right, bottom = bbox
    width, height = frame.size
    return left >= padding and top >= padding and right <= width - padding and bottom <= height - padding


def alpha_metrics(frame: Image.Image):
    bbox = occupied_bbox(frame)
    if bbox is None:
        return None
    left, top, right, bottom = bbox
    return {
        "bbox": [left, top, right, bottom],
        "centerX": round((left + right) / 2.0, 2),
        "footY": bottom,
        "visibleWidth": right - left,
        "visibleHeight": bottom - top,
    }


def append_pivot_warnings(metadata: dict, root: str, direction: str, motion: str,
                          sequence: list[dict], foot_tolerance: float, center_tolerance: float) -> None:
    if motion not in PIVOT_CHECKED_MOTIONS or len(sequence) < 2:
        return
    valid = [item for item in sequence if item["metrics"] is not None]
    if len(valid) < 2:
        return

    median_foot = statistics.median(item["metrics"]["footY"] for item in valid)
    median_center = statistics.median(item["metrics"]["centerX"] for item in valid)
    motion_multiplier = 2.0 if motion == "attack" else 1.0
    allowed_foot = foot_tolerance * motion_multiplier
    allowed_center = center_tolerance * motion_multiplier

    for item in valid:
        frame_index = item["index"]
        metrics = item["metrics"]
        foot_delta = abs(metrics["footY"] - median_foot)
        center_delta = abs(metrics["centerX"] - median_center)
        if foot_delta > allowed_foot:
            metadata["warnings"].append(
                f"foot pivot drift {foot_delta:.1f}px > {allowed_foot:.1f}px: "
                f"{root}/{direction}/{motion}[{frame_index}]"
            )
        if center_delta > allowed_center:
            metadata["warnings"].append(
                f"horizontal pivot drift {center_delta:.1f}px > {allowed_center:.1f}px: "
                f"{root}/{direction}/{motion}[{frame_index}]"
            )

    metadata["qaMetrics"][f"{direction}/{motion}"] = {
        "medianFootY": median_foot,
        "medianCenterX": median_center,
        "footTolerance": allowed_foot,
        "centerTolerance": allowed_center,
    }


def export(sheet: Image.Image, root: str, output: Path, cell: Tuple[int, int], counts: Dict[str, int],
           padding: int, foot_tolerance: float, center_tolerance: float):
    cell_w, cell_h = cell
    total_columns = sum(counts.values())
    required_w = total_columns * cell_w
    required_h = len(DIRECTIONS) * cell_h
    if sheet.width != required_w or sheet.height != required_h:
        raise ValueError(
            f"sheet is {sheet.width}x{sheet.height}, requires exactly {required_w}x{required_h} "
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
        "qaMetrics": {},
    }

    for row, direction in enumerate(DIRECTIONS):
        column = 0
        for motion, count in counts.items():
            sequence: list[dict] = []
            for frame_index in range(count):
                x = column * cell_w
                y = row * cell_h
                frame = sheet.crop((x, y, x + cell_w, y + cell_h))
                metrics = alpha_metrics(frame)
                if metrics is None:
                    metadata["warnings"].append(f"empty: {root}/{direction}/{motion}[{frame_index}]")
                elif not validate_padding(frame, padding):
                    metadata["warnings"].append(
                        f"alpha padding < {padding}px: {root}/{direction}/{motion}[{frame_index}]"
                    )

                target_dir = output / root_path / direction
                target_dir.mkdir(parents=True, exist_ok=True)
                target = target_dir / f"{motion}_{frame_index:02d}.png"
                frame.save(target, "PNG", optimize=True)
                frame_record = {
                    "atlasKey": f"{root}/{direction}/{motion}",
                    "index": frame_index,
                    "file": str(target.relative_to(output)).replace("\\", "/"),
                    "sourceCell": [column, row],
                    "metrics": metrics,
                }
                metadata["frames"].append(frame_record)
                sequence.append(frame_record)
                column += 1

            append_pivot_warnings(
                metadata, root, direction, motion, sequence, foot_tolerance, center_tolerance)

    return metadata


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--sheet", required=True, type=Path)
    parser.add_argument("--root", required=True, help="Atlas root, e.g. survivor/rex or boss/alpha")
    parser.add_argument("--cell", required=True, type=parse_cell)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--boss", action="store_true")
    parser.add_argument("--padding", type=int, default=2)
    parser.add_argument("--foot-tolerance", type=float, default=3.0,
                        help="Allowed alpha-bottom drift in px for idle/run/hit; attack gets 2x")
    parser.add_argument("--center-tolerance", type=float, default=6.0,
                        help="Allowed alpha-bounds center drift in px for idle/run/hit; attack gets 2x")
    for motion in STANDARD:
        parser.add_argument(f"--{motion}", type=int, default=None)
    args = parser.parse_args()

    if args.padding < 0:
        parser.error("--padding must be >= 0")
    if args.foot_tolerance < 0 or args.center_tolerance < 0:
        parser.error("pivot tolerances must be >= 0")
    if not args.sheet.is_file():
        parser.error(f"sheet not found: {args.sheet}")

    counts = frame_counts(args)
    with Image.open(args.sheet) as source:
        if source.format != "PNG":
            parser.error("production source sheet must be PNG")
        sheet = source.convert("RGBA")
        metadata = export(
            sheet, args.root.strip("/"), args.output, args.cell, counts,
            args.padding, args.foot_tolerance, args.center_tolerance)

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
