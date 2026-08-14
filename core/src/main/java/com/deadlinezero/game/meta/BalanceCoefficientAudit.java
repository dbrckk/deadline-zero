package com.deadlinezero.game.meta;

/** Static guardrails for deterministic difficulty curves. Used by tests to catch accidental balance spikes. */
public final class BalanceCoefficientAudit {
    public static final int AUDIT_MAX_STAGE = 30;
    public static final float MAX_STAGE_HP_STEP = 1.24f;
    public static final float MAX_STAGE_DAMAGE_STEP = 1.14f;
    public static final float MAX_STAGE_REWARD_STEP = 1.18f;
    public static final float MAX_THREAT_HP_STEP = 1.14f;
    public static final float MAX_THREAT_DAMAGE_STEP = 1.08f;
    public static final float MAX_THREAT_REWARD_STEP = 1.10f;
    public static final float MIN_MAX_THREAT_REWARD_RATIO = 2.35f;
    public static final float MAX_MAX_THREAT_REWARD_RATIO = 2.65f;

    private BalanceCoefficientAudit() {}

    public static boolean stageCurvesHealthy() {
        RunModifierContext.end();
        for (int stage = 2; stage <= AUDIT_MAX_STAGE; stage++) {
            RunStageContext.begin(stage - 1, 0, 0);
            float previousHp = StageRules.enemyHpMultiplier(stage - 1);
            float previousDamage = StageRules.enemyDamageMultiplier(stage - 1);
            float previousReward = StageRules.rewardMultiplier(stage - 1);
            RunStageContext.begin(stage, 0, 0);
            float hp = StageRules.enemyHpMultiplier(stage);
            float damage = StageRules.enemyDamageMultiplier(stage);
            float reward = StageRules.rewardMultiplier(stage);
            if (!stepHealthy(previousHp, hp, MAX_STAGE_HP_STEP)
                || !stepHealthy(previousDamage, damage, MAX_STAGE_DAMAGE_STEP)
                || !stepHealthy(previousReward, reward, MAX_STAGE_REWARD_STEP)) return false;
        }
        return true;
    }

    public static boolean threatCurvesHealthy() {
        for (int tier = 1; tier <= ThreatTierRules.MAX_TIER; tier++) {
            if (!stepHealthy(ThreatTierRules.enemyHpMultiplier(tier - 1), ThreatTierRules.enemyHpMultiplier(tier), MAX_THREAT_HP_STEP)
                || !stepHealthy(ThreatTierRules.enemyDamageMultiplier(tier - 1), ThreatTierRules.enemyDamageMultiplier(tier), MAX_THREAT_DAMAGE_STEP)
                || !stepHealthy(ThreatTierRules.rewardMultiplier(tier - 1), ThreatTierRules.rewardMultiplier(tier), MAX_THREAT_REWARD_STEP)) return false;
        }
        float ratio = ThreatTierRules.rewardMultiplier(ThreatTierRules.MAX_TIER) / ThreatTierRules.rewardMultiplier(0);
        return ratio >= MIN_MAX_THREAT_REWARD_RATIO && ratio <= MAX_MAX_THREAT_REWARD_RATIO;
    }

    private static boolean stepHealthy(float previous, float current, float maxRatio) {
        if (!Float.isFinite(previous) || !Float.isFinite(current) || previous <= 0f || current < previous) return false;
        return current / previous <= maxRatio;
    }
}
