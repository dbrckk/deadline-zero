package com.deadlinezero.game.visual;

/** Pure stage-to-biome routing used by environment presentation and tests. */
public final class EnvironmentBiomeRules {
    public enum Biome {
        QUARANTINE_YARD("QUARANTINE YARD"),
        CINDER_FOUNDRY("CINDER FOUNDRY"),
        NULL_SECTOR("NULL SECTOR"),
        CRYO_VAULT("CRYO VAULT"),
        CRYOGENIC_DEPTHS("CRYOGENIC DEPTHS");

        public final String label;
        Biome(String label) { this.label = label; }
    }

    public static final int CINDER_FOUNDRY_STAGE = 10;
    public static final int NULL_SECTOR_STAGE = 20;
    public static final int CRYO_VAULT_STAGE = 30;
    public static final int CRYOGENIC_DEPTHS_STAGE = 40;

    private EnvironmentBiomeRules() { }

    public static Biome forStage(int stage) {
        int safe = Math.max(1, stage);
        if (safe >= CRYOGENIC_DEPTHS_STAGE) return Biome.CRYOGENIC_DEPTHS;
        if (safe >= CRYO_VAULT_STAGE) return Biome.CRYO_VAULT;
        if (safe >= NULL_SECTOR_STAGE) return Biome.NULL_SECTOR;
        if (safe >= CINDER_FOUNDRY_STAGE) return Biome.CINDER_FOUNDRY;
        return Biome.QUARANTINE_YARD;
    }

    public static boolean isFoundry(int stage) {
        return forStage(stage) == Biome.CINDER_FOUNDRY;
    }

    public static boolean isNullSector(int stage) {
        return forStage(stage) == Biome.NULL_SECTOR;
    }

    public static boolean isCryoVault(int stage) {
        return forStage(stage) == Biome.CRYO_VAULT;
    }

    public static boolean isCryogenicDepths(int stage) {
        return forStage(stage) == Biome.CRYOGENIC_DEPTHS;
    }
}
