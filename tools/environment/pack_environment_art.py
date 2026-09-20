#!/usr/bin/env python3
"""Pack one authored biome environment set into a deterministic libGDX atlas page.

Source layout:
  art_sources/environment/<biome>/<slot path>.png

Example:
  art_sources/environment/cinder_foundry/floor/concrete_a.png
  art_sources/environment/cinder_foundry/prop/crate_a.png

The source master is expected to be 512x512. This tool downsamples each slot to
256x256, writes one 1024x1024 RGBA page (14 occupied cells in a 4x4 grid), an
atlas fragment and a QA manifest. It does not mutate game.atlas automatically.
"""

from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parents[2]
CONTRACT = ROOT / "config" / "environment-art-contract.json"
DEFAULT_SOURCE = ROOT / "art_sources" / "environment"
DEFAULT_BUILD = ROOT / "build" / "environment_art"
MASTER = 512
CELL = 256
COLUMNS = 4


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as handle:
        for chunk in iter(lambda: handle.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def load_contract() -> dict:
    return json.loads(CONTRACT.read_text(encoding="utf-8"))


def alpha_bbox(image: Image.Image):
    return image.getchannel("A").getbbox()


def normalize(source: Path, slot: dict) -> tuple[Image.Image, dict]:
    with Image.open(source) as raw:
        if raw.format != "PNG":
            raise SystemExit(f"source must be PNG: {source}")
        image = raw.convert("RGBA")
    if image.size != (MASTER, MASTER):
        raise SystemExit(f"source must be {MASTER}x{MASTER}: {source} is {image.size}")

    alpha = image.getchannel("A")
    extrema = alpha.getextrema()
    bbox = alpha_bbox(image)
    if bbox is None:
        raise SystemExit(f"source is fully transparent: {source}")

    if slot["alpha"]:
        coverage = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1]) / float(MASTER * MASTER)
        if coverage < .015:
            raise SystemExit(f"transparent asset silhouette is too small: {source}")
    elif extrema != (255, 255):
        raise SystemExit(f"opaque floor tile contains transparency: {source}")

    runtime = image.resize((CELL, CELL), Image.Resampling.LANCZOS)
    return runtime, {
        "source": str(source),
        "sourceSize": [MASTER, MASTER],
        "runtimeSize": [CELL, CELL],
        "alphaExtrema": list(extrema),
        "bbox": list(bbox),
        "sha256": sha256(source),
    }


def atlas_fragment(page_name: str, biome: str, slots: list[dict]) -> str:
    lines = [
        page_name,
        f"size: {COLUMNS * CELL}, {COLUMNS * CELL}",
        "format: RGBA8888",
        "filter: Linear,Linear",
        "repeat: none",
    ]
    for index, slot in enumerate(slots):
        row, column = divmod(index, COLUMNS)
        key = f"environment/{biome}/{slot['path']}"
        lines.extend([
            key,
            "  rotate: false",
            f"  xy: {column * CELL}, {row * CELL}",
            f"  size: {CELL}, {CELL}",
            f"  orig: {CELL}, {CELL}",
            "  offset: 0, 0",
            "  index: -1",
        ])
    return "\n".join(lines) + "\n"


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--biome", required=True)
    parser.add_argument("--source-root", type=Path, default=DEFAULT_SOURCE)
    parser.add_argument("--output", type=Path, default=DEFAULT_BUILD)
    args = parser.parse_args()

    contract = load_contract()
    biome_ids = {item["id"] for item in contract["biomes"]}
    if args.biome not in biome_ids:
        parser.error(f"unknown biome {args.biome!r}; expected one of {sorted(biome_ids)}")

    slots = contract["slots"]
    page = Image.new("RGBA", (COLUMNS * CELL, COLUMNS * CELL), (0, 0, 0, 0))
    records = []
    for index, slot in enumerate(slots):
        source = args.source_root / args.biome / (slot["path"] + ".png")
        if not source.is_file():
            raise SystemExit(f"missing authored environment source: {source}")
        runtime, qa = normalize(source, slot)
        row, column = divmod(index, COLUMNS)
        page.alpha_composite(runtime, (column * CELL, row * CELL))
        qa["key"] = f"environment/{args.biome}/{slot['path']}"
        qa["kind"] = slot["kind"]
        records.append(qa)

    args.output.mkdir(parents=True, exist_ok=True)
    page_name = f"environment-{args.biome}.png"
    page_path = args.output / page_name
    fragment_path = args.output / f"environment-{args.biome}.atlas.txt"
    manifest_path = args.output / f"environment-{args.biome}.manifest.json"

    page.save(page_path, "PNG", optimize=True)
    fragment_path.write_text(atlas_fragment(page_name, args.biome, slots), encoding="utf-8")
    manifest = {
        "schema": 1,
        "biome": args.biome,
        "page": page_name,
        "pageSize": [COLUMNS * CELL, COLUMNS * CELL],
        "slotCount": len(slots),
        "pageSha256": sha256(page_path),
        "assets": records,
    }
    manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")
    print(f"packed {len(slots)} environment assets for {args.biome} -> {page_path}")
    print(f"atlas fragment: {fragment_path}")
    print(f"manifest: {manifest_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
