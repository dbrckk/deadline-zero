package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.deadlinezero.game.DeadlineZeroGame;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AndroidProcessPersistenceTest {
    private static final int EXPECTED_RUNS = 4242;
    private static final long EXPECTED_KILLS = 987_654_321L;
    private static final int EXPECTED_HIGHEST_STAGE = 7;
    private static final int EXPECTED_SELECTED_STAGE = 6;
    private static final String EXPECTED_RECEIPT = "android-process-persistence-probe-v1";

    @Test
    public void seedProfileBeforeExternalProcessDeath() throws Exception {
        Assume.assumeTrue("seed".equals(phase()));
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.profile.totalRuns = EXPECTED_RUNS;
                game.profile.totalKills = EXPECTED_KILLS;
                game.profile.highestStage = EXPECTED_HIGHEST_STAGE;
                game.profile.selectedStage = EXPECTED_SELECTED_STAGE;
                assertTrue("Persistence probe receipt was already present", game.profile.recordDeliveredPurchaseReceipt(EXPECTED_RECEIPT));
                game.saveProfile();
            });
        }
    }

    @Test
    public void verifyProfileAfterExternalProcessRestart() throws Exception {
        Assume.assumeTrue("verify".equals(phase()));
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                assertEquals("totalRuns did not survive process death", EXPECTED_RUNS, game.profile.totalRuns);
                assertEquals("totalKills did not survive process death", EXPECTED_KILLS, game.profile.totalKills);
                assertEquals("highestStage did not survive process death", EXPECTED_HIGHEST_STAGE, game.profile.highestStage);
                assertEquals("selectedStage did not survive process death", EXPECTED_SELECTED_STAGE, game.profile.selectedStage);
                assertTrue("purchase receipt did not survive process death", game.profile.hasDeliveredPurchaseReceipt(EXPECTED_RECEIPT));
            });
        }
    }

    private static String phase() {
        return InstrumentationRegistry.getArguments().getString("persistencePhase", "");
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to persistence probe", activity);
        return activity;
    }

    private static DeadlineZeroGame game(AndroidLauncher activity) {
        Object listener = activity.getApplicationListener();
        assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
        DeadlineZeroGame game = (DeadlineZeroGame) listener;
        assertNotNull("Deadline Zero profile was not initialized", game.profile);
        return game;
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
        if (failure.get() != null) throw new AssertionError("Persistence probe failed on libGDX game thread", failure.get());
    }
}
