package com.deadlinezero.game.meta;

/** Persistent endgame difficulty ladder layered on top of stage progression. */
public final class ThreatTierRules {
    public static final int UNLOCK_STAGE = 10;
    public static final int MAX_TIER = 20;

    private ThreatTierRules() {}

    public static boolean unlocked(PlayerProfile profile) {
        return profile != null && profile.highestStage >= UNLOCK_STAGE;
    }

    public static int sanitizeTier(int tier) {
        return Math.min(MAX_TIER, Math.max(0, tier));
    }

    public static float enemyHpMultiplier(int tier) {
        int t = sanitizeTier(tier);
        return 1f + t * .10f + t * t * .0025f;
    }

    public static float enemyDamageMultiplier(int tier) {
        return 1f + sanitizeTier(tier) * .055f;
    }

    public static float enemySpeedMultiplier(int tier) {
        return Math.min(1.28f, 1f + sanitizeTier(tier) * .014f);
    }

    public static float spawnIntervalMultiplier(int tier) {
        return Math.max(.72f, 1f - sanitizeTier(tier) * .014f);
    }

    public static float rewardMultiplier(int tier) {
        int t = sanitizeTier(tier);
        return 1f + t * .075f;
    }

    public static int rewardBonusPercent(int tier) {
        return Math.round((rewardMultiplier(tier) - 1f) * 100f);
    }

    /** One-time premium reward when a 5-tier ascension milestone is first unlocked. */
    public static int milestoneGemReward(int newlyUnlockedTier) {
        int tier = sanitizeTier(newlyUnlockedTier);
        if (tier <= 0 || tier % 5 != 0) return 0;
        return 4 + tier / 5 * 2;
    }
}
