package com.deadlinezero.game.ai;

/** Stable boss identity routing by stage. */
public enum BossIdentity {
    ALPHA,
    REVENANT,
    WARDEN,
    HARVESTER,
    NULL_ARCHON;

    public static BossIdentity forStage(int stage) {
        int safeStage = Math.max(1, stage);
        if (safeStage >= 20 && Math.floorMod(safeStage - 20, 5) == 0) return NULL_ARCHON;
        if (safeStage >= 12 && Math.floorMod(safeStage - 12, 5) == 0) return HARVESTER;
        return switch (Math.floorMod(safeStage - 1, 3)) {
            case 1 -> REVENANT;
            case 2 -> WARDEN;
            default -> ALPHA;
        };
    }
}
