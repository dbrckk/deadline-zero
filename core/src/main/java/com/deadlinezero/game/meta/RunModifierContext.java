package com.deadlinezero.game.meta;

/** Active run-wide risk/reward contract plus deterministic pre-run offer generation. */
public final class RunModifierContext {
    public enum Rarity { STANDARD, LEGENDARY }

    public enum Modifier {
        OVERCLOCKED(Rarity.STANDARD, "OVERCLOCKED", "Faster hostiles, denser pressure", 1.00f, 1.18f, 1.08f, .88f, 1.20f),
        GLASS_HORDE(Rarity.STANDARD, "GLASS HORDE", "Fragile enemies, lethal swarm density", .72f, 1.06f, 1.18f, .68f, 1.18f),
        BLOOD_MOON(Rarity.STANDARD, "BLOOD MOON", "Tougher and harder-hitting hostiles", 1.15f, 1.04f, 1.22f, .94f, 1.28f),
        ELITE_HUNT(Rarity.STANDARD, "ELITE HUNT", "Specialists and elites dominate", 1.08f, 1.04f, 1.10f, .92f, 1.24f),
        REDLINE(Rarity.STANDARD, "REDLINE", "Everything accelerates toward the boss", 1.08f, 1.14f, 1.16f, .78f, 1.30f),
        VOLATILE_DEAD(Rarity.LEGENDARY, "VOLATILE DEAD", "Killed hostiles detonate in lethal chain reactions", 1.10f, 1.06f, 1.16f, .86f, 1.48f),
        TWIN_APEX(Rarity.LEGENDARY, "TWIN APEX", "Two apex signals enter the arena together", 1.06f, 1.04f, 1.12f, .92f, 1.55f),
        VOID_STORM(Rarity.LEGENDARY, "VOID STORM", "Tracking anomaly zones repeatedly threaten your position", 1.08f, 1.08f, 1.14f, .84f, 1.52f);

        public final Rarity rarity;
        public final String title;
        public final String description;
        public final float enemyHp;
        public final float enemySpeed;
        public final float enemyDamage;
        public final float spawnInterval;
        public final float reward;

        Modifier(Rarity rarity, String title, String description, float enemyHp, float enemySpeed, float enemyDamage,
                 float spawnInterval, float reward) {
            this.rarity = rarity;
            this.title = title;
            this.description = description;
            this.enemyHp = enemyHp;
            this.enemySpeed = enemySpeed;
            this.enemyDamage = enemyDamage;
            this.spawnInterval = spawnInterval;
            this.reward = reward;
        }

        public int rewardBonusPercent() { return Math.round((reward - 1f) * 100f); }
        public boolean legendary() { return rarity == Rarity.LEGENDARY; }

        /** Compact relative threat score used only for presentation, not combat math. */
        public int threatPercent() {
            float durability = Math.max(.72f, enemyHp);
            float tempo = enemySpeed / Math.max(.55f, spawnInterval);
            float pressure = durability * enemyDamage * tempo;
            if (legendary()) pressure *= 1.22f;
            return Math.max(100, Math.round(pressure * 100f));
        }
    }

    private static final Modifier[] STANDARD = {
        Modifier.OVERCLOCKED, Modifier.GLASS_HORDE, Modifier.BLOOD_MOON, Modifier.ELITE_HUNT, Modifier.REDLINE
    };
    private static final Modifier[] LEGENDARY = {
        Modifier.VOLATILE_DEAD, Modifier.TWIN_APEX, Modifier.VOID_STORM
    };
    private static Modifier active;

    private RunModifierContext() {}

    private static int standardBaseIndex() {
        int stageOffset = Math.floorMod(RunStageContext.stage() * 2, STANDARD.length);
        int ordinalOffset = Math.floorMod(RunStageContext.runOrdinal() * 3, STANDARD.length);
        return (stageOffset + ordinalOffset) % STANDARD.length;
    }

    /** Legendary contracts enter one deterministic offer slot roughly every fourth run from stage 3 onward. */
    public static boolean legendaryOfferAvailable() {
        return RunStageContext.stage() >= 3 && Math.floorMod(RunStageContext.stage() + RunStageContext.runOrdinal(), 4) == 0;
    }

    /** Three unique offers, stable for the same stage/run ordinal. */
    public static Modifier[] offers() {
        int base = standardBaseIndex();
        Modifier[] offers = {
            STANDARD[base],
            STANDARD[(base + 2) % STANDARD.length],
            STANDARD[(base + 4) % STANDARD.length]
        };
        if (legendaryOfferAvailable()) {
            int legendaryIndex = Math.floorMod(RunStageContext.stage() * 5 + RunStageContext.runOrdinal(), LEGENDARY.length);
            offers[2] = LEGENDARY[legendaryIndex];
        }
        return offers;
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
    public static boolean volatileDead() { return active == Modifier.VOLATILE_DEAD; }
    public static boolean twinApex() { return active == Modifier.TWIN_APEX; }
    public static boolean voidStorm() { return active == Modifier.VOID_STORM; }
    public static int requiredBossKills() { return twinApex() ? 2 : 1; }
}
