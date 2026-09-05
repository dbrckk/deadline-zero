#!/usr/bin/env python3
"""Import a manually generated PixelLab idle animation into Deadline Zero.

The tool accepts an animated GIF or a directory of PNG frames, normalizes it to
the actor cell from art_sources/final-sprite-layout.json, selects the contracted
idle frame count, preserves pixel scale by default, anchors frames to a stable
foot pivot, and writes production-named frames plus a preview/QA manifest.

Example:
  python -m pip install pillow
  python tools/import_pixellab_idle.py rex s art_sources/pixellab/rex_idle_s.gif

Output:
  build/pixellab_idle/survivor/rex/idle/s/idle_00.png ... idle_03.png
  build/pixellab_idle/rex_idle_s.gif
  build/pixellab_idle/rex_idle_s.json
"""
from __future__ import annotations

import argparse
import json
import math
from pathlib import Path

try:
    from PIL import Image, ImageSequence
except ImportError as exc:
    raise SystemExit("Pillow is required: python -m pip install pillow") from exc

ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
DEFAULT_OUT = ROOT / "build" / "pixellab_idle"
DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")


def load_actor(actor_id: str) -> tuple[dict, dict]:
    layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
    for actor in layout["actors"]:
        if actor["id"] == actor_id:
            return layout, actor
    raise SystemExit(f"Unknown actor: {actor_id}")


def load_frames(source: Path) -> tuple[list[Image.Image], list[int]]:
    if source.is_dir():
        paths = sorted(source.glob("*.png"))
        if not paths:
            raise SystemExit(f"No PNG frames in {source}")
        return [Image.open(p).convert("RGBA") for p in paths], [200] * len(paths)
    if not source.is_file():
        raise SystemExit(f"Source not found: {source}")
    im = Image.open(source)
    frames, durations = [], []
    for frame in ImageSequence.Iterator(im):
        frames.append(frame.convert("RGBA"))
        durations.append(int(frame.info.get("duration", im.info.get("duration", 200)) or 200))
    return frames, durations


def alpha_bbox(im: Image.Image):
    return im.getchannel("A").getbbox()


def evenly_spaced_indices(count: int, wanted: int) -> list[int]:
    if count < wanted:
        raise SystemExit(f"Need at least {wanted} source frames, got {count}")
    if count == wanted:
        return list(range(count))
    return [min(count - 1, math.floor(i * count / wanted)) for i in range(wanted)]


def normalize(frame: Image.Image, cell: tuple[int, int], foot_y: int | None) -> tuple[Image.Image, dict]:
    cw, ch = cell
    bbox = alpha_bbox(frame)
    if bbox is None:
        raise SystemExit("Encountered an empty source frame")
    left, top, right, bottom = bbox
    art = frame.crop(bbox)
    aw, ah = art.size
    if aw > cw - 4 or ah > ch - 4:
        scale = min((cw - 4) / aw, (ch - 4) / ah)
        nw, nh = max(1, round(aw * scale)), max(1, round(ah * scale))
        art = art.resize((nw, nh), Image.Resampling.NEAREST)
        aw, ah = art.size
    target_foot = foot_y if foot_y is not None else ch - 3
    x = (cw - aw) // 2
    y = target_foot - ah
    if y < 2:
        y = 2
    canvas = Image.new("RGBA", (cw, ch), (0, 0, 0, 0))
    canvas.alpha_composite(art, (x, y))
    out_bbox = alpha_bbox(canvas)
    assert out_bbox is not None
    return canvas, {
        "bbox": list(out_bbox),
        "footY": out_bbox[3] - 1,
        "centerX": (out_bbox[0] + out_bbox[2] - 1) / 2,
        "padding": min(out_bbox[0], out_bbox[1], cw - out_bbox[2], ch - out_bbox[3]),
    }


def main() -> int:
    ap = argparse.ArgumentParser(description=__doc__)
    ap.add_argument("actor")
    ap.add_argument("direction", choices=DIRECTIONS)
    ap.add_argument("source", type=Path)
    ap.add_argument("--output", type=Path, default=DEFAULT_OUT)
    ap.add_argument("--frame-ms", type=int, default=200,
                    help="Preview frame duration; default 200 ms")
    args = ap.parse_args()

    layout, actor = load_actor(args.actor)
    wanted = (layout["bossFrames"] if actor["profile"] == "boss" else layout["standardFrames"])["idle"]
    cell = tuple(actor["cell"])
    frames, durations = load_frames(args.source)
    chosen = evenly_spaced_indices(len(frames), wanted)

    normalized, metrics = [], []
    for idx in chosen:
        frame, metric = normalize(frames[idx], cell, cell[1] - 3)
        normalized.append(frame)
        metrics.append(metric)

    foot_values = [m["footY"] for m in metrics]
    center_values = [m["centerX"] for m in metrics]
    warnings = []
    if max(foot_values) - min(foot_values) > 1:
        warnings.append("foot pivot drift exceeds 1 px")
    if max(center_values) - min(center_values) > 3:
        warnings.append("horizontal alpha-center drift exceeds 3 px")
    if any(m["padding"] < 2 for m in metrics):
        warnings.append("visible art has less than 2 px transparent padding")

    out = args.output.resolve()
    frame_dir = out / actor["root"] / "idle" / args.direction
    frame_dir.mkdir(parents=True, exist_ok=True)
    for i, frame in enumerate(normalized):
        frame.save(frame_dir / f"idle_{i:02d}.png")

    preview = out / f"{args.actor}_idle_{args.direction}.gif"
    normalized[0].save(preview, save_all=True, append_images=normalized[1:],
                       duration=args.frame_ms, loop=0, disposal=2)
    manifest = {
        "schema": 1,
        "actor": args.actor,
        "direction": args.direction,
        "source": str(args.source),
        "sourceFrames": len(frames),
        "selectedSourceFrames": chosen,
        "outputFrames": wanted,
        "cell": list(cell),
        "metrics": metrics,
        "warnings": warnings,
        "preview": str(preview),
        "frameDirectory": str(frame_dir),
    }
    manifest_path = out / f"{args.actor}_idle_{args.direction}.json"
    manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")

    print(f"Imported {args.actor} idle {args.direction}: {len(frames)} -> {wanted} frames")
    print(f"Frames: {frame_dir}")
    print(f"Preview: {preview}")
    print(f"Manifest: {manifest_path}")
    if warnings:
        print("QA warnings:")
        for warning in warnings:
            print(f"  - {warning}")
        return 2
    print("QA: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
