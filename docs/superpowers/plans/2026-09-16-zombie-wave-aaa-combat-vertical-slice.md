# Zombie Wave AAA Combat Vertical Slice Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace the current prototype-like combat presentation with a production-ready dark sci-fi biohazard zombie-wave vertical slice while preserving simulation, progression, save/economy rules, accessibility, and performance gates.

**Architecture:** Keep `GameScreen` as orchestration only. Extend the existing visual boundaries with a pure HUD layout model, a deterministic `CombatWorldRenderer`, a `CombatReadabilityPass`, and budgeted feedback profiles consumed by existing pooled FX/renderers. Android visual probes become the visual acceptance gate for the combat slice before any meta-screen rebuild.

**Tech Stack:** Java 17, libGDX, JUnit, Android instrumentation, GitHub Actions, existing `ShapeRenderer`/`SpriteBatch`, existing pooled FX and thermal/graphics-quality systems.

**Spec:** `docs/superpowers/specs/2026-09-16-zombie-wave-aaa-rebuild-design.md`

## Global Constraints

- Preserve combat simulation, spawn rules, progression, save, economy, billing, settlement, and the 24/24 production actor roster.
- Keep the stylized 2D/pixel-art identity; no 3D or heavy external UI/animation runtime.
- 1536×691 landscape Android is a required visual target alongside 1280×720, 1920×1080, and 16:10.
- New danger/elite/boss cues may not rely on color alone.
- Preserve reduced motion, minimized flashes, shake controls, high-contrast telegraphs, color-vision-safe cues, UI scale, haptics, and touch target sizing.
- No per-frame heap churn in horde hot paths; transient FX remain pooled/capped.
- Existing 40-enemy and 160-enemy/180-projectile performance gates may not regress beyond the existing comparator.
- No permanent tutorial panel may cover the combat center.
- Hazard stripes are accents, never the dominant arena texture.

---

### Task 1: HUD v2 geometry and toast-safe layout

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatHudLayout.java`
- Modify: `core/src/test/java/com/deadlinezero/game/visual/CombatHudLayoutTest.java`

**Interfaces:**
- Consumes: `UiLayout.compute(int,int)` and physical-to-logical scale mapping already exposed by `CombatHudLayout.Layout`.
- Produces: `CombatHudLayout.Layout` regions `survival`, `levelBadge`, `xpRail`, `hordeStatus`, `boss`, `toast`, `dashX`, `dashY`, `dashRadius`, plus `toLogicalX/Y`.

- [ ] **Step 1: Write failing layout tests**

Add tests that compute layouts for 1536×691, 1920×1080, 1280×720 and 1280×800. Assert:

```java
assertTrue(layout.survival().width >= 240f);
assertTrue(layout.levelBadge().width >= 72f);
assertTrue(layout.xpRail().height <= 14f);
assertFalse(layout.survival().overlaps(layout.hordeStatus()));
assertFalse(layout.toast().overlaps(playerSafeZone(layout)));
assertTrue(layout.toast().width <= layout.logicalWidth() * .42f);
assertTrue(layout.dashRadius() >= 32f);
```

Define `playerSafeZone(layout)` in the test as the center 34% width × 42% height rectangle so onboarding cannot occupy the combat focal area.

- [ ] **Step 2: Run the test and observe RED**

Run:

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatHudLayoutTest
```

Expected: compilation/test failure because the v2 region accessors do not exist yet.

- [ ] **Step 3: Implement the minimal v2 geometry**

Replace the dual giant HP/XP cards with:

```java
Rectangle survival = new Rectangle(left, top - 42f * s, Math.min(330f * s, m.contentWidth() * .25f), 42f * s);
Rectangle levelBadge = new Rectangle(survival.x + survival.width + 10f * s, survival.y, 78f * s, survival.height);
Rectangle xpRail = new Rectangle(survival.x, survival.y - 13f * s, survival.width + levelBadge.width + 10f * s, 8f * s);
Rectangle hordeStatus = new Rectangle(m.safeRight() - 320f * s, survival.y, 320f * s, survival.height);
Rectangle toast = placeToastOutsideCenter(m, s);
```

Keep the boss bar centered below the top rail only while a boss is active. Keep the dash hit target at least 64 logical units diameter.

- [ ] **Step 4: Run the test and observe GREEN**

Run the same command. Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/CombatHudLayout.java core/src/test/java/com/deadlinezero/game/visual/CombatHudLayoutTest.java
git commit -m "feat: redesign combat HUD geometry"
```

---

### Task 2: HUD v2 renderer and contextual toast

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java`
- Modify: `assets/i18n/messages.properties`
- Create: `core/src/test/java/com/deadlinezero/game/visual/CombatHudPresentationTest.java`

**Interfaces:**
- Consumes: Task 1 `CombatHudLayout.Layout` v2 regions.
- Produces: compact survival cluster, integrated XP rail, right-side horde status, boss replacement state, transient onboarding toast, subdued mobile controls.

- [ ] **Step 1: Write failing presentation contract tests**

Create pure/static helper methods in the test contract expected from `CombatHudRenderer`:

```java
assertEquals(CombatHudRenderer.HintMode.TOAST, CombatHudRenderer.hintModeFor(false));
assertEquals(CombatHudRenderer.HintMode.NONE, CombatHudRenderer.hintModeFor(true));
assertTrue(CombatHudRenderer.controlIdleAlpha() <= .18f);
assertTrue(CombatHudRenderer.controlActiveAlpha() >= .30f);
assertTrue(CombatHudRenderer.controlActiveAlpha() > CombatHudRenderer.controlIdleAlpha());
```

- [ ] **Step 2: Run RED**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatHudPresentationTest
```

Expected: failure because the new contract methods/enums do not exist.

- [ ] **Step 3: Implement renderer changes**

Implement `HintMode`, `hintModeFor(boolean onboardingComplete)`, and alpha helpers. Render:

- HP as a compact dark glass plate with a left health glyph and one progress strip;
- level inside `levelBadge`, not another long card;
- XP as the thin `xpRail`;
- kills + encounter/contract as right-side icon/text chips;
- boss ETA as compact center-top text; active boss replaces ETA with one boss rail;
- onboarding only inside `toast`, with no permanent dark center panel;
- joystick/dash idle alpha `<= .18f`, active alpha `>= .30f`;
- Android labels without desktop key-hint dominance.

Add only localization keys needed by this renderer, using the existing bitmap-safe sanitizer path.

- [ ] **Step 4: Run targeted + existing HUD tests**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatHudPresentationTest --tests com.deadlinezero.game.visual.CombatHudLayoutTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java assets/i18n/messages.properties core/src/test/java/com/deadlinezero/game/visual/CombatHudPresentationTest.java
git commit -m "feat: ship compact combat HUD v2"
```

---

### Task 3: Deterministic quarantine arena renderer

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/visual/CombatWorldStyle.java`
- Create: `core/src/main/java/com/deadlinezero/game/visual/CombatWorldRenderer.java`
- Create: `core/src/test/java/com/deadlinezero/game/visual/CombatWorldStyleTest.java`
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java`

**Interfaces:**
- Consumes: camera projection, run stage/biome context, visual time, graphics/thermal quality.
- Produces: deterministic base material, large-scale breakup, sparse hazard accents, lanes/seams/grates, story props, contamination decals, world shadows and atmosphere hooks.

- [ ] **Step 1: Write failing deterministic style tests**

Create a pure `CombatWorldStyle.Profile` generated from biome/stage seed. Assert:

```java
var a = CombatWorldStyle.forStage(1, 42L);
var b = CombatWorldStyle.forStage(1, 42L);
assertEquals(a, b);
assertTrue(a.hazardCoverage() <= .12f);
assertTrue(a.largeFeatureCount() >= 4 && a.largeFeatureCount() <= 12);
assertTrue(a.propCount() >= 6 && a.propCount() <= 24);
assertTrue(a.decalCount() >= 8 && a.decalCount() <= 36);
```

- [ ] **Step 2: Run RED**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatWorldStyleTest
```

Expected: compile failure because the profile does not exist.

- [ ] **Step 3: Implement deterministic world style**

`CombatWorldStyle` owns only immutable values/seeded placement parameters. `CombatWorldRenderer` draws them with existing `ShapeRenderer`/`SpriteBatch` without allocating each frame. Quarantine stage 1 must use charcoal concrete/asphalt, broken lane lines, drains/grates, barricade silhouettes, blood/infected residue and sparse amber hazard paint. Remove the current large repeated hazard-stripe field from `GameScreen` world drawing.

- [ ] **Step 4: Wire into GameScreen**

Add one final field:

```java
private final CombatWorldRenderer worldRenderer = new CombatWorldRenderer();
```

Call it before `CombatSpritePass`, with no simulation state mutation. `GameScreen` must not regain arena drawing details.

- [ ] **Step 5: Run tests**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatWorldStyleTest --tests com.deadlinezero.game.visual.EnvironmentBiomeRulesTest
```

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/CombatWorldStyle.java core/src/main/java/com/deadlinezero/game/visual/CombatWorldRenderer.java core/src/main/java/com/deadlinezero/game/screen/GameScreen.java core/src/test/java/com/deadlinezero/game/visual/CombatWorldStyleTest.java
git commit -m "feat: add authored quarantine combat arena"
```

---

### Task 4: Horde readability pass

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/visual/CombatReadabilityPass.java`
- Create: `core/src/main/java/com/deadlinezero/game/visual/HordeRolePresentation.java`
- Create: `core/src/test/java/com/deadlinezero/game/visual/HordeRolePresentationTest.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatSpritePass.java`
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java`

**Interfaces:**
- Consumes: `Enemy.Type`, champion variant, boss identity, accessibility high-contrast flag, graphics quality.
- Produces: semantic `RoleStyle` containing shadow scale, outline/rim strength, telegraph geometry, motion emphasis, and projectile-danger family.

- [ ] **Step 1: Write failing role-mapping tests**

Assert semantic differentiation without depending on color:

```java
assertEquals(HordeRolePresentation.Motion.FAST, style(Enemy.Type.RUNNER).motion());
assertTrue(style(Enemy.Type.BRUTE).shadowScale() > style(Enemy.Type.RUNNER).shadowScale());
assertEquals(HordeRolePresentation.Telegraph.PREFIRE, style(Enemy.Type.RANGED).telegraph());
assertEquals(HordeRolePresentation.Telegraph.PHASED, style(Enemy.Type.BOSS).telegraph());
assertNotEquals(style(Enemy.Type.RUNNER).silhouetteClass(), style(Enemy.Type.BRUTE).silhouetteClass());
```

- [ ] **Step 2: Run RED**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.HordeRolePresentationTest
```

- [ ] **Step 3: Implement mapping and render pass**

`HordeRolePresentation` is pure data. `CombatReadabilityPass` renders ground shadows, player grounding, role-specific non-color geometry, ranged pre-fire marks, elite/boss emphasis and low-quality crowd simplification. Existing champion two-character badges remain intact.

- [ ] **Step 4: Integrate around CombatSpritePass**

Render ground/readability-underlay before sprites, then semantic overlays/telegraphs after sprites. Keep combat movement/AI untouched.

- [ ] **Step 5: Run tests**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.HordeRolePresentationTest --tests com.deadlinezero.game.visual.ChampionVariantPresentationTest --tests com.deadlinezero.game.visual.HostileProjectilePresentationTest
```

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/CombatReadabilityPass.java core/src/main/java/com/deadlinezero/game/visual/HordeRolePresentation.java core/src/main/java/com/deadlinezero/game/visual/CombatSpritePass.java core/src/main/java/com/deadlinezero/game/screen/GameScreen.java core/src/test/java/com/deadlinezero/game/visual/HordeRolePresentationTest.java
git commit -m "feat: improve zombie horde role readability"
```

---

### Task 5: Budgeted combat feedback vocabulary

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/visual/CombatFeedbackProfile.java`
- Create: `core/src/test/java/com/deadlinezero/game/visual/CombatFeedbackProfileTest.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatPolishController.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/WorldFxRenderer.java`
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java`

**Interfaces:**
- Consumes: quality level, thermal level/effective FX quality, reduced-motion/minimized-flash settings, event type.
- Produces: immutable/cached feedback profiles for `FIRE`, `HIT`, `CRIT`, `KILL`, `DASH`, `PICKUP`, `BOSS_WINDUP`, `BOSS_RELEASE`, `LOW_HP`.

- [ ] **Step 1: Write failing budget tests**

```java
assertTrue(profile(HIT, LOW).particleBudget() <= profile(HIT, HIGH).particleBudget());
assertEquals(0f, reduced(DASH).afterimageStrength(), .0001f);
assertTrue(minimizedFlash(CRIT).flashAlpha() <= .08f);
assertTrue(profile(KILL, HIGH).decalLifetime() <= 8f);
assertTrue(profile(BOSS_RELEASE, HIGH).shakeScale() <= 1f);
```

- [ ] **Step 2: Run RED**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatFeedbackProfileTest
```

- [ ] **Step 3: Implement profiles and connect existing pools**

Do not create a second FX simulation. Route profile values into existing `ImpactFx`, `DamageNumber`, pooled arcs/particles, camera shake, and `WorldFxRenderer`. Add short death residue/decal rendering only through a capped collection with deterministic eviction.

- [ ] **Step 4: Run visual/FX tests**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.CombatFeedbackProfileTest --tests com.deadlinezero.game.visual.AdaptiveFxBudgetTest --tests com.deadlinezero.game.visual.BootstrapVfxArtTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/CombatFeedbackProfile.java core/src/main/java/com/deadlinezero/game/visual/CombatPolishController.java core/src/main/java/com/deadlinezero/game/visual/WorldFxRenderer.java core/src/main/java/com/deadlinezero/game/screen/GameScreen.java core/src/test/java/com/deadlinezero/game/visual/CombatFeedbackProfileTest.java
git commit -m "feat: add budgeted AAA combat feedback"
```

---

### Task 6: Boss and low-HP presentation pass

**Files:**
- Create: `core/src/main/java/com/deadlinezero/game/visual/BossPresenceProfile.java`
- Create: `core/src/test/java/com/deadlinezero/game/visual/BossPresenceProfileTest.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatReadabilityPass.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java`
- Modify: `core/src/main/java/com/deadlinezero/game/visual/CombatPolishController.java`

**Interfaces:**
- Consumes: boss identity/phase, accessibility state, player HP ratio.
- Produces: boss entrance/phase geometry profile and restrained low-HP vignette/pulse settings.

- [ ] **Step 1: Write failing boss/low-HP tests**

```java
assertTrue(BossPresenceProfile.forPhase(BossIdentity.ALPHA, 3).intensity() > BossPresenceProfile.forPhase(BossIdentity.ALPHA, 1).intensity());
assertTrue(BossPresenceProfile.forPhase(BossIdentity.FROST_COLOSSUS, 2).telegraphSegments() >= 3);
assertTrue(BossPresenceProfile.lowHp(.20f, true).flashAlpha() <= .07f);
assertEquals(0f, BossPresenceProfile.lowHp(.20f, false).motionPulse(), .0001f);
```

- [ ] **Step 2: Run RED**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.BossPresenceProfileTest
```

- [ ] **Step 3: Implement presentation only**

Add geometry-first boss wind-up/phase cues, stronger grounding, phase rail markers and accessibility-aware low-HP edge treatment. Do not alter boss timing, damage, or phase rules.

- [ ] **Step 4: Run relevant tests**

```bash
./gradlew core:test --tests com.deadlinezero.game.visual.BossPresenceProfileTest --tests com.deadlinezero.game.visual.BossPhaseTransitionProfileTest --tests com.deadlinezero.game.visual.BossIdentityArtRoutingTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/visual/BossPresenceProfile.java core/src/main/java/com/deadlinezero/game/visual/CombatReadabilityPass.java core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java core/src/main/java/com/deadlinezero/game/visual/CombatPolishController.java core/src/test/java/com/deadlinezero/game/visual/BossPresenceProfileTest.java
git commit -m "feat: polish boss and low health presentation"
```

---

### Task 7: Android combat visual regression suite

**Files:**
- Modify: `android/src/androidTest/java/com/deadlinezero/game/android/AndroidGameplayVisualProbeTest.java`
- Modify: `.github/workflows/responsive-ui-qa.yml`
- Modify: `.github/workflows/verify.yml`

**Interfaces:**
- Consumes: deterministic debug hooks already used by gameplay visual probes.
- Produces: artifacts for early wave, dense horde, ranged pressure, champion crowd, boss phase, low HP, pickup/upgrade state at standard landscape and 1536×691-like wide-phone aspect.

- [ ] **Step 1: Add failing/strict instrumentation expectations**

Add named capture files and assert each exists and has non-zero dimensions:

```java
capture("combat-early-wave.png");
capture("combat-dense-horde.png");
capture("combat-ranged-pressure.png");
capture("combat-champion-crowd.png");
capture("combat-boss-phase.png");
capture("combat-low-hp.png");
capture("combat-pickup-upgrade.png");
```

In wide mode assert `width / (float) height > 2.05f` after the Activity is launched.

- [ ] **Step 2: Run instrumentation workflow and confirm RED if any new capture path/state hook is missing**

Push the test-only head and inspect `Verify`/`Responsive UI QA`. Expected: new capture contract fails until debug-state setup is implemented or wired.

- [ ] **Step 3: Implement only the deterministic visual state hooks needed by the probes**

Reuse existing visual-probe controls; do not change production combat rules. Each capture must wait for one rendered frame after state setup.

- [ ] **Step 4: Run Android workflows and inspect artifacts manually**

Pass criteria from the spec: no HUD covering combat center, no dominant hazard wallpaper, hostile projectiles distinct from friendly fire, horde roles legible, no unsupported glyphs/placeholders, no giant opaque tutorial panel.

- [ ] **Step 5: Commit**

```bash
git add android/src/androidTest/java/com/deadlinezero/game/android/AndroidGameplayVisualProbeTest.java .github/workflows/responsive-ui-qa.yml .github/workflows/verify.yml
git commit -m "test: add zombie-wave combat visual QA"
```

---

### Task 8: Full combat-slice verification and PR gate

**Files:**
- No production code unless a verification failure exposes a bug.
- Update: `docs/PRODUCTION_STATUS.md` only after the exact head passes all automated combat-slice gates; physical device remains explicitly open.

**Interfaces:**
- Consumes: exact branch head after Tasks 1–7.
- Produces: one reviewable PR with core/Android/runtime/performance/visual evidence.

- [ ] **Step 1: Run core and desktop gates**

```bash
./gradlew core:test
./gradlew desktop:run
```

CI equivalent must pass core tests and desktop smoke.

- [ ] **Step 2: Run Android production/runtime gates**

Require `Verify` jobs `core`, `android`, and `android-runtime` to complete successfully on the same head.

- [ ] **Step 3: Require performance evidence**

Inspect the PR performance artifact. Existing comparator must report no blocker regression for the standard benchmark; stress workload sanity must remain green.

- [ ] **Step 4: Inspect visual artifacts manually**

Review all Task 7 captures at native size. Reject the head if any spec manual-failure criterion is visible.

- [ ] **Step 5: Open/update PR**

Use title:

```text
AAA zombie-wave combat vertical slice
```

PR body must list exact head SHA, Verify run number, Responsive UI QA run number, performance conclusion, and manual visual-review result.

- [ ] **Step 6: Do not merge until every gate is on one exact head**

After merge, rerun `main`, validate APK checksum/ZIP integrity, and leave physical Android gameplay validation open until the user tests the generated APK on representative hardware.
