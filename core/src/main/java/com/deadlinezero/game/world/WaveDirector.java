package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;

public final class WaveDirector {
    private float elapsed;
    private float spawnTimer;
    private int kills;
    public void update(float dt) { elapsed += dt; spawnTimer -= dt; }
    public boolean shouldSpawn() { return spawnTimer <= 0f; }
    public void onSpawn() { spawnTimer = Math.max(0.07f, 0.58f - elapsed * 0.0042f); }
    public void onKill() { kills++; }
    public int kills() { return kills; }
    public float elapsed() { return elapsed; }
    public Enemy.Type chooseType() {
        float r = MathUtils.random();
        if (elapsed > 120 && r < .025f) return Enemy.Type.ELITE;
        if (elapsed > 45 && r < .18f) return Enemy.Type.BRUTE;
        if (elapsed > 15 && r < .40f) return Enemy.Type.RUNNER;
        return Enemy.Type.SHAMBLER;
    }
}
