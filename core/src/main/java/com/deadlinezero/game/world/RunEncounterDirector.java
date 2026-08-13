package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunEncounterRuntime;

/** Deterministic, stage-scaled pressure encounters embedded in a run. */
public final class RunEncounterDirector {
    public enum Type { NONE, SWARM_SURGE, HUNTER_PACK, JUGGERNAUT_PUSH }

    private final int stage;
    private Type active = Type.NONE;
    private float remaining;
    private int nextIndex;

    public RunEncounterDirector(int stage) {
        this.stage = Math.max(1, stage);
    }

    public void update(float dt, float bossProgress) {
        if (active != Type.NONE) {
            remaining = Math.max(0f, remaining - Math.max(0f, dt));
            if (remaining <= 0f) completeActive();
            return;
        }
        if (nextIndex >= 3) return;

        float threshold = switch (nextIndex) {
            case 0 -> .30f;
            case 1 -> .56f;
            default -> .78f;
        };
        if (bossProgress >= threshold) start(nextIndex++);
    }

    private void start(int index) {
        active = switch (index) {
            case 0 -> Type.SWARM_SURGE;
            case 1 -> Type.HUNTER_PACK;
            default -> Type.JUGGERNAUT_PUSH;
        };
        remaining = switch (active) {
            case SWARM_SURGE -> 8.5f;
            case HUNTER_PACK -> 9.5f;
            case JUGGERNAUT_PUSH -> 10.5f;
            default -> 0f;
        };
    }

    private void completeActive() {
        long base = switch (active) {
            case SWARM_SURGE -> 18L;
            case HUNTER_PACK -> 26L;
            case JUGGERNAUT_PUSH -> 34L;
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
            default -> 1f;
        };
    }

    public Enemy.Type overrideType(float roll, Enemy.Type fallback) {
        roll = MathUtils.clamp(roll, 0f, 1f);
        return switch (active) {
            case SWARM_SURGE -> roll < .68f ? Enemy.Type.RUNNER : Enemy.Type.SHAMBLER;
            case HUNTER_PACK -> roll < .48f ? Enemy.Type.RANGED : (roll < .78f ? Enemy.Type.RUNNER : Enemy.Type.ELITE);
            case JUGGERNAUT_PUSH -> roll < .58f ? Enemy.Type.BRUTE : (roll < .82f ? Enemy.Type.ELITE : Enemy.Type.RANGED);
            default -> fallback;
        };
    }

    public Type active() { return active; }
    public boolean activeEncounter() { return active != Type.NONE; }
    public float remaining() { return remaining; }
    public int triggeredCount() { return nextIndex; }
}
