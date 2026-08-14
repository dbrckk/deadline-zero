package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.ThreatTierRules;

final class BossAffixRulesTest {
    @AfterEach void cleanup() { RunStageContext.begin(1, 0, 0); }

    @Test void standardRunsHaveNoBossAffix() {
        assertEquals(BossAffixRules.Affix.NONE, BossAffixRules.forRun(10, 0));
    }

    @Test void ascendedRunsRotateDeterministically() {
        BossAffixRules.Affix first = BossAffixRules.forRun(12, 7);
        BossAffixRules.Affix second = BossAffixRules.forRun(12, 7);
        assertEquals(first, second);
        assertTrue(first != BossAffixRules.Affix.NONE);
    }

    @Test void maximumThreatAlwaysUsesApocalypse() {
        assertEquals(BossAffixRules.Affix.APOCALYPSE,
            BossAffixRules.forRun(20, ThreatTierRules.MAX_TIER));
    }

    @Test void affixRaisesBossStatsOnAscendedRun() {
        RunStageContext.begin(12, 4, 0);
        BossVariantStats.Stats normal = BossVariantStats.forStage(12, 1000f, 1f, 20f);
        RunStageContext.begin(12, 4, 20);
        BossVariantStats.Stats ascended = BossVariantStats.forStage(12, 1000f, 1f, 20f);
        assertTrue(ascended.hp() > normal.hp());
        assertTrue(ascended.speed() > normal.speed());
        assertTrue(ascended.damage() > normal.damage());
    }

    @Test void apocalypseIncreasesBossActionPressure() {
        RunStageContext.begin(20, 0, 20);
        BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.ALPHA);
        assertEquals(BossAffixRules.Affix.APOCALYPSE, runtime.affix());
        assertEquals(9, runtime.summonCount(3));
        assertEquals(28, runtime.enrageShots());
        assertEquals(3, runtime.enrageExplosiveEvery());
        assertTrue(runtime.enrageExplosionRadius() > 2f);
        assertTrue(runtime.enrageProjectileSpeed() > 8.2f);
    }
}
