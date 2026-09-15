package com.deadlinezero.game.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.RunContractScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import com.deadlinezero.game.screen.VictoryScreen;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AndroidFirstPlayableJourneyTest {
    @Test
    public void traversesContractCombatDefeatAndVictorySettlement() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            run(activity, () -> {
                DeadlineZeroGame game = game(activity);
                int runsBefore = game.profile.totalRuns;

                game.profile.selectedStage = 1;
                game.startRun();
                assertTrue("startRun must open contract selection", game.getScreen() instanceof RunContractScreen);

                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("contract selection must enter combat", game.getScreen() instanceof GameScreen);

                game.finishRun(12, 45f, false, 0);
                assertTrue("defeat settlement must open run result", game.getScreen() instanceof RunResultScreen);
                assertEquals("defeat must settle exactly one run", runsBefore + 1, game.profile.totalRuns);

                game.startRun();
                assertTrue(game.getScreen() instanceof RunContractScreen);
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue(game.getScreen() instanceof GameScreen);

                // Public settlement boundary: bossKilled=true exercises victory rewards/progression.
                // Mission-runtime boss objective semantics are covered independently by combat tests.
                game.finishRun(80, 180f, true, 0);
                assertTrue("boss clear must open victory result", game.getScreen() instanceof VictoryScreen);
                assertEquals("victory must settle exactly one additional run", runsBefore + 2, game.profile.totalRuns);

                game.saveProfile();
            });
        }
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> ref = new AtomicReference<>();
        scenario.onActivity(ref::set);
        assertNotNull("Android launcher unavailable", ref.get());
        return ref.get();
    }

    private static DeadlineZeroGame game(AndroidLauncher activity) {
        assertTrue(activity.getApplicationListener() instanceof DeadlineZeroGame);
        DeadlineZeroGame game = (DeadlineZeroGame) activity.getApplicationListener();
        assertNotNull("profile unavailable", game.profile);
        return game;
    }

    private static void run(AndroidLauncher activity, Runnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        activity.postRunnable(() -> {
            try { action.run(); } catch (Throwable t) { failure.set(t); } finally { done.countDown(); }
        });
        assertTrue("game thread timeout", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) throw new AssertionError("first-playable journey failed", failure.get());
    }
}
