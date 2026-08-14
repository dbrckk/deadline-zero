package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;

public final class WaveDirectorTest {
    @AfterEach public void cleanup() { RunModifierContext.end(); }

    @Test public void pressureBandsProgressTowardBoss() {
        RunStageContext.begin(1);
        WaveDirector d = new WaveDirector();
        float boss = d.bossArrivalSeconds();
        assertEquals(WaveDirector.PressureBand.OPENING, d.pressureBand());
        d.update(boss * .30f);
        assertEquals(WaveDirector.PressureBand.BUILD, d.pressureBand());
        d.update(boss * .30f);
        assertEquals(WaveDirector.PressureBand.ASSAULT, d.pressureBand());
        d.update(boss * .25f);
        assertEquals(WaveDirector.PressureBand.CRISIS, d.pressureBand());
    }

    @Test public void spawnCadenceAcceleratesAcrossPressureBands() {
        RunStageContext.begin(1);
        WaveDirector opening = new WaveDirector();
        opening.onSpawn();
        opening.update(.30f);
        assertTrue(!opening.shouldSpawn());

        WaveDirector crisis = new WaveDirector();
        crisis.update(crisis.bossArrivalSeconds() * .85f);
        crisis.onSpawn();
        crisis.update(.30f);
        assertTrue(crisis.shouldSpawn());
    }

    @Test public void bossBecomesPendingAtArrival() {
        RunStageContext.begin(3);
        WaveDirector d = new WaveDirector();
        d.update(d.bossArrivalSeconds() + .01f);
        assertTrue(d.bossPending());
    }

    @Test public void twinApexSpawnsTwoBossSignalsBeforeClosingGate() {
        activateLegendary(RunModifierContext.Modifier.TWIN_APEX);
        WaveDirector d = new WaveDirector();
        d.update(d.bossArrivalSeconds() + .01f);
        assertEquals(Enemy.Type.BOSS, d.chooseType());
        d.onBossSpawned();
        assertEquals(1, d.bossSpawnCount());
        assertTrue(d.bossPending());
        assertTrue(!d.bossSpawned());

        d.onSpawn();
        d.update(.20f);
        assertTrue(d.shouldSpawn());
        assertEquals(Enemy.Type.BOSS, d.chooseType());
        d.onBossSpawned();
        assertEquals(2, d.bossSpawnCount());
        assertTrue(d.bossSpawned());
    }

    @Test public void phantomEclipseDominatesNormalWaveSelection() {
        activateLegendary(RunModifierContext.Modifier.PHANTOM_ECLIPSE);
        WaveDirector d = new WaveDirector();
        int phantoms = 0;
        for (int i = 0; i < 400; i++) if (d.chooseType() == Enemy.Type.PHANTOM) phantoms++;
        assertTrue(phantoms > 170);
    }

    @Test public void specialistSiegeRemovesBasicShamblersAndRunners() {
        activateLegendary(RunModifierContext.Modifier.SPECIALIST_SIEGE);
        WaveDirector d = new WaveDirector();
        for (int i = 0; i < 200; i++) {
            Enemy.Type type = d.chooseType();
            assertTrue(type != Enemy.Type.SHAMBLER && type != Enemy.Type.RUNNER && type != Enemy.Type.PHANTOM);
        }
    }

    private static void activateLegendary(RunModifierContext.Modifier target) {
        for (int stage = 3; stage <= 18; stage++) {
            for (int ordinal = 0; ordinal < 20; ordinal++) {
                RunStageContext.begin(stage, ordinal);
                for (RunModifierContext.Modifier offer : RunModifierContext.offers()) {
                    if (offer == target) {
                        if (!RunModifierContext.choose(target)) throw new AssertionError("Legendary contract activation failed");
                        return;
                    }
                }
            }
        }
        throw new AssertionError("Legendary contract was never offered: " + target);
    }
}
