package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

public final class WaveDirectorTest {
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
}
