#!/usr/bin/env python3
"""Resolve a production actor candidate into deterministic CI metadata."""
from __future__ import annotations

import argparse
import json
import shlex
from pathlib import Path
from urllib.parse import quote


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("candidate")
    parser.add_argument("--config", type=Path, default=Path("config/actor-candidates.json"))
    parser.add_argument("--github-env", type=Path)
    parser.add_argument("--json-out", type=Path)
    args = parser.parse_args()

    data = json.loads(args.config.read_text())
    candidates = data.get("candidates", {})
    if args.candidate not in candidates:
        raise SystemExit(f"Unknown candidate {args.candidate!r}; available: {', '.join(sorted(candidates))}")

    c = candidates[args.candidate]
    source = c["source"]
    render = c["render"]
    actions = c["actions"]
    path = source["path"]
    raw_url = (
        f"https://raw.githubusercontent.com/{source['repository']}/"
        f"{source['commit']}/{quote(path, safe='/')}"
    )
    filename = Path(path).name
    payload = {
        "candidate": args.candidate,
        "actor": c["actor"],
        "filename": filename,
        "raw_url": raw_url,
        "git_blob_sha": source["git_blob_sha"],
        "size_bytes": int(source["size_bytes"]),
        "source": source,
        "actions": actions,
        "render": render,
        "selection_goal": c.get("selection_goal", ""),
    }

    if args.json_out:
        args.json_out.parent.mkdir(parents=True, exist_ok=True)
        args.json_out.write_text(json.dumps(payload, indent=2) + "\n")

    if args.github_env:
        values = {
            "ACTOR_CANDIDATE": args.candidate,
            "ACTOR": c["actor"],
            "SOURCE_FILENAME": filename,
            "SOURCE_URL": raw_url,
            "SOURCE_GIT_BLOB": source["git_blob_sha"],
            "SOURCE_SIZE": str(source["size_bytes"]),
            "TARGET_HEIGHT": str(render["target_height"]),
            "ORTHO_SCALE": str(render["ortho_scale"]),
            "HORIZONTAL_ANCHOR": render["horizontal_anchor"],
        }
        with args.github_env.open("a") as out:
            for key, value in values.items():
                if "\n" in value:
                    raise SystemExit(f"newline not allowed in env value {key}")
                out.write(f"{key}={value}\n")

    print(json.dumps(payload, indent=2))


if __name__ == "__main__":
    main()
