package com.deadlinezero.game.meta;

/** Tracks deterministic bonus rewards earned from optional run encounters. */
public final class RunEncounterRuntime {
    private static long bonusCredits;
    private static int completedEncounters;

    private RunEncounterRuntime() { }

    public static void begin() {
        bonusCredits = 0L;
        completedEncounters = 0;
    }

    public static void award(long credits) {
        if (credits <= 0L) return;
        bonusCredits += credits;
        completedEncounters++;
    }

    public static long bonusCredits() { return bonusCredits; }
    public static int completedEncounters() { return completedEncounters; }

    public static long consumeBonusCredits() {
        long value = bonusCredits;
        bonusCredits = 0L;
        completedEncounters = 0;
        return value;
    }

    public static void end() {
        bonusCredits = 0L;
        completedEncounters = 0;
    }
}
