#!/usr/bin/env python3
"""Report final-art maturity from the machine-readable actor contract and published manifests."""
from __future__ import annotations

import argparse
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
ART = ROOT / "assets" / "art"


def parse_args():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("--json", action="store_true")
    return p.parse_args()


def manifest_path(actor_id: str) -> Path:
    direct = ART / f"{actor_id}-manifest.json"
    if direct.is_file():
        return direct
    if actor_id == "alpha":
        fallback = ART / "boss-manifest.json"
        if fallback.is_file():
            return fallback
    return direct


def main() -> int:
    args = parse_args()
    layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
    rows = []
    for actor in sorted(layout["actors"], key=lambda a: (a["priority"], a["id"])):
        path = manifest_path(actor["id"])
        data = json.loads(path.read_text(encoding="utf-8")) if path.is_file() else {}
        production = data.get("source_production_ready") is True
        phone = data.get("phone_qa_pass") is True
        android = data.get("android_visual_qa_pass") is True
        accepted = data.get("android_accepted") is True
        score = sum((production, phone, android, accepted))
        rows.append({
            "id": actor["id"],
            "priority": actor["priority"],
            "root": actor["root"],
            "manifest": str(path.relative_to(ROOT)) if path.is_file() else None,
            "source_production_ready": production,
            "phone_qa_pass": phone,
            "android_visual_qa_pass": android,
            "android_accepted": accepted,
            "maturity_score": score,
            "source_stage": data.get("source_stage"),
        })

    summary = {
        "actors": len(rows),
        "production_ready": sum(r["source_production_ready"] for r in rows),
        "phone_qa": sum(r["phone_qa_pass"] for r in rows),
        "android_visual_qa": sum(r["android_visual_qa_pass"] for r in rows),
        "android_accepted": sum(r["android_accepted"] for r in rows),
        "least_advanced": [r["id"] for r in rows if r["maturity_score"] == min(x["maturity_score"] for x in rows)],
    }

    if args.json:
        print(json.dumps({"summary": summary, "actors": rows}, indent=2))
        return 0

    print(
        f"Final-art actors: {summary['actors']} | production {summary['production_ready']} | "
        f"phone-QA {summary['phone_qa']} | Android-QA {summary['android_visual_qa']} | "
        f"accepted {summary['android_accepted']}"
    )
    print("Least advanced: " + ", ".join(summary["least_advanced"]))
    print()
    print("score  actor              prod phone android accepted stage")
    for r in rows:
        print(
            f"{r['maturity_score']:>5}  {r['id']:<18} "
            f"{'Y' if r['source_production_ready'] else '-':>4} "
            f"{'Y' if r['phone_qa_pass'] else '-':>5} "
            f"{'Y' if r['android_visual_qa_pass'] else '-':>7} "
            f"{'Y' if r['android_accepted'] else '-':>8} "
            f"{r['source_stage'] or '-'}"
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
