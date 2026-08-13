package com.deadlinezero.game.visual;

/** Presentation-only contract for the upcoming LEAPER enemy. */
public final class LeaperPresentationProfile {
    public static final float HEIGHT = 1.42f;
    public static final float FOOT_OFFSET = .40f;

    public static final float IDLE_FRAME = .12f;
    public static final float RUN_FRAME = .064f;
    public static final float ATTACK_FRAME = .058f;
    public static final float HIT_FRAME = .050f;
    public static final float DEATH_FRAME = .100f;

    public static final float TELEGRAPH_R = .92f;
    public static final float TELEGRAPH_G = .36f;
    public static final float TELEGRAPH_B = .92f;
    public static final float TELEGRAPH_SCALE = 1.10f;

    public static final String ART_ROOT = "enemy/leaper";

    private LeaperPresentationProfile() { }
}
