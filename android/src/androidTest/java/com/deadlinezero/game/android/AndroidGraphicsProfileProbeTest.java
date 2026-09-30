package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.config.GraphicsSettings;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.GameScreen;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runtime contract for every supported graphics-quality / frame-rate combination.
 *
 * This intentionally validates configuration wiring rather than hardware throughput: emulator CI
 * cannot prove that a device sustains 90/120 FPS, but it can prove that the shipping runtime
 * honors every selectable target and quality ceiling when a run is created.
 */
@RunWith(AndroidJUnit4.class)
public final class AndroidGraphicsProfileProbeTest {
    @Test
    public void everyGraphicsProfileAndFrameRateTargetReachesGameScreen() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);

            for (GraphicsSettings.Quality quality : GraphicsSettings.Quality.values()) {
                for (GraphicsSettings.FrameRate frameRate : GraphicsSettings.FrameRate.values()) {
                    AtomicReference<Integer> effectiveTarget = new AtomicReference<>();
                    AtomicReference<Float> effectiveFx = new AtomicReference<>();

                    runOnGameThread(activity, () -> {
                        GraphicsSettings.set(quality);
                        GraphicsSettings.setFrameRate(frameRate);

                        DeadlineZeroGame game = game(activity);
                        game.startRun();
                        game.startRunWithContract(RunModifierContext.offers()[0]);
                        assertTrue("expected GameScreen for graphics profile probe",
                            game.getScreen() instanceof GameScreen);

                        GameScreen screen = (GameScreen) game.getScreen();
                        effectiveTarget.set(screen.effectiveFrameRateTarget());
                        effectiveFx.set(screen.effectiveFxQuality());
                    });

                    assertNotNull("effective FPS target missing for " + quality + "/" + frameRate,
                        effectiveTarget.get());
                    assertNotNull("effective FX quality missing for " + quality + "/" + frameRate,
                        effectiveFx.get());
                    assertEquals("GameScreen did not initialize requested FPS target for "
                            + quality + "/" + frameRate,
                        frameRate.target, effectiveTarget.get().intValue());
                    assertTrue("FX quality exceeded selected ceiling for " + quality + "/" + frameRate,
                        effectiveFx.get() <= quality.fxCeiling + .0001f);
                    assertTrue("FX quality fell below runtime minimum for " + quality + "/" + frameRate,
                        effectiveFx.get() >= .40f - .0001f);
                }
            }
        }
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher unavailable", activity);
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
        if (failure.get() != null) {
            throw new AssertionError("Android graphics profile probe failed on game thread", failure.get());
        }
    }
}
