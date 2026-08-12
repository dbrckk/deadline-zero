package com.deadlinezero.game.meta;

/** Snapshot of the selected stage used by the active run. */
public final class RunStageContext {
    private static int activeStage = 1;

    private RunStageContext() {}

    public static void begin(int selectedStage) {
        activeStage = Math.max(1, selectedStage);
    }

    public static int stage() { return activeStage; }
}
