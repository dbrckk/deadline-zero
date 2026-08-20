# Rex — final production art reference

Rex is the visual quality bar for all final actor art.

## Canonical identity

- Role: balanced precision ranger / primary survivor.
- Silhouette: athletic armored ranger, compact tactical cape, enclosed helmet, readable shoulder/forearm armor, AR-9 carried as the dominant horizontal shape.
- Primary read: cobalt/near-black armor with restrained warm orange emissive accents and cool cyan weapon/visor energy.
- Materials: matte ballistic plates, darker flexible undersuit, small metallic hard-surface details; emissive areas must remain accents rather than covering the silhouette.
- Weapon: AR-9 sci-fi rifle, long enough to read clearly at gameplay scale but kept inside the 96×96 logical cell in all non-attack poses.
- Cape: short and controlled. It may trail during run/death but must not obscure feet or create unstable pivots.
- No baked muzzle flash, UI, text, floor shadow, background, particles, bloom halo, or detached VFX in actor frames.

## Generated concept reference

The approved concept direction is the generated Rex asset-board from the project session: blue armored ranger, orange edge/emissive accents, cyan/blue visor and AR-9 energy, compact cape, high-contrast sci-fi survivor silhouette.

The concept board is **reference art only**, not a cuttable production spritesheet: its frames are illustrative and do not obey the exact 2784×768 grid/pivot contract. Final source art must be redrawn/re-rendered into the deterministic sheet described below.

## Required source sheet

File: `art_sources/rex.png`

- exact size: **2784×768 px**
- transparent RGBA background
- cell: **96×96 px**
- rows: `n, ne, e, se, s, sw, w, nw`
- columns: `idle(4), run(8), attack(6), hit(3), death(8)`
- total: **232 frames**

Column ranges, zero-based:

- idle: 0–3
- run: 4–11
- attack: 12–17
- hit: 18–20
- death: 21–28

Every frame must retain at least 2 transparent pixels around visible art.

## Pivot / contact rules

- Ground contact baseline must be stable across idle/run/attack/hit.
- Character center must not wander between frames.
- Run may use vertical body motion, but the planted foot must remain visually coherent.
- Attack recoil comes from torso/weapon motion, not whole-body sliding.
- Death may intentionally leave the normal pivot envelope.
- Do not independently resize individual frames.

## Direction rules

Rex and the AR-9 must be genuinely redrawn/re-rendered for all 8 directions. Do not create diagonal/back views by mechanically mirroring a front view.

Weapon layering must remain believable:

- front/near-side arms can occlude torso where appropriate;
- rear-facing views must show the backpack/cape/weapon relationship correctly;
- left/right directions may share design symmetry, but authored lighting and weapon handedness must remain consistent.

## Animation intent

### Idle — 4 frames

Subtle breathing and weapon-ready motion. No large cape swing. Loop must be seamless.

### Run — 8 frames

Readable two-step tactical run cycle. Strong planted-foot poses, controlled cape follow-through, weapon held stable enough to preserve Rex's precision identity.

### Attack — 6 frames

AR-9 firing cycle: anticipation → trigger/recoil → recoil peak → recovery. Muzzle flash is rendered by the VFX system and must not be baked into Rex.

### Hit — 3 frames

Fast readable flinch: impact → recoil → recovery. Preserve gameplay silhouette and facing.

### Death — 8 frames

Loss of balance → collapse → settled pose. Keep the sequence readable without gore and without detached particles.

## Phone-scale readability

At actual gameplay scale, preserve these features first:

1. bright visor/helmet read;
2. AR-9 silhouette;
3. blue armored torso mass;
4. orange accent landmarks;
5. compact cape direction.

Tiny armor engraving and micro-surface detail are secondary and must not create noise.

## Palette discipline

Use a controlled palette family rather than per-frame recoloring:

- near-black/navy undersuit;
- cobalt/royal-blue armor;
- cyan/blue visor and weapon energy;
- orange/amber emissive accents;
- cool steel neutral details.

Highlights must be authored consistently with the game's top/upper-side lighting direction. Avoid full-body bloom.

## Acceptance gate

Rex is final only when all of the following pass:

```bash
python3 tools/validate_final_sprite_layout.py
python tools/build_final_sprite_frames.py --clean --strict
gradle buildFinalAtlas
gradle verifyFinalAtlasCoverage
```

The sheet must also pass visual review at native phone gameplay scale: no pivot jitter, no sliding feet, no clipped rifle/cape, no empty cells, no accidental background pixels, and clear attack/hit/death silhouettes.

Once Rex passes, the same silhouette/pivot/material/readability standard becomes the baseline for Nyx, Bastion, Volt, Wraith, bosses, biome signatures and generic enemies.
