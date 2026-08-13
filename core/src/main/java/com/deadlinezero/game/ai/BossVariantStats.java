package com.deadlinezero.game.ai;

/** Applies deterministic stat selection for ALPHA and REVENANT boss spawns. */
public final class BossVariantStats {
    private BossVariantStats() { }

    public record Stats(float hp, float speed, float damage) { }

    public static Stats forStage(int stage, float baseHp, float baseSpeed, float baseDamage) {
        boolean revenant = RevenantBossProfile.useForStage(stage);
        return revenant
            ? new Stats(baseHp * RevenantBossProfile.HP_MULTIPLIER,
                        baseSpeed * RevenantBossProfile.SPEED_MULTIPLIER,
                        baseDamage * RevenantBossProfile.DAMAGE_MULTIPLIER)
            : new Stats(baseHp, baseSpeed, baseDamage);
    }
}
