package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class WeaponSignatureBalanceTest {
    @Test void ionNeedleSignatureStaysInsideControlledAverageDamageBudget() {
        WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
        float totalMultiplier = 0f;
        for (int i = 0; i < 100; i++) totalMultiplier += WeaponSignatureRuntime.consumeShot(false).damageMultiplier();
        float average = totalMultiplier / 100f;
        assertTrue(average >= 1.18f && average <= 1.26f, "ion signature average multiplier: " + average);
    }

    @Test void cinderThermalCycleStaysInsideControlledAverageDamageBudget() {
        WeaponSignatureRuntime.begin(WeaponCatalog.CINDER_CANNON);
        float totalMultiplier = 0f;
        for (int i = 0; i < 100; i++) totalMultiplier += WeaponSignatureRuntime.consumeShot(false).damageMultiplier();
        float average = totalMultiplier / 100f;
        assertTrue(average >= 1.12f && average <= 1.16f, "cinder signature average multiplier: " + average);
    }

    @Test void starterAndMidgameWeaponsNeverReceiveLateGameSignaturePower() {
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            if (weapon == WeaponCatalog.ION_NEEDLE || weapon == WeaponCatalog.CINDER_CANNON) continue;
            WeaponSignatureRuntime.begin(weapon);
            for (int i = 0; i < 24; i++) {
                var modifier = WeaponSignatureRuntime.consumeShot(false);
                assertTrue(!modifier.active() && modifier.damageMultiplier() == 1f,
                    "unexpected signature on " + weapon.id);
            }
        }
    }
}
