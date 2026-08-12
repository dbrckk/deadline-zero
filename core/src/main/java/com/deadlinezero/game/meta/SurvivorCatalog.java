package com.deadlinezero.game.meta;

/** Static playable survivor definitions. */
public final class SurvivorCatalog {
    public enum Survivor {
        REX("Rex", "Vanguard", 1.00f, 1.00f, 1.00f, 0f, 0f),
        NYX("Nyx", "Sharpshooter", .92f, 1.12f, 1.04f, .06f, 0f),
        BASTION("Bastion", "Juggernaut", 1.24f, .92f, .94f, 0f, 0f),
        VOLT("Volt", "Technician", .96f, 1.00f, 1.02f, 0f, .18f),
        WRAITH("Wraith", "Runner", .90f, 1.06f, 1.16f, .03f, .08f);

        public final String displayName;
        public final String role;
        public final float hpMultiplier;
        public final float weaponMultiplier;
        public final float speedMultiplier;
        public final float critBonus;
        public final float abilityBonus;

        Survivor(String displayName, String role, float hpMultiplier, float weaponMultiplier,
                 float speedMultiplier, float critBonus, float abilityBonus) {
            this.displayName = displayName;
            this.role = role;
            this.hpMultiplier = hpMultiplier;
            this.weaponMultiplier = weaponMultiplier;
            this.speedMultiplier = speedMultiplier;
            this.critBonus = critBonus;
            this.abilityBonus = abilityBonus;
        }
    }

    private SurvivorCatalog() {}

    public static Survivor byName(String name) {
        if (name == null) return Survivor.REX;
        try { return Survivor.valueOf(name); }
        catch (IllegalArgumentException ignored) { return Survivor.REX; }
    }
}
