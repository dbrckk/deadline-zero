# Zombie Waves Reference Auto-Combat Phase Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Extract Deadline Zero's existing implicit nearest-enemy auto-fire logic into deterministic, testable auto-targeting and auto-fire components while preserving weapon behavior, damage, progression, boss logic, and the Java/libGDX runtime.

**Architecture:** `GameScreen` currently owns `fireTimer` and directly asks `SpatialHash.nearest(...)` before calling `fire(target)`. This phase moves target selection into `AutoTargetingSystem` and shot cadence into `AutoFireController`; `GameScreen.fire(Enemy)` remains the projectile/weapon-pattern authority so this refactor does not duplicate weapon behavior. Targeting uses the existing `SpatialHash` and a reusable candidate array, with bounded refresh cadence and target stickiness.

**Tech Stack:** Java 21, libGDX, JUnit 5, Gradle 8.11.1, Android instrumentation/CI already present in the repository.

**Spec:** `docs/superpowers/specs/2026-09-16-zombie-waves-reference-architecture-design.md`

## Global Constraints

- Production stack remains Java + libGDX.
- Do not import Zombie Waves proprietary source, assets, audio, textures, UI artwork, maps, names, or data tables.
- Preserve `Player`, `WeaponRuntime`, `WaveDirector`, `SpatialHash`, existing projectile paths, boss logic, contracts, saves, settlement, accessibility, and performance infrastructure.
- No per-frame full-horde scan for target acquisition when `SpatialHash` local queries can be used.
- Target acquisition refreshes at a bounded cadence and reuses memory.
- `GameScreen.fire(Enemy)` remains the shot-emission path in this phase.
- Existing loaded-40-enemy and 160-enemy/180-projectile Android performance gates must remain green.
- Development stays on `zombie-waves-reference-rebuild`, never directly on `main`.

---

## File Structure

- Create `core/src/main/java/com/deadlinezero/game/combat/AutoTargetingSystem.java` — target lifetime, bounded refresh cadence, candidate ranking, target stickiness.
- Create `core/src/test/java/com/deadlinezero/game/combat/AutoTargetingSystemTest.java` — deterministic target-priority and invalidation tests.
- Create `core/src/main/java/com/deadlinezero/game/combat/AutoFireController.java` — fire cooldown ownership and no-target/dead-target behavior.
- Create `core/src/test/java/com/deadlinezero/game/combat/AutoFireControllerTest.java` — deterministic cadence tests.
- Modify `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java` — replace inline nearest-target/fire-timer orchestration with the two new components; leave `fire(Enemy)` intact.
- Modify or add a focused test under `core/src/test/java/com/deadlinezero/game/screen/` only if a pure integration seam is needed after extraction; avoid adding test-only production APIs to `GameScreen`.

---

### Task 1: Auto-targeting contract

**Files:**
- Create: `core/src/test/java/com/deadlinezero/game/combat/AutoTargetingSystemTest.java`
- Create: `core/src/main/java/com/deadlinezero/game/combat/AutoTargetingSystem.java`

**Interfaces:**
- Consumes: `SpatialHash.query(float x, float y, float radius, Array<Enemy> out)`, `Enemy.alive`, `Enemy.type`, `Enemy.position`.
- Produces:
  - `AutoTargetingSystem(float refreshInterval, float acquisitionRadius)`
  - `Enemy update(float dt, float playerX, float playerY, SpatialHash spatial)`
  - `Enemy currentTarget()`
  - package-private/static `float score(Enemy enemy, float playerX, float playerY, Enemy current)` for deterministic unit tests.

- [ ] **Step 1: Write the failing target-selection tests**

Create tests that build a `SpatialHash(2.2f)`, add deterministic enemies with the existing constructor `new Enemy(Enemy.Type, x, y, hp, speed, radius, contactDamage, xpValue)`, rebuild the hash, then assert:

```java
@Test
void nearestBasicThreatWinsWhenRolesAreEqual() {
    SpatialHash spatial = new SpatialHash(2.2f);
    Enemy near = new Enemy(Enemy.Type.SHAMBLER, 2f, 0f, 100f, .1f, .5f, 1f, 1);
    Enemy far = new Enemy(Enemy.Type.SHAMBLER, 7f, 0f, 100f, .1f, .5f, 1f, 1);
    Array<Enemy> enemies = new Array<>();
    enemies.add(near); enemies.add(far); spatial.rebuild(enemies);

    AutoTargetingSystem system = new AutoTargetingSystem(.10f, 18f);
    assertSame(near, system.update(.11f, 0f, 0f, spatial));
}

@Test
void bossPriorityCanBeatModeratelyCloserBasicInfected() {
    SpatialHash spatial = new SpatialHash(2.2f);
    Enemy basic = new Enemy(Enemy.Type.SHAMBLER, 3f, 0f, 100f, .1f, .5f, 1f, 1);
    Enemy boss = new Enemy(Enemy.Type.BOSS, 5f, 0f, 1000f, .1f, .8f, 1f, 1);
    Array<Enemy> enemies = new Array<>();
    enemies.add(basic); enemies.add(boss); spatial.rebuild(enemies);

    AutoTargetingSystem system = new AutoTargetingSystem(.10f, 18f);
    assertSame(boss, system.update(.11f, 0f, 0f, spatial));
}

@Test
void currentTargetIsStickyUntilRefreshOrInvalidation() {
    SpatialHash spatial = new SpatialHash(2.2f);
    Enemy first = new Enemy(Enemy.Type.SHAMBLER, 4f, 0f, 100f, .1f, .5f, 1f, 1);
    Array<Enemy> enemies = new Array<>(); enemies.add(first); spatial.rebuild(enemies);
    AutoTargetingSystem system = new AutoTargetingSystem(.20f, 18f);
    assertSame(first, system.update(.21f, 0f, 0f, spatial));

    Enemy slightlyBetter = new Enemy(Enemy.Type.SHAMBLER, 3.5f, 0f, 100f, .1f, .5f, 1f, 1);
    enemies.add(slightlyBetter); spatial.rebuild(enemies);
    assertSame(first, system.update(.05f, 0f, 0f, spatial));
}

@Test
void deadCurrentTargetIsDroppedImmediately() {
    SpatialHash spatial = new SpatialHash(2.2f);
    Enemy first = new Enemy(Enemy.Type.SHAMBLER, 2f, 0f, 100f, .1f, .5f, 1f, 1);
    Enemy second = new Enemy(Enemy.Type.RUNNER, 4f, 0f, 100f, .1f, .5f, 1f, 1);
    Array<Enemy> enemies = new Array<>(); enemies.add(first); enemies.add(second); spatial.rebuild(enemies);
    AutoTargetingSystem system = new AutoTargetingSystem(.20f, 18f);
    assertSame(first, system.update(.21f, 0f, 0f, spatial));
    first.alive = false; spatial.rebuild(enemies);
    assertSame(second, system.update(.01f, 0f, 0f, spatial));
}
```

- [ ] **Step 2: Run tests and verify RED**

Run:

```bash
gradle :core:test --tests com.deadlinezero.game.combat.AutoTargetingSystemTest
```

Expected: compile failure because `AutoTargetingSystem` does not exist.

- [ ] **Step 3: Implement the minimal targeting system**

Use one reusable `Array<Enemy>` field for query results. The refresh timer is decremented by `dt`. If the current target is dead or outside acquisition radius, force refresh immediately. On refresh, call `spatial.query(playerX, playerY, acquisitionRadius, candidates)` and choose the smallest score.

Use this scoring contract:

```java
static float score(Enemy enemy, float px, float py, Enemy current) {
    float dx = enemy.position.x - px;
    float dy = enemy.position.y - py;
    float value = dx * dx + dy * dy;
    value -= switch (enemy.type) {
        case BOSS -> 18f;
        case ELITE -> 7f;
        case RANGED -> 3f;
        default -> 0f;
    };
    if (enemy == current) value -= 2.5f;
    return value;
}
```

The constructor validates `refreshInterval > 0f` and `acquisitionRadius > 0f`. `currentTarget()` returns the cached identity without allocating.

- [ ] **Step 4: Run focused tests and verify GREEN**

```bash
gradle :core:test --tests com.deadlinezero.game.combat.AutoTargetingSystemTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/combat/AutoTargetingSystem.java core/src/test/java/com/deadlinezero/game/combat/AutoTargetingSystemTest.java
git commit -m "feat: add bounded auto targeting"
```

---

### Task 2: Auto-fire cadence contract

**Files:**
- Create: `core/src/test/java/com/deadlinezero/game/combat/AutoFireControllerTest.java`
- Create: `core/src/main/java/com/deadlinezero/game/combat/AutoFireController.java`

**Interfaces:**
- Consumes: `WeaponRuntime.fireInterval`, `Enemy.alive`.
- Produces:
  - `@FunctionalInterface AutoFireController.ShotEmitter { void fire(Enemy target); }`
  - `void update(float dt, WeaponRuntime weapon, Enemy target, ShotEmitter emitter)`
  - `float cooldown()` for deterministic tests/telemetry.
  - `void reset()` setting cooldown to zero.

- [ ] **Step 1: Write failing cadence tests**

Use an existing weapon definition from `WeaponCatalog` rather than constructing duplicate stats. Tests must prove immediate first shot, no firing without a target, no firing at a dead target, and cadence preservation:

```java
@Test
void firesImmediatelyThenRespectsWeaponInterval() {
    WeaponRuntime weapon = new WeaponRuntime(WeaponCatalog.defaultWeapon());
    weapon.fireInterval = .20f;
    Enemy target = new Enemy(Enemy.Type.SHAMBLER, 2f, 0f, 100f, .1f, .5f, 1f, 1);
    AtomicInteger shots = new AtomicInteger();
    AutoFireController fire = new AutoFireController();

    fire.update(.016f, weapon, target, ignored -> shots.incrementAndGet());
    assertEquals(1, shots.get());
    fire.update(.10f, weapon, target, ignored -> shots.incrementAndGet());
    assertEquals(1, shots.get());
    fire.update(.11f, weapon, target, ignored -> shots.incrementAndGet());
    assertEquals(2, shots.get());
}

@Test
void doesNotFireWithoutLiveTarget() { /* null and alive=false both keep shot count at zero */ }
```

If `WeaponCatalog.defaultWeapon()` is not the exact existing accessor, use the existing starter/default definition from `WeaponCatalog` discovered at implementation time; do not add a production accessor solely for tests.

- [ ] **Step 2: Run tests and verify RED**

```bash
gradle :core:test --tests com.deadlinezero.game.combat.AutoFireControllerTest
```

Expected: compile failure because `AutoFireController` does not exist.

- [ ] **Step 3: Implement minimal controller**

Behavior:

```java
public void update(float dt, WeaponRuntime weapon, Enemy target, ShotEmitter emitter) {
    if (dt < 0f) throw new IllegalArgumentException("dt");
    cooldown = Math.max(0f, cooldown - dt);
    if (weapon == null || emitter == null || target == null || !target.alive) return;
    if (cooldown > 0f) return;
    emitter.fire(target);
    cooldown = Math.max(.01f, weapon.fireInterval);
}
```

This intentionally emits at most one shot per fixed simulation tick, matching the current `GameScreen` behavior.

- [ ] **Step 4: Run focused tests and verify GREEN**

```bash
gradle :core:test --tests com.deadlinezero.game.combat.AutoFireControllerTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/combat/AutoFireController.java core/src/test/java/com/deadlinezero/game/combat/AutoFireControllerTest.java
git commit -m "feat: extract automatic fire cadence"
```

---

### Task 3: Route `GameScreen` through auto-target + auto-fire

**Files:**
- Modify: `core/src/main/java/com/deadlinezero/game/screen/GameScreen.java`
- Test: existing core suite plus focused auto-combat tests from Tasks 1–2.

**Interfaces:**
- Consumes: `AutoTargetingSystem.update(...)`, `AutoFireController.update(...)`, existing `GameScreen.fire(Enemy)`.
- Produces: production run behavior where movement remains the only required continuous combat input and firing still uses the exact existing projectile/weapon path.

- [ ] **Step 1: Add a structural regression test before production edit**

Create `core/src/test/java/com/deadlinezero/game/combat/AutoCombatCompositionTest.java` that composes `SpatialHash`, `AutoTargetingSystem`, `AutoFireController`, a `WeaponRuntime`, and two enemies. Assert that repeated fixed-step updates with no aim/fire input select a live enemy and invoke the emitter multiple times over one second.

```java
@Test
void movementOnlyCombatCompositionProducesShots() {
    SpatialHash spatial = new SpatialHash(2.2f);
    Enemy enemy = new Enemy(Enemy.Type.SHAMBLER, 4f, 0f, 100f, .1f, .5f, 1f, 1);
    Array<Enemy> enemies = new Array<>(); enemies.add(enemy); spatial.rebuild(enemies);
    AutoTargetingSystem targeting = new AutoTargetingSystem(.10f, 18f);
    AutoFireController firing = new AutoFireController();
    WeaponRuntime weapon = new WeaponRuntime(/* existing starter definition */);
    weapon.fireInterval = .20f;
    AtomicInteger shots = new AtomicInteger();

    for (int i = 0; i < 60; i++) {
        Enemy target = targeting.update(1f / 60f, 0f, 0f, spatial);
        firing.update(1f / 60f, weapon, target, ignored -> shots.incrementAndGet());
    }
    assertTrue(shots.get() >= 4);
}
```

This test should be GREEN once Tasks 1–2 are complete; its purpose is to freeze the composition contract before touching `GameScreen`.

- [ ] **Step 2: Modify `GameScreen` fields**

Add imports for the two combat components. Replace:

```java
private float accumulator, fireTimer, contactTimer, cameraShake, visualTime, performanceEvaluationTimer;
```

with a version that removes `fireTimer`, and add:

```java
private final AutoTargetingSystem autoTargeting = new AutoTargetingSystem(.10f, 18f);
private final AutoFireController autoFire = new AutoFireController();
```

Do not remove the existing `aim` or `shotVelocity` vectors if `fire(Enemy)` uses them.

- [ ] **Step 3: Replace inline auto-fire orchestration in `update(float dt)`**

Delete:

```java
fireTimer -= dt;
...
Enemy target = spatial.nearest(player.position.x, player.position.y);
if (target != null && fireTimer <= 0f) {
    fire(target);
    fireTimer = player.weapon.fireInterval;
}
```

Replace with:

```java
Enemy target = autoTargeting.update(dt, player.position.x, player.position.y, spatial);
autoFire.update(dt, player.weapon, target, this::fire);
```

Keep the surrounding simulation order unchanged in this phase: spawning first, target/fire using the previous tick's spatial index, enemy movement next, then `spatial.rebuild(enemies)` for collision/ability queries. This preserves existing timing semantics while extracting responsibilities.

- [ ] **Step 4: Run core regression suite**

```bash
gradle :core:compileJava :core:test :desktop:compileJava
```

Expected: PASS.

- [ ] **Step 5: Run desktop smoke**

```bash
xvfb-run -a gradle :desktop:smokeRun
```

Expected: PASS.

- [ ] **Step 6: Commit**

```bash
git add core/src/main/java/com/deadlinezero/game/screen/GameScreen.java core/src/test/java/com/deadlinezero/game/combat/AutoCombatCompositionTest.java
git commit -m "refactor: route combat through auto targeting and fire"
```

---

### Task 4: Android/runtime/performance verification on exact head

**Files:**
- No production file changes unless verification exposes a defect.
- Existing workflow: `.github/workflows/verify.yml`.

**Interfaces:**
- Consumes: exact branch head after Task 3.
- Produces: verified evidence that the extraction did not regress Android build/runtime or horde performance.

- [ ] **Step 1: Push exact head and wait for Verify workflow**

Required jobs:

- `core`
- `android`
- `android-runtime`

- [ ] **Step 2: Verify Android runtime journey**

Require the existing first-playable journey instrumentation to pass without introducing any manual aim/fire dependency.

- [ ] **Step 3: Verify performance artifacts**

Confirm the existing scenarios still execute:

- `loaded-40-enemy-ring`
- `horde-160-projectile-180`

No blocker performance regression may be introduced relative to the repository's existing comparator policy.

- [ ] **Step 4: Inspect gameplay visual artifact**

Download the current `android-gameplay-visual-qa-*` artifact and inspect at least:

- a normal gameplay frame;
- authored crowd frame;
- ranged attack frame;
- champion crowd frame.

This task does not attempt the later full visual rebuild; it checks that auto-combat extraction does not visually break firing/targets.

- [ ] **Step 5: Record exact verified head**

Do not claim Phase 1 complete until core, Android build, runtime, and performance evidence all correspond to the same commit SHA.

---

## Self-Review Notes

- Spec coverage for this plan is intentionally limited to Phase 1 (`AutoTargetingSystem` + `AutoFireController` + production routing). XP pickups, pressure profiles, trait graph, and visual/meta phases remain separate plans because they are independently reviewable subsystems.
- No new engine/dependency is introduced.
- Existing `GameScreen.fire(Enemy)` remains authoritative, so weapon signatures/projectile semantics are not duplicated.
- Targeting uses `SpatialHash.query(...)` and a reusable candidate buffer rather than a full enemy-list scan.
- The plan contains no `TODO`/`TBD` implementation placeholders; the only implementation-time lookup allowed is selecting the already-existing starter weapon definition accessor from `WeaponCatalog`, explicitly without adding a test-only production API.
