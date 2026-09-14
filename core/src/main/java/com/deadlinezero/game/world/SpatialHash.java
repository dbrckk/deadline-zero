package com.deadlinezero.game.world;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.deadlinezero.game.entities.Enemy;

/** Broad-phase collision index. Buckets are retained and cleared, avoiding frame-by-frame allocation. */
public final class SpatialHash {
    private final float cellSize;
    private final IntMap<Array<Enemy>> cells = new IntMap<>();
    private final Array<Array<Enemy>> activeBuckets = new Array<>(false, 64);
    private int activeBucketCount;
    private int maxQueryRing;

    public SpatialHash(float cellSize) {
        this.cellSize = cellSize;
    }

    public void add(Enemy enemy) {
        if (enemy == null || !enemy.alive) return;
        int cx = floor(enemy.position.x / cellSize);
        int cy = floor(enemy.position.y / cellSize);
        maxQueryRing = Math.max(maxQueryRing, Math.max(Math.abs(cx), Math.abs(cy)));
        int key = key(cx, cy);
        Array<Enemy> bucket = cells.get(key);
        if (bucket == null) {
            bucket = new Array<>(false, 16);
            cells.put(key, bucket);
        }
        if (bucket.size == 0) {
            activeBuckets.add(bucket);
            activeBucketCount++;
        }
        bucket.add(enemy);
    }

    public void rebuild(Array<Enemy> enemies) {
        for (Array<Enemy> bucket : activeBuckets) bucket.clear();
        activeBuckets.clear();
        activeBucketCount = 0;
        maxQueryRing = 0;
        for (Enemy enemy : enemies) add(enemy);
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

    /** Finds the nearest alive enemy without allocating a candidate collection. */
    public Enemy nearest(float x, float y) {
        if (activeBucketCount == 0) return null;
        int originX = floor(x / cellSize);
        int originY = floor(y / cellSize);
        Enemy best = null;
        float bestD2 = Float.MAX_VALUE;
        int limit = maxQueryRing + Math.max(Math.abs(originX), Math.abs(originY)) + 1;
        for (int ring = 0; ring <= limit; ring++) {
            int minX = originX - ring, maxX = originX + ring;
            int minY = originY - ring, maxY = originY + ring;
            for (int cy = minY; cy <= maxY; cy++) {
                for (int cx = minX; cx <= maxX; cx++) {
                    if (ring > 0 && cx != minX && cx != maxX && cy != minY && cy != maxY) continue;
                    Array<Enemy> bucket = cells.get(key(cx, cy));
                    if (bucket == null) continue;
                    for (Enemy enemy : bucket) {
                        if (!enemy.alive) continue;
                        float dx = enemy.position.x - x;
                        float dy = enemy.position.y - y;
                        float d2 = dx * dx + dy * dy;
                        if (d2 < bestD2) { bestD2 = d2; best = enemy; }
                    }
                }
            }
            if (best != null) {
                float outside = Math.max(0f, ring * cellSize - cellSize);
                if (outside * outside > bestD2) break;
            }
        }
        return best;
    }

    /** Finds the nearest alive enemy inside radius, excluding up to two identities. */
    public Enemy nearestWithin(float x, float y, float radius, Enemy excludeA, Enemy excludeB) {
        if (activeBucketCount == 0 || radius <= 0f) return null;
        int minX = floor((x - radius) / cellSize);
        int maxX = floor((x + radius) / cellSize);
        int minY = floor((y - radius) / cellSize);
        int maxY = floor((y + radius) / cellSize);
        float bestD2 = radius * radius;
        Enemy best = null;
        for (int cy = minY; cy <= maxY; cy++) {
            for (int cx = minX; cx <= maxX; cx++) {
                Array<Enemy> bucket = cells.get(key(cx, cy));
                if (bucket == null) continue;
                for (Enemy enemy : bucket) {
                    if (!enemy.alive || enemy == excludeA || enemy == excludeB) continue;
                    float dx = enemy.position.x - x;
                    float dy = enemy.position.y - y;
                    float d2 = dx * dx + dy * dy;
                    if (d2 < bestD2) {
                        bestD2 = d2;
                        best = enemy;
                    }
                }
            }
        }
        return best;
    }

    public int activeBucketCount() {
        return activeBucketCount;
    }

    public int retainedBucketCount() {
        return cells.size;
    }

    private static int floor(float value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static int key(int x, int y) {
        return (x * 73856093) ^ (y * 19349663);
    }
}
