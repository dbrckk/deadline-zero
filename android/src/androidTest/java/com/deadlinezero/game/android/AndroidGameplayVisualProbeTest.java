package com.deadlinezero.game.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.visual.CombatVisualEvents;
import com.deadlinezero.game.world.BiomeEnemyRoster;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Captures deterministic phone-scale gameplay frames for human visual QA in CI artifacts. */
@RunWith(AndroidJUnit4.class)
public final class AndroidGameplayVisualProbeTest {
    @Test
    public void capturesWraithGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                // Exercise WRAITH through the real selected-survivor runtime path. Direct assignment is
                // instrumentation-only so the probe does not depend on account unlock progression.
                game.profile.selectedSurvivor = SurvivorCatalog.Survivor.WRAITH;
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for visual probe", game.getScreen() instanceof GameScreen);
                assertTrue("visual probe must run the production WRAITH selection",
                    game.profile.selectedSurvivor == SurvivorCatalog.Survivor.WRAITH);
            });

            Thread.sleep(2200L);
            capture("wraith-gameplay.png");

            // Deterministic authored-enemy composition. Reflection stays instrumentation-only so
            // production GameScreen does not gain QA API surface.
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("wraith-crowd.png");

            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("wraith-attack.png");
        }
    }

    @Test
    public void capturesRevenantGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for REVENANT visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(4);
                injectRevenantBoss((GameScreen) game.getScreen());
            });

            Thread.sleep(1800L);
            capture("revenant-gameplay.png");

            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("revenant-crowd.png");

            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("revenant-attack.png");
        }
    }

    @Test
    public void capturesWardenGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for WARDEN visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(7);
                injectWardenBoss((GameScreen) game.getScreen());
            });
            Thread.sleep(1800L);
            capture("warden-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("warden-crowd.png");
            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("warden-attack.png");
        }
    }

    @Test
    public void capturesHarvesterGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for HARVESTER visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(6);
                injectHarvesterBoss((GameScreen) game.getScreen());
            });
            Thread.sleep(1800L);
            capture("harvester-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("harvester-crowd.png");
            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("harvester-attack.png");
        }
    }

    @Test
    public void capturesCinderGunnerGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for CINDER GUNNER visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(10);
                injectCinderGunner((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("cinder-gunner-gameplay.png");

            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("cinder-gunner-crowd.png");

            runOnGameThread(activity, () -> forceCinderGunnerAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("cinder-gunner-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectCinderGunner(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy gunner = new Enemy(Enemy.Type.RANGED, 0f, 3.2f, 500_000f, .02f, .50f, 0f, 1);
            assertTrue("stage 10 RANGED must resolve to CINDER GUNNER",
                gunner.biomeIdentity() == BiomeEnemyRoster.Identity.CINDER_GUNNER);
            enemies.add(gunner);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject CINDER GUNNER for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceCinderGunnerAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy gunner = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.CINDER_GUNNER) {
                    gunner = enemy;
                    break;
                }
            }
            assertNotNull("CINDER GUNNER missing before attack capture", gunner);
            Field stateField = gunner.attack.getClass().getDeclaredField("state");
            Field timerField = gunner.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(gunner.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(gunner.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force CINDER GUNNER attack animation for visual QA", exception);
        }
    }

    @Test
    public void capturesStaticSeerGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for STATIC SEER visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(20);
                injectStaticSeer((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("static-seer-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("static-seer-crowd.png");
            runOnGameThread(activity, () -> forceStaticSeerAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("static-seer-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectStaticSeer(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy seer = new Enemy(Enemy.Type.RANGED, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
            assertTrue("stage 20 RANGED must resolve to STATIC SEER",
                seer.biomeIdentity() == BiomeEnemyRoster.Identity.STATIC_SEER);
            enemies.add(seer);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject STATIC SEER for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceStaticSeerAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy seer = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.STATIC_SEER) {
                    seer = enemy;
                    break;
                }
            }
            assertNotNull("STATIC SEER missing before attack capture", seer);
            Field stateField = seer.attack.getClass().getDeclaredField("state");
            Field timerField = seer.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(seer.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(seer.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force STATIC SEER attack animation for visual QA", exception);
        }
    }

    @Test
    public void capturesPhaseStalkerGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for PHASE STALKER visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(20);
                injectPhaseStalker((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("phase-stalker-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("phase-stalker-crowd.png");
            runOnGameThread(activity, () -> forcePhaseStalkerAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("phase-stalker-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectPhaseStalker(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy stalker = new Enemy(Enemy.Type.PHANTOM, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
            assertTrue("stage 20 PHANTOM must resolve to PHASE STALKER",
                stalker.biomeIdentity() == BiomeEnemyRoster.Identity.PHASE_STALKER);
            enemies.add(stalker);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject PHASE STALKER for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forcePhaseStalkerAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy stalker = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.PHASE_STALKER) {
                    stalker = enemy;
                    break;
                }
            }
            assertNotNull("PHASE STALKER missing before attack capture", stalker);
            Field stateField = stalker.attack.getClass().getDeclaredField("state");
            Field timerField = stalker.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(stalker.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(stalker.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force PHASE STALKER attack animation for visual QA", exception);
        }
    }

    @Test
    public void capturesSlagGuardGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for SLAG GUARD visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(10);
                injectSlagGuard((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("slag-guard-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("slag-guard-crowd.png");
            runOnGameThread(activity, () -> forceSlagGuardAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("slag-guard-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectSlagGuard(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy guard = new Enemy(Enemy.Type.SHIELDED, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
            assertTrue("stage 10 SHIELDED must resolve to SLAG GUARD",
                guard.biomeIdentity() == BiomeEnemyRoster.Identity.SLAG_GUARD);
            enemies.add(guard);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject SLAG GUARD for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceSlagGuardAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy guard = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.SLAG_GUARD) {
                    guard = enemy;
                    break;
                }
            }
            assertNotNull("SLAG GUARD missing before attack capture", guard);
            Field stateField = guard.attack.getClass().getDeclaredField("state");
            Field timerField = guard.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(guard.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(guard.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force SLAG GUARD attack animation for visual QA", exception);
        }
    }

    @Test
    public void capturesForgeHoundGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for FORGE HOUND visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(10);
                injectForgeHound((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("forge-hound-gameplay.png");

            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("forge-hound-crowd.png");

            runOnGameThread(activity, () -> forceForgeHoundAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("forge-hound-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectForgeHound(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy hound = new Enemy(Enemy.Type.RUNNER, 0f, 3.2f, 500_000f, .02f, .50f, 0f, 1);
            assertTrue("stage 10 RUNNER must resolve to FORGE HOUND",
                hound.biomeIdentity() == BiomeEnemyRoster.Identity.FORGE_HOUND);
            enemies.add(hound);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject FORGE HOUND for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceForgeHoundAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy hound = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.FORGE_HOUND) {
                    hound = enemy;
                    break;
                }
            }
            assertNotNull("FORGE HOUND missing before attack capture", hound);
            Field stateField = hound.attack.getClass().getDeclaredField("state");
            Field timerField = hound.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(hound.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(hound.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force FORGE HOUND attack animation for visual QA", exception);
        }
    }

    @Test
    public void capturesNullArchonGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for NULL ARCHON visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(20);
                injectNullArchonBoss((GameScreen) game.getScreen());
            });
            Thread.sleep(1800L);
            capture("null-archon-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("null-archon-crowd.png");
            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("null-archon-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectNullArchonBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject NULL ARCHON boss for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectHarvesterBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject HARVESTER boss for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectWardenBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject WARDEN boss for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectRevenantBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject REVENANT boss for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectAuthoredEnemyCrowd(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            int before = enemies.size;

            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, -5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, 4.2f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, -4.2f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.RUNNER, 3.7f, 3.0f, 50_000f, .22f, .46f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.RUNNER, -3.7f, -3.0f, 50_000f, .22f, .46f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BRUTE, -3.7f, 3.0f, 50_000f, .06f, .62f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BRUTE, 3.7f, -3.0f, 50_000f, .06f, .62f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.RANGED, 6.2f, 2.0f, 50_000f, .05f, .46f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.RANGED, -6.2f, -2.0f, 50_000f, .05f, .46f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.ELITE, 2.1f, 5.0f, 50_000f, .07f, .54f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.ELITE, -2.1f, -5.0f, 50_000f, .07f, .54f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHIELDED, 7.0f, -0.9f, 50_000f, .05f, .56f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHIELDED, -7.0f, 0.9f, 50_000f, .05f, .56f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.REGENERATOR, 5.5f, -4.8f, 50_000f, .05f, .54f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.REGENERATOR, -5.5f, 4.8f, 50_000f, .05f, .54f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.PHANTOM, 6.6f, 4.3f, 50_000f, .05f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.PHANTOM, -6.6f, -4.3f, 50_000f, .05f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BOSS, 0.9f, 6.7f, 500_000f, .015f, .78f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BOSS, -0.9f, -6.7f, 500_000f, .015f, .78f, 0f, 1));

            assertTrue("visual probe failed to inject authored enemy crowd", enemies.size >= before + 20);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to access GameScreen enemy collection for visual QA", exception);
        }
    }

    private static void capture(String name) throws Exception {
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull("Android UiAutomation did not return a screenshot", bitmap);
        File root = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "qa");
        assertTrue("unable to create gameplay QA output directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, name);
        try (FileOutputStream stream = new FileOutputStream(output)) {
            assertTrue("unable to encode gameplay QA screenshot", bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
        } finally {
            bitmap.recycle();
        }
        assertTrue("gameplay QA screenshot is unexpectedly small", output.length() > 10_000L);
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to visual probe", activity);
        return activity;
    }

    private static DeadlineZeroGame game(AndroidLauncher activity) {
        Object listener = activity.getApplicationListener();
        assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
        return (DeadlineZeroGame) listener;
    }

    private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        activity.postRunnable(() -> {
            try { action.run(); } catch (Throwable throwable) { failure.set(throwable); } finally { done.countDown(); }
        });
        assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) throw new AssertionError("Android visual probe failed on libGDX game thread", failure.get());
    }
}
