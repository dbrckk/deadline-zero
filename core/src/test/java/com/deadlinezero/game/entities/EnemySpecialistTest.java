package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

final class EnemySpecialistTest {
    @BeforeEach void resetStage() { RunStageContext.begin(1, 0, 0); }
    @AfterEach void cleanupStage() { RunStageContext.begin(1, 0, 0); }

    @Test void shieldedEnemyAbsorbsDamageBeforeHealth() {
        Enemy enemy = new Enemy(Enemy.Type.SHIELDED, 0f, 0f, 100f, 2f, .5f, 10f, 10);
        float hpBefore = enemy.hp;
        float shieldBefore = enemy.shieldHp;

        enemy.damage(Math.min(20f, shieldBefore));

        assertEquals(hpBefore, enemy.hp, .001f);
        assertTrue(enemy.shieldHp < shieldBefore);
        assertTrue(enemy.shieldFraction() >= 0f && enemy.shieldFraction() <= 1f);
    }

    @Test void shieldedEnemyRechargesAfterRecoveryDelay() {
        Enemy enemy = new Enemy(Enemy.Type.SHIELDED, 0f, 0f, 100f, 2f, .5f, 10f, 10);
        enemy.damage(enemy.shieldHp * .5f);
        float depleted = enemy.shieldHp;

        for (int i = 0; i < 260; i++) enemy.updateStatus(1f / 60f);

        assertTrue(enemy.shieldHp > depleted);
        assertTrue(enemy.shieldHp <= enemy.shieldMaxHp);
    }

    @Test void regeneratorRecoversHealthOnlyAfterTakingPressureBreak() {
        Enemy enemy = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .5f, 10f, 10);
        enemy.damage(35f);
        float damaged = enemy.hp;

        for (int i = 0; i < 120; i++) enemy.updateStatus(1f / 60f);
        assertEquals(damaged, enemy.hp, .01f);

        for (int i = 0; i < 120; i++) enemy.updateStatus(1f / 60f);
        assertTrue(enemy.hp > damaged);
        assertTrue(enemy.hp <= enemy.maxHp);
    }

    @Test void phantomCyclesIntoMitigationAndSpeedWindow() {
        Enemy enemy = new Enemy(Enemy.Type.PHANTOM, 0f, 0f, 100f, 2f, .5f, 10f, 10);
        assertFalse(enemy.phased());
        float baseConfiguredSpeed = enemy.speed;

        for (int i = 0; i < 216; i++) enemy.updateStatus(1f / 60f);
        assertTrue(enemy.phased());
        assertTrue(enemy.effectiveSpeed() > baseConfiguredSpeed * 1.30f,
            "phased phantom must gain a material speed multiplier independent of champion burst timing");

        float before = enemy.hp;
        enemy.damage(50f);
        assertTrue(before - enemy.hp < 20f);
    }
}
