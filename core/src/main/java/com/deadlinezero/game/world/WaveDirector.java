package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageMissionRules;

public final class WaveDirector {
    private float elapsed;
    private float spawnTimer;
    private int kills;
    private boolean bossPending;
    private boolean bossSpawned;
    private final float bossArrival = StageMissionRules.bossArrivalSeconds(RunStageContext.stage());

    public void update(float dt) {
        elapsed += dt;
        spawnTimer -= dt;
        if (!bossSpawned && elapsed >= bossArrival) bossPending = true;
        RunMissionRuntime.update(elapsed, kills);
    }

    public boolean shouldSpawn() { return !bossSpawned && spawnTimer <= 0f; }

    public void onSpawn() {
        spawnTimer = Math.max(0.07f, 0.58f - elapsed * 0.0042f);
    }

    public void onBossSpawned() {
        bossSpawned = true;
        bossPending = false;
        spawnTimer = Float.MAX_VALUE;
    }

    public void onKill() {
        kills++;
        RunMissionRuntime.update(elapsed, kills);
    }

    public int kills() { return kills; }
    public float elapsed() { return elapsed; }
    public boolean bossPending() { return bossPending; }
    public boolean bossSpawned() { return bossSpawned; }
    public float bossArrivalSeconds() { return bossArrival; }
    public float secondsUntilBoss() { return Math.max(0f, bossArrival - elapsed); }
    public float bossProgress() { return MathUtils.clamp(elapsed / Math.max(1f, bossArrival), 0f, 1f); }
    public boolean bossWarning() { return !bossSpawned && secondsUntilBoss() <= 30f; }

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
