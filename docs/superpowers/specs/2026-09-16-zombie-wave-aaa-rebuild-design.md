# Deadline Zero — Zombie Wave AAA Rebuild Design

Date: 2026-09-16
Branch: `aaa-zombie-wave-rebuild`
Base: `main` @ `5d1731f25bc09fb7ac7882d5352e505dceb77cd5`

## 1. Objective

Rebuild Deadline Zero’s presentation around a clear **dark sci-fi biohazard zombie-wave survivor** identity, while preserving the existing combat simulation, progression, save model, content counts, billing boundaries, and production actor roster.

The target is not merely “clean responsive UI.” The target is a **premium, high-impact, readable survivor shooter presentation** that feels intentionally authored during dense horde combat and remains legible on real landscape Android phones such as 1536×691.

Success means the game no longer looks like a technical prototype in either combat or meta screens. Combat must communicate horde pressure, danger, impact, progression, boss escalation, and player power at a glance. Meta screens must feel like a cohesive operations hub rather than bordered debug panels.

## 2. Product pillars

### 2.1 Horde-first readability

Every frame should make four things immediately clear:

1. where the player is;
2. which silhouettes are hostile and how dangerous they are;
3. which attacks/projectiles/areas are dangerous;
4. what the player should care about next: survival, upgrade, boss, pickup, objective, or escape space.

Enemy count must read as a **zombie horde**, not as unrelated sprites scattered over a tiled background.

### 2.2 Premium dark biohazard identity

The visual language is charcoal/gunmetal with controlled cold-cyan technology accents, amber warning states, red biohazard/damage states, and restrained infected green. Cyan must no longer dominate entire panels or arena geometry.

### 2.3 Combat feel before menu polish

The priority order is:

1. combat HUD;
2. arena/world presentation;
3. enemy and projectile readability;
4. hit/death/pickup feedback;
5. base/home hub;
6. remaining meta screens;
7. motion and transition polish.

No meta-screen cosmetic work may delay fixing combat readability.

## 3. Non-goals

This rebuild does **not**:

- change core damage, spawn, progression, economy, save, billing, or settlement rules unless a rendering requirement exposes a correctness bug;
- expand the 24/24 production actor roster merely to create more art volume;
- replace the current game with 3D or photorealistic rendering;
- add a heavy external UI/animation runtime;
- trade performance or accessibility for visual effects;
- rely on large opaque overlays that obscure combat.

The quality bar is AAA-style production polish **within Deadline Zero’s stylized 2D/pixel-art visual identity**.

## 4. Existing architecture to preserve

The current code already has useful separation that should be retained:

- `GameScreen` owns simulation orchestration and delegates HUD, sprite, polish, and world-FX presentation;
- `CombatHudRenderer` owns responsive HUD rendering;
- `CombatSpritePass` owns combat sprite presentation;
- `WorldFxRenderer` and pooled FX classes own world-space feedback;
- `UiViewport`, `UiLayout`, `UiRenderer`, `UiTypography`, and `UiMotion` provide the responsive meta/UI foundation;
- Android visual probes already produce deterministic gameplay and 1536×691 UI captures.

The rebuild should **extend these boundaries**, not collapse rendering back into `GameScreen`.

## 5. Combat presentation architecture

Introduce or evolve four focused presentation layers.

### 5.1 `CombatWorldRenderer`

Purpose: render the arena as an authored environment rather than a repeated flat tile field.

Responsibilities:

- biome base material;
- large-scale ground breakup;
- roads / industrial lanes / containment zones;
- hazard markings used sparingly and semantically;
- static props and silhouettes;
- blood/infection/debris decals;
- soft world shadows;
- vignette/atmosphere hooks;
- biome-specific color grading inputs.

The renderer must be deterministic from biome + run seed where practical so Android visual QA remains stable.

### 5.2 `CombatReadabilityPass`

Purpose: preserve player/enemy/projectile clarity under horde density.

Responsibilities:

- player grounding/shadow/rim treatment;
- hostile ground shadows;
- elite/champion semantic accents;
- boss presence treatment;
- friendly versus hostile projectile separation;
- danger telegraphs;
- pickup halo/pulse treatment;
- optional crowd-density simplification at low graphics quality.

No effect may rely on color alone. Existing champion badges/semantic cues remain valid and can be integrated into the new style.

### 5.3 `CombatFeedbackFx`

Purpose: make shots, hits, kills, crits, dashes, pickups, and boss actions feel immediate.

Required vocabulary:

- muzzle flash / fire impulse;
- hit flash;
- impact spark or infection/blood burst;
- critical hit emphasis;
- directional knockback feedback;
- death burst + short-lived corpse/blood/debris decal where budget allows;
- pickup spawn/collect pulse;
- dash streak/afterimage appropriate to accessibility settings;
- boss telegraph wind-up and release;
- low-HP danger treatment.

Effects must scale with existing FX quality and thermal systems.

### 5.4 `CombatHudRenderer` v2

The HUD is redesigned around **minimal obstruction and priority grouping**.

#### Top-left: player survival

- compact HP container;
- optional armor/shield only if active;
- level as a small adjacent badge rather than a second giant horizontal card;
- stage/threat contextual line below or adjacent.

#### Top-center: run progression

- narrow XP progress integrated into the top rail;
- boss ETA shown as text/badge, not a second long dark panel;
- boss state replaces ETA with a dedicated boss-health treatment when active.

#### Top-right: horde status

- kills;
- elapsed time or boss countdown, whichever is more useful;
- active encounter/contract represented as compact chips.

#### Center screen

No permanent instructional panel. Onboarding uses short-lived, context-sensitive **toast hints** positioned away from the player and danger center.

#### Bottom controls

- joystick and dash controls use low-opacity glass/ring treatment;
- cooldown conveyed by radial fill/arc or inner ring state;
- controls remain visible enough for touch but visually recede when not interacting;
- desktop key labels must not dominate Android presentation.

## 6. Arena art direction

The first reference biome pass should use a **quarantine/industrial containment zone** as the visual standard.

Required layers:

1. **Base** — dark asphalt/concrete/metal surface with large-scale variation;
2. **Navigation structure** — roads, seams, containment lanes, drainage/grates;
3. **Story props** — barricades, crates, floodlights, warning posts, destroyed equipment, body bags/carcass silhouettes where appropriate;
4. **Contamination** — blood, infected residue, scorch, drag marks, damaged hazard markings;
5. **Depth** — shadows, pools of darkness, localized light accents;
6. **Atmosphere** — subtle smoke/dust/embers/spores depending on biome;
7. **Combat overlays** — telegraphs, impacts, pickups, abilities.

The screen must not contain large repeated hazard stripes as the dominant visual motif. Hazard markings are accents, not background wallpaper.

## 7. Zombie-wave identity

Enemy classes should be readable as horde roles even before reading HP bars or badges.

### Walkers / base infected

- grouped visual rhythm;
- lower contrast individually, strong threat as a mass;
- small locomotion variation so crowds do not look cloned frame-for-frame.

### Runners

- warmer/aggressive accent;
- stronger forward lean/motion streak or dust trail;
- clear speed threat.

### Brutes / juggernauts

- larger grounding shadow;
- heavier contact telegraph;
- stronger hit pause/camera impulse within accessibility limits.

### Ranged / spitters

- distinct pre-fire telegraph;
- hostile projectile language clearly different from friendly fire;
- projectile trail and impact zone visible against every biome.

### Elites / champions

- preserve non-color semantic markers;
- restrained aura/rim only where it improves instant recognition;
- stronger spawn/death event than normal enemies.

### Bosses

- dedicated entrance/presence treatment;
- phase transitions visually explicit;
- boss attacks always telegraphed through geometry/timing, not color only.

## 8. Meta UI direction

The current bordered-panel language is retained only as an underlying layout primitive, not as the final visual identity.

### 8.1 Home / Base

Recompose the screen as an **operations staging bay**:

- large survivor hero region with depth/background treatment;
- weapon/loadout card as a secondary information block;
- run/threat/contract status grouped into a compact mission-prep panel;
- primary `DEPLOY` CTA visually dominant but not a full-width cyan slab;
- bottom navigation simplified with stronger selected-state hierarchy and less empty framing;
- credits/gems/stage moved into a compact top status strip.

### 8.2 Survivors

- hero-first roster presentation;
- large selected survivor visual;
- role/trait and progression clearly grouped;
- navigation controls integrated into the card, not floating debug-like arrows;
- lock/select states use iconography + text + geometry.

### 8.3 Arsenal / Gear

- reduce spreadsheet feel;
- cards show silhouette/icon, rarity/state, one primary stat line, one secondary differentiator;
- selected weapon receives a visually richer detail panel;
- locked items remain readable without excessive disabled-gray text.

### 8.4 Contracts / Missions / Shop / Settings

These screens follow after combat + home are visually approved. They reuse the same surface, typography, badge, CTA, spacing, and motion tokens but may not regress into large empty rectangles.

## 9. Typography and iconography

- Use the existing bitmap-font pipeline only if it can meet the readability bar; if necessary, add a generated/packaged bitmap font with a controlled glyph set rather than relying on unsupported Unicode.
- Body/caption text must be readable at 1536×691 without squinting.
- Avoid displaying keyboard hints on Android unless relevant.
- Use simple rendered geometric icons where possible: cross/health, skull/kills, timer, warning, dash, lock, currency.
- Important states must combine icon/shape/text rather than color alone.

## 10. Motion

Motion is functional, not decorative.

Allowed:

- 120–220 ms menu focus/selection transitions;
- deploy confirmation sweep/fade;
- short panel entrance for run result/reward;
- hit/kills/pickup micro-feedback;
- boss intro/phase transition emphasis;
- subtle ambient world motion.

Reduced-motion mode replaces nonessential movement with static state changes and reduces shake/flash intensity.

## 11. Performance budgets

The visual rebuild must keep the current performance regression gate green.

Rules:

- no per-frame heap churn in horde hot paths;
- use existing pools for transient FX;
- decals/props use capped pools or static batches where feasible;
- expensive effects scale through existing low/medium/high/ultra and thermal quality controls;
- no new full-screen multi-pass effect unless measured and justified;
- the 40-enemy standard benchmark and 160-enemy/180-projectile stress benchmark must not introduce a CI regression versus current `main` under the existing comparator;
- physical-device validation remains mandatory before declaring the release gate complete.

## 12. Accessibility

Must preserve or improve:

- reduced motion;
- minimized flashes;
- screen-shake controls;
- high-contrast telegraphs;
- color-vision-safe semantic cues;
- UI scale;
- haptics toggle;
- touch target sizing.

New FX and telegraphs must have an accessibility-aware fallback.

## 13. Implementation sequence

### Phase A — combat vertical slice

1. lock screenshot baselines and tests;
2. HUD v2 layout/model;
3. tutorial toast replacement;
4. quarantine arena world renderer;
5. player/enemy shadow/readability pass;
6. projectile/telegraph readability;
7. impact/death/pickup FX pass;
8. boss presentation polish;
9. Android 1536×691 + standard visual QA;
10. performance comparison.

### Phase B — premium Base/Home

1. redesign menu layout model;
2. operations-bay composition;
3. survivor/loadout hierarchy;
4. deploy/threat/contract hierarchy;
5. mobile navigation polish;
6. visual QA at 1536×691 and 16:9.

### Phase C — remaining meta screens

Survivors → Arsenal → Gear → Contracts → Missions → Shop → Settings → Results/Victory.

Each screen migrates only after shared component changes are stable.

### Phase D — final polish

- transitions;
- typography/icon consistency;
- accessibility pass;
- crowd readability capture;
- boss capture;
- low/high quality comparison;
- full Android journey;
- physical-device APK test.

## 14. Testing strategy

### Unit/model tests

Add deterministic tests for:

- HUD region containment and no overlap at 1536×691, 1920×1080, 1280×720, and 16:10;
- toast placement outside central player-safe region;
- control hit targets;
- meta hero/CTA containment;
- graphics-quality effect budgets;
- any new deterministic style/role mapping.

### Android visual probes

Required captures on the final branch:

- combat early wave;
- combat dense horde;
- combat ranged-projectile pressure;
- elite/champion crowd;
- boss phase;
- low-HP state;
- pickup/upgrade state;
- Home/Base;
- Survivors;
- Arsenal;
- Contract.

Wide-phone profile must remain near 1536×691 and enforce the aspect-ratio assertion.

### Manual review criteria

A capture fails even if tests are green when:

- UI obscures the player or combat center;
- text requires zooming/squinting;
- hostile projectiles blend into world art;
- horde roles are visually ambiguous;
- effects hide telegraphs;
- repeated tiles/markings dominate the scene;
- the screen resembles a debug/prototype interface;
- empty rectangles dominate the meta composition;
- cyan is used as a large flat fill without semantic need.

## 15. Release gates

The rebuild is mergeable only when all of the following are true on one exact head:

- core tests green;
- Android production build green;
- desktop smoke green;
- Android runtime/journey/persistence green;
- existing performance comparator reports no blocker regression;
- responsive/wide-phone visual QA green;
- new combat visual artifact has passed manual inspection;
- new Base/Home capture has passed manual inspection;
- no unsupported glyphs/placeholders are visible;
- no blocker-severity visual/readability issue remains.

After merge, the exact `main` commit must regenerate and validate the APK/checksum. The build is not considered physically validated until tested on representative Android hardware.

## 16. Acceptance bar

The rebuild is complete when a first-time viewer can look at a single gameplay screenshot and immediately read:

- “this is a zombie/infected horde survival game”;
- where the player is;
- which enemies are most dangerous;
- where incoming danger is;
- how healthy/progressed the player is;
- whether a boss/encounter is approaching;
- what the immediate action state is.

And a first-time viewer can look at the Home/Base screen and understand:

- selected survivor;
- selected weapon/loadout;
- current run/threat state;
- the primary action to deploy;
- the available meta destinations.

The final visual result must feel authored, cohesive, responsive, and production-ready rather than merely functional.