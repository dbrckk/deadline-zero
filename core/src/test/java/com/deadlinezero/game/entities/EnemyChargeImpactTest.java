package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

public final class EnemyChargeImpactTest {
    @Test public void chargeImpactCanOnlyBeConsumedOncePerCharge() {
        RunStageContext.begin(1);
        Enemy e = new Enemy(Enemy.Type.BRUTE, 0f, 0f, 200f, 1.6f, .72f, 18f, 10);
        e.velocity.set(1f, 0f);

        e.updateAi(.01f, 4f);
        e.updateStatus(.40f);
        e.updateAi(.01f, 4f);

        assertTrue(e.chargeImpactActive());
        assertTrue(e.consumeChargeImpact());
        assertFalse(e.consumeChargeImpact());
        assertFalse(e.chargeImpactActive());
    }
}
