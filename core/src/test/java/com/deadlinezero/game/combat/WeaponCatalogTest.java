package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class WeaponCatalogTest {
    @Test
    void rosterHasDistinctStableIds() {
        WeaponDefinition[] all = WeaponCatalog.all();
        assertTrue(all.length >= 7);
        Set<String> ids = new HashSet<>();
        for (WeaponDefinition weapon : all) {
            assertNotNull(weapon);
            assertTrue(ids.add(weapon.id), "duplicate weapon id: " + weapon.id);
            assertEquals(weapon, WeaponCatalog.byId(weapon.id));
        }
    }

    @Test
    void everyWeaponHasSafeRuntimeParameters() {
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            assertTrue(weapon.damage > 0f);
            assertTrue(weapon.fireInterval >= .04f);
            assertTrue(weapon.projectileSpeed >= 10f);
            assertTrue(weapon.projectileCount >= 1 && weapon.projectileCount <= 12);
            assertTrue(weapon.spreadDegrees >= 0f && weapon.spreadDegrees <= 16f);
            assertTrue(weapon.critChance >= 0f && weapon.critChance <= .35f);
            assertTrue(weapon.critMultiplier >= 1f && weapon.critMultiplier <= 3f);
            assertTrue(weapon.penetration >= 0 && weapon.penetration <= 6);
            assertTrue(weapon.knockback >= 0f && weapon.knockback <= 5f);
            assertNotNull(weapon.element);
        }
    }

    @Test
    void rawPaperDpsStaysWithinIntentionalBand() {
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            float dps = WeaponCatalog.paperDps(weapon);
            assertTrue(dps >= 45f, weapon.id + " paper DPS too low: " + dps);
            assertTrue(dps <= 230f, weapon.id + " paper DPS too high: " + dps);
        }
    }

    @Test
    void elementalRosterCoversFireFrostAndShock() {
        boolean fire = false, frost = false, shock = false;
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            fire |= weapon.element == DamageElement.FIRE;
            frost |= weapon.element == DamageElement.FROST;
            shock |= weapon.element == DamageElement.SHOCK;
        }
        assertTrue(fire && frost && shock);
    }

    @Test
    void unknownIdsFallBackSafelyToStarterWeapon() {
        assertEquals(WeaponCatalog.AR9, WeaponCatalog.byId(null));
        assertEquals(WeaponCatalog.AR9, WeaponCatalog.byId("missing_weapon"));
    }
}
