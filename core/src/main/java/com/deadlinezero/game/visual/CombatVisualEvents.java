package com.deadlinezero.game.visual;

import com.badlogic.gdx.utils.TimeUtils;

/** Presentation-only event bridge for authored animation, VFX and audio timing without gameplay coupling. */
public final class CombatVisualEvents {
    public enum ProtocolCue { NONE, RHYTHM, KILLCHAIN_ARMED, KILLCHAIN, COMBINED, REACTION }
    private static long lastPlayerShotNanos = Long.MIN_VALUE;
    private static long lastDashNanos = Long.MIN_VALUE;
    private static long lastLevelUpNanos = Long.MIN_VALUE;
    private static long playerShotSerial;
    private static long dashSerial;
    private static long levelUpSerial;
    private static long lastProtocolNanos = Long.MIN_VALUE;
    private static long protocolSerial;
    private static ProtocolCue protocolCue = ProtocolCue.NONE;
    private static long lastSynergyNanos = Long.MIN_VALUE;
    private static long synergySerial;
    private static String synergyKey;

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

    public static void markProtocol(ProtocolCue cue) {
        if (cue == null || cue == ProtocolCue.NONE) return;
        protocolCue = cue;
        lastProtocolNanos = TimeUtils.nanoTime();
        protocolSerial++;
    }

    public static void markSynergy(String key) {
        if (key == null || key.isBlank()) return;
        synergyKey = key;
        lastSynergyNanos = TimeUtils.nanoTime();
        synergySerial++;
    }

    public static float playerShotAgeSeconds() { return age(lastPlayerShotNanos); }
    public static float dashAgeSeconds() { return age(lastDashNanos); }
    public static float levelUpAgeSeconds() { return age(lastLevelUpNanos); }
    public static long playerShotSerial() { return playerShotSerial; }
    public static long dashSerial() { return dashSerial; }
    public static long levelUpSerial() { return levelUpSerial; }
    public static float protocolAgeSeconds() { return age(lastProtocolNanos); }
    public static long protocolSerial() { return protocolSerial; }
    public static ProtocolCue protocolCue() { return protocolCue; }
    public static float synergyAgeSeconds() { return age(lastSynergyNanos); }
    public static long synergySerial() { return synergySerial; }
    public static String synergyKey() { return synergyKey; }

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
        lastProtocolNanos = Long.MIN_VALUE;
        protocolSerial = 0L;
        protocolCue = ProtocolCue.NONE;
        lastSynergyNanos = Long.MIN_VALUE;
        synergySerial = 0L;
        synergyKey = null;
    }
}
