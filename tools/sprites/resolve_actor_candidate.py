#!/usr/bin/env python3
"""Resolve a production actor candidate into deterministic CI metadata."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
from urllib.parse import quote


def load_candidates(config: Path, fragments_dir: Path) -> dict:
    data = json.loads(config.read_text())
    candidates = dict(data.get("candidates", {}))
    if fragments_dir.is_dir():
        for fragment in sorted(fragments_dir.glob("*.json")):
            payload = json.loads(fragment.read_text())
            fragment_candidates = payload.get("candidates", payload)
            overlap = candidates.keys() & fragment_candidates.keys()
            if overlap:
                raise SystemExit(f"Duplicate candidate ids in {fragment}: {', '.join(sorted(overlap))}")
            candidates.update(fragment_candidates)
    return candidates


def immutable_url(source: dict) -> str:
    if source.get("url"):
        return source["url"]
    if source.get("repository") and source.get("commit") and source.get("path"):
        return f"https://raw.githubusercontent.com/{source['repository']}/{source['commit']}/{quote(source['path'], safe='/')}"
    raise SystemExit("Source must define either url or repository+commit+path")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("candidate")
    parser.add_argument("--config", type=Path, default=Path("config/actor-candidates.json"))
    parser.add_argument("--fragments-dir", type=Path, default=Path("config/actor-candidates.d"))
    parser.add_argument("--github-env", type=Path)
    parser.add_argument("--json-out", type=Path)
    args = parser.parse_args()

    candidates = load_candidates(args.config, args.fragments_dir)
    if args.candidate not in candidates:
        raise SystemExit(f"Unknown candidate {args.candidate!r}; available: {', '.join(sorted(candidates))}")

    c = candidates[args.candidate]
    source = c["source"]
    render = c["render"]
    actions = c["actions"]
    weapon = c.get("weapon", {})
    defensive_prop = dict(c.get("defensive_prop", {}))
    path = source["path"]
    source_url = immutable_url(source)

    if defensive_prop:
        defensive_prop["url"] = immutable_url(defensive_prop)
        defensive_prop["filename"] = Path(defensive_prop["path"]).name

    filename = Path(path).name
    payload = {
        "candidate": args.candidate,
        "actor": c["actor"],
        "role": c.get("role", c["actor"]),
        "filename": filename,
        "source_url": source_url,
        "git_blob_sha": source.get("git_blob_sha", ""),
        "expected_sha256": source.get("sha256", ""),
        "size_bytes": int(source.get("size_bytes", 0) or 0),
        "source": source,
        "actions": actions,
        "weapon": weapon,
        "defensive_prop": defensive_prop,
        "render": render,
        "selection_goal": c.get("selection_goal", ""),
        "role_gate": c.get("role_gate", {}),
    }

    if args.json_out:
        args.json_out.parent.mkdir(parents=True, exist_ok=True)
        args.json_out.write_text(json.dumps(payload, indent=2) + "\n")

    if args.github_env:
        forward = weapon.get("forward", [])
        grip_offset = weapon.get("grip_offset", [])
        prop_forward = defensive_prop.get("forward", [])
        prop_offset = defensive_prop.get("grip_offset", [])
        prop_rotation = defensive_prop.get("rotation", [])
        values = {
            "ACTOR_CANDIDATE": args.candidate,
            "ACTOR": c["actor"],
            "ACTOR_ROLE": c.get("role", c["actor"]),
            "SOURCE_FILENAME": filename,
            "SOURCE_URL": source_url,
            "SOURCE_GIT_BLOB": source.get("git_blob_sha", ""),
            "SOURCE_EXPECTED_SHA256": source.get("sha256", ""),
            "SOURCE_SIZE": str(source.get("size_bytes", 0) or 0),
            "TARGET_HEIGHT": str(render["target_height"]),
            "ORTHO_SCALE": str(render["ortho_scale"]),
            "HORIZONTAL_ANCHOR": render["horizontal_anchor"],
            "PYTHONPATH": "/usr/lib/python3/dist-packages",
            "DZ_WEAPON_STYLE": weapon.get("style", ""),
            "DZ_WEAPON_BONE": weapon.get("bone", ""),
            "DZ_WEAPON_FORWARD": ",".join(str(v) for v in forward),
            "DZ_WEAPON_GRIP_OFFSET": ",".join(str(v) for v in grip_offset),
            "DZ_DEFENSIVE_PROP_URL": defensive_prop.get("url", ""),
            "DZ_DEFENSIVE_PROP_FILENAME": defensive_prop.get("filename", ""),
            "DZ_DEFENSIVE_PROP_BLOB": defensive_prop.get("git_blob_sha", ""),
            "DZ_DEFENSIVE_PROP_SHA256": defensive_prop.get("sha256", ""),
            "DZ_DEFENSIVE_PROP_SIZE": str(defensive_prop.get("size_bytes", 0) or 0),
            "DZ_DEFENSIVE_PROP_BONE": defensive_prop.get("bone", ""),
            "DZ_DEFENSIVE_PROP_ROLE": defensive_prop.get("role", ""),
            "DZ_DEFENSIVE_PROP_FORWARD": ",".join(str(v) for v in prop_forward),
            "DZ_DEFENSIVE_PROP_GRIP_OFFSET": ",".join(str(v) for v in prop_offset),
            "DZ_DEFENSIVE_PROP_ROTATION": ",".join(str(v) for v in prop_rotation),
            "DZ_DEFENSIVE_PROP_SCALE": str(defensive_prop.get("scale", 1.0)),
        }
        with args.github_env.open("a") as out:
            for key, value in values.items():
                value = str(value)
                if "\n" in value:
                    raise SystemExit(f"newline not allowed in env value {key}")
                out.write(f"{key}={value}\n")

    print(json.dumps(payload, indent=2))


if __name__ == "__main__":
    main()
