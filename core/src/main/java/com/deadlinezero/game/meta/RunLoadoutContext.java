package com.deadlinezero.game.meta;

/** Immutable snapshot of specialized equipped-item bonuses for the active run. */
public final class RunLoadoutContext {
    private static float maxHpMultiplier = 1f;
    private static float moveSpeedMultiplier = 1f;
    private static float dashCooldownMultiplier = 1f;
    private static float weaponDamageMultiplier = 1f;
    private static float critChanceBonus;
    private static float abilityPowerMultiplier = 1f;

    private RunLoadoutContext() {}

    public static void begin(PlayerProfile profile) {
        float hp = 0f;
        float speed = 0f;
        float dash = 0f;
        float weapon = 0f;
        float crit = 0f;
        float ability = 0f;
        if (profile != null) {
            EquipmentItem weaponItem = profile.equipped(PlayerProfile.EquipmentSlot.WEAPON);
            EquipmentItem armor = profile.equipped(PlayerProfile.EquipmentSlot.ARMOR);
            EquipmentItem helmet = profile.equipped(PlayerProfile.EquipmentSlot.HELMET);
            EquipmentItem boots = profile.equipped(PlayerProfile.EquipmentSlot.BOOTS);
            EquipmentItem gloves = profile.equipped(PlayerProfile.EquipmentSlot.GLOVES);
            EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
            if (weaponItem != null) weapon += weaponItem.powerBonus * 1.10f;
            if (gloves != null) {
                weapon += gloves.powerBonus * .18f;
                crit += gloves.powerBonus * .12f;
            }
            if (core != null) ability += core.powerBonus * 1.05f;
            if (helmet != null) ability += helmet.powerBonus * .12f;
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
        weaponDamageMultiplier = 1f + weapon;
        critChanceBonus = Math.min(.18f, crit);
        abilityPowerMultiplier = 1f + ability;
    }

    public static float maxHpMultiplier() { return maxHpMultiplier; }
    public static float moveSpeedMultiplier() { return moveSpeedMultiplier; }
    public static float dashCooldownMultiplier() { return dashCooldownMultiplier; }
    public static float weaponDamageMultiplier() { return weaponDamageMultiplier; }
    public static float critChanceBonus() { return critChanceBonus; }
    public static float abilityPowerMultiplier() { return abilityPowerMultiplier; }
}
