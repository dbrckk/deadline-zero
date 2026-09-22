package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class EnemyHealthBarPresentationTest {
    @Test void fullHealthStandardEnemyDoesNotRenderWorldBar() {
        Enemy enemy = enemy(Enemy.Type.SHAMBLER);
        assertFalse(EnemyHealthBarPresentation.visible(enemy));
    }

    @Test void damagedStandardEnemyRendersWorldBar() {
        Enemy enemy = enemy(Enemy.Type.SHAMBLER);
        enemy.hp = enemy.maxHp * .72f;
        assertTrue(EnemyHealthBarPresentation.visible(enemy));
    }

    @Test void eliteKeepsPriorityBarAtFullHealth() {
        Enemy enemy = enemy(Enemy.Type.ELITE);
        assertTrue(EnemyHealthBarPresentation.visible(enemy));
        assertTrue(EnemyHealthBarPresentation.widthMultiplier(enemy) > 1f);
    }

    @Test void bossNeverUsesWorldBarBecauseHudOwnsBossHealth() {
        Enemy boss = enemy(Enemy.Type.BOSS);
        assertFalse(EnemyHealthBarPresentation.visible(boss));
    }

    private static Enemy enemy(Enemy.Type type) {
        return new Enemy(type, 0f, 0f, 100f, 1f, .5f, 5f, 1);
    }
}
