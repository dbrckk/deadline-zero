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
}
