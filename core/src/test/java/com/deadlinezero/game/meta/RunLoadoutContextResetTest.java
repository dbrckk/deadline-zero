package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.deadlinezero.game.combat.WeaponCatalog;
import org.junit.jupiter.api.Test;

final class RunLoadoutContextResetTest {
    @Test void endRestoresSafeDefaultLoadoutState() {
        PlayerProfile profile = new PlayerProfile();
        profile.selectedSurvivor = SurvivorCatalog.Survivor.WRAITH;
        profile.selectedWeaponId = WeaponCatalog.AR9.id;

        RunLoadoutContext.begin(profile);
        RunLoadoutContext.end();

        assertEquals(1f, RunLoadoutContext.maxHpMultiplier(), 0.0001f);
        assertEquals(1f, RunLoadoutContext.moveSpeedMultiplier(), 0.0001f);
        assertEquals(1f, RunLoadoutContext.dashCooldownMultiplier(), 0.0001f);
        assertEquals(.30f, RunLoadoutContext.dashInvulnerabilitySeconds(), 0.0001f);
        assertEquals(1f, RunLoadoutContext.weaponDamageMultiplier(), 0.0001f);
        assertEquals(0f, RunLoadoutContext.critChanceBonus(), 0.0001f);
        assertEquals(0f, RunLoadoutContext.critDamageBonus(), 0.0001f);
        assertEquals(1f, RunLoadoutContext.abilityPowerMultiplier(), 0.0001f);
        assertEquals(1f, RunLoadoutContext.damageTakenMultiplier(), 0.0001f);
        assertEquals(0, RunLoadoutContext.startingTeslaLevel());
        assertEquals(0, RunLoadoutContext.ascensionSetPieces());
        assertFalse(RunLoadoutContext.zeroDayCoreEquipped());
        assertEquals(SurvivorCatalog.Survivor.REX, RunLoadoutContext.survivor());
        assertEquals(WeaponCatalog.AR9.id, RunLoadoutContext.weaponDefinition().id);
        assertEquals(WeaponSynergyRules.Synergy.NONE, RunLoadoutContext.weaponSynergy());
    }
}
