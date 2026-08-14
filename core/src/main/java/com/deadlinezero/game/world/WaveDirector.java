package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageMissionRules;

/** Stage-aware wave pacing with readable pressure bands, squad bursts and named special encounters. */
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
    private final RunEncounterDirector encounters = new RunEncounterDirector(stage);

    public void update(float dt) {
        elapsed += dt;
        spawnTimer -= dt;
        encounters.update(dt, bossProgress());
        if (!bossSpawned && elapsed >= bossArrival) bossPending = true;
        RunMissionRuntime.update(elapsed, kills);
    }

    public boolean shouldSpawn() { return !bossSpawned && spawnTimer <= 0f; }

    public void onSpawn() {
        if (squadRemaining > 0) {
            squadRemaining--;
            spawnTimer = (.085f + MathUtils.random(0f, .035f)) * encounters.spawnIntervalMultiplier();
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
        spawnTimer = Math.max(.065f, (base - stageAcceleration - lateAcceleration) * encounters.spawnIntervalMultiplier());

        float squadChance = switch (pressureBand()) {
            case OPENING -> .025f;
            case BUILD -> .055f;
            case ASSAULT -> .085f;
            case CRISIS -> .12f;
        };
        squadChance = Math.min(.22f, squadChance + (stage - 1) * .008f);
        if (encounters.activeEncounter()) squadChance = Math.min(.32f, squadChance + .08f);
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
    public RunEncounterDirector.Type activeEncounter() { return encounters.active(); }
    public float encounterSecondsRemaining() { return encounters.remaining(); }

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
        Enemy.Type fallback = switch (pressureBand()) {
            case OPENING -> {
                if (stage >= 5 && r < .018f + stageBias * .08f) yield Enemy.Type.PHANTOM;
                if (stage >= 4 && r < .04f + stageBias * .20f) yield Enemy.Type.RANGED;
                if (r < .24f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case BUILD -> {
                if (stage >= 8 && r < .026f + stageBias * .05f) yield Enemy.Type.SHIELDED;
                if (stage >= 7 && r < .052f + stageBias * .08f) yield Enemy.Type.REGENERATOR;
                if (stage >= 6 && r < .080f + stageBias * .12f) yield Enemy.Type.PHANTOM;
                if (stage >= 6 && r < .115f + stageBias * .16f) yield Enemy.Type.ELITE;
                if (r < .20f + stageBias * .35f) yield Enemy.Type.RANGED;
                if (r < .36f + stageBias * .55f) yield Enemy.Type.BRUTE;
                if (r < .64f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case ASSAULT -> {
                if (stage >= 6 && r < .045f + stageBias * .10f) yield Enemy.Type.SHIELDED;
                if (stage >= 5 && r < .085f + stageBias * .14f) yield Enemy.Type.REGENERATOR;
                if (stage >= 5 && r < .130f + stageBias * .18f) yield Enemy.Type.PHANTOM;
                if (r < .185f + stageBias * .25f) yield Enemy.Type.ELITE;
                if (r < .31f + stageBias * .42f) yield Enemy.Type.RANGED;
                if (r < .50f + stageBias * .60f) yield Enemy.Type.BRUTE;
                if (r < .79f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
            case CRISIS -> {
                if (stage >= 5 && r < .065f + stageBias * .12f) yield Enemy.Type.SHIELDED;
                if (stage >= 5 && r < .125f + stageBias * .18f) yield Enemy.Type.REGENERATOR;
                if (stage >= 4 && r < .185f + stageBias * .22f) yield Enemy.Type.PHANTOM;
                if (r < .255f + stageBias * .30f) yield Enemy.Type.ELITE;
                if (r < .39f + stageBias * .45f) yield Enemy.Type.RANGED;
                if (r < .59f + stageBias * .68f) yield Enemy.Type.BRUTE;
                if (r < .86f + stageBias) yield Enemy.Type.RUNNER;
                yield Enemy.Type.SHAMBLER;
            }
        };
        return encounters.overrideType(MathUtils.random(), fallback);
    }
}
