package com.deadlinezero.game.visual;

/** Visual safety policy for the combat floor. Legacy hazard-wallpaper fallback is forbidden. */
final class CombatFloorFallbackPolicy {
    enum Mode { PREMIUM_TEXTURED, CLEAN_EMPTY }

    private CombatFloorFallbackPolicy() { }

    static Mode modeFor(boolean premiumFloorDrawn) {
        return premiumFloorDrawn ? Mode.PREMIUM_TEXTURED : Mode.CLEAN_EMPTY;
    }
}
