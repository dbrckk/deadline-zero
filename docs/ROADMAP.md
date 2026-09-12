# Production roadmap

## P0 — Foundation (current)
Playable combat loop, Android/desktop targets, pooling, upgrade loop, ads/billing boundary.

## P1 — Production combat
Data-driven weapons, status effects, enemy state machines, boss framework, spatial hash collision, damage numbers, screen-space FX, audio mixer, haptics, pause/settings.

## P2 — Visual identity
Production characters, 8-direction animation, enemy animation sets, environment tiles, shader stack, GPU particles, decals, dynamic light masks, boss intros, cinematic UI motion.

### Roster production status

- 18/24 actors are merged to `main`.
- Actor 19, `FORGE_HOUND`, is in production QA on PR #65.
- Source is pinned to Quaternius Ultimate Monsters `Dog.gltf` with immutable blob/SHA-256 fingerprints.
- Generated contract: 232 frames, 8 directions × 29 frames, 96 px cells.
- Phone-scale gates remain unchanged: median bbox area ≥2200 px² and width ≥38 px; current candidate measures 3654 px² / 63 px.
- Merge requires candidate validation, production staging, catalog, full Verify, Android runtime capture and semantic review of FORGE HOUND gameplay/crowd/attack frames.

## P3 — Meta game
Inventory, equipment rarity, character roster, weapon progression, missions, achievements, daily/weekly systems, offline save migration, cloud-save adapter.

## P4 — Live economy
Rewarded placements, no-ads entitlement, starter pack, premium currency, store catalog, secure purchase verification, remote-configured offers, consent, telemetry.

## P5 — Content scale
5 biomes, 20+ enemies, 8+ elites, 6 bosses, 12+ weapons, 50+ upgrades, synergies/evolutions, events, difficulty modes.

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.
