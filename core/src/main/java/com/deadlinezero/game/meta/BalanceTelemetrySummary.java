package com.deadlinezero.game.meta;

import com.badlogic.gdx.utils.Array;

/** Pure aggregation helpers for recent local balancing telemetry. */
public final class BalanceTelemetrySummary {
    public record Summary(int runs, int wins, float winRate, float averageSeconds, float averageDps,
                          float averageDamageTakenPerMinute, float averageKillsPerMinute) {}

    private BalanceTelemetrySummary() {}

    public static Summary summarize(Array<BalanceRunSample> samples) {
        if (samples == null || samples.size == 0) return new Summary(0, 0, 0f, 0f, 0f, 0f, 0f);
        int runs = 0;
        int wins = 0;
        double seconds = 0d;
        double dps = 0d;
        double takenPerMinute = 0d;
        double killsPerMinute = 0d;
        for (BalanceRunSample sample : samples) {
            if (sample == null) continue;
            runs++;
            if (sample.victory()) wins++;
            seconds += sample.seconds();
            dps += sample.dps();
            takenPerMinute += sample.damageTakenPerMinute();
            killsPerMinute += sample.killsPerMinute();
        }
        if (runs == 0) return new Summary(0, 0, 0f, 0f, 0f, 0f, 0f);
        return new Summary(runs, wins, wins / (float)runs,
            finite(seconds / runs), finite(dps / runs), finite(takenPerMinute / runs), finite(killsPerMinute / runs));
    }

    private static float finite(double value) {
        if (!Double.isFinite(value) || value <= 0d) return 0f;
        return value >= Float.MAX_VALUE ? Float.MAX_VALUE : (float)value;
    }
}
