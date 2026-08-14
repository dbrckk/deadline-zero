package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test void milestoneGemsAndMythicGearAreGrantedOnlyOnFirstUnlock() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 15;
        profile.highestThreatTier = 4;
        profile.selectedThreatTier = 4;
        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 4);
        assertTrue(result.unlocked());
        assertEquals(5, result.tier());
        assertEquals(6, result.milestoneGems());
        assertEquals(6L, profile.currency(PlayerProfile.Currency.GEMS));

        EquipmentItem reward = profile.inventory.find("threat_05_helmet");
        assertNotNull(reward);
        assertEquals(EquipmentItem.Rarity.MYTHIC, reward.rarity);
        assertEquals(PlayerProfile.EquipmentSlot.HELMET, reward.slot);

        int itemCount = profile.inventory.size();
        ThreatProgressionService.UnlockResult duplicate = ThreatProgressionService.applyBossClear(profile, 10, 4);
        assertFalse(duplicate.unlocked());
        assertEquals(6L, profile.currency(PlayerProfile.Currency.GEMS));
        assertEquals(itemCount, profile.inventory.size());
    }

    @Test void milestoneGearUsesReservedCapacityWhenNormalInventoryIsFull() {
        PlayerProfile profile = new PlayerProfile();
        profile.highestStage = 15;
        profile.highestThreatTier = 4;
        for (int i = 0; i < Inventory.NORMAL_CAPACITY; i++) {
            assertTrue(profile.inventory.add(new EquipmentItem("normal_" + i, "Normal " + i,
                PlayerProfile.EquipmentSlot.BOOTS, EquipmentItem.Rarity.COMMON, 1, .001f)));
        }
        assertTrue(profile.inventory.full());

        ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 4);
        assertTrue(result.unlocked());
        assertNotNull(profile.inventory.find("threat_05_helmet"));
        assertEquals(Inventory.NORMAL_CAPACITY + 1, profile.inventory.size());
        assertTrue(profile.inventory.full());
    }

    @Test void allMilestoneRewardsAreUniqueAndMappedToExpectedTiers() {
        String[] ids = new String[4];
        int[] tiers = {5, 10, 15, 20};
        for (int i = 0; i < tiers.length; i++) {
            EquipmentItem item = ThreatMilestoneRewardCatalog.forTier(tiers[i]);
            assertNotNull(item);
            assertEquals(EquipmentItem.Rarity.MYTHIC, item.rarity);
            ids[i] = item.id;
        }
        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) assertFalse(ids[i].equals(ids[j]));
        }
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
