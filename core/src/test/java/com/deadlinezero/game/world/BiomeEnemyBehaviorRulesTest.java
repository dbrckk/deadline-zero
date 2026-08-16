package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class BiomeEnemyBehaviorRulesTest {
    @Test public void forgeHoundIsAggressiveBurstCharger() {
        var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.FORGE_HOUND);
        assertTrue(p.aggressiveCharge());
        assertFalse(p.evasiveStrafe());
        assertTrue(p.speedMultiplier() > 1f);
        assertTrue(p.burstMultiplier() > 1.3f);
        assertTrue(p.chargeStrengthMultiplier() > 1.2f);
    }

    @Test public void cinderGunnerPrioritizesStrongFrequentStrafes() {
        var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.CINDER_GUNNER);
        assertTrue(p.evasiveStrafe());
        assertFalse(p.aggressiveCharge());
        assertTrue(p.tacticCooldownMultiplier() < .8f);
        assertTrue(p.strafeStrengthMultiplier() > 1.3f);
    }

    @Test public void slagGuardTradesSpeedForChargeWeight() {
        var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.SLAG_GUARD);
        assertTrue(p.speedMultiplier() < 1f);
        assertTrue(p.aggressiveCharge());
        assertTrue(p.chargeStrengthMultiplier() > 1.35f);
    }

    @Test public void phaseStalkerHasHighestMobilityBurst() {
        var phase = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.PHASE_STALKER);
        var forge = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.FORGE_HOUND);
        assertTrue(phase.evasiveStrafe());
        assertTrue(phase.burstMultiplier() > forge.burstMultiplier());
        assertTrue(phase.strafeStrengthMultiplier() > 1.4f);
    }

    @Test public void staticSeerHasFastestTacticalCadence() {
        var seer = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.STATIC_SEER);
        var cinder = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.CINDER_GUNNER);
        assertTrue(seer.tacticCooldownMultiplier() < cinder.tacticCooldownMultiplier());
        assertTrue(seer.strafeStrengthMultiplier() > cinder.strafeStrengthMultiplier());
    }

    @Test public void nullWardIsSlowSupportRegenerator() {
        var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.NULL_WARD);
        assertTrue(p.speedMultiplier() < .9f);
        assertTrue(p.recoveryMultiplier() > 1.5f);
        assertFalse(p.aggressiveCharge());
        assertFalse(p.evasiveStrafe());
    }

    @Test public void standardEnemiesKeepNeutralProfile() {
        var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.NONE);
        assertEquals(1f, p.speedMultiplier(), .0001f);
        assertEquals(1f, p.burstMultiplier(), .0001f);
        assertEquals(1f, p.tacticCooldownMultiplier(), .0001f);
        assertEquals(1f, p.recoveryMultiplier(), .0001f);
    }
}
