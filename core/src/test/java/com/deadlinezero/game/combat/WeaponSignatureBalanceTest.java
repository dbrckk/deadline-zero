package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class WeaponSignatureBalanceTest {
    @Test void ionNeedleSignatureStaysInsideControlledAverageDamageBudget() {
        assertAverageDamageMultiplier(WeaponCatalog.ION_NEEDLE, 1.18f, 1.26f);
    }

    @Test void cinderThermalCycleStaysInsideControlledAverageDamageBudget() {
        assertAverageDamageMultiplier(WeaponCatalog.CINDER_CANNON, 1.12f, 1.16f);
    }

    @Test void newEndgameSignaturesStayInsideControlledAverageDamageBudget() {
        assertAverageDamageMultiplier(WeaponCatalog.TEMPEST_BURST, 1.02f, 1.05f);
        assertAverageDamageMultiplier(WeaponCatalog.WHITEOUT_SHARD, 1.01f, 1.04f);
        assertAverageDamageMultiplier(WeaponCatalog.PHOENIX_REPEATER, 1.05f, 1.08f);
    }

    @Test void nonSignatureWeaponsNeverReceiveSignaturePower() {
        Set<String> signatureIds = Set.of(
            WeaponCatalog.ION_NEEDLE.id,
            WeaponCatalog.CINDER_CANNON.id,
            WeaponCatalog.TEMPEST_BURST.id,
            WeaponCatalog.WHITEOUT_SHARD.id,
            WeaponCatalog.PHOENIX_REPEATER.id
        );

        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            if (signatureIds.contains(weapon.id)) continue;
            WeaponSignatureRuntime.begin(weapon);
            for (int i = 0; i < 24; i++) {
                var modifier = WeaponSignatureRuntime.consumeShot(false);
                assertTrue(!modifier.active() && modifier.damageMultiplier() == 1f,
                    "unexpected signature on " + weapon.id);
            }
        }
    }

    private static void assertAverageDamageMultiplier(WeaponDefinition weapon, float min, float max) {
        WeaponSignatureRuntime.begin(weapon);
        float totalMultiplier = 0f;
        for (int i = 0; i < 120; i++) totalMultiplier += WeaponSignatureRuntime.consumeShot(false).damageMultiplier();
        float average = totalMultiplier / 120f;
        assertTrue(average >= min && average <= max,
            weapon.id + " signature average multiplier: " + average);
    }
}
