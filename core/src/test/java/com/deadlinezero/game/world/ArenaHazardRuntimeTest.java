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

    @Test void foundryHazardsStartAtStageTenWithoutThreatRequirement() {
        ArenaHazardRuntime yard = new ArenaHazardRuntime(9, 2, 0);
        assertFalse(yard.foundryHazardsEnabled());
        assertTrue(Float.isInfinite(yard.foundryHazardInterval()));

        ArenaHazardRuntime foundry = new ArenaHazardRuntime(10, 2, 0);
        assertTrue(foundry.foundryHazardsEnabled());
        assertTrue(Float.isFinite(foundry.foundryHazardInterval()));

        float untilFirst = foundry.foundryHazardInterval() * .72f + .05f;
        for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) foundry.update(.05f, 2f, -3f);
        assertTrue(foundry.activeCount() > 0);
        for (ArenaHazardRuntime.Hazard hazard : foundry.hazards()) {
            assertEquals(ArenaHazardRuntime.Phase.WARNING, hazard.phase());
            assertTrue(hazard.type() == ArenaHazardRuntime.Type.LAVA_VENT
                || hazard.type() == ArenaHazardRuntime.Type.STEAM_JET
                || hazard.type() == ArenaHazardRuntime.Type.HEAT_LINE);
            assertEquals(0f, foundry.consumePlayerDamage(hazard.x(), hazard.y(), .4f), .0001f);
        }
    }

    @Test void foundryHazardsAreDeterministicForSameRun() {
        ArenaHazardRuntime first = new ArenaHazardRuntime(18, 37, 6);
        ArenaHazardRuntime second = new ArenaHazardRuntime(18, 37, 6);
        float untilFirst = first.foundryHazardInterval() * .72f + .05f;
        for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) {
            first.update(.05f, 4f, -1f);
            second.update(.05f, 4f, -1f);
        }
        assertEquals(first.activeCount(), second.activeCount());
        assertTrue(first.activeCount() > 0);
        for (int i = 0; i < first.activeCount(); i++) {
            ArenaHazardRuntime.Hazard a = first.hazards().get(i);
            ArenaHazardRuntime.Hazard b = second.hazards().get(i);
            assertEquals(a.type(), b.type());
            assertEquals(a.x(), b.x(), .0001f);
            assertEquals(a.y(), b.y(), .0001f);
            assertEquals(a.radius(), b.radius(), .0001f);
            assertEquals(a.damage(), b.damage(), .0001f);
        }
    }

    @Test void laterFoundryStagesIncreasePressureButStayBounded() {
        ArenaHazardRuntime stage10 = new ArenaHazardRuntime(10, 0, 0);
        ArenaHazardRuntime stage30 = new ArenaHazardRuntime(30, 0, 0);
        assertTrue(stage30.foundryHazardInterval() < stage10.foundryHazardInterval());
        assertTrue(stage30.foundryHazardInterval() >= 9.5f);
    }

    @Test void foundryWarningEventuallyBecomesDamageableAndStillHitsOnce() {
        ArenaHazardRuntime runtime = new ArenaHazardRuntime(10, 3, 0);
        float untilFirst = runtime.foundryHazardInterval() * .72f + .05f;
        for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) runtime.update(.05f, 0f, 0f);
        ArenaHazardRuntime.Hazard target = runtime.hazards().get(0);
        assertEquals(0f, runtime.consumePlayerDamage(target.x(), target.y(), .1f), .0001f);

        float damage = 0f;
        for (int i = 0; i < 30 && damage == 0f; i++) {
            runtime.update(.05f, 0f, 0f);
            damage = runtime.consumePlayerDamage(target.x(), target.y(), .1f);
        }
        assertTrue(damage > 0f);
        assertEquals(0f, runtime.consumePlayerDamage(target.x(), target.y(), .1f), .0001f);
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
