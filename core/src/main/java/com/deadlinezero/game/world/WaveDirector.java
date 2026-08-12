package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;

public final class WaveDirector {
    private float elapsed;
    private float spawnTimer;
    private int kills;
    private int bossesSpawned;
    private boolean bossPending;

    public void update(float dt) {
        elapsed += dt;
        spawnTimer -= dt;
        int expectedBosses = (int)(elapsed / 90f);
        if (expectedBosses > bossesSpawned) bossPending = true;
    }

    public boolean shouldSpawn() { return spawnTimer <= 0f; }

    public void onSpawn() {
        spawnTimer = Math.max(0.07f, 0.58f - elapsed * 0.0042f);
    }

    public void onBossSpawned() {
        bossesSpawned++;
        bossPending = false;
        spawnTimer = 1.2f;
    }

    public void onKill() { kills++; }
    public int kills() { return kills; }
    public float elapsed() { return elapsed; }
    public boolean bossPending() { return bossPending; }

    public Enemy.Type chooseType() {
        if (bossPending) return Enemy.Type.BOSS;
        float r = MathUtils.random();
        if (elapsed > 120f && r < .035f) return Enemy.Type.ELITE;
        if (elapsed > 70f && r < .14f) return Enemy.Type.RANGED;
        if (elapsed > 45f && r < .24f) return Enemy.Type.BRUTE;
        if (elapsed > 15f && r < .48f) return Enemy.Type.RUNNER;
        return Enemy.Type.SHAMBLER;
    }
}
