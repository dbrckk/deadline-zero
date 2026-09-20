#!/usr/bin/env python3
"""Install all authored environment candidate packs into the runtime atlas.

This is an integration helper: it packs every contracted biome from
art_sources/environment/<biome>, installs the generated atlas pages into
assets/art/game.atlas, then enforces 70/70 runtime coverage.

Candidate manifests remain candidate manifests; this tool does not mark visual
QA or production approval as complete.
"""
from __future__ import annotations

import argparse
import json
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CONTRACT = ROOT / "config" / "environment-art-contract.json"
BUILD = ROOT / "build" / "environment_art"
PACK = ROOT / "tools" / "environment" / "pack_environment_art.py"
UPSERT = ROOT / "tools" / "environment" / "upsert_environment_atlas.py"
VALIDATE = ROOT / "tools" / "environment" / "validate_environment_art_contract.py"
ATLAS = ROOT / "assets" / "art" / "game.atlas"


def run(*args: str) -> None:
    subprocess.run([sys.executable, *args], cwd=ROOT, check=True)


def load_biomes() -> list[str]:
    data = json.loads(CONTRACT.read_text(encoding="utf-8"))
    biomes = [item["id"] for item in data["biomes"]]
    if len(biomes) != 5 or len(set(biomes)) != 5:
        raise SystemExit("environment contract must define exactly 5 unique biomes")
    return biomes


def validate_candidate_manifest(biome: str) -> None:
    manifest = ROOT / "art_sources" / "environment" / biome / "candidate-manifest.json"
    if not manifest.is_file():
        raise SystemExit(f"missing candidate manifest: {manifest}")
    data = json.loads(manifest.read_text(encoding="utf-8"))
    if data.get("biome") != biome:
        raise SystemExit(f"candidate biome mismatch: {manifest}")
    if data.get("asset_count") != 14:
        raise SystemExit(f"candidate must contain 14 assets: {manifest}")
    assets = data.get("assets")
    if not isinstance(assets, list) or len(assets) != 14:
        raise SystemExit(f"candidate assets list must contain 14 entries: {manifest}")
    # Integration must never silently promote art approval state.
    if data.get("production_ready") is not False or data.get("visual_qa_pass") is not False:
        raise SystemExit(
            f"candidate approval state changed unexpectedly: {manifest}; "
            "use the dedicated final-art approval flow"
        )


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--atlas",
        type=Path,
        default=ATLAS,
        help="runtime atlas to update (defaults to assets/art/game.atlas)",
    )
    args = parser.parse_args()
    atlas = args.atlas.resolve()

    biomes = load_biomes()
    BUILD.mkdir(parents=True, exist_ok=True)

    for biome in biomes:
        validate_candidate_manifest(biome)
        run(str(PACK), "--biome", biome, "--output", str(BUILD))
        fragment = BUILD / f"environment-{biome}.atlas.txt"
        page = BUILD / f"environment-{biome}.png"
        run(
            str(UPSERT),
            "--atlas", str(atlas),
            "--fragment", str(fragment),
            "--page", str(page),
        )

    run(str(VALIDATE), "--atlas", str(atlas), "--require-complete")

    installed_pages = [atlas.parent / f"environment-{biome}.png" for biome in biomes]
    missing_pages = [str(path) for path in installed_pages if not path.is_file()]
    if missing_pages:
        raise SystemExit("missing installed runtime page(s): " + ", ".join(missing_pages))

    print("environment runtime integration PASS: 5 biomes / 70 regions installed")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

# Runtime integration is intentionally idempotent across CI reruns.
