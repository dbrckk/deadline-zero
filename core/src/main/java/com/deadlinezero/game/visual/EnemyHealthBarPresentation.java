package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;

/** Pure presentation policy for world-space enemy health bars. */
public final class EnemyHealthBarPresentation {
    private EnemyHealthBarPresentation() { }

    public static boolean visible(Enemy enemy) {
        if (enemy == null || !enemy.alive || enemy.type == Enemy.Type.BOSS) return false;
        if (enemy.type == Enemy.Type.ELITE) return true;
        return enemy.hp < enemy.maxHp - .001f;
    }

    public static float widthMultiplier(Enemy enemy) {
        if (enemy == null) return 1f;
        return enemy.type == Enemy.Type.ELITE ? 1.18f : 1f;
    }
}
