package com.deadlinezero.game.meta;

import java.util.EnumMap;

/** Long-term account progression. Storage is intentionally separate from gameplay runtime. */
public final class PlayerProfile {
    public enum Currency { CREDITS, GEMS }
    public enum EquipmentSlot { WEAPON, ARMOR, HELMET, GLOVES, BOOTS, CORE }

    private final EnumMap<Currency, Long> currencies = new EnumMap<>(Currency.class);
    private final EnumMap<EquipmentSlot, EquipmentItem> equipped = new EnumMap<>(EquipmentSlot.class);
    public int accountLevel = 1;
    public long accountXp;
    public int highestStage = 1;
    public int totalRuns;
    public long totalKills;

    public PlayerProfile() {
        for (Currency currency : Currency.values()) currencies.put(currency, 0L);
    }

    public long currency(Currency currency) { return currencies.getOrDefault(currency, 0L); }

    public void addCurrency(Currency currency, long amount) {
        if (amount <= 0) return;
        currencies.put(currency, currency(currency) + amount);
    }

    public boolean spend(Currency currency, long amount) {
        if (amount <= 0 || currency(currency) < amount) return false;
        currencies.put(currency, currency(currency) - amount);
        return true;
    }

    public EquipmentItem equipped(EquipmentSlot slot) { return equipped.get(slot); }
    public void equip(EquipmentItem item) { if (item != null) equipped.put(item.slot, item); }

    public float aggregatePowerMultiplier() {
        float bonus = 0f;
        for (EquipmentItem item : equipped.values()) if (item != null) bonus += item.powerBonus;
        return 1f + bonus;
    }
}
