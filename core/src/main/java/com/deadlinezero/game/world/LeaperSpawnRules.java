package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;

/** Stage and pressure-band spawn contract for the LEAPER archetype. */
public final class LeaperSpawnRules {
    public static final int MIN_STAGE = 3;
    public static final float MAX_SHARE = .18f;

    private LeaperSpawnRules() { }

    public static boolean unlocked(int stage) {
        return stage >= MIN_STAGE;
    }

    public static float share(int stage, WaveDirector.PressureBand band) {
        if (!unlocked(stage) || band == null) return 0f;
        float base = switch (band) {
            case OPENING -> .015f;
            case BUILD -> .045f;
            case ASSAULT -> .085f;
            case CRISIS -> .115f;
        };
        float stageBonus = Math.max(0, stage - MIN_STAGE) * .0075f;
        return MathUtils.clamp(base + stageBonus, 0f, MAX_SHARE);
    }
}
