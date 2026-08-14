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
    VANGUARD_PROTOCOL("VANGUARD PROTOCOL", "AR-9 becomes a hyper-stable penetrating assault platform", 10) {
        @Override public boolean available(Player p) { return weapon(p, "ar9") && !p.legendary.hasVanguardProtocol(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyVanguardProtocol(p); }
    },
    SCATTER_MAELSTROM("SCATTER MAELSTROM", "M12 widens its pellet storm while preserving controlled burst damage", 10) {
        @Override public boolean available(Player p) { return weapon(p, "scattergun") && !p.legendary.hasScatterMaelstrom(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyScatterMaelstrom(p); }
    },
    INFERNO_PYROCLASM("PYROCLASM", "HX-4 doubles flame vectors into a compact incendiary crossfire", 10) {
        @Override public boolean available(Player p) { return weapon(p, "inferno_smg") && !p.legendary.hasInfernoPyroclasm(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyInfernoPyroclasm(p); }
    },
    BREACHER_RUPTURE("RUPTURE MATRIX", "BXR expands its breach cone with heavier stagger and penetration", 10) {
        @Override public boolean available(Player p) { return weapon(p, "breacher") && !p.legendary.hasBreacherRupture(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyBreacherRupture(p); }
    },
    ION_CASCADE("ION CASCADE", "IN-11 overcharges every fourth shot with amplified capacitor output", 10) {
        @Override public boolean available(Player p) { return weapon(p, "ion_needle") && !p.legendary.hasIonCascade(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyIonCascade(p); }
    },
    CINDER_FURNACE("CINDER FURNACE", "C90 thermal shells cycle faster and detonate with heavier payloads", 10) {
        @Override public boolean available(Player p) { return weapon(p, "cinder_cannon") && !p.legendary.hasCinderFurnace(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyCinderFurnace(p); }
    },
    RAIL_PHASE_LANCE("PHASE LANCE", "VX Rail Rifle gains extreme penetration, velocity and precision damage", 10) {
        @Override public boolean available(Player p) { return weapon(p, "rail_rifle") && !p.legendary.hasRailPhaseLance(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyRailPhaseLance(p); }
    },
    CRYO_PRISM("CRYO PRISM", "CR-7 splits into a three-lance frost prism with controlled spread", 10) {
        @Override public boolean available(Player p) { return weapon(p, "cryo_lance") && !p.legendary.hasCryoPrism(); }
        @Override public boolean apply(Player p) { return LegendaryEffects.applyCryoPrism(p); }
    },
    ARC_OVERLOAD("ARC OVERLOAD", "A9 doubles its shock vectors to multiply chain-lightning pressure", 10) {
        @Override public boolean available(Player p) { return weapon(p, "arc_carbine") && !p.legendary.hasArcOverload(); }
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

    public final boolean eligible(Player player) { return player != null && player.level >= minimumLevel && available(player); }
    private static boolean weapon(Player player, String id) {
        return player != null && player.weapon != null && player.weapon.definition != null && id.equals(player.weapon.definition.id);
    }
    public abstract boolean available(Player player);
    public abstract boolean apply(Player player);
}
