# SHAMBLER — production art reference

Status: first reusable enemy-pipeline target after Rex M1.

## Role

The Shambler is the baseline melee hostile. It must read instantly at phone scale as a slow infected humanoid without competing with Rex's cyan/navy hero identity. Its job is to establish the reusable enemy-production conventions: source ingestion, rig/action mapping, eight-direction render, fixed-pivot normalization, atlas publication and crowded Android QA.

Runtime atlas root: `enemy/shambler`.
Final cell: `96x96`.
Directions: `n, ne, e, se, s, sw, w, nw`.
Frames per direction: idle 4, run/walk 8, attack 6, hit 3, death 8. Total: 232.
Runtime presentation height: 1.34 world units (`ArtProfileCatalog.SHAMBLER`).

## Visual identity

Silhouette must remain humanoid but visibly corrupted at 96 px:

- forward-biased torso and asymmetric shoulder line;
- one arm hanging lower/heavier than the other;
- slightly widened, unstable stance;
- head pushed forward rather than heroic/upright;
- hands readable as clawing/grasping forms, not fused blobs;
- no firearm or large held prop;
- no cape, backpack or silhouette elements that can be confused with Rex.

Target material language:

- desaturated sickly flesh as the primary biological read;
- charcoal/dark workwear or torn utility clothing for mass separation;
- restrained infected accent in warm amber/red, never Rex cyan;
- eyes/lesions may carry a small emissive accent, but should not become the largest bright area;
- avoid flat full-body green. Enemy readability should come from silhouette, value structure and motion first.

## Source strategy

Preferred bootstrap master: a CC0 animated zombie/enemy from Quaternius' **Zombie Apocalypse Kit** (March 2024), discovered from the creator's official pack page and official public Google Drive folder. The external asset is a starting geometry/rig source, not the final art direction. It may be materially restyled and animation-remapped before rendering.

Source acceptance requirements:

1. license evidence must remain CC0/public-domain;
2. source must be reproducibly retrievable from the official creator page/folder or be fingerprinted and preserved in-repo;
3. humanoid topology/rig must survive attack, hit and death deformation;
4. geometry must not contain unusable fused limbs at 96px;
5. source animations are optional — our exact five-motion contract remains authoritative;
6. no external runtime dependency: final game consumes only the produced atlas.

## Motion language

### Idle — 4 frames

Low-frequency diseased sway. Weight should lag, shoulders uneven, hands not perfectly still. Feet must remain planted; no root recenter jitter.

### Run / locomotion — 8 frames

For the Shambler this is a fast walk/lurch, not a heroic sprint. The cycle needs a visible left/right transfer at 96 px, mild torso drag and a forward pull. Avoid exaggerated vertical bounce.

### Attack — 6 frames

Readable melee sequence: coil/reach -> committed claw/swing -> contact pose -> recovery. The attack silhouette must change enough to be recognized when surrounded by several enemies. No baked impact flash.

### Hit — 3 frames

Short directional recoil with head/shoulder snap. It should be punchy enough to read under damage-number/VFX pressure but not displace the root dramatically.

### Death — 8 frames

Loss of balance -> collapse. Preserve visible displacement across frames. Final pose must clearly stop reading as a live standing Shambler. Avoid clipping limbs into a single dark mass.

## Render baseline inherited from Rex M1

- Blender Eevee/headless-compatible production path;
- transparent background;
- orthographic camera;
- stable actor origin and floor plane;
- fixed camera/lighting convention across all directions;
- high-resolution masters first (target 512x512), then deterministic 96px normalization;
- fixed transform per direction across all motions so animation/root motion is preserved;
- no per-frame recentering;
- no baked gameplay VFX;
- linear-filtered atlas at runtime;
- real Android gameplay capture remains the final acceptance gate.

## Enemy-specific QA gates

Structural gate:

- exactly 232 frames;
- all eight directions present;
- stable alpha padding;
- no frame exceeds the 96x96 cell after normalization;
- feet/pivot stable for idle/locomotion;
- non-zero motion preserved for attack/death;
- atlas prefix `enemy/shambler/{dir}/{motion}` resolves for all motions.

Visual gate:

- silhouette clearly hostile beside Rex at phone scale;
- body remains readable against current environment values;
- no direction appears mirrored incorrectly;
- attack reaches outward enough to read but does not look like a ranged action;
- hit/death do not expose catastrophic skinning;
- several Shamblers can crowd Rex without turning into an indistinguishable bright blob;
- hero cyan remains visually dominant over enemy accents.

## Acceptance definition

SHAMBLER M1 is accepted only after the generated production atlas passes structural QA, the repository Verify workflow remains green, the runtime selects authored `enemy/shambler` regions rather than fallback art, and Android visual-QA screenshots demonstrate stable scale, orientation, animation readability and crowd separation beside Rex.
