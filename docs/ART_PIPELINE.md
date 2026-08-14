# DEADLINE: ZERO — Production Art Pipeline

Runtime atlas path: `assets/art/game.atlas`

The renderer prefers final atlas art, falls back to the compact bootstrap sheet for legacy keys, and keeps procedural rendering as the final safety net. This lets production art replace bootstrap content incrementally without breaking gameplay.

## Eight-direction atlas contract

Character animation uses the canonical direction tokens:

`n`, `ne`, `e`, `se`, `s`, `sw`, `w`, `nw`

The preferred production key format is:

`{actor-root}/{direction}/{motion}`

where motion is one of `idle`, `run`, `attack`, `hit`, or `death`. Frames are indexed TextureAtlas regions under that same key.

### Survivors

Examples:

- `survivor/rex/n/idle`
- `survivor/rex/ne/run`
- `survivor/rex/e/attack`
- `survivor/rex/sw/hit`
- `survivor/rex/w/death`

Repeat the complete eight-direction set for `rex`, `nyx`, `bastion`, `volt`, and `wraith`.

### Enemies

Use the same directional structure for every `Enemy.Type`, for example:

- `enemy/shambler/n/run`
- `enemy/runner/se/attack`
- `enemy/brute/w/hit`
- `enemy/ranged/nw/idle`
- `enemy/elite/s/death`
- `enemy/shielded/e/run`
- `enemy/regenerator/ne/run`
- `enemy/phantom/sw/attack`
- `enemy/boss/w/attack`

Corpses remain static regions: `enemy/{type}/corpse`.

### Boss identities

Dedicated boss art uses:

- `boss/alpha/{direction}/{motion}`
- `boss/revenant/{direction}/{motion}`

When an identity-specific directional sequence is missing, the runtime falls back through the legacy boss/enemy authored path rather than failing gameplay.

## Legacy compatibility

The previous non-directional format remains supported during migration:

- `survivor/rex/run`
- `enemy/shambler/attack`
- `boss/alpha/death`

Directional art always takes priority when present. Legacy keys should be removed only after all eight directions for the corresponding actor/motion are production-complete.

## Effects

Runtime VFX keys include:

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

## Authoring targets

Character animations should share one top-down/isometric camera, pivot and foot anchor. Target 6–10 frames for run cycles, 4–8 for attacks, 3–5 for hit reactions and 8–14 for deaths. Idle animation can use fewer frames but should include enough secondary motion to prevent a frozen appearance.

Every direction for a given actor/motion must use the same canvas dimensions and pivot. Keep silhouettes readable at phone scale; avoid internal detail that disappears during horde combat.

## Technical constraints

- Power-of-two source sheets where practical.
- Trim transparent borders only when pivot metadata remains stable.
- Extrude atlas edges to prevent sampling seams.
- Use premultiplied-alpha-safe source art.
- Target 2048² combat pages before considering 4096².
- Separate environment atlases from combat-character atlases as content grows.
- Keep animation selection allocation-free in gameplay hot paths.
- Do not mirror directional production sprites at runtime: authored left/right silhouettes, weapons and lighting should remain intentional.

## Rendering strategy

1. Procedural floor, shadows and telegraphs.
2. Directional authored character sprites when available.
3. Projectile and authored/procedural VFX layers.
4. World-space damage numbers.
5. Combat HUD and screen-space effects.

The bootstrap character sheet remains a migration asset, not the final visual target. Final production sets should progressively replace it with complete eight-direction animation families.
