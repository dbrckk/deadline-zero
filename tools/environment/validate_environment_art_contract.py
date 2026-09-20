#!/usr/bin/env python3
"""Validate the Deadline Zero biome environment-art contract and report atlas coverage."""

from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
DEFAULT_CONTRACT = ROOT / "config" / "environment-art-contract.json"
DEFAULT_ATLAS = ROOT / "assets" / "art" / "game.atlas"
EXPECTED_BIOMES = 5
EXPECTED_SLOTS = 14


def load_contract(path: Path) -> dict:
    data = json.loads(path.read_text(encoding="utf-8"))
    biomes = data.get("biomes")
    slots = data.get("slots")
    if not isinstance(biomes, list) or len(biomes) != EXPECTED_BIOMES:
        raise ValueError(f"expected {EXPECTED_BIOMES} biomes")
    if not isinstance(slots, list) or len(slots) != EXPECTED_SLOTS:
        raise ValueError(f"expected {EXPECTED_SLOTS} slots")

    biome_ids = [item.get("id") for item in biomes]
    if any(not isinstance(item, str) or not item for item in biome_ids):
        raise ValueError("every biome requires a non-empty id")
    if len(set(biome_ids)) != len(biome_ids):
        raise ValueError("biome ids must be unique")

    slot_paths = [item.get("path") for item in slots]
    if any(not isinstance(item, str) or not item for item in slot_paths):
        raise ValueError("every slot requires a non-empty path")
    if len(set(slot_paths)) != len(slot_paths):
        raise ValueError("slot paths must be unique")

    for slot in slots:
        kind = slot.get("kind")
        if kind not in {"tile", "decal", "prop"}:
            raise ValueError(f"invalid slot kind: {kind!r}")
        if not isinstance(slot.get("alpha"), bool):
            raise ValueError(f"slot alpha flag must be boolean: {slot.get('path')}")
        if not isinstance(slot.get("tileable"), bool):
            raise ValueError(f"slot tileable flag must be boolean: {slot.get('path')}")

    return data


def expected_keys(contract: dict) -> list[str]:
    return [
        f"environment/{biome['id']}/{slot['path']}"
        for biome in contract["biomes"]
        for slot in contract["slots"]
    ]


def atlas_region_names(path: Path) -> set[str]:
    names: set[str] = set()
    for raw in path.read_text(encoding="utf-8").splitlines():
        stripped = raw.strip()
        if not stripped or ":" in stripped:
            continue
        lower = stripped.lower()
        if lower.endswith((".png", ".jpg", ".jpeg", ".webp")):
            continue
        names.add(stripped.replace("\\", "/").strip("/"))
    return names


def coverage(contract: dict, atlas: Path) -> tuple[list[str], list[str]]:
    expected = expected_keys(contract)
    regions = atlas_region_names(atlas) if atlas.is_file() else set()
    present = [key for key in expected if key in regions]
    missing = [key for key in expected if key not in regions]
    return present, missing


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--contract", type=Path, default=DEFAULT_CONTRACT)
    parser.add_argument("--atlas", type=Path, default=DEFAULT_ATLAS)
    parser.add_argument("--require-complete", action="store_true")
    parser.add_argument("--json", action="store_true")
    args = parser.parse_args()

    try:
        contract = load_contract(args.contract)
        expected = expected_keys(contract)
        if len(expected) != EXPECTED_BIOMES * EXPECTED_SLOTS or len(set(expected)) != len(expected):
            raise ValueError("environment key matrix must contain exactly 70 unique keys")
        present, missing = coverage(contract, args.atlas)
    except (OSError, json.JSONDecodeError, ValueError, TypeError) as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1

    payload = {
        "expected": len(expected),
        "present": len(present),
        "missing": len(missing),
        "complete": not missing,
        "coveragePercent": round(100.0 * len(present) / max(1, len(expected)), 2),
        "missingKeys": missing,
    }
    if args.json:
        print(json.dumps(payload, indent=2))
    else:
        print(
            f"environment art coverage: {payload['present']}/{payload['expected']} "
            f"({payload['coveragePercent']:.2f}%)"
        )
        if missing:
            print("next missing keys:")
            for key in missing[:14]:
                print(f"  - {key}")

    if args.require_complete and missing:
        print(f"ERROR: production environment atlas is missing {len(missing)} region(s)", file=sys.stderr)
        return 2
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
