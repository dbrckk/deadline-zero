# Godot native 3D — physical Android performance evidence

This procedure evaluates the **native Godot** `com.deadlinezero.godot` debug APK, **not** the older libGDX `com.deadlinezero.game` app. Run on actual hardware; emulator success cannot certify mobile GPU, touch, battery, or thermal performance.

## One reproducible player-controlled session

Build/download the native Godot Android debug APK using the **Godot Android First Playable** GitHub Actions workflow. On a PC with Android Platform Tools and Python 3, connect an authorized Android phone with USB debugging enabled.

```sh
adb devices -l
adb install -r deadline-zero-godot-debug.apk
adb logcat -c
adb shell am force-stop com.deadlinezero.godot
adb shell am start -W -a android.intent.action.MAIN -p com.deadlinezero.godot
adb logcat -v threadtime > godot-physical-run.log
```

Play manually for **at least 2–5 minutes**, including a wave under pressure, boss combat where feasible, level-up selection, and movement/aiming in live combat. Press **Ctrl+C** to end the log capture. `adb logcat -c` deliberately clears the device's log buffer before this session, so do not use it if other device logs must be preserved.

Then retain identifying evidence, a real screenshot and a machine-readable native performance gate:

```sh
adb shell getprop ro.product.model
adb shell getprop ro.build.version.release
adb shell getprop ro.build.version.sdk
adb exec-out screencap -p > godot-physical-run.png
python3 tools/android/scan_runtime_log.py godot-physical-run.log \
  --package com.deadlinezero.godot --godot-perf \
  --min-perf-snapshots 6 --max-p95-ms 33.33 \
  --max-hitch-pct 5 --min-fps 25 \
  --json-out godot-physical-qa.json
```

Run the Python command from the repository root; it **exits with code 1** on a process crash/ANR, missing or malformed `DZ_PERF` records, or a frame-budget failure. The given limits are provisional **30 FPS-class diagnostic gates** only. For a 60 FPS-class device use an explicitly agreed tighter budget (for example `--max-p95-ms 16.67 --min-fps 55`), but do not equate a green threshold check with release approval.

## Interpreting the result

- `godot_perf.snapshots`: valid debug readings, emitted about every five game-time seconds; at least six are required above.
- `worst_window_p95_ms`: worst **per-window p95** from a rolling 240-frame ring, not a pooled full-session percentile.
- `worst_window_hitch_pct`: largest fraction of sampled intervals above 33.333 ms in any reported window.
- `lowest_reported_fps`: lowest Godot engine FPS readout in the captured snapshots.
- `peak_enemy_count`: peak observed on-screen gameplay pressure proxy; include the actual scenario when filing a finding.
- `peak_godot_static_memory_mb`: Godot's static-memory metric; **not** Android system PSS or thermal load.

Godot discards frame intervals above 250 ms to avoid measuring resumed-app lifecycle gaps as normal frame pacing. Capture a **separate log for background/foreground and force-stop/relaunch testing** to retain those diagnostic events.

Attach the JSON, relevant (potentially sensitive) log lines, PNG, actual device model/API/OS, APK SHA-256, commit SHA, measured session duration and human observations to the release issue. Avoid publishing unfiltered personal-device logcat publicly. Log parsing does **not** replace touch comfort, thermal throttling, sustained play, crash-free lifecycle, privacy or Play Store sign-off.

An offline saved log can be reanalyzed without a device using the same Python command. This path uses only free Android Platform Tools and Python standard library.
