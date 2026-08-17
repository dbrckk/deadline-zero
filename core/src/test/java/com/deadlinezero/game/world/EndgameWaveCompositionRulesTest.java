package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.EndgameMutatorRules;

final class EndgameWaveCompositionRulesTest {
    @Test void noOverrideBeforeThreatFive() {
        for (EndgameMutatorRules.Mutator mutator : EndgameMutatorRules.Mutator.values()) {
            assertEquals(Enemy.Type.BRUTE,
                EndgameWaveCompositionRules.override(4, mutator, WaveDirector.PressureBand.CRISIS, 0f, Enemy.Type.BRUTE));
        }
    }

    @Test void eachMutatorHasARecognizableCompositionIdentity() {
        assertEquals(Enemy.Type.RUNNER, EndgameWaveCompositionRules.override(10,
            EndgameMutatorRules.Mutator.FRENZY, WaveDirector.PressureBand.ASSAULT, 0f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.SHIELDED, EndgameWaveCompositionRules.override(10,
            EndgameMutatorRules.Mutator.BULWARK, WaveDirector.PressureBand.ASSAULT, 0f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.BRUTE, EndgameWaveCompositionRules.override(10,
            EndgameMutatorRules.Mutator.VOLATILE, WaveDirector.PressureBand.ASSAULT, 0f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.RUNNER, EndgameWaveCompositionRules.override(10,
            EndgameMutatorRules.Mutator.SWARM, WaveDirector.PressureBand.ASSAULT, 0f, Enemy.Type.BRUTE));
    }

    @Test void overrideBudgetRemainsBoundedAndRisesTowardCrisis() {
        float opening5 = EndgameWaveCompositionRules.maximumOverrideShare(5, WaveDirector.PressureBand.OPENING);
        float crisis5 = EndgameWaveCompositionRules.maximumOverrideShare(5, WaveDirector.PressureBand.CRISIS);
        float crisis10 = EndgameWaveCompositionRules.maximumOverrideShare(10, WaveDirector.PressureBand.CRISIS);

        assertTrue(opening5 > 0f);
        assertTrue(crisis5 > opening5);
        assertTrue(crisis10 > crisis5);
        assertTrue(crisis10 <= .35f, "mutator composition must never replace more than 35% of normal picks");
    }

    @Test void rollsOutsideBudgetPreserveEncounterChoice() {
        Enemy.Type fallback = Enemy.Type.REGENERATOR;
        assertEquals(fallback, EndgameWaveCompositionRules.override(10,
            EndgameMutatorRules.Mutator.FRENZY, WaveDirector.PressureBand.OPENING, .95f, fallback));
    }

    @Test void everyActiveMutatorCanBreakARepeatedEnemyStreak() {
        for (EndgameMutatorRules.Mutator mutator : EndgameMutatorRules.Mutator.values()) {
            if (mutator == EndgameMutatorRules.Mutator.NONE) continue;
            Enemy.Type repeated = switch (mutator) {
                case FRENZY, SWARM -> Enemy.Type.RUNNER;
                case BULWARK -> Enemy.Type.SHIELDED;
                case VOLATILE -> Enemy.Type.BRUTE;
                default -> Enemy.Type.SHAMBLER;
            };
            Enemy.Type replacement = EndgameWaveCompositionRules.streakBreaker(mutator, repeated);
            assertNotEquals(repeated, replacement, mutator + " must have a same-theme streak breaker");
            assertNotEquals(Enemy.Type.BOSS, replacement);
        }
    }
}
