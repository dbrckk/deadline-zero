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

    @Test public void fasterCadenceReachesTelegraphEarlier() {
        AttackController fast = new AttackController(EnemyArchetype.MELEE);
        AttackController slow = new AttackController(EnemyArchetype.MELEE);
        fast.setCadence(.50f, .70f, .70f);
        slow.setCadence(1.40f, 1.30f, 1.30f);

        fast.update(1f, .5f);
        slow.update(1f, .5f);
        fast.update(1f, .5f);
        slow.update(1f, .5f);
        fast.consumeAttack();
        slow.consumeAttack();
        fast.update(.8f, .5f);
        slow.update(.8f, .5f);
        fast.update(.01f, .5f);
        slow.update(.01f, .5f);

        assertTrue(fast.state() == EnemyState.TELEGRAPHING || fast.state() == EnemyState.ATTACKING || fast.state() == EnemyState.RECOVERING);
        assertTrue(slow.state() == EnemyState.CHASING || slow.state() == EnemyState.RECOVERING);
    }
}
