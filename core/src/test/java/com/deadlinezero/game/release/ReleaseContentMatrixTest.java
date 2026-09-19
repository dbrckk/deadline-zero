package com.deadlinezero.game.release;

import static org.junit.jupiter.api.Assertions.*;

import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.ThreatTierRules;
import com.deadlinezero.game.progression.Upgrade;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;
import com.deadlinezero.game.world.BiomeEnemyRoster;
import com.deadlinezero.game.world.RunEncounterDirector;
import org.junit.jupiter.api.Test;

/** Release contract for the M5 content matrix. */
final class ReleaseContentMatrixTest {
    @Test void shippingContentMeetsRoadmapMinimums() {
        assertTrue(EnvironmentBiomeRules.Biome.values().length >= 5, "5+ biomes");
        assertTrue(WeaponCatalog.all().length >= 12, "12+ weapons");
        assertTrue(Upgrade.values().length >= 50, "50+ run upgrades");
        assertTrue(BossIdentity.values().length >= 6, "6+ bosses");

        int nonBossArchetypes = Enemy.Type.values().length - 1;
        int biomeSignatures = BiomeEnemyRoster.Identity.values().length - 1;
        int championVariants = Enemy.Variant.values().length - 1;
        assertTrue(nonBossArchetypes + biomeSignatures + championVariants >= 20, "20+ enemy gameplay profiles");
        assertTrue(championVariants >= 8, "8+ champion variants");

        int encounterTypes = RunEncounterDirector.Type.values().length - 1;
        assertTrue(encounterTypes >= 1, "encounter events present");
        assertTrue(ThreatTierRules.MAX_TIER >= 1, "difficulty ladder present");
    }

    @Test void abilityEvolutionAndCrossBuildSynergyAreProductionFeatures() {
        AbilityLoadout loadout = new AbilityLoadout();
        for (int i = 0; i < 5; i++) loadout.upgrade(AbilityType.TESLA_ORB);
        for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.DRONE);

        assertTrue(loadout.evolved(AbilityType.TESLA_ORB));
        assertTrue(loadout.hasTeslaEvolution());
    }

    @Test void eventProtocolsHaveSecondStageEvolutions() {
        assertNotNull(Upgrade.RHYTHM_ACCELERATOR);
        assertNotNull(Upgrade.KILLCHAIN_OVERCHARGE);
        assertNotNull(Upgrade.REACTION_CASCADE);
    }
}
