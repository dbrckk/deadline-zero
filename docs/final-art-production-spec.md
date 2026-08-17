# Deadline Zero — Final Art Production Contract

This document defines the production-ready sprite/animation deliverable for the Android release. Bootstrap/generated art remains fallback-only. Final assets must ship through `core/assets/art/game.atlas` and its texture pages.

## Rendering target

- Camera: top-down 3/4 action view, strong silhouette readability on a phone screen.
- Native character cell target: 96x96 px for standard actors, 128x128 px for bosses. Transparent padding is allowed; feet/pivot must remain stable.
- Texture filtering: nearest for pixel-authored sheets, linear only if the final source is painted/high-resolution and tested for halo-free alpha edges.
- Premultiplied-looking fringes, white matte edges, baked drop shadows, and UI text inside sprites are forbidden.
- Lighting language: cool ambient world light, warm muzzle/fire accents, cyan/shock/frost accents, purple/null accents.
- Every animation must preserve a stable ground contact point to prevent visual foot sliding.

## Directions

All playable survivors, biome-signature enemies, and bosses require 8 directional sets:

`n`, `ne`, `e`, `se`, `s`, `sw`, `w`, `nw`.

Mirroring is acceptable only for emergency fallback. Final production art must support all eight directional atlas keys.

## Motion frame minimums

These are minimums, not caps:

| Motion | Standard actors | Bosses | Notes |
|---|---:|---:|---|
| idle | 4 | 6 | breathing/weight shift, no static cutout |
| run | 8 | 8 | full gait loop, readable foot plant |
| attack | 6 | 8 | anticipation, contact/fire frame, recovery |
| hit | 3 | 4 | fast readable reaction without hiding telegraph state |
| death | 8 | 10 | non-looping, readable silhouette collapse/disintegration |

For very fast actors (Wraith, Forge Hound, Phase Stalker), run can use 10-12 frames if it materially improves motion.

## Survivors

Required roots:

- `survivor/rex` — Vanguard. Broad readable armor silhouette, teal/cyan tech accent, confident planted stance.
- `survivor/nyx` — Sharpshooter. Lean silhouette, precision optics, longer weapon profile, controlled recoil.
- `survivor/bastion` — Juggernaut. Heavy armor mass, slower weight transfer, reinforced shoulders/torso.
- `survivor/volt` — Technician. Compact tech rig, electrical accent nodes, active utility silhouette.
- `survivor/wraith` — Runner. Light armor, long stride, aggressive forward lean, high-speed readability.

Key format example:

`survivor/rex/ne/run`

## Generic enemy archetypes

Required roots:

- `enemy/shambler`
- `enemy/runner`
- `enemy/brute`
- `enemy/ranged`
- `enemy/elite`
- `enemy/shielded`
- `enemy/regenerator`
- `enemy/phantom`
- `enemy/boss` (generic fallback only; production boss identities below take priority)

Each generic enemy also requires `corpse` unless its death animation fully replaces corpse persistence in the final renderer.

## Biome-signature enemies

Required directional roots:

- `enemy/biome/forge_hound` — Cinder Foundry pouncer; low forward silhouette, heat vents, ember trail language.
- `enemy/biome/cinder_gunner` — Cinder Foundry ranged unit; weapon/arm cannon silhouette, explosive chamber glow.
- `enemy/biome/slag_guard` — Cinder Foundry heavy charger; thick plated front, furnace seams, high mass.
- `enemy/biome/phase_stalker` — Null Sector assassin; broken/glitch silhouette, purple/cyan phase seams.
- `enemy/biome/static_seer` — Null Sector ranged zoning unit; floating/lean silhouette, electric crown/sensor motif.
- `enemy/biome/null_ward` — Null Sector support; broad warding geometry, frost/null field emitters.

## Boss identities

Required roots:

- `boss/alpha` — industrial military apex unit, heavy readable armor, controlled cadence.
- `boss/revenant` — faster predatory silhouette, damaged/unstable energy seams, more aggressive posture.
- `boss/null_archon` — late-game null entity, large occult-tech silhouette, phase fracture motifs.

Bosses must be visually distinguishable in grayscale silhouette before color/VFX are applied.

## Weapon sprites

Every `WeaponCatalog` entry requires `weapon/<id>` with:

- transparent background,
- muzzle anchor visually consistent with player hand placement,
- no baked muzzle flash,
- consistent pivot/orientation for runtime rotation.

## VFX animation roots

Required production sequences include:

- `fx/muzzle_fire`
- `fx/dash`
- `fx/level_up`
- `fx/impact_energy`
- `fx/impact_fire`
- `fx/impact_frost`
- `fx/impact_shock`
- `fx/impact_kill`
- `fx/boss_explosion`
- `fx/legendary_overdrive`
- `fx/legendary_singularity`
- `fx/legendary_apex`
- Null Archon aura/portal/fracture sequences used by the renderer.

Persistent aura FX must loop seamlessly. One-shot impact FX must end cleanly on transparent frames or a visually negligible terminal frame.

## Sprite-sheet cutting rules

When source art is delivered as sheets:

1. Remove empty outer margins while preserving a consistent logical cell size per actor.
2. Keep a fixed foot/pivot coordinate across all frames of an actor.
3. Export frames in atlas order using the exact runtime key names.
4. Do not resize individual frames independently after cutting.
5. Maintain alpha padding of at least 2 px around non-empty pixels to avoid atlas bleeding.
6. Extrude atlas edges by 2 px in the packer.
7. Keep directional/motion groups contiguous in source sheets for easier QA.

Recommended source-sheet organization per actor:

- rows: 8 directions,
- columns grouped by motion: idle, run, attack, hit, death,
- a separate metadata manifest records frame counts and source cell size.

## Visual QA gate

An actor is not considered final until all checks pass:

- readable at actual phone gameplay scale,
- no foot/pivot jitter,
- no frame clipping,
- no atlas bleeding,
- no animation popping between idle/run/attack,
- attack contact frame matches gameplay timing,
- hit flash/reaction remains visible,
- telegraphs remain more readable than decorative VFX,
- 45-60 FPS Android target remains intact during horde stress scenes,
- silhouette remains distinguishable under elemental tinting and post FX.

## Production priority

1. Rex + core weapon + core VFX (establish final quality bar).
2. Alpha/Revenant/Null Archon bosses.
3. Six biome-signature enemies.
4. Nyx/Bastion/Volt/Wraith.
5. Remaining generic enemy archetypes.
6. Environment tiles/props/decals and secondary VFX polish.

Generated/bootstrap art must remain available until every required final asset is validated in the release atlas.