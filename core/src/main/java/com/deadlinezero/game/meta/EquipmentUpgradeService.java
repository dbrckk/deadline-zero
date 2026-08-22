package com.deadlinezero.game.meta;

/** Credit-based equipment leveling. Items are immutable; upgrades return replacements. */
public final class EquipmentUpgradeService {
    private EquipmentUpgradeService() {}

    public static long cost(EquipmentItem item) {
        if (item == null) return Long.MAX_VALUE;
        double rarity = switch (item.rarity) {
            case COMMON -> 1d;
            case RARE -> 1.35d;
            case EPIC -> 1.85d;
            case LEGENDARY -> 2.6d;
            case MYTHIC -> 3.8d;
        };
        double level = item.level;
        double raw = (55d + level * level * 13d) * rarity;
        if (!Double.isFinite(raw) || raw >= Long.MAX_VALUE) return Long.MAX_VALUE;
        return Math.max(40L, Math.round(raw));
    }

    public static EquipmentItem upgrade(PlayerProfile profile, EquipmentItem item) {
        if (profile == null || item == null) return item;
        if (item.level == Integer.MAX_VALUE) return item;
        double multiplier = 1.075d + Math.min(.025d, (item.level + 1d) * .001d);
        double projectedPower = item.powerBonus * multiplier;
        if (!Double.isFinite(projectedPower) || projectedPower > Float.MAX_VALUE) return item;
        long cost = cost(item);
        if (!profile.spend(PlayerProfile.Currency.CREDITS, cost)) return item;
        int nextLevel = item.level + 1;
        return new EquipmentItem(item.id, item.name, item.slot, item.rarity, nextLevel, (float) projectedPower);
    }
}
