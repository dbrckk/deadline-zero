# Rex 3D master production brief

## Goal
Create one game-ready 3D master for Rex that can be rigged once and rendered into all Deadline Zero sprite animations. The master must remain visually identical across idle, run, attack, hit, and death.

## Preferred free/mobile-first route
Use Meshy in the browser for the first 3D pass because its current free tier supports model generation, free rigging/animation operations, and GLB/FBX export for eligible free generations. Free outputs are CC BY 4.0 and therefore require attribution.

## Rex visual target
- Adult helmeted futuristic ranger/soldier.
- Athletic, compact silhouette readable at very small mobile scale.
- Enclosed helmet with bright cyan visor.
- Dark navy / near-black hard-surface armor.
- Restrained orange armor accents.
- Compact dark tactical cape/scarf; avoid oversized cloth.
- AR-9-style sci-fi rifle held as the dominant horizontal shape.
- Practical boots, pouches, chest armor, forearm and shin guards.
- No exposed face.
- No logos, text, insignia, UI, base, pedestal, environment, muzzle flash, smoke, particles, or loose floating parts.
- Symmetrical enough to rig cleanly but with enough asymmetry to give Rex a recognizable silhouette.

## Meshy generation prompt
Game-ready stylized 3D character, full body, adult futuristic armored ranger named Rex, athletic compact proportions, enclosed sci-fi helmet with bright cyan glowing visor, dark navy and near-black hard-surface armor, restrained orange armor accents, compact dark tactical cape and scarf, practical pouches and boots, holding a compact futuristic assault rifle across the torso, premium mobile action-game quality, strong readable silhouette from top-down isometric camera, clean PBR materials, realistic hard-surface detailing but not photorealistic, optimized geometry, neutral A-pose, centered, isolated character, no environment, no floor, no text, no logo, no muzzle flash, no particles, no floating accessories.

## Negative guidance
Avoid: giant shoulder pads, huge cape, fantasy armor, medieval parts, exposed face, oversized head, chibi proportions, anime face, loose cables, transparent body parts, extra weapons, duplicate limbs, fused rifle/hands, deformed fingers, asymmetrical leg length, extreme micro-detail that will disappear at 96 px.

## Acceptance test before rigging
Reject the generation if any of these fail:
1. Both legs and arms are anatomically clean and separated enough for rigging.
2. Rifle does not merge into torso or hands.
3. Helmet and visor are clearly readable.
4. Character remains recognizable from front, side and rear views.
5. Cape does not hide both legs or the weapon.
6. No detached/floating geometry.
7. Silhouette remains readable when previewed at ~96 px height.

## Rig and animation contract
For the standard actor profile:
- idle: 4 sampled output frames per direction
- run: 8
- attack: 6
- hit: 3
- death: 8
- directions: N, NE, E, SE, S, SW, W, NW
- final cell: 96x96 RGBA
- master render: 512x512 RGBA or higher

Animation intent:
- Idle: subtle breathing and weapon-ready motion; feet essentially fixed.
- Run: readable full-body locomotion, limited cape swing, stable weapon.
- Attack: short rifle firing/recoil motion; do not bake muzzle flash into character frames.
- Hit: concise readable recoil without large displacement.
- Death: clean collapse with no gore requirement; body must remain inside render framing.

## Export
Preferred source file: `art_sources/3d/rex_master.glb`.
FBX is acceptable as an intermediate if animation/rig data is preserved.

After export, Deadline Zero's Blender renderer should produce 8-direction transparent masters, then the existing art pipeline downsamples/packs them into `art_sources/rex.png` and the final atlas.
