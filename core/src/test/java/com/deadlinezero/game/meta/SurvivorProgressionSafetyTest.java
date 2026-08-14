package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class SurvivorProgressionSafetyTest {
    @Test void exactThresholdLevelsNormally() {
        SurvivorProgression progression = new SurvivorProgression();
        progression.addXp(SurvivorCatalog.Survivor.REX, 180L);
        assertEquals(2, progression.level(SurvivorCatalog.Survivor.REX));
        assertEquals(0L, progression.xp(SurvivorCatalog.Survivor.REX));
    }

    @Test void extremeXpRemainsCanonicalWithoutOverflow() {
        SurvivorProgression progression = new SurvivorProgression();
        progression.addXp(SurvivorCatalog.Survivor.REX, Long.MAX_VALUE);

        int level = progression.level(SurvivorCatalog.Survivor.REX);
        long xp = progression.xp(SurvivorCatalog.Survivor.REX);
        assertTrue(level > 1_000_000);
        assertTrue(xp >= 0L);
        assertTrue(xp < progression.xpForNext(SurvivorCatalog.Survivor.REX));
    }

    @Test void corruptedPersistedXpIsNormalizedOnRestore() {
        SurvivorProgression progression = new SurvivorProgression();
        progression.setState(SurvivorCatalog.Survivor.NYX, 1, Long.MAX_VALUE, true);

        assertTrue(progression.level(SurvivorCatalog.Survivor.NYX) > 1);
        assertTrue(progression.xp(SurvivorCatalog.Survivor.NYX) >= 0L);
        assertTrue(progression.xp(SurvivorCatalog.Survivor.NYX) < progression.xpForNext(SurvivorCatalog.Survivor.NYX));
        assertTrue(progression.unlocked(SurvivorCatalog.Survivor.NYX));
    }

    @Test void addingToExistingProgressCannotWrapNegative() {
        SurvivorProgression progression = new SurvivorProgression();
        progression.setState(SurvivorCatalog.Survivor.REX, 50, 100L, true);
        progression.addXp(SurvivorCatalog.Survivor.REX, Long.MAX_VALUE);

        assertTrue(progression.xp(SurvivorCatalog.Survivor.REX) >= 0L);
        assertTrue(progression.xp(SurvivorCatalog.Survivor.REX) < progression.xpForNext(SurvivorCatalog.Survivor.REX));
    }
}
