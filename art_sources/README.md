# Final sprite source sheets

This directory is the source-of-truth staging area for production character sheets before they are cut and packed into `assets/art/game.atlas`.

## Fixed layout

Rows are always: `n, ne, e, se, s, sw, w, nw`.

Columns are contiguous motion groups: `idle, run, attack, hit, death`.

Standard frame minimums are `4 + 8 + 6 + 3 + 8 = 29` cells per row. Boss minimums are `6 + 8 + 8 + 4 + 10 = 36` cells per row.

### Standard 96×96 actor

- source width: `29 × 96 = 2784 px`
- source height: `8 × 96 = 768 px`
- total minimum frames: `232`

Fast actors using 10 run frames use 31 columns:

- source width: `31 × 96 = 2976 px`
- source height: `768 px`
- total frames: `248`

### Boss 128×128

- source width: `36 × 128 = 4608 px`
- source height: `8 × 128 = 1024 px`
- total minimum frames: `288`

The source sheet may be wider than a final atlas page. TexturePacker splits packed output across pages when necessary.

## Required filenames

Use `<id>.png`, matching `final-sprite-layout.json`, for example:

- `rex.png`
- `wraith.png`
- `alpha.png`
- `revenant.png`
- `warden.png`
- `harvester.png`
- `null_archon.png`
- `forge_hound.png`

## 1. Validate sources and contract

The layout contract is validated on every CI run without external Python packages:

```bash
python3 tools/validate_final_sprite_layout.py
```

This checks the schema, direction/motion ordering, unique actor IDs and atlas roots, logical cell sizes, priorities, TexturePacker parity and packing invariants. Whenever an `<id>.png` source sheet exists, its PNG IHDR dimensions must exactly match the dimensions implied by the contract.

## 2. Cut and QA all frames

Install Pillow once for local cutting:

```bash
python -m pip install pillow
```

Final production command:

```bash
python tools/build_final_sprite_frames.py --clean --require-all --strict
```

The batch builder reads `final-sprite-layout.json`, processes actors by production priority, writes every frame under `build/art_frames`, and emits `build/art_frames/final-sprite-build.json`.

`--require-all` rejects missing contracted sheets. `--strict` rejects any QA warning, including empty cells, insufficient alpha padding, unstable foot pivots or excessive horizontal pivot drift. The defaults permit 3 px of foot drift and 6 px of horizontal alpha-center drift for idle/run/hit; attack receives 2× those tolerances because attack silhouettes legitimately extend farther. Death is measured but excluded from pivot-stability rejection because the actor is expected to fall.

During incremental art delivery, omit `--require-all` so missing sheets are skipped:

```bash
python tools/build_final_sprite_frames.py --clean --strict
```

Single-actor cutting remains available for diagnosis. Example for Rex:

```bash
python tools/slice_sprite_sheet.py \
  --sheet art_sources/rex.png \
  --root survivor/rex \
  --cell 96 \
  --output build/art_frames
```

## 3. Pack and verify the production atlas

Once the strict batch build is complete:

```bash
gradle buildFinalAtlas
```

`packFinalAtlas` resolves the project-matched libGDX TexturePacker, refuses incomplete or warning-bearing frame manifests, removes only previous `game.atlas` / `game*.png` atlas outputs, and writes the final packed files under `assets/art/` using `tools/texturepacker-final.json`.

`buildFinalAtlas` then runs `verifyFinalAtlasCoverage`, which requires all 24 actors, 8 directions and 5 motion groups with the exact indexed frame sets from the contract. It also verifies every atlas page exists, is a readable PNG and fits within the 4096×4096 page ceiling.

You can rerun only the audit with:

```bash
gradle verifyFinalAtlasCoverage
```

or:

```bash
python3 tools/verify_final_atlas.py --atlas assets/art/game.atlas
```

The Google Play production asset gate depends on this coverage audit, so a partial atlas cannot be shipped merely because `game.atlas` exists.

## Non-negotiable QA

- transparent background;
- exact logical sheet dimensions;
- stable foot/pivot position across non-death frames;
- at least 2 transparent pixels around visible art;
- no baked UI text or muzzle flash;
- no independently rescaled frames;
- animation contact frames must match gameplay timing;
- assets must remain readable at actual phone gameplay scale.

Bootstrap/generated art remains fallback-only and must never be treated as final production art.
