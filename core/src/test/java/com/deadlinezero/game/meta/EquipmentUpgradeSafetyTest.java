package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EquipmentUpgradeSafetyTest {
    @Test void extremeLevelCostSaturatesInsteadOfOverflowingCheap() {
        EquipmentItem item = item(Integer.MAX_VALUE, .10f);
        assertEquals(Long.MAX_VALUE, EquipmentUpgradeService.cost(item));
    }

    @Test void maxLevelUpgradeDoesNotChargeOrWrapLevel() {
        PlayerProfile profile = fundedProfile();
        EquipmentItem item = item(Integer.MAX_VALUE, .10f);

        EquipmentItem result = EquipmentUpgradeService.upgrade(profile, item);

        assertSame(item, result);
        assertEquals(Long.MAX_VALUE, profile.currency(PlayerProfile.Currency.CREDITS));
    }

    @Test void nonRepresentablePowerUpgradeDoesNotChargeOrResetPower() {
        PlayerProfile profile = fundedProfile();
        EquipmentItem item = item(10, Float.MAX_VALUE);

        EquipmentItem result = EquipmentUpgradeService.upgrade(profile, item);

        assertSame(item, result);
        assertTrue(result.powerBonus > 0f);
        assertEquals(Long.MAX_VALUE, profile.currency(PlayerProfile.Currency.CREDITS));
    }

    private static PlayerProfile fundedProfile() {
        PlayerProfile profile = new PlayerProfile();
        profile.addCurrency(PlayerProfile.Currency.CREDITS, Long.MAX_VALUE);
        return profile;
    }

    private static EquipmentItem item(int level, float power) {
        return new EquipmentItem("safety", "Safety", PlayerProfile.EquipmentSlot.WEAPON,
            EquipmentItem.Rarity.MYTHIC, level, power);
    }
}
