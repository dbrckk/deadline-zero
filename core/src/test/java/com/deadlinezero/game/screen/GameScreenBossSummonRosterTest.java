package com.deadlinezero.game.screen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

public final class GameScreenBossSummonRosterTest {
    @Test public void nullArchonPhaseTwoUsesStalkersAndSeersOnly() {
        assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 0));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 2, 1));
        assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 2));
        assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 3));
    }

    @Test public void nullArchonPhaseThreeAddsNullWards() {
        assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 3, 0));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 3, 1));
        assertEquals(Enemy.Type.REGENERATOR, GameScreen.bossSummonType(true, false, 3, 2));
        assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 3, 3));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 3, 4));
        assertEquals(Enemy.Type.REGENERATOR, GameScreen.bossSummonType(true, false, 3, 5));
    }

    @Test public void revenantHistoricalPhaseThreeCadenceIsPreserved() {
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, true, 3, 0));
        assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, true, 3, 1));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, true, 3, 2));
        assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, true, 3, 3));
    }

    @Test public void standardBossHistoricalRosterIsPreserved() {
        assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 2, 0));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, false, 3, 0));
        assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 3, 1));
        assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 3, 2));
        assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, false, 3, 3));
    }
}
