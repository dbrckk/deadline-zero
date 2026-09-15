package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;

/** Stable non-color semantic cue for champion variants. */
public final class ChampionVariantPresentation {
    private ChampionVariantPresentation() { }

    public static String badge(Enemy.Variant variant) {
        if (variant == null) return "";
        return switch (variant) {
            case SWIFT -> "SW";
            case ARMORED -> "AR";
            case FERAL -> "FE";
            case VOLATILE -> "VO";
            case JUGGERNAUT -> "JU";
            case RAVAGER -> "RA";
            case AEGIS -> "AE";
            case HUNTER -> "HU";
            default -> "";
        };
    }
}
