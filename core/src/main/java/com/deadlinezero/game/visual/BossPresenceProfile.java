package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.ai.BossIdentity;

/** Pure presentation data for boss escalation and critical-health treatment. */
public final class BossPresenceProfile {
    public record PhaseProfile(
        float intensity,
        float ringScale,
        int markerCount,
        int telegraphSegments,
        float lineWeight,
        float pulseRate
    ) {}

    public record LowHpProfile(
        float edgeAlpha,
        float flashAlpha,
        float motionPulse,
        float warningWidth
    ) {}

    private BossPresenceProfile() {}

    public static PhaseProfile forPhase(BossIdentity identity, int phase) {
        BossIdentity safe = identity == null ? BossIdentity.ALPHA : identity;
        int p = MathUtils.clamp(phase, 1, 3);
        float identityBoost = switch (safe) {
            case FROST_COLOSSUS -> .10f;
            case NULL_ARCHON -> .12f;
            case HARVESTER -> .08f;
            case WARDEN -> .06f;
            case REVENANT -> .07f;
            default -> 0f;
        };
        float intensity = MathUtils.clamp(.58f + (p - 1) * .16f + identityBoost, .55f, 1f);
        float ringScale = Math.min(1.45f, 1.18f + (p - 1) * .10f + identityBoost * .25f);
        int markers = 3 + p;
        int segments = 3 + p + (safe == BossIdentity.FROST_COLOSSUS ? 2 : 0);
        float lineWeight = .032f + p * .006f;
        float pulseRate = 4.4f + p * 1.3f;
        return new PhaseProfile(intensity, ringScale, markers, segments, lineWeight, pulseRate);
    }

    /** motionAllowed should be false for reduced-motion presentation. */
    public static LowHpProfile lowHp(float hpRatio, boolean motionAllowed) {
        float hp = MathUtils.clamp(hpRatio, 0f, 1f);
        if (hp > .30f) return new LowHpProfile(0f, 0f, 0f, 0f);
        float urgency = 1f - hp / .30f;
        float edge = .055f + urgency * .085f;
        float flash = Math.min(.07f, .018f + urgency * .047f);
        float motion = motionAllowed ? .035f + urgency * .045f : 0f;
        float width = 18f + urgency * 16f;
        return new LowHpProfile(edge, flash, motion, width);
    }
}
