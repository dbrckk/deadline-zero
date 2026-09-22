package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.MathUtils;

/** Pure timing/comfort profile for the short non-blocking camera reveal when a boss enters. */
public final class BossRevealCameraProfile {
    public static final float DURATION = 1.20f;
    public static final float MAX_FOCUS_WEIGHT = .76f;
    public static final float MAX_ZOOM_OUT = .28f;
    public static final float BOSS_FOCUS_X = .62f;
    public static final float BOSS_FOCUS_Y = .70f;

    private BossRevealCameraProfile() { }

    public static float envelope(float remainingSeconds) {
        float remaining = MathUtils.clamp(remainingSeconds, 0f, DURATION);
        float progress = 1f - remaining / DURATION;
        return MathUtils.sin(progress * MathUtils.PI);
    }

    public static float focusWeight(float envelope, boolean reducedMotion) {
        if (reducedMotion) return 0f;
        return MAX_FOCUS_WEIGHT * MathUtils.clamp(envelope, 0f, 1f);
    }

    public static float zoom(float baseZoom, float envelope, boolean reducedMotion) {
        if (reducedMotion) return baseZoom;
        return baseZoom + MAX_ZOOM_OUT * MathUtils.clamp(envelope, 0f, 1f);
    }
}
