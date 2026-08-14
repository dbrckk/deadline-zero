package com.deadlinezero.game.meta;

import java.util.List;

import com.badlogic.gdx.utils.Array;

/** Produces actionable, read-only balance diagnostics from recent local telemetry. */
public final class BalanceTelemetryReport {
    public record Outlier(BalanceTelemetrySegments.Dimension dimension, String key,
                          BalanceTelemetrySummary.Summary summary, BalanceHealthRules.Assessment assessment) {}
    public record Report(BalanceTelemetrySummary.Summary overall, BalanceHealthRules.Assessment overallHealth,
                         Outlier worstOutlier) {}

    private BalanceTelemetryReport() {}

    public static Report analyze(Array<BalanceRunSample> samples) {
        BalanceTelemetrySummary.Summary overall = BalanceTelemetrySummary.summarize(samples);
        BalanceHealthRules.Assessment overallHealth = BalanceHealthRules.assess(overall);
        Outlier worst = null;
        int worstRank = -1;
        for (BalanceTelemetrySegments.Dimension dimension : BalanceTelemetrySegments.Dimension.values()) {
            List<BalanceTelemetrySegments.Segment> segments = BalanceTelemetrySegments.group(samples, dimension);
            for (BalanceTelemetrySegments.Segment segment : segments) {
                BalanceHealthRules.Assessment assessment = BalanceHealthRules.assess(segment.summary());
                int rank = severity(assessment.status());
                if (rank > worstRank || (rank == worstRank && worseEvidence(segment.summary(), worst == null ? null : worst.summary()))) {
                    worstRank = rank;
                    worst = new Outlier(dimension, segment.key(), segment.summary(), assessment);
                }
            }
        }
        if (worst != null && worst.assessment().status() == BalanceHealthRules.Status.HEALTHY) worst = null;
        return new Report(overall, overallHealth, worst);
    }

    private static int severity(BalanceHealthRules.Status status) {
        return switch (status) {
            case TOO_HARD, TOO_EASY -> 4;
            case TOO_SHORT, TOO_LONG -> 3;
            case HEALTHY -> 1;
            case LOW_SAMPLE -> 0;
        };
    }

    private static boolean worseEvidence(BalanceTelemetrySummary.Summary candidate, BalanceTelemetrySummary.Summary current) {
        if (current == null) return true;
        if (candidate.runs() != current.runs()) return candidate.runs() > current.runs();
        return Math.abs(candidate.winRate() - .53f) > Math.abs(current.winRate() - .53f);
    }
}
