#!/usr/bin/env python3
"""Generate deterministic Null Sector environment candidate masters.

Authored procedural candidate only; never marks assets production-ready.
"""
from __future__ import annotations
import argparse, json, math, random
from pathlib import Path
from PIL import Image, ImageChops, ImageDraw, ImageEnhance, ImageFilter

SIZE=512
SLOTS=(
"floor/concrete_a","floor/concrete_b","floor/concrete_c","floor/hazard_a",
"decal/crack_a","decal/blood_a","decal/scorch_a",
"prop/barrier_a","prop/debris_a","prop/debris_b","prop/wall_a",
"prop/wall_b","prop/crate_a","prop/beacon_a")
BLACK=(11,13,18,255); ALLOY=(28,31,39,255); ALLOY2=(40,44,55,255)
VIOLET=(137,72,220,255); VIOLET2=(83,42,145,255); CYAN=(63,202,226,255); PALE=(130,149,170,255)

def clamp(v): return 0 if v<0 else 255 if v>255 else int(v)
def transparent(): return Image.new("RGBA",(SIZE,SIZE),(0,0,0,0))
def noise(base,seed,strength=9):
    rnd=random.Random(seed); im=Image.new("RGBA",(SIZE,SIZE)); px=im.load()
    p1,p2=rnd.random()*6.28,rnd.random()*6.28
    for y in range(SIZE):
        for x in range(SIZE):
            n=(math.sin(x*.049+p1)+math.cos(y*.057+p2))*strength*.28+rnd.uniform(-strength,strength)
            px[x,y]=(clamp(base[0]+n),clamp(base[1]+n),clamp(base[2]+n),255)
    return im

def panel(img,spacing=128,diag=False):
    d=ImageDraw.Draw(img,"RGBA")
    for x in range(0,SIZE,spacing):
        d.line((x,0,x,SIZE),fill=(3,5,9,180),width=4)
        d.line((x+4,0,x+4,SIZE),fill=(82,95,120,34),width=1)
    for y in range(0,SIZE,spacing):
        d.line((0,y,SIZE,y),fill=(3,5,9,180),width=4)
        d.line((0,y+4,SIZE,y+4),fill=(82,95,120,34),width=1)
    if diag:
        for k in range(-SIZE,SIZE*2,180):
            d.line((k,0,k-SIZE,SIZE),fill=(*VIOLET2[:3],26),width=2)

def finish(img,slot):
    img=img.convert("RGBA")
    if slot.startswith("floor/"):
        img=ImageEnhance.Contrast(img).enhance(1.08)
        img=ImageEnhance.Sharpness(img).enhance(1.18)
        img.putalpha(Image.new("L",img.size,255))
        return img
    a=img.getchannel("A")
    sh=Image.new("RGBA",img.size,(0,0,0,0)); m=a.filter(ImageFilter.GaussianBlur(12)); shifted=Image.new("L",img.size,0); shifted.paste(m,(9,13)); sh.putalpha(shifted.point(lambda p:int(p*.34)))
    out=Image.alpha_composite(sh,img)
    inner=ImageChops.subtract(a,a.filter(ImageFilter.MinFilter(7)))
    hi=Image.new("RGBA",img.size,(110,190,210,0)); hi.putalpha(inner.point(lambda p:int(p*.22)))
    return ImageEnhance.Sharpness(Image.alpha_composite(out,hi)).enhance(1.22)

def floor_a():
    im=noise((24,27,35,255),201); panel(im,128,True); d=ImageDraw.Draw(im,"RGBA")
    for x,y in ((64,64),(192,64),(320,64),(448,64),(64,320),(320,320)):
        d.ellipse((x-4,y-4,x+4,y+4),fill=(2,4,7,220),outline=(87,107,137,70),width=2)
    return im
def floor_b():
    im=noise((19,22,29,255),211); panel(im,96); d=ImageDraw.Draw(im,"RGBA")
    for y in range(48,SIZE,128):
        d.rectangle((0,y,SIZE,y+14),fill=(6,8,14,115)); d.line((0,y+3,SIZE,y+3),fill=(*CYAN[:3],24),width=1)
    return im
def floor_c():
    im=noise((30,31,39,255),221); panel(im,160,True); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(222)
    for _ in range(16):
        x,y=rnd.randrange(SIZE),rnd.randrange(SIZE); r=rnd.randrange(9,24)
        d.polygon([(x,y-r),(x+r,y),(x,y+r),(x-r,y)],fill=(9,10,16,26),outline=(*VIOLET2[:3],18))
    return im
def floor_hazard():
    im=noise((17,19,27,255),231); panel(im,128); d=ImageDraw.Draw(im,"RGBA")
    for k in range(-SIZE,SIZE*2,96):
        d.polygon([(k,0),(k+18,0),(k-SIZE+18,SIZE),(k-SIZE,SIZE)],fill=(*VIOLET[:3],65))
    for y in (128,384): d.line((0,y,SIZE,y),fill=(*CYAN[:3],36),width=2)
    return im

def fracture():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(241); o=(256,255)
    for b in range(12):
        pts=[o]; ang=b*math.tau/12+rnd.uniform(-.2,.2)
        for s in range(1,rnd.randrange(5,9)):
            r=s*rnd.randrange(18,29); pts.append((o[0]+math.cos(ang)*r+rnd.uniform(-10,10),o[1]+math.sin(ang)*r+rnd.uniform(-10,10)))
        d.line(pts,fill=(18,8,28,230),width=11); d.line(pts,fill=(*VIOLET[:3],175),width=4); d.line(pts,fill=(185,128,255,150),width=1)
    return im.filter(ImageFilter.GaussianBlur(.4))
def blood():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(251)
    for _ in range(26):
        x,y=int(rnd.gauss(256,66)),int(rnd.gauss(256,54)); rx,ry=rnd.randrange(8,40),rnd.randrange(5,25)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(62,8,24,rnd.randrange(65,135)))
    return im.filter(ImageFilter.GaussianBlur(1.8))
def scorch():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA")
    for r,a in ((170,10),(130,19),(96,30),(66,44),(38,58)):
        d.ellipse((256-r,256-r*.7,256+r,256+r*.7),fill=(5,6,11,a))
    d.ellipse((224,226,288,286),fill=(*VIOLET[:3],22)); return im.filter(ImageFilter.GaussianBlur(8))

def framed(box,fill,outline=(83,93,120,255),radius=22):
    im=transparent(); x0,y0,x1,y1=box
    sh=transparent(); sd=ImageDraw.Draw(sh,"RGBA"); sd.rounded_rectangle((x0+12,y0+16,x1+16,y1+20),radius=radius,fill=(0,0,0,130))
    im=Image.alpha_composite(im,sh.filter(ImageFilter.GaussianBlur(9))); d=ImageDraw.Draw(im,"RGBA")
    d.rounded_rectangle(box,radius=radius,fill=fill,outline=outline,width=7); return im

def barrier():
    im=framed((70,166,442,334),(26,29,38,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((106,194,406,302),fill=(12,15,23,255),outline=(53,66,90,255),width=4)
    for x in range(128,390,66):
        d.polygon([(x,202),(x+20,202),(x+56,294),(x+36,294)],fill=(*VIOLET2[:3],150))
    d.line((115,246,397,246),fill=(*CYAN[:3],80),width=2); return im

def debris(seed,void=False):
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(seed)
    for _ in range(17):
        cx,cy=rnd.randrange(110,402),rnd.randrange(120,392); r=rnd.randrange(15,42)
        pts=[(cx+math.cos(k*math.tau/6)*r*rnd.uniform(.6,1.2),cy+math.sin(k*math.tau/6)*r*rnd.uniform(.6,1.2)) for k in range(6)]
        d.polygon(pts,fill=((28,28,40,255) if void else (45,47,56,255)),outline=(8,10,16,230))
        if void and rnd.random()<.55:
            x,y=pts[0]; d.line((x,y,cx,cy),fill=(*VIOLET[:3],130),width=3)
    return im

def wall(seed,portal=False):
    im=framed((58,112,454,378),(29,31,41,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((90,145,422,342),fill=(12,15,23,255),outline=(56,65,88,255),width=4)
    for y in (166,248,326): d.line((98,y,414,y),fill=(87,101,128,60),width=2)
    if portal:
        d.rounded_rectangle((153,177,359,309),radius=16,fill=(26,13,42,245),outline=VIOLET,width=5)
        d.ellipse((208,202,304,298),outline=CYAN,width=3)
    return im
def crate():
    im=framed((118,112,394,386),(38,40,50,255)); d=ImageDraw.Draw(im,"RGBA")
    d.rectangle((146,142,366,354),fill=(17,19,27,255),outline=(72,84,108,255),width=4)
    d.line((152,148,360,348),fill=(76,88,112,130),width=8); d.line((360,148,152,348),fill=(76,88,112,130),width=8)
    d.rectangle((218,217,294,277),fill=(31,18,49,230),outline=VIOLET,width=3); return im
def beacon():
    im=transparent(); glow=transparent(); gd=ImageDraw.Draw(glow,"RGBA")
    for r,a in ((116,12),(78,24),(44,45)): gd.ellipse((256-r,246-r,256+r,246+r),fill=(*CYAN[:3],a))
    im=Image.alpha_composite(im,glow.filter(ImageFilter.GaussianBlur(10))); d=ImageDraw.Draw(im,"RGBA")
    d.polygon([(256,132),(366,220),(326,350),(186,350),(146,220)],fill=(20,22,31,245),outline=(79,91,118,255))
    d.ellipse((194,184,318,308),fill=(29,20,47,255),outline=VIOLET,width=5)
    d.ellipse((224,214,288,278),fill=(33,114,133,255),outline=CYAN,width=4); return im

GEN={"floor/concrete_a":floor_a,"floor/concrete_b":floor_b,"floor/concrete_c":floor_c,"floor/hazard_a":floor_hazard,
"decal/crack_a":fracture,"decal/blood_a":blood,"decal/scorch_a":scorch,
"prop/barrier_a":barrier,"prop/debris_a":lambda:debris(261,False),"prop/debris_b":lambda:debris(267,True),
"prop/wall_a":lambda:wall(271,False),"prop/wall_b":lambda:wall(273,True),"prop/crate_a":crate,"prop/beacon_a":beacon}

def main():
    p=argparse.ArgumentParser(); p.add_argument("--output",type=Path,default=Path("art_sources/environment/null_sector")); p.add_argument("--manifest",type=Path,default=Path("art_sources/environment/null_sector/candidate-manifest.json")); a=p.parse_args()
    a.output.mkdir(parents=True,exist_ok=True); assets=[]
    for slot in SLOTS:
        path=a.output/(slot+".png"); path.parent.mkdir(parents=True,exist_ok=True); finish(GEN[slot](),slot).save(path,"PNG",optimize=True); assets.append(str(path).replace("\\","/"))
    m={"schema":1,"biome":"null_sector","stage":"procedural-authored-candidate-v1","production_ready":False,"visual_qa_pass":False,"generator":"tools/environment/generate_null_sector_candidate.py","master_size":[512,512],"asset_count":14,"assets":assets,"notes":"Near-black/violet/cyan candidate. Keep floors dark; not FINAL until premium visual QA."}
    a.manifest.write_text(json.dumps(m,indent=2)+"\n",encoding="utf-8"); print("generated 14 Null Sector candidate masters"); return 0
if __name__=="__main__": raise SystemExit(main())
