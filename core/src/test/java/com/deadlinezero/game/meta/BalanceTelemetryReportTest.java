package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.Array;

final class BalanceTelemetryReportTest {
    @Test void identifiesStrongestSupportedOutlier() {
        Array<BalanceRunSample> samples = new Array<>();
        for (int i = 0; i < 6; i++) samples.add(sample(4, 0, false, "REDLINE", "REX", "ar9"));
        for (int i = 0; i < 6; i++) samples.add(sample(5, 0, i < 4, "BLOOD MOON", "NYX", "shotgun"));

        BalanceTelemetryReport.Report report = BalanceTelemetryReport.analyze(samples);
        assertNotNull(report.worstOutlier());
        assertEquals(BalanceHealthRules.Status.TOO_HARD, report.worstOutlier().assessment().status());
        assertEquals(BalanceTelemetrySegments.Dimension.STAGE, report.worstOutlier().dimension());
        assertEquals("4", report.worstOutlier().key());
    }

    @Test void healthySegmentsProduceNoOutlier() {
        Array<BalanceRunSample> samples = new Array<>();
        for (int i = 0; i < 10; i++) samples.add(sample(4, 1, i < 5, "REDLINE", "REX", "ar9"));
        BalanceTelemetryReport.Report report = BalanceTelemetryReport.analyze(samples);
        assertEquals(BalanceHealthRules.Status.HEALTHY, report.overallHealth().status());
        assertNull(report.worstOutlier());
    }

    private static BalanceRunSample sample(int stage, int threat, boolean victory, String contract, String survivor, String weapon) {
        return new BalanceRunSample(1, stage, threat, 1, victory, 100f, 20, 5000f, 800f, 300f, 80f,
            contract, survivor, weapon, 0, false);
    }
}
