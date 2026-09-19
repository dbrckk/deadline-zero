package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;
import org.junit.jupiter.api.Test;

final class UpgradeDraftPolicyTest {
    private Player freshPlayer() {
        RunLoadoutContext.end();
        return new Player(0f, 0f);
    }

    @Test void kineticFreshRunKeepsBroadDraftPool() {
        Player player = freshPlayer();
        assertFalse(UpgradeDraftPolicy.hasEstablishedBuild(player));
        assertEquals(1f, UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.FIRE_CONTROL), .0001f);
        assertEquals(1f, UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.TESLA_ORB), .0001f);
    }

    @Test void elementalBuildStronglyPrefersMatchingFamilyAndDeemphasizesConflicts() {
        Player player = freshPlayer();
        player.weapon.element = DamageElement.FIRE;

        assertTrue(UpgradeDraftPolicy.hasEstablishedBuild(player));
        assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.FIRE_CONTROL) >= 2f);
        assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.THERMAL_LANCE));
        assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.CRYO_HAMMER) < 1f);
        assertFalse(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.CRYO_HAMMER));
    }

    @Test void investedAbilityPrefersItsOwnTreeAndKnownSynergyPartners() {
        Player player = freshPlayer();
        player.abilities.upgrade(AbilityType.TESLA_ORB);
        player.abilities.upgrade(AbilityType.TESLA_ORB);

        assertTrue(UpgradeDraftPolicy.hasEstablishedBuild(player));
        assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.TESLA_ORB));
        assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.CRYO_NOVA));
        assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.DRONE));
        assertFalse(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.MISSILE_SWARM));
    }

    @Test void maturePartnerRaisesSynergyOfferWeight() {
        Player player = freshPlayer();
        player.abilities.upgrade(AbilityType.CRYO_NOVA);
        player.abilities.upgrade(AbilityType.CRYO_NOVA);
        player.abilities.upgrade(AbilityType.CRYO_NOVA);

        assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.MISSILE_SWARM) >= 2f);
        assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.ORBITAL) >= 2f);
    }

    @Test void protocolEvolutionBecomesFocusedBuildPathChoice() {
        Player player = freshPlayer();
        player.protocols.enableRhythm();

        assertTrue(UpgradeDraftPolicy.hasEstablishedBuild(player));
        assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.RHYTHM_ACCELERATOR));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
    }

    @Test void establishedElementAlwaysGetsOneRelevantDraftSlot() {
        Player player = freshPlayer();
        player.weapon.element = DamageElement.FIRE;
        Upgrade[] choices = new Upgrade[3];

        for (int sample = 0; sample < 100; sample++) {
            UpgradeSelector.fillChoices(player, choices);
            assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, choices[0]), choices[0].name());
            assertTrue(UpgradeSelector.isAvailable(player, choices[0]), choices[0].name());
            assertTrue(choices[0] != choices[1] && choices[0] != choices[2] && choices[1] != choices[2]);
        }
    }

    @Test void establishedAbilityAlwaysGetsOwnOrSynergyRelevantDraftSlot() {
        Player player = freshPlayer();
        player.abilities.upgrade(AbilityType.TESLA_ORB);
        player.abilities.upgrade(AbilityType.TESLA_ORB);
        Upgrade[] choices = new Upgrade[3];

        for (int sample = 0; sample < 100; sample++) {
            UpgradeSelector.fillChoices(player, choices);
            assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, choices[0]), choices[0].name());
            assertTrue(UpgradeSelector.isAvailable(player, choices[0]), choices[0].name());
        }
    }
}
