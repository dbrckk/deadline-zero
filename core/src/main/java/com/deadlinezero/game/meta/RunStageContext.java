package com.deadlinezero.game.meta;

/** Snapshot of the selected stage and run ordinal used by the active run. */
public final class RunStageContext {
    private static int activeStage = 1;
    private static int activeRunOrdinal;

    private RunStageContext() {}

    public static void begin(int selectedStage) {
        begin(selectedStage, 0);
    }

    public static void begin(int selectedStage, int runOrdinal) {
        activeStage = Math.max(1, selectedStage);
        activeRunOrdinal = Math.max(0, runOrdinal);
    }

    public static int stage() { return activeStage; }
    public static int runOrdinal() { return activeRunOrdinal; }

    /** Stable per-run seed suitable for deterministic encounter planning, not security. */
    public static int encounterSeed() {
        int x = activeStage * 0x45d9f3b + activeRunOrdinal * 0x119de1f3;
        x ^= x >>> 16;
        return x & 0x7fffffff;
    }
}
