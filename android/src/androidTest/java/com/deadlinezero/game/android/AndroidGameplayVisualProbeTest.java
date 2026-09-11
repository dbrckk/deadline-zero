package com.deadlinezero.game.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.visual.CombatVisualEvents;
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
