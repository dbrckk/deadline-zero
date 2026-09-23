# Deadline Zero — Godot 3D migration

## Decision
Deadline Zero moves toward a native 3D Godot implementation while the existing libGDX game remains the behavioral/reference implementation during migration.

Target engine: **Godot 4.7.2 stable**.

## Why this lane
The desired game is a 3D mobile survivor shooter. The existing 2D/libGDX presentation can validate systems and progression, but it should no longer constrain final rendering, animation, camera, lighting, VFX or imported 3D content.

## Migration gates

### Gate 1 — playable 3D combat foundation
- player movement
- three-quarter follow camera
- auto-aim and auto-fire
- 3D enemies/hordes
- projectiles and impacts
- XP pickups
- level-up choices
- HUD
- mobile renderer

### Gate 2 — authored CC0 assets
- replace procedural survivor with rigged Quaternius character
- replace enemy primitives with at least four animated zombie archetypes
- import weapon models
- import barricades/crates/vehicles
- import Poly Haven ground PBR material
- preserve source/license manifest

### Gate 3 — Zombie Waves-class presentation principles
- readable horde silhouettes at phone scale
- enemy animation state machine
- per-weapon projectile/VFX identity
- muzzle flashes, hit sparks, decals, death VFX
- boss reveal camera and boss-specific FX
- rarity/icon-driven upgrade cards
- drop/reward showcase
- camera shake/hit-stop/haptics with accessibility controls

### Gate 4 — mobile production
- MultiMesh/HLOD strategy for static props
- pooled enemies/projectiles/VFX
- baked or mobile-friendly lighting where appropriate
- texture budgets and LODs
- Android frame-time telemetry
- Play Store export pipeline

## Non-copy rule
Do not redistribute or recreate proprietary Zombie Waves meshes, textures, shaders, audio, animations, code or exact layouts. Reverse-engineering findings are used only to identify abstract production patterns and quality gaps.
