# Kenney Space Kit — curated source subset

Official pack: **Space Kit** by Kenney.

Official page: https://kenney.nl/assets/space-kit  
License: **Creative Commons CC0 1.0 / public domain dedication**.  
Commercial use and redistribution are permitted; attribution is not required.

This directory contains a deliberately small source-geometry subset for Deadline: Zero. These files are **kitbash/source geometry**, not final hero-facing materials. Production use should pass through `tools/blender/optimize_game_asset.py`, then receive Deadline: Zero PBR materials from the approved Poly Haven / project-owned material stack.

Transport mirror used for the exact FBX bytes:
- Repository: https://github.com/beep2bleep/FreeAssetsByKenneyNLandQuaternius
- Pinned mirror commit: `dea756baf3b3a4889d8c245e456a4791f961578a`
- Original asset author/license remain Kenney / CC0; the mirror is not treated as the licensing authority.

Imported source files:
- `console.fbx`
- `console_screen.fbx`
- `metal_fence.fbx`
- `metal_structure_cross.fbx`
- `pipe_straight.fbx`
- `pipe_corner.fbx`
- `pipe_opening.fbx`
- `satellite_dish.fbx`
- `barrel_large.fbx`
- `station_module.fbx`

Intended roles:
- consoles and station module: background/hero set dressing after PBR rematerialization;
- pipes/opening: wall and floor service-detail kit;
- metal fence/structure: arena silhouette and perimeter breakup;
- barrel/satellite dish: secondary storytelling props.

Mobile rule: do not ship an FBX directly just because it imports. Promote only reviewed GLB output that meets `asset_manifest.json` budgets.
