package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.RunLoadoutContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class WeaponFamilyLegendaryBalanceTest {
    @AfterEach void resetLoadout() { RunLoadoutContext.begin(null); }

    @Test void directWeaponTransformationsStayInsideIntentionalPowerBands() {
        assertVolleyBand("rail_rifle", LegendaryChoice.RAIL_PHASE_LANCE, 1.18f, 1.28f);
        assertVolleyBand("cryo_lance", LegendaryChoice.CRYO_PRISM, 1.40f, 1.48f);
        assertVolleyBand("arc_carbine", LegendaryChoice.ARC_OVERLOAD, 1.32f, 1.40f);
    }

    @Test void cinderFurnaceOnlyAddsModestBaselineCadenceBeforeSignaturePayload() {
        Player p = playerWith("cinder_cannon");
        float before = p.weapon.fireInterval;
        assertTrue(LegendaryChoice.CINDER_FURNACE.apply(p));
        float cadenceGain = before / p.weapon.fireInterval;
        assertTrue(cadenceGain >= 1.05f && cadenceGain <= 1.08f);
    }

    @Test void ionCascadeKeepsBaselineWeaponDamageStable() {
        Player p = playerWith("ion_needle");
        float before = p.weapon.damage;
        assertTrue(LegendaryChoice.ION_CASCADE.apply(p));
        assertTrue(Math.abs(p.weapon.damage / before - 1f) < .0001f);
    }

    private static void assertVolleyBand(String weaponId, LegendaryChoice choice, float min, float max) {
        Player p = playerWith(weaponId);
        float before = p.weapon.damage * p.weapon.projectileCount;
        assertTrue(choice.apply(p));
        float after = p.weapon.damage * p.weapon.projectileCount;
        float ratio = after / before;
        assertTrue(ratio >= min && ratio <= max, weaponId + " volley ratio out of band: " + ratio);
    }

    private static Player playerWith(String weaponId) {
        PlayerProfile profile = new PlayerProfile();
        profile.selectedWeaponId = weaponId;
        RunLoadoutContext.begin(profile);
        Player player = new Player(0f, 0f);
        player.level = 20;
        return player;
    }
}
