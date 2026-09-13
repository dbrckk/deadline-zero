package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;

/** Deterministic survivor x weapon build synergies applied once when a run loadout is snapshotted. */
public final class WeaponSynergyRules {
    public enum Synergy {
        NONE("NO SIGNATURE SYNERGY", 1f, 1f, 0f, 1f),
        ARC_CONDUCTOR("ARC CONDUCTOR", 1.10f, 1.08f, .04f, 1f),
        EXECUTION_PROTOCOL("EXECUTION PROTOCOL", 1.08f, 1f, .06f, 1f),
        SIEGE_FURNACE("SIEGE FURNACE", 1.12f, 1f, 0f, .95f),
        CRYO_GHOST("CRYO GHOST", 1.06f, 1.10f, .03f, 1f),
        FROST_PHANTOM("FROST PHANTOM", 1.08f, 1.08f, .02f, .98f),
        TEMPEST_CIRCUIT("TEMPEST CIRCUIT", 1.11f, 1.10f, .03f, 1f),
        DEADEYE_MAGNUM("DEADEYE MAGNUM", 1.09f, 1f, .07f, 1f);

        public final String displayName;
        public final float weaponDamageMultiplier;
        public final float abilityPowerMultiplier;
        public final float critChanceBonus;
        public final float damageTakenMultiplier;

        Synergy(String displayName, float weaponDamageMultiplier, float abilityPowerMultiplier,
                float critChanceBonus, float damageTakenMultiplier) {
            this.displayName = displayName;
            this.weaponDamageMultiplier = weaponDamageMultiplier;
            this.abilityPowerMultiplier = abilityPowerMultiplier;
            this.critChanceBonus = critChanceBonus;
            this.damageTakenMultiplier = damageTakenMultiplier;
        }
    }

    private WeaponSynergyRules() {}

    public static Synergy resolve(SurvivorCatalog.Survivor survivor, WeaponDefinition weapon) {
        SurvivorCatalog.Survivor safeSurvivor = survivor == null ? SurvivorCatalog.Survivor.REX : survivor;
        WeaponDefinition safeWeapon = weapon == null ? WeaponCatalog.AR9 : weapon;

        if (safeWeapon == WeaponCatalog.ION_NEEDLE) {
            if (safeSurvivor == SurvivorCatalog.Survivor.VOLT) return Synergy.ARC_CONDUCTOR;
            if (safeSurvivor == SurvivorCatalog.Survivor.NYX) return Synergy.EXECUTION_PROTOCOL;
        }
        if (safeWeapon == WeaponCatalog.CINDER_CANNON && safeSurvivor == SurvivorCatalog.Survivor.BASTION) {
            return Synergy.SIEGE_FURNACE;
        }
        if (safeWeapon == WeaponCatalog.CRYO_LANCE && safeSurvivor == SurvivorCatalog.Survivor.WRAITH) {
            return Synergy.CRYO_GHOST;
        }
        if (safeWeapon == WeaponCatalog.GLACIER_REPEATER && safeSurvivor == SurvivorCatalog.Survivor.WRAITH) {
            return Synergy.FROST_PHANTOM;
        }
        if (safeWeapon == WeaponCatalog.STORM_COIL && safeSurvivor == SurvivorCatalog.Survivor.VOLT) {
            return Synergy.TEMPEST_CIRCUIT;
        }
        if (safeWeapon == WeaponCatalog.TITAN_REVOLVER && safeSurvivor == SurvivorCatalog.Survivor.NYX) {
            return Synergy.DEADEYE_MAGNUM;
        }
        return Synergy.NONE;
    }
}
