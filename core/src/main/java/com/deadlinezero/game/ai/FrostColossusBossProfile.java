package com.deadlinezero.game.ai;

/** Stage-40 Cryogenic Depths apex boss. Heavy, deliberate area denial with compact explosive lattices. */
public final class FrostColossusBossProfile {
    public static final float HP_MULTIPLIER = 1.42f;
    public static final float SPEED_MULTIPLIER = .76f;
    public static final float DAMAGE_MULTIPLIER = 1.18f;

    public static final float PHASE2_CHARGE_COOLDOWN = 5.8f;
    public static final float PHASE3_CHARGE_COOLDOWN = 4.35f;
    public static final float PHASE2_CHARGE_DURATION = .92f;
    public static final float PHASE3_CHARGE_DURATION = 1.04f;

    public static final float PHASE2_SUMMON_COOLDOWN = 9.4f;
    public static final float PHASE3_SUMMON_COOLDOWN = 6.4f;
    public static final int PHASE2_SUMMON_COUNT = 3;
    public static final int PHASE3_SUMMON_COUNT = 5;

    public static final float PHASE3_PULSE_COOLDOWN = 4.25f;
    public static final int ENRAGE_SHOTS = 22;
    public static final float ENRAGE_PROJECTILE_SPEED = 7.35f;
    public static final int ENRAGE_EXPLOSIVE_EVERY = 3;
    public static final float ENRAGE_EXPLOSION_RADIUS = 2.75f;

    private FrostColossusBossProfile() { }
}
