# DEADLINE: ZERO — Production Art Pipeline

Runtime atlas path: `assets/art/game.atlas`

The game is intentionally resilient: if the atlas is absent or incomplete, procedural rendering remains available and gameplay still runs.

## Atlas naming contract

### Survivors
Use indexed regions for animation frames:

- `survivor/rex/idle`
- `survivor/rex/run`
- `survivor/rex/attack`
- `survivor/rex/hit`
- `survivor/rex/death`

Repeat the same structure for `nyx`, `bastion`, `volt`, and `wraith`.

### Enemies

- `enemy/shambler/idle|run|attack|hit|death`
- `enemy/runner/idle|run|attack|hit|death`
- `enemy/brute/idle|run|attack|hit|death`
- `enemy/ranged/idle|run|attack|hit|death`
- `enemy/elite/idle|run|attack|hit|death`
- `enemy/boss/idle|run|attack|hit|death`

### Effects

- `fx/muzzle_kinetic`
- `fx/muzzle_fire`
- `fx/muzzle_frost`
- `fx/muzzle_shock`
- `fx/explosion_small`
- `fx/explosion_large`
- `fx/impact_kinetic`
- `fx/impact_fire`
- `fx/impact_frost`
- `fx/impact_shock`
- `fx/dash`
- `fx/spawn`
- `fx/death`

## Authoring targets

Character animations should be authored at a consistent isometric/top-down camera angle. Prefer 6–10 frames for run cycles, 4–8 for attacks, 3–5 for hit reactions, and 8–14 for deaths. Keep silhouettes readable at phone scale and avoid tiny internal detail that disappears during horde combat.

## Technical constraints

- Power-of-two source sheets where practical.
- Trim transparent borders during packing.
- Extrude atlas edges to prevent sampling seams.
- Use premultiplied-alpha-safe source art.
- Keep combat atlas pages within mobile texture limits; target 2048² pages before considering 4096².
- Separate large environment atlases from combat-character atlases when content grows.
- No runtime allocation is required to choose animation frames.

## Rendering strategy

1. Procedural floor/shadows/telegraphs render first.
2. Authored character sprites render next if `game.atlas` exists.
3. Projectile/VFX layers render above characters.
4. Damage numbers render in world space.
5. Combat HUD renders last in screen space.

The procedural character bodies remain a debug/fallback mode until the final authored set is complete.
