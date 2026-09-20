#!/usr/bin/env python3
"""Fail when accepted actor QA evidence and published manifest state drift apart."""
from __future__ import annotations

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CONTRACTS = ROOT / "config" / "actor-production-contracts.json"
ART = ROOT / "assets" / "art"


def manifest_path(actor: str) -> Path:
    if actor == "boss":
        return ART / "boss-manifest.json"
    return ART / f"{actor}-manifest.json"


def is_explicit_candidate(manifest: dict) -> bool:
    stage = str(manifest.get("source_stage") or "").lower()
    return "candidate" in stage or "smoke" in stage


def main() -> int:
    contracts = json.loads(CONTRACTS.read_text(encoding="utf-8")).get("actors", {})
    failures: list[str] = []
    checked = 0
    skipped_candidates: list[str] = []

    for actor, contract in sorted(contracts.items()):
        if contract.get("status") != "accepted":
            continue
        validation = contract.get("validation", {})
        if validation.get("phone_qa_pass") is not True or validation.get("android_visual_qa_pass") is not True:
            continue

        path = manifest_path(actor)
        if not path.is_file():
            failures.append(f"{actor}: missing published manifest {path.relative_to(ROOT)}")
            continue

        manifest = json.loads(path.read_text(encoding="utf-8"))
        if is_explicit_candidate(manifest):
            skipped_candidates.append(actor)
            continue

        checked += 1
        if manifest.get("source_production_ready") is not True:
            failures.append(f"{actor}: source_production_ready must be true")
        if manifest.get("android_accepted") is not True:
            failures.append(f"{actor}: android_accepted must be true")
        if manifest.get("android_visual_qa_pass") is not True:
            failures.append(f"{actor}: android_visual_qa_pass must be true")

        expected_run = validation.get("android_runtime_run_id")
        if expected_run and manifest.get("android_runtime_run_id") != expected_run:
            failures.append(
                f"{actor}: android_runtime_run_id {manifest.get('android_runtime_run_id')} != contract {expected_run}"
            )
        expected_artifact = validation.get("android_visual_artifact_id")
        if expected_artifact and manifest.get("android_visual_artifact_id") != expected_artifact:
            failures.append(
                f"{actor}: android_visual_artifact_id {manifest.get('android_visual_artifact_id')} != contract {expected_artifact}"
            )

    print(
        f"Final-art promotion consistency: checked={checked}, "
        f"explicit_candidates={','.join(skipped_candidates) or '-'}"
    )
    if failures:
        for failure in failures:
            print("ERROR:", failure)
        return 1
    print("Final-art promotion consistency PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
