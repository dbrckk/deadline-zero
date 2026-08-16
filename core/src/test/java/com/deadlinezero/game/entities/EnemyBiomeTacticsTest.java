package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

public final class EnemyBiomeTacticsTest {
    @AfterEach void resetStage() { RunStageContext.begin(1); }

    @Test public void forgeHoundSchedulesChargeAtMidRange() {
        RunStageContext.begin(10);
        Enemy e = new Enemy(Enemy.Type.RUNNER, 0, 0, 100, 4f, .3f, 8f, 1);
        e.updateAi(.01f, 5f);
        assertEquals(Enemy.Tactic.CHARGE, e.pendingTactic());
        assertTrue(e.tacticalTelegraph());
    }

    @Test public void cinderGunnerSchedulesStrafe() {
        RunStageContext.begin(10);
        Enemy e = new Enemy(Enemy.Type.RANGED, 0, 0, 100, 2f, .4f, 8f, 1);
        e.updateAi(.01f, 6f);
        assertEquals(Enemy.Tactic.STRAFE, e.pendingTactic());
    }

    @Test public void slagGuardSchedulesHeavyCharge() {
        RunStageContext.begin(10);
        Enemy e = new Enemy(Enemy.Type.SHIELDED, 0, 0, 100, 2f, .5f, 8f, 1);
        e.updateAi(.01f, 5f);
        assertEquals(Enemy.Tactic.CHARGE, e.pendingTactic());
    }

    @Test public void phaseStalkerSchedulesFlankStrafe() {
        RunStageContext.begin(20);
        Enemy e = new Enemy(Enemy.Type.PHANTOM, 0, 0, 100, 3f, .4f, 8f, 1);
        e.updateAi(.01f, 5f);
        assertEquals(Enemy.Tactic.STRAFE, e.pendingTactic());
    }

    @Test public void staticSeerSchedulesZoningStrafe() {
        RunStageContext.begin(20);
        Enemy e = new Enemy(Enemy.Type.RANGED, 0, 0, 100, 2f, .4f, 8f, 1);
        e.updateAi(.01f, 8f);
        assertEquals(Enemy.Tactic.STRAFE, e.pendingTactic());
    }

    @Test public void nullWardGetsSupportRecoveryMultiplier() {
        RunStageContext.begin(20);
        Enemy e = new Enemy(Enemy.Type.REGENERATOR, 0, 0, 1000, 2f, .4f, 8f, 1);
        assertTrue(e.biomeBehavior().recoveryMultiplier() > 1.5f);
        assertTrue(e.biomeBehavior().speedMultiplier() < 1f);
    }
}
