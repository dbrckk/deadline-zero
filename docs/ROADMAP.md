# Production roadmap

## P0 — Foundation (current)
Playable combat loop, Android/desktop targets, pooling, upgrade loop, ads/billing boundary.

## P1 — Production combat
Data-driven weapons, status effects, enemy state machines, boss framework, spatial hash collision, damage numbers, screen-space FX, audio mixer, haptics, pause/settings.

## P2 — Visual identity
Production characters, 8-direction animation, enemy animation sets, environment tiles, shader stack, GPU particles, decals, dynamic light masks, boss intros, cinematic UI motion.

### Roster production status

- 21/24 actors are merged to `main`.
- Actor 22, `PHASE_STALKER`, is in final production QA on PR #68.
- Source: Quaternius Ultimate Monsters `Flying/glTF/Ghost.gltf`, pinned by immutable commit and Git blob.
- Native `Flying_Idle`, `Fast_Flying`, `Headbutt`/`Punch`, `HitReact`, and `Death` actions are resolved exactly.
- Shared production contract remains 232 frames, 8 directions × 29 frames, 96 px cells.
- Phone gates remain strict: median bbox area ≥2200 px² and width ≥38 px; PHASE STALKER measures 3770 px² / 60 px on the accepted candidate pass.
- Dedicated Android gameplay/crowd/attack captures now target PHASE STALKER at NULL SECTOR stage 20; semantic review confirms the floating ghost silhouette remains distinct in solo/crowd views and the forced attack telegraph is visible.

## P3 — Meta game
Inventory, equipment rarity, character roster, weapon progression, missions, achievements, daily/weekly systems, offline save migration, cloud-save adapter.

## P4 — Live economy
Rewarded placements, no-ads entitlement, starter pack, premium currency, store catalog, secure purchase verification, remote-configured offers, consent, telemetry.

## P5 — Content scale
5 biomes, 20+ enemies, 8+ elites, 6 bosses, 12+ weapons, 50+ upgrades, synergies/evolutions, events, difficulty modes.

## P6 — Launch quality
Low/medium/high/ultra profiles, device thermals, 60/90/120 FPS validation, ANR/crash targets, accessibility, localization, store assets, closed/open testing, retention and economy tuning.
