package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class EnvironmentBiomeRulesTest {
    @Test public void quarantineOwnsEarlyStages() {
        assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(1));
        assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(9));
        assertFalse(EnvironmentBiomeRules.isFoundry(9));
    }

    @Test public void foundryStartsAtStageTenAndPersists() {
        assertEquals(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY, EnvironmentBiomeRules.forStage(10));
        assertEquals(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY, EnvironmentBiomeRules.forStage(30));
        assertTrue(EnvironmentBiomeRules.isFoundry(10));
    }

    @Test public void invalidStagesSanitizeToFirstBiome() {
        assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(0));
        assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(-50));
    }
}
