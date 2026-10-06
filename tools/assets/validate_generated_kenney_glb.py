#!/usr/bin/env python3
"""Validate generated Kenney-derived Deadline: Zero GLBs."""
from __future__ import annotations
import argparse, json, struct, sys
from pathlib import Path

ASSETS = [
    "console","console_screen","metal_fence","metal_structure_cross",
    "pipe_straight","pipe_corner","pipe_opening","satellite_dish",
    "barrel_large","station_module",
]
MAX_BYTES = 2 * 1024 * 1024
BUDGET = {0: 12000, 1: 6000}

def read_glb(path: Path):
    data=path.read_bytes()
    if len(data)<20: raise ValueError("too small")
    magic,version,total=struct.unpack_from("<4sII",data,0)
    if magic!=b"glTF" or version!=2 or total!=len(data): raise ValueError("invalid GLB header")
    json_len,json_type=struct.unpack_from("<II",data,12)
    if json_type!=0x4E4F534A: raise ValueError("first chunk is not JSON")
    doc=json.loads(data[20:20+json_len].decode("utf-8").rstrip(" \t\r\n\0"))
    return doc,len(data)

def triangles(doc):
    accessors=doc.get("accessors",[])
    total=0
    for mesh in doc.get("meshes",[]):
        for prim in mesh.get("primitives",[]):
            if prim.get("mode",4)!=4: continue
            if "indices" in prim: total += int(accessors[prim["indices"]]["count"])//3
            else:
                pos=prim.get("attributes",{}).get("POSITION")
                if pos is not None: total += int(accessors[pos]["count"])//3
    return total

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("directory",type=Path)
    a=ap.parse_args()
    errors=[]
    for asset in ASSETS:
        lod_tris={}
        for lod in (0,1):
            name=f"dz_kenney_{asset}_lod{lod}.glb"
            path=a.directory/name
            if not path.exists():
                errors.append(f"{name}: missing"); continue
            try: doc,size=read_glb(path)
            except Exception as exc:
                errors.append(f"{name}: {exc}"); continue
            tri=triangles(doc); lod_tris[lod]=tri
            mats=len(doc.get("materials",[])); imgs=len(doc.get("images",[]))
            if tri<12: errors.append(f"{name}: suspiciously empty ({tri} triangles)")
            if tri>BUDGET[lod]: errors.append(f"{name}: {tri} triangles > {BUDGET[lod]}")
            if mats>2: errors.append(f"{name}: {mats} materials > 2")
            if imgs!=0: errors.append(f"{name}: source promotion must not embed images ({imgs})")
            if size>MAX_BYTES: errors.append(f"{name}: {size} bytes > {MAX_BYTES}")
            print(f"DZ_KENNEY_GLB_OK {name} triangles={tri} materials={mats} bytes={size}")
        if 0 in lod_tris and 1 in lod_tris and lod_tris[1] > lod_tris[0]:
            errors.append(f"{asset}: LOD1 {lod_tris[1]} triangles exceeds LOD0 {lod_tris[0]}")
    if errors:
        print("DZ_KENNEY_GLB_INVALID")
        for e in errors: print(" -",e)
        sys.exit(1)
    print(f"DZ_KENNEY_KIT_VALID assets={len(ASSETS)} lods=2")

if __name__=="__main__":
    main()
