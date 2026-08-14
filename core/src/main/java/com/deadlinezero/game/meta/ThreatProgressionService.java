package com.deadlinezero.game.meta;

/** Applies one successful endgame clear to persistent threat progression. */
public final class ThreatProgressionService {
    public record UnlockResult(boolean unlocked, int tier, int milestoneGems) {
        public static UnlockResult none() { return new UnlockResult(false, 0, 0); }
    }

    private ThreatProgressionService() {}

    public static UnlockResult applyBossClear(PlayerProfile profile, int stage, int clearedThreatTier) {
        if (profile == null || stage < ThreatTierRules.UNLOCK_STAGE) return UnlockResult.none();
        int safeTier = ThreatTierRules.sanitizeTier(clearedThreatTier);
        if (safeTier != profile.highestThreatTier) return UnlockResult.none();
        if (!profile.unlockNextThreatTier()) return UnlockResult.none();

        int unlockedTier = profile.highestThreatTier;
        int milestoneGems = ThreatTierRules.milestoneGemReward(unlockedTier);
        if (milestoneGems > 0) profile.addCurrency(PlayerProfile.Currency.GEMS, milestoneGems);

        EquipmentItem milestoneGear = ThreatMilestoneRewardCatalog.forTier(unlockedTier);
        if (milestoneGear != null && profile.inventory.find(milestoneGear.id) == null) {
            profile.inventory.addExclusive(milestoneGear);
        }
        return new UnlockResult(true, unlockedTier, milestoneGems);
    }
}
