package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

final class PlayerProjectilePresentationTest {
    @Test void allTwelveWeaponFamiliesHaveDistinctPresentationStyles() {
        EnumSet<PlayerProjectilePresentation.Style> styles =
            EnumSet.noneOf(PlayerProjectilePresentation.Style.class);

        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            PlayerProjectilePresentation.Profile profile = PlayerProjectilePresentation.forWeapon(
                weapon.id, weapon.element, false, WeaponSignatureRuntime.Kind.NONE);
            styles.add(profile.style());
            assertSane(profile);
        }

        assertEquals(12, styles.size());
    }

    @Test void allFiveSignatureShotsEscalateTheirExpectedFamily() {
        assertSignature("ion_needle", DamageElement.SHOCK, WeaponSignatureRuntime.Kind.ION_OVERCHARGE,
            PlayerProjectilePresentation.Style.ION);
        assertSignature("cinder_cannon", DamageElement.FIRE, WeaponSignatureRuntime.Kind.CINDER_OVERHEAT,
            PlayerProjectilePresentation.Style.CINDER);
        assertSignature("tempest_burst", DamageElement.SHOCK, WeaponSignatureRuntime.Kind.TEMPEST_SURGE,
            PlayerProjectilePresentation.Style.TEMPEST);
        assertSignature("whiteout_shard", DamageElement.FROST, WeaponSignatureRuntime.Kind.WHITEOUT_SHATTER,
            PlayerProjectilePresentation.Style.WHITEOUT);
        assertSignature("phoenix_repeater", DamageElement.FIRE, WeaponSignatureRuntime.Kind.PHOENIX_IGNITION,
            PlayerProjectilePresentation.Style.PHOENIX);
    }

    @Test void criticalPresentationNeverShrinksItsBaseProfile() {
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            PlayerProjectilePresentation.Profile base = PlayerProjectilePresentation.forWeapon(
                weapon.id, weapon.element, false, WeaponSignatureRuntime.Kind.NONE);
            PlayerProjectilePresentation.Profile crit = PlayerProjectilePresentation.forWeapon(
                weapon.id, weapon.element, true, WeaponSignatureRuntime.Kind.NONE);
            assertTrue(crit.coreScale() >= base.coreScale());
            assertTrue(crit.impactScale() >= base.impactScale());
            assertTrue(crit.alpha() >= base.alpha());
        }
    }

    private static void assertSignature(String weaponId, DamageElement element,
                                        WeaponSignatureRuntime.Kind kind,
                                        PlayerProjectilePresentation.Style expectedStyle) {
        PlayerProjectilePresentation.Profile base = PlayerProjectilePresentation.forWeapon(
            weaponId, element, false, WeaponSignatureRuntime.Kind.NONE);
        PlayerProjectilePresentation.Profile signature = PlayerProjectilePresentation.forWeapon(
            weaponId, element, false, kind);
        assertEquals(expectedStyle, signature.style());
        assertTrue(signature.signature());
        assertTrue(signature.trailLength() >= base.trailLength());
        assertTrue(signature.coreScale() >= base.coreScale());
        assertTrue(signature.impactScale() >= base.impactScale());
        assertSane(signature);
    }

    private static void assertSane(PlayerProjectilePresentation.Profile profile) {
        assertTrue(profile.trailLength() > 0f);
        assertTrue(profile.trailWidth() > 0f);
        assertTrue(profile.alpha() > 0f && profile.alpha() <= 1f);
        assertTrue(profile.coreScale() > 0f);
        assertTrue(profile.impactScale() > 0f);
    }
}
