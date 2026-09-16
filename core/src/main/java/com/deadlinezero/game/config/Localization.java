package com.deadlinezero.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Centralized runtime localization facade.
 *
 * The initial shipping catalog is English-only, but all callers use stable keys so additional
 * locales can be added without changing screen logic.
 */
public final class Localization {
    private final I18NBundle bundle;

    private Localization(I18NBundle bundle) {
        this.bundle = bundle;
    }

    public static Localization loadEnglish() {
        I18NBundle bundle = I18NBundle.createBundle(
            Gdx.files.internal("i18n/messages"),
            Locale.ENGLISH
        );
        return new Localization(bundle);
    }

    public String text(String key) {
        if (key == null || key.isBlank()) return "";
        try {
            return sanitizeForBitmapFont(bundle.get(key));
        } catch (MissingResourceException ignored) {
            return sanitizeForBitmapFont(key);
        }
    }

    public String format(String key, Object... args) {
        if (key == null || key.isBlank()) return "";
        try {
            return sanitizeForBitmapFont(bundle.format(key, args));
        } catch (MissingResourceException ignored) {
            return sanitizeForBitmapFont(key);
        }
    }

    /**
     * Normalizes punctuation that is not present in libGDX's bundled default BitmapFont.
     * Screens intentionally keep authored localization copy expressive; this render boundary
     * guarantees that the runtime never substitutes missing-glyph boxes on the shipping font.
     */
    public static String sanitizeForBitmapFont(String value) {
        if (value == null || value.isEmpty()) return "";
        return value
            .replace("•", "|")
            .replace("‹", "<")
            .replace("›", ">")
            .replace("←", "<-")
            .replace("→", "->")
            .replace("↑", "^")
            .replace("↓", "v")
            .replace("–", "-")
            .replace("—", "-")
            .replace("…", "...");
    }
}
