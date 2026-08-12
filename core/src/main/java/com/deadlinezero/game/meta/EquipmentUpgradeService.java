package com.deadlinezero.game.meta;

/** Credit-based equipment leveling. Items are immutable; upgrades return replacements. */
public final class EquipmentUpgradeService {
    private EquipmentUpgradeService() {}

    public static long cost(EquipmentItem item) {
        if (item == null) return Long.MAX_VALUE;
        float rarity = switch (item.rarity) {
            case COMMON -> 1f;
            case RARE -> 1.35f;
            case EPIC -> 1.85f;
            case LEGENDARY -> 2.6f;
            case MYTHIC -> 3.8f;
        };
        return Math.max(40L, Math.round((55f + item.level * item.level * 13f) * rarity));
    }

    public static EquipmentItem upgrade(PlayerProfile profile, EquipmentItem item) {
        if (profile == null || item == null) return item;
        long cost = cost(item);
        if (!profile.spend(PlayerProfile.Currency.CREDITS, cost)) return item;
        int nextLevel = item.level + 1;
        float nextPower = item.powerBonus * (1.075f + Math.min(.025f, nextLevel * .001f));
        return new EquipmentItem(item.id, item.name, item.slot, item.rarity, nextLevel, nextPower);
    }
}
