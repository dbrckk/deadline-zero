#!/usr/bin/env python3
import argparse
import json
import math
from pathlib import Path

ALLOWED = {
    "min_median_bbox_width_px": ("median_bbox_width_px", ">="),
    "min_median_bbox_height_px": ("median_bbox_height_px", ">="),
    "min_median_bbox_area_px2": ("median_bbox_area_px2", ">="),
    "max_median_bbox_width_px": ("median_bbox_width_px", "<="),
    "max_median_bbox_height_px": ("median_bbox_height_px", "<="),
    "max_median_bbox_area_px2": ("median_bbox_area_px2", "<="),
}


def _load(path):
    return json.loads(Path(path).read_text())


def validate(candidate, metrics):
    constraints = candidate.get("role_gate", {}).get("metrics", {})
    if not isinstance(constraints, dict):
        raise ValueError("role_gate.metrics must be an object")

    unknown = sorted(set(constraints) - set(ALLOWED))
    if unknown:
        raise ValueError("unknown role metric gate(s): " + ", ".join(unknown))

    checks = []
    passed = True
    for gate, threshold in sorted(constraints.items()):
        if isinstance(threshold, bool) or not isinstance(threshold, (int, float)) or not math.isfinite(threshold):
            raise ValueError(f"{gate} must be a finite number")
        metric_name, operator = ALLOWED[gate]
        if metric_name not in metrics:
            raise ValueError(f"missing silhouette metric: {metric_name}")
        actual = metrics[metric_name]
        if isinstance(actual, bool) or not isinstance(actual, (int, float)) or not math.isfinite(actual):
            raise ValueError(f"{metric_name} must be a finite number")
        ok = actual >= threshold if operator == ">=" else actual <= threshold
        checks.append({
            "gate": gate,
            "metric": metric_name,
            "operator": operator,
            "threshold": threshold,
            "actual": actual,
            "pass": ok,
        })
        passed = passed and ok

    return {
        "pass": passed,
        "constraint_count": len(checks),
        "checks": checks,
    }


def main():
    parser = argparse.ArgumentParser(description="Validate config-driven actor role silhouette metrics")
    parser.add_argument("--candidate", required=True)
    parser.add_argument("--metrics", required=True)
    parser.add_argument("--output")
    args = parser.parse_args()

    candidate = _load(args.candidate)
    metrics = _load(args.metrics)
    report = validate(candidate, metrics)
    rendered = json.dumps(report, indent=2) + "\n"
    if args.output:
        Path(args.output).write_text(rendered)
    print(rendered, end="")
    if not report["pass"]:
        raise SystemExit("Actor role metric gate failed")


if __name__ == "__main__":
    main()
