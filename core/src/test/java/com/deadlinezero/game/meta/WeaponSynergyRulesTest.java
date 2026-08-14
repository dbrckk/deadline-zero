package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.combat.WeaponCatalog;
import org.junit.jupiter.api.Test;

final class WeaponSynergyRulesTest {
    @Test void resolvesOnlyIntentionalSignaturePairs() {
        assertEquals(WeaponSynergyRules.Synergy.ARC_CONDUCTOR,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.VOLT, WeaponCatalog.ION_NEEDLE));
        assertEquals(WeaponSynergyRules.Synergy.EXECUTION_PROTOCOL,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.NYX, WeaponCatalog.ION_NEEDLE));
        assertEquals(WeaponSynergyRules.Synergy.SIEGE_FURNACE,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.BASTION, WeaponCatalog.CINDER_CANNON));
        assertEquals(WeaponSynergyRules.Synergy.CRYO_GHOST,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.WRAITH, WeaponCatalog.CRYO_LANCE));
        assertEquals(WeaponSynergyRules.Synergy.NONE,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.REX, WeaponCatalog.ION_NEEDLE));
        assertEquals(WeaponSynergyRules.Synergy.NONE,
            WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.VOLT, WeaponCatalog.AR9));
    }

    @Test void synergyBonusesStayInsideSafePowerBudget() {
        for (WeaponSynergyRules.Synergy synergy : WeaponSynergyRules.Synergy.values()) {
            assertTrue(synergy.weaponDamageMultiplier >= 1f && synergy.weaponDamageMultiplier <= 1.12f);
            assertTrue(synergy.abilityPowerMultiplier >= 1f && synergy.abilityPowerMultiplier <= 1.10f);
            assertTrue(synergy.critChanceBonus >= 0f && synergy.critChanceBonus <= .06f);
            assertTrue(synergy.damageTakenMultiplier >= .95f && synergy.damageTakenMultiplier <= 1f);
        }
    }

    @Test void nullInputsFallBackWithoutGrantingFreeSynergy() {
        assertEquals(WeaponSynergyRules.Synergy.NONE, WeaponSynergyRules.resolve(null, null));
    }
}
