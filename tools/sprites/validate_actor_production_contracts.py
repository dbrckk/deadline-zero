#!/usr/bin/env python3
from __future__ import annotations

import hashlib
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
            if manifest.get("root") != root:
                fail(f"{actor}: manifest root mismatch: {manifest.get('root')!r} != {root!r}")
            if manifest.get("frame_count") != total:
                fail(f"{actor}: manifest frame_count mismatch: {manifest.get('frame_count')!r}")
            if manifest.get("cell") != contract.get("cell"):
                fail(f"{actor}: manifest cell mismatch: {manifest.get('cell')!r}")
            if manifest.get("directions") != directions:
                fail(f"{actor}: manifest directions mismatch: {manifest.get('directions')!r}")
            if manifest.get("animations") != animations:
                fail(f"{actor}: manifest animations mismatch: {manifest.get('animations')!r}")
            if manifest.get("png") != Path(png).name:
                fail(f"{actor}: manifest PNG name mismatch: {manifest.get('png')!r} != {Path(png).name!r}")
            expected_png_sha256 = manifest.get("png_sha256")
            if not isinstance(expected_png_sha256, str) or len(expected_png_sha256) != 64:
                fail(f"{actor}: manifest missing valid png_sha256")
            actual_png_sha256 = hashlib.sha256((ROOT / png).read_bytes()).hexdigest()
            if actual_png_sha256 != expected_png_sha256:
                fail(
                    f"{actor}: published PNG sha256 mismatch: "
                    f"{actual_png_sha256} != {expected_png_sha256}"
                )

            source = spec.get("source") or {}
            provenance_pairs = (
                ("source_sha256", "sha256"),
                ("source_git_blob", "git_blob_sha"),
                ("source_mirror_commit", "mirror_commit"),
            )
            for manifest_key, contract_key in provenance_pairs:
                if manifest_key in manifest and contract_key in source:
                    if manifest.get(manifest_key) != source.get(contract_key):
                        fail(
                            f"{actor}: provenance mismatch for {manifest_key}: "
                            f"{manifest.get(manifest_key)!r} != {source.get(contract_key)!r}"
                        )

            published_from_run = manifest.get("published_from_run")
            validation_run = manifest.get("validation_run")
            if validation_run is not None and published_from_run is not None:
                if validation_run != published_from_run:
                    fail(
                        f"{actor}: manifest publication evidence mismatch: "
                        f"validation_run={validation_run!r} != published_from_run={published_from_run!r}"
                    )
            validation_artifact_id = manifest.get("validation_artifact_id")
            if validation_artifact_id is not None:
                if not isinstance(validation_artifact_id, int) or validation_artifact_id <= 0:
                    fail(f"{actor}: invalid manifest validation_artifact_id: {validation_artifact_id!r}")

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
