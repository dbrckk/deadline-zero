package com.deadlinezero.game.combat;

/**
 * Per-run deterministic signature passives for late-game weapons.
 * Kept data-only so projectile decoration remains testable and independent from screens.
 */
public final class WeaponSignatureRuntime {
    public enum Kind { NONE, ION_OVERCHARGE, CINDER_OVERHEAT }

    public record ShotModifier(Kind kind, boolean active, boolean forceCritical, float damageMultiplier,
                               int penetrationBonus, float knockbackMultiplier, float radius) {
        static ShotModifier none() { return new ShotModifier(Kind.NONE, false, false, 1f, 0, 1f, .11f); }
    }

    private static String weaponId = "ar9";
    private static float weaponCritMultiplier = 1f;
    private static int shotIndex;
    private static boolean ionCascade;
    private static boolean cinderFurnace;

    private WeaponSignatureRuntime() {}

    public static void begin(WeaponDefinition definition) {
        WeaponDefinition safe = definition == null ? WeaponCatalog.AR9 : definition;
        weaponId = safe.id;
        weaponCritMultiplier = Math.max(1f, safe.critMultiplier);
        shotIndex = 0;
        ionCascade = false;
        cinderFurnace = false;
    }

    public static void enableIonCascade() {
        if (!"ion_needle".equals(weaponId)) return;
        ionCascade = true;
        shotIndex = 0;
    }

    public static void enableCinderFurnace() {
        if (!"cinder_cannon".equals(weaponId)) return;
        cinderFurnace = true;
        shotIndex = 0;
    }

    /** Called exactly once per spawned player projectile. */
    public static ShotModifier consumeShot(boolean alreadyCritical) {
        shotIndex++;
        int ionCadence = ionCascade ? 4 : 5;
        if ("ion_needle".equals(weaponId) && shotIndex % ionCadence == 0) {
            float critMultiplier = alreadyCritical ? (ionCascade ? 1.18f : 1.12f) : weaponCritMultiplier;
            float legendaryBoost = ionCascade ? 1.10f : 1f;
            return new ShotModifier(Kind.ION_OVERCHARGE, true, true,
                critMultiplier * legendaryBoost, ionCascade ? 2 : 1,
                ionCascade ? 1.28f : 1.18f, ionCascade ? .15f : .135f);
        }
        int cinderCadence = cinderFurnace ? 3 : 4;
        if ("cinder_cannon".equals(weaponId) && shotIndex % cinderCadence == 0) {
            return new ShotModifier(Kind.CINDER_OVERHEAT, true, false,
                cinderFurnace ? 1.72f : 1.55f, cinderFurnace ? 2 : 1,
                cinderFurnace ? 1.42f : 1.28f, cinderFurnace ? .21f : .17f);
        }
        return ShotModifier.none();
    }

    public static int shotIndex() { return shotIndex; }
    public static String weaponId() { return weaponId; }
    public static boolean ionCascadeEnabled() { return ionCascade; }
    public static boolean cinderFurnaceEnabled() { return cinderFurnace; }
}
