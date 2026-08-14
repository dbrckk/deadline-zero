package com.deadlinezero.game.ai;

/** Deterministic boss identity selection that preserves existing rotations and adds Null milestones. */
public enum BossIdentity {
    ALPHA,
    REVENANT,
    WARDEN,
    HARVESTER,
    NULL_ARCHON;

    public static BossIdentity forStage(int stage) {
        int safeStage = Math.max(1, stage);
        if (safeStage >= 20 && Math.floorMod(safeStage - 20, 5) == 0) return NULL_ARCHON;
        if (safeStage >= 12 && safeStage % 5 == 2) return HARVESTER;
        if (safeStage >= 7 && safeStage % 4 == 3) return WARDEN;
        if (RevenantBossProfile.useForStage(safeStage)) return REVENANT;
        return ALPHA;
    }
}
