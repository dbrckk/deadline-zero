#!/usr/bin/env python3
"""Batch-cut all production actor sheets defined by final-sprite-layout.json.

Requires Pillow because it delegates frame extraction to slice_sprite_sheet.py.
Typical final-art command:

  python tools/build_final_sprite_frames.py --clean --require-all --strict

Without --require-all, missing source PNGs are skipped so artists can deliver actors incrementally.
"""

from __future__ import annotations

import argparse
import json
import shutil
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
CUTTER = ROOT / "tools" / "slice_sprite_sheet.py"
DEFAULT_OUTPUT = ROOT / "build" / "art_frames"


def actor_command(actor: dict, output: Path, padding: int,
                  foot_tolerance: float, center_tolerance: float) -> list[str]:
    cell_w, cell_h = actor["cell"]
    cell_arg = str(cell_w) if cell_w == cell_h else f"{cell_w}x{cell_h}"
    command = [
        sys.executable,
        str(CUTTER),
        "--sheet", str(ROOT / "art_sources" / f"{actor['id']}.png"),
        "--root", actor["root"],
        "--cell", cell_arg,
        "--output", str(output),
        "--padding", str(padding),
        "--foot-tolerance", str(foot_tolerance),
        "--center-tolerance", str(center_tolerance),
    ]
    if actor["profile"] == "boss":
        command.append("--boss")
    if "runFrames" in actor:
        command.extend(["--run", str(actor["runFrames"])])
    return command


def manifest_path(actor: dict, output: Path) -> Path:
    return output / (actor["root"].replace("/", "__") + ".frames.json")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUTPUT)
    parser.add_argument("--padding", type=int, default=2)
    parser.add_argument("--foot-tolerance", type=float, default=3.0,
                        help="Allowed foot-pivot drift in pixels; attack receives 2x")
    parser.add_argument("--center-tolerance", type=float, default=6.0,
                        help="Allowed horizontal alpha-center drift in pixels; attack receives 2x")
    parser.add_argument("--clean", action="store_true", help="Delete the output directory before cutting")
    parser.add_argument("--require-all", action="store_true", help="Fail when any contracted source PNG is missing")
    parser.add_argument("--strict", action="store_true", help="Fail on empty cells, padding violations, or pivot drift")
    args = parser.parse_args()

    if args.padding < 0:
        parser.error("--padding must be >= 0")
    if args.foot_tolerance < 0 or args.center_tolerance < 0:
        parser.error("pivot tolerances must be >= 0")
    if not LAYOUT.is_file() or not CUTTER.is_file():
        parser.error("final sprite layout or cutter is missing")

    layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
    actors = sorted(layout["actors"], key=lambda actor: (actor["priority"], actor["id"]))
    output = args.output.resolve()
    if args.clean and output.exists():
        shutil.rmtree(output)
    output.mkdir(parents=True, exist_ok=True)

    missing: list[str] = []
    warnings: list[str] = []
    built: list[dict] = []

    for actor in actors:
        sheet = ROOT / "art_sources" / f"{actor['id']}.png"
        if not sheet.is_file():
            missing.append(actor["id"])
            continue
        print(f"\n== {actor['id']} (priority {actor['priority']}) ==")
        completed = subprocess.run(
            actor_command(actor, output, args.padding, args.foot_tolerance, args.center_tolerance),
            cwd=ROOT,
        )
        if completed.returncode != 0:
            return completed.returncode
        manifest = manifest_path(actor, output)
        if not manifest.is_file():
            print(f"ERROR: cutter did not create {manifest}", file=sys.stderr)
            return 1
        metadata = json.loads(manifest.read_text(encoding="utf-8"))
        actor_warnings = metadata.get("warnings", [])
        warnings.extend(actor_warnings)
        built.append({
            "id": actor["id"],
            "root": actor["root"],
            "priority": actor["priority"],
            "frames": len(metadata.get("frames", [])),
            "warnings": len(actor_warnings),
            "pivotSequences": len(metadata.get("qaMetrics", {})),
        })

    aggregate = {
        "schema": 1,
        "qa": {
            "padding": args.padding,
            "footTolerance": args.foot_tolerance,
            "centerTolerance": args.center_tolerance,
        },
        "builtActors": built,
        "missingActors": missing,
        "totalFrames": sum(item["frames"] for item in built),
        "qaWarnings": len(warnings),
    }
    aggregate_path = output / "final-sprite-build.json"
    aggregate_path.write_text(json.dumps(aggregate, indent=2) + "\n", encoding="utf-8")

    print("\n== final sprite build summary ==")
    print(f"actors built: {len(built)}/{len(actors)}")
    print(f"frames built: {aggregate['totalFrames']}")
    print(f"QA warnings: {len(warnings)}")
    print(f"pivot tolerances: foot={args.foot_tolerance}px center={args.center_tolerance}px")
    print(f"manifest: {aggregate_path}")
    if missing:
        print("missing: " + ", ".join(missing))
    if warnings:
        for warning in warnings[:40]:
            print(f"  - {warning}")
        if len(warnings) > 40:
            print(f"  ... {len(warnings) - 40} more")

    if args.require_all and missing:
        print("ERROR: --require-all set and production sheets are missing", file=sys.stderr)
        return 2
    if args.strict and warnings:
        print("ERROR: --strict set and sprite QA warnings were found", file=sys.stderr)
        return 3
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
