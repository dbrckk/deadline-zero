#!/usr/bin/env python3
import argparse
import json
import re
from pathlib import Path

REQUIRED = ("idle", "run", "attack", "hit", "death")
KNOWN_SUFFIXES = (
    "characterarmature",
    "armature",
    "rig",
    "skeleton",
)


def canonical(name: str) -> str:
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


def resolve(available, aliases_by_motion):
    lowered = {name.lower(): name for name in available}
    canonical_to_names = {}
    for name in available:
        canonical_to_names.setdefault(canonical(name), []).append(name)

    mapped = {}
    methods = {}
    ambiguities = {}
    for motion, aliases in aliases_by_motion.items():
        for alias in aliases:
            exact = lowered.get(alias.lower())
            if exact is not None:
                mapped[motion] = exact
                methods[motion] = "exact"
                break

            matches = canonical_to_names.get(canonical(alias), [])
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


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--candidate", required=True)
    parser.add_argument("--inspection", required=True)
    parser.add_argument("--out", required=True)
    args = parser.parse_args()

    candidate = json.loads(Path(args.candidate).read_text())
    inspection = json.loads(Path(args.inspection).read_text())
    available = [row["name"] for row in inspection.get("actions", [])]
    payload = resolve(available, candidate["actions"])
    Path(args.out).write_text(json.dumps(payload, indent=2) + "\n")
    print(json.dumps(payload, indent=2))
    if payload["missing"]:
        raise SystemExit("Candidate lacks required native actions: " + ", ".join(payload["missing"]))


if __name__ == "__main__":
    main()
