# Production roadmap

## P0 — Foundation (current)
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

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.
