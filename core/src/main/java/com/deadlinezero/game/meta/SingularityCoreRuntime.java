package com.deadlinezero.game.meta;

/** Per-run deterministic cadence for the Zero-Day Singularity Core projectile passive. */
public final class SingularityCoreRuntime {
    private static boolean active;
    private static long shotSequence;

    private SingularityCoreRuntime() {}

    public static void begin(boolean enabled) {
        active = enabled;
        shotSequence = 0L;
    }

    public static boolean consumeShotMark() {
        if (!active) return false;
        shotSequence++;
        return SingularityCoreRules.markedShot(shotSequence);
    }

    public static boolean active() { return active; }
    public static long shotSequence() { return shotSequence; }
}
