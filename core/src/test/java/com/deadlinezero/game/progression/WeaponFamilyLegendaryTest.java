package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.RunLoadoutContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class WeaponFamilyLegendaryTest {
    @AfterEach void resetLoadout() { RunLoadoutContext.begin(null); }

    @Test void ionCascadeOnlyAppearsForIonNeedleAndAcceleratesSignatureCadence() {
        Player p = playerWith("ion_needle");
        p.level = 12;
        assertTrue(LegendaryChoice.ION_CASCADE.eligible(p));
        assertFalse(LegendaryChoice.CINDER_FURNACE.eligible(p));
        assertTrue(LegendaryChoice.ION_CASCADE.apply(p));
        assertTrue(p.legendary.hasIonCascade());
        assertTrue(WeaponSignatureRuntime.ionCascadeEnabled());
        for (int i = 1; i <= 4; i++) {
            var shot = WeaponSignatureRuntime.consumeShot(false);
            assertEquals(i == 4, shot.active());
            if (i == 4) {
                assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, shot.kind());
                assertEquals(2, shot.penetrationBonus());
                assertTrue(shot.radius() >= .15f);
            }
        }
        assertFalse(LegendaryChoice.ION_CASCADE.eligible(p));
    }

    @Test void cinderFurnaceCyclesEveryThirdShellAndStrengthensPayload() {
        Player p = playerWith("cinder_cannon");
        p.level = 12;
        assertTrue(LegendaryChoice.CINDER_FURNACE.apply(p));
        assertTrue(WeaponSignatureRuntime.cinderFurnaceEnabled());
        for (int i = 1; i <= 3; i++) {
            var shot = WeaponSignatureRuntime.consumeShot(false);
            assertEquals(i == 3, shot.active());
            if (i == 3) {
                assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, shot.kind());
                assertEquals(1.72f, shot.damageMultiplier(), .0001f);
                assertEquals(2, shot.penetrationBonus());
                assertEquals(.21f, shot.radius(), .0001f);
            }
        }
    }

    @Test void railPhaseLanceTurnsPrecisionWeaponIntoExtremePiercer() {
        Player p = playerWith("rail_rifle");
        p.level = 12;
        float damage = p.weapon.damage;
        float speed = p.weapon.projectileSpeed;
        int penetration = p.weapon.penetration;
        float crit = p.weapon.critChance;
        assertTrue(LegendaryChoice.RAIL_PHASE_LANCE.apply(p));
        assertEquals(damage * 1.22f, p.weapon.damage, .0001f);
        assertEquals(speed * 1.12f, p.weapon.projectileSpeed, .0001f);
        assertEquals(penetration + 2, p.weapon.penetration);
        assertEquals(crit + .08f, p.weapon.critChance, .0001f);
    }

    @Test void cryoPrismCreatesThreeControlledFrostLances() {
        Player p = playerWith("cryo_lance");
        p.level = 12;
        float damage = p.weapon.damage;
        int penetration = p.weapon.penetration;
        assertTrue(LegendaryChoice.CRYO_PRISM.apply(p));
        assertEquals(3, p.weapon.projectileCount);
        assertEquals(damage * .48f, p.weapon.damage, .0001f);
        assertEquals(penetration + 1, p.weapon.penetration);
        assertTrue(p.weapon.spreadDegrees >= 5f);
    }

    @Test void arcOverloadDoublesShockVectorsWithoutRunawayPaperDamage() {
        Player p = playerWith("arc_carbine");
        p.level = 12;
        float damage = p.weapon.damage;
        assertTrue(LegendaryChoice.ARC_OVERLOAD.apply(p));
        assertEquals(2, p.weapon.projectileCount);
        assertEquals(damage * .68f, p.weapon.damage, .0001f);
        assertEquals(2, p.weapon.penetration);
        float totalVolley = p.weapon.damage * p.weapon.projectileCount;
        assertTrue(totalVolley >= damage * 1.30f && totalVolley <= damage * 1.40f);
    }

    @Test void incompatibleWeaponSpecificChoicesNeverEnterOffers() {
        Player p = playerWith("ar9");
        p.level = 20;
        LegendaryChoice[] out = new LegendaryChoice[8];
        int count = LegendarySelector.fillChoices(p, out);
        assertEquals(4, count);
        boolean sawVanguard = false;
        for (int i = 0; i < count; i++) {
            LegendaryChoice choice = out[i];
            assertTrue(choice == LegendaryChoice.OVERDRIVE
                || choice == LegendaryChoice.SINGULARITY
                || choice == LegendaryChoice.APEX
                || choice == LegendaryChoice.VANGUARD_PROTOCOL,
                "Unexpected AR-9 legendary offer: " + choice);
            sawVanguard |= choice == LegendaryChoice.VANGUARD_PROTOCOL;
        }
        assertTrue(sawVanguard);
    }

    @Test void ownedGeneralLegendariesStillAllowCompatibleFamilyOffer() {
        Player p = playerWith("rail_rifle");
        p.level = 20;
        assertTrue(LegendaryEffects.applyOverdrive(p));
        assertTrue(LegendaryEffects.applySingularity(p));
        assertTrue(LegendaryEffects.applyApex(p));
        assertTrue(LegendarySelector.shouldOffer(p));
        LegendaryChoice[] out = new LegendaryChoice[3];
        assertEquals(1, LegendarySelector.fillChoices(p, out));
        assertEquals(LegendaryChoice.RAIL_PHASE_LANCE, out[0]);
    }

    private static Player playerWith(String weaponId) {
        PlayerProfile profile = new PlayerProfile();
        profile.selectedWeaponId = weaponId;
        RunLoadoutContext.begin(profile);
        return new Player(0f, 0f);
    }
}
