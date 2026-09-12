#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CONFIG = ROOT / "config" / "actor-production-contracts.json"
ATLAS = ROOT / "assets" / "art" / "game.atlas"
EXPECTED_COMPLETE_ROSTER = {
    "rex", "shambler", "runner", "brute", "ranged", "elite", "shielded", "regenerator",
    "phantom", "boss", "nyx", "bastion", "volt", "wraith", "revenant", "warden",
    "harvester", "null_archon", "forge_hound", "cinder_gunner", "slag_guard", "phase_stalker",
    "static_seer", "null_ward",
}


def fail(message: str) -> None:
    raise SystemExit(message)


def validate_complete_roster(actors) -> None:
    accepted_names = {actor for actor, spec in actors.items() if spec.get("status") == "accepted"}
    if accepted_names != EXPECTED_COMPLETE_ROSTER:
        missing = sorted(EXPECTED_COMPLETE_ROSTER - accepted_names)
        unexpected = sorted(accepted_names - EXPECTED_COMPLETE_ROSTER)
        fail(f"complete roster contract mismatch: missing={missing}, unexpected={unexpected}")


def main() -> None:
    data = json.loads(CONFIG.read_text(encoding="utf-8"))
    if data.get("schema_version") != 1:
        fail("unsupported actor production contract schema")

    contract = data.get("frame_contract") or {}
    directions = contract.get("directions") or []
    animations = contract.get("animations") or {}
    total = contract.get("total_frames")
    frames_per_direction = contract.get("frames_per_direction")

    if directions != ["n", "ne", "e", "se", "s", "sw", "w", "nw"]:
        fail(f"unexpected direction contract: {directions}")
    if animations != {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}:
        fail(f"unexpected animation contract: {animations}")
    if sum(animations.values()) != frames_per_direction:
        fail("frames_per_direction does not match animation counts")
    if len(directions) * frames_per_direction != total:
        fail("total_frames does not match direction/animation contract")

    atlas_text = ATLAS.read_text(encoding="utf-8") if ATLAS.exists() else ""
    actors = data.get("actors") or {}
    if not actors:
        fail("no actor production contracts defined")

    validate_complete_roster(actors)

    accepted = []
    for actor, spec in sorted(actors.items()):
        status = spec.get("status")
        root = spec.get("atlas_root")
        if not root or not isinstance(root, str):
            fail(f"{actor}: missing atlas_root")
        if status not in {"accepted", "candidate", "planned"}:
            fail(f"{actor}: invalid status {status!r}")

        if status != "accepted":
            continue

        accepted.append(actor)
        png = spec.get("published_png")
        if not png or not (ROOT / png).is_file():
            fail(f"{actor}: accepted actor missing published PNG: {png}")

        manifest_path = spec.get("published_manifest")
        if manifest_path:
            path = ROOT / manifest_path
            if not path.is_file():
                fail(f"{actor}: accepted actor missing manifest: {manifest_path}")
            manifest = json.loads(path.read_text(encoding="utf-8"))
            if manifest.get("actor") != actor:
                fail(f"{actor}: manifest actor mismatch: {manifest.get('actor')!r}")
            if manifest.get("frame_count") != total:
                fail(f"{actor}: manifest frame_count mismatch: {manifest.get('frame_count')!r}")

        for direction in directions:
            for animation, count in animations.items():
                key = f"{root}/{direction}/{animation}\n"
                got = atlas_text.count(key)
                if got != count:
                    fail(f"{actor}: atlas group {root}/{direction}/{animation} has {got}, expected {count}")

    print(
        "Actor production contracts PASS:",
        f"{len(accepted)} accepted actor(s)",
        ", ".join(accepted),
        f"with {total} frames each",
    )


if __name__ == "__main__":
    main()
