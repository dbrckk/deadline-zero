package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.meta.SingularityCoreRuntime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class WeaponSignatureRuntimeTest {
    @BeforeEach void resetCore() { SingularityCoreRuntime.begin(false); }

    @Test void ionNeedleOverchargesExactlyEveryFifthProjectile() {
        WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
        for (int i = 1; i <= 10; i++) {
            var mark = WeaponSignatureRuntime.consumeShot(false);
            assertEquals(i % 5 == 0, mark.active());
            if (i % 5 == 0) {
                assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, mark.kind());
                assertTrue(mark.forceCritical());
                assertEquals(1, mark.penetrationBonus());
                assertTrue(mark.damageMultiplier() >= 2f);
            } else assertEquals(WeaponSignatureRuntime.Kind.NONE, mark.kind());
        }
    }

    @Test void cinderCannonThermalCycleTriggersEveryFourthShell() {
        WeaponSignatureRuntime.begin(WeaponCatalog.CINDER_CANNON);
        for (int i = 1; i <= 8; i++) {
            var mark = WeaponSignatureRuntime.consumeShot(false);
            assertEquals(i % 4 == 0, mark.active());
            if (i % 4 == 0) {
                assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, mark.kind());
                assertFalse(mark.forceCritical());
                assertEquals(1.55f, mark.damageMultiplier(), .0001f);
                assertEquals(.17f, mark.radius(), .0001f);
            } else assertEquals(WeaponSignatureRuntime.Kind.NONE, mark.kind());
        }
    }

    @Test void beginningANewRunResetsSignatureCadence() {
        WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
        for (int i = 0; i < 4; i++) assertFalse(WeaponSignatureRuntime.consumeShot(false).active());
        WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
        assertFalse(WeaponSignatureRuntime.consumeShot(false).active());
        assertEquals(1, WeaponSignatureRuntime.shotIndex());
    }

    @Test void ionOverchargeTransformsActualProjectile() {
        WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
        for (int i = 0; i < 4; i++) new Projectile().spawn(0, 0, 1, 0, 10f, false, 2, 1f, DamageElement.SHOCK);
        Projectile p = new Projectile().spawn(0, 0, 1, 0, 10f, false, 2, 1f, DamageElement.SHOCK);
        assertTrue(p.weaponSignature);
        assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, p.weaponSignatureKind);
        assertTrue(p.critical);
        assertEquals(3, p.penetrationRemaining);
        assertTrue(p.damage >= 20f);
        assertTrue(p.radius > .11f);
    }

    @Test void cinderThermalShellStacksWithNormalFireIdentity() {
        WeaponSignatureRuntime.begin(WeaponCatalog.CINDER_CANNON);
        for (int i = 0; i < 3; i++) new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 4f, DamageElement.FIRE);
        Projectile p = new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 4f, DamageElement.FIRE);
        assertTrue(p.weaponSignature);
        assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, p.weaponSignatureKind);
        assertEquals(DamageElement.FIRE, p.element);
        assertEquals(31f, p.damage, .0001f);
        assertEquals(2, p.penetrationRemaining);
        assertEquals(.17f, p.radius, .0001f);
        assertTrue(p.knockback > 4f);
    }
}
