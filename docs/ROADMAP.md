# Production roadmap

## P0 — Foundation (current)
Playable combat loop, Android/desktop targets, pooling, upgrade loop, ads/billing boundary.

## P1 — Production combat
Data-driven weapons, status effects, enemy state machines, boss framework, spatial hash collision, damage numbers, screen-space FX, audio mixer, haptics, pause/settings.

## P2 — Visual identity
Production characters, 8-direction animation, enemy animation sets, environment tiles, shader stack, GPU particles, decals, dynamic light masks, boss intros, cinematic UI motion.

### Roster production status

- 20/24 actors are merged to `main`.
- Actor 21, `SLAG_GUARD`, is in candidate validation on branch `roster-actor-21-slag-guard`.
- Initial source: Quaternius `Knight_Golden_Male.blend`, pinned by immutable commit and Git blob.
- Defensive identity uses an explicit CC0 KayKit large shield attached to the left-hand bone; the shield source is independently pinned by Git blob.
- Shared production contract remains 232 frames, 8 directions × 29 frames, 96 px cells.
- Heavy-role phone gates remain strict: median bbox area ≥2200 px² and width ≥38 px.
- Merge requires source/action validation, production staging, full final CI, dedicated Android gameplay/crowd/attack capture, and semantic visual review.

## P3 — Meta game
Inventory, equipment rarity, character roster, weapon progression, missions, achievements, daily/weekly systems, offline save migration, cloud-save adapter.

## P4 — Live economy
Rewarded placements, no-ads entitlement, starter pack, premium currency, store catalog, secure purchase verification, remote-configured offers, consent, telemetry.

## P5 — Content scale
5 biomes, 20+ enemies, 8+ elites, 6 bosses, 12+ weapons, 50+ upgrades, synergies/evolutions, events, difficulty modes.

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.
