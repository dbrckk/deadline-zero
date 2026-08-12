package com.deadlinezero.game.meta;

import com.badlogic.gdx.math.MathUtils;

/** Deterministic rules for equipment drop rarity and slot generation. */
public final class EquipmentDropTable {
    private static long sequence;

    private EquipmentDropTable() {}

    public static EquipmentItem roll(int stage, boolean bossKilled) {
        PlayerProfile.EquipmentSlot[] slots = PlayerProfile.EquipmentSlot.values();
        PlayerProfile.EquipmentSlot slot = slots[MathUtils.random(slots.length - 1)];
        EquipmentItem.Rarity rarity = rollRarity(stage, bossKilled);
        int level = Math.max(1, stage + MathUtils.random(-1, 2));
        float base = switch (rarity) {
            case COMMON -> .015f;
            case RARE -> .028f;
            case EPIC -> .050f;
            case LEGENDARY -> .082f;
            case MYTHIC -> .125f;
        };
        float power = base * (1f + (level - 1) * .065f);
        String id = "eq-" + (++sequence) + "-" + slot.name().toLowerCase();
        String name = rarity.name() + " " + prettySlot(slot);
        return new EquipmentItem(id, name, slot, rarity, level, power);
    }

    private static EquipmentItem.Rarity rollRarity(int stage, boolean bossKilled) {
        float bonus = Math.min(.16f, Math.max(1, stage) * .006f) + (bossKilled ? .08f : 0f);
        float r = MathUtils.random();
        if (r < .004f + bonus * .06f) return EquipmentItem.Rarity.MYTHIC;
        if (r < .025f + bonus * .18f) return EquipmentItem.Rarity.LEGENDARY;
        if (r < .115f + bonus * .55f) return EquipmentItem.Rarity.EPIC;
        if (r < .38f + bonus) return EquipmentItem.Rarity.RARE;
        return EquipmentItem.Rarity.COMMON;
    }

    private static String prettySlot(PlayerProfile.EquipmentSlot slot) {
        return switch (slot) {
            case WEAPON -> "Weapon";
            case ARMOR -> "Armor";
            case HELMET -> "Helmet";
            case GLOVES -> "Gloves";
            case BOOTS -> "Boots";
            case CORE -> "Core";
        };
    }
}
