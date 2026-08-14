package com.deadlinezero.game.visual;

import com.deadlinezero.game.ai.BossIdentity;

/** Data-only presentation contract for boss phase transitions. */
public final class BossPhaseTransitionProfile {
    private BossPhaseTransitionProfile() { }

    public record Spec(float duration, float radiusMultiplier, float audioPitch, int vibrationMs) { }

    public static Spec forPhase(BossIdentity identity, int phase) {
        BossIdentity safeIdentity = identity == null ? BossIdentity.ALPHA : identity;
        int safePhase = Math.max(2, Math.min(3, phase));
        float duration = safePhase == 3 ? .86f : .66f;
        float radius = safePhase == 3 ? 3.25f : 2.72f;
        int vibration = safePhase == 3 ? 34 : 24;
        float pitch = switch (safeIdentity) {
            case REVENANT -> safePhase == 3 ? .94f : 1.02f;
            case WARDEN -> safePhase == 3 ? .66f : .74f;
            case HARVESTER -> safePhase == 3 ? 1.18f : 1.10f;
            default -> safePhase == 3 ? .78f : .86f;
        };
        return new Spec(duration, radius, pitch, vibration);
    }
}
