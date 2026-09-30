package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Release guardrail ensuring the documented accessibility surface remains wired into
 * persistent runtime settings and the production Settings screen.
 */
final class AccessibilityReleaseContractTest {
    @Test void releaseContractMatchesShippingAccessibilitySurface() throws Exception {
        Path root = repositoryRoot();
        String settings = Files.readString(root.resolve(
            "core/src/main/java/com/deadlinezero/game/config/AccessibilitySettings.java"), StandardCharsets.UTF_8);
        String screen = Files.readString(root.resolve(
            "core/src/main/java/com/deadlinezero/game/screen/SettingsScreen.java"), StandardCharsets.UTF_8);
        String contract = Files.readString(root.resolve(
            "play/store/ACCESSIBILITY.md"), StandardCharsets.UTF_8);

        String[] persistentControls = {
            "screenShake", "screenShakeStrength", "hitStop", "damageFlash",
            "highContrastTelegraphs", "reduceFlashes", "reducedMotion", "haptics",
            "colorVisionMode", "uiScale", "masterVolume", "sfxVolume", "musicVolume"
        };
        for (String control : persistentControls) {
            assertTrue(settings.contains(control), "Accessibility setting missing from persistence model: " + control);
        }

        String[] screenKeys = {
            "settings.screenShake", "settings.shakeStrength", "settings.hitStop",
            "settings.damageFlash", "settings.highContrastTelegraphs", "settings.reduceFlashes",
            "settings.haptics", "settings.colorVision", "settings.reducedMotion",
            "settings.uiScale", "settings.masterVolume", "settings.sfxVolume", "settings.musicVolume"
        };
        for (String key : screenKeys) {
            assertTrue(screen.contains(key), "Accessibility control missing from Settings screen: " + key);
        }

        assertTrue(settings.contains("enforceReducedMotion()"));
        assertTrue(settings.contains("screenShake = false"));
        assertTrue(settings.contains("hitStop = false"));
        assertTrue(settings.contains("reduceFlashes = true"));

        assertTrue(contract.contains("Every setting persists after app restart."));
        assertTrue(contract.contains("Reduced motion materially reduces non-essential motion."));
        assertTrue(contract.contains("High-contrast telegraphs remain readable in dense combat."));
        assertTrue(contract.contains("Critical gameplay information is not communicated by color alone"));
    }

    @Test void automatedAccessibilityRegressionCoverageExists() {
        Path root = repositoryRoot();
        assertTrue(Files.isRegularFile(root.resolve(
            "core/src/test/java/com/deadlinezero/game/config/AccessibilitySettingsTest.java")));
        assertTrue(Files.isRegularFile(root.resolve(
            "core/src/test/java/com/deadlinezero/game/config/AccessibilityColorVisionTest.java")));
    }

    private static Path repositoryRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("play/store/ACCESSIBILITY.md"))
                && Files.isDirectory(candidate.resolve("core/src/main/java"))) {
                return candidate;
            }
        }
        throw new IllegalStateException("Unable to locate repository root from " + current);
    }
}
