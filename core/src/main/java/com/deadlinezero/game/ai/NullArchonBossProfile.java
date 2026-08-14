package com.deadlinezero.game.ai;

/** Late-game Null Sector boss tuning. Fast phase pressure with dense but compact projectile lattices. */
public final class NullArchonBossProfile {
    public static final float HP_MULTIPLIER = 1.20f;
    public static final float SPEED_MULTIPLIER = 1.12f;
    public static final float DAMAGE_MULTIPLIER = 1.16f;

    public static final float PHASE2_CHARGE_COOLDOWN = 3.40f;
    public static final float PHASE2_SUMMON_COOLDOWN = 7.20f;
    public static final int PHASE2_SUMMON_COUNT = 4;

    public static final float PHASE3_CHARGE_COOLDOWN = 2.45f;
    public static final float PHASE3_SUMMON_COOLDOWN = 4.80f;
    public static final int PHASE3_SUMMON_COUNT = 6;
    public static final float PHASE3_PULSE_COOLDOWN = 2.70f;

    public static final int ENRAGE_SHOTS = 30;
    public static final float ENRAGE_PROJECTILE_SPEED = 9.40f;
    public static final int ENRAGE_EXPLOSIVE_EVERY = 6;
    public static final float ENRAGE_EXPLOSION_RADIUS = 1.65f;

    private NullArchonBossProfile() { }
}
