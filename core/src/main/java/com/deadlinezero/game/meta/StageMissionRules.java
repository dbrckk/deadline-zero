package com.deadlinezero.game.meta;

/** Centralized rules for finite campaign missions. */
public final class StageMissionRules {
    private StageMissionRules() {}

    public static float bossArrivalSeconds(int stage) {
        int s = Math.max(1, stage) - 1;
        return Math.min(600f, 360f + s * 15f);
    }

    public static long firstClearCredits(int stage) {
        return 900L + Math.max(1, stage) * 220L;
    }

    public static int firstClearGems(int stage) {
        return 8 + Math.min(22, Math.max(1, stage) * 2);
    }
}
