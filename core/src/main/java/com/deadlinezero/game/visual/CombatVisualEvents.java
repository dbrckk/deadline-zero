package com.deadlinezero.game.visual;

import com.badlogic.gdx.utils.TimeUtils;

/** Presentation-only event bridge for authored animation, VFX and audio timing without gameplay coupling. */
public final class CombatVisualEvents {
    private static long lastPlayerShotNanos = Long.MIN_VALUE;
    private static long lastDashNanos = Long.MIN_VALUE;
    private static long lastLevelUpNanos = Long.MIN_VALUE;
    private static long playerShotSerial;
    private static long dashSerial;
    private static long levelUpSerial;

    private CombatVisualEvents() {}

    public static void markPlayerShot() {
        lastPlayerShotNanos = TimeUtils.nanoTime();
        playerShotSerial++;
    }

    public static void markDash() {
        lastDashNanos = TimeUtils.nanoTime();
        dashSerial++;
    }

    public static void markLevelUp() {
        lastLevelUpNanos = TimeUtils.nanoTime();
        levelUpSerial++;
    }

    public static float playerShotAgeSeconds() { return age(lastPlayerShotNanos); }
    public static float dashAgeSeconds() { return age(lastDashNanos); }
    public static float levelUpAgeSeconds() { return age(lastLevelUpNanos); }
    public static long playerShotSerial() { return playerShotSerial; }
    public static long dashSerial() { return dashSerial; }
    public static long levelUpSerial() { return levelUpSerial; }

    private static float age(long nanos) {
        if (nanos == Long.MIN_VALUE) return Float.POSITIVE_INFINITY;
        long elapsed = Math.max(0L, TimeUtils.nanoTime() - nanos);
        return elapsed / 1_000_000_000f;
    }

    public static void reset() {
        lastPlayerShotNanos = Long.MIN_VALUE;
        lastDashNanos = Long.MIN_VALUE;
        lastLevelUpNanos = Long.MIN_VALUE;
        playerShotSerial = 0L;
        dashSerial = 0L;
        levelUpSerial = 0L;
    }
}
