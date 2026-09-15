package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class AccessibilityColorVisionTest {
    @Test void colorVisionModesCycleBothDirections() {
        AccessibilitySettings.ColorVisionMode mode = AccessibilitySettings.ColorVisionMode.STANDARD;
        assertEquals(AccessibilitySettings.ColorVisionMode.DEUTERANOPIA, mode.next(1));
        assertEquals(AccessibilitySettings.ColorVisionMode.TRITANOPIA, mode.next(-1));
        assertEquals(AccessibilitySettings.ColorVisionMode.STANDARD,
            AccessibilitySettings.ColorVisionMode.TRITANOPIA.next(1));
    }

    @Test void invalidStoredColorVisionModeFallsBackToStandard() {
        assertEquals(AccessibilitySettings.ColorVisionMode.STANDARD,
            AccessibilitySettings.ColorVisionMode.fromStored("UNKNOWN"));
        assertEquals(AccessibilitySettings.ColorVisionMode.STANDARD,
            AccessibilitySettings.ColorVisionMode.fromStored(null));
    }

    @Test void normalizeClampsComfortScalarsAndReducedMotion() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.screenShakeStrength = 4f;
        settings.uiScale = .1f;
        settings.masterVolume = Float.NaN;
        settings.sfxVolume = -2f;
        settings.musicVolume = 5f;
        settings.reducedMotion = true;
        settings.normalize();
        assertEquals(1f, settings.screenShakeStrength);
        assertEquals(.85f, settings.uiScale);
        assertEquals(1f, settings.masterVolume);
        assertEquals(0f, settings.sfxVolume);
        assertEquals(1f, settings.musicVolume);
        assertFalse(settings.screenShake);
        assertFalse(settings.hitStop);
        assertTrue(settings.reduceFlashes);
    }
}
