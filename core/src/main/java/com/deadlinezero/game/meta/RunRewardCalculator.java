package com.deadlinezero.game.meta;

/** Centralizes soft-currency rewards so balancing can evolve without touching combat code. */
public final class RunRewardCalculator {
    public record Rewards(long credits, long accountXp, int gems) {}

    private RunRewardCalculator() {}

    public static Rewards calculate(int kills, float secondsSurvived, boolean bossKilled, int stage) {
        long credits = Math.max(0, kills) * 2L + (long)(Math.max(0f, secondsSurvived) / 6f);
        long xp = Math.max(0, kills) + (long)(Math.max(0f, secondsSurvived) / 4f);
        if (bossKilled) {
            credits += 180L + Math.max(1, stage) * 25L;
            xp += 90L + Math.max(1, stage) * 15L;
        }
        int gems = bossKilled ? Math.min(8, 1 + Math.max(1, stage) / 3) : 0;
        return new Rewards(credits, xp, gems);
    }
}
