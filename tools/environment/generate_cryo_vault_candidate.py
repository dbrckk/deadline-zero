#!/usr/bin/env python3
"""Generate deterministic Cryo Vault environment candidate masters."""
from __future__ import annotations
import argparse,json,math,random
from pathlib import Path
from PIL import Image,ImageChops,ImageDraw,ImageEnhance,ImageFilter
from candidate_utils import make_tileable_edges

SIZE=512
SLOTS=("floor/concrete_a","floor/concrete_b","floor/concrete_c","floor/hazard_a",
"decal/crack_a","decal/blood_a","decal/scorch_a","prop/barrier_a","prop/debris_a",
"prop/debris_b","prop/wall_a","prop/wall_b","prop/crate_a","prop/beacon_a")
NAVY=(20,31,43,255); STEEL=(56,72,84,255); STEEL2=(82,99,111,255)
ICE=(156,222,238,255); ICE2=(98,180,207,255); WHITE=(220,242,246,255); BLOOD=(80,16,24,255)

def clamp(v): return 0 if v<0 else 255 if v>255 else int(v)
def transparent(): return Image.new("RGBA",(SIZE,SIZE),(0,0,0,0))
def noise(base,seed,strength=10):
    rnd=random.Random(seed); im=Image.new("RGBA",(SIZE,SIZE)); px=im.load()
    for y in range(SIZE):
        for x in range(SIZE):
            n=(math.sin(x*.051)+math.cos(y*.047))*strength*.25+rnd.uniform(-strength,strength)
            px[x,y]=(clamp(base[0]+n),clamp(base[1]+n),clamp(base[2]+n),255)
    return im
def panels(im,spacing=128):
    d=ImageDraw.Draw(im,"RGBA")
    for x in range(0,SIZE,spacing):
        d.line((x,0,x,SIZE),fill=(8,14,20,190),width=4); d.line((x+4,0,x+4,SIZE),fill=(*WHITE[:3],36),width=1)
    for y in range(0,SIZE,spacing):
        d.line((0,y,SIZE,y),fill=(8,14,20,190),width=4); d.line((0,y+4,SIZE,y+4),fill=(*WHITE[:3],36),width=1)
def frost(im,seed,count=26):
    d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(seed)
    for _ in range(count):
        x,y=rnd.randrange(SIZE),rnd.randrange(SIZE); rx,ry=rnd.randrange(18,64),rnd.randrange(5,18)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(*ICE[:3],rnd.randrange(8,24)))
def finish(im,slot):
    im=im.convert("RGBA")
    if slot.startswith("floor/"):
        im=ImageEnhance.Contrast(im).enhance(1.08); im=ImageEnhance.Sharpness(im).enhance(1.18); im.putalpha(Image.new("L",im.size,255)); return make_tileable_edges(im)
    a=im.getchannel("A"); shadow=Image.new("RGBA",im.size,(0,0,0,0)); sm=a.filter(ImageFilter.GaussianBlur(12)); shifted=Image.new("L",im.size,0); shifted.paste(sm,(10,14)); shadow.putalpha(shifted.point(lambda p:int(p*.30)))
    out=Image.alpha_composite(shadow,im); inner=ImageChops.subtract(a,a.filter(ImageFilter.MinFilter(7))); hi=Image.new("RGBA",im.size,(220,245,250,0)); hi.putalpha(inner.point(lambda p:int(p*.26))); return ImageEnhance.Sharpness(Image.alpha_composite(out,hi)).enhance(1.24)

def floor_a():
    im=noise((43,58,70,255),301,9); panels(im,128); frost(im,302,20); return im
def floor_b():
    im=noise((35,49,62,255),311,9); panels(im,96); d=ImageDraw.Draw(im,"RGBA")
    for y in range(52,SIZE,128): d.rectangle((0,y,SIZE,y+16),fill=(20,32,43,95)); d.line((0,y+3,SIZE,y+3),fill=(*ICE[:3],38),width=2)
    frost(im,312,16); return im
def floor_c():
    im=noise((50,62,71,255),321,10); panels(im,160); frost(im,322,32); return im
def floor_hazard():
    im=noise((30,43,55,255),331,8); panels(im,128); d=ImageDraw.Draw(im,"RGBA")
    for k in range(-SIZE,SIZE*2,80): d.polygon([(k,0),(k+26,0),(k-SIZE+26,SIZE),(k-SIZE,SIZE)],fill=(92,170,201,90))
    return im

def ice_crack():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(341); o=(256,256)
    for b in range(12):
        pts=[o]; ang=b*math.tau/12+rnd.uniform(-.16,.16)
        for s in range(1,rnd.randrange(5,9)):
            r=s*rnd.randrange(18,30); pts.append((o[0]+math.cos(ang)*r+rnd.uniform(-9,9),o[1]+math.sin(ang)*r+rnd.uniform(-9,9)))
        d.line(pts,fill=(13,27,38,210),width=8); d.line(pts,fill=(*ICE[:3],180),width=3); d.line(pts,fill=(*WHITE[:3],130),width=1)
    return im.filter(ImageFilter.GaussianBlur(.35))
def blood():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(351)
    for _ in range(24):
        x,y=int(rnd.gauss(256,68)),int(rnd.gauss(256,52)); rx,ry=rnd.randrange(8,40),rnd.randrange(5,24)
        d.ellipse((x-rx,y-ry,x+rx,y+ry),fill=(*BLOOD[:3],rnd.randrange(65,135)))
    return im.filter(ImageFilter.GaussianBlur(1.8))
def scorch():
    im=transparent(); d=ImageDraw.Draw(im,"RGBA")
    for r,a in ((168,10),(128,18),(92,28),(62,40),(36,52)): d.ellipse((256-r,256-r*.68,256+r,256+r*.68),fill=(8,15,20,a))
    d.ellipse((224,226,288,286),fill=(*ICE2[:3],18)); return im.filter(ImageFilter.GaussianBlur(8))

def framed(box,fill,outline=STEEL2,radius=22):
    im=transparent(); x0,y0,x1,y1=box; sh=transparent(); sd=ImageDraw.Draw(sh,"RGBA"); sd.rounded_rectangle((x0+12,y0+16,x1+16,y1+20),radius=radius,fill=(0,0,0,105)); im=Image.alpha_composite(im,sh.filter(ImageFilter.GaussianBlur(9))); d=ImageDraw.Draw(im,"RGBA"); d.rounded_rectangle(box,radius=radius,fill=fill,outline=outline,width=7); return im
def barrier():
    im=framed((72,166,440,334),(54,68,79,255)); d=ImageDraw.Draw(im,"RGBA"); d.rectangle((108,194,404,302),fill=(31,45,57,255),outline=(90,112,124,255),width=4)
    for x in range(126,390,66): d.polygon([(x,202),(x+20,202),(x+56,294),(x+36,294)],fill=(*ICE2[:3],115))
    return im
def debris(seed,icy=False):
    im=transparent(); d=ImageDraw.Draw(im,"RGBA"); rnd=random.Random(seed)
    for _ in range(18):
        cx,cy=rnd.randrange(110,402),rnd.randrange(120,392); r=rnd.randrange(14,42); pts=[(cx+math.cos(k*math.tau/6)*r*rnd.uniform(.6,1.2),cy+math.sin(k*math.tau/6)*r*rnd.uniform(.6,1.2)) for k in range(6)]; d.polygon(pts,fill=((58,78,90,255) if icy else (62,70,76,255)),outline=(20,29,36,230))
        if icy and rnd.random()<.5:
            x,y=pts[0]; d.line((x,y,cx,cy),fill=(*ICE[:3],115),width=3)
    return im
def wall(seed,frosted=False):
    im=framed((58,112,454,378),(55,67,77,255)); d=ImageDraw.Draw(im,"RGBA"); d.rectangle((90,145,422,342),fill=(28,41,52,255),outline=(94,116,128,255),width=4)
    for y in (166,248,326): d.line((98,y,414,y),fill=(156,184,191,70),width=2)
    if frosted:
        d.rounded_rectangle((150,180,362,308),radius=15,fill=(61,98,116,235),outline=ICE,width=5); d.line((164,244,348,244),fill=WHITE,width=3)
    return im
def crate():
    im=framed((118,112,394,386),(66,79,87,255)); d=ImageDraw.Draw(im,"RGBA"); d.rectangle((146,142,366,354),fill=(36,50,59,255),outline=(102,124,133,255),width=4); d.line((152,148,360,348),fill=(156,184,190,130),width=8); d.line((360,148,152,348),fill=(156,184,190,130),width=8); d.rectangle((218,217,294,277),fill=(49,92,108,230),outline=ICE,width=3); return im
def beacon():
    im=transparent(); glow=transparent(); gd=ImageDraw.Draw(glow,"RGBA")
    for r,a in ((116,12),(78,24),(44,44)): gd.ellipse((256-r,246-r,256+r,246+r),fill=(*ICE[:3],a))
    im=Image.alpha_composite(im,glow.filter(ImageFilter.GaussianBlur(10))); d=ImageDraw.Draw(im,"RGBA"); d.ellipse((146,136,366,356),fill=(38,52,61,245),outline=(105,129,141,255),width=8); d.ellipse((184,174,328,318),fill=(42,77,91,255),outline=ICE,width=5); d.ellipse((222,212,290,280),fill=(111,202,224,255),outline=WHITE,width=4); return im

GEN={"floor/concrete_a":floor_a,"floor/concrete_b":floor_b,"floor/concrete_c":floor_c,"floor/hazard_a":floor_hazard,
"decal/crack_a":ice_crack,"decal/blood_a":blood,"decal/scorch_a":scorch,
"prop/barrier_a":barrier,"prop/debris_a":lambda:debris(361,False),"prop/debris_b":lambda:debris(367,True),
"prop/wall_a":lambda:wall(371,False),"prop/wall_b":lambda:wall(373,True),"prop/crate_a":crate,"prop/beacon_a":beacon}

def main():
    p=argparse.ArgumentParser(); p.add_argument("--output",type=Path,default=Path("art_sources/environment/cryo_vault")); p.add_argument("--manifest",type=Path,default=Path("art_sources/environment/cryo_vault/candidate-manifest.json")); a=p.parse_args(); a.output.mkdir(parents=True,exist_ok=True); assets=[]
    for slot in SLOTS:
        path=a.output/(slot+".png"); path.parent.mkdir(parents=True,exist_ok=True); finish(GEN[slot](),slot).save(path,"PNG",optimize=True); assets.append(str(path).replace("\\","/"))
    m={"schema":1,"biome":"cryo_vault","stage":"procedural-authored-candidate-v1","production_ready":False,"visual_qa_pass":False,"generator":"tools/environment/generate_cryo_vault_candidate.py","master_size":[512,512],"asset_count":14,"assets":assets,"notes":"Cryogenic-facility candidate with frost and restrained white-blue accents. Not FINAL until premium visual QA."}
    a.manifest.write_text(json.dumps(m,indent=2)+"\n",encoding="utf-8"); print("generated 14 Cryo Vault candidate masters"); return 0
if __name__=="__main__": raise SystemExit(main())

# Regeneration trigger: seam-safe floor masters.
