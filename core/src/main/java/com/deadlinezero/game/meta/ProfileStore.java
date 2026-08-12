package com.deadlinezero.game.meta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Persistent account storage backed by libGDX Preferences on Android/Desktop. */
public final class ProfileStore {
    private static final String PREFS = "deadline-zero-profile-v1";

    private ProfileStore() {}

    public static PlayerProfile load() {
        Preferences p = Gdx.app.getPreferences(PREFS);
        PlayerProfile profile = new PlayerProfile();
        profile.accountLevel = Math.max(1, p.getInteger("accountLevel", 1));
        profile.accountXp = Math.max(0L, p.getLong("accountXp", 0L));
        profile.highestStage = Math.max(1, p.getInteger("highestStage", 1));
        profile.totalRuns = Math.max(0, p.getInteger("totalRuns", 0));
        profile.totalKills = Math.max(0L, p.getLong("totalKills", 0L));
        profile.addCurrency(PlayerProfile.Currency.CREDITS, Math.max(0L, p.getLong("credits", 0L)));
        profile.addCurrency(PlayerProfile.Currency.GEMS, Math.max(0L, p.getLong("gems", 0L)));
        return profile;
    }

    public static void save(PlayerProfile profile) {
        if (profile == null) return;
        Preferences p = Gdx.app.getPreferences(PREFS);
        p.putInteger("accountLevel", profile.accountLevel);
        p.putLong("accountXp", profile.accountXp);
        p.putInteger("highestStage", profile.highestStage);
        p.putInteger("totalRuns", profile.totalRuns);
        p.putLong("totalKills", profile.totalKills);
        p.putLong("credits", profile.currency(PlayerProfile.Currency.CREDITS));
        p.putLong("gems", profile.currency(PlayerProfile.Currency.GEMS));
        p.flush();
    }
}
