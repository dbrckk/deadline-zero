package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.GameScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Regression probe for the 1536x691 landscape format reported by the physical-device smoke test. */
@RunWith(AndroidJUnit4.class)
public final class AndroidMobilePresentationProbeTest {
    private static final int WIDTH = 1536;
    private static final int HEIGHT = 691;

    @Test
    public void capturesCombatAt1536x691() throws Exception {
        shell("wm size " + WIDTH + "x" + HEIGHT);
        try {
            Thread.sleep(500L);
            try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
                AndroidLauncher activity = activity(scenario);
                runOnGameThread(activity, () -> {
                    DeadlineZeroGame game = game(activity);
                    game.startRun();
                    game.startRunWithContract(RunModifierContext.offers()[0]);
                    assertTrue("expected GameScreen for physical-phone presentation probe",
                        game.getScreen() instanceof GameScreen);
                });
                Thread.sleep(900L);
                capture("mobile-1536x691.png");
            }
        } finally {
            shell("wm size reset");
            Thread.sleep(300L);
        }
    }

    private static void capture(String name) throws Exception {
        File root = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "qa");
        assertTrue("unable to create mobile presentation QA directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, name);

        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull("Android UiAutomation did not return a mobile presentation screenshot", bitmap);
        try {
            assertEquals("physical-phone QA screenshot width", WIDTH, bitmap.getWidth());
            assertEquals("physical-phone QA screenshot height", HEIGHT, bitmap.getHeight());
            try (FileOutputStream stream = new FileOutputStream(output, false)) {
                assertTrue("unable to encode mobile presentation screenshot",
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
            }
        } finally {
            bitmap.recycle();
        }
        assertTrue("mobile presentation screenshot is unexpectedly small", output.length() > 10_000L);
    }

    private static void shell(String command) throws Exception {
        try (ParcelFileDescriptor descriptor = InstrumentationRegistry.getInstrumentation()
            .getUiAutomation().executeShellCommand(command)) {
            assertNotNull("shell command did not return a descriptor: " + command, descriptor);
        }
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to mobile presentation probe", activity);
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
            throw new AssertionError("mobile presentation probe failed on libGDX game thread", failure.get());
        }
    }
}
