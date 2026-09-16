# AAA UI/UX Overhaul Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace physical-pixel UI rendering with a responsive libGDX presentation system and migrate all player-facing screens/HUD to a cohesive premium sci-fi visual language without changing gameplay simulation.

**Architecture:** Introduce a small in-project UI layer under `com.deadlinezero.game.ui` that owns logical sizing, safe frame math, responsive breakpoints, shared visual tokens, input unprojection, typography scales, and reusable draw primitives. Migrate screens incrementally to the shared coordinate system; keep existing `SpriteBatch`, `ShapeRenderer`, authored art, localization, audio, haptics and accessibility settings. Extend Android visual regression coverage to a 20:9 wide-phone profile before merging.

**Tech Stack:** Java 17, libGDX, JUnit 5, Gradle, Android runtime/instrumentation CI, existing authored sprite atlas and accessibility settings.

**Spec:** `docs/superpowers/specs/2026-09-15-aaa-ui-ux-overhaul-design.md`

## Global Constraints

- Logical UI baseline is 1280x720 using an ExtendViewport-style model; wider screens expose extra horizontal space instead of stretching 16:9 content.
- Do not add a third-party UI framework or heavyweight animation runtime.
- Reuse existing `ShapeRenderer`, `SpriteBatch`, fonts and authored art; no per-frame texture allocation.
- Preserve color-vision modes, high-contrast telegraphs, reduced flashes/motion, UI scale and keyboard controls.
- Minimum logical touch target is 56 units.
- Primary validation sizes: 1280x720, 1536x691, 1920x1080, 2400x1080 and 2560x1600.
- Do not modify gameplay simulation, balance, production actor atlas or store/economy behavior.
- Final merge requires green core/desktop/Android/runtime/performance CI plus human inspection of baseline and wide-phone visual artifacts.

---

### Task 1: Responsive UI foundation

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/ui/UiLayout.java`
- Create: `core/src/main/java/com/deadlinezero/game/ui/UiViewport.java`
- Create: `core/src/main/java/com/deadlinezero/game/ui/UiTypography.java`
- Test: `core/src/test/java/com/deadlinezero/game/ui/UiLayoutTest.java`

**Interfaces:**
- Produces: `UiLayout.compute(int screenWidth, int screenHeight) -> Metrics`, `UiViewport.resize(int,int)`, `UiViewport.width()`, `UiViewport.height()`, `UiViewport.unproject(float,float,Vector2)`, `UiTypography.scale(Role)`.
- `Metrics` exposes logical width/height, safe left/right/top/bottom, header/content/footer bounds, compact/wide state and 56-unit touch minimum.

- [ ] **Step 1: Write failing layout tests**

```java
@Test void widePhoneExtendsHorizontallyWithoutShrinkingLogicalHeight() {
    UiLayout.Metrics m = UiLayout.compute(1536, 691);
    assertEquals(720f, m.height(), 0.01f);
    assertTrue(m.width() > 1280f);
    assertTrue(m.safeLeft() >= 24f && m.safeRight() <= m.width() - 24f);
}

@Test void requiredFormatsKeepSafeFrameAndTouchTargets() {
    int[][] sizes = {{1280,720},{1536,691},{1920,1080},{2400,1080},{2560,1600}};
    for (int[] s : sizes) {
        UiLayout.Metrics m = UiLayout.compute(s[0], s[1]);
        assertTrue(m.contentWidth() > 0f);
        assertTrue(m.touchTarget() >= 56f);
        assertTrue(m.safeBottom() < m.safeTop());
    }
}
```

- [ ] **Step 2: Run the focused test and confirm RED**

Run: `./gradlew :core:test --tests com.deadlinezero.game.ui.UiLayoutTest`
Expected: FAIL because `UiLayout` does not exist.

- [ ] **Step 3: Implement pure layout math and viewport wrapper**

Use `BASE_W=1280f`, `BASE_H=720f`; logical width is `max(BASE_W, BASE_H * screenWidth / screenHeight)`. Safe margin is `max(24f, (logicalWidth-BASE_W)*0.06f)`, capped so content remains at least 1180 logical units. Header=88, footer=92, content between those bands. `UiViewport` owns an `ExtendViewport(BASE_W, BASE_H)` and an `OrthographicCamera`, applies projection to SpriteBatch/ShapeRenderer and unprojects physical input.

- [ ] **Step 4: Run focused test and full core tests**

Run: `./gradlew :core:test --tests com.deadlinezero.game.ui.UiLayoutTest && ./gradlew :core:test`
Expected: PASS.

- [ ] **Step 5: Commit foundation**

```bash
git add core/src/main/java/com/deadlinezero/game/ui core/src/test/java/com/deadlinezero/game/ui
git commit -m "feat: add responsive logical UI foundation"
```

### Task 2: Shared premium UI renderer and theme tokens

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/ui/UiRenderer.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/VisualTheme.java`
- Test: `core/src/test/java/com/deadlinezero/game/ui/UiRendererStateTest.java`

**Interfaces:**
- Consumes: `UiLayout.Metrics`, `UiTypography.Role`.
- Produces stateless draw helpers for `background`, `panel`, `card`, `button`, `chip`, `progress`, `topRail`, `bottomNav`, `focusRail`, plus `ButtonState {NORMAL,PRESSED,SELECTED,DISABLED,DANGER}`.

- [ ] **Step 1: Write failing semantic-state tests** asserting disabled/selected/danger states resolve to distinct border/fill/label token sets and none rely on alpha=0.
- [ ] **Step 2: Run focused test and confirm RED** with missing `UiRenderer`.
- [ ] **Step 3: Add surface/border/text tokens** to `VisualTheme` (`SURFACE_0`, `SURFACE_1`, `SURFACE_2`, `BORDER`, `BORDER_FOCUS`, `TEXT_STRONG`, `TEXT_DIM`) while retaining existing semantic color helpers.
- [ ] **Step 4: Implement allocation-free primitive drawing** using rectangles, thin rails, corner marks and restrained accent fills; no textures allocated in render methods.
- [ ] **Step 5: Run tests**: `./gradlew :core:test --tests com.deadlinezero.game.ui.UiRendererStateTest && ./gradlew :core:test`.
- [ ] **Step 6: Commit**: `git commit -am "feat: add premium UI primitives and tokens"` plus new files.

### Task 3: Home/deployment screen migration

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/screen/MenuScreen.java`
- Test: `core/src/test/java/com/deadlinezero/game/screen/MenuLayoutModelTest.java`
- Create: `core/src/main/java/com/deadlinezero/game/screen/MenuLayoutModel.java`

**Interfaces:**
- Produces pure `MenuLayoutModel.layout(UiLayout.Metrics)` bounds for top rail, survivor focal card, loadout/threat card, deploy CTA and bottom tabs.

- [ ] **Step 1: Write failing tests** for 1280x720 and 1536x691 ensuring all interactive bounds stay within safe frame, deploy CTA >=56 high, survivor card does not overlap CTA, and bottom tabs remain evenly distributed.
- [ ] **Step 2: Confirm RED** with `./gradlew :core:test --tests com.deadlinezero.game.screen.MenuLayoutModelTest`.
- [ ] **Step 3: Implement layout model** using logical units only.
- [ ] **Step 4: Rewrite MenuScreen rendering** to call `UiViewport.apply`, `UiRenderer`, `UiTypography`, authored survivor art, restrained ambient scan/depth lines, explicit loadout/threat cards and dominant deployment CTA. Remove the large cyan decorative circle.
- [ ] **Step 5: Rewrite touch handling** to unproject via `UiViewport` and hit-test explicit bounds; preserve keyboard shortcuts.
- [ ] **Step 6: Add `resize()` and dispose viewport-owned resources where applicable.**
- [ ] **Step 7: Run focused and full tests**.
- [ ] **Step 8: Commit**: `git commit -am "feat: redesign responsive deployment home"`.

### Task 4: Survivor roster migration

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/screen/SurvivorScreen.java`
- Create: `core/src/main/java/com/deadlinezero/game/screen/SurvivorLayoutModel.java`
- Test: `core/src/test/java/com/deadlinezero/game/screen/SurvivorLayoutModelTest.java`

**Interfaces:**
- Produces explicit previous/next/select CTA hit regions, portrait/card/stats/XP bounds and wide/compact arrangement.

- [ ] **Step 1: Write failing tests** guaranteeing 1536x691 portrait, stats, XP and CTA do not overlap and only CTA/arrow bounds trigger actions.
- [ ] **Step 2: Confirm RED**.
- [ ] **Step 3: Implement layout model** with wide-phone two-column composition and compact fallback.
- [ ] **Step 4: Redesign SurvivorScreen** with focal portrait, role/level hierarchy, five grouped stat metrics, XP rail, locked/equipped/select button states and non-color lock cue.
- [ ] **Step 5: Remove whole-screen tap-to-select**; use unprojected explicit hit targets.
- [ ] **Step 6: Run tests and commit** `feat: redesign responsive survivor roster`.

### Task 5: Arsenal and gear responsive cards

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/screen/ArsenalScreen.java`
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GearScreen.java`
- Create: `core/src/main/java/com/deadlinezero/game/ui/ResponsiveGrid.java`
- Test: `core/src/test/java/com/deadlinezero/game/ui/ResponsiveGridTest.java`

**Interfaces:**
- Produces `ResponsiveGrid.columns(float contentWidth, float minCardWidth, int maxColumns)` and card bounds with >=16 gap and >=56 interaction height.

- [ ] **Step 1: Write failing grid tests** for all five target sizes.
- [ ] **Step 2: Confirm RED**.
- [ ] **Step 3: Implement ResponsiveGrid** with 2-column compact, 3-column wide where card width remains >=300 logical units.
- [ ] **Step 4: Migrate Arsenal** to shared header/cards/detail pane, explicit focus vs equipped state, readable locked cards and >=56 paging controls.
- [ ] **Step 5: Migrate Gear** to the same component language and unprojected input.
- [ ] **Step 6: Run tests and commit** `feat: migrate arsenal and gear to responsive cards`.

### Task 6: Remaining meta screens

**Files:**
- Modify: `MissionsScreen.java`, `ShopScreen.java`, `SettingsScreen.java`, `CloudSaveScreen.java`, `RunContractScreen.java`, `RunResultScreen.java`, `VictoryScreen.java` under `core/src/main/java/com/deadlinezero/game/screen/`.
- Test: `core/src/test/java/com/deadlinezero/game/screen/MetaScreenLayoutContractTest.java`

**Interfaces:**
- Each screen must expose or reuse a pure layout function/model whose interactive rectangles can be validated without GL.

- [ ] **Step 1: Add failing contract tests** that instantiate each layout model for 1280x720 and 1536x691 and assert containment/no critical overlaps/minimum touch height.
- [ ] **Step 2: Confirm RED**.
- [ ] **Step 3: Migrate Missions/Shop** to shared section cards and semantic CTA states.
- [ ] **Step 4: Migrate Settings/Cloud** to grouped panels, readable warnings/confirmation states and explicit actions.
- [ ] **Step 5: Migrate Contracts/Results/Victory** to outcome hierarchy with one dominant primary action and large reward metrics.
- [ ] **Step 6: Run focused/full core tests and commit** `feat: unify meta screens under responsive UI system`.

### Task 7: Combat HUD migration and polish

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java`
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java`
- Create: `core/src/main/java/com/deadlinezero/game/visual/CombatHudLayout.java`
- Test: `core/src/test/java/com/deadlinezero/game/visual/CombatHudLayoutTest.java`

**Interfaces:**
- `CombatHudLayout.compute(UiLayout.Metrics,float uiScale,boolean bossActive)` returns HP/XP/status/boss/onboarding/dash bounds; render continues to receive simulation state only.

- [ ] **Step 1: Write failing tests** for baseline/wide/tablet at UI scale 0.8/1.0/1.3 and boss active/inactive; assert no top-rail overlap and dash target >=56.
- [ ] **Step 2: Confirm RED**.
- [ ] **Step 3: Implement HUD layout** using logical metrics.
- [ ] **Step 4: Redesign HUD rendering** with compact top metrics, bounded onboarding panel, conditional boss dominance and improved dash cooldown/pressed ring while preserving virtual-stick semantics.
- [ ] **Step 5: Keep accessibility branches** for high contrast, color vision and flash reduction.
- [ ] **Step 6: Run tests/perf smoke and commit** `feat: redesign responsive combat HUD`.

### Task 8: Motion, interaction feedback and accessibility reduction

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/ui/UiMotion.java`
- Modify migrated screens to use `UiMotion` for reveal/focus/press interpolation.
- Test: `core/src/test/java/com/deadlinezero/game/ui/UiMotionTest.java`

**Interfaces:**
- `UiMotion.progress(float elapsed,float duration,boolean reduceMotion)` and deterministic ease-out/ease-in-out helpers; reduced-motion returns stable end state without flash/pulse loops.

- [ ] **Step 1: Write failing timing/reduced-motion tests** for 70-100ms press, 100-140ms focus and 160-220ms reveal ranges.
- [ ] **Step 2: Confirm RED**.
- [ ] **Step 3: Implement allocation-free interpolation**.
- [ ] **Step 4: Apply only short transitions to migrated screens; avoid continuous motion when accessibility minimizes effects.**
- [ ] **Step 5: Run core tests and commit** `feat: add accessible UI motion and feedback`.

### Task 9: Wide-phone Android visual regression coverage

**Files:**
- Modify: `.github/workflows/verify.yml`
- Modify Android runtime visual-capture scripts/tests identified by the existing workflow.
- Test: existing CI plus any JVM helper tests added for capture configuration.

**Interfaces:**
- Existing baseline captures remain; add a wide-phone capture profile approximating 1536x691 / 20:9 and include home, survivor, arsenal, settings, combat, upgrade, boss HUD and result/victory.

- [ ] **Step 1: Add failing/config validation** for required wide-profile capture names where feasible.
- [ ] **Step 2: Add wide emulator/screenshot invocation** without removing existing coverage.
- [ ] **Step 3: Run available local/static workflow validation and core tests.**
- [ ] **Step 4: Commit** `test: add wide-phone UI visual regression coverage`.

### Task 10: Full verification, visual QA, merge and final APK

**Files:**
- Update docs only if implementation changes user-visible controls or milestone status.

**Interfaces:**
- Final PR branch -> green GitHub Actions -> human visual artifact inspection -> merge -> green `main` workflow -> verified APK artifact.

- [ ] **Step 1: Run/trigger full verification**: core, desktop smoke, Android build/lint, Android runtime, performance baseline.
- [ ] **Step 2: Inspect all baseline and wide-phone screenshots manually**; reject green CI if hierarchy, clipping, scaling or interaction affordances are visibly wrong.
- [ ] **Step 3: Fix any visual/runtime regressions using TDD/root-cause debugging and repeat verification.**
- [ ] **Step 4: Open PR with before/after summary and explicit 1536x691 regression closure.**
- [ ] **Step 5: Merge only after all required checks and visual review pass.**
- [ ] **Step 6: Verify post-merge `main` workflow and download final first-playable artifact.**
- [ ] **Step 7: Validate artifact checksum and APK ZIP integrity.**
- [ ] **Step 8: Provide the APK for the physical-device smoke flow. The private first-playable is only fully complete after physical launch -> home -> survivor/loadout -> deploy -> combat -> upgrade -> boss -> result -> save/restart succeeds without a UI blocker.**
