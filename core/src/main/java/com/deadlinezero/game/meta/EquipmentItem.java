package com.deadlinezero.game.meta;

public final class EquipmentItem {
    public enum Rarity { COMMON, RARE, EPIC, LEGENDARY, MYTHIC }

    public final String id;
    public final String name;
    public final PlayerProfile.EquipmentSlot slot;
    public final Rarity rarity;
    public final int level;
    public final float powerBonus;

    public EquipmentItem(String id, String name, PlayerProfile.EquipmentSlot slot,
                         Rarity rarity, int level, float powerBonus) {
        this.id = id;
        this.name = name;
        this.slot = slot;
        this.rarity = rarity;
        this.level = Math.max(1, level);
        this.powerBonus = Math.max(0f, powerBonus);
    }
}
