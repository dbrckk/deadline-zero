package com.deadlinezero.game.android;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Deterministic combat-state screenshots used as the zombie-wave visual regression contract. */
@RunWith(AndroidJUnit4.class)
public final class AndroidCombatVisualRegressionTest {
    private static final String[] EXPECTED_CAPTURES = {
        "combat-early-wave.png",
        "combat-dense-horde.png",
        "combat-ranged-pressure.png",
        "combat-champion-crowd.png",
        "combat-boss-phase.png",
        "combat-low-hp.png",
        "combat-pickup-upgrade.png"
    };

    @Test
    public void capturesRequiredCombatRegressionStates() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = CombatVisualProbeFixture.activity(scenario);
            CombatVisualProbeFixture.assertWideAspectWhenRequested();
            CombatVisualProbeFixture.captureRequiredStates(activity, EXPECTED_CAPTURES);
        }
    }
}
