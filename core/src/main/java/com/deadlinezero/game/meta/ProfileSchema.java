package com.deadlinezero.game.meta;

import com.badlogic.gdx.Preferences;

/** Versioned, idempotent migration pipeline for the persistent player profile. */
final class ProfileSchema {
    static final String VERSION_KEY = "schema.version";
    static final int LEGACY_UNVERSIONED = 0;
    static final int CURRENT_VERSION = 3;

    private ProfileSchema() {}

    interface Store {
        int getInteger(String key, int defaultValue);
        void putInteger(String key, int value);
        void flush();
    }

    static final class PreferencesStore implements Store {
        private final Preferences preferences;

        PreferencesStore(Preferences preferences) {
            if (preferences == null) throw new IllegalArgumentException("preferences");
            this.preferences = preferences;
        }

        @Override public int getInteger(String key, int defaultValue) {
            return preferences.getInteger(key, defaultValue);
        }

        @Override public void putInteger(String key, int value) {
            preferences.putInteger(key, value);
        }

        @Override public void flush() {
            preferences.flush();
        }
    }

    /**
     * Migrates a supported profile to the current schema. Returns false for a profile created by a
     * newer app version so callers can preserve it without overwriting it during a downgrade.
     */
    static boolean migrate(Store store) {
        if (store == null) return false;
        int version = sanitizedVersion(store.getInteger(VERSION_KEY, LEGACY_UNVERSIONED));
        if (version > CURRENT_VERSION) return false;

        boolean changed = false;
        while (version < CURRENT_VERSION) {
            version = migrateOne(store, version);
            changed = true;
        }

        if (changed) store.flush();
        return true;
    }

    static void stampCurrent(Store store) {
        if (store == null) return;
        store.putInteger(VERSION_KEY, CURRENT_VERSION);
    }

    static int sanitizedVersion(int rawVersion) {
        return Math.max(LEGACY_UNVERSIONED, rawVersion);
    }

    private static int migrateOne(Store store, int fromVersion) {
        return switch (fromVersion) {
            case LEGACY_UNVERSIONED -> migrateLegacyToV1(store);
            case 1 -> migrateV1ToV2(store);
            case 2 -> migrateV2ToV3(store);
            default -> throw new IllegalStateException("Unsupported profile migration from schema " + fromVersion);
        };
    }

    /**
     * Legacy saves already use the v1 field layout. The first migration therefore records the
     * schema marker without rewriting progression data, making the operation lossless and idempotent.
     */
    private static int migrateLegacyToV1(Store store) {
        store.putInteger(VERSION_KEY, 1);
        return 1;
    }

    /** Weekly mission fields are additive and use safe defaults, so v2 only advances the schema marker. */
    private static int migrateV1ToV2(Store store) {
        store.putInteger(VERSION_KEY, 2);
        return 2;
    }

    /** Achievement claims are additive booleans with safe false defaults. */
    private static int migrateV2ToV3(Store store) {
        store.putInteger(VERSION_KEY, 3);
        return 3;
    }
}
