#!/usr/bin/env python3
"""Normalize Rex 512px renders into the 96x96 production cell contract."""
from __future__ import annotations
import argparse, json
from pathlib import Path
from PIL import Image, ImageDraw

EXPECTED={'idle':4,'run':8,'attack':6,'hit':3,'death':8}
DIRS=('n','ne','e','se','s','sw','w','nw')

def args():
 p=argparse.ArgumentParser(); p.add_argument('--input',type=Path,required=True); p.add_argument('--output',type=Path,required=True); p.add_argument('--report',type=Path,required=True); p.add_argument('--contact-sheet',type=Path,required=True); return p.parse_args()

def bbox_alpha(im): return im.getchannel('A').getbbox()

def main():
 a=args(); a.output.mkdir(parents=True,exist_ok=True); rows=[]; expected_paths=[]
 for anim,count in EXPECTED.items():
  for d in DIRS:
   for i in range(count): expected_paths.append((anim,d,i,a.input/anim/d/f'{anim}_{i:02d}.png'))
 missing=[str(p) for *_,p in expected_paths if not p.exists()]
 if missing: raise SystemExit(f'missing {len(missing)} frames')
 # Stable scale and ground anchor per direction/animation: use global largest alpha box,
 # preserving motion while avoiding per-frame breathing/jitter from independent crops.
 maxw=maxh=1
 source=[]
 for anim,d,i,p in expected_paths:
  im=Image.open(p).convert('RGBA'); b=bbox_alpha(im)
  if not b: raise SystemExit(f'empty frame {p}')
  maxw=max(maxw,b[2]-b[0]); maxh=max(maxh,b[3]-b[1]); source.append((anim,d,i,im,b))
 scale=min(82/maxw,88/maxh)
 for anim,d,i,im,b in source:
  crop=im.crop(b); nw=max(1,round(crop.width*scale)); nh=max(1,round(crop.height*scale)); crop=crop.resize((nw,nh),Image.Resampling.LANCZOS)
  cell=Image.new('RGBA',(96,96),(0,0,0,0)); x=(96-nw)//2; y=92-nh; cell.alpha_composite(crop,(x,y))
  out=a.output/anim/d/f'{anim}_{i:02d}.png'; out.parent.mkdir(parents=True,exist_ok=True); cell.save(out)
  bb=bbox_alpha(cell); margin=min(bb[0],bb[1],96-bb[2],96-bb[3]); rows.append({'path':str(out.relative_to(a.output)),'bbox':list(bb),'margin':margin,'width':bb[2]-bb[0],'height':bb[3]-bb[1]})
 bad=[r for r in rows if r['margin']<2 or r['width']<12 or r['height']<20]
 report={'actor':'rex','stage':'96px-normalization','frame_count':len(rows),'expected':232,'scale':scale,'bad_frames':bad,'pass':len(rows)==232 and not bad}
 a.report.parent.mkdir(parents=True,exist_ok=True); a.report.write_text(json.dumps(report,indent=2)+'\n')
 # Phone-scale sheet: one representative sample for each animation/direction.
 thumb=96; sheet=Image.new('RGBA',(8*thumb,5*thumb),(28,28,32,255)); draw=ImageDraw.Draw(sheet)
 for r,(anim,count) in enumerate(EXPECTED.items()):
  idx=count//2
  for c,d in enumerate(DIRS):
   im=Image.open(a.output/anim/d/f'{anim}_{idx:02d}.png').convert('RGBA'); sheet.alpha_composite(im,(c*thumb,r*thumb)); draw.text((c*thumb+2,r*thumb+2),f'{anim} {d}',fill=(255,255,255,255))
 a.contact_sheet.parent.mkdir(parents=True,exist_ok=True); sheet.save(a.contact_sheet)
 print(json.dumps(report,indent=2))
 if not report['pass']: raise SystemExit('96px QA failed')
if __name__=='__main__': main()
