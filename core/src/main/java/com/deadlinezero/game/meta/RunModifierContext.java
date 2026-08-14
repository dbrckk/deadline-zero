package com.deadlinezero.game.meta;

/** Active run-wide risk/reward contract plus deterministic pre-run offer generation. */
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

        public int rewardBonusPercent() { return Math.round((reward - 1f) * 100f); }

        /** Compact relative threat score used only for presentation, not combat math. */
        public int threatPercent() {
            float durability = Math.max(.72f, enemyHp);
            float tempo = enemySpeed / Math.max(.55f, spawnInterval);
            float pressure = durability * enemyDamage * tempo;
            return Math.max(100, Math.round(pressure * 100f));
        }
    }

    private static Modifier active;

    private RunModifierContext() {}

    private static int baseIndex() {
        Modifier[] values = Modifier.values();
        int stageOffset = Math.floorMod(RunStageContext.stage() * 2, values.length);
        int ordinalOffset = Math.floorMod(RunStageContext.runOrdinal() * 3, values.length);
        return (stageOffset + ordinalOffset) % values.length;
    }

    /** Three unique offers, stable for the same stage/run ordinal. */
    public static Modifier[] offers() {
        Modifier[] values = Modifier.values();
        int base = baseIndex();
        return new Modifier[] {
            values[base],
            values[(base + 2) % values.length],
            values[(base + 4) % values.length]
        };
    }

    /** Legacy/direct-run fallback: activates the first deterministic offer. */
    public static void begin() { active = offers()[0]; }

    /** Activates only a contract that belongs to the current run's offer set. */
    public static boolean choose(Modifier selection) {
        if (selection == null) return false;
        for (Modifier offered : offers()) {
            if (offered == selection) {
                active = selection;
                return true;
            }
        }
        return false;
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
