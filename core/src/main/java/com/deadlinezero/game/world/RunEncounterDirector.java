package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunEncounterRuntime;
import com.deadlinezero.game.meta.RunStageContext;

/** Deterministic, stage-scaled pressure encounters embedded in a run. */
public final class RunEncounterDirector {
    public enum Type {
        NONE,
        SWARM_SURGE,
        HUNTER_PACK,
        JUGGERNAUT_PUSH,
        PHANTOM_BREACH,
        REGEN_BLOOM,
        BULWARK_LINE
    }

    private static final Type[] CATALOG = {
        Type.SWARM_SURGE,
        Type.HUNTER_PACK,
        Type.JUGGERNAUT_PUSH,
        Type.PHANTOM_BREACH,
        Type.REGEN_BLOOM,
        Type.BULWARK_LINE
    };

    private final int stage;
    private final Type[] plan = new Type[3];
    private Type active = Type.NONE;
    private float remaining;
    private int nextIndex;

    public RunEncounterDirector(int stage) {
        this.stage = Math.max(1, stage);
        buildPlan(RunStageContext.encounterSeed(), RunStageContext.runOrdinal());
    }

    private void buildPlan(int seed, int runOrdinal) {
        int safeOrdinal = Math.max(0, runOrdinal);
        int start = Math.floorMod(seed + safeOrdinal, CATALOG.length);
        int direction = (safeOrdinal & 1) == 0 ? 1 : -1;
        for (int i = 0; i < plan.length; i++) {
            plan[i] = CATALOG[Math.floorMod(start + i * direction, CATALOG.length)];
        }
    }

    public void update(float dt, float bossProgress) {
        if (active != Type.NONE) {
            remaining = Math.max(0f, remaining - Math.max(0f, dt));
            if (remaining <= 0f) completeActive();
            return;
        }
        if (nextIndex >= plan.length) return;

        float threshold = switch (nextIndex) {
            case 0 -> .30f;
            case 1 -> .56f;
            default -> .78f;
        };
        if (bossProgress >= threshold) start(nextIndex++);
    }

    private void start(int index) {
        active = plan[MathUtils.clamp(index, 0, plan.length - 1)];
        remaining = switch (active) {
            case SWARM_SURGE -> 8.5f;
            case HUNTER_PACK -> 9.5f;
            case JUGGERNAUT_PUSH -> 10.5f;
            case PHANTOM_BREACH -> 8.8f;
            case REGEN_BLOOM -> 10.2f;
            case BULWARK_LINE -> 10.8f;
            default -> 0f;
        };
    }

    private void completeActive() {
        long base = switch (active) {
            case SWARM_SURGE -> 18L;
            case HUNTER_PACK -> 26L;
            case JUGGERNAUT_PUSH -> 34L;
            case PHANTOM_BREACH -> 30L;
            case REGEN_BLOOM -> 32L;
            case BULWARK_LINE -> 36L;
            default -> 0L;
        };
        long stageBonus = Math.min(50L, Math.max(0, stage - 1) * 2L);
        RunEncounterRuntime.award(base + stageBonus);
        active = Type.NONE;
    }

    public float spawnIntervalMultiplier() {
        return switch (active) {
            case SWARM_SURGE -> .52f;
            case HUNTER_PACK -> .74f;
            case JUGGERNAUT_PUSH -> .82f;
            case PHANTOM_BREACH -> .66f;
            case REGEN_BLOOM -> .78f;
            case BULWARK_LINE -> .84f;
            default -> 1f;
        };
    }

    public Enemy.Type overrideType(float roll, Enemy.Type fallback) {
        roll = MathUtils.clamp(roll, 0f, 1f);
        Enemy.Type encounterType = switch (active) {
            case SWARM_SURGE -> roll < .68f ? Enemy.Type.RUNNER : Enemy.Type.SHAMBLER;
            case HUNTER_PACK -> roll < .48f ? Enemy.Type.RANGED : (roll < .78f ? Enemy.Type.RUNNER : Enemy.Type.ELITE);
            case JUGGERNAUT_PUSH -> roll < .58f ? Enemy.Type.BRUTE : (roll < .82f ? Enemy.Type.ELITE : Enemy.Type.RANGED);
            case PHANTOM_BREACH -> roll < .62f ? Enemy.Type.PHANTOM : (roll < .84f ? Enemy.Type.RUNNER : Enemy.Type.RANGED);
            case REGEN_BLOOM -> roll < .58f ? Enemy.Type.REGENERATOR : (roll < .82f ? Enemy.Type.SHAMBLER : Enemy.Type.BRUTE);
            case BULWARK_LINE -> roll < .56f ? Enemy.Type.SHIELDED : (roll < .80f ? Enemy.Type.BRUTE : Enemy.Type.ELITE);
            default -> fallback;
        };
        return BiomeEnemyRoster.remap(stage, roll, encounterType);
    }

    public Type active() { return active; }
    public boolean activeEncounter() { return active != Type.NONE; }
    public float remaining() { return remaining; }
    public int triggeredCount() { return nextIndex; }
    public Type planned(int index) {
        if (index < 0 || index >= plan.length) return Type.NONE;
        return plan[index];
    }
}
