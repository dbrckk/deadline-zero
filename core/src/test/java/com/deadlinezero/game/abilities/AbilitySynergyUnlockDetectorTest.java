package com.deadlinezero.game.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class AbilitySynergyUnlockDetectorTest {
    @Test void detectsNewSuperconductorAfterMaturingTesla() {
        AbilityLoadout a = new AbilityLoadout();
        for (int i = 0; i < 3; i++) a.upgrade(AbilityType.CRYO_NOVA);
        for (int i = 0; i < 2; i++) a.upgrade(AbilityType.TESLA_ORB);
        var before = AbilitySynergyUnlockDetector.snapshot(a);

        a.upgrade(AbilityType.TESLA_ORB);

        assertEquals(AbilitySynergyUnlockDetector.Synergy.SUPERCONDUCTOR,
            AbilitySynergyUnlockDetector.newlyActivated(before, a));
    }

    @Test void noEventWhenUpgradeDoesNotCreateSynergy() {
        AbilityLoadout a = new AbilityLoadout();
        var before = AbilitySynergyUnlockDetector.snapshot(a);
        a.upgrade(AbilityType.TESLA_ORB);
        assertEquals(AbilitySynergyUnlockDetector.Synergy.NONE,
            AbilitySynergyUnlockDetector.newlyActivated(before, a));
    }

    @Test void stormBladeHasPriorityWhenSeveralSynergiesAppearTogether() {
        AbilityLoadout a = new AbilityLoadout();
        for (int i = 0; i < 5; i++) a.upgrade(AbilityType.ORBITAL_BLADE);
        for (int i = 0; i < 4; i++) a.upgrade(AbilityType.TESLA_ORB);
        var before = AbilitySynergyUnlockDetector.snapshot(a);

        a.upgrade(AbilityType.TESLA_ORB);

        assertEquals(AbilitySynergyUnlockDetector.Synergy.STORM_BLADE,
            AbilitySynergyUnlockDetector.newlyActivated(before, a));
    }
}
