# Godot 4 3D migration — Deadline: Zero

## Decision

Deadline: Zero is moving from the current libGDX 2D presentation to a **Godot 4 real-time 3D runtime**. The target remains Android-first and the presentation target is the same broad mobile 3D survivor-shooter class as Zombie Waves, while retaining Deadline: Zero's own art direction, names, balance and systems.

The existing libGDX game remains the behavioral reference until each vertical slice passes parity. Do not delete it during migration.

## Visual target

- perspective/isometric 3D battlefield readable on a phone;
- player as the stable visual anchor;
- animated 3D operative and enemy silhouettes;
- real meshes/materials for weapons, enemies and props;
- PBR or stylized-PBR environment materials;
- decals for impacts, blood, scorch and hazards;
- GPU particles for muzzle flash, hits, elemental reactions, deaths and bosses;
- dynamic key/fill lighting with restrained mobile shadow budgets;
- 3D boss reveal and weapon showcase cameras;
- Godot Control UI over the 3D battlefield;
- escalating enemy density without hiding hostile telegraphs or the operative.

## Asset policy

Third-party assets are allowed only when the license permits use in a shipped commercial Android game. Every imported external asset must be recorded in `godot/assets/ATTRIBUTION.md` before merge with source, author, exact pack, license, local paths and modifications.

Prefer CC0, MIT, Apache-2.0 or explicit commercial-use licenses. Attribution-required assets are acceptable when the obligation can be satisfied.

**Do not import ripped/extracted Zombie Waves models, textures, audio, VFX, UI or proprietary code.** The reverse-engineering repo is a design/behavior reference, not an asset source.

## Migration order

1. 3D combat vertical slice: camera, locomotion, auto-target/fire, projectile travel, enemy crowd, floor and lighting.
2. Player/enemy model pipeline: GLB/glTF, rigs, animation state machine, LOD/material budget.
3. Combat VFX: muzzle, impact, death, elemental, boss GPU particles and decals.
4. Environment: five biome kits, props, modular floors, hazards and lighting profiles.
5. Gameplay parity: weapons, upgrades, bosses, abilities, encounters, difficulty and save schema.
6. Meta UI: Home, Survivor, Arsenal, Gear, Missions, Shop, Settings, Results/Victory.
7. Android services: billing, ads, consent, Play Games, review and cloud/save adapters.
8. Android performance/thermal QA and final Play release gates.

## Mobile constraints

- one primary shadowed directional light in combat;
- local lights reserved for short-lived premium FX;
- prefer baked/vertex/environment lighting for static props;
- GLB/glTF for production models;
- LOD/culling for dense enemy crowds;
- pooled projectiles/VFX/enemies;
- GPU particles rather than per-particle gameplay nodes;
- decals bounded by lifetime/count budgets;
- transparent materials kept out of the dense crowd hot path when possible.

## Current scaffold

The first Godot scene is intentionally primitive. It validates the new runtime architecture before licensed production models are introduced. Primitive capsules do **not** count as production art.
