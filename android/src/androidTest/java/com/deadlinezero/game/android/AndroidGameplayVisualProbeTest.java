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
import com.deadlinezero.game.progression.Upgrade;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.MissionsScreen;
import com.deadlinezero.game.screen.CloudSaveScreen;
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
    public void capturesWeeklyMissionsScreen() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.profile.daily.loginStreak = 7;
                game.profile.daily.killsToday = 72;
                game.profile.daily.runsToday = 2;
                game.profile.daily.bossesToday = 0;
                game.profile.weekly.kills = 760;
                game.profile.weekly.runs = 11;
                game.profile.weekly.bosses = 3;
                game.profile.totalRuns = 25;
                game.profile.totalKills = 5_000L;
                game.profile.highestStage = 5;
                game.profile.selectedStage = 5;
                game.profile.accountLevel = 10;
                game.profile.achievements.markClaimed(com.deadlinezero.game.meta.AchievementService.Achievement.FIRST_DEPLOYMENT);
                game.showMissions();
                assertTrue("expected MissionsScreen for weekly visual probe", game.getScreen() instanceof MissionsScreen);
            });
            Thread.sleep(700L);
            capture("weekly-missions.png");
        }
    }

    @Test
    public void capturesGraphicsQualitySettings() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                com.deadlinezero.game.config.GraphicsSettings.set(
                    com.deadlinezero.game.config.GraphicsSettings.Quality.LOW);
                game.showSettings();
                assertTrue("expected SettingsScreen for graphics-quality visual probe",
                    game.getScreen() instanceof com.deadlinezero.game.screen.SettingsScreen);
            });
            Thread.sleep(600L);
            capture("graphics-settings.png");
        } finally {
            com.deadlinezero.game.config.GraphicsSettings.set(
                com.deadlinezero.game.config.GraphicsSettings.Quality.ULTRA);
        }
    }

    @Test
    public void capturesCloudSaveScreen() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showCloudSave();
                assertTrue("expected CloudSaveScreen for visual probe", game.getScreen() instanceof CloudSaveScreen);
            });
            Thread.sleep(500L);
            capture("cloud-save.png");
        }
    }

    @Test
    public void capturesChampionVariantCrowd() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for champion visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(1);
                injectChampionVariantCrowd((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("champion-variants.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectChampionVariantCrowd(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy.Variant[] variants = {
                Enemy.Variant.SWIFT, Enemy.Variant.ARMORED, Enemy.Variant.FERAL, Enemy.Variant.VOLATILE,
                Enemy.Variant.JUGGERNAUT, Enemy.Variant.RAVAGER, Enemy.Variant.AEGIS, Enemy.Variant.HUNTER
            };
            float[][] positions = {
                {-8.5f, 4.2f}, {-3.0f, 5.2f}, {3.0f, 5.2f}, {8.5f, 4.2f},
                {-8.5f, -3.5f}, {-3.0f, -4.8f}, {3.0f, -4.8f}, {8.5f, -3.5f}
            };
            for (int i = 0; i < variants.length; i++) {
                Enemy enemy = new Enemy(Enemy.Type.ELITE, positions[i][0], positions[i][1],
                    500_000f, .01f, .58f, 0f, 1);
                // Instrumentation-only deterministic visual state; runtime selection is tested separately.
                enemy.variant = variants[i];
                enemies.add(enemy);
            }
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject champion variants for visual QA", exception);
        }
    }

    @Test
    public void capturesExpandedUpgradePoolOverlay() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for upgrade visual probe", game.getScreen() instanceof GameScreen);
                injectUpgradeChoices((GameScreen) game.getScreen());
            });
            Thread.sleep(700L);
            capture("upgrade-pool.png");
        }
    }

    private static void injectUpgradeChoices(GameScreen screen) {
        try {
            Field choicesField = GameScreen.class.getDeclaredField("choices");
            choicesField.setAccessible(true);
            Upgrade[] choices = (Upgrade[]) choicesField.get(screen);
            choices[0] = Upgrade.GLASS_CANNON;
            choices[1] = Upgrade.ELEMENTAL_HARMONIZER;
            choices[2] = Upgrade.BARRAGE_MATRIX;

            Field choosingUpgrade = GameScreen.class.getDeclaredField("choosingUpgrade");
            choosingUpgrade.setAccessible(true);
            choosingUpgrade.setBoolean(screen, true);

            Field choosingLegendary = GameScreen.class.getDeclaredField("choosingLegendary");
            choosingLegendary.setAccessible(true);
            choosingLegendary.setBoolean(screen, false);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject expanded upgrade choices for visual QA", exception);
        }
    }

    @Test
    public void capturesRexGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.profile.selectedSurvivor = SurvivorCatalog.Survivor.REX;
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for Rex visual probe", game.getScreen() instanceof GameScreen);
                assertTrue("visual probe must run the production REX selection",
                    game.profile.selectedSurvivor == SurvivorCatalog.Survivor.REX);
            });

            Thread.sleep(2200L);
            capture("rex-gameplay.png");

            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(700L);
            capture("rex-shambler-crowd.png");

            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("rex-attack.png");
        }
    }

    @Test
    public void capturesShamblerGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.profile.selectedSurvivor = SurvivorCatalog.Survivor.REX;
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for Shambler visual probe", game.getScreen() instanceof GameScreen);
                injectShambler((GameScreen) game.getScreen());
            });

            Thread.sleep(900L);
            capture("shambler-gameplay.png");

            runOnGameThread(activity, () -> forceShamblerAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("shambler-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectShambler(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.clear();
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, 3.2f, 500_000f, .01f, .58f, 0f, 1));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject Shambler for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceShamblerAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy shambler = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.type == Enemy.Type.SHAMBLER) {
                    shambler = enemy;
                    break;
                }
            }
            assertNotNull("Shambler missing before attack capture", shambler);
            Field stateField = shambler.attack.getClass().getDeclaredField("state");
            Field timerField = shambler.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(shambler.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(shambler.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force Shambler attack animation for visual QA", exception);
        }
    }

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
    public void capturesBossRevealCameraFraming() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for boss reveal visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(4);
                GameScreen screen = (GameScreen) game.getScreen();
                Enemy boss = injectRevenantBoss(screen);
                armBossReveal(screen, boss);
            });

            // The profile peaks at roughly half of its 1.2 s reveal window.
            Thread.sleep(560L);
            capture("boss-reveal-framing.png");
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
    public void capturesFrostColossusGameplay() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for FROST COLOSSUS visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(40);
                assertTrue("stage 40 must route to FROST COLOSSUS",
                    com.deadlinezero.game.ai.BossIdentity.forStage(40)
                        == com.deadlinezero.game.ai.BossIdentity.FROST_COLOSSUS);
                injectFrostColossusBoss((GameScreen) game.getScreen());
            });
            Thread.sleep(1500L);
            capture("frost-colossus-gameplay.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectFrostColossusBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, 1.05f, 0f, 2));
            enemies.add(new Enemy(Enemy.Type.SHIELDED, -4.5f, 1.8f, 500_000f, .02f, .58f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BRUTE, 4.6f, 1.5f, 500_000f, .02f, .62f, 0f, 1));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject FROST COLOSSUS boss for visual QA", exception);
        }
    }

    @Test
    public void capturesCryogenicDepthsGameplay() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for CRYOGENIC DEPTHS visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(40);
                assertTrue("stage 40 must route to CRYOGENIC DEPTHS",
                    com.deadlinezero.game.visual.EnvironmentBiomeRules.forStage(40)
                        == com.deadlinezero.game.visual.EnvironmentBiomeRules.Biome.CRYOGENIC_DEPTHS);
                injectCryogenicDepthsCrowd((GameScreen) game.getScreen());
            });
            Thread.sleep(1200L);
            capture("cryogenic-depths-gameplay.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectCryogenicDepthsCrowd(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.REGENERATOR, -5.2f, 3.2f, 500_000f, .02f, .58f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.RANGED, 0.6f, 4.4f, 500_000f, .02f, .46f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.ELITE, 5.4f, 2.8f, 500_000f, .02f, .62f, 0f, 1));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject CRYOGENIC DEPTHS crowd for visual QA", exception);
        }
    }

    @Test
    public void capturesCryoVaultGameplay() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for CRYO VAULT visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(30);
                assertTrue("stage 30 must route to CRYO VAULT",
                    com.deadlinezero.game.visual.EnvironmentBiomeRules.forStage(30)
                        == com.deadlinezero.game.visual.EnvironmentBiomeRules.Biome.CRYO_VAULT);
                injectCryoVaultCrowd((GameScreen) game.getScreen());
            });
            Thread.sleep(1200L);
            capture("cryo-vault-gameplay.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectCryoVaultCrowd(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            enemies.add(new Enemy(Enemy.Type.SHIELDED, -4.8f, 3.0f, 500_000f, .02f, .58f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.PHANTOM, 0.4f, 4.2f, 500_000f, .02f, .44f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.BRUTE, 5.2f, 2.6f, 500_000f, .02f, .62f, 0f, 1));
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject CRYO VAULT crowd for visual QA", exception);
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
    public void capturesNullWardGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for NULL WARD visual probe", game.getScreen() instanceof GameScreen);
                RunStageContext.begin(20);
                injectNullWard((GameScreen) game.getScreen());
            });
            Thread.sleep(900L);
            capture("null-ward-gameplay.png");
            runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(500L);
            capture("null-ward-crowd.png");
            runOnGameThread(activity, () -> forceNullWardAttack((GameScreen) game(activity).getScreen()));
            Thread.sleep(80L);
            capture("null-ward-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectNullWard(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
            assertTrue("stage 20 REGENERATOR must resolve to NULL WARD",
                ward.biomeIdentity() == BiomeEnemyRoster.Identity.NULL_WARD);
            enemies.add(ward);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject NULL WARD for visual QA", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void forceNullWardAttack(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy ward = null;
            for (Enemy enemy : enemies) {
                if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.NULL_WARD) {
                    ward = enemy;
                    break;
                }
            }
            assertNotNull("NULL WARD missing before attack capture", ward);
            Field stateField = ward.attack.getClass().getDeclaredField("state");
            Field timerField = ward.attack.getClass().getDeclaredField("timer");
            stateField.setAccessible(true);
            timerField.setAccessible(true);
            stateField.set(ward.attack, EnemyState.TELEGRAPHING);
            timerField.setFloat(ward.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force NULL WARD attack animation for visual QA", exception);
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
    private static Enemy injectRevenantBoss(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy boss = new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2);
            enemies.add(boss);
            return boss;
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject REVENANT boss for visual QA", exception);
        }
    }

    private static void armBossReveal(GameScreen screen, Enemy boss) {
        try {
            Field target = GameScreen.class.getDeclaredField("bossRevealTarget");
            target.setAccessible(true);
            target.set(screen, boss);
            Field timer = GameScreen.class.getDeclaredField("bossRevealTimer");
            timer.setAccessible(true);
            timer.setFloat(screen, com.deadlinezero.game.visual.BossRevealCameraProfile.DURATION);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to arm boss reveal camera for visual QA", exception);
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
        File root = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "qa");
        assertTrue("unable to create gameplay QA output directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, name);

        long size = 0L;
        for (int attempt = 1; attempt <= 3; attempt++) {
            Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
            assertNotNull("Android UiAutomation did not return a screenshot", bitmap);
            try (FileOutputStream stream = new FileOutputStream(output, false)) {
                assertTrue("unable to encode gameplay QA screenshot", bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
            } finally {
                bitmap.recycle();
            }

            size = output.length();
            if (size > 10_000L) return;

            // Android Emulator occasionally exposes a stale/broken color buffer for a single frame.
            // Retry the capture, but keep the exact same semantic size gate for the final artifact.
            if (attempt < 3) Thread.sleep(300L);
        }

        assertTrue("gameplay QA screenshot is unexpectedly small after retries: " + size, size > 10_000L);
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
