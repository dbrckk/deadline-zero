package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.combat.WeaponCatalog;
import org.junit.jupiter.api.Test;

final class WeaponProgressionTest {
    @Test
    void starterWeaponIsAlwaysAvailable() {
        assertTrue(WeaponProgression.unlocked(null, WeaponCatalog.AR9));
        PlayerProfile profile = new PlayerProfile();
        assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.AR9));
        assertEquals(WeaponCatalog.AR9, profile.selectedWeapon());
    }

    @Test
    void lockedWeaponCannotBeSelectedEarly() {
        PlayerProfile profile = new PlayerProfile();
        assertFalse(profile.selectWeapon(WeaponCatalog.RAIL_RIFLE));
        assertEquals(WeaponCatalog.AR9, profile.selectedWeapon());
    }

    @Test
    void accountLevelUnlocksWeaponSelection() {
        PlayerProfile profile = new PlayerProfile();
        profile.accountLevel = 8;
        assertTrue(profile.selectWeapon(WeaponCatalog.ARC_CARBINE));
        assertEquals(WeaponCatalog.ARC_CARBINE, profile.selectedWeapon());
    }

    @Test
    void stageProgressCanUnlockControlWeaponsEarlier() {
        PlayerProfile profile = new PlayerProfile();
        profile.accountLevel = 2;
        profile.highestStage = 6;
        assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.ARC_CARBINE));
        assertTrue(profile.selectWeapon(WeaponCatalog.ARC_CARBINE));
    }

    @Test
    void invalidStoredWeaponFallsBackSafely() {
        PlayerProfile profile = new PlayerProfile();
        profile.selectedWeaponId = "corrupted_weapon_id";
        profile.validateSelectedWeapon();
        assertEquals(WeaponCatalog.AR9, profile.selectedWeapon());
    }
}
