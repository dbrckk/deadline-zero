package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BossAttackPatternCatalogTest {
    @Test void revenantIsDenserAndFasterInEachPhase() {
        for (int phase = 1; phase <= 3; phase++) {
            var alpha = BossAttackPatternCatalog.forPhase(false, phase);
            var revenant = BossAttackPatternCatalog.forPhase(true, phase);
            assertTrue(revenant.shots() >= alpha.shots());
            assertTrue(revenant.speedMultiplier() > alpha.speedMultiplier());
        }
    }

    @Test void phaseTwoRevenantIntroducesExplosives() {
        var alpha = BossAttackPatternCatalog.forPhase(false, 2);
        var revenant = BossAttackPatternCatalog.forPhase(true, 2);
        assertEquals(0, alpha.explosiveEvery());
        assertTrue(revenant.explosiveEvery() > 0);
        assertTrue(revenant.explosionRadius() > 0f);
    }

    @Test void phaseThreeRevenantEscalatesDensity() {
        var phase2 = BossAttackPatternCatalog.forPhase(true, 2);
        var phase3 = BossAttackPatternCatalog.forPhase(true, 3);
        assertTrue(phase3.shots() > phase2.shots());
        assertTrue(phase3.explosionRadius() >= phase2.explosionRadius());
    }
}
