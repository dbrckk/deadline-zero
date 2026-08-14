package com.deadlinezero.game.meta;

/** Active run-wide risk/reward contract. Selection is deterministic from stage and run ordinal. */
public final class RunModifierContext {
    public enum Modifier {
        OVERCLOCKED("OVERCLOCKED", "Faster hostiles, denser pressure", 1.00f, 1.18f, 1.08f, .88f, 1.20f),
        GLASS_HORDE("GLASS HORDE", "Fragile enemies, lethal swarm density", .72f, 1.06f, 1.18f, .68f, 1.18f),
        BLOOD_MOON("BLOOD MOON", "Tougher and harder-hitting hostiles", 1.15f, 1.04f, 1.22f, .94f, 1.28f),
        ELITE_HUNT("ELITE HUNT", "Specialists and elites dominate", 1.08f, 1.04f, 1.10f, .92f, 1.24f),
        REDLINE("REDLINE", "Everything accelerates toward the boss", 1.08f, 1.14f, 1.16f, .78f, 1.30f);

        public final String title;
        public final String description;
        public final float enemyHp;
        public final float enemySpeed;
        public final float enemyDamage;
        public final float spawnInterval;
        public final float reward;

        Modifier(String title, String description, float enemyHp, float enemySpeed, float enemyDamage,
                 float spawnInterval, float reward) {
            this.title = title;
            this.description = description;
            this.enemyHp = enemyHp;
            this.enemySpeed = enemySpeed;
            this.enemyDamage = enemyDamage;
            this.spawnInterval = spawnInterval;
            this.reward = reward;
        }
    }

    private static Modifier active;

    private RunModifierContext() {}

    public static void begin() {
        Modifier[] values = Modifier.values();
        int stageOffset = Math.floorMod(RunStageContext.stage() * 2, values.length);
        int ordinalOffset = Math.floorMod(RunStageContext.runOrdinal() * 3, values.length);
        active = values[(stageOffset + ordinalOffset) % values.length];
    }

    public static void end() { active = null; }
    public static boolean active() { return active != null; }
    public static Modifier modifier() { return active; }
    public static String title() { return active == null ? "STANDARD" : active.title; }
    public static String description() { return active == null ? "Standard combat parameters" : active.description; }
    public static float enemyHpMultiplier() { return active == null ? 1f : active.enemyHp; }
    public static float enemySpeedMultiplier() { return active == null ? 1f : active.enemySpeed; }
    public static float enemyDamageMultiplier() { return active == null ? 1f : active.enemyDamage; }
    public static float spawnIntervalMultiplier() { return active == null ? 1f : active.spawnInterval; }
    public static float rewardMultiplier() { return active == null ? 1f : active.reward; }
    public static int rewardBonusPercent() { return Math.round((rewardMultiplier() - 1f) * 100f); }
    public static boolean eliteHunt() { return active == Modifier.ELITE_HUNT; }
}
