#!/usr/bin/env python3
"""Acquire pinned CC0 Poly Haven source textures for Deadline: Zero.

This is a developer tool, never part of the shipped runtime.
It uses only the Python standard library and Poly Haven's public read-only API.
"""
from __future__ import annotations
import argparse, hashlib, json, os, sys, urllib.request
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
MANIFEST = ROOT / "godot/assets/asset_manifest.json"
API = "https://api.polyhaven.com"
UA = "DeadlineZeroAssetPipeline/2.0 (+https://github.com/dbrckk/deadline-zero)"

def get_json(url: str):
    req = urllib.request.Request(url, headers={"User-Agent": UA})
    with urllib.request.urlopen(req, timeout=45) as response:
        return json.load(response)

def walk_records(node, path=()):
    if isinstance(node, dict):
        if isinstance(node.get("url"), str):
            yield path, node
        for key, value in node.items():
            if key not in {"url", "size", "md5"}:
                yield from walk_records(value, path + (str(key),))
    elif isinstance(node, list):
        for i, value in enumerate(node):
            yield from walk_records(value, path + (str(i),))

def map_matches(path, wanted: str) -> bool:
    p = "/".join(path).lower()
    aliases = {
        "diffuse": ("diff", "diffuse", "albedo"),
        "nor_gl": ("nor_gl", "normal_gl", "normal"),
        "arm": ("arm", "ao/rough/metal", "ao_rough_metal"),
        "rough": ("rough", "roughness"),
        "metal": ("metal", "metallic"),
        "ao": ("ao", "ambient_occlusion"),
        "disp": ("disp", "displacement", "height"),
    }
    return any(token in p for token in aliases.get(wanted.lower(), (wanted.lower(),)))

def choose_record(files, map_name: str, resolution: str, fmt: str):
    candidates = []
    for path, record in walk_records(files):
        url = record["url"]
        joined = "/".join(path).lower()
        if resolution.lower() not in joined:
            continue
        if not (joined.endswith("/" + fmt.lower()) or url.lower().split("?")[0].endswith("." + fmt.lower())):
            continue
        if map_matches(path, map_name):
            candidates.append((path, record))
    if not candidates:
        return None
    candidates.sort(key=lambda item: (len(item[0]), "/".join(item[0])))
    return candidates[0]

def download(url: str, dest: Path, md5: str | None):
    req = urllib.request.Request(url, headers={"User-Agent": UA})
    dest.parent.mkdir(parents=True, exist_ok=True)
    h = hashlib.md5()
    with urllib.request.urlopen(req, timeout=120) as response, dest.open("wb") as out:
        while True:
            chunk = response.read(1024 * 1024)
            if not chunk:
                break
            out.write(chunk)
            h.update(chunk)
    if md5 and h.hexdigest().lower() != md5.lower():
        dest.unlink(missing_ok=True)
        raise RuntimeError(f"MD5 mismatch for {dest.name}")

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--id", action="append", help="Manifest source id; repeatable. Default: all pinned Poly Haven textures.")
    ap.add_argument("--output", type=Path, default=ROOT / "asset_staging/polyhaven")
    ap.add_argument("--dry-run", action="store_true")
    args = ap.parse_args()

    manifest = json.loads(MANIFEST.read_text())
    sources = [s for s in manifest["sources"] if s.get("provider") == "polyhaven" and s.get("asset_id")]
    if args.id:
        wanted = set(args.id)
        sources = [s for s in sources if s["id"] in wanted]
        missing = wanted - {s["id"] for s in sources}
        if missing:
            raise SystemExit("Unknown Poly Haven manifest id(s): " + ", ".join(sorted(missing)))

    total = 0
    for source in sources:
        asset_id = source["asset_id"]
        files = get_json(f"{API}/files/{asset_id}")
        selected = []
        for map_name in source.get("maps", ["Diffuse", "nor_gl", "arm"]):
            choice = choose_record(files, map_name, source.get("resolution", "2k"), source.get("format", "jpg"))
            if choice is None:
                print(f"WARN {asset_id}: no {map_name} {source.get('resolution')} {source.get('format')}", file=sys.stderr)
                continue
            path, record = choice
            filename = os.path.basename(record["url"].split("?")[0])
            size = int(record.get("size") or 0)
            total += size
            selected.append((map_name, path, record, filename))
        print(f"{source['id']} -> {len(selected)} maps")
        for map_name, path, record, filename in selected:
            print(f"  {map_name:8s} {record.get('size', 0)/1024/1024:6.2f} MiB  {filename}")
        if args.dry_run:
            continue

        target = args.output / asset_id
        target.mkdir(parents=True, exist_ok=True)
        metadata = {
            "source": source["page"],
            "asset_id": asset_id,
            "license": source["license"],
            "resolution": source.get("resolution"),
            "format": source.get("format"),
            "api": API,
            "files": [],
        }
        for map_name, path, record, filename in selected:
            dest = target / filename
            download(record["url"], dest, record.get("md5"))
            metadata["files"].append({
                "map": map_name,
                "file": filename,
                "md5": record.get("md5"),
                "api_path": list(path),
            })
        (target / "SOURCE.json").write_text(json.dumps(metadata, indent=2) + "\n")
    print(f"Selected transfer: {total/1024/1024:.2f} MiB")
    if args.dry_run:
        print("Dry run only; no files downloaded.")

if __name__ == "__main__":
    main()
