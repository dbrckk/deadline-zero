package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

final class ArenaHazardRuntimeTest {
    @Test void deathBurstCannotDamageDuringWarningAndHitsOnlyOnce() {
        ArenaHazardRuntime runtime = new ArenaHazardRuntime(12, 4, 10);
        runtime.scheduleDeathBurst(2f, -1f, 2.2f, 31f);

        assertEquals(0f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);
        runtime.update(.30f, 0f, 0f);
        assertEquals(ArenaHazardRuntime.Phase.WARNING, runtime.hazards().get(0).phase());
        assertEquals(0f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);

        runtime.update(.19f, 0f, 0f);
        assertEquals(ArenaHazardRuntime.Phase.ACTIVE, runtime.hazards().get(0).phase());
        assertEquals(31f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);
        assertEquals(0f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);
    }

    @Test void playerOutsideActiveHazardIsNotHit() {
        ArenaHazardRuntime runtime = new ArenaHazardRuntime(12, 4, 10);
        runtime.scheduleDeathBurst(0f, 0f, 2f, 20f);
        runtime.update(.49f, 0f, 0f);
        assertEquals(0f, runtime.consumePlayerDamage(4f, 0f, .4f), .0001f);
        assertFalse(runtime.hazards().get(0).playerDamageConsumed());
    }

    @Test void periodicHazardsUnlockAtThreatFive() {
        ArenaHazardRuntime standard = new ArenaHazardRuntime(10, 1, 4);
        assertFalse(standard.periodicHazardsEnabled());
        assertTrue(Float.isInfinite(standard.periodicInterval()));

        ArenaHazardRuntime ascended = new ArenaHazardRuntime(10, 1, 5);
        assertTrue(ascended.periodicHazardsEnabled());
        assertTrue(Float.isFinite(ascended.periodicInterval()));
    }

    @Test void periodicStrikePlacementIsDeterministicForSameRun() {
        ArenaHazardRuntime first = new ArenaHazardRuntime(14, 22, 9);
        ArenaHazardRuntime second = new ArenaHazardRuntime(14, 22, 9);
        float interval = first.periodicInterval();
        for (float elapsed = 0f; elapsed < interval + .1f; elapsed += .05f) {
            first.update(.05f, 3f, -2f);
            second.update(.05f, 3f, -2f);
        }
        assertEquals(1, first.activeCount());
        assertEquals(1, second.activeCount());
        ArenaHazardRuntime.Hazard a = first.hazards().get(0);
        ArenaHazardRuntime.Hazard b = second.hazards().get(0);
        assertEquals(a.type(), b.type());
        assertEquals(a.x(), b.x(), .0001f);
        assertEquals(a.y(), b.y(), .0001f);
        assertEquals(a.radius(), b.radius(), .0001f);
        assertEquals(a.damage(), b.damage(), .0001f);
    }

    @Test void higherThreatRaisesPeriodicDamageAndFrequency() {
        ArenaHazardRuntime tier5 = new ArenaHazardRuntime(12, 0, 5);
        ArenaHazardRuntime tier20 = new ArenaHazardRuntime(12, 0, 20);
        assertTrue(tier20.periodicInterval() < tier5.periodicInterval());

        float interval = tier20.periodicInterval();
        for (float elapsed = 0f; elapsed < interval + .1f; elapsed += .05f) tier20.update(.05f, 0f, 0f);
        assertTrue(tier20.hazards().get(0).damage() > 14f);
    }

    @Test void deathBurstRulesEscalateWithThreatAndNeverApplyToBoss() {
        assertFalse(DeathBurstRules.enabled(Enemy.Type.BRUTE, 7));
        assertTrue(DeathBurstRules.enabled(Enemy.Type.BRUTE, 8));
        assertTrue(DeathBurstRules.enabled(Enemy.Type.SHIELDED, 8));
        assertFalse(DeathBurstRules.enabled(Enemy.Type.PHANTOM, 14));
        assertTrue(DeathBurstRules.enabled(Enemy.Type.PHANTOM, 15));
        assertFalse(DeathBurstRules.enabled(Enemy.Type.BOSS, 20));
        assertTrue(DeathBurstRules.damage(Enemy.Type.ELITE, 20) > DeathBurstRules.damage(Enemy.Type.ELITE, 8));
    }
}
