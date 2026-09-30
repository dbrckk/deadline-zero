#!/usr/bin/env python3
import argparse
import json
import sys
from pathlib import Path

REQUIRED_PROFILE_COMBINATIONS = {
    (quality, fps)
    for quality in ("LOW", "MEDIUM", "HIGH", "ULTRA")
    for fps in (60, 90, 120)
}


def load(path: str) -> dict:
    return json.loads(Path(path).read_text())


def build_evidence(crash: dict, probe: dict, stress: dict, profiles: dict, commit: str = "") -> dict:
    errors = []

    if crash.get("ok") is not True:
        errors.append("runtime crash/ANR scan is not clean")
    if crash.get("findings"):
        errors.append("runtime crash/ANR findings are present")

    for label, data in (("probe", probe), ("stress", stress)):
        if data.get("targetFps") not in (60, 90, 120):
            errors.append(f"{label} has unsupported targetFps")
        if not isinstance(data.get("averageFps"), (int, float)) or data["averageFps"] <= 0:
            errors.append(f"{label} has invalid averageFps")
        if not isinstance(data.get("p95FrameMs"), (int, float)) or data["p95FrameMs"] <= 0:
            errors.append(f"{label} has invalid p95FrameMs")
        if not isinstance(data.get("p99FrameMs"), (int, float)) or data["p99FrameMs"] < data.get("p95FrameMs", 0):
            errors.append(f"{label} has invalid p99FrameMs")
        if not isinstance(data.get("jankRatio"), (int, float)) or not 0 <= data["jankRatio"] <= 1:
            errors.append(f"{label} has invalid jankRatio")

    rows = profiles.get("combinations", [])
    actual = {
        (row.get("quality"), row.get("requestedFps"))
        for row in rows
        if isinstance(row, dict)
    }
    if profiles.get("schemaVersion") != 1:
        errors.append("graphics profile evidence has unsupported schemaVersion")
    if actual != REQUIRED_PROFILE_COMBINATIONS:
        errors.append("graphics profile evidence does not cover all 12 supported combinations")
    for row in rows:
        if not isinstance(row, dict):
            errors.append("graphics profile row is malformed")
            continue
        if row.get("effectiveFps") != row.get("requestedFps"):
            errors.append(
                f"graphics profile {row.get('quality')}/{row.get('requestedFps')} did not initialize requested FPS"
            )
        fx = row.get("effectiveFxQuality")
        ceiling = row.get("qualityCeiling")
        if not isinstance(fx, (int, float)) or not isinstance(ceiling, (int, float)):
            errors.append("graphics profile contains invalid FX values")
        elif not 0.40 <= fx <= ceiling + 0.0001:
            errors.append(
                f"graphics profile {row.get('quality')}/{row.get('requestedFps')} violates FX ceiling"
            )

    return {
        "schemaVersion": 1,
        "commit": commit,
        "automatedRuntimeGatePassed": not errors,
        "errors": errors,
        "crashAnr": {
            "ok": crash.get("ok") is True,
            "findingCount": len(crash.get("findings", [])),
        },
        "performance": {
            "loaded": {
                "scenario": probe.get("scenario"),
                "targetFps": probe.get("targetFps"),
                "averageFps": probe.get("averageFps"),
                "p95FrameMs": probe.get("p95FrameMs"),
                "p99FrameMs": probe.get("p99FrameMs"),
                "jankRatio": probe.get("jankRatio"),
                "thermalLevel": probe.get("thermalLevel"),
                "effectiveFxQuality": probe.get("effectiveFxQuality"),
            },
            "stress": {
                "scenario": stress.get("scenario"),
                "targetFps": stress.get("targetFps"),
                "averageFps": stress.get("averageFps"),
                "p95FrameMs": stress.get("p95FrameMs"),
                "p99FrameMs": stress.get("p99FrameMs"),
                "jankRatio": stress.get("jankRatio"),
                "thermalLevel": stress.get("thermalLevel"),
                "effectiveFxQuality": stress.get("effectiveFxQuality"),
            },
        },
        "graphicsProfiles": {
            "combinationCount": len(rows),
            "covered": [
                {"quality": quality, "fps": fps}
                for quality, fps in sorted(actual)
            ],
        },
        "scope": (
            "Automated emulator/runtime evidence only. This does not replace physical-device "
            "thermal, sustained-FPS, readability, billing, Play services, or release-candidate QA."
        ),
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--crash-anr", required=True)
    parser.add_argument("--probe", required=True)
    parser.add_argument("--stress", required=True)
    parser.add_argument("--profiles", required=True)
    parser.add_argument("--commit", default="")
    parser.add_argument("--json-out", required=True)
    args = parser.parse_args()

    evidence = build_evidence(
        load(args.crash_anr),
        load(args.probe),
        load(args.stress),
        load(args.profiles),
        args.commit,
    )
    rendered = json.dumps(evidence, indent=2, sort_keys=True)
    Path(args.json_out).write_text(rendered + "\n")
    print(rendered)
    return 0 if evidence["automatedRuntimeGatePassed"] else 1


if __name__ == "__main__":
    sys.exit(main())
