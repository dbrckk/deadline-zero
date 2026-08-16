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
    public boolean reducedMotion = false;
    public boolean haptics = true;
    public float uiScale = 1f;
    public float masterVolume = 1f;
    public float sfxVolume = .85f;
    public float musicVolume = .65f;

    public static AccessibilitySettings load() {
        AccessibilitySettings s = new AccessibilitySettings();
        Preferences p = Gdx.app.getPreferences(PREFS);
        s.screenShake = p.getBoolean("screenShake", true);
        s.screenShakeStrength = p.getFloat("screenShakeStrength", 1f);
        s.hitStop = p.getBoolean("hitStop", true);
        s.damageFlash = p.getBoolean("damageFlash", true);
        s.highContrastTelegraphs = p.getBoolean("highContrastTelegraphs", false);
        s.reduceFlashes = p.getBoolean("reduceFlashes", false);
        s.reducedMotion = p.getBoolean("reducedMotion", false);
        s.haptics = p.getBoolean("haptics", true);
        s.uiScale = p.getFloat("uiScale", 1f);
        s.masterVolume = p.getFloat("masterVolume", 1f);
        s.sfxVolume = p.getFloat("sfxVolume", .85f);
        s.musicVolume = p.getFloat("musicVolume", .65f);
        s.normalize();
        active = s;
        return s;
    }

    public static AccessibilitySettings active() {
        if (active == null) active = new AccessibilitySettings();
        return active;
    }

    /** Keeps all scalar comfort settings inside the ranges supported by runtime render/audio systems. */
    public void normalize() {
        screenShakeStrength = clampFinite(screenShakeStrength, 0f, 1f, 1f);
        uiScale = clampFinite(uiScale, .85f, 1.35f, 1f);
        masterVolume = clampFinite(masterVolume, 0f, 1f, 1f);
        sfxVolume = clampFinite(sfxVolume, 0f, 1f, .85f);
        musicVolume = clampFinite(musicVolume, 0f, 1f, .65f);
        if (reducedMotion) enforceReducedMotion();
    }

    /** One-switch comfort preset. Audio/UI preferences are intentionally untouched. */
    public void setReducedMotion(boolean enabled) {
        reducedMotion = enabled;
        if (enabled) {
            enforceReducedMotion();
        } else {
            screenShake = true;
            hitStop = true;
        }
    }

    private void enforceReducedMotion() {
        screenShake = false;
        hitStop = false;
        reduceFlashes = true;
    }

    public boolean motionControlLocked() { return reducedMotion; }
    public boolean allowsScreenShake() { return !reducedMotion && screenShake && screenShakeStrength > 0f; }
    public boolean allowsHitStop() { return !reducedMotion && hitStop; }
    public boolean minimizesFlashes() { return reducedMotion || reduceFlashes; }

    public void save() {
        normalize();
        active = this;
        Gdx.app.getPreferences(PREFS)
            .putBoolean("screenShake", screenShake)
            .putFloat("screenShakeStrength", screenShakeStrength)
            .putBoolean("hitStop", hitStop)
            .putBoolean("damageFlash", damageFlash)
            .putBoolean("highContrastTelegraphs", highContrastTelegraphs)
            .putBoolean("reduceFlashes", reduceFlashes)
            .putBoolean("reducedMotion", reducedMotion)
            .putBoolean("haptics", haptics)
            .putFloat("uiScale", uiScale)
            .putFloat("masterVolume", masterVolume)
            .putFloat("sfxVolume", sfxVolume)
            .putFloat("musicVolume", musicVolume)
            .flush();
    }

    private static float clampFinite(float v, float min, float max, float fallback) {
        return Float.isFinite(v) ? Math.max(min, Math.min(max, v)) : fallback;
    }
}
