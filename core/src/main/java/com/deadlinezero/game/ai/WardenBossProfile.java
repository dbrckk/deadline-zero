package com.deadlinezero.game.ai;

/** Deterministic tuning contract for the heavy WARDEN boss identity. */
public final class WardenBossProfile {
    private WardenBossProfile() { }

    public static final float HP_MULTIPLIER = 1.34f;
    public static final float SPEED_MULTIPLIER = .82f;
    public static final float DAMAGE_MULTIPLIER = 1.22f;

    public static final float PHASE2_CHARGE_COOLDOWN = 5.4f;
    public static final float PHASE3_CHARGE_COOLDOWN = 4.1f;
    public static final float PHASE2_CHARGE_DURATION = .82f;
    public static final float PHASE3_CHARGE_DURATION = .96f;

    public static final float PHASE2_SUMMON_COOLDOWN = 10.5f;
    public static final float PHASE3_SUMMON_COOLDOWN = 7.6f;
    public static final int PHASE2_SUMMON_COUNT = 2;
    public static final int PHASE3_SUMMON_COUNT = 4;

    public static final float PHASE3_PULSE_COOLDOWN = 4.8f;
    public static final int ENRAGE_SHOTS = 12;
    public static final float ENRAGE_PROJECTILE_SPEED = 6.7f;
    public static final int ENRAGE_EXPLOSIVE_EVERY = 2;
    public static final float ENRAGE_EXPLOSION_RADIUS = 2.85f;
}
