package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class HarvesterBossProfileTest {
    @Test void entersLateGameRotationDeterministically() {
        assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(12));
        assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(17));
        assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(22));
    }

    @Test void identityHasPressureOrientedStats() {
        BossVariantStats.Stats stats = BossVariantStats.forIdentity(BossIdentity.HARVESTER, 100f, 2f, 10f);
        assertEquals(116f, stats.hp(), .001f);
        assertEquals(2.16f, stats.speed(), .001f);
        assertEquals(11.4f, stats.damage(), .001f);
    }

    @Test void phaseThreeUsesDenseFastProjectilePattern() {
        BossAttackPatternCatalog.Pattern pattern = BossAttackPatternCatalog.forPhase(BossIdentity.HARVESTER, 3);
        assertEquals(24, pattern.shots());
        assertTrue(pattern.radial());
        assertTrue(pattern.speedMultiplier() >= 1.2f);
        assertEquals(4, pattern.explosiveEvery());
    }

    @Test void runtimeSummonsMoreMinionsThanWarden() {
        BossCombatRuntime harvester = new BossCombatRuntime(BossIdentity.HARVESTER);
        BossCombatRuntime warden = new BossCombatRuntime(BossIdentity.WARDEN);
        assertTrue(harvester.summonCount(2) > warden.summonCount(2));
        assertTrue(harvester.summonCount(3) > warden.summonCount(3));
        assertTrue(harvester.enrageShots() > warden.enrageShots());
    }
}
