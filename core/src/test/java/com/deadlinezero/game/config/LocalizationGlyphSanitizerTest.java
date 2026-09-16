package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

final class LocalizationGlyphSanitizerTest {
    @Test
    void unsupportedUiGlyphsAreNormalizedForDefaultBitmapFont() {
        String source = "A • B ‹ C › D ← E → F ↑ G ↓ H – I — J … K";

        String sanitized = Localization.sanitizeForBitmapFont(source);

        assertEquals("A | B < C > D <- E -> F ^ G v H - I - J ... K", sanitized);
        for (char unsupported : new char[] {'•', '‹', '›', '←', '→', '↑', '↓', '–', '—', '…'}) {
            assertFalse(sanitized.indexOf(unsupported) >= 0, "unsupported glyph survived: " + unsupported);
        }
    }

    @Test
    void unresolvedFormatTokensDoNotLeakIntoVisibleUi() {
        assertEquals("TAP / R TO CHANGE",
            Localization.sanitizeForBitmapFont("{0} • TAP / R TO CHANGE"));
    }

    @Test
    void leadingDesktopEscapeHintDoesNotCrowdMobileBackRail() {
        assertEquals("BACK TO BASE", Localization.sanitizeForBitmapFont("ESC • BACK TO BASE"));
        assertEquals("TAP A CARD | ESC TO CANCEL",
            Localization.sanitizeForBitmapFont("TAP A CARD • ESC TO CANCEL"));
    }

    @Test
    void nullAndAsciiStringsRemainSafe() {
        assertEquals("", Localization.sanitizeForBitmapFont(null));
        assertEquals("DPS 120 | FIRE 0.25s", Localization.sanitizeForBitmapFont("DPS 120 | FIRE 0.25s"));
    }
}
