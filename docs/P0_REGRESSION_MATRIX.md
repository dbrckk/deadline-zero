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

- [ ] Desktop debug launch completes one full run to result and returns to menu.
- [ ] Android debug launch completes one full run to result and returns to menu.
- [ ] Android pause/resume, activity recreation and process-death matrix passes (#16).
- [ ] Google Play license-test billing matrix passes (#17).

## Closure rule

Automated M0 regression coverage is complete. The remaining rows require end-to-end runtime or real Android/Google Play evidence and remain explicit platform gates rather than being inferred from compilation or headless tests.
