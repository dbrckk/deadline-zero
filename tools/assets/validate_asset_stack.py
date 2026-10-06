#!/usr/bin/env python3
"""Validate Deadline: Zero's asset-source and mobile-budget contract."""
from __future__ import annotations
import json, pathlib, sys

ROOT = pathlib.Path(__file__).resolve().parents[2]
MANIFEST = ROOT / "godot/assets/asset_manifest.json"
APPROVED = {"CC0-1.0", "Project-Owned"}
errors = []

def fail(message):
    errors.append(message)

data = json.loads(MANIFEST.read_text())
if data.get("schema") != 2:
    fail("asset_manifest.json must use schema 2")
policy = data.get("policy", {})
if set(policy.get("shipped_asset_licenses", [])) != APPROVED:
    fail("shipping license allowlist must remain CC0-1.0 + Project-Owned")

seen = set()
for source in data.get("sources", []):
    sid = source.get("id")
    if not sid or sid in seen:
        fail(f"missing/duplicate source id: {sid!r}")
        continue
    seen.add(sid)
    license_id = source.get("license")
    if license_id not in APPROVED:
        fail(f"{sid}: unapproved shipped asset license {license_id!r}")
    page = source.get("page")
    if page and not page.startswith("https://"):
        fail(f"{sid}: source page must be HTTPS")
    if source.get("provider") == "polyhaven":
        if not source.get("asset_id"):
            fail(f"{sid}: Poly Haven entry must pin asset_id")
        if source.get("resolution") not in {"1k", "2k"}:
            fail(f"{sid}: mobile PBR source must be pinned to 1k/2k")
        maps = {m.lower() for m in source.get("maps", [])}
        if not {"diffuse", "nor_gl"}.issubset(maps):
            fail(f"{sid}: PBR texture must include Diffuse + OpenGL normal")

budgets = data.get("budgets", {}).get("mobile", {})
required_roles = {"enemy_standard","enemy_elite","boss","survivor","environment_prop","hero_prop","ground_material","decal"}
if set(budgets) != required_roles:
    fail("mobile budget roles changed or are incomplete")
for role, budget in budgets.items():
    if budget.get("texture_max", 0) > 2048:
        fail(f"{role}: mobile texture budget exceeds 2K")
    if "materials" in budget and budget["materials"] > 3:
        fail(f"{role}: material-slot budget exceeds 3")

third_party = ROOT / "godot/assets/third_party"
if third_party.exists():
    for provider_dir in [p for p in third_party.iterdir() if p.is_dir()]:
        # Existing imported packs must carry source/license evidence somewhere in the subtree.
        shipped = [p for p in provider_dir.rglob("*") if p.suffix.lower() in {".gltf",".glb",".fbx",".obj",".png",".jpg",".jpeg",".webp"}]
        if shipped:
            source_docs = list(provider_dir.rglob("SOURCE.md")) + list(provider_dir.rglob("SOURCE.json"))
            licenses = list(provider_dir.rglob("LICENSE.txt")) + list(provider_dir.rglob("LICENSE.md"))
            if not source_docs:
                fail(f"{provider_dir.relative_to(ROOT)}: imported assets missing SOURCE metadata")
            if not licenses and provider_dir.name != "polyhaven":
                fail(f"{provider_dir.relative_to(ROOT)}: imported assets missing local license evidence")

if errors:
    print("ASSET_STACK_INVALID")
    for error in errors:
        print(" -", error)
    sys.exit(1)

print(f"ASSET_STACK_OK sources={len(seen)} mobile_roles={len(budgets)}")
