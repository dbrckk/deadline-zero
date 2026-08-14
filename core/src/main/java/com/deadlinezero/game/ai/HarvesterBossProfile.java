package com.deadlinezero.game.ai;

/** Deterministic tuning contract for the pressure-oriented HARVESTER boss identity. */
public final class HarvesterBossProfile {
    private HarvesterBossProfile() { }

    public static final float HP_MULTIPLIER = 1.16f;
    public static final float SPEED_MULTIPLIER = 1.08f;
    public static final float DAMAGE_MULTIPLIER = 1.14f;

    public static final float PHASE2_CHARGE_COOLDOWN = 3.8f;
    public static final float PHASE3_CHARGE_COOLDOWN = 2.7f;
    public static final float PHASE2_CHARGE_DURATION = .48f;
    public static final float PHASE3_CHARGE_DURATION = .60f;

    public static final float PHASE2_SUMMON_COOLDOWN = 6.4f;
    public static final float PHASE3_SUMMON_COOLDOWN = 4.2f;
    public static final int PHASE2_SUMMON_COUNT = 5;
    public static final int PHASE3_SUMMON_COUNT = 8;

    public static final float PHASE3_PULSE_COOLDOWN = 3.1f;
    public static final int ENRAGE_SHOTS = 28;
    public static final float ENRAGE_PROJECTILE_SPEED = 8.8f;
    public static final int ENRAGE_EXPLOSIVE_EVERY = 5;
    public static final float ENRAGE_EXPLOSION_RADIUS = 1.75f;
}
