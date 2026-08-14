package com.deadlinezero.game.ai;

/** Deterministic boss identity selection that preserves the existing REVENANT rotation. */
public enum BossIdentity {
    ALPHA,
    REVENANT,
    WARDEN;

    public static BossIdentity forStage(int stage) {
        int safeStage = Math.max(1, stage);
        if (safeStage >= 7 && safeStage % 4 == 3) return WARDEN;
        if (RevenantBossProfile.useForStage(safeStage)) return REVENANT;
        return ALPHA;
    }
}
