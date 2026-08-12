package com.deadlinezero.game.world;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.deadlinezero.game.entities.Enemy;

/** Broad-phase collision index. Buckets are retained and cleared, avoiding frame-by-frame allocation. */
public final class SpatialHash {
    private final float cellSize;
    private final IntMap<Array<Enemy>> cells = new IntMap<>();

    public SpatialHash(float cellSize) {
        this.cellSize = cellSize;
    }

    public void rebuild(Array<Enemy> enemies) {
        for (Array<Enemy> bucket : cells.values()) bucket.clear();
        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;
            int cx = floor(enemy.position.x / cellSize);
            int cy = floor(enemy.position.y / cellSize);
            int key = key(cx, cy);
            Array<Enemy> bucket = cells.get(key);
            if (bucket == null) {
                bucket = new Array<>(false, 16);
                cells.put(key, bucket);
            }
            bucket.add(enemy);
        }
    }

    public void query(float x, float y, float radius, Array<Enemy> out) {
        out.clear();
        int minX = floor((x - radius) / cellSize);
        int maxX = floor((x + radius) / cellSize);
        int minY = floor((y - radius) / cellSize);
        int maxY = floor((y + radius) / cellSize);
        for (int cy = minY; cy <= maxY; cy++) {
            for (int cx = minX; cx <= maxX; cx++) {
                Array<Enemy> bucket = cells.get(key(cx, cy));
                if (bucket != null) out.addAll(bucket);
            }
        }
    }

    private static int floor(float value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static int key(int x, int y) {
        return (x * 73856093) ^ (y * 19349663);
    }
}
