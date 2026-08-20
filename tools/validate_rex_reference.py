#!/usr/bin/env python3
"""Keep Rex's final-art reference aligned with the machine-readable sprite contract."""

from __future__ import annotations

import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
REFERENCE = ROOT / "art_sources" / "rex-reference.md"
EXPECTED_DIRECTIONS = ["n", "ne", "e", "se", "s", "sw", "w", "nw"]
EXPECTED_MOTIONS = ["idle", "run", "attack", "hit", "death"]


def fail(message: str) -> None:
    raise ValueError(message)


def main() -> int:
    try:
        layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
        reference = REFERENCE.read_text(encoding="utf-8")
        rex = next((actor for actor in layout["actors"] if actor["id"] == "rex"), None)
        if rex is None:
            fail("final-sprite-layout.json must contain Rex")
        if rex["root"] != "survivor/rex" or rex["cell"] != [96, 96] or rex["profile"] != "standard":
            fail("Rex must remain the 96x96 standard survivor/rex reference actor")
        if layout["directions"] != EXPECTED_DIRECTIONS or layout["motionOrder"] != EXPECTED_MOTIONS:
            fail("Rex reference validator requires the canonical 8-direction / 5-motion layout")

        counts = dict(layout["standardFrames"])
        if "runFrames" in rex:
            counts["run"] = rex["runFrames"]
        columns = sum(counts[motion] for motion in EXPECTED_MOTIONS)
        width = columns * rex["cell"][0]
        height = len(EXPECTED_DIRECTIONS) * rex["cell"][1]
        total = columns * len(EXPECTED_DIRECTIONS)

        required_tokens = [
            "art_sources/rex.png",
            f"{width}×{height}",
            "96×96",
            f"{total} frames",
            "transparent RGBA background",
            "no baked muzzle flash",
            "stable",
            "python3 tools/validate_final_sprite_layout.py",
            "gradle buildFinalAtlas",
        ]
        for token in required_tokens:
            if token.lower() not in reference.lower():
                fail(f"rex-reference.md is missing contract token: {token}")

        print(f"Rex art reference OK: {width}x{height}, {total} frames, 8 directions, production QA documented")
        return 0
    except (OSError, KeyError, StopIteration, json.JSONDecodeError, ValueError) as exc:
        print(f"ERROR: Rex art reference validation failed: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
