package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class WardenBossProfileTest {
    @Test void wardenIsHeavySlowAndDangerous() {
        assertTrue(WardenBossProfile.HP_MULTIPLIER > 1f);
        assertTrue(WardenBossProfile.SPEED_MULTIPLIER < 1f);
        assertTrue(WardenBossProfile.DAMAGE_MULTIPLIER > 1f);
    }

    @Test void stageSevenReceivesWardenStats() {
        var base = BossVariantStats.forStage(1, 100f, 10f, 20f);
        var warden = BossVariantStats.forStage(7, 100f, 10f, 20f);
        assertTrue(warden.hp() > base.hp());
        assertTrue(warden.speed() < base.speed());
        assertTrue(warden.damage() > base.damage());
    }

    @Test void runtimeUsesLowDensityHeavyPressure() {
        BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.WARDEN);
        assertEquals(2, runtime.summonCount(2));
        assertEquals(4, runtime.summonCount(3));
        assertEquals(12, runtime.enrageShots());
        assertEquals(2, runtime.enrageExplosiveEvery());
        assertTrue(runtime.enrageExplosionRadius() > 2f);
        assertTrue(runtime.warden());
    }
}
