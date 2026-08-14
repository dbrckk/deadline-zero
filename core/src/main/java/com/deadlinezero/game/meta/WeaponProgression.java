package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;

/** Deterministic, non-paywalled weapon unlock progression. */
public final class WeaponProgression {
    private WeaponProgression() {}

    public static boolean unlocked(PlayerProfile profile, WeaponDefinition weapon) {
        if (weapon == null || weapon == WeaponCatalog.AR9) return true;
        if (profile == null) return false;
        int level = Math.max(1, profile.accountLevel);
        int stage = Math.max(1, profile.highestStage);
        return switch (weapon.id) {
            case "scattergun" -> level >= 2;
            case "inferno_smg" -> level >= 4;
            case "cryo_lance" -> level >= 6 || stage >= 4;
            case "arc_carbine" -> level >= 8 || stage >= 6;
            case "rail_rifle" -> level >= 10 || stage >= 8;
            case "breacher" -> level >= 12 || stage >= 10;
            case "ion_needle" -> level >= 14 || stage >= 12;
            case "cinder_cannon" -> level >= 16 || stage >= 14;
            default -> false;
        };
    }

    public static int unlockAccountLevel(WeaponDefinition weapon) {
        if (weapon == null || weapon == WeaponCatalog.AR9) return 1;
        return switch (weapon.id) {
            case "scattergun" -> 2;
            case "inferno_smg" -> 4;
            case "cryo_lance" -> 6;
            case "arc_carbine" -> 8;
            case "rail_rifle" -> 10;
            case "breacher" -> 12;
            case "ion_needle" -> 14;
            case "cinder_cannon" -> 16;
            default -> Integer.MAX_VALUE;
        };
    }
}
