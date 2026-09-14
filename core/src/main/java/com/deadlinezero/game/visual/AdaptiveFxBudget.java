package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.config.GraphicsSettings;

/** Smoothly adapts optional visual density to sustained frame rate without changing gameplay. */
public final class AdaptiveFxBudget {
    private float smoothedFps = 60f;
    private float quality = 1f;
    private float warmup = 1.5f;
    private float externalCeiling = 1f;
    private float externalCeilingTarget = 1f;

    public void update(float dt) {
        dt = Math.max(0f, Math.min(.1f, dt));
        advanceExternalCeiling(dt);
        if (warmup > 0f) {
            warmup = Math.max(0f, warmup - dt);
            return;
        }

        float fps = Math.max(1f, Gdx.graphics.getFramesPerSecond());
        float fpsBlend = 1f - (float)Math.exp(-dt * 2.0f);
        smoothedFps = MathUtils.lerp(smoothedFps, fps, fpsBlend);

        float target;
        if (smoothedFps < 36f) target = .42f;
        else if (smoothedFps < 44f) target = .58f;
        else if (smoothedFps < 52f) target = .76f;
        else if (smoothedFps < 57f) target = .90f;
        else target = 1f;

        // Degrade quickly under load, recover slowly to avoid visual quality oscillation.
        float response = target < quality ? 2.6f : .65f;
        float qualityBlend = 1f - (float)Math.exp(-dt * response);
        quality = MathUtils.lerp(quality, target, qualityBlend);
    }

    public void setExternalCeiling(float ceiling) {
        externalCeilingTarget = MathUtils.clamp(ceiling, .40f, 1f);
        if (externalCeilingTarget < externalCeiling) externalCeiling = externalCeilingTarget;
    }

    void advanceExternalCeiling(float dt) {
        if (externalCeiling >= externalCeilingTarget) return;
        float safeDt = MathUtils.clamp(dt, 0f, .1f);
        float blend = 1f - (float)Math.exp(-safeDt * .45f);
        externalCeiling = MathUtils.lerp(externalCeiling, externalCeilingTarget, blend);
        if (Math.abs(externalCeilingTarget - externalCeiling) < .002f) externalCeiling = externalCeilingTarget;
    }

    public float quality() {
        return Math.min(Math.min(MathUtils.clamp(quality, .40f, 1f), GraphicsSettings.fxCeiling()), externalCeiling);
    }
    public boolean allowHeavyFx() { return quality() >= .72f; }
    public boolean allowExtraFx() { return quality() >= .90f; }
    public int geometrySegments(int high, int low) {
        return Math.max(low, Math.round(MathUtils.lerp(low, high, quality())));
    }
    public float smoothedFps() { return smoothedFps; }
}
