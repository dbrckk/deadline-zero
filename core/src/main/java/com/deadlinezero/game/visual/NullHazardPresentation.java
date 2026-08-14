package com.deadlinezero.game.visual;

import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.world.ArenaHazardRuntime;

/** Presentation contract for Null Sector hazards. */
public final class NullHazardPresentation {
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

    private static final Profile RIFT = new Profile(
        .52f, .22f, 1f, .16f, .03f, .42f, 9.5f, 9, AudioDirector.Cue.NULL_RIFT);
    private static final Profile STATIC = new Profile(
        .24f, .78f, 1f, .72f, .94f, 1f, 18f, 6, AudioDirector.Cue.NULL_STATIC);
    private static final Profile BEAM = new Profile(
        .80f, .46f, 1f, .92f, .72f, 1f, 14f, 4, AudioDirector.Cue.NULL_BEAM);

    private NullHazardPresentation() { }

    public static boolean isNull(ArenaHazardRuntime.Type type) {
        return type == ArenaHazardRuntime.Type.VOID_RIFT
            || type == ArenaHazardRuntime.Type.STATIC_BURST
            || type == ArenaHazardRuntime.Type.NULL_BEAM;
    }

    public static Profile forType(ArenaHazardRuntime.Type type) {
        return switch (type) {
            case VOID_RIFT -> RIFT;
            case STATIC_BURST -> STATIC;
            case NULL_BEAM -> BEAM;
            default -> throw new IllegalArgumentException("Not a Null Sector hazard: " + type);
        };
    }
}
