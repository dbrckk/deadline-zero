package com.deadlinezero.game.combat;

/**
 * Per-run deterministic signature passives for late-game weapons.
 * Kept data-only so projectile decoration remains testable and independent from screens.
 */
public final class WeaponSignatureRuntime {
    public record ShotModifier(boolean active, boolean forceCritical, float damageMultiplier,
                               int penetrationBonus, float knockbackMultiplier, float radius) {
        static ShotModifier none() { return new ShotModifier(false, false, 1f, 0, 1f, .11f); }
    }

    private static String weaponId = "ar9";
    private static float weaponCritMultiplier = 1f;
    private static int shotIndex;

    private WeaponSignatureRuntime() {}

    public static void begin(WeaponDefinition definition) {
        WeaponDefinition safe = definition == null ? WeaponCatalog.AR9 : definition;
        weaponId = safe.id;
        weaponCritMultiplier = Math.max(1f, safe.critMultiplier);
        shotIndex = 0;
    }

    /** Called exactly once per spawned player projectile. */
    public static ShotModifier consumeShot(boolean alreadyCritical) {
        shotIndex++;
        if ("ion_needle".equals(weaponId) && shotIndex % 5 == 0) {
            // Capacitor overcharge: every fifth needle is guaranteed to crit and pierces one extra target.
            float critMultiplier = alreadyCritical ? 1.12f : weaponCritMultiplier;
            return new ShotModifier(true, true, critMultiplier, 1, 1.18f, .135f);
        }
        if ("cinder_cannon".equals(weaponId) && shotIndex % 4 == 0) {
            // Thermal cycle: every fourth shell dumps stored heat into a heavier incendiary payload.
            return new ShotModifier(true, false, 1.55f, 1, 1.28f, .17f);
        }
        return ShotModifier.none();
    }

    public static int shotIndex() { return shotIndex; }
    public static String weaponId() { return weaponId; }
}
