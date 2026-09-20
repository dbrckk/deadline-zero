# Premium environment art contract

Deadline Zero currently has production actor art, but the environment still relies on bootstrap/procedural presentation. This contract defines the next production-art batch.

## Atlas regions required

All regions are transparent PNG/WebP sources packed into `assets/art/game.atlas`.

### Floors — P0
- environment/floor/concrete_a
- environment/floor/concrete_b
- environment/floor/concrete_c
- environment/floor/hazard_a

Each floor source must tile seamlessly. Target source size: 512x512. Avoid baked gameplay shadows.

### Decals — P0
- environment/decal/crack_a
- environment/decal/blood_a
- environment/decal/scorch_a

Target: 512x512 transparent canvas. Soft alpha edges; no opaque rectangular background.

### Props — P0
- environment/prop/barrier_a
- environment/prop/debris_a
- environment/prop/debris_b
- environment/prop/wall_a
- environment/prop/wall_b
- environment/prop/crate_a
- environment/prop/beacon_a

Target: 512x512 transparent canvas, top-down/isometric-compatible silhouette. Keep a consistent light direction and readable outline at phone scale.

## Visual language

Premium industrial sci-fi survival. Dark gunmetal and desaturated concrete form the neutral base. Cyan is friendly/information, red is danger, amber is Foundry heat, violet is Null corruption, pale cyan is Cryo. Materials must read before detail: steel, concrete, emissive glass, hazard paint, ash/ice/corruption.

Do not bake text into world props. Do not imitate Zombie Waves assets; use it only as a quality/density reference.

## Biome reuse

The renderer tints the same authored neutral set for Quarantine, Foundry, Null Sector, Cryo Vault and Cryogenic Depths. Therefore neutral material fidelity and strong silhouettes are more important than biome-specific color baked into the source.

## Acceptance

- no visible tile seams at 3x3 repetition;
- no black/white matte around transparent props;
- prop silhouette remains readable at 64 px display size;
- no accidental text/glyph artifacts;
- consistent perspective and light direction across the pack;
- atlas region names exactly match this contract;
- Android responsive UI/runtime visual QA remains green.

## Production order

1. concrete_a/b/c + hazard_a
2. wall_a/b + barrier_a + crate_a
3. debris_a/b + beacon_a
4. crack_a + blood_a + scorch_a
5. in-game capture review and one correction pass

This batch is the highest-value remaining visual work because actor production art is already 24/24 while the world still falls back to generated/bootstrap geometry.
