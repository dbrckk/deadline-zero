package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Gameplay stat identity for each boss variant. */
public final class BossVariantStats {
    public record Stats(float hpMultiplier, float speedMultiplier, float damageMultiplier) { }

    private static final Stats ALPHA = new Stats(1.00f, 1.00f, 1.00f);
    private static final Stats REVENANT = new Stats(0.92f, 1.08f, 1.08f);
    private static final Stats WARDEN = new Stats(1.24f, 0.82f, 1.18f);
    private static final Stats HARVESTER = new Stats(1.16f, 1.08f, 1.14f);
    private static final Stats NULL_ARCHON = new Stats(
        NullArchonBossProfile.HP_MULTIPLIER,
        NullArchonBossProfile.SPEED_MULTIPLIER,
        NullArchonBossProfile.DAMAGE_MULTIPLIER);

    private BossVariantStats() { }

    public static Stats forIdentity(BossIdentity identity) {
        BossIdentity safe = identity == null ? BossIdentity.ALPHA : identity;
        int threatTier = RunStageContext.threatTier();
        BossAffixRules.Affix affix = BossAffixRules.forRun(RunStageContext.stage(), threatTier);
        Stats base = switch (safe) {
            case REVENANT -> REVENANT;
            case WARDEN -> WARDEN;
            case HARVESTER -> HARVESTER;
            case NULL_ARCHON -> NULL_ARCHON;
            default -> ALPHA;
        };
        return new Stats(
            base.hpMultiplier() * BossAffixRules.hpMultiplier(affix),
            base.speedMultiplier() * BossAffixRules.speedMultiplier(affix),
            base.damageMultiplier() * BossAffixRules.damageMultiplier(affix));
    }

    public static float applyHp(BossIdentity identity, float base) {
        return Math.max(1f, base * forIdentity(identity).hpMultiplier());
    }

    public static float applySpeed(BossIdentity identity, float base) {
        return Math.max(.1f, base * forIdentity(identity).speedMultiplier());
    }

    public static float applyDamage(BossIdentity identity, float base) {
        return Math.max(0f, base * forIdentity(identity).damageMultiplier());
    }
}
