package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class ThreatTierRulesTest {
    @AfterEach void cleanup() {
        RunModifierContext.end();
        RunStageContext.begin(1, 0, 0);
    }

    @Test void threatRemainsLockedBeforeStageTen() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 9;
        profile.highestThreatTier = 4;
        profile.selectedThreatTier = 3;
        profile.normalizeLoadedState();
        assertFalse(ThreatTierRules.unlocked(profile));
        assertEquals(0, profile.highestThreatTier);
        assertEquals(0, profile.selectedThreatTier);
        assertFalse(profile.selectThreatTier(1));
    }

    @Test void unlockedProfileCanSelectOnlyEarnedThreat() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 10;
        profile.highestThreatTier = 4;
        profile.normalizeLoadedState();
        assertTrue(ThreatTierRules.unlocked(profile));
        assertTrue(profile.selectThreatTier(4));
        assertEquals(4, profile.selectedThreatTier);
        assertFalse(profile.selectThreatTier(5));
        assertEquals(4, profile.selectedThreatTier);
    }

    @Test void nextThreatUnlocksSequentiallyAndCapsAtTwenty() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 12;
        assertTrue(profile.unlockNextThreatTier());
        assertEquals(1, profile.highestThreatTier);
        assertEquals(1, profile.selectedThreatTier);
        profile.highestThreatTier = ThreatTierRules.MAX_TIER;
        profile.selectedThreatTier = ThreatTierRules.MAX_TIER;
        assertFalse(profile.unlockNextThreatTier());
        assertEquals(ThreatTierRules.MAX_TIER, profile.highestThreatTier);
    }

    @Test void threatTierRaisesCombatAndRewardScaling() {
        RunStageContext.begin(10, 0, 0);
        float hp0 = StageRules.enemyHpMultiplier(10);
        float damage0 = StageRules.enemyDamageMultiplier(10);
        float speed0 = StageRules.enemySpeedMultiplier(10);
        float reward0 = StageRules.rewardMultiplier(10);

        RunStageContext.begin(10, 0, 8);
        assertTrue(StageRules.enemyHpMultiplier(10) > hp0);
        assertTrue(StageRules.enemyDamageMultiplier(10) > damage0);
        assertTrue(StageRules.enemySpeedMultiplier(10) > speed0);
        assertTrue(StageRules.rewardMultiplier(10) > reward0);
    }

    @Test void milestoneGemsAreOnlyPaidEveryFiveTiers() {
        assertEquals(0, ThreatTierRules.milestoneGemReward(4));
        assertEquals(6, ThreatTierRules.milestoneGemReward(5));
        assertEquals(8, ThreatTierRules.milestoneGemReward(10));
        assertEquals(12, ThreatTierRules.milestoneGemReward(20));
    }

    @Test void runSeedIncludesThreatTier() {
        RunStageContext.begin(12, 33, 0);
        int base = RunStageContext.encounterSeed();
        RunStageContext.begin(12, 33, 1);
        int ascended = RunStageContext.encounterSeed();
        assertTrue(base != ascended);
    }
}
