package com.deadlinezero.game.meta;

/** Conservative health classification for local balance telemetry segments. */
public final class BalanceHealthRules {
    public static final int MIN_CONFIDENT_RUNS = 5;
    public static final float HARD_WIN_RATE = .28f;
    public static final float EASY_WIN_RATE = .78f;
    public static final float MIN_HEALTHY_SECONDS = 45f;
    public static final float MAX_HEALTHY_SECONDS = 210f;

    public enum Status { LOW_SAMPLE, TOO_HARD, TOO_EASY, TOO_SHORT, TOO_LONG, HEALTHY }

    public record Assessment(Status status, String reason) {}

    private BalanceHealthRules() {}

    public static Assessment assess(BalanceTelemetrySummary.Summary summary) {
        if (summary == null || summary.runs() < MIN_CONFIDENT_RUNS) {
            int runs = summary == null ? 0 : summary.runs();
            return new Assessment(Status.LOW_SAMPLE, "Need " + Math.max(0, MIN_CONFIDENT_RUNS - runs) + " more runs");
        }
        if (summary.winRate() < HARD_WIN_RATE) {
            return new Assessment(Status.TOO_HARD, "Win rate " + percent(summary.winRate()) + " < " + percent(HARD_WIN_RATE));
        }
        if (summary.winRate() > EASY_WIN_RATE) {
            return new Assessment(Status.TOO_EASY, "Win rate " + percent(summary.winRate()) + " > " + percent(EASY_WIN_RATE));
        }
        if (summary.averageSeconds() < MIN_HEALTHY_SECONDS) {
            return new Assessment(Status.TOO_SHORT, "Average run below " + Math.round(MIN_HEALTHY_SECONDS) + "s");
        }
        if (summary.averageSeconds() > MAX_HEALTHY_SECONDS) {
            return new Assessment(Status.TOO_LONG, "Average run above " + Math.round(MAX_HEALTHY_SECONDS) + "s");
        }
        return new Assessment(Status.HEALTHY, "Within target envelope");
    }

    private static String percent(float value) { return Math.round(value * 100f) + "%"; }
}
