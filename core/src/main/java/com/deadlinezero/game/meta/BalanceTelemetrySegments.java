package com.deadlinezero.game.meta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Array;

/** Pure segmentation of local run telemetry for balance analysis. */
public final class BalanceTelemetrySegments {
    public enum Dimension { STAGE, THREAT, CONTRACT, SURVIVOR, WEAPON }

    public record Segment(String key, BalanceTelemetrySummary.Summary summary) {}

    private BalanceTelemetrySegments() {}

    public static List<Segment> group(Array<BalanceRunSample> samples, Dimension dimension) {
        if (samples == null || samples.size == 0 || dimension == null) return List.of();
        Map<String, Array<BalanceRunSample>> buckets = new LinkedHashMap<>();
        for (BalanceRunSample sample : samples) {
            if (sample == null) continue;
            String key = key(sample, dimension);
            buckets.computeIfAbsent(key, ignored -> new Array<>()).add(sample);
        }
        List<Segment> result = new ArrayList<>(buckets.size());
        for (Map.Entry<String, Array<BalanceRunSample>> entry : buckets.entrySet()) {
            result.add(new Segment(entry.getKey(), BalanceTelemetrySummary.summarize(entry.getValue())));
        }
        result.sort(segmentComparator(dimension));
        return List.copyOf(result);
    }

    private static String key(BalanceRunSample sample, Dimension dimension) {
        return switch (dimension) {
            case STAGE -> Integer.toString(sample.stage());
            case THREAT -> Integer.toString(sample.threatTier());
            case CONTRACT -> sample.contract();
            case SURVIVOR -> sample.survivor();
            case WEAPON -> sample.weaponId();
        };
    }

    private static Comparator<Segment> segmentComparator(Dimension dimension) {
        if (dimension == Dimension.STAGE || dimension == Dimension.THREAT) {
            return Comparator.comparingInt(segment -> parseInt(segment.key()));
        }
        return Comparator.comparing(Segment::key);
    }

    private static int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (RuntimeException ignored) { return Integer.MAX_VALUE; }
    }
}
