#!/usr/bin/env python3
"""Normalize Rex 512px renders into the 96x96 production cell contract.

A single fixed transform is used for every frame of a direction. This preserves
intra-animation motion and cross-action pose offsets instead of independently
recentering/grounding every frame, which can erase run bob, recoil and collapse
motion. The transform is still deterministic and uses one global scale across
all eight directions.
"""
from __future__ import annotations

import argparse
import json
from collections import defaultdict
from pathlib import Path

from PIL import Image, ImageDraw

EXPECTED = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
DIRS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")


def args():
    p = argparse.ArgumentParser()
    p.add_argument("--input", type=Path, required=True)
    p.add_argument("--output", type=Path, required=True)
    p.add_argument("--report", type=Path, required=True)
    p.add_argument("--contact-sheet", type=Path, required=True)
    return p.parse_args()


def bbox_alpha(im):
    return im.getchannel("A").getbbox()


def union_boxes(boxes):
    return (
        min(b[0] for b in boxes),
        min(b[1] for b in boxes),
        max(b[2] for b in boxes),
        max(b[3] for b in boxes),
    )


def main():
    a = args()
    a.output.mkdir(parents=True, exist_ok=True)

    expected_paths = []
    for anim, count in EXPECTED.items():
        for d in DIRS:
            for i in range(count):
                expected_paths.append((anim, d, i, a.input / anim / d / f"{anim}_{i:02d}.png"))

    missing = [str(p) for *_, p in expected_paths if not p.exists()]
    if missing:
        raise SystemExit(f"missing {len(missing)} frames")

    source = []
    boxes_by_direction = defaultdict(list)
    source_size = None
    for anim, d, i, p in expected_paths:
        im = Image.open(p).convert("RGBA")
        if source_size is None:
            source_size = im.size
        elif im.size != source_size:
            raise SystemExit(f"mixed source sizes: {p} is {im.size}, expected {source_size}")
        b = bbox_alpha(im)
        if not b:
            raise SystemExit(f"empty frame {p}")
        source.append((anim, d, i, im, b))
        boxes_by_direction[d].append(b)

    unions = {d: union_boxes(boxes_by_direction[d]) for d in DIRS}
    maxw = max(b[2] - b[0] for b in unions.values())
    maxh = max(b[3] - b[1] for b in unions.values())
    scale = min(82 / maxw, 88 / maxh)

    source_center_x = source_size[0] / 2.0
    transforms = {}
    for d, b in unions.items():
        uw, uh = b[2] - b[0], b[3] - b[1]
        nw = max(1, round(uw * scale))
        nh = max(1, round(uh * scale))
        # Preserve the renderer's world-centre projection horizontally instead
        # of centering an asymmetric cape/rifle silhouette.
        pivot_x_in_crop = source_center_x - b[0]
        x = round(48 - pivot_x_in_crop * scale)
        # Ground the whole direction union once. Individual frames are never
        # re-grounded, so their relative vertical movement survives.
        y = 92 - nh
        transforms[d] = {
            "union_bbox": list(b),
            "scaled_size": [nw, nh],
            "dest": [x, y],
            "pivot_source_x": source_center_x,
        }

    rows = []
    motion = defaultdict(list)
    for anim, d, i, im, _ in source:
        u = unions[d]
        t = transforms[d]
        crop = im.crop(u).resize(tuple(t["scaled_size"]), Image.Resampling.LANCZOS)
        cell = Image.new("RGBA", (96, 96), (0, 0, 0, 0))
        cell.alpha_composite(crop, tuple(t["dest"]))

        out = a.output / anim / d / f"{anim}_{i:02d}.png"
        out.parent.mkdir(parents=True, exist_ok=True)
        cell.save(out)

        bb = bbox_alpha(cell)
        if not bb:
            row = {"path": str(out.relative_to(a.output)), "bbox": None, "margin": -1, "width": 0, "height": 0}
        else:
            margin = min(bb[0], bb[1], 96 - bb[2], 96 - bb[3])
            row = {
                "path": str(out.relative_to(a.output)),
                "bbox": list(bb),
                "margin": margin,
                "width": bb[2] - bb[0],
                "height": bb[3] - bb[1],
            }
            motion[(anim, d)].append({
                "cx": (bb[0] + bb[2]) / 2.0,
                "cy": (bb[1] + bb[3]) / 2.0,
                "bottom": bb[3],
            })
        rows.append(row)

    bad = [r for r in rows if r["margin"] < 2 or r["width"] < 12 or r["height"] < 20]

    motion_ranges = {}
    for (anim, d), values in sorted(motion.items()):
        if not values:
            continue
        motion_ranges[f"{anim}/{d}"] = {
            "cx_range": max(v["cx"] for v in values) - min(v["cx"] for v in values),
            "cy_range": max(v["cy"] for v in values) - min(v["cy"] for v in values),
            "bottom_range": max(v["bottom"] for v in values) - min(v["bottom"] for v in values),
        }

    report = {
        "actor": "rex",
        "stage": "96px-normalization-v2",
        "normalization_mode": "fixed-transform-per-direction",
        "frame_count": len(rows),
        "expected": 232,
        "source_size": list(source_size),
        "scale": scale,
        "direction_transforms": transforms,
        "motion_ranges": motion_ranges,
        "bad_frames": bad,
        "pass": len(rows) == 232 and not bad,
    }
    a.report.parent.mkdir(parents=True, exist_ok=True)
    a.report.write_text(json.dumps(report, indent=2) + "\n")

    # Phone-scale sheet: one representative sample for each animation/direction.
    thumb = 96
    sheet = Image.new("RGBA", (8 * thumb, 5 * thumb), (28, 28, 32, 255))
    draw = ImageDraw.Draw(sheet)
    for r, (anim, count) in enumerate(EXPECTED.items()):
        idx = count // 2
        for c, d in enumerate(DIRS):
            im = Image.open(a.output / anim / d / f"{anim}_{idx:02d}.png").convert("RGBA")
            sheet.alpha_composite(im, (c * thumb, r * thumb))
            draw.text((c * thumb + 2, r * thumb + 2), f"{anim} {d}", fill=(255, 255, 255, 255))
    a.contact_sheet.parent.mkdir(parents=True, exist_ok=True)
    sheet.save(a.contact_sheet)

    print(json.dumps(report, indent=2))
    if not report["pass"]:
        raise SystemExit("96px QA failed")


if __name__ == "__main__":
    main()
