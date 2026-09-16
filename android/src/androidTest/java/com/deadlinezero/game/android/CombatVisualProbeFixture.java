package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.progression.Upgrade;
import com.deadlinezero.game.screen.GameScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** Test-only deterministic state builder for combat visual regression screenshots. */
final class CombatVisualProbeFixture {
    private static final float MIN_WIDE_ASPECT = 2.05f;
    private static final String WIDE_PROBE_ARGUMENT = "deadlinezero.wideProbe";

    private CombatVisualProbeFixture() { }

    static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to combat visual probe", activity);
        return activity;
    }

    static void assertWideAspectWhenRequested() {
        if (!wideProbeRequested()) return;
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull("Android UiAutomation did not return a wide-aspect probe screenshot", bitmap);
        try {
            float aspect = bitmap.getWidth() / (float) Math.max(1, bitmap.getHeight());
            assertTrue("wide combat probe did not reach aspect > " + MIN_WIDE_ASPECT + ", was " + aspect,
                aspect > MIN_WIDE_ASPECT);
        } finally {
            bitmap.recycle();
        }
    }

    static void captureRequiredStates(AndroidLauncher activity, String[] names) throws Exception {
        assertNotNull("capture names", names);
        assertEquals("combat regression contract must contain seven states", 7, names.length);

        runOnGameThread(activity, () -> {
            DeadlineZeroGame game = game(activity);
            game.startRun();
            game.startRunWithContract(RunModifierContext.offers()[0]);
            assertTrue("expected GameScreen for combat visual regression", game.getScreen() instanceof GameScreen);
        });

        captureState(activity, names[0], CombatVisualProbeFixture::setupEarlyWave);
        captureState(activity, names[1], CombatVisualProbeFixture::setupDenseHorde);
        captureState(activity, names[2], CombatVisualProbeFixture::setupRangedPressure);
        captureState(activity, names[3], CombatVisualProbeFixture::setupChampionCrowd);
        captureState(activity, names[4], CombatVisualProbeFixture::setupBossPhase);
        captureState(activity, names[5], CombatVisualProbeFixture::setupLowHp);
        captureState(activity, names[6], CombatVisualProbeFixture::setupUpgradeState);
    }

    private static void captureState(AndroidLauncher activity, String name, StateSetup setup) throws Exception {
        runOnGameThread(activity, () -> {
            GameScreen screen = screen(activity);
            resetPresentationState(screen);
            setup.apply(screen);
        });
        // One or more rendered frames must occur after deterministic state injection.
        Thread.sleep(420L);
        capture(name);
    }

    private static void setupEarlyWave(GameScreen screen) {
        RunStageContext.begin(1);
        Array<Enemy> enemies = enemies(screen);
        Enemy.Type[] types = {
            Enemy.Type.SHAMBLER, Enemy.Type.SHAMBLER, Enemy.Type.RUNNER, Enemy.Type.SHAMBLER,
            Enemy.Type.BRUTE, Enemy.Type.RUNNER, Enemy.Type.SHAMBLER, Enemy.Type.RANGED
        };
        float[][] positions = {
            {-7.5f, 3.8f}, {-4.8f, -4.2f}, {-1.8f, 5.4f}, {2.2f, -5.0f},
            {5.8f, 3.4f}, {7.2f, -2.8f}, {0.6f, 6.2f}, {-6.8f, -1.2f}
        };
        for (int i = 0; i < types.length; i++) {
            enemies.add(newEnemy(types[i], positions[i][0], positions[i][1], 80_000f, .01f, radius(types[i]), 0f, 1));
        }
    }

    private static void setupDenseHorde(GameScreen screen) {
        RunStageContext.begin(1);
        Array<Enemy> enemies = enemies(screen);
        Enemy.Type[] cycle = {
            Enemy.Type.SHAMBLER, Enemy.Type.RUNNER, Enemy.Type.SHAMBLER, Enemy.Type.BRUTE,
            Enemy.Type.SHAMBLER, Enemy.Type.RANGED, Enemy.Type.SHIELDED, Enemy.Type.PHANTOM
        };
        for (int i = 0; i < 36; i++) {
            double angle = Math.PI * 2d * i / 36d;
            float radius = 5.3f + (i % 3) * 1.25f;
            float x = (float) Math.cos(angle) * radius * 1.34f;
            float y = (float) Math.sin(angle) * radius;
            Enemy.Type type = cycle[i % cycle.length];
            enemies.add(newEnemy(type, x, y, 140_000f, .008f, radius(type), 0f, 1));
        }
    }

    private static void setupRangedPressure(GameScreen screen) {
        RunStageContext.begin(10);
        Array<Enemy> enemies = enemies(screen);
        float[][] positions = {
            {-7.2f, 4.5f}, {-2.7f, 5.9f}, {2.8f, 5.8f}, {7.1f, 4.3f},
            {-7.0f, -4.0f}, {-2.5f, -5.6f}, {2.6f, -5.5f}, {7.0f, -3.9f}
        };
        for (float[] position : positions) {
            Enemy enemy = newEnemy(Enemy.Type.RANGED, position[0], position[1], 180_000f, .006f, .48f, 0f, 1);
            forceTelegraph(enemy);
            enemies.add(enemy);
        }
    }

    private static void setupChampionCrowd(GameScreen screen) {
        RunStageContext.begin(12);
        Array<Enemy> enemies = enemies(screen);
        Enemy.Variant[] variants = {
            Enemy.Variant.SWIFT, Enemy.Variant.ARMORED, Enemy.Variant.FERAL, Enemy.Variant.VOLATILE,
            Enemy.Variant.JUGGERNAUT, Enemy.Variant.RAVAGER, Enemy.Variant.AEGIS, Enemy.Variant.HUNTER
        };
        float[][] positions = {
            {-7.6f, 4.0f}, {-2.8f, 5.0f}, {2.8f, 5.0f}, {7.6f, 4.0f},
            {-7.6f, -3.5f}, {-2.8f, -4.8f}, {2.8f, -4.8f}, {7.6f, -3.5f}
        };
        for (int i = 0; i < variants.length; i++) {
            Enemy enemy = newEnemy(Enemy.Type.ELITE, positions[i][0], positions[i][1], 220_000f, .006f, .56f, 0f, 1);
            enemy.applyVariant(variants[i]);
            enemies.add(enemy);
        }
    }

    private static void setupBossPhase(GameScreen screen) {
        RunStageContext.begin(40);
        Enemy boss = newEnemy(Enemy.Type.BOSS, 0f, 4.2f, 900_000f, .004f, 1.05f, 0f, 1);
        boss.hp = boss.maxHp * .28f;
        boss.bossPhases.update(.28f);
        enemies(screen).add(boss);
    }

    private static void setupLowHp(GameScreen screen) {
        RunStageContext.begin(1);
        Player player = player(screen);
        player.hp = Math.max(1f, player.maxHp * .20f);
        Array<Enemy> enemies = enemies(screen);
        float[][] positions = {{-6f,3f},{0f,5.5f},{6f,3f},{-6f,-3f},{0f,-5.5f},{6f,-3f}};
        for (int i = 0; i < positions.length; i++) {
            Enemy.Type type = i % 3 == 0 ? Enemy.Type.BRUTE : Enemy.Type.SHAMBLER;
            enemies.add(newEnemy(type, positions[i][0], positions[i][1], 120_000f, .006f, radius(type), 0f, 1));
        }
    }

    private static void setupUpgradeState(GameScreen screen) {
        Player player = player(screen);
        player.hp = player.maxHp;
        try {
            Field choicesField = GameScreen.class.getDeclaredField("choices");
            choicesField.setAccessible(true);
            Upgrade[] choices = (Upgrade[]) choicesField.get(screen);
            choices[0] = Upgrade.GLASS_CANNON;
            choices[1] = Upgrade.ELEMENTAL_HARMONIZER;
            choices[2] = Upgrade.BARRAGE_MATRIX;
            setBoolean(screen, "choosingUpgrade", true);
            setBoolean(screen, "choosingLegendary", false);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to configure upgrade visual state", exception);
        }
    }

    private static void resetPresentationState(GameScreen screen) {
        enemies(screen).clear();
        Player player = player(screen);
        player.position.set(0f, 0f);
        player.velocity.setZero();
        player.hp = player.maxHp;
        player.alive = true;
        try {
            setBoolean(screen, "choosingUpgrade", false);
            setBoolean(screen, "choosingLegendary", false);
            setBoolean(screen, "gameOver", false);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to reset combat visual state", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static Array<Enemy> enemies(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            return (Array<Enemy>) field.get(screen);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to access GameScreen enemies", exception);
        }
    }

    private static Player player(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("player");
            field.setAccessible(true);
            return (Player) field.get(screen);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to access GameScreen player", exception);
        }
    }

    private static Enemy newEnemy(Enemy.Type type, float x, float y, float hp, float speed,
                                  float radius, float damage, int xp) {
        try {
            Constructor<Enemy> constructor = Enemy.class.getDeclaredConstructor(
                Enemy.Type.class, float.class, float.class, float.class, float.class,
                float.class, float.class, int.class, boolean.class
            );
            constructor.setAccessible(true);
            return constructor.newInstance(type, x, y, hp, speed, radius, damage, xp, false);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to create deterministic visual-probe enemy", exception);
        }
    }

    private static void forceTelegraph(Enemy enemy) {
        try {
            Field state = enemy.attack.getClass().getDeclaredField("state");
            Field timer = enemy.attack.getClass().getDeclaredField("timer");
            state.setAccessible(true);
            timer.setAccessible(true);
            state.set(enemy.attack, EnemyState.TELEGRAPHING);
            timer.setFloat(enemy.attack, 10f);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to force ranged pressure telegraph", exception);
        }
    }

    private static float radius(Enemy.Type type) {
        return switch (type) {
            case BRUTE -> .64f;
            case SHIELDED -> .58f;
            case ELITE -> .56f;
            case RANGED -> .48f;
            case RUNNER, PHANTOM -> .46f;
            default -> .52f;
        };
    }

    private static void setBoolean(GameScreen screen, String name, boolean value) throws ReflectiveOperationException {
        Field field = GameScreen.class.getDeclaredField(name);
        field.setAccessible(true);
        field.setBoolean(screen, value);
    }

    private static GameScreen screen(AndroidLauncher activity) {
        DeadlineZeroGame game = game(activity);
        assertTrue("combat visual fixture requires GameScreen", game.getScreen() instanceof GameScreen);
        return (GameScreen) game.getScreen();
    }

    private static DeadlineZeroGame game(AndroidLauncher activity) {
        Object listener = activity.getApplicationListener();
        assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
        return (DeadlineZeroGame) listener;
    }

    private static void capture(String name) throws Exception {
        File root = new File(
            InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null),
            "qa"
        );
        assertTrue("unable to create combat visual QA output directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, name);

        long size = 0L;
        for (int attempt = 1; attempt <= 3; attempt++) {
            Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
            assertNotNull("Android UiAutomation did not return a combat regression screenshot", bitmap);
            try {
                assertTrue("combat regression screenshot width must be non-zero", bitmap.getWidth() > 0);
                assertTrue("combat regression screenshot height must be non-zero", bitmap.getHeight() > 0);
                if (wideProbeRequested()) {
                    float aspect = bitmap.getWidth() / (float) Math.max(1, bitmap.getHeight());
                    assertTrue("wide combat regression capture aspect must exceed " + MIN_WIDE_ASPECT + ": " + aspect,
                        aspect > MIN_WIDE_ASPECT);
                }
                try (FileOutputStream stream = new FileOutputStream(output, false)) {
                    assertTrue("unable to encode combat regression PNG",
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
                }
            } finally {
                bitmap.recycle();
            }
            size = output.length();
            if (size > 10_000L) return;
            if (attempt < 3) Thread.sleep(300L);
        }
        assertTrue("combat regression screenshot is unexpectedly small after retries: " + size, size > 10_000L);
    }

    private static boolean wideProbeRequested() {
        Bundle arguments = InstrumentationRegistry.getArguments();
        return "true".equalsIgnoreCase(arguments.getString(WIDE_PROBE_ARGUMENT, "false"));
    }

    private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        activity.postRunnable(() -> {
            try {
                action.run();
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                done.countDown();
            }
        });
        assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) {
            throw new AssertionError("Combat visual fixture failed on libGDX game thread", failure.get());
        }
    }

    @FunctionalInterface
    private interface StateSetup {
        void apply(GameScreen screen);
    }
}
