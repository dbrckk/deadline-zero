# Deadline Zero — Premium art pipeline

## Goal
Reach a polished mobile 2.5D visual standard comparable in production quality to leading survivor shooters, while keeping Deadline Zero's own art direction and existing 2D atlas runtime.

## Production strategy
Use 3D as the consistency source, then render sprites for the existing game:

1. Create one authoritative 3D character model.
2. Rig once.
3. Author five actions: `idle`, `run`, `attack`, `hit`, `death`.
4. Render each action from 8 orthographic directions.
5. Render masters at 512x512 with alpha.
6. Downsample and normalize to the actor contract (`96x96` for standard actors).
7. Assemble the contracted source sheet.
8. Run existing strict QA and atlas packing.

This avoids independent AI-generated frames drifting in costume, proportions, weapon geometry, lighting, or silhouette.

## Rex quality target
Rex is the reference-quality actor.

Visual identity:
- dark navy / near-black armor;
- cyan visor or energy accent;
- restrained orange highlights;
- clear rifle silhouette;
- compact cloak / tactical cloth element;
- readable silhouette at phone scale;
- no excessive micro-detail that disappears after downsampling.

## Animation contract
Standard actor counts per direction:

| Action | Frames |
| --- | ---: |
| idle | 4 |
| run | 8 |
| attack | 6 |
| hit | 3 |
| death | 8 |

Directions: `n`, `ne`, `e`, `se`, `s`, `sw`, `w`, `nw`.

Total for one standard actor: 232 frames.

## Render standard
- Blender Eevee.
- Transparent background.
- Orthographic camera.
- 512x512 master frame.
- Stable camera and actor origin across all frames.
- Feet remain anchored to the same ground plane.
- No baked UI.
- No baked muzzle flash in base attack frames; weapon VFX should be layered in the game unless explicitly required.
- Character receives coherent key/fill/rim lighting that remains constant across all directions.
- Avoid camera-relative lighting that causes the same material to change identity between directions.

## Downsampling
Do not author directly at 96x96 when a 3D source is available.

Recommended path:

`512x512 render -> alpha crop / pivot normalization -> 96x96 production cell`

The final reduction is where edge quality and material readability are judged. If important details disappear at 96x96, simplify the model or increase value/shape contrast instead of adding more texture detail.

## VFX standard
Character art alone is insufficient for a premium result. Gameplay polish must include:
- ground/contact shadows;
- hit flash;
- recoil and impact response;
- muzzle flash as separate VFX;
- projectile trail where appropriate;
- enemy hit particles;
- death particles/debris where appropriate;
- restrained screen shake;
- high-contrast damage feedback;
- environment decals and impact marks where performance permits.

## Environment standard
Use a darker/desaturated world so gameplay actors and VFX remain readable. Each combat arena should combine:
- large readable floor shapes;
- medium props for structure;
- small debris/detail clusters;
- value separation between walkable area and obstacles;
- coherent directional lighting;
- enough variation to prevent a tiled/empty appearance.

## Tooling
Render all directions/actions from Blender:

```bash
blender -b path/to/rex.blend -P tools/blender/render_actor_8dir.py -- \
  --actor rex \
  --output build/blender_renders/rex
```

Then normalize/assemble rendered frames into `art_sources/rex.png` and run:

```bash
python tools/build_final_sprite_frames.py --clean --strict
```

When all contracted actors exist:

```bash
python tools/build_final_sprite_frames.py --clean --require-all --strict
./gradlew buildFinalAtlas
./gradlew verifyFinalAtlasCoverage
```

## Acceptance gate for Rex
Rex is not considered production-ready until:
- all 232 frames exist;
- all 8 directions are visually correct;
- character design is invariant across every action and direction;
- no visible pivot jitter during idle/run;
- no frame violates transparent padding requirements;
- all important equipment remains readable after 96x96 downsample;
- strict sprite QA passes;
- atlas verification passes;
- Rex remains immediately distinguishable from enemies during crowded phone-scale gameplay.

Only after this gate passes should the same pipeline be applied to the remaining actors.
