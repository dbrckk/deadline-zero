package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ThreatProgressionServiceTest {
    @Test void clearBeforeEndgameDoesNotUnlockThreat() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 9;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 9, 0);
        assertFalse(result.unlocked());
        assertEquals(0, profile.highestThreatTier);
    }

    @Test void clearingHighestThreatUnlocksExactlyOneTier() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 12;
        profile.highestThreatTier = 3;
        profile.selectedThreatTier = 3;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 3);
        assertTrue(result.unlocked());
        assertEquals(4, result.tier());
        assertEquals(4, profile.highestThreatTier);
        assertEquals(4, profile.selectedThreatTier);
    }

    @Test void lowerThreatClearCannotAdvanceHighestThreat() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 14;
        profile.highestThreatTier = 6;
        profile.selectedThreatTier = 3;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 12, 3);
        assertFalse(result.unlocked());
        assertEquals(6, profile.highestThreatTier);
    }

    @Test void milestoneGemsAreGrantedOnlyOnFirstUnlock() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 15;
        profile.highestThreatTier = 4;
        profile.selectedThreatTier = 4;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 4);
        assertTrue(result.unlocked());
        assertEquals(5, result.tier());
        assertEquals(6, result.milestoneGems());
        assertEquals(6L, profile.currency(PlayerProfile.Currency.GEMS));

        ThreatProgressionService.UnlockResult duplicate = ThreatProgressionService.applyBossClear(profile, 10, 4);
        assertFalse(duplicate.unlocked());
        assertEquals(6L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test void maxThreatCannotOverflow() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 30;
        profile.highestThreatTier = ThreatTierRules.MAX_TIER;
        profile.selectedThreatTier = ThreatTierRules.MAX_TIER;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 30, ThreatTierRules.MAX_TIER);
        assertFalse(result.unlocked());
        assertEquals(ThreatTierRules.MAX_TIER, profile.highestThreatTier);
    }
}
