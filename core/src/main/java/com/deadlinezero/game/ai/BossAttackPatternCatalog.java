package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Data-only projectile pattern catalog for all boss identities. */
public final class BossAttackPatternCatalog {
    private BossAttackPatternCatalog() { }

    public record Pattern(int shots, float spreadDegrees, float speedMultiplier,
                          float damageMultiplier, int explosiveEvery,
                          float explosionRadius, boolean radial) { }

    /**
     * Compatibility overload retained for existing callers. REVENANT remains explicit while
     * non-REVENANT calls resolve the active stage so newer boss identities are not downgraded
     * to ALPHA by legacy boolean call sites.
     */
    public static Pattern forPhase(boolean revenant, int phase) {
        BossIdentity identity = revenant ? BossIdentity.REVENANT : BossIdentity.forStage(RunStageContext.stage());
        return forPhase(identity, phase);
    }

    public static Pattern forPhase(BossIdentity identity, int phase) {
        BossIdentity safeIdentity = identity == null ? BossIdentity.ALPHA : identity;
        int safePhase = Math.max(1, Math.min(3, phase));
        if (safeIdentity == BossIdentity.REVENANT) {
            return switch (safePhase) {
                case 1 -> new Pattern(7, 8f, 1.16f, .70f, 0, 0f, false);
                case 2 -> new Pattern(12, 30f, 1.10f, .64f, 4, 1.7f, true);
                default -> new Pattern(18, 20f, 1.14f, .60f, 3, 2.0f, true);
            };
        }
        if (safeIdentity == BossIdentity.WARDEN) {
            return switch (safePhase) {
                case 1 -> new Pattern(3, 17f, .82f, .96f, 0, 0f, false);
                case 2 -> new Pattern(8, 45f, .78f, .82f, 2, 2.45f, true);
                default -> new Pattern(10, 36f, .76f, .78f, 2, 2.85f, true);
            };
        }
        return switch (safePhase) {
            case 1 -> new Pattern(5, 11f, 1f, .72f, 0, 0f, false);
            case 2 -> new Pattern(10, 36f, .89f, .62f, 0, 0f, true);
            default -> new Pattern(14, 360f / 14f, .94f, .58f, 3, 2.2f, true);
        };
    }
}
