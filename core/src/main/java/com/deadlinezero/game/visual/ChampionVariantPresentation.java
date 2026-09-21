package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;

/** Stable non-color semantic cue for champion variants. */
public final class ChampionVariantPresentation {
    public enum Marker {
        NONE,
        CHEVRON,
        ARMOR,
        CLAW,
        VOLATILE_CORE,
        JUGGERNAUT,
        RAVAGER,
        AEGIS,
        HUNTER
    }

    private ChampionVariantPresentation() { }

    public static Marker marker(Enemy.Variant variant) {
        if (variant == null) return Marker.NONE;
        return switch (variant) {
            case SWIFT -> Marker.CHEVRON;
            case ARMORED -> Marker.ARMOR;
            case FERAL -> Marker.CLAW;
            case VOLATILE -> Marker.VOLATILE_CORE;
            case JUGGERNAUT -> Marker.JUGGERNAUT;
            case RAVAGER -> Marker.RAVAGER;
            case AEGIS -> Marker.AEGIS;
            case HUNTER -> Marker.HUNTER;
            default -> Marker.NONE;
        };
    }
}
