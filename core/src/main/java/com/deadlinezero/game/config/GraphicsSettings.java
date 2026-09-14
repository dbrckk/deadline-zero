package com.deadlinezero.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Persistent user-selected rendering ceiling and best-effort frame-rate target. */
public final class GraphicsSettings {
    private static final String PREFS = "deadline-zero-graphics";

    public enum Quality {
        LOW(.50f),
        MEDIUM(.68f),
        HIGH(.86f),
        ULTRA(1.00f);

        public final float fxCeiling;

        Quality(float fxCeiling) {
            this.fxCeiling = fxCeiling;
        }

        public Quality next(int direction) {
            Quality[] values = values();
            int index = Math.max(0, Math.min(values.length - 1, ordinal() + Integer.signum(direction)));
            return values[index];
        }
    }

    public enum FrameRate {
        FPS_60(60, "60 FPS"),
        FPS_90(90, "90 FPS"),
        FPS_120(120, "120 FPS");

        public final int target;
        public final String label;

        FrameRate(int target, String label) {
            this.target = target;
            this.label = label;
        }

        public FrameRate next(int direction) {
            FrameRate[] values = values();
            int index = Math.max(0, Math.min(values.length - 1, ordinal() + Integer.signum(direction)));
            return values[index];
        }
    }

    private static Quality active = Quality.ULTRA;
    private static FrameRate frameRate = FrameRate.FPS_60;

    private GraphicsSettings() {}

    public static Quality load() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        String rawQuality = prefs.getString("quality", Quality.ULTRA.name());
        String rawFrameRate = prefs.getString("frameRate", FrameRate.FPS_60.name());
        try { active = Quality.valueOf(rawQuality); }
        catch (IllegalArgumentException ignored) { active = Quality.ULTRA; }
        try { frameRate = FrameRate.valueOf(rawFrameRate); }
        catch (IllegalArgumentException ignored) { frameRate = FrameRate.FPS_60; }
        applyFrameRate();
        return active;
    }

    public static Quality active() { return active; }
    public static FrameRate frameRate() { return frameRate; }

    public static void set(Quality quality) {
        active = quality == null ? Quality.ULTRA : quality;
    }

    public static void setFrameRate(FrameRate next) {
        frameRate = next == null ? FrameRate.FPS_60 : next;
        applyFrameRate();
    }

    public static void save() {
        Gdx.app.getPreferences(PREFS)
            .putString("quality", active.name())
            .putString("frameRate", frameRate.name())
            .flush();
    }

    public static float fxCeiling() { return active.fxCeiling; }

    public static void applyFrameRate() {
        if (Gdx.graphics != null) Gdx.graphics.setForegroundFPS(frameRate.target);
    }
}
