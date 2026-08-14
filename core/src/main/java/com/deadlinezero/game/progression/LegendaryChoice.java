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
    },
    ION_CASCADE("ION CASCADE", "IN-11 overcharges every fourth shot with amplified capacitor output", 10) {
        @Override public boolean available(Player p) {
            return weapon(p, "ion_needle") && !p.legendary.hasIonCascade();
        }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyIonCascade(p); }
    },
    CINDER_FURNACE("CINDER FURNACE", "C90 thermal shells cycle faster and detonate with heavier payloads", 10) {
        @Override public boolean available(Player p) {
            return weapon(p, "cinder_cannon") && !p.legendary.hasCinderFurnace();
        }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyCinderFurnace(p); }
    },
    RAIL_PHASE_LANCE("PHASE LANCE", "VX Rail Rifle gains extreme penetration, velocity and precision damage", 10) {
        @Override public boolean available(Player p) {
            return weapon(p, "rail_rifle") && !p.legendary.hasRailPhaseLance();
        }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyRailPhaseLance(p); }
    },
    CRYO_PRISM("CRYO PRISM", "CR-7 splits into a three-lance frost prism with controlled spread", 10) {
        @Override public boolean available(Player p) {
            return weapon(p, "cryo_lance") && !p.legendary.hasCryoPrism();
        }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyCryoPrism(p); }
    },
    ARC_OVERLOAD("ARC OVERLOAD", "A9 doubles its shock vectors to multiply chain-lightning pressure", 10) {
        @Override public boolean available(Player p) {
            return weapon(p, "arc_carbine") && !p.legendary.hasArcOverload();
        }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyArcOverload(p); }
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
        return player != null && player.level >= minimumLevel && available(player);
    }

    private static boolean weapon(Player player, String id) {
        return player != null && player.weapon != null && player.weapon.definition != null
            && id.equals(player.weapon.definition.id);
    }

    public abstract boolean available(Player player);
    public abstract boolean apply(Player player);
}
