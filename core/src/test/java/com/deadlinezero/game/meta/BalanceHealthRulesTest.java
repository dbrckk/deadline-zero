package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class BalanceHealthRulesTest {
    @Test void requiresEnoughRunsBeforeJudging() {
        var summary = new BalanceTelemetrySummary.Summary(4, 2, .5f, 100f, 40f, 80f, 20f);
        assertEquals(BalanceHealthRules.Status.LOW_SAMPLE, BalanceHealthRules.assess(summary).status());
    }

    @Test void detectsDifficultyAndDurationOutliers() {
        assertEquals(BalanceHealthRules.Status.TOO_HARD,
            BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 2, .20f, 100f, 40f, 80f, 20f)).status());
        assertEquals(BalanceHealthRules.Status.TOO_EASY,
            BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 9, .90f, 100f, 40f, 80f, 20f)).status());
        assertEquals(BalanceHealthRules.Status.TOO_SHORT,
            BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 5, .50f, 30f, 40f, 80f, 20f)).status());
        assertEquals(BalanceHealthRules.Status.TOO_LONG,
            BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 5, .50f, 240f, 40f, 80f, 20f)).status());
    }

    @Test void acceptsTargetEnvelope() {
        var summary = new BalanceTelemetrySummary.Summary(12, 7, 7f / 12f, 105f, 40f, 80f, 20f);
        assertEquals(BalanceHealthRules.Status.HEALTHY, BalanceHealthRules.assess(summary).status());
    }
}
