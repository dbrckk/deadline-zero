# Biome environment production sources

This directory is the source-of-truth location for premium environment art.

## Required matrix

Five biome packs are contracted:

- `quarantine_yard`
- `cinder_foundry`
- `null_sector`
- `cryo_vault`
- `cryogenic_depths`

Each pack contains the same 14 slots from `config/environment-art-contract.json`:

```
floor/concrete_a.png
floor/concrete_b.png
floor/concrete_c.png
floor/hazard_a.png

decal/crack_a.png
decal/blood_a.png
decal/scorch_a.png

prop/barrier_a.png
prop/debris_a.png
prop/debris_b.png
prop/wall_a.png
prop/wall_b.png
prop/crate_a.png
prop/beacon_a.png
```

Total release target: **70 authored environment assets**.

## Source format

- PNG only.
- 512x512 master.
- Floor tiles: fully opaque and seamless on all four edges.
- Decals/props: transparent background with clean premultiplied-alpha-safe edges.
- Orthographic/top-down compatible perspective.
- No text or baked UI.
- Do not bake large global light pools into floors; runtime lighting/VFX need room to read.
- Props must retain a clear silhouette after downsampling to the 256x256 runtime cell.
- Use biome material identity rather than simple color tinting.

## Premium visual direction

### Quarantine Yard
Cold industrial concrete, distressed steel, emergency red fixtures, abandoned military containment equipment, subtle biological contamination.

### Cinder Foundry
Blackened steel, ceramic heat shielding, molten-orange emissive seams, furnace damage, ash and slag. Avoid covering the whole tile with orange.

### Null Sector
Near-black alloy, violet spatial fractures, restrained cyan instrumentation, impossible/alien geometry accents. Keep walkable floor darker than attacks.

### Cryo Vault
Clean but damaged high-security cryogenic facility, frost buildup, cold white-blue edge light, insulated storage hardware.

### Cryogenic Depths
Older buried infrastructure, thick ice, teal/cyan frozen machinery, deep blue voids, heavier damage and mineral/frost accumulation.

## Build one biome

Example:

```bash
python tools/environment/pack_environment_art.py --biome cinder_foundry
```

Outputs:

- `build/environment_art/environment-cinder_foundry.png`
- `build/environment_art/environment-cinder_foundry.atlas.txt`
- `build/environment_art/environment-cinder_foundry.manifest.json`

The page is a deterministic 4x4 grid with 14 occupied 256x256 cells.

## Runtime integration

The renderer first searches:

```
environment/<biome>/<slot>
```

and falls back to:

```
environment/<slot>
```

then to the deterministic bootstrap renderer.

This makes it safe to land one complete biome at a time while the other packs are still in production.

## QA

Run:

```bash
python tools/environment/validate_environment_art_contract.py
```

For final release-art closure:

```bash
python tools/environment/validate_environment_art_contract.py --require-complete
```

The strict command must reach **70/70**.
