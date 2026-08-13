package com.deadlinezero.game.progression;

import com.deadlinezero.game.entities.Player;

/** Standalone run-local legendary choices, intentionally separate from standard Upgrade. */
public enum LegendaryChoice {
    OVERDRIVE("OVERDRIVE", "High-output weapon and mobility transformation", 8) {
        @Override public boolean available(Player p) { return !p.legendary.hasOverdrive(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyOverdrive(p); }
    },
    SINGULARITY("SINGULARITY", "Dense penetrating projectile transformation", 10) {
        @Override public boolean available(Player p) { return !p.legendary.hasSingularity(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applySingularity(p); }
    },
    APEX("APEX PROTOCOL", "Instant Tier II ability network", 12) {
        @Override public boolean available(Player p) { return !p.legendary.hasApex(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyApex(p); }
    };

    public final String title;
    public final String description;
    public final int minimumLevel;

    LegendaryChoice(String title, String description, int minimumLevel) {
        this.title = title;
        this.description = description;
        this.minimumLevel = minimumLevel;
    }

    public final boolean eligible(Player player) {
        return player.level >= minimumLevel && available(player);
    }

    public abstract boolean available(Player player);
    public abstract boolean apply(Player player);
}
