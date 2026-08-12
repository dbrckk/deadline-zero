package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;

/** Conservative mobile quality tiers. AUTO avoids expensive effects on weaker devices. */
public enum GraphicsQuality {
    LOW(.00f, false, false, 0.55f),
    MEDIUM(.28f, true, false, 0.78f),
    HIGH(.48f, true, true, 1.00f),
    ULTRA(.62f, true, true, 1.18f);

    public final float postFxIntensity;
    public final boolean authoredFx;
    public final boolean extraGlow;
    public final float particleScale;

    GraphicsQuality(float postFxIntensity, boolean authoredFx, boolean extraGlow, float particleScale) {
        this.postFxIntensity = postFxIntensity;
        this.authoredFx = authoredFx;
        this.extraGlow = extraGlow;
        this.particleScale = particleScale;
    }

    public static GraphicsQuality autoDetect() {
        int width = Math.max(1, Gdx.graphics.getBackBufferWidth());
        int height = Math.max(1, Gdx.graphics.getBackBufferHeight());
        long pixels = (long)width * height;
        int fps = Gdx.graphics.getFramesPerSecond();

        // Resolution is a useful zero-cost proxy before a real benchmark/device database is added.
        if (pixels <= 1_200_000L) return HIGH;
        if (pixels <= 2_400_000L) return MEDIUM;
        if (fps > 0 && fps < 48) return LOW;
        return MEDIUM;
    }
}
