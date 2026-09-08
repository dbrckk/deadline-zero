#!/usr/bin/env python3
"""Resolve required actor motions against imported Blender action names.

Importers commonly append armature/object suffixes (for example
``Idle_CharacterArmature``). Keep candidate configs semantic by normalizing both
configured aliases and imported action names in one shared implementation used
by every CI backend.
"""
from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

REQUIRED = ("idle", "run", "attack", "hit", "death")


def normalize(name: str) -> str:
    value = name.strip().lower()
    # Blender/glTF importer suffixes seen in production sources. Strip these
    # before punctuation so aliases remain independent of importer internals.
    value = re.sub(r"_(?:character)?armature(?:\.\d+)?$", "", value)
    value = re.sub(r"_armature(?:\.\d+)?$", "", value)
    return re.sub(r"[^a-z0-9]+", "", value)


def resolve(actions: dict[str, list[str]], available: list[str]) -> dict:
    index: dict[str, str] = {}
    for name in available:
        index.setdefault(normalize(name), name)

    mapped: dict[str, str] = {}
    for motion, aliases in actions.items():
        for alias in aliases:
            hit = index.get(normalize(alias))
            if hit is not None:
                mapped[motion] = hit
                break

    missing = [motion for motion in REQUIRED if motion not in mapped]
    return {
        "available_actions": available,
        "resolved": mapped,
        "missing": missing,
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--candidate", type=Path, required=True)
    parser.add_argument("--inspection", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()

    candidate = json.loads(args.candidate.read_text())
    inspection = json.loads(args.inspection.read_text())
    available = [row["name"] for row in inspection["actions"]]
    payload = resolve(candidate["actions"], available)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(payload, indent=2) + "\n")
    print(json.dumps(payload, indent=2))
    if payload["missing"]:
        raise SystemExit("Candidate lacks required native actions: " + ", ".join(payload["missing"]))


if __name__ == "__main__":
    main()
