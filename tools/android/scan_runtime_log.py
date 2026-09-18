#!/usr/bin/env python3
import argparse
import json
import re
import sys
from pathlib import Path

DEFAULT_PACKAGE = "com.deadlinezero.game"

def scan(text: str, package: str = DEFAULT_PACKAGE) -> dict:
    lines = text.splitlines()
    findings = []

    anr = re.compile(rf"\bANR in {re.escape(package)}\b", re.IGNORECASE)
    process = re.compile(rf"\bProcess:\s*{re.escape(package)}\b", re.IGNORECASE)
    fatal_exception = re.compile(r"\bFATAL EXCEPTION\b")
    native_fatal = re.compile(r"\bFatal signal\s+(?:6|11)\b", re.IGNORECASE)
    package_token = re.compile(re.escape(package), re.IGNORECASE)

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
            if package_token.search(window):
                findings.append({"type": "native_crash", "line": i + 1, "text": line.strip()})

    return {"package": package, "ok": not findings, "findings": findings}

def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("logcat")
    parser.add_argument("--package", default=DEFAULT_PACKAGE)
    parser.add_argument("--json-out")
    args = parser.parse_args()

    result = scan(Path(args.logcat).read_text(errors="replace"), args.package)
    rendered = json.dumps(result, indent=2, sort_keys=True)
    print(rendered)
    if args.json_out:
        Path(args.json_out).write_text(rendered + "\n")
    return 0 if result["ok"] else 1

if __name__ == "__main__":
    sys.exit(main())
