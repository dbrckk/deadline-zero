package com.deadlinezero.game.visual;

import com.badlogic.gdx.utils.TimeUtils;

/** Presentation-only event bridge for authored animation timing without coupling renderers to gameplay code. */
public final class CombatVisualEvents {
    private static long lastPlayerShotNanos = Long.MIN_VALUE;

    private CombatVisualEvents() {}

    public static void markPlayerShot() {
        lastPlayerShotNanos = TimeUtils.nanoTime();
    }

    public static float playerShotAgeSeconds() {
        if (lastPlayerShotNanos == Long.MIN_VALUE) return Float.POSITIVE_INFINITY;
        long elapsed = Math.max(0L, TimeUtils.nanoTime() - lastPlayerShotNanos);
        return elapsed / 1_000_000_000f;
    }

    public static void reset() {
        lastPlayerShotNanos = Long.MIN_VALUE;
    }
}
