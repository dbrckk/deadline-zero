package com.deadlinezero.game.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;
import com.deadlinezero.game.progression.Upgrade;
import org.junit.jupiter.api.Test;

final class AbilityUpgradeGuidanceTest {
    private Player fresh() {
        RunLoadoutContext.end();
        return new Player(0f, 0f);
    }

    @Test void freshAbilityShowsUnlockThenTierAndEvolutionMilestones() {
        Player p = fresh();
        assertEquals("combat.abilityGuidance.unlock", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
        p.abilities.upgrade(AbilityType.TESLA_ORB);
        assertEquals("combat.abilityGuidance.level", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
        p.abilities.upgrade(AbilityType.TESLA_ORB);
        assertEquals("combat.abilityGuidance.tier2", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
        p.abilities.upgrade(AbilityType.TESLA_ORB);
        p.abilities.upgrade(AbilityType.TESLA_ORB);
        assertEquals("combat.abilityGuidance.evolution", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
    }

    @Test void choiceThatCompletesSynergyOverridesGenericMilestone() {
        Player p = fresh();
        for (int i = 0; i < 2; i++) p.abilities.upgrade(AbilityType.CRYO_NOVA);
        for (int i = 0; i < 2; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
        assertEquals("combat.synergy.superconductor", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
    }

    @Test void evolvedTeslaMakesDroneTierTwoChoiceExposeArcReactor() {
        Player p = fresh();
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
        for (int i = 0; i < 2; i++) p.abilities.upgrade(AbilityType.DRONE);
        assertEquals("combat.synergy.arcReactor", AbilityUpgradeGuidance.key(p, Upgrade.DRONE));
    }

    @Test void nonAbilityAndMaxedAbilityHaveNoGuidance() {
        Player p = fresh();
        assertNull(AbilityUpgradeGuidance.key(p, Upgrade.DAMAGE));
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.ORBITAL_BLADE);
        assertNull(AbilityUpgradeGuidance.key(p, Upgrade.ORBITAL));
    }
}
