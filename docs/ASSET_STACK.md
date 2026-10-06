# Deadline: Zero — Premium 3D Asset Stack

This is the production asset pipeline for the Godot shipping runtime. The goal is not to maximize asset count; it is to make every shipped asset coherent, license-safe, mobile-budgeted and visually stronger than the current Quaternius-only baseline.

## Source hierarchy

### Tier A — ship-quality foundations
- **Project-owned assets**: preferred for hero silhouettes, decals, unique weapons, boss identity and branded environment pieces.
- **Poly Haven CC0**: primary source for realistic scanned PBR surfaces and selected source models/HDRIs. Acquire through `tools/assets/acquire_polyhaven.py`, then commit the chosen processed files so builds remain offline/reproducible.
- **ambientCG CC0**: secondary PBR library when Poly Haven lacks a surface. Pin the exact asset ID/license in `asset_manifest.json` before import.

### Tier B — kitbash / animation / blockout
- **Quaternius Zombie Apocalypse**: current player/zombie/environment base.
- **Quaternius Modular Sci‑Fi MegaKit**: public free subset for walls, floors, doors, columns and modular industrial geometry.
- **Quaternius Sci‑Fi Essentials**: public free subset for weapons, robots, crates, screens and props.
- **Quaternius Universal Animation Library 2**: retarget source for armed, melee, locomotion and zombie animation.

Tier B geometry is not automatically “final”. Hero-facing pieces should be reworked in Blender and use Deadline: Zero materials/decals.

## Authoring stack

1. **Blender 5.2 LTS** — modeling, kitbash, retopo, UVs, baking, rig cleanup, LOD generation and GLB export.
2. **Material Maker** — procedural PBR and decal authoring. Export albedo, normal (OpenGL), roughness/metallic/AO.
3. **TRELLIS.2 (optional, own GPU)** — MIT-licensed source-mesh generation for unique props only. Generated meshes are never imported raw; retopo/UV/bake/LOD in Blender is mandatory.
4. **meshoptimizer / gltfpack** — optional post-export simplification/packing for static GLB assets.
5. **Godot 4.7.2** — final importer/runtime. glTF/GLB is the canonical interchange format.

**Do not use Hunyuan3D-2 in this project**: its current community license excludes EU territory.

## Visual language

Deadline: Zero is industrial quarantine sci-fi, not generic low-poly sci-fi.

- Dominant surfaces: dark painted steel, aged concrete/asphalt, corrugated/container metal, rusty grate.
- Accent language: cold cyan systems/readability + restrained hazard orange/red.
- Roughness variation matters more than saturated color variation.
- Geometry silhouettes stay readable from the gameplay camera; microdetail belongs in normals/roughness, not geometry.
- Hero assets get authored wear masks and decals. Repeating environment modules get shared tiled PBR materials.
- No raw marketplace look: imported meshes are regraded/retextured into one coherent palette.

## Mobile production budgets

Budgets are defined in `godot/assets/asset_manifest.json`. Key targets:

- standard enemy: <= 9k LOD0 / 4.5k LOD1 triangles, <= 2 materials, <= 1K character textures;
- elite: <= 14k / 7k, <= 2 materials;
- boss: <= 30k / 15k, <= 3 materials, <= 2K;
- survivor: <= 22k / 11k, <= 3 materials, <= 2K;
- ordinary environment prop: <= 12k / 6k, <= 2 materials, <= 2K;
- decals: <= 1K individually, prefer a <= 2K atlas.

For dense hordes, draw-call/material count is a first-class budget. Prefer shared materials and atlases over unique 2K maps per prop.

## Pipeline

1. **Discover**: choose a source from the manifest or add one with an explicit license.
2. **Acquire**: download source material into a staging directory; never make network access part of the shipped runtime.
3. **Prepare in Blender**:
   - normalize scale/origin;
   - remove hidden/internal geometry;
   - merge unnecessary material slots;
   - retopo/decimate;
   - UV unwrap;
   - bake high→low normal/AO when needed;
   - create LOD1/LOD2 where the asset can occupy meaningful screen area;
   - export GLB.
4. **Texture**:
   - tiled environment materials come from Poly Haven/ambientCG or Material Maker;
   - unique assets receive authored masks/decals;
   - use OpenGL normal maps for Godot.
5. **Optimize**: optional `gltfpack` for static assets after visual validation.
6. **Import to Godot** and add collision only where gameplay needs it.
7. **Validate** with `python3 tools/assets/validate_asset_stack.py` and the existing rendered-frame CI.
8. **Review at gameplay scale**, not only in a turntable.

## Immediate asset plan

Priority order for the current game:

1. **Arena material pass**: asphalt/concrete base + rusty metal/grate/container variants.
2. **Modular industrial set**: wall/floor/door/pillar/pipe/barrier pieces, retextured into DZ palette.
3. **Decal pass**: existing project-owned crack/scorch/blood plus hazard stripes, quarantine numbering and grime masks.
4. **Hero weapon pass**: rifle + 5 protocol visual variants using shared geometry/material masks.
5. **Enemy silhouette pass**: keep existing animation rigs but progressively replace/augment visible armor/props.
6. **Boss hero pass**: unique geometry and 2K material set, then dedicated LOD1.
7. **Lighting/HDRI reference**: use HDRIs only as authoring/reference where helpful; runtime lighting remains deliberately controlled for mobile.

## Legal/provenance rules

- Shipped third-party assets default to **CC0**. Other licenses require explicit review.
- Keep `SOURCE.md`/license provenance beside imported third-party packs.
- Do not redistribute raw paid/restricted marketplace packs.
- Do not import any proprietary Zombie Waves asset. It remains benchmark/reference only.
- Generator/tool licenses do not automatically become asset licenses; record generator provenance for project-owned outputs.

## Original procedural industrial kit

`tools/blender/build_industrial_kit.py` creates five project-owned GLB review assets with Blender 5.2.2 LTS: cargo crate, service pillar, pipe rack, bulkhead panel and floor grate. The dedicated `Build Industrial 3D Kit` workflow validates triangle/material/file-size budgets and uploads the GLBs as an artifact. Promote an output into `godot/assets/` only after its structural and gameplay-scale review passes.
