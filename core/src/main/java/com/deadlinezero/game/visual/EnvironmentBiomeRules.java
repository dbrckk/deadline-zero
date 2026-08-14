package com.deadlinezero.game.visual;

/** Pure stage-to-biome routing used by environment presentation and tests. */
public final class EnvironmentBiomeRules {
    public enum Biome {
        QUARANTINE_YARD("QUARANTINE YARD"),
        CINDER_FOUNDRY("CINDER FOUNDRY");

        public final String label;
        Biome(String label) { this.label = label; }
    }

    public static final int CINDER_FOUNDRY_STAGE = 10;

    private EnvironmentBiomeRules() { }

    public static Biome forStage(int stage) {
        return Math.max(1, stage) >= CINDER_FOUNDRY_STAGE
            ? Biome.CINDER_FOUNDRY
            : Biome.QUARANTINE_YARD;
    }

    public static boolean isFoundry(int stage) {
        return forStage(stage) == Biome.CINDER_FOUNDRY;
    }
}
