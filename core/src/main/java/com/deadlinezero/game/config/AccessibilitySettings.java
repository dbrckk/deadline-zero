package com.deadlinezero.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Persistent comfort/accessibility options kept independent from gameplay balance. */
public final class AccessibilitySettings {
    private static final String PREFS = "deadline-zero-accessibility";
    private static AccessibilitySettings active;

    public boolean screenShake = true;
    public float screenShakeStrength = 1f;
    public boolean hitStop = true;
    public boolean damageFlash = true;
    public boolean highContrastTelegraphs = false;
    public boolean reduceFlashes = false;
    public float uiScale = 1f;
    public float masterVolume = 1f;
    public float sfxVolume = .85f;
    public float musicVolume = .65f;

    public static AccessibilitySettings load() {
        AccessibilitySettings s = new AccessibilitySettings();
        Preferences p = Gdx.app.getPreferences(PREFS);
        s.screenShake = p.getBoolean("screenShake", true);
        s.screenShakeStrength = clamp(p.getFloat("screenShakeStrength", 1f), 0f, 1f);
        s.hitStop = p.getBoolean("hitStop", true);
        s.damageFlash = p.getBoolean("damageFlash", true);
        s.highContrastTelegraphs = p.getBoolean("highContrastTelegraphs", false);
        s.reduceFlashes = p.getBoolean("reduceFlashes", false);
        s.uiScale = clamp(p.getFloat("uiScale", 1f), .85f, 1.35f);
        s.masterVolume = clamp(p.getFloat("masterVolume", 1f), 0f, 1f);
        s.sfxVolume = clamp(p.getFloat("sfxVolume", .85f), 0f, 1f);
        s.musicVolume = clamp(p.getFloat("musicVolume", .65f), 0f, 1f);
        active = s;
        return s;
    }

    public static AccessibilitySettings active() {
        if (active == null) active = new AccessibilitySettings();
        return active;
    }

    public void save() {
        active = this;
        Gdx.app.getPreferences(PREFS)
            .putBoolean("screenShake", screenShake)
            .putFloat("screenShakeStrength", clamp(screenShakeStrength, 0f, 1f))
            .putBoolean("hitStop", hitStop)
            .putBoolean("damageFlash", damageFlash)
            .putBoolean("highContrastTelegraphs", highContrastTelegraphs)
            .putBoolean("reduceFlashes", reduceFlashes)
            .putFloat("uiScale", clamp(uiScale, .85f, 1.35f))
            .putFloat("masterVolume", clamp(masterVolume, 0f, 1f))
            .putFloat("sfxVolume", clamp(sfxVolume, 0f, 1f))
            .putFloat("musicVolume", clamp(musicVolume, 0f, 1f))
            .flush();
    }

    private static float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }
}
