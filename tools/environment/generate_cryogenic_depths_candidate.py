#!/usr/bin/env python3
"""Generate deterministic Cryogenic Depths environment candidate masters."""
from __future__ import annotations
import argparse, json, math, random
from pathlib import Path
from PIL import Image, ImageChops, ImageDraw, ImageEnhance, ImageFilter
from candidate_utils import make_tileable_edges

SIZE=512
SLOTS=("floor/concrete_a","floor/concrete_b","floor/concrete_c","floor/hazard_a",
"decal/crack_a","decal/blood_a","decal/scorch_a","prop/barrier_a","prop/debris_a",
"prop/debris_b","prop/wall_a","prop/wall_b","prop/crate_a","prop/beacon_a")
ABYSS=(8,22,31,255); STEEL=(34,58,67,255); STEEL2=(52,86,96,255)
TEAL=(45,182,185,255); CYAN=(93,222,231,255); ICE=(180,246,248,255); BLOOD=(64,12,24,255)

def clamp(v): return 0 if v<0 else 255 if v>255 else int(v)
def transparent(): return Image.new("RGBA",(SIZE,SIZE),(0,0,0,0))
def noise(base,seed,strength=9):
    rnd=random.Random(seed); im=Image.new("RGBA",(SIZE,SIZE)); px=im.load()
    p1,p2=rnd.random()*math.tau,rnd.random()*math.tau
    for y in range(SIZE):
        for x in range(SIZE):
            n=(math.sin(x*.038+p1)+math.cos(y*.043+p2))*strength*.35+rnd.uniform(-strength,strength)
            px[x,y]=(clamp(base[0]+n),clamp(base[1]+n),clamp(base[2]+n),255)
    return im

def seams(im,spacing=128):
    d=ImageDraw.Draw(im,"RGBA")
    for x in range(0,SIZE,spacing):
        d.line((x,0,x,SIZE),fill=(2,10,15,205),width=5)
        d.line((x+5,0,x+5,SIZE),fill=(*CYAN[:3],28),width=1)
    for y in range(0,SIZE,spacing):
        d.line((0,y,SIZE,y),fill=(2,10,15,205),width=5)
        d.line((0,y+5,SIZE,y+5),fill=(*CYAN[:3],28),width=1)

def frost(im,seed,count=30):
    overlay=transparent(); d=ImageDraw.Draw(overlay,"RGBA"); rnd=random.Random(seed)
    for _ in range(count):
        x,y=rnd.randrange(SIZE),rnd.randrange(SIZE)
        rx,ry=rnd.randrange(20,72),rnd.randrange(6,20)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(*CYAN[:3],rnd.randrange(9,26)))
    im.alpha_composite(overlay)

def finish(im,slot):
    im=im.convert("RGBA")
    if slot.startswith("floor/"):
        im=ImageEnhance.Contrast(im).enhance(1.10)
        im=ImageEnhance.Sharpness(im).enhance(1.18)
        im.putalpha(Image.new("L",im.size,255)); return make_tileable_edges(im)
    a=im.getchannel("A")
    sh=Image.new("RGBA",im.size,(0,0,0,0))
    sm=a.filter(ImageFilter.GaussianBlur(13))
    shifted=Image.new("L",im.size,0); shifted.paste(sm,(11,15))
    sh.putalpha(shifted.point(lambda p:int(p*.34)))
    out=Image.alpha_composite(sh,im)
    edge=ImageChops.subtract(a,a.filter(ImageFilter.MinFilter(7)))
    hi=Image.new("RGBA",im.size,(160,244,246,0))
    hi.putalpha(edge.point(lambda p:int(p*.27)))
    return ImageEnhance.Sharpness(Image.alpha_composite(out,hi)).enhance(1.24)

def floor_a():
    im=noise((24,45,54,255),401); seams(im,128); frost(im,402,26)
    d=ImageDraw.Draw(im,"RGBA")
    for x,y in ((64,64),(192,64),(320,64),(448,64),(64,320),(320,320)):
        d.ellipse((x-4,y-4,x+4,y+4),fill=(3,15,20,220),outline=(*CYAN[:3],70),width=2)
    return im
def floor_b():
    im=noise((17,38,48,255),411); seams(im,96); frost(im,412,20)
    d=ImageDraw.Draw(im,"RGBA")
    for y in range(44,SIZE,128):
        d.rectangle((0,y,SIZE,y+16),fill=(4,20,27,110))
        d.line((0,y+3,SIZE,y+3),fill=(*TEAL[:3],48),width=2)
    return im
def floor_c():
    im=noise((29,53,62,255),421); seams(im,160); frost(im,422,38); return im
def floor_hazard():
    im=noise((13,31,41,255),431); seams(im,128); d=ImageDraw.Draw(im,"RGBA")
    for k in range(-SIZE,SIZE*2,88):
        d.polygon([(k,0),(k+24,0),(k-SIZE+24,SIZE),(k-SIZE,SIZE)],fill=(*TEAL[:3],80))
    d.line((0,128,SIZE,128),fill=(*CYAN[:3],48),width=2)
    d.line((0,384,SIZE,384),fill=(*CYAN[:3],48),width=2)
    return im

def crack():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(441); o=(256,256)
    for b in range(14):
        pts=[o]; ang=b*math.tau/14+rnd.uniform(-.15,.15)
        for s in range(1,rnd.randrange(5,9)):
            r=s*rnd.randrange(17,28)
            pts.append((o[0]+math.cos(ang)*r+rnd.uniform(-8,8),o[1]+math.sin(ang)*r+rnd.uniform(-8,8)))
        d.line(pts,fill=(5,21,28,220),width=9)
        d.line(pts,fill=(*TEAL[:3],175),width=4)
        d.line(pts,fill=(*ICE[:3],115),width=1)
    return im.filter(ImageFilter.GaussianBlur(.35))
def blood():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(451)
    for _ in range(25):
        x,y=int(rnd.gauss(256,70)),int(rnd.gauss(256,54))
        rx,ry=rnd.randrange(8,42),rnd.randrange(5,26)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(*BLOOD[:3],rnd.randrange(65,130)))
    return im.filter(ImageFilter.GaussianBlur(1.8))
def scorch():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA")
    for r,a in ((176,10),(136,18),(100,28),(68,40),(40,54)):
        d.ellipse((256-r,256-r*.68,256+r,256+r*.68),fill=(3,15,21,a))
    d.ellipse((222,224,290,288),fill=(*TEAL[:3],20))
    return im.filter(ImageFilter.GaussianBlur(8))

def framed(box,fill,outline=STEEL2,radius=22):
    im=transparent(); x0,y0,x1,y1=box
    sh=transparent(); sd=ImageDraw.Draw(sh,"RGBA")
    sd.rounded_rectangle((x0+12,y0+16,x1+16,y1+20),radius=radius,fill=(0,0,0,120))
    im=Image.alpha_composite(im,sh.filter(ImageFilter.GaussianBlur(10)))
    d=ImageDraw.Draw(im,"RGBA")
    d.rounded_rectangle(box,radius=radius,fill=fill,outline=outline,width=7)
    return im
def barrier():
    im=framed((70,166,442,334),(31,55,63,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((106,194,406,302),fill=(11,31,40,255),outline=(67,104,112,255),width=4)
    for x in range(126,390,66):
        d.polygon([(x,202),(x+20,202),(x+56,294),(x+36,294)],fill=(*TEAL[:3],125))
    d.line((116,246,396,246),fill=(*CYAN[:3],95),width=2); return im
def debris(seed,crystal=False):
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(seed)
    for _ in range(18):
        cx,cy=rnd.randrange(110,402),rnd.randrange(120,392); r=rnd.randrange(14,42)
        pts=[(cx+math.cos(k*math.tau/6)*r*rnd.uniform(.6,1.2),cy+math.sin(k*math.tau/6)*r*rnd.uniform(.6,1.2)) for k in range(6)]
        d.polygon(pts,fill=((31,64,72,255) if crystal else (44,66,72,255)),outline=(5,22,28,230))
        if crystal and rnd.random()<.55:
            x,y=pts[0]; d.line((x,y,cx,cy),fill=(*CYAN[:3],135),width=3)
    return im
def wall(seed,window=False):
    im=framed((58,112,454,378),(35,58,66,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((90,145,422,342),fill=(10,29,37,255),outline=(70,105,113,255),width=4)
    for y in (166,248,326): d.line((98,y,414,y),fill=(133,190,194,54),width=2)
    if window:
        d.rounded_rectangle((152,178,360,308),radius=15,fill=(18,72,80,235),outline=TEAL,width=5)
        d.line((166,244,346,244),fill=ICE,width=3)
    return im
def crate():
    im=framed((118,112,394,386),(45,66,71,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((146,142,366,354),fill=(17,36,42,255),outline=(81,116,122,255),width=4)
    d.line((152,148,360,348),fill=(105,150,154,135),width=8)
    d.line((360,148,152,348),fill=(105,150,154,135),width=8)
    d.rectangle((218,217,294,277),fill=(22,84,91,230),outline=TEAL,width=3); return im
def beacon():
    im=transparent(); glow=transparent(); gd=ImageDraw.Draw(glow,"RGBA")
    for r,a in ((122,12),(82,25),(46,48)):
        gd.ellipse((256-r,246-r,256+r,246+r),fill=(*CYAN[:3],a))
    im=Image.alpha_composite(im,glow.filter(ImageFilter.GaussianBlur(10)))
    d=ImageDraw.Draw(im,"RGBA")
    d.ellipse((145,135,367,357),fill=(24,49,57,245),outline=(83,122,130,255),width=8)
    d.ellipse((184,174,328,318),fill=(20,78,85,255),outline=TEAL,width=5)
    d.ellipse((220,210,292,282),fill=(83,194,198,255),outline=ICE,width=4)
    return im

GEN={"floor/concrete_a":floor_a,"floor/concrete_b":floor_b,"floor/concrete_c":floor_c,"floor/hazard_a":floor_hazard,
"decal/crack_a":crack,"decal/blood_a":blood,"decal/scorch_a":scorch,
"prop/barrier_a":barrier,"prop/debris_a":lambda:debris(461,False),"prop/debris_b":lambda:debris(467,True),
"prop/wall_a":lambda:wall(471,False),"prop/wall_b":lambda:wall(473,True),"prop/crate_a":crate,"prop/beacon_a":beacon}

def main():
    p=argparse.ArgumentParser()
    p.add_argument("--output",type=Path,default=Path("art_sources/environment/cryogenic_depths"))
    p.add_argument("--manifest",type=Path,default=Path("art_sources/environment/cryogenic_depths/candidate-manifest.json"))
    a=p.parse_args(); a.output.mkdir(parents=True,exist_ok=True); assets=[]
    for slot in SLOTS:
        path=a.output/(slot+".png"); path.parent.mkdir(parents=True,exist_ok=True)
        finish(GEN[slot](),slot).save(path,"PNG",optimize=True)
        assets.append(str(path).replace("\\","/"))
    m={"schema":1,"biome":"cryogenic_depths","stage":"procedural-authored-candidate-v1","production_ready":False,
       "visual_qa_pass":False,"generator":"tools/environment/generate_cryogenic_depths_candidate.py",
       "master_size":[512,512],"asset_count":14,"assets":assets,
       "notes":"Abyssal blue / frozen teal deep-cryo candidate. Not FINAL until premium visual QA."}
    a.manifest.write_text(json.dumps(m,indent=2)+"\n",encoding="utf-8")
    print("generated 14 Cryogenic Depths candidate masters")
    return 0

if __name__=="__main__": raise SystemExit(main())

# Deterministic by design: reruns must not mutate approved candidate masters.

# Regeneration trigger: seam-safe floor masters.
