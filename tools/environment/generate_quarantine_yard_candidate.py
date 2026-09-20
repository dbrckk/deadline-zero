#!/usr/bin/env python3
"""Generate a deterministic Quarantine Yard environment candidate pack.

This is an authored procedural candidate, not final release art. It exists to move
M3 from zero environment sources to a reviewable, reproducible first-biome pack
without mislabeling generated references as final.
"""
from __future__ import annotations

import argparse
import json
import math
import random
from pathlib import Path

from PIL import Image, ImageChops, ImageDraw, ImageFilter

SIZE = 512
BG = (18, 29, 34, 255)
STEEL = (55, 72, 78, 255)
STEEL_DARK = (29, 41, 46, 255)
STEEL_LIGHT = (90, 108, 112, 255)
RED = (165, 42, 38, 255)
RED_BRIGHT = (224, 62, 48, 255)
CYAN = (76, 202, 218, 255)
DIRTY = (70, 66, 54, 255)

SLOTS = (
    "floor/concrete_a", "floor/concrete_b", "floor/concrete_c", "floor/hazard_a",
    "decal/crack_a", "decal/blood_a", "decal/scorch_a",
    "prop/barrier_a", "prop/debris_a", "prop/debris_b", "prop/wall_a",
    "prop/wall_b", "prop/crate_a", "prop/beacon_a",
)


def clamp(v: int) -> int:
    return 0 if v < 0 else 255 if v > 255 else v


def rgba_noise(base, strength: int, seed: int, *, periodic: bool = False) -> Image.Image:
    img = Image.new("RGBA", (SIZE, SIZE))
    px = img.load()
    rnd = random.Random(seed)
    phases = [rnd.random() * math.tau for _ in range(6)]
    for y in range(SIZE):
        for x in range(SIZE):
            if periodic:
                n = (
                    math.sin(math.tau * x / 64 + phases[0]) * 0.30
                    + math.cos(math.tau * y / 96 + phases[1]) * 0.28
                    + math.sin(math.tau * (x + y) / 128 + phases[2]) * 0.20
                    + math.cos(math.tau * (x - y) / 80 + phases[3]) * 0.16
                )
                n *= strength
            else:
                n = rnd.uniform(-strength, strength)
            px[x, y] = (
                clamp(int(base[0] + n)),
                clamp(int(base[1] + n)),
                clamp(int(base[2] + n)),
                base[3],
            )
    return img


def vignette(img: Image.Image, amount: float = .18) -> None:
    px = img.load()
    cx = cy = SIZE / 2
    maxd = math.hypot(cx, cy)
    for y in range(SIZE):
        for x in range(SIZE):
            r, g, b, a = px[x, y]
            d = math.hypot(x - cx, y - cy) / maxd
            f = 1.0 - amount * d * d
            px[x, y] = (int(r * f), int(g * f), int(b * f), a)


def add_panel_seams(img: Image.Image, spacing: int, alpha: int = 80) -> None:
    d = ImageDraw.Draw(img, "RGBA")
    for x in range(0, SIZE, spacing):
        d.line((x, 0, x, SIZE), fill=(6, 11, 13, alpha), width=3)
        d.line((x + 4, 0, x + 4, SIZE), fill=(105, 120, 122, alpha // 3), width=1)
    for y in range(0, SIZE, spacing):
        d.line((0, y, SIZE, y), fill=(6, 11, 13, alpha), width=3)
        d.line((0, y + 4, SIZE, y + 4), fill=(105, 120, 122, alpha // 3), width=1)


def add_scuffs(img: Image.Image, seed: int, count: int = 45) -> None:
    d = ImageDraw.Draw(img, "RGBA")
    rnd = random.Random(seed)
    for _ in range(count):
        x = rnd.randrange(SIZE)
        y = rnd.randrange(SIZE)
        length = rnd.randrange(8, 52)
        angle = rnd.random() * math.tau
        x2 = int(x + math.cos(angle) * length)
        y2 = int(y + math.sin(angle) * length)
        shade = rnd.choice(((8, 13, 15, 32), (120, 118, 96, 20), (145, 55, 43, 14)))
        d.line((x, y, x2, y2), fill=shade, width=rnd.choice((1, 1, 2)))


def floor_variant(seed: int, base=(43, 56, 61, 255), seam=128) -> Image.Image:
    img = rgba_noise(base, 15, seed, periodic=True)
    add_panel_seams(img, seam, 86)
    add_scuffs(img, seed + 100, 55)
    return img


def floor_concrete_a() -> Image.Image:
    img = floor_variant(11, (41, 53, 58, 255), 128)
    d = ImageDraw.Draw(img, "RGBA")
    for x, y in ((64, 64), (192, 64), (320, 64), (448, 64), (64, 320), (320, 320)):
        d.ellipse((x-4, y-4, x+4, y+4), fill=(12, 18, 20, 150))
        d.ellipse((x-2, y-2, x+2, y+2), fill=(116, 126, 124, 100))
    return img


def floor_concrete_b() -> Image.Image:
    img = floor_variant(17, (36, 48, 54, 255), 96)
    d = ImageDraw.Draw(img, "RGBA")
    for y in range(48, SIZE, 128):
        d.rectangle((0, y, SIZE, y+18), fill=(25, 38, 43, 105))
        d.line((0, y+4, SIZE, y+4), fill=(92, 107, 110, 45), width=2)
    return img


def floor_concrete_c() -> Image.Image:
    img = floor_variant(23, (47, 57, 59, 255), 160)
    d = ImageDraw.Draw(img, "RGBA")
    rnd = random.Random(23)
    for _ in range(18):
        x = rnd.randrange(SIZE)
        y = rnd.randrange(SIZE)
        rr = rnd.randrange(10, 34)
        d.ellipse((x-rr, y-rr//2, x+rr, y+rr//2), fill=(27, 35, 34, 24))
    return img


def floor_hazard() -> Image.Image:
    img = floor_variant(29, (32, 43, 47, 255), 128)
    overlay = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    d = ImageDraw.Draw(overlay, "RGBA")
    band = 32
    for k in range(-SIZE, SIZE * 2, band * 2):
        d.polygon([(k, 0), (k+band, 0), (k-SIZE+band, SIZE), (k-SIZE, SIZE)],
                  fill=(175, 45, 38, 160))
    overlay = overlay.filter(ImageFilter.GaussianBlur(.35))
    img = Image.alpha_composite(img, overlay)
    d = ImageDraw.Draw(img, "RGBA")
    d.rectangle((0, 0, SIZE-1, SIZE-1), outline=(9, 14, 16, 150), width=12)
    return img


def transparent() -> Image.Image:
    return Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))


def glow_layer(color, center, radii) -> Image.Image:
    layer = transparent()
    d = ImageDraw.Draw(layer, "RGBA")
    for radius, alpha in radii:
        d.ellipse((center[0]-radius, center[1]-radius, center[0]+radius, center[1]+radius),
                  fill=(*color[:3], alpha))
    return layer.filter(ImageFilter.GaussianBlur(10))


def crack() -> Image.Image:
    img = transparent()
    d = ImageDraw.Draw(img, "RGBA")
    rnd = random.Random(41)
    origin = (256, 260)
    for branch in range(11):
        pts = [origin]
        angle = branch * (math.tau / 11) + rnd.uniform(-.16, .16)
        length = rnd.randrange(85, 210)
        steps = rnd.randrange(5, 9)
        for s in range(1, steps + 1):
            r = length * s / steps
            pts.append((int(origin[0] + math.cos(angle) * r + rnd.uniform(-14, 14)),
                        int(origin[1] + math.sin(angle) * r + rnd.uniform(-14, 14))))
        d.line(pts, fill=(10, 15, 17, 210), width=6)
        d.line(pts, fill=(105, 119, 118, 70), width=2)
    return img.filter(ImageFilter.GaussianBlur(.4))


def blood() -> Image.Image:
    img = transparent()
    d = ImageDraw.Draw(img, "RGBA")
    rnd = random.Random(47)
    for _ in range(34):
        x = int(rnd.gauss(256, 72))
        y = int(rnd.gauss(256, 56))
        rx = rnd.randrange(10, 52)
        ry = rnd.randrange(6, 36)
        d.ellipse((x-rx, y-ry, x+rx, y+ry), fill=(88, 9, 13, rnd.randrange(70, 155)))
    for _ in range(20):
        x = rnd.randrange(100, 412)
        y = rnd.randrange(110, 402)
        r = rnd.randrange(3, 10)
        d.ellipse((x-r, y-r, x+r, y+r), fill=(130, 12, 18, rnd.randrange(80, 165)))
    return img.filter(ImageFilter.GaussianBlur(2.1))


def scorch() -> Image.Image:
    img = transparent()
    center = (256, 256)
    d = ImageDraw.Draw(img, "RGBA")
    for radius, alpha in ((170, 14), (135, 22), (105, 34), (72, 50), (42, 66)):
        d.ellipse((center[0]-radius, center[1]-radius*.72, center[0]+radius, center[1]+radius*.72),
                  fill=(8, 9, 9, alpha))
    rnd = random.Random(53)
    for _ in range(30):
        ang = rnd.random() * math.tau
        r = rnd.randrange(35, 165)
        x = int(center[0] + math.cos(ang) * r)
        y = int(center[1] + math.sin(ang) * r * .72)
        rr = rnd.randrange(6, 22)
        d.ellipse((x-rr, y-rr, x+rr, y+rr), fill=(20, 17, 14, rnd.randrange(18, 55)))
    return img.filter(ImageFilter.GaussianBlur(7))


def metallic_shadow(img: Image.Image, box, alpha=110):
    shadow = transparent()
    d = ImageDraw.Draw(shadow, "RGBA")
    x0,y0,x1,y1 = box
    d.rounded_rectangle((x0+15,y0+20,x1+18,y1+24), radius=24, fill=(0,0,0,alpha))
    return Image.alpha_composite(img, shadow.filter(ImageFilter.GaussianBlur(10)))


def barrier() -> Image.Image:
    img = transparent()
    img = metallic_shadow(img, (70, 170, 442, 340))
    d = ImageDraw.Draw(img, "RGBA")
    d.rounded_rectangle((72, 160, 440, 330), radius=28, fill=(37, 50, 55, 255), outline=(100, 116, 118, 255), width=8)
    d.rectangle((110, 190, 402, 295), fill=(56, 67, 70, 255), outline=(20, 28, 31, 255), width=5)
    for x in range(128, 390, 64):
        d.polygon([(x,196),(x+24,196),(x+62,289),(x+38,289)], fill=(164,44,39,220))
    for x in (92, 420):
        d.ellipse((x-12, 238, x+12, 262), fill=(18,24,26,255), outline=(126,136,132,220), width=3)
    return img


def debris(seed: int, warm=False) -> Image.Image:
    img = transparent()
    d = ImageDraw.Draw(img, "RGBA")
    rnd = random.Random(seed)
    pieces=[]
    for _ in range(18):
        cx=rnd.randrange(120,392); cy=rnd.randrange(125,390)
        rr=rnd.randrange(18,52)
        pts=[]
        for k in range(rnd.randrange(4,7)):
            a=k*math.tau/rnd.randrange(4,7)+rnd.uniform(-.25,.25)
            rad=rr*rnd.uniform(.55,1.2)
            pts.append((cx+math.cos(a)*rad,cy+math.sin(a)*rad))
        pieces.append(pts)
    shadow=transparent(); sd=ImageDraw.Draw(shadow,"RGBA")
    for pts in pieces:
        sd.polygon([(x+10,y+14) for x,y in pts],fill=(0,0,0,100))
    img=Image.alpha_composite(img,shadow.filter(ImageFilter.GaussianBlur(7)))
    d=ImageDraw.Draw(img,"RGBA")
    for i,pts in enumerate(pieces):
        base=(83,74,55,255) if warm else ((55,66,69,255) if i%2==0 else (74,78,72,255))
        d.polygon(pts,fill=base,outline=(24,31,33,230))
        if i%3==0:
            x,y=pts[0]; d.line((x,y,pts[-1][0],pts[-1][1]),fill=(141,59,48,130),width=4)
    return img


def wall(seed: int, red_panel=False) -> Image.Image:
    img=transparent()
    img=metallic_shadow(img,(60,120,452,385),125)
    d=ImageDraw.Draw(img,"RGBA")
    d.rounded_rectangle((58,112,454,374),radius=30,fill=(43,55,60,255),outline=(111,128,130,255),width=10)
    d.rectangle((88,144,424,340),fill=(29,40,44,255),outline=(71,87,91,255),width=5)
    for y in (160,248,328):
        d.line((96,y,416,y),fill=(101,111,110,100),width=3)
    for x in (92,420):
        for y in (148,338):
            d.ellipse((x-6,y-6,x+6,y+6),fill=(12,18,20,255),outline=(145,151,144,180),width=2)
    if red_panel:
        d.rounded_rectangle((150,180,362,300),radius=16,fill=(108,30,30,235),outline=(193,67,53,220),width=5)
        for x in range(170,345,42):
            d.rectangle((x,194,x+18,286),fill=(195,54,43,100))
    return img


def crate() -> Image.Image:
    img=transparent()
    img=metallic_shadow(img,(120,110,392,390),125)
    d=ImageDraw.Draw(img,"RGBA")
    d.rounded_rectangle((116,106,396,384),radius=24,fill=(50,62,65,255),outline=(112,127,128,255),width=9)
    d.rectangle((145,136,367,354),fill=(35,46,49,255),outline=(80,93,95,255),width=5)
    d.line((150,142,362,348),fill=(116,129,126,150),width=10)
    d.line((362,142,150,348),fill=(116,129,126,150),width=10)
    d.rectangle((211,214,301,276),fill=(119,33,32,230),outline=(196,62,49,210),width=4)
    return img


def beacon() -> Image.Image:
    img=transparent()
    img=Image.alpha_composite(img,glow_layer(CYAN,(256,245),((120,18),(80,28),(48,48))))
    d=ImageDraw.Draw(img,"RGBA")
    d.ellipse((142,132,370,360),fill=(21,30,33,230),outline=(86,103,105,255),width=10)
    d.ellipse((174,164,338,328),fill=(37,51,55,255),outline=(116,133,132,220),width=6)
    d.ellipse((208,198,304,294),fill=(40,126,136,255),outline=(111,236,244,255),width=5)
    d.ellipse((228,218,284,274),fill=(145,245,248,250))
    for a in range(0,360,45):
        rad=math.radians(a)
        x=256+math.cos(rad)*94; y=246+math.sin(rad)*94
        d.ellipse((x-8,y-8,x+8,y+8),fill=(12,18,20,255),outline=(139,146,139,180),width=2)
    return img


GENERATORS = {
    "floor/concrete_a": floor_concrete_a,
    "floor/concrete_b": floor_concrete_b,
    "floor/concrete_c": floor_concrete_c,
    "floor/hazard_a": floor_hazard,
    "decal/crack_a": crack,
    "decal/blood_a": blood,
    "decal/scorch_a": scorch,
    "prop/barrier_a": barrier,
    "prop/debris_a": lambda: debris(61, False),
    "prop/debris_b": lambda: debris(67, True),
    "prop/wall_a": lambda: wall(71, False),
    "prop/wall_b": lambda: wall(73, True),
    "prop/crate_a": crate,
    "prop/beacon_a": beacon,
}


def main() -> int:
    p=argparse.ArgumentParser(description=__doc__)
    p.add_argument("--output",type=Path,default=Path("art_sources/environment/quarantine_yard"))
    p.add_argument("--manifest",type=Path,default=Path("art_sources/environment/quarantine_yard/candidate-manifest.json"))
    args=p.parse_args()
    args.output.mkdir(parents=True,exist_ok=True)
    produced=[]
    for slot in SLOTS:
        path=args.output/(slot+".png")
        path.parent.mkdir(parents=True,exist_ok=True)
        img=GENERATORS[slot]().convert("RGBA")
        if slot.startswith("floor/"):
            # Contract requires fully opaque floors.
            alpha=Image.new("L",img.size,255)
            img.putalpha(alpha)
        img.save(path,"PNG",optimize=True)
        produced.append(str(path).replace("\\","/"))
    manifest={
        "schema":1,
        "biome":"quarantine_yard",
        "stage":"procedural-authored-candidate",
        "production_ready":False,
        "visual_qa_pass":False,
        "generator":"tools/environment/generate_quarantine_yard_candidate.py",
        "master_size":[512,512],
        "asset_count":len(produced),
        "assets":produced,
        "notes":"Deterministic candidate pack. Must not be promoted as FINAL without premium visual QA."
    }
    args.manifest.parent.mkdir(parents=True,exist_ok=True)
    args.manifest.write_text(json.dumps(manifest,indent=2)+"\n",encoding="utf-8")
    print(f"generated {len(produced)} Quarantine Yard candidate masters")
    return 0


if __name__=="__main__":
    raise SystemExit(main())
