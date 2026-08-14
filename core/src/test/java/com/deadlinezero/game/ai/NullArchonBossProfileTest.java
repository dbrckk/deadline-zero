package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class NullArchonBossProfileTest {
    @Test void nullArchonOwnsFiveStageNullMilestones() {
        assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(20));
        assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(25));
        assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(30));
        assertTrue(BossIdentity.forStage(19) != BossIdentity.NULL_ARCHON);
        assertTrue(BossIdentity.forStage(21) != BossIdentity.NULL_ARCHON);
    }

    @Test void nullArchonHasFastDensePhaseThreePattern() {
        BossAttackPatternCatalog.Pattern p = BossAttackPatternCatalog.forPhase(BossIdentity.NULL_ARCHON, 3);
        assertEquals(30, p.shots());
        assertEquals(12f, p.spreadDegrees(), .0001f);
        assertTrue(p.speedMultiplier() > 1.20f);
        assertEquals(5, p.explosiveEvery());
        assertTrue(p.explosionRadius() >= 1.70f);
        assertTrue(p.radial());
    }

    @Test void nullArchonCombatCadenceIsDistinctAndAggressive() {
        BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.NULL_ARCHON);
        assertTrue(runtime.nullArchon());
        assertEquals(NullArchonBossProfile.PHASE3_SUMMON_COUNT, runtime.summonCount(3));
        assertEquals(NullArchonBossProfile.ENRAGE_SHOTS, runtime.enrageShots());
        assertEquals(NullArchonBossProfile.ENRAGE_PROJECTILE_SPEED, runtime.enrageProjectileSpeed(), .0001f);
        assertEquals(NullArchonBossProfile.ENRAGE_EXPLOSIVE_EVERY, runtime.enrageExplosiveEvery());
        assertEquals(NullArchonBossProfile.ENRAGE_EXPLOSION_RADIUS, runtime.enrageExplosionRadius(), .0001f);
    }

    @Test void nullArchonStatsRemainInsideLateGameBossEnvelope() {
        assertTrue(NullArchonBossProfile.HP_MULTIPLIER >= 1.15f && NullArchonBossProfile.HP_MULTIPLIER <= 1.30f);
        assertTrue(NullArchonBossProfile.SPEED_MULTIPLIER >= 1.05f && NullArchonBossProfile.SPEED_MULTIPLIER <= 1.18f);
        assertTrue(NullArchonBossProfile.DAMAGE_MULTIPLIER >= 1.10f && NullArchonBossProfile.DAMAGE_MULTIPLIER <= 1.22f);
    }
}
