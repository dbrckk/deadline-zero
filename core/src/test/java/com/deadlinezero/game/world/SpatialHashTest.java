package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class SpatialHashTest {
    private static Enemy enemy(float x, float y) {
        return new Enemy(Enemy.Type.SHAMBLER, x, y, 100f, 1f, .4f, 1f, 1);
    }

    @Test void nearestReturnsClosestAliveEnemyAcrossCellBoundaries() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();
        Enemy fartherSameCell = enemy(1.8f, 0f);
        Enemy closestNextCell = enemy(2.21f, 0f);
        Enemy far = enemy(8f, 0f);
        enemies.add(fartherSameCell);
        enemies.add(closestNextCell);
        enemies.add(far);

        hash.rebuild(enemies);

        assertSame(fartherSameCell, hash.nearest(0f, 0f));
        assertSame(closestNextCell, hash.nearest(2.15f, 0f));
    }

    @Test void nearestSkipsDeadEnemies() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();
        Enemy dead = enemy(.2f, 0f);
        Enemy alive = enemy(1.1f, 0f);
        dead.alive = false;
        enemies.add(dead);
        enemies.add(alive);

        hash.rebuild(enemies);

        assertSame(alive, hash.nearest(0f, 0f));
    }

    @Test void nearestReturnsNullForEmptyOrAllDeadIndex() {
        SpatialHash hash = new SpatialHash(2.2f);
        assertNull(hash.nearest(0f, 0f));

        Array<Enemy> enemies = new Array<>();
        Enemy dead = enemy(0f, 0f);
        dead.alive = false;
        enemies.add(dead);
        hash.rebuild(enemies);

        assertNull(hash.nearest(0f, 0f));
        assertEquals(0, hash.activeBucketCount());
    }

    @Test void nearestWithinHonorsRadiusAndExclusions() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();
        Enemy source = enemy(0f, 0f);
        Enemy excluded = enemy(1f, 0f);
        Enemy valid = enemy(2.8f, 0f);
        Enemy outside = enemy(3.41f, 0f);
        enemies.add(source);
        enemies.add(excluded);
        enemies.add(valid);
        enemies.add(outside);

        hash.rebuild(enemies);

        assertSame(valid, hash.nearestWithin(0f, 0f, 3.4f, source, excluded));
        assertNull(hash.nearestWithin(0f, 0f, .9f, source, excluded));
    }

    @Test void nearestWithinUsesTrueEuclideanRadiusNotOnlyCoveredCells() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();
        Enemy diagonalOutside = enemy(2.5f, 2.5f);
        Enemy inside = enemy(2.0f, 2.0f);
        enemies.add(diagonalOutside);
        enemies.add(inside);

        hash.rebuild(enemies);

        assertSame(inside, hash.nearestWithin(0f, 0f, 3.4f, null, null));
    }

    @Test void historicalBucketsDoNotStayActiveAcrossRebuilds() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();

        for (int i = 0; i < 120; i++) {
            enemies.clear();
            enemies.add(enemy(i * 2.3f, 0f));
            hash.rebuild(enemies);
            assertEquals(1, hash.activeBucketCount());
        }

        int retained = hash.retainedBucketCount();
        assertEquals(120, retained);

        enemies.clear();
        enemies.add(enemy(0f, 0f));
        hash.rebuild(enemies);

        assertEquals(1, hash.activeBucketCount());
        assertEquals(retained, hash.retainedBucketCount());
    }

    @Test void retainedBucketsAreReusedWhileActiveCountTracksCurrentPopulation() {
        SpatialHash hash = new SpatialHash(2.2f);
        Array<Enemy> enemies = new Array<>();
        enemies.add(enemy(-5f, 0f));
        enemies.add(enemy(5f, 0f));
        hash.rebuild(enemies);

        int retained = hash.retainedBucketCount();
        assertEquals(2, hash.activeBucketCount());
        assertEquals(2, retained);

        enemies.clear();
        enemies.add(enemy(-5f, 0f));
        hash.rebuild(enemies);

        assertEquals(1, hash.activeBucketCount());
        assertEquals(retained, hash.retainedBucketCount());
    }
}
