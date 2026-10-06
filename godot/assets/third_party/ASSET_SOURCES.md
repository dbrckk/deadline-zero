# Approved third-party 3D / PBR sources

Only assets with an explicit redistribution/commercial-use license may enter the shipped game. The canonical machine-readable list is `res://assets/asset_manifest.json`.

## Tier A — PBR / final-material sources

### Poly Haven
- License: CC0 assets.
- Use: scanned PBR asphalt, concrete, metal, grates, container surfaces; selected realistic source models if needed.
- Acquisition: `python3 tools/assets/acquire_polyhaven.py --dry-run`, then fetch selected pinned IDs to staging.
- First pinned materials: `concrete_tiles_02`, `asphalt_04`, `road_damaged_clean`, `factory_wall`, `rusty_metal_04`, `metal_grate_rusty`, `container_side`.
- Import target after processing: `res://assets/third_party/polyhaven/<asset_id>/`.

### ambientCG
- License: CC0.
- Use: fallback PBR surface library when the required material does not exist at Poly Haven.
- Rule: pin exact asset ID/source in `asset_manifest.json` before import.

## Tier B — kitbash / animation sources

### Quaternius — Zombie Apocalypse Kit
- License: CC0.
- Existing use: playable survivor, zombie base meshes/animations and urban props.
- Import: `res://assets/third_party/quaternius/zombie_apocalypse/`.

### Quaternius — Sci-Fi Essentials Kit
- License: CC0.
- Planned use: weapons, robots, crates, screens and sci-fi props.
- Only the publicly downloadable free subset is assumed by the pipeline.

### Quaternius — Modular Sci-Fi MegaKit
- License: CC0 for published assets.
- Planned use: modular wall/floor/door/column source geometry.
- Re-materialize with Deadline: Zero PBR before hero-facing use.

### Quaternius — Universal Animation Library 2
- License: CC0.
- Planned use: retarget armed/melee/zombie/locomotion clips to validated DZ rigs.

## Project-owned
Existing environment crack/scorch/blood source decals under `art_sources/environment/**/decal/` are eligible for promotion into the Godot runtime after visual review/atlas packing.

## Forbidden / reference-only
- Proprietary Zombie Waves art, meshes, textures, audio, shaders or animations.
- Hunyuan3D-2 outputs in this project while its current license excludes EU territory.


### Kenney — Space Kit
- License: CC0.
- Current use: curated FBX source geometry for consoles, service pipes, perimeter structures, satellite dish, station module and barrels.
- Official page: https://kenney.nl/assets/space-kit
- Runtime rule: convert/optimize to GLB, then rematerialize with Deadline: Zero PBR; do not treat the original flat materials as final art.

### Kenney — Modular Space Kit
- License: CC0.
- Status: approved next-source candidate (2026 release, 40 modular 3D files).
- Best use: corridor/wall/floor modules where Quaternius geometry would otherwise require Godot-3 conversion.
- Official page: https://kenney.nl/assets/modular-space-kit
