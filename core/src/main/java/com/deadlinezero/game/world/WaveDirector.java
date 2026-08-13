package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageMissionRules;

/** Stage-aware wave pacing with readable pressure bands and short squad bursts before the boss. */
public final class WaveDirector {
    public enum PressureBand { OPENING, BUILD, ASSAULT, CRISIS }

    private float elapsed;
    private float spawnTimer;
    private int kills;
    private int squadRemaining;
    private boolean bossPending;
    private boolean bossSpawned;
    private final int stage = Math.max(1, RunStageContext.stage());
    private final float bossArrival = StageMissionRules.bossArrivalSeconds(stage);

    public void update(float dt) {
        elapsed += dt;
        spawnTimer -= dt;
        if (!bossSpawned && elapsed >= bossArrival) bossPending = true;
        RunMissionRuntime.update(elapsed, kills);
    }

    public boolean shouldSpawn() { return !bossSpawned && spawnTimer <= 0f; }

    public void onSpawn() {
        if (squadRemaining > 0) {
            squadRemaining--;
            spawnTimer = .085f + MathUtils.random(0f, .035f);
            return;
        }

        float base = switch (pressureBand()) {
            case OPENING -> .58f;
            case BUILD -> .45f;
            case ASSAULT -> .34f;
            case CRISIS -> .25f;
        };
        float stageAcceleration = Math.min(.11f, (stage - 1) * .008f);
        float lateAcceleration = Math.min(.10f, elapsed * .00055f);
        spawnTimer = Math.max(.075f, base - stageAcceleration - lateAcceleration);

        float squadChance = switch (pressureBand()) {
            case OPENING -> .025f;
            case BUILD -> .055f;
            case ASSAULT -> .085f;
            case CRISIS -> .12f;
        };
        squadChance = Math.min(.22f, squadChance + (stage - 1) * .008f);
        if (!bossPending && MathUtils.random() < squadChance) {
            int min = pressureBand().ordinal() >= PressureBand.ASSAULT.ordinal() ? 2 : 1;
            int max = Math.min(5, min + 1 + stage / 5);
            squadRemaining = MathUtils.random(min, max);
        }
    }

    public void onBossSpawned() {
        bossSpawned = true;
        bossPending = false;
        squadRemaining = 0;
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
    public int squadRemaining() { return squadRemaining; }
    public float bossArrivalSeconds() { return bossArrival; }
    public float secondsUntilBoss() { return Math.max(0f, bossArrival - elapsed); }
    public float bossProgress() { return MathUtils.clamp(elapsed / Math.max(1f, bossArrival), 0f, 1f); }
    public boolean bossWarning() { return !bossSpawned && secondsUntilBoss() <= 30f; }

    public PressureBand pressureBand() {
        float p = bossProgress();
        if (p < .24f) return PressureBand.OPENING;
        if (p < .52f) return PressureBand.BUILD;
        if (p < .80f) return PressureBand.ASSAULT;
        return PressureBand.CRISIS;
    }

    public Enemy.Type chooseType() {
        if (bossPending) return Enemy.Type.BOSS;
        float r = MathUtils.random();
        float stageBias = Math.min(.14f, (stage - 1) * .012f);
        return switch (pressureBand()) {
            case OPENING -> {
                if (stage >= 4 && r < .04f + stageBias * .20f) yield Enemy.Type.RANGED;
                if (r < .24f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case BUILD -> {
                if (stage >= 6 && r < .025f + stageBias * .16f) yield Enemy.Type.ELITE;
                if (r < .12f + stageBias * .35f) yield Enemy.Type.RANGED;
                if (r < .28f + stageBias * .55f) yield Enemy.Type.BRUTE;
                if (r < .58f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case ASSAULT -> {
                if (r < .045f + stageBias * .25f) yield Enemy.Type.ELITE;
                if (r < .20f + stageBias * .42f) yield Enemy.Type.RANGED;
                if (r < .43f + stageBias * .60f) yield Enemy.Type.BRUTE;
                if (r < .76f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case CRISIS -> {
                if (r < .075f + stageBias * .30f) yield Enemy.Type.ELITE;
                if (r < .27f + stageBias * .45f) yield Enemy.Type.RANGED;
                if (r < .53f + stageBias * .68f) yield Enemy.Type.BRUTE;
                if (r < .84f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
        };
    }
}
