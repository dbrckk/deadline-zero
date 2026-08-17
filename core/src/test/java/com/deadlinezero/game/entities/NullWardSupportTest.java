package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.meta.RunStageContext;
import org.junit.jupiter.api.Test;

final class NullWardSupportTest {
    @Test void pulseHealsAndBuffsNearbyNonBossAlly() {
        RunStageContext.begin(20, 77, 0);
        Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8);
        Enemy ally = new Enemy(Enemy.Type.RUNNER, 1f, 0f, 100f, 3f, .35f, 10f, 8);
        ally.damage(50f);
        float hpBefore = ally.hp;
        float speedBefore = ally.effectiveSpeed();

        ward.updateStatus(Enemy.nullWardPulseInterval());

        assertTrue(ally.hp > hpBefore, "Null Ward should heal a nearby ally");
        assertTrue(ally.supportBuffed(), "Null Ward should grant its temporary support buff");
        assertTrue(ally.effectiveSpeed() > speedBefore, "support buff should increase ally mobility");
        assertTrue(ward.supportPulseFlash() > 0f, "pulse should expose a short visual event window");
    }

    @Test void simultaneousWardsCannotStackBurstHealingOnOneTarget() {
        RunStageContext.begin(20, 79, 0);
        Enemy wardA = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8);
        Enemy wardB = new Enemy(Enemy.Type.REGENERATOR, .5f, 0f, 100f, 2f, .45f, 10f, 8);
        Enemy ally = new Enemy(Enemy.Type.RUNNER, 1f, 0f, 100f, 3f, .35f, 10f, 8);
        ally.damage(60f);
        float before = ally.hp;
        float expectedSingleHeal = ally.maxHp * .045f;

        wardA.updateStatus(Enemy.nullWardPulseInterval());
        wardB.updateStatus(Enemy.nullWardPulseInterval());

        assertEquals(before + expectedSingleHeal, ally.hp, .01f,
            "synchronized Null Wards must not multiply burst healing");
        assertTrue(ally.supportBuffed(), "the shared target should still receive the support buff");
    }

    @Test void pulseIgnoresBossesAndDistantEnemies() {
        RunStageContext.begin(20, 78, 0);
        Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8);
        Enemy boss = new Enemy(Enemy.Type.BOSS, 1f, 0f, 500f, 1f, 1.2f, 20f, 100);
        Enemy distant = new Enemy(Enemy.Type.RUNNER, Enemy.nullWardPulseRadius() + 2f, 0f, 100f, 3f, .35f, 10f, 8);
        boss.damage(100f);
        distant.damage(40f);
        float bossHp = boss.hp;
        float distantHp = distant.hp;

        ward.updateStatus(Enemy.nullWardPulseInterval());

        assertEquals(bossHp, boss.hp, .001f);
        assertEquals(distantHp, distant.hp, .001f);
        assertTrue(!boss.supportBuffed());
        assertTrue(!distant.supportBuffed());
    }
}
