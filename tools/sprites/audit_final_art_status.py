#!/usr/bin/env python3
"""Report final-art maturity from published manifests plus production-contract evidence."""
from __future__ import annotations

import argparse
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
CONTRACTS = ROOT / "config" / "actor-production-contracts.json"
ART = ROOT / "assets" / "art"
GATES = (
    ("source_production_ready", "production"),
    ("phone_qa_pass", "phone"),
    ("android_visual_qa_pass", "android_visual"),
    ("accepted", "accepted"),
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


def contract_key(actor_id: str) -> str:
    return "boss" if actor_id == "alpha" else actor_id


def main() -> int:
    args = parse_args()
    layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
    contracts = json.loads(CONTRACTS.read_text(encoding="utf-8")).get("actors", {})
    rows = []

    for actor in sorted(layout["actors"], key=lambda a: (a["priority"], a["id"])):
        actor_id = actor["id"]
        path = manifest_path(actor_id)
        data = json.loads(path.read_text(encoding="utf-8")) if path.is_file() else {}
        contract = contracts.get(contract_key(actor_id), {})
        validation = contract.get("validation", {})

        manifest_phone = data.get("phone_qa_pass") is True
        contract_phone = validation.get("phone_qa_pass") is True
        manifest_android = data.get("android_visual_qa_pass") is True
        contract_android = validation.get("android_visual_qa_pass") is True
        manifest_accepted = data.get("android_accepted") is True
        contract_accepted = contract.get("status") == "accepted"

        gate_state = {
            "source_production_ready": data.get("source_production_ready") is True,
            "phone_qa_pass": manifest_phone or contract_phone,
            "android_visual_qa_pass": manifest_android or contract_android,
            "accepted": manifest_accepted or contract_accepted,
        }
        missing_gates = [label for key, label in GATES if not gate_state[key]]
        score = len(GATES) - len(missing_gates)
        drift = []
        if contract_phone and not manifest_phone:
            drift.append("phone")
        if contract_android and not manifest_android:
            drift.append("android_visual")
        if contract_accepted and not manifest_accepted:
            drift.append("accepted")

        rows.append({
            "id": actor_id,
            "priority": actor["priority"],
            "root": actor["root"],
            "manifest": str(path.relative_to(ROOT)) if path.is_file() else None,
            **gate_state,
            "maturity_score": score,
            "missing_gates": missing_gates,
            "source_stage": data.get("source_stage"),
            "evidence": {
                "phone": "manifest" if manifest_phone else ("contract" if contract_phone else None),
                "android_visual": "manifest" if manifest_android else ("contract" if contract_android else None),
                "accepted": "manifest" if manifest_accepted else ("contract" if contract_accepted else None),
            },
            "manifest_evidence_drift": drift,
        })

    minimum_score = min((r["maturity_score"] for r in rows), default=0)
    summary = {
        "actors": len(rows),
        "production_ready": sum(r["source_production_ready"] for r in rows),
        "phone_qa": sum(r["phone_qa_pass"] for r in rows),
        "android_visual_qa": sum(r["android_visual_qa_pass"] for r in rows),
        "accepted": sum(r["accepted"] for r in rows),
        "manifest_evidence_drift": sum(bool(r["manifest_evidence_drift"]) for r in rows),
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
        f"accepted {summary['accepted']}"
    )
    print("Gate deficits: " + ", ".join(
        f"{label}={count}" for label, count in summary["gate_deficits"].items()
    ))
    print(f"Manifest/evidence drift: {summary['manifest_evidence_drift']} actors")
    print("Least advanced: " + ", ".join(summary["least_advanced"]))
    print()
    print("score  actor              prod phone android accepted stage  blockers")
    for r in rows:
        print(
            f"{r['maturity_score']:>5}  {r['id']:<18} "
            f"{'Y' if r['source_production_ready'] else '-':>4} "
            f"{'Y' if r['phone_qa_pass'] else '-':>5} "
            f"{'Y' if r['android_visual_qa_pass'] else '-':>7} "
            f"{'Y' if r['accepted'] else '-':>8} "
            f"{r['source_stage'] or '-'}  "
            f"{','.join(r['missing_gates']) or '-'}"
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
