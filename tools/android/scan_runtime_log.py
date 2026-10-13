#!/usr/bin/env python3
import argparse
import json
import re
import sys
from pathlib import Path

DEFAULT_PACKAGE = "com.deadlinezero.game"

def _exact_package(pattern_prefix: str, package: str) -> re.Pattern:
    # Require a delimiter that cannot continue an Android package name. This prevents
    # com.deadlinezero.game.test from being mistaken for com.deadlinezero.game.
    return re.compile(
        rf"{pattern_prefix}{re.escape(package)}(?=$|[\s,():/])",
        re.IGNORECASE | re.MULTILINE,
    )

def scan(text: str, package: str = DEFAULT_PACKAGE) -> dict:
    lines = text.splitlines()
    findings = []

    anr = _exact_package(r"\bANR in\s+", package)
    process = _exact_package(r"\bProcess:\s*", package)
    cmdline = _exact_package(r"\bCmdline:\s*", package)
    fatal_exception = re.compile(r"\bFATAL EXCEPTION\b")
    native_fatal = re.compile(r"\bFatal signal\s+(?:6|11)\b", re.IGNORECASE)

    for i, line in enumerate(lines):
        if anr.search(line):
            findings.append({"type": "anr", "line": i + 1, "text": line.strip()})

        if fatal_exception.search(line):
            window = "\n".join(lines[i:min(len(lines), i + 8)])
            if process.search(window):
                findings.append({"type": "java_crash", "line": i + 1, "text": line.strip()})

        if native_fatal.search(line):
            start = max(0, i - 3)
            end = min(len(lines), i + 8)
            window = "\n".join(lines[start:end])
            if cmdline.search(window) or process.search(window):
                findings.append({"type": "native_crash", "line": i + 1, "text": line.strip()})

    return {"package": package, "ok": not findings, "findings": findings}


GODOT_PERF_KEYS = {
    "fps", "p95_ms", "max_ms", "hitch_pct", "samples", "mem_mb",
    "enemies", "projectiles", "hostile", "xp", "elapsed",
}


def summarize_godot_perf(
    text: str,
    min_snapshots: int = 6,
    max_p95_ms: float = 33.33,
    max_hitch_pct: float = 5.0,
    min_fps: float = 25.0,
) -> dict:
    """Evaluate actual Godot debug DZ_PERF windows; never infer a device-wide p95."""
    import math

    windows = []
    malformed = 0
    for line in text.splitlines():
        if "DZ_PERF " not in line:
            continue
        fields = {}
        for token in line.split("DZ_PERF ", 1)[1].split():
            if "=" in token:
                key, value = token.split("=", 1)
                fields[key] = value
        if not GODOT_PERF_KEYS.issubset(fields):
            malformed += 1
            continue
        try:
            window = {key: float(fields[key]) for key in GODOT_PERF_KEYS}
        except ValueError:
            malformed += 1
            continue
        if (
            not all(math.isfinite(value) for value in window.values())
            or not 0.0 < window["fps"] <= 1000.0
            or not 0.0 < window["p95_ms"] <= window["max_ms"] <= 250.0
            or not 0.0 <= window["hitch_pct"] <= 100.0
            or not 1.0 <= window["samples"] <= 240.0
            or any(window[key] < 0.0 for key in ("mem_mb", "enemies", "projectiles", "hostile", "xp", "elapsed"))
        ):
            malformed += 1
            continue
        windows.append(window)

    errors = []
    if not all(math.isfinite(n) and n >= 0 for n in (max_p95_ms, max_hitch_pct, min_fps)):
        errors.append("Invalid non-finite or negative performance limit")
    if min_snapshots < 1:
        errors.append("min_snapshots must be at least 1")
    if len(windows) < max(1, min_snapshots):
        errors.append(f"Only {len(windows)} valid DZ_PERF snapshots; need {max(1, min_snapshots)}")
    if malformed:
        errors.append(f"{malformed} malformed DZ_PERF snapshots")
    summary = {
        "snapshots": len(windows),
        "malformed_snapshots": malformed,
        "worst_window_p95_ms": None,
        "worst_window_max_ms": None,
        "worst_window_hitch_pct": None,
        "lowest_reported_fps": None,
        "peak_enemy_count": None,
        "peak_godot_static_memory_mb": None,
        "elapsed_first_s": None,
        "elapsed_last_s": None,
        "scope": (
            "Godot debug process on the captured device. Windows overlap and are not an "
            "aggregate frame percentile. Godot static memory is not Android process PSS. "
            "Excludes >250ms lifecycle gaps; no thermal, touch, or Play certification."
        ),
    }
    if windows:
        summary.update({
            "worst_window_p95_ms": round(max(w["p95_ms"] for w in windows), 3),
            "worst_window_max_ms": round(max(w["max_ms"] for w in windows), 3),
            "worst_window_hitch_pct": round(max(w["hitch_pct"] for w in windows), 3),
            "lowest_reported_fps": round(min(w["fps"] for w in windows), 3),
            "peak_enemy_count": int(max(w["enemies"] for w in windows)),
            "peak_godot_static_memory_mb": round(max(w["mem_mb"] for w in windows), 3),
            "elapsed_first_s": min(w["elapsed"] for w in windows),
            "elapsed_last_s": max(w["elapsed"] for w in windows),
        })
        if summary["worst_window_p95_ms"] > max_p95_ms:
            errors.append(f"Worst window p95 exceeds {max_p95_ms} ms")
        if summary["worst_window_hitch_pct"] > max_hitch_pct:
            errors.append(f"Worst window hitch ratio exceeds {max_hitch_pct}%")
        if summary["lowest_reported_fps"] < min_fps:
            errors.append(f"Reported FPS falls below {min_fps}")
    summary["ok"] = not errors
    summary["errors"] = errors
    summary["limits"] = {
        "min_snapshots": min_snapshots,
        "max_p95_ms": max_p95_ms,
        "max_hitch_pct": max_hitch_pct,
        "min_fps": min_fps,
    }
    return summary


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("logcat")
    parser.add_argument("--package", default=DEFAULT_PACKAGE)
    parser.add_argument("--json-out")
    parser.add_argument("--godot-perf", action="store_true", help="Validate real Godot DZ_PERF debug frame windows")
    parser.add_argument("--min-perf-snapshots", type=int, default=6)
    parser.add_argument("--max-p95-ms", type=float, default=33.33)
    parser.add_argument("--max-hitch-pct", type=float, default=5.0)
    parser.add_argument("--min-fps", type=float, default=25.0)
    args = parser.parse_args()

    log_text = Path(args.logcat).read_text(errors="replace")
    result = scan(log_text, args.package)
    if args.godot_perf:
        result["godot_perf"] = summarize_godot_perf(
            log_text, args.min_perf_snapshots, args.max_p95_ms,
            args.max_hitch_pct, args.min_fps,
        )
        result["ok"] = result["ok"] and result["godot_perf"]["ok"]
    rendered = json.dumps(result, indent=2, sort_keys=True)
    print(rendered)
    if args.json_out:
        Path(args.json_out).write_text(rendered + "\n")
    return 0 if result["ok"] else 1

if __name__ == "__main__":
    sys.exit(main())
