package com.deadlinezero.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Persistent user-selected rendering ceiling layered over the adaptive FX budget. */
public final class GraphicsSettings {
    private static final String PREFS = "deadline-zero-graphics";

    public enum Quality {
        LOW(.50f, 30),
        MEDIUM(.68f, 60),
        HIGH(.86f, 60),
        ULTRA(1.00f, 120);

        public final float fxCeiling;
        public final int targetFps;

        Quality(float fxCeiling, int targetFps) {
            this.fxCeiling = fxCeiling;
            this.targetFps = targetFps;
        }

        public Quality next(int direction) {
            Quality[] values = values();
            int index = Math.max(0, Math.min(values.length - 1, ordinal() + Integer.signum(direction)));
            return values[index];
        }
    }

    private static Quality active = Quality.HIGH;

    private GraphicsSettings() {}

    public static Quality load() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        String raw = prefs.getString("quality", Quality.HIGH.name());
        try { active = Quality.valueOf(raw); }
        catch (IllegalArgumentException ignored) { active = Quality.HIGH; }
        return active;
    }

    public static Quality active() { return active; }

    public static void set(Quality quality) {
        active = quality == null ? Quality.HIGH : quality;
    }

    public static void save() {
        Gdx.app.getPreferences(PREFS).putString("quality", active.name()).flush();
    }

    public static float fxCeiling() { return active.fxCeiling; }
    public static int targetFps() { return active.targetFps; }
}
