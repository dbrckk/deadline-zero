# Deadline: Zero — AAA UI/UX Overhaul Design

Date: 2026-09-15
Branch: `aaa-ui-ux-overhaul`

## Objective

Replace the current pixel-coordinate-driven presentation with a cohesive, premium sci-fi interface that remains readable and touch-safe across landscape Android devices, including the 1536x691 physical-device case that exposed the current scaling failure.

The target is not literal big-budget AAA asset volume. The target is AAA-style presentation discipline: stable responsive layout, clear hierarchy, authored visual language, polished states and transitions, consistent interaction feedback, readable combat HUD, and production-level visual QA.

## Current problems

1. Multiple screens derive layout directly from `Gdx.graphics.getWidth()/getHeight()` and mix physical pixels, percentages, fixed sizes and font scaling.
2. `ShapeRenderer`, `SpriteBatch`, touch hit-testing and text do not share a single logical coordinate system.
3. Wide phones therefore produce disproportionate decorative geometry, tiny text, inconsistent hit areas and weak information hierarchy.
4. UI patterns are duplicated across `MenuScreen`, `SurvivorScreen`, `ArsenalScreen`, `GearScreen`, `MissionsScreen`, `ShopScreen`, `SettingsScreen`, contract/result/victory screens and the combat HUD.
5. Visual hierarchy currently relies too heavily on large cyan fills and small default-font labels instead of cards, depth, spacing and semantic emphasis.

## Direction

Use a dark premium military-sci-fi language:

- graphite/near-black layered surfaces;
- restrained cyan as interaction/accent, not background fill;
- amber/gold for progression/reward, red for danger, violet for special/legendary systems;
- thin luminous edge treatment and depth bands rather than large neon slabs;
- strong asymmetrical composition for home/deployment screens;
- survivor and weapon art as focal content;
- compact tactical HUD with large-value legibility and low combat obstruction;
- short motion/feedback cues rather than decorative continuous animation everywhere.

## Star-list review

`dbrckk/star-list` contains no direct libGDX UI framework appropriate for this runtime. Relevant repositories are useful as references rather than dependencies:

- `shadcn-ui/ui` — component-state discipline and token-driven design;
- `microsoft/fluentui` — hierarchy, accessibility, interaction states;
- `android/nowinandroid` — mobile navigation, touch ergonomics, adaptive layout principles;
- `motion-canvas/motion-canvas` — motion-graphics timing reference, not runtime integration;
- Lottie/Rive/Spine alternatives in the catalog — potentially useful for later authored animation, but adding them now would introduce runtime/tooling complexity without fixing the core layout problem.

Decision: do not add a new UI framework or animation runtime for this overhaul. Build on existing libGDX rendering and introduce a small in-project presentation layer.

## UI architecture

### 1. Logical viewport

Introduce `UiViewport` around libGDX `ExtendViewport` with a minimum logical canvas of 1280x720.

- Height remains approximately 720 logical units on wide landscape phones.
- Extra horizontal space is exposed rather than stretching 16:9 content.
- `resize(width, height)` updates the viewport for every screen.
- `apply()` configures projection for both `ShapeRenderer` and `SpriteBatch`.
- `unproject(screenX, screenY)` converts touch input into logical UI coordinates.
- No screen should directly use physical pixels for layout after migration.

### 2. Safe layout frame

Introduce `UiFrame`/`UiMetrics`:

- logical safe margins;
- header, content and bottom-navigation zones;
- spacing scale (4/8/12/16/24/32/48 logical units);
- minimum touch target of 56 logical units;
- compact and wide breakpoints based on logical width;
- helpers for center, left/right anchors and content columns.

Android display cutout/system-bar insets remain a future platform enhancement if not exposed by the existing launcher; the first pass guarantees internal safe margins and no edge-critical controls.

### 3. Shared visual primitives

Introduce a light-weight `UiRenderer` using the existing renderers:

- background field / vignette bands;
- tactical panel;
- selected/focused card;
- primary, secondary and destructive buttons;
- chips/badges;
- progress/stat bars;
- top status rail;
- bottom navigation;
- dividers, corner marks and accent rails;
- disabled/locked/pressed/focused/selected states.

Primitives use the existing accessibility-aware `VisualTheme` and add semantic surface/border/text tokens instead of screen-local colors.

### 4. Typography system

Introduce named style scales rather than arbitrary `font.getData().setScale()` values scattered across screens:

- DISPLAY
- TITLE
- SECTION
- BODY
- LABEL
- CAPTION
- METRIC

The implementation initially keeps the existing bitmap font dependency for risk control but applies consistent scale, contrast, alignment and line spacing. A higher-quality authored font asset can be swapped behind the typography layer later without screen rewrites.

### 5. Motion and feedback

Use deterministic short transitions implemented in-core:

- screen reveal: 160-220 ms;
- card focus/selection: 100-140 ms;
- CTA press: 70-100 ms;
- status confirmation: 180-260 ms;
- no mandatory motion for accessibility users who minimize motion/flashes.

Existing audio/haptic cues remain and are paired with visible pressed/selected states.

## Screen redesign

### Home / deployment

- Top rail: level, currencies, stage/threat status.
- Main left/content focal block: selected survivor portrait, name, role and concise build identity.
- Secondary loadout block: equipped weapon and threat tier.
- Right/bottom dominant deployment CTA with stage context.
- Bottom navigation uses evenly spaced tactile tabs with a clear active state.
- Decorative circle from the current screen is removed; ambient decoration becomes subtle framing, scanning lines and depth panels.

### Survivors

- Large portrait card with clear previous/next interaction zones.
- Name/role/level hierarchy.
- Five core stats rendered as grouped metrics rather than sentence text.
- XP/progression bar with explicit current/next values.
- Primary select/equipped/locked CTA with correct state.
- Tapping the screen no longer blindly selects; only explicit card/navigation/CTA hit targets act.

### Arsenal / Gear

- Responsive card grid based on available logical width.
- Focus state distinct from equipped state.
- Detail panel shows art, role/element, stat deltas and synergy.
- Previous/next page controls meet touch target minimums.
- Locked cards remain readable rather than being mostly obscured by a black overlay.

### Missions / Shop / Settings / Cloud / Contracts

- Migrate to shared header, card and CTA components.
- Group content into semantic sections.
- Ensure selection, disabled, warning and confirmation states are visually distinct without relying solely on color.

### Run result / victory

- Strong outcome headline and one dominant next action.
- Reward/progression summary in large metrics.
- Secondary details collapsed into compact cards.

### Combat HUD

Keep simulation untouched.

- Apply a dedicated logical HUD viewport with shared projection.
- Compact HP/XP/status rail at the top.
- Boss bar becomes visually dominant only when active.
- Stage/kills/contract become small but high-contrast tactical metrics.
- Dash control and virtual stick preserve existing input semantics but gain consistent ring/pressed/cooldown presentation.
- Onboarding hint uses a bounded readable panel instead of floating text.
- Maintain color-vision modes, high-contrast telegraphs, reduced flashes and UI scale settings.

## Responsive behavior

Primary validation formats:

- 1280x720 (16:9 baseline)
- 1536x691 (reported physical device, ~20:9)
- 1920x1080 (16:9 high resolution)
- 2400x1080 (20:9 wide)
- 2560x1600 (tablet-like 16:10)

No critical control may leave the safe frame, overlap another interactive control, or render below the minimum readable scale.

## Input contract

Every migrated screen must:

1. receive physical input coordinates;
2. convert them with the same `UiViewport.unproject` used for rendering;
3. hit-test against logical component bounds;
4. expose explicit interaction regions rather than whole-screen shortcuts;
5. retain keyboard controls for desktop smoke tests where they already exist.

## Testing strategy

### Unit tests

Add pure tests for:

- viewport/layout size calculations at the target aspect ratios;
- safe-frame containment;
- touch conversion/hit target helpers;
- typography and spacing token invariants;
- responsive grid column/card calculations;
- state-specific component presentation data.

### Runtime tests

Extend Android runtime capture to include at least:

- home/deployment;
- survivor roster;
- arsenal;
- settings;
- combat baseline;
- upgrade overlay;
- boss HUD;
- result/victory.

Capture at a standard emulator format and an additional wide-phone format approximating 1536x691/20:9.

### Human visual QA

The overhaul does not merge merely because screenshots exist. Captures must be inspected for:

- readable text;
- clear primary action;
- no stretching or oversized decoration;
- correct touch-target spacing;
- consistent hierarchy and visual language;
- no clipped art/text;
- sufficient contrast;
- combat HUD obstruction and readability.

## Implementation sequence

1. Add logical viewport, layout metrics, UI tokens and unit tests.
2. Migrate/redesign Home and Survivors first because they reproduce the physical-device defect.
3. Add wide-phone Android screenshot coverage and verify the reported failure is gone.
4. Migrate Arsenal, Gear, Missions, Shop, Settings, Cloud, Contracts and result/victory screens.
5. Redesign Combat HUD through the same logical coordinate system while keeping combat simulation unchanged.
6. Add interaction-state polish, motion and accessibility reductions.
7. Run full core/desktop/Android/runtime/performance CI.
8. Inspect all visual artifacts manually.
9. Produce a first-playable APK from the validated commit.
10. Require one final physical-device smoke test before declaring the private first-playable fully complete.

## Performance constraints

- No per-frame texture allocation.
- Reuse existing `ShapeRenderer`, `SpriteBatch` and font objects.
- Keep UI animation math allocation-free in render loops.
- Do not add a heavyweight web/mobile UI runtime.
- Preserve existing Android performance regression gate.
- UI overhaul must not introduce blocker-level p95/p99/jank regressions.

## Non-goals

- Rewriting gameplay simulation or combat balance.
- Replacing the production actor atlas.
- Adding a third-party UI framework.
- Adding paid/proprietary art tooling as a runtime requirement.
- Store/public-launch work such as Play testing tracks, retention tuning or commercial economy tuning.

## Definition of done

The overhaul is done when:

- all production-facing screens use the logical UI coordinate system;
- 1536x691 no longer reproduces the giant-decoration/tiny-text failure;
- primary flows are visually coherent and touch-safe;
- Android visual QA covers both baseline and wide layouts;
- accessibility settings continue to function;
- full CI and performance regression gates pass;
- a new installable APK is produced;
- the APK completes a physical-device flow: launch -> home -> survivor/loadout -> deploy -> combat -> upgrade -> boss -> result -> save/restart without UI blocker.
