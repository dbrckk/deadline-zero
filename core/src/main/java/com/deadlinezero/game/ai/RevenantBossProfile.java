package com.deadlinezero.game.ai;

/** Deterministic tuning contract for the faster REVENANT boss variant. */
public final class RevenantBossProfile {
    private RevenantBossProfile() { }

    public static final int MIN_STAGE = 4;
    public static final float HP_MULTIPLIER = .88f;
    public static final float SPEED_MULTIPLIER = 1.18f;
    public static final float DAMAGE_MULTIPLIER = 1.08f;

    public static final float PHASE2_CHARGE_COOLDOWN = 3.45f;
    public static final float PHASE3_CHARGE_COOLDOWN = 2.45f;
    public static final float PHASE2_SUMMON_COOLDOWN = 7.0f;
    public static final float PHASE3_SUMMON_COOLDOWN = 4.4f;
    public static final float PHASE3_PULSE_COOLDOWN = 3.05f;

    public static final int PHASE2_SUMMON_COUNT = 4;
    public static final int PHASE3_SUMMON_COUNT = 8;
    public static final int ENRAGE_SHOTS = 24;
    public static final float ENRAGE_PROJECTILE_SPEED = 9.0f;
    public static final int ENRAGE_EXPLOSIVE_EVERY = 3;
    public static final float ENRAGE_EXPLOSION_RADIUS = 2.15f;

    public static boolean unlocked(int stage) { return stage >= MIN_STAGE; }

    /** Alternates boss identities after unlock so runs gain predictable variety without RNG streaks. */
    public static boolean useForStage(int stage) {
        return unlocked(stage) && stage % 2 == 0;
    }
}
