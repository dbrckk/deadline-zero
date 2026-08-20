# Final production art rollout

This document turns the final-art goal into deterministic acceptance gates. Bootstrap/generated placeholders remain valid runtime fallbacks, but never count as release-complete art.

## Gate A — Reference actor: Rex

Rex is the mandatory quality bar before mass-producing other actors.

Required source: `art_sources/rex.png`

Contract:
- 2784×768 RGBA source sheet.
- 96×96 logical cells.
- 8 authored directions: n, ne, e, se, s, sw, w, nw.
- Per direction: idle 4, run 8, attack 6, hit 3, death 8.
- 232 usable frames total.
- Stable ground-contact pivot and at least 2 transparent pixels around visible art.
- No baked muzzle flash, floor shadow, text, UI, bloom halo, particles or background.

Acceptance:
1. `python3 tools/validate_rex_reference.py`
2. `python3 tools/validate_final_sprite_layout.py`
3. `python3 tools/build_final_sprite_frames.py --clean --strict`
4. `gradle buildFinalAtlas`
5. `gradle verifyFinalAtlasCoverage`
6. Native-phone visual review: no pivot jitter, sliding feet, clipped rifle/cape, empty cells or background contamination.

## Gate B — Survivors

After Rex passes, produce Nyx, Bastion, Volt and Wraith with the same standard-profile animation budget and pivot rules. Each survivor must retain a distinct silhouette at phone scale; recolors of Rex do not qualify.

## Gate C — Bosses

Produce Alpha, Revenant, Warden, Harvester and Null Archon using the boss profile in `final-sprite-layout.json`. Boss silhouettes, attack anticipation and death animations must remain readable under heavy projectile/VFX load.

## Gate D — Biome signatures

Produce Forge Hound, Cinder Gunner, Slag Guard, Phase Stalker, Static Seer and Null Ward. Their authored silhouettes must communicate their gameplay pattern before color alone is considered.

## Gate E — Generic enemy roster

Produce Shambler, Runner, Brute, Ranged, Elite, Shielded, Regenerator and Phantom. These may share material language, but their silhouette, speed read and threat class must remain distinct.

## Gate F — Combat VFX

Replace procedural/bootstrap presentation with authored atlas sequences while preserving procedural fallbacks:
- muzzle fire;
- kinetic/energy, fire, frost, shock and kill impacts;
- explosions;
- dash/trails;
- level-up;
- boss death/phase attacks;
- Overdrive, Singularity and Apex overlays;
- shields, portals and void/null effects.

Persistent aura sequences must loop cleanly. One-shot effects must not freeze on their terminal frame.

## Gate G — Environments

For every biome, deliver terrain, borders, props, hazards and decals as reusable authored modules. Repetition must not be obvious during a normal run. Decorative art must never obscure collision boundaries, projectiles or telegraphs.

## Gate H — UI/store art

Deliver final HUD icons, upgrade/skill icons, menu art, app icon, 1024×500 feature graphic and representative store screenshots captured from the actual final build.

## Release-art definition of done

Release art is complete only when:
- final atlas coverage passes without relying on bootstrap sprites;
- every required actor animation meets the machine-readable frame budget;
- no placeholder/bootstrap asset is visible in a normal complete run;
- combat remains readable at native phone scale;
- memory/atlas size remains inside Android production budgets;
- screenshots used for store submission are captured from the shipping build.

Concept boards generated during development are visual references only. They must be redrawn/re-rendered into the deterministic source-sheet contracts before being considered production sprites.