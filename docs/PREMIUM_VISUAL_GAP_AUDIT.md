# Premium visual gap audit

This document is a release-quality checklist for Deadline: Zero. It uses public survivor-shooter presentation patterns as **abstract design references only**. No third-party art, code, text, branding, layouts or proprietary assets are copied.

## Release rule

A screen is not considered visually approved only because it compiles or uses the premium renderer. Approval requires a real Android capture at the wide-phone QA aspect and a manual check for:

- immediate focal hierarchy;
- readable text at phone scale;
- authored or intentionally procedural iconography;
- no debug-like empty space, flat slabs or accidental opaque alpha;
- no overlapping text, bars or controls;
- consistent material depth and accent use;
- touch targets that remain visually distinct;
- no obvious texture repetition or dominant decorative motif;
- combat actors readable against the environment;
- feedback that communicates damage, pickups, upgrade state and danger without obscuring play.

## Current screen audit

| Surface | Current state | Remaining visual gap / gate |
| --- | --- | --- |
| Base / Home | Premium renderer, hero staging, authored selected-weapon preview | Character asset fidelity and typography remain below the long-term target; verify every asset refresh on Android |
| Survivors | Premium hero panel, larger staged operative | Character asset fidelity remains the largest gap |
| Arsenal | Premium grid/detail chrome, enlarged authored weapon showcase | Weapon art fidelity and richer item iconography remain the main gap |
| Gear | Premium inventory and equipment-bay empty state | Verify populated inventory captures in addition to the empty state |
| Missions | Premium panels/cards, progress rails, mission/achievement icons | Verify dense/claimable states after premium-panel migration |
| Shop | Premium chest/offer cards and authored scalable glyphs | Product-art richness is still lighter than top commercial references |
| Cloud Save | Premium state focal graphic and action hierarchy | Utility screen; readability and conflict/error states must remain primary |
| Settings | Grouped premium control panels and segmented sliders | Typography and long localized strings remain the main QA risk |
| Run Contract | Premium risk/reward cards and CTA hierarchy | Add/retain stronger biome identity if future screenshots feel too abstract |
| Run Results | Premium hero/reward/coaching hierarchy | Wide-phone Android capture added; approve only after capture inspection |
| Victory | Premium reward celebration and next-stage CTA | Wide-phone Android capture added; approve only after capture inspection |
| Combat HUD | Responsive HP/XP, boss timeline, event cues, mobile controls | Keep HUD compact enough that the battlefield remains dominant |
| Upgrade choice | Premium shared card renderer | Re-check after combat alpha blending fix; previous capture showed opaque slabs |
| Legendary choice | Premium shared card renderer | Same gate as standard upgrade overlay |
| Game Over / Revive | Premium modal treatment | Add dedicated Android capture if regressions appear |
| Gameplay world | Authored actors, authored biome atlas, VFX, lighting/shadows | Camera framing, actor readability, environment repetition and density are active gates |
| Boss presentation | Identity-specific phases, telegraphs, transitions and audio/haptics | Validate each boss on Android; do not infer quality from code alone |

## Survivor-shooter benchmark lessons

Useful general principles observed in established mobile survivor shooters:

1. **The player is the visual anchor.** The camera should follow the operative, not leave them as a small object in a large static arena.
2. **Combat space must beat decoration.** Hazard motifs and floor patterns should support navigation, not become the dominant image.
3. **Large readable silhouettes win on phones.** Player, enemy and weapon art must survive small physical screens.
4. **Reward decisions need immediate visual parsing.** Upgrade cards should communicate rarity and choice hierarchy before the player reads every word.
5. **Meta screens should show the item/reward, not only text about it.** Weapons, chests, characters and rewards need visual focal objects.
6. **Victory and reward screens should escalate presentation.** Completion needs stronger hierarchy than ordinary menus.
7. **One-handed combat benefits from stable framing.** Auto-fire/auto-aim systems feel better when the operative remains predictably framed and threats enter the camera in a readable way.
8. **Density should rise without visual mud.** More enemies and effects are useful only when player silhouette, hostile telegraphs and pickups remain separable.

## Active refactor in this pass

- explicit alpha blending for the combat ShapeRenderer pipeline;
- tighter camera zoom and player-follow framing;
- larger player presentation;
- larger floor tiles to reduce visible repetition;
- lower decorative hazard density and removal of the Quarantine Yard center stripe;
- smaller onboarding obstruction;
- premium upgrade-choice chrome;
- premium mission panels/cards;
- Android QA coverage extended to Run Results and Victory.

## Next visual gates after this pass

1. Inspect new wide-phone screenshots for Combat, Run Results and Victory.
2. Inspect the Android gameplay visual artifact, especially upgrade-pool and biome crowd captures.
3. If actor/weapon fidelity is still the dominant gap, improve the actual production art source rather than adding more UI chrome.
4. Keep performance telemetry green; premium presentation must not compromise stable frame pacing.
