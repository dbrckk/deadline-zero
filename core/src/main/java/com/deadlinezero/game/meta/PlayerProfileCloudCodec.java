package com.deadlinezero.game.meta;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.services.CloudSaveSnapshot;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Canonical cloud representation of every PlayerProfile field persisted by ProfileStore. */
public final class PlayerProfileCloudCodec {
    public static final int SCHEMA_VERSION = ProfileSchema.CURRENT_VERSION;

    private PlayerProfileCloudCodec() {}

    public static CloudSaveSnapshot snapshot(PlayerProfile profile, long revision,
                                             long updatedAtEpochMillis, String deviceId) {
        if (profile == null) throw new IllegalArgumentException("profile");
        return CloudSaveSnapshot.create(SCHEMA_VERSION, revision, updatedAtEpochMillis, deviceId, encode(profile));
    }

    public static PlayerProfile restore(CloudSaveSnapshot snapshot) {
        if (snapshot == null || !snapshot.integrityValid()) throw new IllegalArgumentException("snapshot");
        if (snapshot.schemaVersion != SCHEMA_VERSION) {
            throw new IllegalArgumentException("Unsupported cloud profile schema " + snapshot.schemaVersion);
        }
        return decode(snapshot.payload);
    }

    public static String encode(PlayerProfile profile) {
        if (profile == null) throw new IllegalArgumentException("profile");
        JsonValue root = object();
        put(root, "schema", SCHEMA_VERSION);
        put(root, "accountLevel", profile.accountLevel);
        put(root, "accountXp", profile.accountXp);
        put(root, "highestStage", profile.highestStage);
        put(root, "selectedStage", profile.selectedStage);
        put(root, "highestThreatTier", profile.highestThreatTier);
        put(root, "selectedThreatTier", profile.selectedThreatTier);
        put(root, "totalRuns", profile.totalRuns);
        put(root, "totalKills", profile.totalKills);
        put(root, "removeAdsPurchased", profile.removeAdsPurchased);
        put(root, "starterPackGranted", profile.starterPackGranted);
        put(root, "selectedSurvivor", profile.selectedSurvivor.name());
        put(root, "selectedWeaponId", profile.selectedWeapon().id);

        JsonValue currencies = object();
        for (PlayerProfile.Currency currency : PlayerProfile.Currency.values()) {
            put(currencies, currency.name(), profile.currency(currency));
        }
        root.addChild("currencies", currencies);

        List<String> receipts = new ArrayList<>(profile.deliveredPurchaseReceipts());
        Collections.sort(receipts);
        JsonValue receiptArray = array();
        for (String receipt : receipts) receiptArray.addChild(new JsonValue(receipt));
        root.addChild("purchaseReceipts", receiptArray);

        JsonValue survivorArray = array();
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            JsonValue state = object();
            put(state, "id", survivor.name());
            put(state, "level", profile.survivors.level(survivor));
            put(state, "xp", profile.survivors.xp(survivor));
            put(state, "unlocked", profile.survivors.unlocked(survivor));
            survivorArray.addChild(state);
        }
        root.addChild("survivors", survivorArray);

        JsonValue weaponMastery = array();
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            JsonValue state = object();
            put(state, "id", weapon.id);
            put(state, "wins", profile.mastery.weaponWins(weapon.id));
            weaponMastery.addChild(state);
        }
        root.addChild("weaponMastery", weaponMastery);

        JsonValue biomeMastery = array();
        for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
            JsonValue state = object();
            put(state, "id", biome.name());
            put(state, "wins", profile.mastery.biomeWins(biome));
            biomeMastery.addChild(state);
        }
        root.addChild("biomeMastery", biomeMastery);

        JsonValue daily = object();
        put(daily, "epochDay", profile.daily.epochDay);
        put(daily, "loginStreak", profile.daily.loginStreak);
        put(daily, "loginClaimed", profile.daily.loginClaimed);
        put(daily, "rewardedChestClaimed", profile.daily.rewardedChestClaimed);
        put(daily, "killsToday", profile.daily.killsToday);
        put(daily, "runsToday", profile.daily.runsToday);
        put(daily, "bossesToday", profile.daily.bossesToday);
        put(daily, "killMissionClaimed", profile.daily.killMissionClaimed);
        put(daily, "runMissionClaimed", profile.daily.runMissionClaimed);
        put(daily, "bossMissionClaimed", profile.daily.bossMissionClaimed);
        root.addChild("daily", daily);

        JsonValue inventory = array();
        int count = Math.min(profile.inventory.size(), Inventory.MAX_ITEMS);
        for (int i = 0; i < count; i++) {
            EquipmentItem item = profile.inventory.items().get(i);
            if (item == null || item.id == null || item.id.isBlank()) continue;
            JsonValue encoded = object();
            put(encoded, "id", item.id);
            put(encoded, "name", item.name == null ? "Equipment" : item.name);
            put(encoded, "slot", item.slot.name());
            put(encoded, "rarity", item.rarity.name());
            put(encoded, "level", item.level);
            put(encoded, "powerBonus", item.powerBonus);
            inventory.addChild(encoded);
        }
        root.addChild("inventory", inventory);

        JsonValue equipped = object();
        for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
            EquipmentItem item = profile.equipped(slot);
            put(equipped, slot.name(), item == null ? "" : item.id);
        }
        root.addChild("equipped", equipped);
        return root.toJson(JsonWriter.OutputType.json);
    }

    public static PlayerProfile decode(String payload) {
        if (payload == null || payload.isBlank()) throw new IllegalArgumentException("payload");
        final JsonValue root;
        try {
            root = new JsonReader().parse(payload);
        } catch (RuntimeException invalid) {
            throw new IllegalArgumentException("Malformed cloud profile", invalid);
        }
        if (root == null || !root.isObject()) throw new IllegalArgumentException("Cloud profile root");
        int schema = root.getInt("schema", -1);
        if (schema != SCHEMA_VERSION) throw new IllegalArgumentException("Unsupported cloud profile schema " + schema);

        PlayerProfile profile = new PlayerProfile();
        profile.accountLevel = root.getInt("accountLevel", 1);
        profile.accountXp = root.getLong("accountXp", 0L);
        profile.highestStage = root.getInt("highestStage", 1);
        profile.selectedStage = root.getInt("selectedStage", 1);
        profile.highestThreatTier = root.getInt("highestThreatTier", 0);
        profile.selectedThreatTier = root.getInt("selectedThreatTier", 0);
        profile.totalRuns = root.getInt("totalRuns", 0);
        profile.totalKills = root.getLong("totalKills", 0L);
        profile.removeAdsPurchased = root.getBoolean("removeAdsPurchased", false);
        profile.starterPackGranted = root.getBoolean("starterPackGranted", false);
        profile.selectedSurvivor = SurvivorCatalog.byName(root.getString("selectedSurvivor", SurvivorCatalog.Survivor.REX.name()));
        profile.selectedWeaponId = WeaponCatalog.byId(root.getString("selectedWeaponId", WeaponCatalog.AR9.id)).id;

        JsonValue currencies = root.get("currencies");
        if (currencies != null && currencies.isObject()) {
            for (PlayerProfile.Currency currency : PlayerProfile.Currency.values()) {
                profile.addCurrency(currency, Math.max(0L, currencies.getLong(currency.name(), 0L)));
            }
        }

        JsonValue receipts = root.get("purchaseReceipts");
        if (receipts != null && receipts.isArray()) {
            for (JsonValue value = receipts.child; value != null; value = value.next) {
                if (value.isString()) profile.recordDeliveredPurchaseReceipt(value.asString());
            }
        }

        JsonValue survivors = root.get("survivors");
        if (survivors != null && survivors.isArray()) {
            for (JsonValue state = survivors.child; state != null; state = state.next) {
                SurvivorCatalog.Survivor survivor = SurvivorCatalog.byName(state.getString("id", ""));
                profile.survivors.setState(
                    survivor,
                    state.getInt("level", 1),
                    state.getLong("xp", 0L),
                    state.getBoolean("unlocked", survivor == SurvivorCatalog.Survivor.REX));
            }
        }

        JsonValue weaponMastery = root.get("weaponMastery");
        if (weaponMastery != null && weaponMastery.isArray()) {
            for (JsonValue state = weaponMastery.child; state != null; state = state.next) {
                String id = state.getString("id", WeaponCatalog.AR9.id);
                profile.mastery.setWeaponWins(id, state.getInt("wins", 0));
            }
        }

        JsonValue biomeMastery = root.get("biomeMastery");
        if (biomeMastery != null && biomeMastery.isArray()) {
            for (JsonValue state = biomeMastery.child; state != null; state = state.next) {
                try {
                    EnvironmentBiomeRules.Biome biome =
                        EnvironmentBiomeRules.Biome.valueOf(state.getString("id", ""));
                    profile.mastery.setBiomeWins(biome, state.getInt("wins", 0));
                } catch (IllegalArgumentException ignored) { }
            }
        }

        JsonValue daily = root.get("daily");
        if (daily != null && daily.isObject()) {
            profile.daily.epochDay = daily.getLong("epochDay", -1L);
            profile.daily.loginStreak = Math.max(0, daily.getInt("loginStreak", 0));
            profile.daily.loginClaimed = daily.getBoolean("loginClaimed", false);
            profile.daily.rewardedChestClaimed = daily.getBoolean("rewardedChestClaimed", false);
            profile.daily.killsToday = Math.max(0, daily.getInt("killsToday", 0));
            profile.daily.runsToday = Math.max(0, daily.getInt("runsToday", 0));
            profile.daily.bossesToday = Math.max(0, daily.getInt("bossesToday", 0));
            profile.daily.killMissionClaimed = daily.getBoolean("killMissionClaimed", false);
            profile.daily.runMissionClaimed = daily.getBoolean("runMissionClaimed", false);
            profile.daily.bossMissionClaimed = daily.getBoolean("bossMissionClaimed", false);
        }

        JsonValue inventory = root.get("inventory");
        if (inventory != null && inventory.isArray()) {
            for (JsonValue encoded = inventory.child; encoded != null; encoded = encoded.next) {
                try {
                    String id = encoded.getString("id", "");
                    if (id.isBlank()) continue;
                    EquipmentItem item = new EquipmentItem(
                        id,
                        encoded.getString("name", "Equipment"),
                        PlayerProfile.EquipmentSlot.valueOf(encoded.getString("slot", "WEAPON")),
                        EquipmentItem.Rarity.valueOf(encoded.getString("rarity", "COMMON")),
                        encoded.getInt("level", 1),
                        encoded.getFloat("powerBonus", 0f));
                    profile.inventory.restore(item);
                } catch (IllegalArgumentException ignored) { }
            }
        }

        JsonValue equipped = root.get("equipped");
        if (equipped != null && equipped.isObject()) {
            for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
                EquipmentItem item = profile.inventory.find(equipped.getString(slot.name(), ""));
                if (item != null && item.slot == slot) profile.equip(item);
            }
        }

        profile.normalizeLoadedState();
        return profile;
    }

    private static JsonValue object() { return new JsonValue(JsonValue.ValueType.object); }
    private static JsonValue array() { return new JsonValue(JsonValue.ValueType.array); }
    private static void put(JsonValue object, String name, String value) { object.addChild(name, new JsonValue(value)); }
    private static void put(JsonValue object, String name, boolean value) { object.addChild(name, new JsonValue(value)); }
    private static void put(JsonValue object, String name, int value) { object.addChild(name, new JsonValue((long)value)); }
    private static void put(JsonValue object, String name, long value) { object.addChild(name, new JsonValue(value)); }
    private static void put(JsonValue object, String name, float value) { object.addChild(name, new JsonValue((double)value)); }
}
