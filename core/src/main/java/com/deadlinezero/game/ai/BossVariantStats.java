package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Applies deterministic stat selection for all boss identities and active endgame affix. */
public final class BossVariantStats {
    private BossVariantStats() { }

    public record Stats(float hp, float speed, float damage) { }

    public static Stats forStage(int stage, float baseHp, float baseSpeed, float baseDamage) {
        Stats identityStats = forIdentity(BossIdentity.forStage(stage), baseHp, baseSpeed, baseDamage);
        BossAffixRules.Affix affix = BossAffixRules.forRun(stage, RunStageContext.threatTier());
        return new Stats(identityStats.hp() * affix.hp, identityStats.speed() * affix.speed,
            identityStats.damage() * affix.damage);
    }

    public static Stats forIdentity(BossIdentity identity, float baseHp, float baseSpeed, float baseDamage) {
        BossIdentity safeIdentity = identity == null ? BossIdentity.ALPHA : identity;
        return switch (safeIdentity) {
            case REVENANT -> new Stats(baseHp * RevenantBossProfile.HP_MULTIPLIER,
                baseSpeed * RevenantBossProfile.SPEED_MULTIPLIER,
                baseDamage * RevenantBossProfile.DAMAGE_MULTIPLIER);
            case WARDEN -> new Stats(baseHp * WardenBossProfile.HP_MULTIPLIER,
                baseSpeed * WardenBossProfile.SPEED_MULTIPLIER,
                baseDamage * WardenBossProfile.DAMAGE_MULTIPLIER);
            default -> new Stats(baseHp, baseSpeed, baseDamage);
        };
    }
}
