package com.deadlinezero.game.visual;

import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.world.ArenaHazardRuntime;

/** Presentation contract for Cinder Foundry hazards. Keeps color, animation and audio routing deterministic. */
public final class FoundryHazardPresentation {
    public static final class Profile {
        public final float warningR, warningG, warningB;
        public final float activeR, activeG, activeB;
        public final float pulseSpeed;
        public final int spokes;
        public final AudioDirector.Cue cue;

        private Profile(float warningR, float warningG, float warningB,
                        float activeR, float activeG, float activeB,
                        float pulseSpeed, int spokes, AudioDirector.Cue cue) {
            this.warningR = warningR;
            this.warningG = warningG;
            this.warningB = warningB;
            this.activeR = activeR;
            this.activeG = activeG;
            this.activeB = activeB;
            this.pulseSpeed = pulseSpeed;
            this.spokes = spokes;
            this.cue = cue;
        }
    }

    private static final Profile LAVA = new Profile(
        1f, .42f, .05f, 1f, .12f, .01f, 10.5f, 7, AudioDirector.Cue.FOUNDRY_LAVA);
    private static final Profile STEAM = new Profile(
        .78f, .90f, 1f, .96f, .98f, 1f, 16f, 6, AudioDirector.Cue.FOUNDRY_STEAM);
    private static final Profile HEAT = new Profile(
        1f, .72f, .16f, 1f, .28f, .03f, 13f, 4, AudioDirector.Cue.FOUNDRY_HEAT);

    private FoundryHazardPresentation() { }

    public static boolean isFoundry(ArenaHazardRuntime.Type type) {
        return type == ArenaHazardRuntime.Type.LAVA_VENT
            || type == ArenaHazardRuntime.Type.STEAM_JET
            || type == ArenaHazardRuntime.Type.HEAT_LINE;
    }

    public static Profile forType(ArenaHazardRuntime.Type type) {
        return switch (type) {
            case LAVA_VENT -> LAVA;
            case STEAM_JET -> STEAM;
            case HEAT_LINE -> HEAT;
            default -> throw new IllegalArgumentException("Not a Foundry hazard: " + type);
        };
    }
}
