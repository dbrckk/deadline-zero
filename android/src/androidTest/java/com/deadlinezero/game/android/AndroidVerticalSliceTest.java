package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.screen.RunContractScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AndroidVerticalSliceTest {
    @Test
    public void realAndroidRuntimeCompletesM0VerticalSliceAndPersistsSettlement() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            AtomicReference<Integer> runsBefore = new AtomicReference<>();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                assertTrue("expected menu after Android launcher create", game.getScreen() instanceof MenuScreen);
                runsBefore.set(game.profile.totalRuns);
            });
            allowFrames();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                assertTrue("expected contract screen after startRun", game.getScreen() instanceof RunContractScreen);
            });
            allowFrames();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen after contract selection", game.getScreen() instanceof GameScreen);
            });
            allowFrames();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.finishRun(12, 30f, false, 1);
                assertTrue("expected RunResultScreen after settlement", game.getScreen() instanceof RunResultScreen);
                assertEquals("settlement must increment totalRuns exactly once", runsBefore.get().intValue() + 1, game.profile.totalRuns);
                assertEquals("settlement must survive immediate Android profile reload", game.profile.totalRuns, ProfileStore.load().totalRuns);
            });
            allowFrames();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showMenu();
                assertTrue("expected menu after leaving result screen", game.getScreen() instanceof MenuScreen);
                assertEquals("returning to menu must not duplicate settlement", runsBefore.get().intValue() + 1, game.profile.totalRuns);
            });
            allowFrames();
        }
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to vertical-slice probe", activity);
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
        if (failure.get() != null) throw new AssertionError("Android vertical-slice probe failed on libGDX game thread", failure.get());
    }

    private static void allowFrames() throws InterruptedException {
        Thread.sleep(250L);
    }
}
