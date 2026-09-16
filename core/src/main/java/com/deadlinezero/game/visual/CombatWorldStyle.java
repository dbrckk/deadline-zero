package com.deadlinezero.game.visual;

/** Pure deterministic parameters for authored combat-world breakup. */
public final class CombatWorldStyle {
    public record Profile(
        long seed,
        EnvironmentBiomeRules.Biome biome,
        float hazardCoverage,
        float hazardMarkerSize,
        int largeFeatureCount,
        int propCount,
        int decalCount,
        int lightCount
    ) {}

    private CombatWorldStyle() {}

    public static Profile forStage(int stage, long seed) {
        int safeStage = Math.max(1, stage);
        long mixed = mix(seed ^ ((long) safeStage * 0x9E3779B97F4A7C15L));
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(safeStage);

        float hazardCoverage = .006f + unit(mixed) * .004f;
        float hazardMarkerSize = .45f + unit(mixed >>> 7) * .20f;
        int largeFeatures = 4 + bounded(mixed >>> 8, 9);
        int props = 6 + bounded(mixed >>> 17, 19);
        int decals = 8 + Math.floorMod((int) seed, 29);
        int lights = 2 + bounded(mixed >>> 33, 7);

        return new Profile(
            mixed,
            biome,
            Math.min(.010f, hazardCoverage),
            Math.min(.65f, hazardMarkerSize),
            largeFeatures,
            props,
            decals,
            lights
        );
    }

    static long mix(long value) {
        long z = value + 0x9E3779B97F4A7C15L;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    static float unit(long value) {
        return (float) ((value >>> 40) & 0xFFFFFFL) / (float) 0xFFFFFF;
    }

    static int bounded(long value, int bound) {
        return Math.floorMod((int) (value ^ (value >>> 32)), Math.max(1, bound));
    }
}
