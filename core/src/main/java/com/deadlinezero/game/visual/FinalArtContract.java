package com.deadlinezero.game.visual;

/**
 * Release-quality animation requirements. Bootstrap/generated art is intentionally excluded:
 * these values describe the minimum frame counts expected from the final production atlas.
 */
public final class FinalArtContract {
    private FinalArtContract() {}

    public static int minimumFrames(GameArt.Motion motion, boolean boss) {
        if (motion == null) return 1;
        return switch (motion) {
            case IDLE -> boss ? 6 : 4;
            case RUN -> 8;
            case ATTACK -> boss ? 8 : 6;
            case HIT -> boss ? 4 : 3;
            case DEATH -> boss ? 10 : 8;
        };
    }

    public static int preferredFastRunFrames() { return 10; }
    public static int directions() { return Direction8.values().length; }

    public static int minimumDirectionalActorFrames(boolean boss) {
        int perDirection = 0;
        for (GameArt.Motion motion : GameArt.Motion.values()) {
            perDirection += minimumFrames(motion, boss);
        }
        return directions() * perDirection;
    }
}
