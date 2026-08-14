package com.deadlinezero.game.meta;

/** Centralized deterministic stage scaling. Keeps combat/reward tuning out of screens. */
public final class StageRules {
    private StageRules() {}

    public static float enemyHpMultiplier(int stage) {
        int s = Math.max(1, stage) - 1;
        return (1f + s * .16f + s * s * .006f) * RunModifierContext.enemyHpMultiplier();
    }

    public static float enemyDamageMultiplier(int stage) {
        int s = Math.max(1, stage) - 1;
        return (1f + s * .095f) * RunModifierContext.enemyDamageMultiplier();
    }

    public static float enemySpeedMultiplier(int stage) {
        int s = Math.max(1, stage) - 1;
        return Math.min(1.60f, Math.min(1.42f, 1f + s * .018f) * RunModifierContext.enemySpeedMultiplier());
    }

    public static float rewardMultiplier(int stage) {
        int s = Math.max(1, stage) - 1;
        return (1f + s * .12f) * RunModifierContext.rewardMultiplier();
    }

    public static int nextStage(int completedStage) {
        return Math.max(1, completedStage) + 1;
    }
}
