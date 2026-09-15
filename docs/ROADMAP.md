# Production roadmap

## P0 — Foundation (complete)
Playable combat loop, Android/desktop targets, pooling, upgrade loop, ads/billing boundary.

## P1 — Production combat
Data-driven weapons, status effects, enemy state machines, boss framework, spatial hash collision, damage numbers, screen-space FX, audio mixer, haptics, pause/settings.

## P2 — Visual identity
Production characters, 8-direction animation, enemy animation sets, environment tiles, shader stack, GPU particles, decals, dynamic light masks, boss intros, cinematic UI motion.

### Roster production status

- **24/24 production actors are merged to `main`.**
- The final actor, `NULL_WARD`, merged through PR #70 after immutable source/action validation, production staging, final CI, dedicated Android gameplay/crowd/attack QA, and semantic visual review.
- The shared production contract remains 232 frames per actor: 8 directions × 29 frames, 96 px cells.
- Phone-scale quality gates were preserved through completion; no threshold was lowered to accept an actor.
- Final NULL WARD candidate measured 3563 px² median bbox area and 58 px median width against required minima of 2200 px² / 38 px.
- NULL SECTOR Android semantic QA explicitly verifies `REGENERATOR → NULL_WARD` at stage 20 and captures gameplay, crowd readability, and attack telegraph states.
- Roster production is complete; subsequent visual work should focus on cross-roster polish/regression QA rather than adding unfinished base actors.

## P3 — Meta game
Inventory, equipment rarity, character roster, weapon progression, missions, achievements, daily/weekly systems, offline save migration, cloud-save adapter.

## P4 — Live economy
Rewarded placements, no-ads entitlement, starter pack, premium currency, store catalog, secure purchase verification, remote-configured offers, consent, telemetry.

## P5 — Content scale
5 biomes, 20+ enemies, 8+ elites, 6 bosses, 12+ weapons, 50+ upgrades, synergies/evolutions, events, difficulty modes.

### P5 production status

- **5/5 biomes reached:** Quarantine Yard, Cinder Foundry, Null Sector, Cryo Vault and Cryogenic Depths.
- **12/12+ weapons reached:** the production Arsenal now contains twelve progression-gated weapons.
- **52/50+ standard upgrades reached:** the level-up pool spans offense, survivability, mobility, elemental specialization and hybrid build matrices, with explicit mobile-safe stacking caps.
- **6/6 bosses reached:** Alpha, Revenant, Warden, Harvester, Null Archon and Frost Colossus all route through the shared boss framework with phase-specific runtime behavior.
- **22/20+ gameplay enemy profiles reached:** 8 base non-boss archetypes + 6 authored biome-signature profiles + 8 champion variants. Counts intentionally describe gameplay profiles, not duplicated art actors.
- **8/8+ champion/elite profiles reached:** Swift, Armored, Feral, Volatile, Juggernaut, Ravager, Aegis and Hunter each carry distinct stat/cadence/pattern tuning.
- Ability trees already provide five levelled abilities with Tier II/evolution states and multiple cross-tree synergies.
- Runs already include six deterministic encounter-event archetypes, five standard contracts, three legendary contracts, four rotating endgame mutators and a persistent 0–20 Threat difficulty ladder.
- The production actor roster remains 24/24 and is not being expanded merely to inflate content counts.
- **Remaining P5 closure work:** champion crowd capture is automated in Android runtime CI; complete final semantic review of that artifact and run final content balance/pacing regression across the now-complete count targets.

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.


## First-playable release gate

A directly installable test build is considered first-playable-ready when all of the following are true:

- Core, Android production build, desktop smoke runtime, and Android runtime CI are green on the release candidate.
- The automated Android champion-variant crowd artifact passes semantic readability review.
- A full run-path regression covers launch/menu, run start, combat, upgrade choice, boss progression, run end, save, process death, and restart.
- P5 balance/pacing regression has no blocker-severity issue.
- A debug/test APK is produced from the validated release-candidate commit and smoke-tested on a physical Android device.

Store assets, public testing tracks, retention tuning, and commercial economy tuning remain P6 release work and do not block a private first-playable build.
