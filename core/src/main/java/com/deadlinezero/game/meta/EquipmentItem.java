package com.deadlinezero.game.meta;

public final class EquipmentItem {
    public enum Rarity { COMMON, RARE, EPIC, LEGENDARY, MYTHIC }

    public final String id;
    public final String name;
    public final PlayerProfile.EquipmentSlot slot;
    public final Rarity rarity;
    public final int level;
    public final float powerBonus;

    public String rarityKey() { return "equipment.rarity." + rarity.name().toLowerCase(java.util.Locale.ROOT); }
    public String slotKey() { return "equipment.slot." + slot.name().toLowerCase(java.util.Locale.ROOT); }
    public String nameKey() {
        return ThreatMilestoneRewardCatalog.isExclusiveId(id) ? "equipment." + id + ".name" : null;
    }

    public EquipmentItem(String id, String name, PlayerProfile.EquipmentSlot slot,
                         Rarity rarity, int level, float powerBonus) {
        this.id = id;
        this.name = name;
        this.slot = slot;
        this.rarity = rarity;
        this.level = Math.max(1, level);
        this.powerBonus = Float.isFinite(powerBonus) ? Math.max(0f, powerBonus) : 0f;
    }
}
