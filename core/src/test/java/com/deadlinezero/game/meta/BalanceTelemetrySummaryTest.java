package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.Test;

final class BalanceTelemetrySummaryTest {
    @Test void summaryComputesStableBalancingMetrics() {
        Array<BalanceRunSample> samples = new Array<>();
        samples.add(new BalanceRunSample(1, 10, 2, 4, true, 60f, 30,
            600f, 120f, 80f, 30f, "REDLINE", "STANDARD PRESSURE", "REX", "ar9", 2, false));
        samples.add(new BalanceRunSample(2, 10, 2, 5, false, 120f, 20,
            600f, 240f, 70f, 40f, "REDLINE", "STANDARD PRESSURE", "REX", "ar9", 2, false));

        BalanceTelemetrySummary.Summary summary = BalanceTelemetrySummary.summarize(samples);
        assertEquals(2, summary.runs());
        assertEquals(1, summary.wins());
        assertEquals(.5f, summary.winRate(), .0001f);
        assertEquals(90f, summary.averageSeconds(), .0001f);
        assertEquals(7.5f, summary.averageDps(), .0001f);
        assertEquals(120f, summary.averageDamageTakenPerMinute(), .0001f);
        assertEquals(20f, summary.averageKillsPerMinute(), .0001f);
    }

    @Test void emptySummaryIsAllZero() {
        BalanceTelemetrySummary.Summary summary = BalanceTelemetrySummary.summarize(new Array<>());
        assertEquals(0, summary.runs());
        assertEquals(0f, summary.winRate(), .0001f);
        assertEquals(0f, summary.averageDps(), .0001f);
    }
}
