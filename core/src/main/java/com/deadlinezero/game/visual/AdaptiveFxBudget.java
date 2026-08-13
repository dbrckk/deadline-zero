package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/** Smoothly adapts optional visual density to sustained frame rate without changing gameplay. */
public final class AdaptiveFxBudget {
    private float smoothedFps = 60f;
    private float quality = 1f;

    public void update(float dt) {
        float fps = Math.max(1f, Gdx.graphics.getFramesPerSecond());
        float blend = 1f - (float)Math.exp(-Math.max(0f, dt) * 2.2f);
        smoothedFps = MathUtils.lerp(smoothedFps, fps, blend);
        float target;
        if (smoothedFps < 38f) target = .45f;
        else if (smoothedFps < 48f) target = .68f;
        else if (smoothedFps < 56f) target = .84f;
        else target = 1f;
        quality = MathUtils.lerp(quality, target, blend * .7f);
    }

    public float quality() { return MathUtils.clamp(quality, .40f, 1f); }
    public boolean allowHeavyFx() { return quality > .72f; }
    public boolean allowExtraFx() { return quality > .90f; }
    public float smoothedFps() { return smoothedFps; }
}
