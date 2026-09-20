#!/usr/bin/env python3
"""Report final-art maturity from the machine-readable actor contract and published manifests."""
from __future__ import annotations

import argparse
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
ART = ROOT / "assets" / "art"
GATES = (
    ("source_production_ready", "production"),
    ("phone_qa_pass", "phone"),
    ("android_visual_qa_pass", "android_visual"),
    ("android_accepted", "android_accepted"),
)


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
        gate_state = {key: data.get(key) is True for key, _ in GATES}
        missing_gates = [label for key, label in GATES if not gate_state[key]]
        score = len(GATES) - len(missing_gates)
        rows.append({
            "id": actor["id"],
            "priority": actor["priority"],
            "root": actor["root"],
            "manifest": str(path.relative_to(ROOT)) if path.is_file() else None,
            **gate_state,
            "maturity_score": score,
            "missing_gates": missing_gates,
            "source_stage": data.get("source_stage"),
        })

    minimum_score = min((r["maturity_score"] for r in rows), default=0)
    summary = {
        "actors": len(rows),
        "production_ready": sum(r["source_production_ready"] for r in rows),
        "phone_qa": sum(r["phone_qa_pass"] for r in rows),
        "android_visual_qa": sum(r["android_visual_qa_pass"] for r in rows),
        "android_accepted": sum(r["android_accepted"] for r in rows),
        "gate_deficits": {
            label: sum(not r[key] for r in rows)
            for key, label in GATES
        },
        "least_advanced": [r["id"] for r in rows if r["maturity_score"] == minimum_score],
    }

    if args.json:
        print(json.dumps({"summary": summary, "actors": rows}, indent=2))
        return 0

    print(
        f"Final-art actors: {summary['actors']} | production {summary['production_ready']} | "
        f"phone-QA {summary['phone_qa']} | Android-QA {summary['android_visual_qa']} | "
        f"accepted {summary['android_accepted']}"
    )
    print("Gate deficits: " + ", ".join(
        f"{label}={count}" for label, count in summary["gate_deficits"].items()
    ))
    print("Least advanced: " + ", ".join(summary["least_advanced"]))
    print()
    print("score  actor              prod phone android accepted stage  blockers")
    for r in rows:
        print(
            f"{r['maturity_score']:>5}  {r['id']:<18} "
            f"{'Y' if r['source_production_ready'] else '-':>4} "
            f"{'Y' if r['phone_qa_pass'] else '-':>5} "
            f"{'Y' if r['android_visual_qa_pass'] else '-':>7} "
            f"{'Y' if r['android_accepted'] else '-':>8} "
            f"{r['source_stage'] or '-'}  "
            f"{','.join(r['missing_gates']) or '-'}"
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
