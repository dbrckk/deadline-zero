package com.deadlinezero.game.combat;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.world.SpatialHash;
import org.junit.jupiter.api.Test;

final class AutoTargetingSystemTest {
    private static Enemy enemy(Enemy.Type type, float x, float y) {
        return new Enemy(type, x, y, type == Enemy.Type.BOSS ? 1000f : 100f, .1f, .5f, 1f, 1);
    }

    @Test void nearestBasicThreatWinsWhenRolesAreEqual() {
        SpatialHash spatial = new SpatialHash(2.2f);
        Enemy near = enemy(Enemy.Type.SHAMBLER, 2f, 0f);
        Enemy far = enemy(Enemy.Type.SHAMBLER, 7f, 0f);
        Array<Enemy> enemies = new Array<>();
        enemies.add(near); enemies.add(far); spatial.rebuild(enemies);

        AutoTargetingSystem system = new AutoTargetingSystem(.10f, 18f);
        assertSame(near, system.update(.11f, 0f, 0f, spatial));
    }

    @Test void bossPriorityCanBeatModeratelyCloserBasicInfected() {
        SpatialHash spatial = new SpatialHash(2.2f);
        Enemy basic = enemy(Enemy.Type.SHAMBLER, 3f, 0f);
        Enemy boss = enemy(Enemy.Type.BOSS, 5f, 0f);
        Array<Enemy> enemies = new Array<>();
        enemies.add(basic); enemies.add(boss); spatial.rebuild(enemies);

        AutoTargetingSystem system = new AutoTargetingSystem(.10f, 18f);
        assertSame(boss, system.update(.11f, 0f, 0f, spatial));
    }

    @Test void currentTargetIsStickyUntilRefresh() {
        SpatialHash spatial = new SpatialHash(2.2f);
        Enemy first = enemy(Enemy.Type.SHAMBLER, 4f, 0f);
        Array<Enemy> enemies = new Array<>();
        enemies.add(first); spatial.rebuild(enemies);
        AutoTargetingSystem system = new AutoTargetingSystem(.20f, 18f);
        assertSame(first, system.update(.21f, 0f, 0f, spatial));

        Enemy slightlyBetter = enemy(Enemy.Type.SHAMBLER, 3.5f, 0f);
        enemies.add(slightlyBetter); spatial.rebuild(enemies);
        assertSame(first, system.update(.05f, 0f, 0f, spatial));
    }

    @Test void deadCurrentTargetIsDroppedImmediately() {
        SpatialHash spatial = new SpatialHash(2.2f);
        Enemy first = enemy(Enemy.Type.SHAMBLER, 2f, 0f);
        Enemy second = enemy(Enemy.Type.RUNNER, 4f, 0f);
        Array<Enemy> enemies = new Array<>();
        enemies.add(first); enemies.add(second); spatial.rebuild(enemies);
        AutoTargetingSystem system = new AutoTargetingSystem(.20f, 18f);
        assertSame(first, system.update(.21f, 0f, 0f, spatial));

        first.alive = false; spatial.rebuild(enemies);
        assertSame(second, system.update(.01f, 0f, 0f, spatial));
    }
}