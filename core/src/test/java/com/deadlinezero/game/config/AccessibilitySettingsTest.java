package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class AccessibilitySettingsTest {
    @Test void reducedMotionDisablesMotionHeavyFeedback() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.setReducedMotion(true);

        assertTrue(settings.reducedMotion);
        assertTrue(settings.motionControlLocked());
        assertFalse(settings.screenShake);
        assertFalse(settings.hitStop);
        assertTrue(settings.reduceFlashes);
        assertFalse(settings.allowsScreenShake());
        assertFalse(settings.allowsHitStop());
        assertTrue(settings.minimizesFlashes());
    }

    @Test void normalizationRepairsInconsistentReducedMotionPreferences() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.reducedMotion = true;
        settings.screenShake = true;
        settings.hitStop = true;
        settings.reduceFlashes = false;
        settings.normalize();

        assertFalse(settings.screenShake);
        assertFalse(settings.hitStop);
        assertTrue(settings.reduceFlashes);
    }

    @Test void disablingPresetRestoresMotionFeedbackWithoutTouchingFlashPreference() {
        AccessibilitySettings settings = new AccessibilitySettings();
        settings.setReducedMotion(true);
        settings.setReducedMotion(false);

        assertFalse(settings.reducedMotion);
        assertFalse(settings.motionControlLocked());
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
