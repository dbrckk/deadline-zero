package com.deadlinezero.game.combat;

import com.deadlinezero.game.config.GameConfig;

public final class WeaponCatalog {
    private WeaponCatalog() {}

    public static final WeaponDefinition AR9 = new WeaponDefinition(
        "ar9", "AR-9 Vanguard",
        GameConfig.PLAYER_DAMAGE,
        GameConfig.PLAYER_FIRE_INTERVAL,
        GameConfig.PROJECTILE_SPEED,
        1, 5.5f,
        0.08f, 2f,
        0, 1.1f,
        DamageElement.KINETIC
    );

    public static final WeaponDefinition SCATTERGUN = new WeaponDefinition(
        "scattergun", "M12 Scattergun",
        15f, 0.72f, 16f,
        6, 8.5f,
        0.06f, 1.8f,
        0, 2.4f,
        DamageElement.KINETIC
    );

    public static final WeaponDefinition RAIL_RIFLE = new WeaponDefinition(
        "rail_rifle", "VX Rail Rifle",
        72f, 0.95f, 30f,
        1, 0f,
        0.15f, 2.4f,
        4, 4.2f,
        DamageElement.KINETIC
    );

    /** Fast elemental rifle: lower raw impact, reliable burn pressure and excellent kiting. */
    public static final WeaponDefinition INFERNO_SMG = new WeaponDefinition(
        "inferno_smg", "HX-4 Inferno",
        8.6f, 0.105f, 23f,
        1, 7.0f,
        0.07f, 1.75f,
        0, .70f,
        DamageElement.FIRE
    );

    /** Precision frost weapon: strong control with measured cadence and mild penetration. */
    public static final WeaponDefinition CRYO_LANCE = new WeaponDefinition(
        "cryo_lance", "CR-7 Cryo Lance",
        31f, 0.42f, 25f,
        1, 1.2f,
        0.11f, 2.05f,
        1, 1.55f,
        DamageElement.FROST
    );

    /** Chain-oriented shock carbine: moderate DPS but premium crowd disruption. */
    public static final WeaponDefinition ARC_CARBINE = new WeaponDefinition(
        "arc_carbine", "A9 Arc Carbine",
        17.5f, 0.255f, 22f,
        1, 3.0f,
        0.10f, 1.95f,
        1, 1.05f,
        DamageElement.SHOCK
    );

    /** High-risk close-range burst weapon that rewards aggressive positioning. */
    public static final WeaponDefinition BREACHER = new WeaponDefinition(
        "breacher", "BXR Breacher",
        10.8f, 0.46f, 17f,
        9, 11.5f,
        0.05f, 1.70f,
        0, 3.0f,
        DamageElement.KINETIC
    );

    /** Endgame precision needle: extremely fast, crit-heavy and naturally pierces clustered specialists. */
    public static final WeaponDefinition ION_NEEDLE = new WeaponDefinition(
        "ion_needle", "IN-11 Ion Needle",
        9.2f, 0.095f, 31f,
        1, .65f,
        .22f, 2.15f,
        2, .62f,
        DamageElement.SHOCK
    );

    /** Heavy incendiary cannon: deliberately slow cadence, massive impact and persistent burn pressure. */
    public static final WeaponDefinition CINDER_CANNON = new WeaponDefinition(
        "cinder_cannon", "C90 Cinder Cannon",
        86f, 1.08f, 18f,
        1, 0f,
        .08f, 2.25f,
        1, 5.4f,
        DamageElement.FIRE
    );

    private static final WeaponDefinition[] ALL = {
        AR9, SCATTERGUN, RAIL_RIFLE, INFERNO_SMG, CRYO_LANCE, ARC_CARBINE, BREACHER,
        ION_NEEDLE, CINDER_CANNON
    };

    public static WeaponDefinition[] all() { return ALL.clone(); }

    public static WeaponDefinition byId(String id) {
        if (id == null) return AR9;
        for (WeaponDefinition definition : ALL) {
            if (definition.id.equalsIgnoreCase(id)) return definition;
        }
        return AR9;
    }

    /** Simple paper-DPS estimate used for balance tests; utility is intentionally excluded. */
    public static float paperDps(WeaponDefinition weapon) {
        if (weapon == null) return 0f;
        return weapon.damage * Math.max(1, weapon.projectileCount) / Math.max(.04f, weapon.fireInterval);
    }
}
