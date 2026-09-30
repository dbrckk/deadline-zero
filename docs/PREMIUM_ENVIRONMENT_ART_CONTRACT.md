# Premium environment art contract

Deadline Zero uses biome-specific authored environment packs. The release contract is defined by `config/environment-art-contract.json`: five biomes x fourteen required regions = **70 biome-specific atlas regions**.

## Required biome packs

Each biome must provide the same 14 semantic slots under its own atlas namespace:

- `quarantine_yard`
- `cinder_foundry`
- `null_sector`
- `cryo_vault`
- `cryogenic_depths`

For each biome, required regions are:

### Floors
- `environment/<biome>/floor/concrete_a`
- `environment/<biome>/floor/concrete_b`
- `environment/<biome>/floor/concrete_c`
- `environment/<biome>/floor/hazard_a`

### Decals
- `environment/<biome>/decal/crack_a`
- `environment/<biome>/decal/blood_a`
- `environment/<biome>/decal/scorch_a`

### Props
- `environment/<biome>/prop/barrier_a`
- `environment/<biome>/prop/debris_a`
- `environment/<biome>/prop/debris_b`
- `environment/<biome>/prop/wall_a`
- `environment/<biome>/prop/wall_b`
- `environment/<biome>/prop/crate_a`
- `environment/<biome>/prop/beacon_a`

Source masters live under `art_sources/environment/<biome>/`. The environment tooling packs them into the production atlas while preserving biome-specific identity.

## Visual language

All five packs share a coherent premium industrial sci-fi survival language, but they are not simple recolors of one neutral set.

- **Quarantine Yard:** cold industrial steel, emergency red, dirty concrete.
- **Cinder Foundry:** blackened steel, furnace damage, molten-orange accents.
- **Null Sector:** near-black alloy, restrained cyan instrumentation, violet void fractures.
- **Cryo Vault:** damaged clean cryogenic facility, frost, white-blue edge light.
- **Cryogenic Depths:** buried frozen infrastructure, teal/cyan machinery, heavy ice.

Across every biome, walkable floor fields must stay dark/desaturated enough that actors, projectiles and combat telegraphs remain visually dominant.

## Source and runtime rules

- Source master target: 512x512.
- Runtime target: 256px-class packed regions unless the packing contract explicitly changes.
- Floors must be fully opaque and tile seamlessly.
- Decals and props require transparent backgrounds with clean alpha edges.
- No baked text, logos or gameplay-critical symbols.
- Props must remain readable at approximately 32-96 screen pixels.
- Perspective and light direction must remain coherent inside each pack.
- Runtime should prefer `environment/<biome>/<slot>`; generic `environment/<slot>` fallback is development-only and must not be visible in a release-approved biome.

## Candidate versus FINAL

The presence of 14 source files is **not** sufficient for release approval.

Each `art_sources/environment/<biome>/candidate-manifest.json` remains authoritative for candidate state. A pack must not be described as FINAL while either of these is false:

- `production_ready`
- `visual_qa_pass`

Current generated/procedural candidates therefore remain candidates until premium visual QA and Android gameplay review explicitly accept them.

## Automated validation

Normal CI validates:

- environment contract structure;
- required source coverage;
- atlas integration rules;
- deterministic tooling;
- Android runtime stability;
- representative gameplay capture generation.

Run:

```bash
python3 tools/environment/validate_environment_art_contract.py
```

and the relevant Gradle/Android verification gates before promotion.

## Visual acceptance

Before setting a biome candidate to FINAL:

- [ ] All 14 biome-specific regions are present in the final atlas.
- [ ] No generic/bootstrap environment fallback is visible.
- [ ] 3x3 floor repetition has no visible seam.
- [ ] Transparent props/decals have no black/white matte or rectangular background.
- [ ] Prop silhouettes remain readable at phone scale.
- [ ] Material contrast does not overpower enemies, projectiles or telegraphs.
- [ ] No clipping or obvious perspective mismatch is visible.
- [ ] A representative Android gameplay capture for the biome has been reviewed.
- [ ] Core / Android / Android-runtime / responsive-UI gates remain green.

Existing deterministic Android captures include dedicated Cryo Vault and Cryogenic Depths frames, stage-specific Cinder/Null gameplay evidence, and a dedicated Quarantine Yard capture in the current verification pipeline.

## Promotion rule

Promotion must be evidence-based. Do not flip `production_ready` or `visual_qa_pass` solely because source count, packing, or CI is green. Human visual review of the generated Android artifact remains required for premium-art acceptance.
