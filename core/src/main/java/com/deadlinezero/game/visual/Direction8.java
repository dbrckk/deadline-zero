package com.deadlinezero.game.visual;

/** Stable eight-way facing used by authored top-down character animation keys. */
public enum Direction8 {
    N("n"), NE("ne"), E("e"), SE("se"), S("s"), SW("sw"), W("w"), NW("nw");

    private static final float MIN_DIRECTION_LEN2 = .0004f;
    private final String atlasToken;

    Direction8(String atlasToken) { this.atlasToken = atlasToken; }

    public String atlasToken() { return atlasToken; }

    /** Keeps the previous facing while nearly stationary to avoid idle-direction flicker. */
    public static Direction8 fromVector(float x, float y, Direction8 fallback) {
        Direction8 safeFallback = fallback == null ? E : fallback;
        if (!Float.isFinite(x) || !Float.isFinite(y) || x * x + y * y < MIN_DIRECTION_LEN2) return safeFallback;

        double degrees = Math.toDegrees(Math.atan2(y, x));
        int octant = Math.floorMod((int)Math.floor((degrees + 22.5d) / 45d), 8);
        return switch (octant) {
            case 0 -> E;
            case 1 -> NE;
            case 2 -> N;
            case 3 -> NW;
            case 4 -> W;
            case 5 -> SW;
            case 6 -> S;
            default -> SE;
        };
    }
}
