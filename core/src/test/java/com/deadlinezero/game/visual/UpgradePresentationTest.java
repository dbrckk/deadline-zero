package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.progression.Upgrade;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

final class UpgradePresentationTest {
    @Test void everyUpgradeHasAVisualArchetype() {
        for (Upgrade upgrade : Upgrade.values()) {
            assertNotNull(UpgradePresentation.archetype(upgrade), upgrade.name());
        }
        assertEquals(60, Upgrade.values().length);
    }

    @Test void allSixteenVisualArchetypesAreActuallyUsed() {
        EnumSet<UpgradePresentation.Archetype> seen =
            EnumSet.noneOf(UpgradePresentation.Archetype.class);
        for (Upgrade upgrade : Upgrade.values()) seen.add(UpgradePresentation.archetype(upgrade));
        assertEquals(EnumSet.allOf(UpgradePresentation.Archetype.class), seen);
    }

    @Test void signatureAbilityFamiliesRemainSemanticallyDistinct() {
        assertEquals(UpgradePresentation.Archetype.SHOCK,
            UpgradePresentation.archetype(Upgrade.TESLA_ORB));
        assertEquals(UpgradePresentation.Archetype.MISSILE,
            UpgradePresentation.archetype(Upgrade.MISSILE_SWARM));
        assertEquals(UpgradePresentation.Archetype.FROST,
            UpgradePresentation.archetype(Upgrade.CRYO_NOVA));
        assertEquals(UpgradePresentation.Archetype.DRONE,
            UpgradePresentation.archetype(Upgrade.DRONE));
        assertEquals(UpgradePresentation.Archetype.ORBITAL,
            UpgradePresentation.archetype(Upgrade.ORBITAL));
        assertEquals(UpgradePresentation.Archetype.PROTOCOL,
            UpgradePresentation.archetype(Upgrade.REACTION_CASCADE));
    }

    @Test void nullUpgradeFallsBackToDamageInsteadOfCrashing() {
        assertEquals(UpgradePresentation.Archetype.DAMAGE,
            UpgradePresentation.archetype(null));
        assertTrue(UpgradePresentation.Archetype.values().length >= 16);
    }
}
