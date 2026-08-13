package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

public final class EnemyTacticsTest {
    @Test public void rangedPreparesStrafeAtOuterPreferredRange() {
        RunStageContext.begin(1);
        Enemy ranged = new Enemy(Enemy.Type.RANGED, 0f, 0f, 72f, 2.15f, .42f, 13f, 12);
        ranged.velocity.set(1f, 0f);
        ranged.updateAi(.01f, 8.5f);
        assertEquals(Enemy.Tactic.STRAFE, ranged.pendingTactic());
        assertTrue(ranged.tacticalTelegraph());
    }

    @Test public void brutePreparesChargeAtMidRange() {
        RunStageContext.begin(1);
        Enemy brute = new Enemy(Enemy.Type.BRUTE, 0f, 0f, 145f, 1.6f, .72f, 18f, 15);
        brute.velocity.set(1f, 0f);
        brute.updateAi(.01f, 5f);
        assertEquals(Enemy.Tactic.CHARGE, brute.pendingTactic());
        assertTrue(brute.tacticalTelegraph());
    }

    @Test public void bossDoesNotUseGenericTactics() {
        RunStageContext.begin(1);
        Enemy boss = new Enemy(Enemy.Type.BOSS, 0f, 0f, 2200f, 1.35f, 1.65f, 24f, 280);
        boss.velocity.set(1f, 0f);
        boss.updateAi(.01f, 5f);
        assertEquals(Enemy.Tactic.NONE, boss.pendingTactic());
    }
}
