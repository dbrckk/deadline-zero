package com.deadlinezero.game.meta;

/** Immutable snapshot of specialized equipped-item bonuses for the active run. */
public final class RunLoadoutContext {
    private static float maxHpMultiplier = 1f;
    private static float moveSpeedMultiplier = 1f;
    private static float dashCooldownMultiplier = 1f;

    private RunLoadoutContext() {}

    public static void begin(PlayerProfile profile) {
        float hp = 0f;
        float speed = 0f;
        float dash = 0f;
        if (profile != null) {
            EquipmentItem armor = profile.equipped(PlayerProfile.EquipmentSlot.ARMOR);
            EquipmentItem helmet = profile.equipped(PlayerProfile.EquipmentSlot.HELMET);
            EquipmentItem boots = profile.equipped(PlayerProfile.EquipmentSlot.BOOTS);
            EquipmentItem gloves = profile.equipped(PlayerProfile.EquipmentSlot.GLOVES);
            EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
            if (armor != null) hp += armor.powerBonus * 1.15f;
            if (helmet != null) hp += helmet.powerBonus * .55f;
            if (core != null) hp += core.powerBonus * .20f;
            if (boots != null) speed += boots.powerBonus * .55f;
            if (gloves != null) speed += gloves.powerBonus * .12f;
            if (core != null) dash += core.powerBonus * .28f;
            if (boots != null) dash += boots.powerBonus * .18f;
        }
        maxHpMultiplier = 1f + hp;
        moveSpeedMultiplier = 1f + speed;
        dashCooldownMultiplier = Math.max(.72f, 1f - dash);
    }

    public static float maxHpMultiplier() { return maxHpMultiplier; }
    public static float moveSpeedMultiplier() { return moveSpeedMultiplier; }
    public static float dashCooldownMultiplier() { return dashCooldownMultiplier; }
}
