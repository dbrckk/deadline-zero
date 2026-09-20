# Deadline Zero — Production Status

This file is the operational source of truth for release progress. `docs/ROADMAP.md` remains the strategic roadmap.

## Current milestone

**Parallel closure — M0/M1 physical proof + M2/M7 hardening**

Goal: preserve a green production baseline while closing the remaining physical-device/platform gates, performance calibration and Play release-readiness work.

### M0 exit criteria

- [x] Inventory restore regression fixed and merged (#2).
- [x] Equipment upgrade overflow regression fixed and merged (#3).
- [x] Pre-existing PR backlog classified: #4/#5 closed as non-canonical generated reference art; #6 merged.
- [x] Latest hardened `main` Verify passes core, Android build and Android emulator runtime jobs.
- [ ] No known P0 data-loss, economy-corruption, crash-loop, save-migration, billing, or startup blocker remains open.
- [x] Desktop and Android emulator builds are proven through the complete automated playable loop; representative physical Android gameplay remains a device gate.
- [x] Strict release-bundle path is documented in `docs/PLAY_RELEASE.md` and enforced by `:android:bundlePlayRelease`.
- [x] Automated P0 regression suite proves save/load, inventory, upgrades, progression, purchase boundaries and game-loop settlement.

## Active M0 proof tasks

- [x] #15 — explicit profile schema versioning and migration protection merged via #18.
- [ ] #16 — lifecycle/process recreation: automated background/foreground, Activity recreation, external process restart and durable profile reload now pass on emulator; representative physical-device lifecycle/process-death matrix remains.
- [ ] #17 — Play Billing exactly-once: automated idempotency and `grant -> persist -> consume` proof merged via #19; Play license-test device validation remains.
- [x] Automated vertical-slice proof — desktop end-to-end runtime merged via #24 and Android emulator end-to-end runtime merged via #32.
- [ ] Physical vertical-slice proof — one complete player-controlled Android run on representative hardware remains required.
- [x] P0 persistence/regression proof — representative completed-run, reload and interrupted-run fixtures merged via #21; Android emulator process-death persistence proof merged via #30.

## Milestone board

| Milestone | Goal | State | Exit signal |
|---|---|---|---|
| M0 — Repository Green | Stable baseline | IN PROGRESS | Clean CI + no known P0 blockers |
| M1 — Vertical Slice Complete | Full run from launch to end | NOT STARTED | One complete Android run without critical fallback |
| M2 — Combat Production | Production-grade combat systems | IN PROGRESS | Systems complete + stress-tested |
| M3 — Final Art Alpha | Deterministic final-art pipeline | COMPLETE | 24/24 production actors merged and atlas contract complete |
| M4 — Meta Complete | Durable progression/meta | IN PROGRESS | Save-safe progression loop complete |
| M5 — Content Complete | Release content target reached | IN PROGRESS | Count targets reached; balance/pacing regression remains |
| M6 — Economy Complete | Monetization + compliance | IN PROGRESS | Automated boundaries complete; real Play/ads validation remains |
| M7 — Release Candidate | Store-ready quality | IN PROGRESS | Automated release contracts/gates complete; manual Play/device gates remain |

## Execution order

1. Finish M0 repository correctness and proof.
2. Prove M1 vertical slice on representative Android hardware.
3. Stabilize M2 combat systems.
4. Run M3 final-art production in parallel after the Rex quality bar is locked.
5. Complete M4 meta/progression/save hardening.
6. Scale M5 content only after systems stabilize.
7. Productionize/tune M6 economy.
8. Complete M7 performance, accessibility, localization, store and release certification.

## Confirmed strengths

- Profile persistence has explicit schema versioning, ordered migration handling and future-schema downgrade protection.
- Inventory restore preserves exclusive capacity behavior and upgrade costs are overflow-safe.
- Runtime profile state is saved on settlement, pause and dispose.
- Lifecycle cleanup clears ephemeral run state before durable menu/shutdown states.
- Completed-run settlement is covered by an exactly-once regression fixture.
- Settlement persistence is covered by save/reload regression without duplication or loss.
- Interrupted runs are covered by a regression proving no settlement rewards are granted.
- Desktop LWJGL3/Xvfb runtime exercises menu -> contract -> `GameScreen` -> settlement/`RunResult` -> menu, renders each stage, verifies exactly-once settlement and reloads the persisted result (#24).
- Android instrumentation launches the real `AndroidLauncher` on an x86_64 emulator and exercises background/foreground plus Activity recreation with attached libGDX content.
- Android emulator CI force-stops the installed app and proves the real launcher can create a fresh app process (#29).
- Android emulator CI writes distinctive state through the real `DeadlineZeroGame.saveProfile()` path, kills the app process, then proves a fresh launcher reloads run counters, kill counters, stage state and purchase-receipt replay protection from durable profile storage (#30).
- Android instrumentation now drives the real `AndroidLauncher`/`DeadlineZeroGame` through menu -> contract -> `GameScreen` -> settlement/`RunResultScreen` -> durable reload -> menu and proves exactly-once settlement on emulator (#32).
- Consumable purchase receipt IDs are persisted with replay protection.
- Consumable delivery persists the grant before asking Google Play to consume the token.
- Durable billing entitlements have authoritative reconciliation plus an offline cache; entitlement prefs are excluded from Android backup/transfer.
- The strict Play release task rejects test AdMob IDs, missing production assets, malformed privacy/signing/version config and inadequate store graphics.
- CI validates core tests, real desktop LWJGL3 runtime smoke, Android lint/build, Android emulator vertical slice/lifecycle/process persistence and final-sprite validators.

## Runtime contract

The intended loop is:

`launch -> menu -> loadout/contract -> GameScreen -> run objective -> settlement -> Victory/RunResult -> persisted profile`

Active combat-run restoration after OS process death is intentionally unsupported in M0. Durable profile state must survive; an interrupted run must abort safely without duplicate rewards or partial settlement.

## Quality gates

### Build / CI

- Core compile + tests pass.
- Desktop compile and LWJGL3 runtime smoke pass.
- Android lint debug/release passes.
- Android debug APK assembles and release AAB bundles in verification mode.
- Android instrumentation launches the real app successfully on emulator.
- Android emulator completes the automated end-to-end vertical slice and proves persisted settlement reload.
- Android emulator runtime survives Activity recreation, external process restart and durable-profile reload checks.
- Final sprite layout validators pass.

### Runtime

- Desktop cold launch and complete automated loop succeed.
- Android cold launch and complete automated loop succeed on emulator.
- New and existing profiles load safely.
- Android durable profile state survives an externally forced process restart on emulator.
- Android complete player-controlled run can start, progress, pause/resume, end and persist on representative physical hardware.
- Background/foreground and activity recreation do not corrupt durable state.
- Process death returns to a supported safe state.

### Save / economy

- Save schema is explicit and migration-safe.
- Inventory limits/exclusive capacity survive reload.
- Currency and upgrade math remain overflow-safe.
- Settlement cannot grant twice.
- Completed settlement survives save/reload without duplication or loss.
- Interrupted runs cannot grant settlement rewards.
- Purchases cannot duplicate grants on retry/restore.
- Profile purchase-receipt replay protection survives Android process restart.
- Durable entitlement revocation follows authoritative Play state.

### Art

Generated/bootstrap art remains fallback/reference only. Per-asset state:

`NOT_STARTED -> SOURCE_READY -> GENERATED_REFERENCE -> PRODUCTION_SOURCE -> SLICED -> MACHINE_VALIDATED -> IN_GAME_QA -> FINAL`

Only `FINAL` counts toward release-art completion.

## M2 automated performance evidence

- [x] Android telemetry captures average FPS, p95/p99 frame time, jank, thermal state, FX quality, enemy/projectile load and spatial occupancy.
- [x] Pull requests compare the standard 40-enemy Android benchmark against the current `main` baseline with a regression gate.
- [x] Spatial broad-phase is shared by targeting, collision and ability queries; rebuild cost and hot-path allocations have been reduced.
- [x] Deterministic Android stress scenario covers a 160-enemy horde plus 180 active projectiles and publishes `performance-stress.json`.
- [x] Stress telemetry sanity/workload-retention checks pass in CI.
- [ ] Calibrate absolute low/medium/high device budgets on representative physical Android hardware before declaring M2 complete.

## Cross-milestone progress snapshot

- **P2 environment art:** 0/70 biome-specific production regions are authored; a 5-biome × 14-slot contract, runtime override path, deterministic packer and CI coverage reporter now define the closure path. Generic Pixmap/bootstrap environment art remains a development fallback and does not count toward the 70-slot target.
- **M3 art:** 24/24 production actors are merged to `main`; production atlas/layout validation is automated.
- **M5 content:** 5/5 biomes, 12/12+ weapons, 52/50+ standard upgrades, 6/6 bosses, 22/20+ gameplay enemy profiles and 8/8+ champion profiles are implemented. Final balance/pacing regression remains.
- **M6 economy/compliance:** billing idempotency/persistence boundaries, entitlement reconciliation, rewarded-ad/consent integration and strict production configuration checks exist; licensed Play purchase/restore/refund/revocation and production ad-consent validation remain manual platform gates.
- **M7 release:** canonical listing copy, listing validation, Data Safety contract, Play Console contract, versioned release notes, release-readiness matrix, strict Play bundle configuration checks and machine-readable release evidence are implemented.
- **Performance:** standard Android regression benchmark plus deterministic 160-enemy/180-projectile stress benchmark are automated and archived.
- **Remaining hard blockers:** representative physical-device gameplay/lifecycle/performance validation, real Play Billing/Play Games/AdMob configuration, final Store graphics, public privacy policy, Play Console declarations/testing and pre-launch/vitals review.

## Current next actions

1. Prove one complete player-controlled Android debug loop on representative physical hardware.
2. Execute the remaining #16 physical lifecycle/process-death matrix.
3. Execute #17 Google Play license-testing matrix.
4. Re-evaluate the remaining generic P0-blocker criterion after those platform gates.
5. Do not start major M1/M2 scope until remaining M0 proof tasks are resolved or explicitly accepted as physical-device gates.

## Definition of Done

Compilation is not runtime validation. Generated art is not final production art. Emulator vertical-slice/lifecycle/process-persistence proof substantially improves Android confidence but does not substitute for physical-device touch/input/performance gameplay, representative physical lifecycle/process-death validation or Google Play license testing where real platform behavior is the subject of the gate.
