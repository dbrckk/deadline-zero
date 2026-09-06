# Rex master workflow

## Current source
Rex is exported from Meshy as an unrigged GLB. Keep the original GLB immutable as the HD source.

## 1 — Deterministic preparation

```bash
blender -b --factory-startup -P tools/blender/prepare_meshy_actor.py -- \
  --input path/to/rex.glb \
  --output build/rex_master_hd.blend \
  --actor Rex
```

This imports, grounds, centers, scales to 2 m, applies stable transforms, enables smooth shading, records source triangle counts and adds a neutral rigging material when the GLB is untextured.

Do **not** destructively reduce the only master. For an optional lightweight rigging copy:

```bash
blender -b --factory-startup -P tools/blender/prepare_meshy_actor.py -- \
  --input path/to/rex.glb \
  --output build/rex_rigging_proxy.blend \
  --actor Rex --decimate 20000
```

A decimated proxy is not automatically production topology. Inspect shoulders, elbows, wrists, fingers, hips, knees, ankles and cape before skinning.

## 2 — Rig gate

The raw Meshy export has no authoritative skeleton. Do not fabricate a rig merely to automate the step. Rigging is approved only when:

- pelvis/spine/head chain follows the body;
- shoulder and arm chains are symmetric;
- elbows and knees bend on the intended axes;
- wrists/hands remain stable;
- feet have stable ground contact;
- cape does not receive destructive body weights;
- deformation is acceptable in shoulder, elbow, wrist, hip and knee stress poses.

Use a standard humanoid rig in Blender or another rigging service, then return the rigged GLB/FBX/BLEND to this pipeline.

## 3 — Animation gate

Create actions named exactly:

- `idle`
- `run`
- `attack`
- `hit`
- `death`

Validate `idle` first. Do not author the complete set until the rig passes deformation QA.

## 4 — Render gate

```bash
blender -b build/rex_rigged.blend -P tools/blender/render_actor_8dir.py -- \
  --actor rex --output build/blender_renders/rex
```

This produces transparent 512x512 masters in eight directions. Inspect those renders at final phone scale before completing all animations.

## 5 — Production sprite gate

Normalize/downsample into the 96x96 actor contract, then run the repository's strict sprite build and atlas verification. Rex is approved only after the final in-game 96x96 result is readable and stable.
