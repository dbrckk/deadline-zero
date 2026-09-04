# Deadline Zero — Production Status

This file is the operational source of truth for release progress. `docs/ROADMAP.md` remains the strategic roadmap; this document tracks what is active, blocked, validated, and next.

## Current milestone

**M0 — Repository Green**

Goal: make `main` a reliable production baseline before adding more scope.

### M0 exit criteria

- [x] Inventory restore regression fixed and merged (#2).
- [x] Equipment upgrade overflow regression fixed and merged (#3).
- [ ] All remaining open PRs classified as merge / rework / close.
- [ ] Latest `main` Verify workflow passes core and Android jobs.
- [ ] No known P0 data-loss, economy-corruption, crash-loop, save-migration, billing, or startup blocker remains open.
- [ ] Desktop and Android debug builds launch into a complete playable loop.
- [ ] Release bundle can be produced from a clean checkout using documented commands.
- [ ] P0 regression suite covers save/load, inventory capacity, upgrades, purchases boundaries, progression, and game-loop completion.

## Milestone board

| Milestone | Goal | State | Exit signal |
|---|---|---|---|
| M0 — Repository Green | Stable baseline | IN PROGRESS | Clean CI + no known P0 blockers |
| M1 — Vertical Slice Complete | Full run from launch to end | NOT STARTED | One complete Android run without critical fallback |
| M2 — Combat Production | Production-grade combat systems | NOT STARTED | Systems complete + stress-tested |
| M3 — Final Art Alpha | Deterministic final-art pipeline | NOT STARTED | Reference bar passed, production atlas expanding |
| M4 — Meta Complete | Durable progression/meta | NOT STARTED | Save-safe progression loop complete |
| M5 — Content Complete | Release content target reached | NOT STARTED | Content matrix complete and balanced |
| M6 — Economy Complete | Monetization + compliance | NOT STARTED | Purchases/ads/consent verified end-to-end |
| M7 — Release Candidate | Store-ready quality | NOT STARTED | Closed-test RC meets launch gates |

## Execution order

Work should follow this dependency order unless a blocker explicitly requires otherwise:

1. M0 repository health and correctness.
2. M1 complete vertical slice.
3. M2 combat systems stabilization.
4. M3 final-art production in parallel with M2 after the Rex quality bar is locked.
5. M4 meta/progression/save hardening.
6. M5 content scale only after systems are stable.
7. M6 economy/monetization after progression values are sufficiently stable to tune.
8. M7 performance, accessibility, localization, store, testing, and release certification.

## Active blockers / risks

### Open PR classification

- PR #4 — Leaper generated sheet 8 reference. Reference/source material only; does not count as production art.
- PR #5 — Leaper generated sheet 10 reference. Reference/source material only; does not count as production art.

Before merging either art PR, confirm that the files are useful references, correctly licensed/owned, reasonably sized, and do not create the false impression that production atlas coverage has increased.

### Art production risk

`art_sources/PRODUCTION_ART_ROADMAP.md` and `docs/final-art-production-spec.md` correctly define bootstrap/generated material as fallback/reference only. Release progress must therefore distinguish source/reference generation from final authored/sliced/validated atlas delivery.

Recommended per-asset state machine:

`NOT_STARTED -> SOURCE_READY -> GENERATED_REFERENCE -> PRODUCTION_SOURCE -> SLICED -> MACHINE_VALIDATED -> IN_GAME_QA -> FINAL`

Only `FINAL` counts toward release-art completion.

### Scope risk

Do not scale content before combat, save, progression, and performance contracts are stable. Content multiplied across unstable systems causes rework in balance, QA, assets, and persistence.

## Quality gates

### Build / CI

- Core compile and unit tests pass.
- Desktop compile passes.
- Android lint debug + release pass.
- Android debug APK assembles.
- Android release AAB bundles.
- Final sprite layout validators pass.

### Runtime

- Cold launch succeeds.
- New profile creation succeeds.
- Existing profile load succeeds.
- Complete run can start, progress, pause/resume, end, and persist.
- Background/foreground cycle does not corrupt state.
- No fatal exception during a normal 20+ minute run.

### Save / economy

- Save format has an explicit version.
- Migration tests exist for supported previous versions.
- Inventory limits and exclusive capacity remain valid after reload.
- Currency operations are overflow-safe and cannot charge on failed upgrades.
- Purchases cannot grant twice on restore/retry paths.

### Performance

- Horde stress scene benchmark exists.
- Frame-time budget is measured on representative Android hardware.
- Allocations during steady-state combat are bounded.
- Thermal throttling behavior is tested before RC.

### Art

- Rex passes the complete Gate A contract first.
- Final atlas coverage can be measured automatically.
- No bootstrap sprite appears in a representative complete run before release.
- Pivot stability, clipping, alpha bleeding, readability, and effect priority pass native-phone QA.

## Milestone definitions

### M1 — Vertical Slice Complete

Required:

- Launch -> menu -> character/loadout -> run -> combat -> upgrades -> boss/end condition -> result -> persistent progression.
- Pause/settings/audio/haptics minimum production behavior.
- Save/reload across app restart.
- Representative Android device validation.
- No placeholder system required to complete the loop.

### M2 — Combat Production

Required:

- Data-driven weapons.
- Status effects.
- Enemy state machines.
- Boss framework.
- Spatial-hash or equivalent scalable collision path.
- Damage-number and combat-feedback system.
- Screen-space FX, audio mixer, haptics, pause/settings.
- Deterministic or repeatable test harness for core combat calculations where practical.
- Stress tests for enemy/projectile counts.

### M3 — Final Art Alpha

Production order:

1. Rex + core weapon + core VFX.
2. Alpha / Revenant / Null Archon and other contracted bosses.
3. Six biome-signature enemies.
4. Nyx / Bastion / Volt / Wraith.
5. Generic enemy roster.
6. Environment modules, secondary VFX, UI/store art.

Every actor must pass machine validation plus native-phone visual QA before being marked final.

### M4 — Meta Complete

Required:

- Inventory and equipment rarity.
- Character roster.
- Weapon progression.
- Missions and achievements.
- Daily/weekly systems.
- Save versioning and migration tests.
- Cloud-save adapter boundary with conflict policy documented.

### M5 — Content Complete

Target content matrix from the roadmap:

- 5 biomes.
- 20+ enemies.
- 8+ elites.
- 6 bosses.
- 12+ weapons.
- 50+ upgrades.
- Synergies/evolutions.
- Events.
- Difficulty modes.

Do not mark complete from raw counts alone: all entries must be integrated, balanced enough for testing, save-safe, performant, and represented by production-ready presentation or explicitly tracked final-art debt.

### M6 — Economy Complete

Required:

- Rewarded placements.
- No-ads entitlement.
- Starter pack.
- Premium currency/catalog.
- Purchase verification and restore paths.
- Idempotent grant handling.
- Remote-configured offers with safe defaults.
- Consent/privacy flow.
- Telemetry needed for economy tuning.

### M7 — Release Candidate

Required:

- Low / medium / high / ultra profiles where supported by roadmap.
- 60/90/120 FPS behavior validated where applicable.
- Thermal, crash and ANR targets measured.
- Accessibility pass.
- Localization pass.
- Final store icon, feature graphic and screenshots from the shipping build.
- Closed-test feedback triaged.
- Economy and retention tuning performed with measured data.
- Release checklist in `docs/PLAY_RELEASE.md` and `docs/STORE_RELEASE.md` fully satisfied.

## Current next actions

1. Classify PR #4 and #5 as reference-art changes and merge only if they remain useful and clean.
2. Verify the newest `main` after #2/#3 merges.
3. Audit save-versioning and migration coverage.
4. Audit whether a full vertical slice is currently achievable on Android and desktop.
5. Create targeted P0/P1 issues from the findings instead of adding untracked scope.

## Definition of Done policy

A task is not done because code, data, or an image exists. It is done only when its acceptance criteria pass in the relevant automated tests and/or runtime QA.

For final art, generated reference boards never equal final production sprites. For gameplay systems, compilation never equals runtime validation. For release, a local successful run never equals Play Store readiness.
