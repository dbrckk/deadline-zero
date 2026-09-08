#!/usr/bin/env python3
"""Validate that a RANGED actor has a phone-readable forward attack protrusion."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
from statistics import median
from PIL import Image


def bbox(path: Path):
    return Image.open(path).convert("RGBA").getchannel("A").getbbox()


def directional_extension(root: Path, direction: str) -> dict:
    idle = sorted((root / "idle" / direction).glob("*.png"))
    attack = sorted((root / "attack" / direction).glob("*.png"))
    if not idle or not attack:
        raise SystemExit(f"missing idle/attack frames for {direction}")
    ib = [bbox(p) for p in idle]
    ab = [bbox(p) for p in attack]
    if any(b is None for b in ib + ab):
        raise SystemExit(f"empty frame in {direction}")
    if direction == "e":
        idle_front = median(b[2] for b in ib)
        attack_front = max(b[2] for b in ab)
        extension = attack_front - idle_front
    elif direction == "w":
        idle_front = median(b[0] for b in ib)
        attack_front = min(b[0] for b in ab)
        extension = idle_front - attack_front
    else:
        raise ValueError(direction)
    return {
        "direction": direction,
        "idle_front_px": idle_front,
        "attack_front_px": attack_front,
        "forward_extension_px": extension,
    }


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("--input", type=Path, required=True)
    ap.add_argument("--report", type=Path, required=True)
    ap.add_argument("--minimum-extension-px", type=float, default=8.0)
    args = ap.parse_args()
    rows = [directional_extension(args.input, "e"), directional_extension(args.input, "w")]
    minimum = min(r["forward_extension_px"] for r in rows)
    payload = {
        "pass": minimum >= args.minimum_extension_px,
        "gate": "ranged-forward-attack-readability-v1",
        "minimum_required_extension_px": args.minimum_extension_px,
        "minimum_observed_extension_px": minimum,
        "directions": rows,
    }
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(payload, indent=2) + "\n")
    print(json.dumps(payload, indent=2))
    if not payload["pass"]:
        raise SystemExit("RANGED attack is not visibly distinct enough at phone scale")


if __name__ == "__main__":
    main()
