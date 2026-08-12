package com.deadlinezero.game.meta;

/** Centralized equip/upgrade/merge rules for persistent gear. */
public final class EquipmentService {
    private EquipmentService() {}

    public static boolean equip(PlayerProfile profile, String itemId) {
        if (profile == null || itemId == null) return false;
        EquipmentItem item = profile.inventory.find(itemId);
        if (item == null) return false;
        profile.equip(item);
        return true;
    }

    public static boolean unequip(PlayerProfile profile, PlayerProfile.EquipmentSlot slot) {
        if (profile == null || slot == null || profile.equipped(slot) == null) return false;
        profile.unequip(slot);
        return true;
    }

    public static EquipmentItem bestForSlot(PlayerProfile profile, PlayerProfile.EquipmentSlot slot) {
        if (profile == null || slot == null) return null;
        EquipmentItem best = null;
        for (EquipmentItem item : profile.inventory.items()) {
            if (item.slot != slot) continue;
            if (best == null || score(item) > score(best)) best = item;
        }
        return best;
    }

    public static boolean upgrade(PlayerProfile profile, String itemId) {
        if (profile == null || itemId == null) return false;
        EquipmentItem current = profile.inventory.find(itemId);
        if (current == null) return false;
        EquipmentItem upgraded = EquipmentUpgradeService.upgrade(profile, current);
        if (upgraded == current) return false;
        boolean equipped = profile.equipped(current.slot) != null && itemId.equals(profile.equipped(current.slot).id);
        profile.inventory.replace(upgraded);
        if (equipped) profile.equip(upgraded);
        return true;
    }

    /** Merge three same-slot/same-rarity items into one item of the next rarity. */
    public static EquipmentItem mergeThree(PlayerProfile profile, String aId, String bId, String cId) {
        if (profile == null || aId == null || bId == null || cId == null) return null;
        EquipmentItem a = profile.inventory.find(aId);
        EquipmentItem b = profile.inventory.find(bId);
        EquipmentItem c = profile.inventory.find(cId);
        if (a == null || b == null || c == null || a == b || a == c || b == c) return null;
        if (a.slot != b.slot || a.slot != c.slot || a.rarity != b.rarity || a.rarity != c.rarity) return null;
        EquipmentItem.Rarity next = nextRarity(a.rarity);
        if (next == a.rarity) return null;
        int level = Math.max(a.level, Math.max(b.level, c.level));
        float power = Math.max(a.powerBonus, Math.max(b.powerBonus, c.powerBonus)) * 1.62f;
        EquipmentItem merged = new EquipmentItem("merge-" + System.nanoTime(), next.name() + " " + pretty(a.slot), a.slot, next, level, power);
        profile.inventory.remove(a.id);
        profile.inventory.remove(b.id);
        profile.inventory.remove(c.id);
        profile.inventory.add(merged);
        profile.equip(merged);
        return merged;
    }

    public static float score(EquipmentItem item) {
        if (item == null) return 0f;
        return item.powerBonus * (1f + item.level * .01f);
    }

    private static EquipmentItem.Rarity nextRarity(EquipmentItem.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> EquipmentItem.Rarity.RARE;
            case RARE -> EquipmentItem.Rarity.EPIC;
            case EPIC -> EquipmentItem.Rarity.LEGENDARY;
            case LEGENDARY -> EquipmentItem.Rarity.MYTHIC;
            case MYTHIC -> EquipmentItem.Rarity.MYTHIC;
        };
    }

    private static String pretty(PlayerProfile.EquipmentSlot slot) {
        String s = slot.name().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
