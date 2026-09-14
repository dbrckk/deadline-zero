package com.deadlinezero.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Persistent user-selected rendering ceiling layered over the adaptive FX budget. */
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

    private static Quality active = Quality.ULTRA;

    private GraphicsSettings() {}

    public static Quality load() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        String raw = prefs.getString("quality", Quality.ULTRA.name());
        try { active = Quality.valueOf(raw); }
        catch (IllegalArgumentException ignored) { active = Quality.ULTRA; }
        return active;
    }

    public static Quality active() { return active; }

    public static void set(Quality quality) {
        active = quality == null ? Quality.ULTRA : quality;
    }

    public static void save() {
        Gdx.app.getPreferences(PREFS).putString("quality", active.name()).flush();
    }

    public static float fxCeiling() { return active.fxCeiling; }
}
