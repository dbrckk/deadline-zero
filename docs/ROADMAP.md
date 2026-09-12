# Production roadmap

## P0 — Foundation (current)
Playable combat loop, Android/desktop targets, pooling, upgrade loop, ads/billing boundary.

## P1 — Production combat
Data-driven weapons, status effects, enemy state machines, boss framework, spatial hash collision, damage numbers, screen-space FX, audio mixer, haptics, pause/settings.

## P2 — Visual identity
Production characters, 8-direction animation, enemy animation sets, environment tiles, shader stack, GPU particles, decals, dynamic light masks, boss intros, cinematic UI motion.

### Roster production status

- 19/24 actors are merged to `main`; actor 20, `CINDER_GUNNER`, is accepted on PR #66 pending final post-acceptance CI.
- CINDER GUNNER source is pinned by Git blob and SHA-256, with explicit configured rifle geometry and native `Shoot_OneHanded` semantics.
- Production contract: 232 frames, 8 directions × 29 frames, 96 px cells.
- Enforced phone-scale gates: median bbox area ≥1850 px² and width ≥32 px; validated result is 2079 px² / 34.5 px with 40 px minimum master margin.
- Final Android gameplay/crowd/attack evidence from Verify #1737 passed semantic review; merge remains gated on all final workflows for the accepted head.

## P3 — Meta game
Inventory, equipment rarity, character roster, weapon progression, missions, achievements, daily/weekly systems, offline save migration, cloud-save adapter.

## P4 — Live economy
Rewarded placements, no-ads entitlement, starter pack, premium currency, store catalog, secure purchase verification, remote-configured offers, consent, telemetry.

## P5 — Content scale
5 biomes, 20+ enemies, 8+ elites, 6 bosses, 12+ weapons, 50+ upgrades, synergies/evolutions, events, difficulty modes.

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.
