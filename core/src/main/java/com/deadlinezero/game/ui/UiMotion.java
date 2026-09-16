package com.deadlinezero.game.ui;

import com.badlogic.gdx.math.MathUtils;

/** Deterministic short UI motion curves with an immediate reduced-motion path. */
public final class UiMotion {
    public static final float PRESS_SECONDS = .085f;
    public static final float FOCUS_SECONDS = .12f;
    public static final float REVEAL_SECONDS = .19f;
    public static final float CONFIRM_SECONDS = .22f;

    private UiMotion() {}

    public static float progress(float elapsed, float duration, boolean reduceMotion) {
        if (reduceMotion) return 1f;
        if (duration <= 0f) return 1f;
        float t = MathUtils.clamp(elapsed / duration, 0f, 1f);
        return easeOutCubic(t);
    }

    public static float easeOutCubic(float t) {
        float c = MathUtils.clamp(t, 0f, 1f);
        float inv = 1f - c;
        return 1f - inv * inv * inv;
    }

    public static float easeInOut(float t) {
        float c = MathUtils.clamp(t, 0f, 1f);
        return c < .5f ? 4f * c * c * c : 1f - (float) Math.pow(-2f * c + 2f, 3f) / 2f;
    }

    public static float pressScale(float elapsed, boolean reduceMotion) {
        if (reduceMotion) return 1f;
        float p = progress(elapsed, PRESS_SECONDS, false);
        return 1f - .025f * (1f - Math.abs(p * 2f - 1f));
    }
}
