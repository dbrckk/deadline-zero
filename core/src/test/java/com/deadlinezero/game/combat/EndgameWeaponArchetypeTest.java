package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EndgameWeaponArchetypeTest {
    @Test void ionNeedleIsPrecisionShockPiercer() {
        WeaponDefinition weapon = WeaponCatalog.ION_NEEDLE;
        assertEquals(DamageElement.SHOCK, weapon.element);
        assertTrue(weapon.fireInterval < .10f);
        assertTrue(weapon.critChance >= .20f);
        assertTrue(weapon.penetration >= 2);
        assertTrue(weapon.spreadDegrees < 1f);
    }

    @Test void cinderCannonIsSlowHeavyFireWeapon() {
        WeaponDefinition weapon = WeaponCatalog.CINDER_CANNON;
        assertEquals(DamageElement.FIRE, weapon.element);
        assertTrue(weapon.damage >= 80f);
        assertTrue(weapon.fireInterval >= 1f);
        assertTrue(weapon.knockback >= 4f);
        assertTrue(weapon.penetration >= 1);
    }

    @Test void newWeaponsStayInsideGlobalPaperDpsBand() {
        assertTrue(WeaponCatalog.paperDps(WeaponCatalog.ION_NEEDLE) <= 230f);
        assertTrue(WeaponCatalog.paperDps(WeaponCatalog.ION_NEEDLE) >= 45f);
        assertTrue(WeaponCatalog.paperDps(WeaponCatalog.CINDER_CANNON) <= 230f);
        assertTrue(WeaponCatalog.paperDps(WeaponCatalog.CINDER_CANNON) >= 45f);
    }
}
