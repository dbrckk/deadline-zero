# Android lifecycle and process-recreation QA

This document defines the supported recovery behavior for Deadline Zero on Android.

## Persistence contract

Long-term account state is durable and must survive app backgrounding, Activity recreation and OS process death:

- currencies and account progression
- unlocked survivors and weapon progression
- inventory and equipped items
- daily/mastery progression
- purchase receipt replay protection
- durable entitlement cache where applicable

An active combat run is intentionally ephemeral in M0. The game does not serialize enemy/projectile/world state. After OS process death the next launch must start from a safe menu state using the last durable account save; the interrupted run must not be settled, rewarded, duplicated or partially resumed.

The device-local Play entitlement cache (`deadline-zero-entitlements-v1.xml`) is intentionally excluded from Android cloud backup/device transfer because Google Play is authoritative for store ownership.

## Physical-device smoke matrix

Use a Play/internal-test or debug build on a representative Android device. Before each case record credits, gems, inventory count, equipped item IDs, selected survivor/weapon, account level, highest stage and purchase-entitlement state.

### 1. Cold start from persisted profile
1. Make a visible durable progression change and return to menu.
2. Force-stop the app.
3. Relaunch.
4. Verify all recorded durable values are unchanged.

### 2. Background / foreground
1. Start from menu and record durable values.
2. Background the app for at least 30 seconds.
3. Resume it.
4. Verify the current screen is usable, audio resumes once, and durable values are unchanged.

### 3. Activity recreation
1. Enable Developer options > Don't keep activities, or use an equivalent Activity-recreation path.
2. Navigate through menu screens and trigger recreation.
3. Verify the app returns to a safe usable state without duplicate rewards, duplicate purchases, stuck audio, or corrupted profile data.

### 4. Process death while in a run
1. Record durable account values.
2. Start a run and earn only run-local progress; do not finish the run.
3. Kill the app process from Android/ADB/Developer options.
4. Relaunch.
5. Verify the app starts at a safe menu state.
6. Verify the interrupted run produced no settlement reward, duplicate reward, stage clear, daily completion or item drop.
7. Verify the previously durable account values remain intact.

### 5. Process death immediately after completed settlement
1. Complete a run and reach the result/victory screen.
2. Immediately kill the process.
3. Relaunch.
4. Verify the completed settlement exists exactly once: no lost reward and no duplicate reward.

### 6. Fullscreen ad lifecycle
1. Trigger a rewarded/fullscreen presentation.
2. Background/foreground around presentation where the test environment permits it.
3. Verify audio suspension/resume happens once and the game does not remain permanently suspended.

### 7. Purchase lifecycle
Run the Play-license-test scenarios from `docs/PLAY_RELEASE.md`: durable restore, starter-pack idempotency, consumable receipt replay, pending purchase, reconnect and process interruption.

## Pass criteria

The lifecycle gate passes only when:

- durable profile values survive all supported transitions;
- an interrupted active run is discarded safely without settlement;
- a completed run settlement persists exactly once;
- no duplicate currency, inventory item, daily progress or entitlement grant appears;
- audio/fullscreen state cannot become permanently suspended;
- no startup crash or corrupted-profile reset occurs.

Record device model, Android version, build SHA and result for each case before closing issue #16.
