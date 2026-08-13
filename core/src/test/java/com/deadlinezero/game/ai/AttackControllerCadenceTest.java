package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class AttackControllerCadenceTest {
    @Test public void cadenceModifiersAreClamped() {
        AttackController c = new AttackController(EnemyArchetype.MELEE);
        c.setCadence(.1f, 4f, .2f);
        assertEquals(.45f, c.cooldownMultiplier(), .0001f);
        assertEquals(1.8f, c.telegraphMultiplier(), .0001f);
    }

    @Test public void fasterCadenceUsesShorterConfiguredCycle() {
        AttackController fast = new AttackController(EnemyArchetype.MELEE);
        AttackController slow = new AttackController(EnemyArchetype.MELEE);
        fast.setCadence(.50f, .70f, .70f);
        slow.setCadence(1.40f, 1.30f, 1.30f);

        assertTrue(fast.cooldownMultiplier() < slow.cooldownMultiplier());
        assertTrue(fast.telegraphMultiplier() < slow.telegraphMultiplier());

        float fastCycle = EnemyArchetype.MELEE.attackCooldown * fast.cooldownMultiplier()
            + EnemyArchetype.MELEE.telegraphDuration * fast.telegraphMultiplier();
        float slowCycle = EnemyArchetype.MELEE.attackCooldown * slow.cooldownMultiplier()
            + EnemyArchetype.MELEE.telegraphDuration * slow.telegraphMultiplier();
        assertTrue(fastCycle < slowCycle);
    }
}
