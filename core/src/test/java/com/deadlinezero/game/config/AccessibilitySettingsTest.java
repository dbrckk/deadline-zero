package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class AccessibilitySettingsTest {
    @Test void reducedMotionDisablesMotionHeavyFeedback() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.setReducedMotion(true);

        assertTrue(settings.reducedMotion);
        assertFalse(settings.screenShake);
        assertFalse(settings.hitStop);
        assertTrue(settings.reduceFlashes);
        assertFalse(settings.allowsScreenShake());
        assertFalse(settings.allowsHitStop());
        assertTrue(settings.minimizesFlashes());
    }

    @Test void disablingPresetRestoresMotionFeedbackWithoutTouchingFlashPreference() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.setReducedMotion(true);
        settings.setReducedMotion(false);

        assertFalse(settings.reducedMotion);
        assertTrue(settings.screenShake);
        assertTrue(settings.hitStop);
        assertTrue(settings.reduceFlashes);
        assertTrue(settings.allowsScreenShake());
        assertTrue(settings.allowsHitStop());
        assertTrue(settings.minimizesFlashes());
    }

    @Test void helperMethodsRespectManualControls() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.screenShakeStrength = 0f;
        settings.hitStop = false;
        settings.reduceFlashes = false;

        assertFalse(settings.allowsScreenShake());
        assertFalse(settings.allowsHitStop());
        assertFalse(settings.minimizesFlashes());
    }
}
