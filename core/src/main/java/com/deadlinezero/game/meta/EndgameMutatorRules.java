package com.deadlinezero.game.meta;

/**
 * Deterministic high-Threat run mutators. These are intentionally modest overlays on top of
 * contracts and Threat scaling: they create different pressure profiles without producing
 * abrupt stat spikes or requiring network state.
 */
public final class EndgameMutatorRules {
    public enum Mutator {
        NONE("STANDARD PRESSURE", 1f, 1f, 1f, 1f, 1f),
        FRENZY("FRENZY", 1f, 1.05f, 1.03f, .95f, 1.05f),
        BULWARK("BULWARK", 1.10f, .98f, 1.03f, 1f, 1.06f),
        VOLATILE("VOLATILE", 1.02f, 1.02f, 1.10f, .98f, 1.07f),
        SWARM("SWARM", .92f, 1.03f, 1.02f, .88f, 1.08f);

        public final String label;
        public final float enemyHp;
        public final float enemySpeed;
        public final float enemyDamage;
        public final float spawnInterval;
        public final float reward;

        Mutator(String label, float enemyHp, float enemySpeed, float enemyDamage,
                float spawnInterval, float reward) {
            this.label = label;
            this.enemyHp = enemyHp;
            this.enemySpeed = enemySpeed;
            this.enemyDamage = enemyDamage;
            this.spawnInterval = spawnInterval;
            this.reward = reward;
        }
    }

    private static final Mutator[] ROTATION = {
        Mutator.FRENZY, Mutator.BULWARK, Mutator.VOLATILE, Mutator.SWARM
    };

    private EndgameMutatorRules() { }

    /** Mutators begin at Threat 3 and are stable for the same run identity. */
    public static Mutator current() {
        int threat = RunStageContext.threatTier();
        if (threat < 3) return Mutator.NONE;
        int index = Math.floorMod(
            RunStageContext.stage() * 7 + RunStageContext.runOrdinal() * 11 + threat * 13,
            ROTATION.length);
        return ROTATION[index];
    }

    public static boolean active() { return current() != Mutator.NONE; }
    public static String label() { return current().label; }
    public static float enemyHpMultiplier() { return current().enemyHp; }
    public static float enemySpeedMultiplier() { return current().enemySpeed; }
    public static float enemyDamageMultiplier() { return current().enemyDamage; }
    public static float spawnIntervalMultiplier() { return current().spawnInterval; }
    public static float rewardMultiplier() { return current().reward; }
}
