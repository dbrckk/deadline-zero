# Deadline Zéro — native 3D Godot

This is the active **Godot 4.7.2** native-3D first-playable lane in `dbrckk/deadline-zero`. It is **not** yet a certified Play Store release or an independently verified AAA-quality production build. The repository also contains an older Java/libGDX Android lane; checks for that lane must not be confused with native Godot device measurements.

## Run the actual game

Open `godot/project.godot` in Godot **4.7.2**, then run `res://scenes/Main.tscn`. The renderer is **Mobile**, with 3D MSAA, ETC2/ASTC import support, a 60 Hz physics tick and a landscape/mobile canvas layout.

- **Movement:** WASD/arrow keys or the left-hand Android touch-drag joystick. The player aims/fires automatically; target switching has hysteresis and bounded moving-enemy projectile lead.
- **Combat:** six weapon protocols (Vanguard, Scatter, Rail, Inferno, Cryo and Arc); actual 3D muzzle/trail/impact feedback, status effects and swept shot collision with archetype-specific target footprints.
- **Enemies:** eight authored zombie archetypes (Shambler, Runner, Charger, Harrier, Regenerator, Brute, Elite and Boss), with imported skinned Quaternius GLTF meshes/animations, unique silhouettes, colored material grades, attack tells and boss phases.
- **Progression:** magnetized XP pickups with ordinary/elite/boss visual tiers, upgrade choices, wave pressure and game-over/restart UX.
- **Arena:** authored industrial props, PBR atlas surfaces, decals, floor wear, low-cost contact shadows, distinct atmosphere and mobile-budgeted shadows. Four detailed project-owned filtration reactor machines add machined 3D rotors, coolant tubing, ventilation, hazard armor and physically textured panels as seven batched mesh finishes per model; all lighting remains within the existing mobile budget. Existing project-owned Blender emergency-light lenses and shadowless floor pools shift smoothly from cyan to copper as horde pressure rises; boss defeat cools the lighting again. Reduced-flashes disables its brightness pulse without suppressing the threat color cue. No additional lights, materials, meshes or render passes are created for this effect.

## Production art and licensing

The playable scene uses **real imported GLTF assets and authored in-engine visuals** rather than concept images. Sources and usage requirements are tracked in `godot/assets/third_party/ASSET_SOURCES.md`, including the Quaternius Zombie Apocalypse Kit and industrial props. Imported textured surfaces retain their authored UV atlases; enemy grades use shared opaque materials and per-instance variations instead of allocating a separate material for every horde member.

The current art direction emphasizes legible three-quarter-view character silhouettes, tactile projectile response, contained emissive accents, atmosphere and consistent UI spacing. Imported low-poly models still require material/animation refinement and inspection on an actual phone before reaching the intended premium visual bar.

## Verification and Android artifacts

The repository's GitHub Actions workflows run the real Godot integration scripts from `godot/tests/` in addition to its independent Android checks. In particular:

- `authored_asset_validation.gd`, `enemy_silhouette_identity_test.gd`, `authored_gait_sync_test.gd` and `archetype_roster_render_test.gd` inspect real imported geometry, animations, materials and rendered composition.
- `weapon_protocol_behavior_test.gd`, `rifle_muzzle_ballistics_test.gd`, `first_playable_run_path_test.gd` and `xp_orb_runtime_test.gd` exercise actual runtime gameplay.
- The **Godot Android First Playable** workflow exports and smoke-tests a native Android debug APK; **Godot Play AAB Verify** validates the signed bundle path. The exact workflow artifacts and test results are on GitHub Actions; a green CI job is not evidence of real-device thermal or FPS performance.
- `.ai/project-state.md` and `.ai/session-state.json` contain the live work state and pending PRs.
- [Physical Android QA procedure](PHYSICAL_ANDROID_QA.md) grades real Godot `DZ_PERF` p95/hitch/FPS windows together with process crash/ANR findings. This is a diagnostic gate, not a substitute for player-controlled review or thermal measurement.


## Remaining launch gates

The native APK still requires representative **physical Android** runs covering p95 frame pacing and spikes, thermal throttling, touch precision, crash/ANR recovery and on-screen legibility. Play Store release and billing lifecycle criteria remain tracked in repository issues **#14, #16 and #17**. Do not promote a debug APK to a release candidate until those device and store checks have been performed.

