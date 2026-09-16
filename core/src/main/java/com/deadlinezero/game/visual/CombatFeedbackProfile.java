package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.MathUtils;

/** Immutable visual budgets for high-frequency combat feedback. */
public final class CombatFeedbackProfile {
    public enum Event {
        FIRE, HIT, CRIT, KILL, DASH, PICKUP, BOSS_WINDUP, BOSS_RELEASE, LOW_HP
    }

    public record Profile(
        int particleBudget,
        int geometryBudget,
        float glowAlpha,
        float flashAlpha,
        float shakeScale,
        float afterimageStrength,
        float decalLifetime
    ) {}

    private CombatFeedbackProfile() {}

    public static Profile forEvent(Event event, GraphicsQuality quality,
                                   boolean reducedMotion, boolean minimizedFlash) {
        Event safeEvent = event == null ? Event.HIT : event;
        GraphicsQuality safeQuality = quality == null ? GraphicsQuality.MEDIUM : quality;
        float q = switch (safeQuality) {
            case LOW -> .48f;
            case MEDIUM -> .68f;
            case HIGH -> .88f;
            case ULTRA -> 1f;
        };

        Base base = base(safeEvent);
        int particles = Math.max(base.minParticles, Math.round(base.particles * q));
        int geometry = Math.max(base.minGeometry, Math.round(base.geometry * MathUtils.lerp(.55f, 1f, q)));
        float glow = base.glow * MathUtils.lerp(.58f, 1f, q);
        float flash = minimizedFlash ? Math.min(.08f, base.flash * .38f) : base.flash;
        float shake = MathUtils.clamp(base.shake, 0f, 1f);
        float afterimage = reducedMotion ? 0f : base.afterimage * q;
        float decal = MathUtils.clamp(base.decalLifetime, 0f, 8f);

        return new Profile(particles, geometry, glow, flash, shake, afterimage, decal);
    }

    private static Base base(Event event) {
        return switch (event) {
            case FIRE -> new Base(5, 1, 12, 6, .30f, .08f, .16f, .16f, 0f);
            case HIT -> new Base(7, 2, 14, 7, .34f, .10f, .18f, .08f, 0f);
            case CRIT -> new Base(12, 3, 20, 9, .52f, .16f, .32f, .12f, .7f);
            case KILL -> new Base(14, 4, 22, 10, .44f, .12f, .22f, .10f, 6.5f);
            case DASH -> new Base(8, 2, 14, 7, .26f, .05f, .20f, .72f, 0f);
            case PICKUP -> new Base(6, 2, 12, 6, .30f, .07f, .08f, .18f, 0f);
            case BOSS_WINDUP -> new Base(14, 4, 26, 14, .54f, .11f, .38f, .08f, 0f);
            case BOSS_RELEASE -> new Base(22, 6, 34, 18, .72f, .18f, .82f, .16f, 1.4f);
            case LOW_HP -> new Base(3, 1, 8, 4, .18f, .09f, .12f, .04f, 0f);
        };
    }

    private record Base(
        int particles,
        int minParticles,
        int geometry,
        int minGeometry,
        float glow,
        float flash,
        float shake,
        float afterimage,
        float decalLifetime
    ) {}
}
