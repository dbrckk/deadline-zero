#!/usr/bin/env python3
"""Resolve required actor motions against imported Blender action names.

Candidate configs use semantic aliases while Blender importers may append object,
armature, rig, skeleton, or numeric suffixes. Resolution is deterministic:
exact names win; canonical matches are accepted only when unique; ambiguous
canonical matches are reported and never guessed.
"""
from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

REQUIRED = ("idle", "run", "attack", "hit", "death")
KNOWN_SUFFIXES = ("characterarmature", "armature", "rig", "skeleton")


def normalize(name: str) -> str:
    value = re.sub(r"\.\d{3}$", "", name.strip().lower())
    compact = re.sub(r"[^a-z0-9]+", "", value)
    changed = True
    while changed:
        changed = False
        for suffix in KNOWN_SUFFIXES:
            if compact.endswith(suffix) and len(compact) > len(suffix):
                compact = compact[: -len(suffix)]
                changed = True
                break
    return compact


def resolve(actions: dict[str, list[str]], available: list[str]) -> dict:
    lowered = {name.lower(): name for name in available}
    canonical_to_names: dict[str, list[str]] = {}
    for name in available:
        canonical_to_names.setdefault(normalize(name), []).append(name)

    mapped: dict[str, str] = {}
    methods: dict[str, str] = {}
    ambiguities: dict[str, dict[str, list[str]]] = {}

    for motion, aliases in actions.items():
        for alias in aliases:
            exact = lowered.get(alias.lower())
            if exact is not None:
                mapped[motion] = exact
                methods[motion] = "exact"
                break

            matches = canonical_to_names.get(normalize(alias), [])
            if len(matches) == 1:
                mapped[motion] = matches[0]
                methods[motion] = "canonical"
                break
            if len(matches) > 1:
                ambiguities.setdefault(motion, {})[alias] = matches

    missing = [motion for motion in REQUIRED if motion not in mapped]
    return {
        "available_actions": available,
        "resolved": mapped,
        "resolution_method": methods,
        "missing": missing,
        "ambiguities": ambiguities,
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
