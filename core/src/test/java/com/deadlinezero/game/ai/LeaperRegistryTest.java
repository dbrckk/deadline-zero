package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

final class LeaperRegistryTest {
    @Test
    void registryMarksOnlyRegisteredEnemyInstances() {
        LeaperRegistry registry = new LeaperRegistry();
        Enemy a = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 10f, 2f, .3f, 1f, 1);
        Enemy b = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 10f, 2f, .3f, 1f, 1);

        assertFalse(registry.contains(a));
        assertFalse(registry.contains(b));
        registry.register(a);
        assertTrue(registry.contains(a));
        assertFalse(registry.contains(b));
    }

    @Test
    void nullRegistrationIsIgnoredSafely() {
        LeaperRegistry registry = new LeaperRegistry();
        registry.register(null);
        assertFalse(registry.contains(null));
    }
}
