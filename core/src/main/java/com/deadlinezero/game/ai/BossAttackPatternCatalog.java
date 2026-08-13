package com.deadlinezero.game.ai;

/** Data-only projectile pattern catalog for ALPHA and REVENANT boss identities. */
public final class BossAttackPatternCatalog {
    private BossAttackPatternCatalog() { }

    public record Pattern(int shots, float spreadDegrees, float speedMultiplier,
                          float damageMultiplier, int explosiveEvery,
                          float explosionRadius, boolean radial) { }

    public static Pattern forPhase(boolean revenant, int phase) {
        int safePhase = Math.max(1, Math.min(3, phase));
        if (revenant) {
            return switch (safePhase) {
                case 1 -> new Pattern(7, 8f, 1.16f, .70f, 0, 0f, false);
                case 2 -> new Pattern(12, 30f, 1.10f, .64f, 4, 1.7f, true);
                default -> new Pattern(18, 20f, 1.14f, .60f, 3, 2.0f, true);
            };
        }
        return switch (safePhase) {
            case 1 -> new Pattern(5, 11f, 1f, .72f, 0, 0f, false);
            case 2 -> new Pattern(10, 36f, .89f, .62f, 0, 0f, true);
            default -> new Pattern(14, 360f / 14f, .94f, .58f, 3, 2.2f, true);
        };
    }
}
