#!/usr/bin/env python3
"""Static semantic QA for authored biome environment candidates.

This complements the packer with inexpensive checks that catch obvious visual
production defects before Android capture review. It never marks art FINAL.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import math
from pathlib import Path

from PIL import Image, ImageChops, ImageDraw, ImageStat

ROOT = Path(__file__).resolve().parents[2]
CONTRACT = ROOT / "config" / "environment-art-contract.json"
SOURCE = ROOT / "art_sources" / "environment"
DEFAULT_OUT = ROOT / "build" / "environment_art" / "semantic_qa"
MASTER = 512


def sha256(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def luminance(rgb: tuple[float, float, float]) -> float:
    r, g, b = rgb
    return .2126 * r + .7152 * g + .0722 * b


def edge_rgb_mae(image: Image.Image) -> dict[str, float]:
    rgb = image.convert("RGB")
    left = rgb.crop((0, 0, 1, MASTER))
    right = rgb.crop((MASTER - 1, 0, MASTER, MASTER))
    top = rgb.crop((0, 0, MASTER, 1))
    bottom = rgb.crop((0, MASTER - 1, MASTER, MASTER))
    lr = ImageStat.Stat(ImageChops.difference(left, right)).mean
    tb = ImageStat.Stat(ImageChops.difference(top, bottom)).mean
    return {
        "leftRightMae": round(sum(lr) / 3.0, 3),
        "topBottomMae": round(sum(tb) / 3.0, 3),
    }


def border_alpha_max(image: Image.Image) -> int:
    a = image.getchannel("A")
    strips = [
        a.crop((0, 0, MASTER, 2)),
        a.crop((0, MASTER - 2, MASTER, MASTER)),
        a.crop((0, 0, 2, MASTER)),
        a.crop((MASTER - 2, 0, MASTER, MASTER)),
    ]
    return max(strip.getextrema()[1] for strip in strips)


def alpha_coverage(image: Image.Image) -> float:
    a = image.getchannel("A")
    hist = a.histogram()
    nonzero = sum(hist[1:])
    return nonzero / float(MASTER * MASTER)


def make_review_sheet(records: list[dict], output: Path) -> None:
    tile = 128
    cols = 5
    rows = math.ceil(len(records) / cols)
    sheet = Image.new("RGBA", (cols * tile, rows * tile), (8, 12, 16, 255))
    for i, record in enumerate(records):
        with Image.open(record["path"]) as raw:
            image = raw.convert("RGBA")
        if record["kind"] == "tile":
            preview = Image.new("RGBA", (tile, tile), (0, 0, 0, 255))
            unit = tile // 3
            small = image.resize((unit, unit), Image.Resampling.LANCZOS)
            for yy in range(3):
                for xx in range(3):
                    preview.alpha_composite(small, (xx * unit, yy * unit))
        else:
            preview = Image.new("RGBA", (tile, tile), (8, 12, 16, 255))
            small = image.resize((64, 64), Image.Resampling.LANCZOS)
            preview.alpha_composite(small, ((tile - 64) // 2, (tile - 64) // 2))
        sheet.alpha_composite(preview, ((i % cols) * tile, (i // cols) * tile))
    output.parent.mkdir(parents=True, exist_ok=True)
    sheet.save(output, "PNG", optimize=True)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUT)
    args = parser.parse_args()

    contract = json.loads(CONTRACT.read_text(encoding="utf-8"))
    biomes = [b["id"] for b in contract["biomes"]]
    slots = contract["slots"]
    failures: list[str] = []
    hashes: dict[str, str] = {}
    records: list[dict] = []

    for biome in biomes:
        manifest_path = SOURCE / biome / "candidate-manifest.json"
        if not manifest_path.is_file():
            failures.append(f"{biome}: missing candidate manifest")
            continue
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        if manifest.get("asset_count") != len(slots):
            failures.append(f"{biome}: manifest asset_count is not {len(slots)}")

        for slot in slots:
            path = SOURCE / biome / (slot["path"] + ".png")
            if not path.is_file():
                failures.append(f"{biome}/{slot['path']}: missing source")
                continue
            with Image.open(path) as raw:
                image = raw.convert("RGBA")
            if image.size != (MASTER, MASTER):
                failures.append(f"{biome}/{slot['path']}: expected 512x512")
                continue

            digest = sha256(path)
            previous = hashes.get(digest)
            if previous:
                failures.append(f"{biome}/{slot['path']}: exact duplicate of {previous}")
            else:
                hashes[digest] = f"{biome}/{slot['path']}"

            alpha = image.getchannel("A")
            extrema = alpha.getextrema()
            bbox = alpha.getbbox()
            coverage = alpha_coverage(image)
            stat = ImageStat.Stat(image.convert("RGB"))
            mean_rgb = tuple(stat.mean)
            record = {
                "biome": biome,
                "slot": slot["path"],
                "kind": slot["kind"],
                "path": str(path),
                "sha256": digest,
                "alphaCoverage": round(coverage, 4),
                "meanLuminance": round(luminance(mean_rgb), 2),
            }

            if slot["kind"] == "tile":
                if extrema != (255, 255):
                    failures.append(f"{biome}/{slot['path']}: floor is not fully opaque")
                edges = edge_rgb_mae(image)
                record.update(edges)
                # This is a deliberately conservative automated guard. Final seam
                # acceptance still comes from the generated 3x3 review sheet.
                if edges["leftRightMae"] > 48 or edges["topBottomMae"] > 48:
                    failures.append(
                        f"{biome}/{slot['path']}: edge discontinuity too large "
                        f"(LR={edges['leftRightMae']}, TB={edges['topBottomMae']})"
                    )
                if not 8 <= record["meanLuminance"] <= 155:
                    failures.append(
                        f"{biome}/{slot['path']}: floor luminance outside combat-safe range "
                        f"({record['meanLuminance']})"
                    )
            else:
                if bbox is None:
                    failures.append(f"{biome}/{slot['path']}: transparent asset has no silhouette")
                record["borderAlphaMax"] = border_alpha_max(image)
                if record["borderAlphaMax"] > 96:
                    failures.append(
                        f"{biome}/{slot['path']}: silhouette touches/crowds canvas border "
                        f"(alpha={record['borderAlphaMax']})"
                    )
                if coverage < .012:
                    failures.append(
                        f"{biome}/{slot['path']}: silhouette coverage too small ({coverage:.4f})"
                    )

            records.append(record)

    args.output.mkdir(parents=True, exist_ok=True)
    report = {
        "schema": 1,
        "biomes": len(biomes),
        "slotsPerBiome": len(slots),
        "expectedAssets": len(biomes) * len(slots),
        "checkedAssets": len(records),
        "failures": failures,
        "pass": not failures and len(records) == len(biomes) * len(slots),
        "records": records,
    }
    (args.output / "semantic-qa.json").write_text(
        json.dumps(report, indent=2) + "\n", encoding="utf-8"
    )
    make_review_sheet(records, args.output / "environment-review-sheet.png")

    print(
        f"environment semantic QA: {len(records)}/{report['expectedAssets']} checked; "
        f"{len(failures)} failure(s)"
    )
    for failure in failures:
        print(f"  - {failure}")
    return 0 if report["pass"] else 2


if __name__ == "__main__":
    raise SystemExit(main())

# CI retrigger after deterministic candidate regeneration.
