package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ThreatSetBonusRulesTest {
    @Test void setCountsOnlyEquippedExclusiveMilestoneItems() {
        PlayerProfile profile = new PlayerProfile();
        EquipmentItem helm = ThreatMilestoneRewardCatalog.forTier(5);
        EquipmentItem gloves = ThreatMilestoneRewardCatalog.forTier(10);
        EquipmentItem ordinary = new EquipmentItem("ordinary", "Ordinary Armor",
            PlayerProfile.EquipmentSlot.ARMOR, EquipmentItem.Rarity.MYTHIC, 20, .4f);
        profile.inventory.add(helm);
        profile.inventory.add(gloves);
        profile.inventory.add(ordinary);
        EquipmentService.equip(profile, helm.id);
        EquipmentService.equip(profile, gloves.id);
        EquipmentService.equip(profile, ordinary.id);
        assertEquals(2, ThreatSetBonusRules.equippedPieces(profile));
    }

    @Test void twoPieceBonusImprovesWeaponAndAbilityOnly() {
        assertEquals(1.08f, ThreatSetBonusRules.weaponMultiplier(2), .0001f);
        assertEquals(1.08f, ThreatSetBonusRules.abilityMultiplier(2), .0001f);
        assertEquals(1f, ThreatSetBonusRules.hpMultiplier(2), .0001f);
        assertEquals(1f, ThreatSetBonusRules.moveSpeedMultiplier(2), .0001f);
    }

    @Test void threePieceBonusAddsDurabilityAndMobility() {
        assertEquals(1.05f, ThreatSetBonusRules.hpMultiplier(3), .0001f);
        assertEquals(1.06f, ThreatSetBonusRules.moveSpeedMultiplier(3), .0001f);
        assertEquals(1f, ThreatSetBonusRules.damageTakenMultiplier(3), .0001f);
    }

    @Test void fourPieceBonusAddsDefenseAndDashWindow() {
        assertEquals(.90f, ThreatSetBonusRules.damageTakenMultiplier(4), .0001f);
        assertEquals(.06f, ThreatSetBonusRules.dashInvulnerabilityBonus(4), .0001f);
    }

    @Test void runLoadoutSnapshotsEquippedAscensionSet() {
        PlayerProfile profile = new PlayerProfile();
        for (int tier : new int[] {5, 10, 15, 20}) {
            EquipmentItem item = ThreatMilestoneRewardCatalog.forTier(tier);
            profile.inventory.add(item);
            EquipmentService.equip(profile, item.id);
        }
        RunLoadoutContext.begin(profile);
        assertEquals(4, RunLoadoutContext.ascensionSetPieces());
        assertTrue(RunLoadoutContext.weaponDamageMultiplier() > 1f);
        assertTrue(RunLoadoutContext.abilityPowerMultiplier() > 1f);
        assertTrue(RunLoadoutContext.damageTakenMultiplier() < 1f);
        assertTrue(RunLoadoutContext.dashInvulnerabilitySeconds() >= .36f);
    }
}
