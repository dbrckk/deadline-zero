package com.deadlinezero.game.meta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Device-local cache for store-owned entitlements that must not be restored from Android backup.
 * Google Play remains authoritative; this cache only preserves offline UX after a successful sync.
 */
public final class EntitlementStore {
    public static final String PREFS = "deadline-zero-entitlements-v1";
    private static final String REMOVE_ADS = "removeAds";

    private EntitlementStore() {}

    public static void loadInto(PlayerProfile profile) {
        if (profile == null) return;
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        profile.removeAdsPurchased = prefs.getBoolean(REMOVE_ADS, false);
    }

    public static void save(PlayerProfile profile) {
        if (profile == null) return;
        Gdx.app.getPreferences(PREFS)
            .putBoolean(REMOVE_ADS, profile.removeAdsPurchased)
            .flush();
    }
}
