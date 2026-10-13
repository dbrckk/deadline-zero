#!/usr/bin/env bash
# Real physical Android evidence for the native Godot game, not the legacy libGDX package.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
PACKAGE="com.deadlinezero.godot"
APK="${1:-}"
OUTPUT="${2:-build/godot-physical-qa/$(date +%Y%m%d-%H%M%S)}"

if [[ -z "$APK" || ! -s "$APK" ]]; then
  echo "Usage: $0 /path/to/deadline-zero-godot-debug.apk [output-directory]" >&2
  exit 2
fi
for command_name in adb python3; do
  if ! command -v "$command_name" >/dev/null 2>&1; then
    echo "Missing required command: $command_name" >&2
    exit 2
  fi
done
if [[ "$(adb get-state 2>/dev/null)" != "device" ]]; then
  echo "Connect one authorized physical Android phone (USB debugging; ANDROID_SERIAL if needed)." >&2
  exit 2
fi

mkdir -p "$OUTPUT"
python3 - "$APK" "$OUTPUT/apk-sha256.txt" <<'PY'
import hashlib
import pathlib
import sys
apk = pathlib.Path(sys.argv[1])
digest = hashlib.sha256()
with apk.open("rb") as source:
    for block in iter(lambda: source.read(1024 * 1024), b""):
        digest.update(block)
pathlib.Path(sys.argv[2]).write_text(f"{digest.hexdigest()}  {apk.name}\n")
PY

{
  printf 'Package: %s\n' "$PACKAGE"
  printf 'Repository commit: %s\n' "$(git -C "$ROOT" rev-parse HEAD 2>/dev/null || echo unavailable)"
  printf 'Device model: %s\n' "$(adb shell getprop ro.product.model | tr -d '\r')"
  printf 'Android version: %s\n' "$(adb shell getprop ro.build.version.release | tr -d '\r')"
  printf 'API level: %s\n' "$(adb shell getprop ro.build.version.sdk | tr -d '\r')"
  printf 'Device serial: %s\n' "$(adb get-serialno | tr -d '\r')"
} > "$OUTPUT/device.txt"

echo "Installing actual Godot Android APK on selected device..."
adb install -r "$APK"
adb shell pm path "$PACKAGE" | grep -q 'package:'
adb shell am force-stop "$PACKAGE"
# This clears the device's prior log buffer; only do this on an explicitly
# authorized QA device. Captured logcat stays local and may contain private data.
adb logcat -c
LOG_PID=""
stop_capture() {
  if [[ -n "$LOG_PID" ]]; then
    kill "$LOG_PID" 2>/dev/null || true
    wait "$LOG_PID" 2>/dev/null || true
    LOG_PID=""
  fi
}
trap stop_capture EXIT
adb logcat -v threadtime > "$OUTPUT/logcat.txt" &
LOG_PID=$!
adb shell am start -W -a android.intent.action.MAIN -p "$PACKAGE" | tee "$OUTPUT/launch.txt"
date -u +%Y-%m-%dT%H:%M:%SZ > "$OUTPUT/session-start-utc.txt"

echo "Play the real 3D game for at least 2–5 minutes with movement, pressure and upgrades."
echo "Keep the app foregrounded; press ENTER here when the player-controlled session ends."
read -r -p "Finish capture: " _
date -u +%Y-%m-%dT%H:%M:%SZ > "$OUTPUT/session-end-utc.txt"
stop_capture

adb exec-out screencap -p > "$OUTPUT/real-phone-screen.png"
adb shell dumpsys thermalservice > "$OUTPUT/thermalservice.txt" 2>&1 || true
adb shell dumpsys battery > "$OUTPUT/battery.txt" 2>&1 || true

python3 "$ROOT/tools/android/scan_runtime_log.py" "$OUTPUT/logcat.txt" \
  --package "$PACKAGE" --godot-perf \
  --min-perf-snapshots "${DZ_MIN_SNAPSHOTS:-6}" \
  --max-p95-ms "${DZ_MAX_P95_MS:-33.33}" \
  --max-hitch-pct "${DZ_MAX_HITCH_PCT:-5}" \
  --min-fps "${DZ_MIN_FPS:-25}" \
  --json-out "$OUTPUT/godot-physical-qa.json"
echo "Native physical Android evidence saved to: $OUTPUT"
echo "Review on-phone touch, real GPU output and thermal behavior manually; a passing gate is not Play certification."
