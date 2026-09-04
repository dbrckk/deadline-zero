# Deadline Zero — Production Status

This file is the operational source of truth for release progress. `docs/ROADMAP.md` remains the strategic roadmap.

## Current milestone

**M0 — Repository Green**

Goal: make `main` a reliable production baseline before adding more scope.

### M0 exit criteria

- [x] Inventory restore regression fixed and merged (#2).
- [x] Equipment upgrade overflow regression fixed and merged (#3).
- [x] Pre-existing PR backlog classified: #4/#5 closed as non-canonical generated reference art; #6 merged.
- [x] Latest hardened `main` Verify passes core and Android jobs.
- [ ] No known P0 data-loss, economy-corruption, crash-loop, save-migration, billing, or startup blocker remains open.
- [ ] Desktop and Android debug builds are proven through a complete playable loop.
- [x] Strict release-bundle path is documented in `docs/PLAY_RELEASE.md` and enforced by `:android:bundlePlayRelease`.
- [x] Automated P0 regression suite proves save/load, inventory, upgrades, progression, purchase boundaries and game-loop settlement.

## Active M0 proof tasks

- [x] #15 — explicit profile schema versioning and migration protection merged via #18.
- [ ] #16 — lifecycle/process recreation: automated hardening and repeatable QA procedure complete; physical-device lifecycle matrix remains.
- [ ] #17 — Play Billing exactly-once: automated idempotency and `grant -> persist -> consume` proof merged via #19; Play license-test device validation remains.
- [ ] Vertical-slice proof — desktop runtime path is now proven end-to-end via #24; Android physical runtime proof is still required.
- [x] P0 persistence/regression proof — representative completed-run, reload and interrupted-run fixtures merged via #21 and pass CI.

## Milestone board

| Milestone | Goal | State | Exit signal |
|---|---|---|---|
| M0 — Repository Green | Stable baseline | IN PROGRESS | Clean CI + no known P0 blockers |
| M1 — Vertical Slice Complete | Full run from launch to end | NOT STARTED | One complete Android run without critical fallback |
| M2 — Combat Production | Production-grade combat systems | NOT STARTED | Systems complete + stress-tested |
| M3 — Final Art Alpha | Deterministic final-art pipeline | NOT STARTED | Rex bar passed, production atlas expanding |
| M4 — Meta Complete | Durable progression/meta | NOT STARTED | Save-safe progression loop complete |
| M5 — Content Complete | Release content target reached | NOT STARTED | Content matrix complete and balanced |
| M6 — Economy Complete | Monetization + compliance | NOT STARTED | Purchases/ads/consent verified end-to-end |
| M7 — Release Candidate | Store-ready quality | NOT STARTED | Closed-test RC meets launch gates |

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
- Desktop LWJGL3/Xvfb runtime now exercises menu -> contract -> GameScreen -> settlement/RunResult -> menu, renders each stage, verifies exactly-once settlement and reloads the persisted result (#24).
- Consumable purchase receipt IDs are persisted with replay protection.
- Consumable delivery now persists the grant before asking Google Play to consume the token.
- Durable billing entitlements have authoritative reconciliation plus an offline cache; entitlement prefs are excluded from Android backup/transfer.
- The strict Play release task rejects test AdMob IDs, missing production assets, malformed privacy/signing/version config and inadequate store graphics.
- CI validates core tests, real desktop LWJGL3 runtime smoke, Android lint/build and final-sprite validators.

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
- Final sprite layout validators pass.

### Runtime

- Cold launch succeeds.
- New and existing profiles load safely.
- Desktop complete loop is proven in automated LWJGL3 runtime smoke.
- Android complete run can start, progress, pause/resume, end and persist on representative hardware.
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
- Durable entitlement revocation follows authoritative Play state.

### Art

Generated/bootstrap art remains fallback/reference only. Per-asset state:

`NOT_STARTED -> SOURCE_READY -> GENERATED_REFERENCE -> PRODUCTION_SOURCE -> SLICED -> MACHINE_VALIDATED -> IN_GAME_QA -> FINAL`

Only `FINAL` counts toward release-art completion.

## Current next actions

1. Prove a complete Android debug loop on a physical device.
2. Execute #16 lifecycle matrix on a physical Android device.
3. Execute #17 Google Play license-testing matrix.
4. Re-evaluate the remaining generic P0-blocker criterion after those platform gates.
5. Do not start major M1/M2 scope until remaining M0 proof tasks are resolved or explicitly accepted as physical-device gates.

## Definition of Done

Compilation is not runtime validation. Generated art is not final production art. Automated desktop runtime proof does not substitute for Android lifecycle or Google Play device validation where platform behavior is the subject of the test.
