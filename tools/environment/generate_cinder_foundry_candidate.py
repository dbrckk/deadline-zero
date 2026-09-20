#!/usr/bin/env python3
"""Generate deterministic Cinder Foundry environment candidate masters.

These are authored procedural candidates, not final release art.
"""
from __future__ import annotations

import argparse, json, math, random
from pathlib import Path
from PIL import Image, ImageChops, ImageDraw, ImageEnhance, ImageFilter
from candidate_utils import make_tileable_edges

SIZE=512
SLOTS=(
    "floor/concrete_a","floor/concrete_b","floor/concrete_c","floor/hazard_a",
    "decal/crack_a","decal/blood_a","decal/scorch_a",
    "prop/barrier_a","prop/debris_a","prop/debris_b","prop/wall_a",
    "prop/wall_b","prop/crate_a","prop/beacon_a",
)
BLACK=(20,22,23,255); STEEL=(48,49,47,255); STEEL2=(70,68,62,255)
CERAMIC=(108,94,78,255); ASH=(64,60,54,255); ORANGE=(238,96,22,255)
HOT=(255,151,42,255); RED=(152,42,25,255)

def transparent(): return Image.new("RGBA",(SIZE,SIZE),(0,0,0,0))
def clamp(v): return 0 if v<0 else 255 if v>255 else int(v)

def noise(base,seed,strength=12):
    rnd=random.Random(seed); img=Image.new("RGBA",(SIZE,SIZE)); px=img.load()
    for y in range(SIZE):
        for x in range(SIZE):
            n=(math.sin(x*.075+rnd.random()*.02)+math.cos(y*.061))*strength*.32+rnd.uniform(-strength,strength)
            px[x,y]=(clamp(base[0]+n),clamp(base[1]+n),clamp(base[2]+n),255)
    return img

def seams(img,spacing=128,hot=False):
    d=ImageDraw.Draw(img,"RGBA")
    for x in range(0,SIZE,spacing):
        d.line((x,0,x,SIZE),fill=(7,8,8,190),width=5)
        d.line((x+5,0,x+5,SIZE),fill=(120,105,84,50),width=2)
        if hot and x:
            d.line((x-2,0,x-2,SIZE),fill=(*ORANGE[:3],45),width=2)
    for y in range(0,SIZE,spacing):
        d.line((0,y,SIZE,y),fill=(7,8,8,190),width=5)
        d.line((0,y+5,SIZE,y+5),fill=(120,105,84,50),width=2)

def scuffs(img,seed,count=60):
    d=ImageDraw.Draw(img,"RGBA"); rnd=random.Random(seed)
    for _ in range(count):
        x,y=rnd.randrange(SIZE),rnd.randrange(SIZE); l=rnd.randrange(8,55); a=rnd.random()*math.tau
        d.line((x,y,x+math.cos(a)*l,y+math.sin(a)*l),fill=rnd.choice(((5,5,5,40),(130,110,85,24),(220,87,24,18))),width=rnd.choice((1,2,2)))

def finish(img,slot,seed):
    img=img.convert("RGBA")
    if slot.startswith("floor/"):
        img=ImageEnhance.Contrast(img).enhance(1.12)
        img=ImageEnhance.Sharpness(img).enhance(1.22)
        img.putalpha(Image.new("L",img.size,255))
        return make_tileable_edges(img)
    alpha=img.getchannel("A")
    shadow=Image.new("RGBA",img.size,(0,0,0,0)); sm=alpha.filter(ImageFilter.GaussianBlur(12))
    shifted=Image.new("L",img.size,0); shifted.paste(sm,(10,14)); shadow.putalpha(shifted.point(lambda p:int(p*.36)))
    out=Image.alpha_composite(shadow,img)
    edge=ImageChops.subtract(alpha,alpha.filter(ImageFilter.MinFilter(7)))
    hi=Image.new("RGBA",img.size,(208,169,115,0)); hi.putalpha(edge.point(lambda p:int(p*.30)))
    out=Image.alpha_composite(out,hi)
    return ImageEnhance.Sharpness(ImageEnhance.Contrast(out).enhance(1.08)).enhance(1.25)

def floor_a():
    img=noise((42,43,40,255),101,13); seams(img,128); scuffs(img,102)
    d=ImageDraw.Draw(img,"RGBA")
    for x,y in ((64,64),(192,64),(320,64),(448,64),(64,320),(320,320)):
        d.ellipse((x-5,y-5,x+5,y+5),fill=(8,8,8,220),outline=(128,112,84,120),width=2)
    return img

def floor_b():
    img=noise((34,35,34,255),111,10); seams(img,96)
    d=ImageDraw.Draw(img,"RGBA")
    for y in range(32,SIZE,96):
        d.rectangle((0,y,SIZE,y+20),fill=(20,20,19,115))
        d.line((0,y+3,SIZE,y+3),fill=(155,91,45,45),width=2)
    scuffs(img,112,45); return img

def floor_c():
    img=noise((53,49,43,255),121,14); seams(img,160)
    d=ImageDraw.Draw(img,"RGBA"); rnd=random.Random(122)
    for _ in range(24):
        x,y=rnd.randrange(SIZE),rnd.randrange(SIZE); r=rnd.randrange(8,28)
        d.ellipse((x-r,y-r//2,x+r,y+r//2),fill=(15,14,13,rnd.randrange(18,42)))
    return img

def floor_hazard():
    img=noise((28,29,28,255),131,10); seams(img,128)
    d=ImageDraw.Draw(img,"RGBA")
    for k in range(-SIZE,SIZE*2,64):
        d.polygon([(k,0),(k+28,0),(k-SIZE+28,SIZE),(k-SIZE,SIZE)],fill=(173,58,24,145))
    for y in (128,384):
        d.line((0,y,SIZE,y),fill=(*ORANGE[:3],85),width=4)
    return img

def lava_crack():
    img=transparent(); d=ImageDraw.Draw(img,"RGBA"); rnd=random.Random(141); o=(256,256)
    for b in range(10):
        pts=[o]; a=b*math.tau/10+rnd.uniform(-.18,.18)
        for s in range(1,rnd.randrange(5,9)):
            r=s*rnd.randrange(22,32); pts.append((o[0]+math.cos(a)*r+rnd.uniform(-12,12),o[1]+math.sin(a)*r+rnd.uniform(-12,12)))
        d.line(pts,fill=(12,9,7,230),width=12); d.line(pts,fill=(*ORANGE[:3],170),width=5); d.line(pts,fill=(*HOT[:3],150),width=2)
    return img.filter(ImageFilter.GaussianBlur(.5))

def blood():
    img=transparent(); d=ImageDraw.Draw(img,"RGBA"); rnd=random.Random(151)
    for _ in range(28):
        x,y=int(rnd.gauss(256,74)),int(rnd.gauss(256,56)); rx,ry=rnd.randrange(8,46),rnd.randrange(5,28)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(74,8,8,rnd.randrange(75,150)))
    return img.filter(ImageFilter.GaussianBlur(1.8))

def scorch():
    img=transparent(); d=ImageDraw.Draw(img,"RGBA")
    for r,a in ((180,12),(140,22),(105,36),(72,56),(42,78)):
        d.ellipse((256-r,256-r*.65,256+r,256+r*.65),fill=(10,8,6,a))
    d.ellipse((220,230,292,282),fill=(*ORANGE[:3],26))
    return img.filter(ImageFilter.GaussianBlur(8))

def shadowed(box,fill,outline=(150,112,74,255),radius=24):
    img=transparent(); x0,y0,x1,y1=box
    sh=transparent(); sd=ImageDraw.Draw(sh,"RGBA"); sd.rounded_rectangle((x0+15,y0+18,x1+18,y1+22),radius=radius,fill=(0,0,0,125))
    img=Image.alpha_composite(img,sh.filter(ImageFilter.GaussianBlur(10)))
    d=ImageDraw.Draw(img,"RGBA"); d.rounded_rectangle(box,radius=radius,fill=fill,outline=outline,width=8)
    return img

def barrier():
    img=shadowed((70,164,442,334),(49,45,40,255)); d=ImageDraw.Draw(img,"RGBA")
    d.rectangle((105,194,407,300),fill=(85,70,55,255),outline=(28,26,23,255),width=5)
    for x in range(118,390,70):
        d.polygon([(x,199),(x+22,199),(x+58,294),(x+36,294)],fill=(203,69,26,210))
    return img

def debris(seed,slag=False):
    img=transparent(); d=ImageDraw.Draw(img,"RGBA"); rnd=random.Random(seed)
    for _ in range(20):
        cx,cy=rnd.randrange(110,402),rnd.randrange(120,392); r=rnd.randrange(14,44)
        pts=[(cx+math.cos(k*math.tau/5)*r*rnd.uniform(.6,1.2),cy+math.sin(k*math.tau/5)*r*rnd.uniform(.6,1.2)) for k in range(5)]
        d.polygon(pts,fill=((55,48,40,255) if slag else (65,64,59,255)),outline=(18,17,15,230))
        if slag and rnd.random()<.45:
            x,y=pts[0]; d.ellipse((x-5,y-5,x+5,y+5),fill=(*ORANGE[:3],160))
    return img

def wall(seed,hotpanel=False):
    img=shadowed((56,112,456,378),(49,46,42,255)); d=ImageDraw.Draw(img,"RGBA")
    d.rectangle((90,145,422,343),fill=(27,27,26,255),outline=(97,84,67,255),width=5)
    for y in (168,248,326): d.line((98,y,414,y),fill=(135,112,80,90),width=3)
    if hotpanel:
        d.rounded_rectangle((150,180,362,306),radius=16,fill=(81,35,22,245),outline=ORANGE,width=5)
        d.line((165,244,347,244),fill=HOT,width=4)
    return img

def crate():
    img=shadowed((116,110,396,386),(63,58,50,255)); d=ImageDraw.Draw(img,"RGBA")
    d.rectangle((145,140,367,354),fill=(38,37,34,255),outline=(105,91,70,255),width=5)
    d.line((151,145,361,349),fill=(135,107,72,160),width=10); d.line((361,145,151,349),fill=(135,107,72,160),width=10)
    d.rectangle((215,215,297,279),fill=(104,39,22,230),outline=ORANGE,width=4); return img

def beacon():
    img=transparent()
    glow=transparent(); gd=ImageDraw.Draw(glow,"RGBA")
    for r,a in ((120,16),(80,30),(46,54)): gd.ellipse((256-r,246-r,256+r,246+r),fill=(*ORANGE[:3],a))
    img=Image.alpha_composite(img,glow.filter(ImageFilter.GaussianBlur(10)))
    d=ImageDraw.Draw(img,"RGBA"); d.ellipse((145,135,367,357),fill=(28,27,25,240),outline=(118,94,68,255),width=9)
    d.ellipse((178,168,334,324),fill=(58,51,42,255),outline=(155,111,67,220),width=6)
    d.ellipse((208,198,304,294),fill=(151,61,22,255),outline=ORANGE,width=5); d.ellipse((229,219,283,273),fill=HOT)
    return img

GENERATORS={
"floor/concrete_a":floor_a,"floor/concrete_b":floor_b,"floor/concrete_c":floor_c,"floor/hazard_a":floor_hazard,
"decal/crack_a":lava_crack,"decal/blood_a":blood,"decal/scorch_a":scorch,
"prop/barrier_a":barrier,"prop/debris_a":lambda:debris(161,False),"prop/debris_b":lambda:debris(167,True),
"prop/wall_a":lambda:wall(171,False),"prop/wall_b":lambda:wall(173,True),"prop/crate_a":crate,"prop/beacon_a":beacon,
}

def main():
    p=argparse.ArgumentParser(); p.add_argument("--output",type=Path,default=Path("art_sources/environment/cinder_foundry")); p.add_argument("--manifest",type=Path,default=Path("art_sources/environment/cinder_foundry/candidate-manifest.json")); a=p.parse_args()
    a.output.mkdir(parents=True,exist_ok=True); assets=[]
    for i,slot in enumerate(SLOTS):
        path=a.output/(slot+".png"); path.parent.mkdir(parents=True,exist_ok=True)
        img=finish(GENERATORS[slot](),slot,2000+i*101); img.save(path,"PNG",optimize=True); assets.append(str(path).replace("\\","/"))
    manifest={"schema":1,"biome":"cinder_foundry","stage":"procedural-authored-candidate-v1","production_ready":False,"visual_qa_pass":False,"generator":"tools/environment/generate_cinder_foundry_candidate.py","master_size":[512,512],"asset_count":14,"assets":assets,"notes":"Distinct blackened-steel/ceramic/slag candidate. Not FINAL until premium visual QA."}
    a.manifest.write_text(json.dumps(manifest,indent=2)+"\n",encoding="utf-8")
    print("generated 14 Cinder Foundry candidate masters")
    return 0
if __name__=="__main__": raise SystemExit(main())

# Regeneration trigger: seam-safe floor masters.
