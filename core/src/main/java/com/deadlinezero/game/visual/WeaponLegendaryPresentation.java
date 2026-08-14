package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Player;

/** Pure presentation routing for the one weapon-family legendary owned by the current run. */
public final class WeaponLegendaryPresentation {
    public enum Style {
        NONE("", .65f, .82f, 1f),
        VANGUARD("VANGUARD PROTOCOL", .30f, .92f, 1f),
        SCATTER("SCATTER MAELSTROM", 1f, .72f, .18f),
        RAIL("PHASE LANCE", .92f, .92f, 1f),
        INFERNO("PYROCLASM", 1f, .24f, .05f),
        CRYO("CRYO PRISM", .45f, .92f, 1f),
        ARC("ARC OVERLOAD", .62f, .36f, 1f),
        BREACHER("RUPTURE MATRIX", 1f, .42f, .16f),
        ION("ION CASCADE", .20f, .88f, 1f),
        CINDER("CINDER FURNACE", 1f, .50f, .08f);

        public final String label;
        public final float r, g, b;
        Style(String label, float r, float g, float b) {
            this.label = label;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private WeaponLegendaryPresentation() { }

    public static Style style(Player p) {
        if (p == null || p.legendary == null) return Style.NONE;
        if (p.legendary.hasVanguardProtocol()) return Style.VANGUARD;
        if (p.legendary.hasScatterMaelstrom()) return Style.SCATTER;
        if (p.legendary.hasRailPhaseLance()) return Style.RAIL;
        if (p.legendary.hasInfernoPyroclasm()) return Style.INFERNO;
        if (p.legendary.hasCryoPrism()) return Style.CRYO;
        if (p.legendary.hasArcOverload()) return Style.ARC;
        if (p.legendary.hasBreacherRupture()) return Style.BREACHER;
        if (p.legendary.hasIonCascade()) return Style.ION;
        if (p.legendary.hasCinderFurnace()) return Style.CINDER;
        return Style.NONE;
    }
}
