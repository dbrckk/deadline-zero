"""Shared deterministic finishing helpers for environment candidate generators."""
from __future__ import annotations

from PIL import Image


def make_tileable_edges(image: Image.Image, band: int = 24) -> Image.Image:
    """Mirror-average opposite edge bands so repeated floor tiles join cleanly.

    The operation preserves the interior material treatment while making both
    sides of each seam share the same pixel band. This is deterministic and is
    intentionally applied only to opaque floor candidates.
    """
    out = image.convert("RGBA").copy()
    width, height = out.size
    if width < band * 2 or height < band * 2:
        raise ValueError("image is too small for requested tileable edge band")

    px = out.load()

    # Pair left/right edge bands. Repeated tiles then meet with mirrored,
    # identical neighborhoods rather than merely matching the outermost pixel.
    for y in range(height):
        for i in range(band):
            left = px[i, y]
            right = px[width - 1 - i, y]
            avg = tuple((left[c] + right[c]) // 2 for c in range(4))
            px[i, y] = avg
            px[width - 1 - i, y] = avg

    # Pair top/bottom after horizontal reconciliation so corners also agree.
    for x in range(width):
        for i in range(band):
            top = px[x, i]
            bottom = px[x, height - 1 - i]
            avg = tuple((top[c] + bottom[c]) // 2 for c in range(4))
            px[x, i] = avg
            px[x, height - 1 - i] = avg

    return out
