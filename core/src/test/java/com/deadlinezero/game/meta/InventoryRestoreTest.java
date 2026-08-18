package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class InventoryRestoreTest {
    @Test void persistedExclusiveGearSurvivesAFullNormalInventory() {
        Inventory inventory = new Inventory();
        for (int i = 0; i < Inventory.NORMAL_CAPACITY; i++) {
            assertTrue(inventory.restore(normal("normal_" + i)));
        }

        EquipmentItem exclusive = ThreatMilestoneRewardCatalog.forTier(5);
        assertTrue(inventory.restore(exclusive));
        assertNotNull(inventory.find(exclusive.id));
        assertEquals(Inventory.NORMAL_CAPACITY + 1, inventory.size());
    }

    @Test void restoreStillRejectsOverflowAndDuplicateExclusiveGear() {
        Inventory inventory = new Inventory();
        for (int i = 0; i < Inventory.NORMAL_CAPACITY; i++) assertTrue(inventory.restore(normal("normal_" + i)));
        int[] tiers = {5, 10, 15, 20};
        for (int tier : tiers) assertTrue(inventory.restore(ThreatMilestoneRewardCatalog.forTier(tier)));

        assertFalse(inventory.restore(ThreatMilestoneRewardCatalog.forTier(5)));
        assertFalse(inventory.restore(normal("overflow")));
        assertEquals(Inventory.MAX_ITEMS, inventory.size());
    }

    private static EquipmentItem normal(String id) {
        return new EquipmentItem(id, id, PlayerProfile.EquipmentSlot.ARMOR,
            EquipmentItem.Rarity.COMMON, 1, .01f);
    }
}
