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

    @Test void foundryHazardsOwnStagesTenThroughNineteen() {
        ArenaHazardRuntime yard = new ArenaHazardRuntime(9, 2, 0);
        assertFalse(yard.foundryHazardsEnabled());
        assertTrue(Float.isInfinite(yard.foundryHazardInterval()));

        ArenaHazardRuntime foundry = new ArenaHazardRuntime(10, 2, 0);
        assertTrue(foundry.foundryHazardsEnabled());
        assertTrue(Float.isFinite(foundry.foundryHazardInterval()));

        ArenaHazardRuntime lastFoundry = new ArenaHazardRuntime(19, 2, 0);
        assertTrue(lastFoundry.foundryHazardsEnabled());
        ArenaHazardRuntime nullSector = new ArenaHazardRuntime(20, 2, 0);
        assertFalse(nullSector.foundryHazardsEnabled());
        assertTrue(Float.isInfinite(nullSector.foundryHazardInterval()));
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
        ArenaHazardRuntime stage19 = new ArenaHazardRuntime(19, 0, 0);
        assertTrue(stage19.foundryHazardInterval() < stage10.foundryHazardInterval());
        assertTrue(stage19.foundryHazardInterval() >= 10.2f);
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

    @Test void nullSectorHazardsStartAtStageTwentyAndReplaceFoundryPressure() {
        ArenaHazardRuntime stage19 = new ArenaHazardRuntime(19, 4, 0);
        assertFalse(stage19.nullSectorHazardsEnabled());
        assertTrue(Float.isInfinite(stage19.nullSectorHazardInterval()));

        ArenaHazardRuntime stage20 = new ArenaHazardRuntime(20, 4, 0);
        assertTrue(stage20.nullSectorHazardsEnabled());
        assertFalse(stage20.foundryHazardsEnabled());
        assertTrue(Float.isFinite(stage20.nullSectorHazardInterval()));

        float untilFirst = stage20.nullSectorHazardInterval() * .68f + .05f;
        for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) stage20.update(.05f, 1f, 2f);
        assertTrue(stage20.activeCount() > 0);
        for (ArenaHazardRuntime.Hazard hazard : stage20.hazards()) {
            assertTrue(hazard.type() == ArenaHazardRuntime.Type.VOID_RIFT
                || hazard.type() == ArenaHazardRuntime.Type.STATIC_BURST
                || hazard.type() == ArenaHazardRuntime.Type.NULL_BEAM);
            assertEquals(ArenaHazardRuntime.Phase.WARNING, hazard.phase());
            assertEquals(0f, stage20.consumePlayerDamage(hazard.x(), hazard.y(), .1f), .0001f);
        }
    }

    @Test void nullSectorHazardsAreDeterministicForSameRun() {
        ArenaHazardRuntime first = new ArenaHazardRuntime(24, 19, 0);
        ArenaHazardRuntime second = new ArenaHazardRuntime(24, 19, 0);
        float untilFirst = first.nullSectorHazardInterval() * .68f + .05f;
        for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) {
            first.update(.05f, -3f, 2f);
            second.update(.05f, -3f, 2f);
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

    @Test void laterNullSectorStagesIncreasePressureButStayBounded() {
        ArenaHazardRuntime stage20 = new ArenaHazardRuntime(20, 0, 0);
        ArenaHazardRuntime stage30 = new ArenaHazardRuntime(30, 0, 0);
        assertTrue(stage30.nullSectorHazardInterval() < stage20.nullSectorHazardInterval());
        assertTrue(stage30.nullSectorHazardInterval() >= 8.8f);
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
