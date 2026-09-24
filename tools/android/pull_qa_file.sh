#!/usr/bin/env bash
set -euo pipefail

source_path=${1:?source path required}
destination=${2:?destination path required}
retries=${QA_PULL_RETRIES:-3}
delay=${QA_PULL_DELAY_SECONDS:-1}

mkdir -p "$(dirname "$destination")"
rm -f "$destination"

for ((attempt=1; attempt<=retries; attempt++)); do
  if adb pull "$source_path" "$destination" && test -s "$destination"; then
    exit 0
  fi
  rm -f "$destination"
  if (( attempt < retries )); then
    sleep "$delay"
    adb wait-for-device || true
  fi
done

echo "Failed to pull non-empty QA artifact after ${retries} attempts: ${source_path}" >&2
exit 1
