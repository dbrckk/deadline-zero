#!/usr/bin/env python3
"""Resolve a production actor candidate into deterministic CI metadata."""
from __future__ import annotations

import argparse
import json
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

    if source.get("url"):
        source_url = source["url"]
    elif source.get("repository") and source.get("commit"):
        source_url = (
            f"https://raw.githubusercontent.com/{source['repository']}/"
            f"{source['commit']}/{quote(path, safe='/')}"
        )
    else:
        raise SystemExit("Candidate source must define either url or repository+commit")

    filename = Path(path).name
    payload = {
        "candidate": args.candidate,
        "actor": c["actor"],
        "filename": filename,
        "source_url": source_url,
        "git_blob_sha": source.get("git_blob_sha", ""),
        "expected_sha256": source.get("sha256", ""),
        "size_bytes": int(source.get("size_bytes", 0) or 0),
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
            "SOURCE_URL": source_url,
            "SOURCE_GIT_BLOB": source.get("git_blob_sha", ""),
            "SOURCE_EXPECTED_SHA256": source.get("sha256", ""),
            "SOURCE_SIZE": str(source.get("size_bytes", 0) or 0),
            "TARGET_HEIGHT": str(render["target_height"]),
            "ORTHO_SCALE": str(render["ortho_scale"]),
            "HORIZONTAL_ANCHOR": render["horizontal_anchor"],
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
