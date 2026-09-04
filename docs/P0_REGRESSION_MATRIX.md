# P0 Regression Proof Matrix

This matrix defines the minimum evidence required to close the remaining automated M0 regression gap.

## Automated core proof

- [x] Inventory restore preserves exclusive items when normal capacity is full.
- [x] Equipment upgrade cost/power math is overflow-safe.
- [x] Profile schema migration is explicit, ordered and downgrade-safe.
- [x] Billing grant replay is idempotent.
- [x] Consumable delivery persists before Play consumption.
- [x] Ephemeral run loadout state resets to safe defaults.
- [x] Representative settlement fixture proves one completed run updates run counters, rewards and progression exactly once (#21).
- [x] Representative reload fixture proves the settlement result survives save/load without duplication or loss (#21).
- [x] Representative interrupted-run fixture proves no settlement/reward occurs when the active run is abandoned (#21).

## Platform proof

- [x] Desktop LWJGL3 runtime smoke completes menu -> contract -> GameScreen -> settlement/RunResult -> menu, renders each state, proves exactly-once run settlement, and reloads the persisted settled profile (#24).
- [x] Android instrumentation launches the real `AndroidLauncher` successfully on an x86_64 emulator and keeps the Activity alive with attached content (#26).
- [ ] Android debug launch completes one full run to result and returns to menu on representative physical hardware.
- [ ] Android pause/resume, activity recreation and process-death matrix passes on physical hardware (#16).
- [ ] Google Play license-test billing matrix passes (#17).

## Closure rule

Automated M0 core coverage, desktop end-to-end runtime wiring and Android emulator startup are complete. Remaining rows require representative physical Android or Google Play evidence and remain explicit platform gates rather than being inferred from compilation, desktop execution, emulator startup or headless tests.
