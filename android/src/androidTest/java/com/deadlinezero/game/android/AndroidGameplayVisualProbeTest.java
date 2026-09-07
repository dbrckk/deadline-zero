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
    public void capturesRexGameplayAndAttackFrames() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for visual probe", game.getScreen() instanceof GameScreen);
            });

            // Opening pressure confirms the ordinary gameplay composition first.
            Thread.sleep(2200L);
            capture("rex-gameplay.png");

            // Force a deterministic phone-scale composition containing authored Rex and four
            // durable Shamblers. Reflection is deliberately confined to instrumentation code so
            // production GameScreen does not gain QA-only API surface.
            runOnGameThread(activity, () -> injectShamblerCrowd((GameScreen) game(activity).getScreen()));
            Thread.sleep(180L);
            capture("rex-shambler-crowd.png");

            // Force the authored attack presentation window without changing gameplay state.
            runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
            Thread.sleep(80L);
            capture("rex-attack.png");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectShamblerCrowd(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            // Large HP keeps the QA actors alive despite Rex auto-fire. Very low speed keeps the
            // four cardinal placements readable while still exercising normal enemy rendering.
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, -5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, 4.2f, 50_000f, .08f, .50f, 0f, 1));
            enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, -4.2f, 50_000f, .08f, .50f, 0f, 1));
            assertTrue("visual probe failed to inject Shambler crowd", enemies.size >= 4);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to access GameScreen enemy collection for visual QA", exception);
        }
    }

    private static void capture(String name) throws Exception {
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull("Android UiAutomation did not return a screenshot", bitmap);
        File root = new File(
            InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "qa");
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
            try {
                action.run();
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                done.countDown();
            }
        });
        assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) throw new AssertionError("Android visual probe failed on libGDX game thread", failure.get());
    }
}
