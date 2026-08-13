package com.deadlinezero.game.ai;

/** Data-only tuning contract for the upcoming LEAPER combat archetype. */
public final class LeaperProfile {
    private LeaperProfile() { }

    public static final float BASE_HP = 46f;
    public static final float BASE_SPEED = 3.15f;
    public static final float RADIUS = .38f;
    public static final float CONTACT_POWER = 12f;
    public static final int XP_VALUE = 10;

    public static final float LEAP_MIN_RANGE = 2.2f;
    public static final float LEAP_MAX_RANGE = 8.6f;
    public static final float LEAP_WINDUP = .20f;
    public static final float LEAP_IMPULSE = 4.25f;
    public static final float LEAP_COOLDOWN_MIN = 1.55f;
    public static final float LEAP_COOLDOWN_MAX = 1.85f;
    public static final float LEAP_IMPACT_WINDOW = .20f;

    public static boolean inLeapRange(float distance) {
        return distance >= LEAP_MIN_RANGE && distance <= LEAP_MAX_RANGE;
    }
}
