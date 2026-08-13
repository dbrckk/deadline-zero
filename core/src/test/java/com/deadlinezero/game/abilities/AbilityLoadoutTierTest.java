package com.deadlinezero.game.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class AbilityLoadoutTierTest {
    @Test public void tiersAdvanceAtLevelsThreeAndFive() {
        AbilityLoadout loadout = new AbilityLoadout();
        assertEquals(0, loadout.tier(AbilityType.TESLA_ORB));
        loadout.upgrade(AbilityType.TESLA_ORB);
        assertEquals(1, loadout.tier(AbilityType.TESLA_ORB));
        loadout.upgrade(AbilityType.TESLA_ORB);
        loadout.upgrade(AbilityType.TESLA_ORB);
        assertEquals(2, loadout.tier(AbilityType.TESLA_ORB));
        loadout.upgrade(AbilityType.TESLA_ORB);
        loadout.upgrade(AbilityType.TESLA_ORB);
        assertEquals(3, loadout.tier(AbilityType.TESLA_ORB));
        assertTrue(loadout.evolved(AbilityType.TESLA_ORB));
    }

    @Test public void levelsRemainCappedAtFive() {
        AbilityLoadout loadout = new AbilityLoadout();
        for (int i = 0; i < 20; i++) loadout.upgrade(AbilityType.DRONE);
        assertEquals(AbilityLoadout.MAX_LEVEL, loadout.level(AbilityType.DRONE));
    }

    @Test public void superconductorRequiresMatureTeslaAndCryo() {
        AbilityLoadout loadout = new AbilityLoadout();
        for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.TESLA_ORB);
        assertFalse(loadout.hasSuperconductorSynergy());
        for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.CRYO_NOVA);
        assertTrue(loadout.hasSuperconductorSynergy());
    }

    @Test public void stormBladeRequiresBothEvolutions() {
        AbilityLoadout loadout = new AbilityLoadout();
        for (int i = 0; i < 5; i++) loadout.upgrade(AbilityType.ORBITAL_BLADE);
        for (int i = 0; i < 4; i++) loadout.upgrade(AbilityType.TESLA_ORB);
        assertFalse(loadout.hasStormBladeSynergy());
        loadout.upgrade(AbilityType.TESLA_ORB);
        assertTrue(loadout.hasStormBladeSynergy());
    }
}
