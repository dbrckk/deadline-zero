package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class RunShareTextTest {
    @Test void shareTextContainsOnlySettledRunFactsAndPlayLink() {
        RunResult result = new RunResult(87, 154f, true, 20,
            new RunRewardCalculator.Rewards(500, 300, 2), null,
            "REDLINE • SWARM", 38, 6, 50, 0, 0);

        String text = RunShareText.format(result);
        assertTrue(text.contains("Stage 20"));
        assertTrue(text.contains("Threat 6"));
        assertTrue(text.contains("87 kills"));
        assertTrue(text.contains("02:34"));
        assertTrue(text.contains("REDLINE • SWARM"));
        assertTrue(text.endsWith(RunShareText.PLAY_URL));
        assertFalse(text.toLowerCase().contains("best"));
        assertFalse(text.toLowerCase().contains("million"));
    }

    @Test void threatZeroDoesNotPretendAscensionWasActive() {
        RunResult result = new RunResult(10, 65f, true, 2,
            new RunRewardCalculator.Rewards(100, 50, 0), null);
        String text = RunShareText.format(result);
        assertFalse(text.contains("Threat 0"));
        assertTrue(text.contains("01:05"));
    }

    @Test void contractTextCannotInjectExtraLines() {
        RunResult result = new RunResult(1, 1f, true, 1,
            new RunRewardCalculator.Rewards(1, 1, 0), null,
            "REDLINE\nFAKE CLAIM", 0);
        String text = RunShareText.format(result);
        assertTrue(text.contains("Contract: REDLINE FAKE CLAIM"));
    }
}
