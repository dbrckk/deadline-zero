# Final sprite source sheets

This directory is the source-of-truth staging area for production character sheets before they are cut and packed into `core/assets/art/game.atlas`.

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

The source sheet may be wider than a final atlas page. TexturePacker is expected to split output across pages when necessary.

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

## Cutting

Example for Rex:

```bash
python tools/slice_sprite_sheet.py \
  --sheet art_sources/rex.png \
  --root survivor/rex \
  --cell 96 \
  --output build/art_frames
```

Example for Alpha:

```bash
python tools/slice_sprite_sheet.py \
  --sheet art_sources/alpha.png \
  --root boss/alpha \
  --cell 128 \
  --boss \
  --output build/art_frames
```

Example for Wraith/Forge Hound/Phase Stalker fast-run sheets:

```bash
python tools/slice_sprite_sheet.py \
  --sheet art_sources/wraith.png \
  --root survivor/wraith \
  --cell 96 \
  --run 10 \
  --output build/art_frames
```

## Non-negotiable QA

- transparent background;
- fixed logical cell size;
- stable foot/pivot position across frames;
- at least 2 transparent pixels around visible art;
- no baked UI text or muzzle flash;
- no independently rescaled frames;
- animation contact frames must match gameplay timing;
- assets must remain readable at actual phone gameplay scale.

Bootstrap/generated art remains fallback-only and must never be treated as final production art.
