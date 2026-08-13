package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BossCombatSecondaryTuningTest {
    @Test void revenantSummonsMoreMinions() {
        BossCombatRuntime alpha = new BossCombatRuntime(false);
        BossCombatRuntime revenant = new BossCombatRuntime(true);
        assertTrue(revenant.summonCount(2) > alpha.summonCount(2));
        assertTrue(revenant.summonCount(3) > alpha.summonCount(3));
    }

    @Test void revenantEnrageIsDenserAndFaster() {
        BossCombatRuntime alpha = new BossCombatRuntime(false);
        BossCombatRuntime revenant = new BossCombatRuntime(true);
        assertTrue(revenant.enrageShots() > alpha.enrageShots());
        assertTrue(revenant.enrageProjectileSpeed() > alpha.enrageProjectileSpeed());
        assertTrue(revenant.enrageExplosiveEvery() < alpha.enrageExplosiveEvery());
        assertTrue(revenant.enrageExplosionRadius() > alpha.enrageExplosionRadius());
    }

    @Test void alphaKeepsExistingSecondaryValues() {
        BossCombatRuntime alpha = new BossCombatRuntime(false);
        assertEquals(3, alpha.summonCount(2));
        assertEquals(6, alpha.summonCount(3));
        assertEquals(20, alpha.enrageShots());
        assertEquals(8.2f, alpha.enrageProjectileSpeed(), .0001f);
        assertEquals(4, alpha.enrageExplosiveEvery());
        assertEquals(2.0f, alpha.enrageExplosionRadius(), .0001f);
    }
}
