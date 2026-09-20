#!/usr/bin/env python3
"""Install or replace one generated environment atlas page in assets/art/game.atlas."""

from __future__ import annotations

import argparse
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
DEFAULT_ATLAS = ROOT / "assets" / "art" / "game.atlas"


def is_page_line(line: str) -> bool:
    stripped = line.strip()
    if not stripped or ":" in stripped:
        return False
    return stripped.lower().endswith((".png", ".jpg", ".jpeg", ".webp"))


def page_blocks(text: str) -> tuple[list[str], list[list[str]]]:
    lines = text.splitlines()
    starts = [i for i, line in enumerate(lines) if is_page_line(line)]
    if not starts:
        return lines, []
    prefix = lines[: starts[0]]
    blocks: list[list[str]] = []
    for index, start in enumerate(starts):
        end = starts[index + 1] if index + 1 < len(starts) else len(lines)
        block = lines[start:end]
        while block and not block[-1].strip():
            block.pop()
        blocks.append(block)
    return prefix, blocks


def upsert_atlas_text(existing: str, fragment: str) -> str:
    fragment_lines = fragment.strip().splitlines()
    if not fragment_lines or not is_page_line(fragment_lines[0]):
        raise ValueError("fragment must begin with a texture page filename")
    page_name = fragment_lines[0].strip()

    prefix, blocks = page_blocks(existing)
    kept = [block for block in blocks if not block or block[0].strip() != page_name]
    kept.append(fragment_lines)

    out: list[str] = []
    if prefix:
        out.extend(prefix)
        if out and out[-1].strip():
            out.append("")
    for i, block in enumerate(kept):
        if i > 0 and out and out[-1].strip():
            out.append("")
        out.extend(block)
    return "\n".join(out).rstrip() + "\n"


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--atlas", type=Path, default=DEFAULT_ATLAS)
    parser.add_argument("--fragment", type=Path, required=True)
    parser.add_argument("--page", type=Path, required=True)
    args = parser.parse_args()

    if not args.atlas.is_file():
        parser.error(f"atlas does not exist: {args.atlas}")
    if not args.fragment.is_file():
        parser.error(f"fragment does not exist: {args.fragment}")
    if not args.page.is_file():
        parser.error(f"page does not exist: {args.page}")
    if args.page.suffix.lower() != ".png":
        parser.error("environment atlas page must be PNG")

    fragment = args.fragment.read_text(encoding="utf-8")
    first = fragment.strip().splitlines()[0].strip()
    if first != args.page.name:
        parser.error(f"fragment page {first!r} does not match PNG {args.page.name!r}")

    updated = upsert_atlas_text(args.atlas.read_text(encoding="utf-8"), fragment)
    destination = args.atlas.parent / args.page.name
    shutil.copy2(args.page, destination)
    args.atlas.write_text(updated, encoding="utf-8")
    print(f"installed {destination.name} into {args.atlas}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
