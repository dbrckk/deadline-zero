package com.deadlinezero.game.visual;

import com.badlogic.gdx.utils.TimeUtils;

/** Presentation-only event bridge for authored animation/VFX timing without coupling renderers to gameplay code. */
public final class CombatVisualEvents {
    private static long lastPlayerShotNanos = Long.MIN_VALUE;
    private static long lastDashNanos = Long.MIN_VALUE;
    private static long lastLevelUpNanos = Long.MIN_VALUE;

    private CombatVisualEvents() {}

    public static void markPlayerShot() { lastPlayerShotNanos = TimeUtils.nanoTime(); }
    public static void markDash() { lastDashNanos = TimeUtils.nanoTime(); }
    public static void markLevelUp() { lastLevelUpNanos = TimeUtils.nanoTime(); }

    public static float playerShotAgeSeconds() { return age(lastPlayerShotNanos); }
    public static float dashAgeSeconds() { return age(lastDashNanos); }
    public static float levelUpAgeSeconds() { return age(lastLevelUpNanos); }

    private static float age(long nanos) {
        if (nanos == Long.MIN_VALUE) return Float.POSITIVE_INFINITY;
        long elapsed = Math.max(0L, TimeUtils.nanoTime() - nanos);
        return elapsed / 1_000_000_000f;
    }

    public static void reset() {
        lastPlayerShotNanos = Long.MIN_VALUE;
        lastDashNanos = Long.MIN_VALUE;
        lastLevelUpNanos = Long.MIN_VALUE;
    }
}
