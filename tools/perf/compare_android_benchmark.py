#!/usr/bin/env python3
import argparse
import json
import math
import sys
from pathlib import Path

MAX_AVG_FPS_DROP = 0.15
MAX_P95_INCREASE = 0.15
MAX_P99_INCREASE = 0.20
MAX_JANK_ABSOLUTE_INCREASE = 0.05

COMPARABLE_THERMAL = {"UNKNOWN", "NORMAL", "LIGHT"}

def load(path: str) -> dict:
    return json.loads(Path(path).read_text())

def finite_positive(value) -> bool:
    return isinstance(value, (int, float)) and math.isfinite(value) and value > 0

def validate(data: dict) -> None:
    required = {
        "scenario", "targetFps", "averageFps", "p95FrameMs", "p99FrameMs",
        "jankRatio", "stable", "thermalLevel", "effectiveFxQuality",
        "activeEnemies", "activeProjectiles"
    }
    missing = required - data.keys()
    if missing:
        raise ValueError(f"missing benchmark fields: {sorted(missing)}")
    if data["targetFps"] not in (60, 90, 120):
        raise ValueError(f"unsupported targetFps: {data['targetFps']}")
    for key in ("averageFps", "p95FrameMs", "p99FrameMs"):
        if not finite_positive(data[key]):
            raise ValueError(f"invalid {key}: {data[key]}")
    if not 0 <= data["jankRatio"] <= 1:
        raise ValueError(f"invalid jankRatio: {data['jankRatio']}")

def comparable(base: dict, current: dict) -> tuple[bool, str]:
    if base["scenario"] != current["scenario"]:
        return False, "scenario differs"
    if base["targetFps"] != current["targetFps"]:
        return False, "effective target FPS differs"
    if base["thermalLevel"] not in COMPARABLE_THERMAL or current["thermalLevel"] not in COMPARABLE_THERMAL:
        return False, "thermal pressure is not comparable"
    if abs(base["effectiveFxQuality"] - current["effectiveFxQuality"]) > 0.08:
        return False, "effective FX quality differs materially"
    if current["activeEnemies"] < max(1, int(base["activeEnemies"] * 0.90)):
        return False, "current workload has materially fewer active enemies"
    return True, "comparable"

def compare(base: dict, current: dict) -> dict:
    validate(current)
    try:
        validate(base)
    except ValueError as exc:
        return {
            "comparable": False,
            "reason": f"incompatible baseline: {exc}",
            "regressions": [],
            "metrics": {}
        }
    ok, reason = comparable(base, current)
    result = {
        "comparable": ok,
        "reason": reason,
        "regressions": [],
        "metrics": {}
    }
    if not ok:
        return result

    avg_drop = (base["averageFps"] - current["averageFps"]) / base["averageFps"]
    p95_inc = (current["p95FrameMs"] - base["p95FrameMs"]) / base["p95FrameMs"]
    p99_inc = (current["p99FrameMs"] - base["p99FrameMs"]) / base["p99FrameMs"]
    jank_inc = current["jankRatio"] - base["jankRatio"]

    result["metrics"] = {
        "averageFpsDrop": avg_drop,
        "p95Increase": p95_inc,
        "p99Increase": p99_inc,
        "jankAbsoluteIncrease": jank_inc
    }

    latency_regression = p95_inc > MAX_P95_INCREASE or p99_inc > MAX_P99_INCREASE
    jank_regression = jank_inc > MAX_JANK_ABSOLUTE_INCREASE

    # SwiftShader/host scheduling can depress average FPS while percentile frame times
    # remain unchanged. Treat average FPS as a corroborating signal, not a standalone gate.
    if avg_drop > MAX_AVG_FPS_DROP and (latency_regression or jank_regression):
        result["regressions"].append(
            f"average FPS dropped {avg_drop:.1%} (> {MAX_AVG_FPS_DROP:.0%}) with corroborating frame-time/jank regression"
        )
    if p95_inc > MAX_P95_INCREASE:
        result["regressions"].append(f"p95 frame time increased {p95_inc:.1%} (> {MAX_P95_INCREASE:.0%})")
    if p99_inc > MAX_P99_INCREASE:
        result["regressions"].append(f"p99 frame time increased {p99_inc:.1%} (> {MAX_P99_INCREASE:.0%})")
    if jank_regression:
        result["regressions"].append(
            f"jank ratio increased {jank_inc:.3f} (> {MAX_JANK_ABSOLUTE_INCREASE:.2f})"
        )
    return result

def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("baseline")
    parser.add_argument("current")
    parser.add_argument("--json-out")
    parser.add_argument("--require-comparable", action="store_true")
    args = parser.parse_args()

    result = compare(load(args.baseline), load(args.current))
    rendered = json.dumps(result, indent=2, sort_keys=True)
    print(rendered)
    if args.json_out:
        Path(args.json_out).write_text(rendered + "\n")

    if args.require_comparable and not result["comparable"]:
        return 2
    return 1 if result["regressions"] else 0

if __name__ == "__main__":
    sys.exit(main())
