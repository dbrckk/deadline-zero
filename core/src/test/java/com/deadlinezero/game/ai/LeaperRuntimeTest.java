package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

final class LeaperRuntimeTest {
    @Test void registeredEnemyCanTelegraphAndImpactOnce() {
        LeaperRuntime runtime = new LeaperRuntime();
        Enemy enemy = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 46f, 3.15f, .38f, 12f, 10);
        runtime.register(enemy);
        assertTrue(runtime.contains(enemy));

        for (int i = 0; i < 240 && !runtime.telegraphing(enemy); i++) {
            runtime.update(enemy, .02f, 4f, 1f, 0f);
        }
        assertTrue(runtime.telegraphing(enemy));

        for (int i = 0; i < 20; i++) runtime.update(enemy, .02f, 4f, 1f, 0f);
        assertTrue(enemy.impulse.x > 0f);
        assertTrue(runtime.consumeImpact(enemy));
        assertFalse(runtime.consumeImpact(enemy));
    }

    @Test void unregisteredEnemyNeverActivates() {
        LeaperRuntime runtime = new LeaperRuntime();
        Enemy enemy = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 46f, 3.15f, .38f, 12f, 10);
        for (int i = 0; i < 200; i++) runtime.update(enemy, .02f, 4f, 1f, 0f);
        assertFalse(runtime.contains(enemy));
        assertFalse(runtime.telegraphing(enemy));
        assertFalse(runtime.consumeImpact(enemy));
    }
}
