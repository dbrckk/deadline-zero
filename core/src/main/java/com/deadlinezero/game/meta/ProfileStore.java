package com.deadlinezero.game.meta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.deadlinezero.game.combat.WeaponCatalog;

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
        profile.selectedStage = Math.min(profile.highestStage, Math.max(1, p.getInteger("selectedStage", 1)));
        profile.totalRuns = Math.max(0, p.getInteger("totalRuns", 0));
        profile.totalKills = Math.max(0L, p.getLong("totalKills", 0L));
        profile.removeAdsPurchased = p.getBoolean("purchase.removeAds", false);
        profile.starterPackGranted = p.getBoolean("purchase.starterPackGranted", false);
        profile.onboardingCompleted = p.getBoolean("onboarding.completed", false);
        profile.selectedSurvivor = SurvivorCatalog.byName(p.getString("survivor.selected", SurvivorCatalog.Survivor.REX.name()));
        profile.selectedWeaponId = WeaponCatalog.byId(p.getString("weapon.selected", WeaponCatalog.AR9.id)).id;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, Math.max(0L, p.getLong("credits", 0L)));
        profile.addCurrency(PlayerProfile.Currency.GEMS, Math.max(0L, p.getLong("gems", 0L)));

        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String key = "survivor." + survivor.name() + ".";
            profile.survivors.setState(survivor,
                Math.max(1, p.getInteger(key + "level", 1)),
                Math.max(0L, p.getLong(key + "xp", 0L)),
                p.getBoolean(key + "unlocked", survivor == SurvivorCatalog.Survivor.REX));
        }
        profile.survivors.refreshUnlocks(profile);
        if (!profile.survivors.unlocked(profile.selectedSurvivor)) profile.selectedSurvivor = SurvivorCatalog.Survivor.REX;
        profile.validateSelectedWeapon();

        profile.daily.epochDay = p.getLong("daily.epochDay", -1L);
        profile.daily.loginStreak = Math.max(0, p.getInteger("daily.loginStreak", 0));
        profile.daily.loginClaimed = p.getBoolean("daily.loginClaimed", false);
        profile.daily.rewardedChestClaimed = p.getBoolean("daily.rewardedChestClaimed", false);
        profile.daily.killsToday = Math.max(0, p.getInteger("daily.kills", 0));
        profile.daily.runsToday = Math.max(0, p.getInteger("daily.runs", 0));
        profile.daily.bossesToday = Math.max(0, p.getInteger("daily.bosses", 0));
        profile.daily.killMissionClaimed = p.getBoolean("daily.killClaimed", false);
        profile.daily.runMissionClaimed = p.getBoolean("daily.runClaimed", false);
        profile.daily.bossMissionClaimed = p.getBoolean("daily.bossClaimed", false);

        int itemCount = Math.min(Inventory.MAX_ITEMS, Math.max(0, p.getInteger("inventory.count", 0)));
        for (int i = 0; i < itemCount; i++) {
            String key = "inventory." + i + ".";
            try {
                String id = p.getString(key + "id", "");
                if (id.isEmpty()) continue;
                EquipmentItem item = new EquipmentItem(id, p.getString(key + "name", "Equipment"),
                    PlayerProfile.EquipmentSlot.valueOf(p.getString(key + "slot", "WEAPON")),
                    EquipmentItem.Rarity.valueOf(p.getString(key + "rarity", "COMMON")),
                    Math.max(1, p.getInteger(key + "level", 1)), Math.max(0f, p.getFloat(key + "power", 0f)));
                profile.inventory.add(item);
            } catch (IllegalArgumentException ignored) { }
        }
        for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
            String id = p.getString("equipped." + slot.name(), "");
            EquipmentItem item = profile.inventory.find(id);
            if (item != null) profile.equip(item);
        }
        return profile;
    }

    public static void save(PlayerProfile profile) {
        if (profile == null) return;
        Preferences p = Gdx.app.getPreferences(PREFS);
        p.putInteger("accountLevel", profile.accountLevel);
        p.putLong("accountXp", profile.accountXp);
        p.putInteger("highestStage", profile.highestStage);
        p.putInteger("selectedStage", profile.selectedStage);
        p.putInteger("totalRuns", profile.totalRuns);
        p.putLong("totalKills", profile.totalKills);
        p.putBoolean("purchase.removeAds", profile.removeAdsPurchased);
        p.putBoolean("purchase.starterPackGranted", profile.starterPackGranted);
        p.putBoolean("onboarding.completed", profile.onboardingCompleted);
        p.putString("survivor.selected", profile.selectedSurvivor.name());
        p.putString("weapon.selected", profile.selectedWeapon().id);
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String key = "survivor." + survivor.name() + ".";
            p.putInteger(key + "level", profile.survivors.level(survivor));
            p.putLong(key + "xp", profile.survivors.xp(survivor));
            p.putBoolean(key + "unlocked", profile.survivors.unlocked(survivor));
        }
        p.putLong("credits", profile.currency(PlayerProfile.Currency.CREDITS));
        p.putLong("gems", profile.currency(PlayerProfile.Currency.GEMS));

        p.putLong("daily.epochDay", profile.daily.epochDay);
        p.putInteger("daily.loginStreak", profile.daily.loginStreak);
        p.putBoolean("daily.loginClaimed", profile.daily.loginClaimed);
        p.putBoolean("daily.rewardedChestClaimed", profile.daily.rewardedChestClaimed);
        p.putInteger("daily.kills", profile.daily.killsToday);
        p.putInteger("daily.runs", profile.daily.runsToday);
        p.putInteger("daily.bosses", profile.daily.bossesToday);
        p.putBoolean("daily.killClaimed", profile.daily.killMissionClaimed);
        p.putBoolean("daily.runClaimed", profile.daily.runMissionClaimed);
        p.putBoolean("daily.bossClaimed", profile.daily.bossMissionClaimed);

        int count = Math.min(profile.inventory.size(), Inventory.MAX_ITEMS);
        p.putInteger("inventory.count", count);
        for (int i = 0; i < count; i++) {
            EquipmentItem item = profile.inventory.items().get(i);
            String key = "inventory." + i + ".";
            p.putString(key + "id", item.id);
            p.putString(key + "name", item.name);
            p.putString(key + "slot", item.slot.name());
            p.putString(key + "rarity", item.rarity.name());
            p.putInteger(key + "level", item.level);
            p.putFloat(key + "power", item.powerBonus);
        }
        for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
            EquipmentItem item = profile.equipped(slot);
            p.putString("equipped." + slot.name(), item == null ? "" : item.id);
        }
        p.flush();
    }
}
