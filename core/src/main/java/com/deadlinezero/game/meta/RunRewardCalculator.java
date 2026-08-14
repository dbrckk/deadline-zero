package com.deadlinezero.game.meta;

/** Centralizes soft-currency rewards so balancing can evolve without touching combat code. */
public final class RunRewardCalculator {
    public record Rewards(long credits, long accountXp, int gems) {}

    private RunRewardCalculator() {}

    public static Rewards calculate(int kills, float secondsSurvived, boolean bossKilled, int stage) {
        int safeStage = Math.max(1, stage);
        long credits = Math.max(0, kills) * 2L + (long)(Math.max(0f, secondsSurvived) / 6f);
        long xp = Math.max(0, kills) + (long)(Math.max(0f, secondsSurvived) / 4f);
        if (bossKilled) {
            credits = ProfileCounterMath.addNonNegative(credits, 180L + safeStage * 25L);
            xp = ProfileCounterMath.addNonNegative(xp, 90L + safeStage * 15L);
        }
        float multiplier = StageRules.rewardMultiplier(safeStage);
        credits = ProfileCounterMath.scaleNonNegative(credits, multiplier);
        xp = ProfileCounterMath.scaleNonNegative(xp, 1f + (multiplier - 1f) * .65f);
        int gems = bossKilled ? Math.min(12, 1 + safeStage / 3) : 0;
        return new Rewards(credits, xp, gems);
    }
}
