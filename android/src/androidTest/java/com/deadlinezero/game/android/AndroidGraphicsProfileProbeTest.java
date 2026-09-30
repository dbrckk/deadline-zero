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
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runtime contract for every supported graphics-quality / frame-rate combination.
 *
 * Emulator CI cannot prove sustained 90/120 FPS throughput, but it can prove that the shipping
 * runtime honors every selectable target and quality ceiling. The test also writes a machine-
 * readable matrix so release CI can archive exact evidence for the tested build.
 */
@RunWith(AndroidJUnit4.class)
public final class AndroidGraphicsProfileProbeTest {
    @Test
    public void everyGraphicsProfileAndFrameRateTargetReachesGameScreen() throws Exception {
        List<Result> results = new ArrayList<>();

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

                    results.add(new Result(
                        quality.name(),
                        quality.fxCeiling,
                        frameRate.target,
                        effectiveTarget.get(),
                        effectiveFx.get()
                    ));
                }
            }
        }

        assertEquals("expected complete 4 x 3 graphics matrix", 12, results.size());
        writeMatrix(results);
    }

    private static void writeMatrix(List<Result> results) throws Exception {
        File root = new File(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getExternalFilesDir(null),
            "qa"
        );
        assertTrue("unable to create graphics-profile QA output directory",
            root.isDirectory() || root.mkdirs());

        File output = new File(root, "graphics-profile-matrix.json");
        try (FileWriter writer = new FileWriter(output, false)) {
            writer.write("{\n  \"schemaVersion\": 1,\n  \"combinations\": [\n");
            for (int i = 0; i < results.size(); i++) {
                Result result = results.get(i);
                writer.write("    {");
                writer.write("\"quality\":\"" + result.quality + "\",");
                writer.write("\"qualityCeiling\":" + result.qualityCeiling + ",");
                writer.write("\"requestedFps\":" + result.requestedFps + ",");
                writer.write("\"effectiveFps\":" + result.effectiveFps + ",");
                writer.write("\"effectiveFxQuality\":" + result.effectiveFxQuality);
                writer.write("}");
                writer.write(i + 1 < results.size() ? ",\n" : "\n");
            }
            writer.write("  ]\n}\n");
        }
        assertTrue("graphics profile matrix JSON was not written",
            output.isFile() && output.length() > 200L);
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

    private static final class Result {
        final String quality;
        final float qualityCeiling;
        final int requestedFps;
        final int effectiveFps;
        final float effectiveFxQuality;

        Result(String quality, float qualityCeiling, int requestedFps, int effectiveFps, float effectiveFxQuality) {
            this.quality = quality;
            this.qualityCeiling = qualityCeiling;
            this.requestedFps = requestedFps;
            this.effectiveFps = effectiveFps;
            this.effectiveFxQuality = effectiveFxQuality;
        }
    }
}
