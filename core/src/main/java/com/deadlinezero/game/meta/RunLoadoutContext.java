package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;

/** Immutable snapshot of gear, weapon and survivor bonuses for the active run. */
public final class RunLoadoutContext {
    private static float maxHpMultiplier = 1f;
    private static float moveSpeedMultiplier = 1f;
    private static float dashCooldownMultiplier = 1f;
    private static float dashInvulnerabilitySeconds = .30f;
    private static float weaponDamageMultiplier = 1f;
    private static float critChanceBonus;
    private static float critDamageBonus;
    private static float abilityPowerMultiplier = 1f;
    private static float damageTakenMultiplier = 1f;
    private static int startingTeslaLevel;
    private static int ascensionSetPieces;
    private static boolean zeroDayCoreEquipped;
    private static SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.REX;
    private static WeaponDefinition weaponDefinition = WeaponCatalog.AR9;
    private static WeaponSynergyRules.Synergy weaponSynergy = WeaponSynergyRules.Synergy.NONE;

    private RunLoadoutContext() {}

    public static void begin(PlayerProfile profile) {
        float hp = 0f, speed = 0f, dash = 0f, weapon = 0f, crit = 0f, ability = 0f;
        survivor = profile == null ? SurvivorCatalog.Survivor.REX : profile.selectedSurvivor;
        weaponDefinition = profile == null ? WeaponCatalog.AR9 : profile.selectedWeapon();
        weaponSynergy = WeaponSynergyRules.resolve(survivor, weaponDefinition);
        WeaponSignatureRuntime.begin(weaponDefinition);
        float levelPower = profile == null ? 1f : profile.survivors.levelPowerMultiplier(survivor);
        ascensionSetPieces = ThreatSetBonusRules.equippedPieces(profile);
        zeroDayCoreEquipped = SingularityCoreRules.equipped(profile);
        SingularityCoreRuntime.begin(zeroDayCoreEquipped);
        if (profile != null) {
            EquipmentItem weaponItem = profile.equipped(PlayerProfile.EquipmentSlot.WEAPON);
            EquipmentItem armor = profile.equipped(PlayerProfile.EquipmentSlot.ARMOR);
            EquipmentItem helmet = profile.equipped(PlayerProfile.EquipmentSlot.HELMET);
            EquipmentItem boots = profile.equipped(PlayerProfile.EquipmentSlot.BOOTS);
            EquipmentItem gloves = profile.equipped(PlayerProfile.EquipmentSlot.GLOVES);
            EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
            if (weaponItem != null) weapon += weaponItem.powerBonus * 1.10f;
            if (gloves != null) { weapon += gloves.powerBonus * .18f; crit += gloves.powerBonus * .12f; }
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

        float adaptive = survivor == SurvivorCatalog.Survivor.REX ? 1.05f : 1f;
        maxHpMultiplier = (1f + hp) * survivor.hpMultiplier * (1f + (levelPower - 1f) * .55f) * adaptive
            * ThreatSetBonusRules.hpMultiplier(ascensionSetPieces);
        moveSpeedMultiplier = (1f + speed) * survivor.speedMultiplier * adaptive
            * ThreatSetBonusRules.moveSpeedMultiplier(ascensionSetPieces);
        dashCooldownMultiplier = Math.max(.60f, (1f - dash) * (survivor == SurvivorCatalog.Survivor.WRAITH ? .76f : 1f));
        dashInvulnerabilitySeconds = (survivor == SurvivorCatalog.Survivor.WRAITH ? .42f : .30f)
            + ThreatSetBonusRules.dashInvulnerabilityBonus(ascensionSetPieces);
        weaponDamageMultiplier = (1f + weapon) * survivor.weaponMultiplier * levelPower * adaptive
            * ThreatSetBonusRules.weaponMultiplier(ascensionSetPieces) * weaponSynergy.weaponDamageMultiplier;
        critChanceBonus = Math.min(.25f, crit + survivor.critBonus + (survivor == SurvivorCatalog.Survivor.NYX ? .04f : 0f)
            + weaponSynergy.critChanceBonus);
        critDamageBonus = survivor == SurvivorCatalog.Survivor.NYX ? .40f : 0f;
        abilityPowerMultiplier = (1f + ability) * (1f + survivor.abilityBonus) * levelPower * adaptive
            * ThreatSetBonusRules.abilityMultiplier(ascensionSetPieces) * weaponSynergy.abilityPowerMultiplier;
        damageTakenMultiplier = (survivor == SurvivorCatalog.Survivor.BASTION ? .82f : 1f)
            * ThreatSetBonusRules.damageTakenMultiplier(ascensionSetPieces) * weaponSynergy.damageTakenMultiplier;
        startingTeslaLevel = survivor == SurvivorCatalog.Survivor.VOLT ? 1 : 0;
    }

    /** Clears all ephemeral run-derived state before returning to a durable menu state. */
    public static void end() {
        maxHpMultiplier = 1f;
        moveSpeedMultiplier = 1f;
        dashCooldownMultiplier = 1f;
        dashInvulnerabilitySeconds = .30f;
        weaponDamageMultiplier = 1f;
        critChanceBonus = 0f;
        critDamageBonus = 0f;
        abilityPowerMultiplier = 1f;
        damageTakenMultiplier = 1f;
        startingTeslaLevel = 0;
        ascensionSetPieces = 0;
        zeroDayCoreEquipped = false;
        survivor = SurvivorCatalog.Survivor.REX;
        weaponDefinition = WeaponCatalog.AR9;
        weaponSynergy = WeaponSynergyRules.Synergy.NONE;
        WeaponSignatureRuntime.begin(WeaponCatalog.AR9);
        SingularityCoreRuntime.begin(false);
    }

    public static float maxHpMultiplier() { return maxHpMultiplier; }
    public static float moveSpeedMultiplier() { return moveSpeedMultiplier; }
    public static float dashCooldownMultiplier() { return dashCooldownMultiplier; }
    public static float dashInvulnerabilitySeconds() { return dashInvulnerabilitySeconds; }
    public static float weaponDamageMultiplier() { return weaponDamageMultiplier; }
    public static float critChanceBonus() { return critChanceBonus; }
    public static float critDamageBonus() { return critDamageBonus; }
    public static float abilityPowerMultiplier() { return abilityPowerMultiplier; }
    public static float damageTakenMultiplier() { return damageTakenMultiplier; }
    public static int startingTeslaLevel() { return startingTeslaLevel; }
    public static int ascensionSetPieces() { return ascensionSetPieces; }
    public static boolean zeroDayCoreEquipped() { return zeroDayCoreEquipped; }
    public static SurvivorCatalog.Survivor survivor() { return survivor; }
    public static WeaponDefinition weaponDefinition() { return weaponDefinition; }
    public static WeaponSynergyRules.Synergy weaponSynergy() { return weaponSynergy; }
}
