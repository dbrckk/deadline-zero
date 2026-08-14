package com.deadlinezero.game.meta;

/** Snapshot of the selected stage, run ordinal and endgame threat tier used by the active run. */
public final class RunStageContext {
    private static int activeStage = 1;
    private static int activeRunOrdinal;
    private static int activeThreatTier;

    private RunStageContext() {}

    public static void begin(int selectedStage) {
        begin(selectedStage, 0, 0);
    }

    public static void begin(int selectedStage, int runOrdinal) {
        begin(selectedStage, runOrdinal, 0);
    }

    public static void begin(int selectedStage, int runOrdinal, int threatTier) {
        activeStage = Math.max(1, selectedStage);
        activeRunOrdinal = Math.max(0, runOrdinal);
        activeThreatTier = ThreatTierRules.sanitizeTier(threatTier);
        BalanceTelemetryRuntime.begin(activeStage, activeRunOrdinal, activeThreatTier);
    }

    public static int stage() { return activeStage; }
    public static int runOrdinal() { return activeRunOrdinal; }
    public static int threatTier() { return activeThreatTier; }

    /** Stable per-run seed suitable for deterministic encounter planning, not security. */
    public static int encounterSeed() {
        int x = activeStage * 0x45d9f3b + activeRunOrdinal * 0x119de1f3 + activeThreatTier * 0x27d4eb2d;
        x ^= x >>> 16;
        return x & 0x7fffffff;
    }
}
