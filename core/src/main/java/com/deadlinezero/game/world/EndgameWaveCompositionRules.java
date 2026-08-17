package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.EndgameMutatorRules;

/** Pure bounded composition overlay for high-Threat runs. */
public final class EndgameWaveCompositionRules {
    private EndgameWaveCompositionRules() { }

    public static Enemy.Type override(int threatTier, EndgameMutatorRules.Mutator mutator,
                                      WaveDirector.PressureBand band, float rawRoll, Enemy.Type fallback) {
        if (threatTier < 5 || mutator == null || mutator == EndgameMutatorRules.Mutator.NONE || fallback == null) {
            return fallback;
        }
        float r = MathUtils.clamp(rawRoll, 0f, 1f);
        float intensity = threatTier >= 10 ? 1f : .72f;
        float gate = .24f + band.ordinal() * .035f;
        if (r > gate * intensity) return fallback;

        float pick = gate <= 0f ? 0f : r / Math.max(.0001f, gate * intensity);
        return switch (mutator) {
            case FRENZY -> pick < .58f ? Enemy.Type.RUNNER : Enemy.Type.PHANTOM;
            case BULWARK -> pick < .50f ? Enemy.Type.SHIELDED : Enemy.Type.REGENERATOR;
            case VOLATILE -> pick < .45f ? Enemy.Type.BRUTE : (pick < .78f ? Enemy.Type.ELITE : Enemy.Type.RANGED);
            case SWARM -> pick < .64f ? Enemy.Type.RUNNER : Enemy.Type.SHAMBLER;
            default -> fallback;
        };
    }

    public static float maximumOverrideShare(int threatTier, WaveDirector.PressureBand band) {
        if (threatTier < 5) return 0f;
        float intensity = threatTier >= 10 ? 1f : .72f;
        return (.24f + band.ordinal() * .035f) * intensity;
    }

    /** Alternate within the same mutator identity when one enemy type has repeated too long. */
    public static Enemy.Type streakBreaker(EndgameMutatorRules.Mutator mutator, Enemy.Type repeated) {
        if (mutator == null || mutator == EndgameMutatorRules.Mutator.NONE || repeated == null) return repeated;
        return switch (mutator) {
            case FRENZY -> repeated == Enemy.Type.RUNNER ? Enemy.Type.PHANTOM : Enemy.Type.RUNNER;
            case BULWARK -> repeated == Enemy.Type.SHIELDED ? Enemy.Type.REGENERATOR : Enemy.Type.SHIELDED;
            case VOLATILE -> repeated == Enemy.Type.BRUTE ? Enemy.Type.ELITE
                : (repeated == Enemy.Type.ELITE ? Enemy.Type.RANGED : Enemy.Type.BRUTE);
            case SWARM -> repeated == Enemy.Type.RUNNER ? Enemy.Type.SHAMBLER : Enemy.Type.RUNNER;
            default -> repeated;
        };
    }
}
