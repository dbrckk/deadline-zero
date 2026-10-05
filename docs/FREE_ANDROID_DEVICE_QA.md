# Free real-device Android QA

Deadline: Zero can cover most hardware validation without purchasing a device farm subscription.

## 1. Firebase Test Lab — automated physical smoke

Firebase Test Lab's Spark plan currently includes a no-cost quota of up to **5 physical-device test runs per day** and 10 virtual-device runs per day. The repository workflow `Godot Firebase Physical QA` is manual-only so the quota cannot be consumed by normal pushes.

### One-time setup

Create or choose a Firebase/Google Cloud project and enable Test Lab. Add these protected GitHub Actions secrets:

- `FIREBASE_TEST_LAB_PROJECT_ID`
- `FIREBASE_TEST_LAB_SERVICE_ACCOUNT_JSON`

The service account should receive only the permissions needed to run/read Test Lab matrices.

### Run

Open Actions → **Godot Firebase Physical QA** → Run workflow.

Inputs:

- `device_model`: current Firebase Test Lab physical `MODEL_ID`;
- `android_version`: an `OS_VERSION_ID` supported by that model;
- `timeout`: short Robo smoke duration, default 120s.

Before selecting a model, query the live catalog:

```bash
gcloud firebase test android models list
gcloud firebase test android models describe MODEL_ID
```

The workflow builds the current Godot APK, records the selected device description, runs a landscape Robo smoke on real hardware, and uploads the matrix result JSON.

Do not schedule this workflow automatically. Keeping it manual avoids accidental quota/billing consumption.

## 2. Samsung Remote Test Lab — free interactive physical QA

Samsung Remote Test Lab is a free service for registered Samsung developers and exposes real Galaxy hardware, including current Galaxy S/Z/Tab devices. Use it for checks that Robo testing cannot judge:

- touch comfort and dead-zone feel;
- perceived frame pacing/heat during a complete run;
- audio balance through real hardware;
- haptics;
- readability of damage/pressure telegraphs;
- background/foreground behavior;
- Fold/Flip aspect-ratio and continuity checks.

Remote Debug Bridge can expose the reserved device to local `adb`, so the same diagnostic commands used by the repository Android smoke can be reused.

## Recommended free matrix

Use a small representative matrix rather than many redundant devices:

| Class | Suggested target | What it proves |
| --- | --- | --- |
| Baseline | older/mid-range physical Android in Firebase Test Lab | CPU/GPU pressure, memory, loading |
| Current flagship | recent Pixel/Galaxy physical device | current API/rendering behavior |
| Samsung flagship | Galaxy S-series through Remote Test Lab | One UI + common commercial hardware |
| Foldable | Galaxy Z Fold/Flip through Remote Test Lab | unusual landscape/aspect behavior |
| Tablet | Galaxy Tab through Remote Test Lab | large-screen HUD/readability |

Exact Firebase model IDs change over time, so query the live catalog rather than hard-coding them.

## Evidence to retain

For every release candidate record:

- commit SHA and APK SHA-256;
- device model / OS / API;
- run duration;
- launch result;
- crash/ANR outcome;
- one complete player-controlled run on at least one real device;
- background → foreground cycle;
- force-stop → relaunch;
- screenshot or video of actual gameplay;
- any thermal/frame-rate observations.

Automated Firebase success is useful evidence, but it does not replace the final player-controlled physical run or subjective readability/comfort pass.
