package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

final class MasteryEconomyGuardrailTest {
    @Test void fullMasteryEconomyRemainsFiniteAndNonPayToWin() {
        int totalCredits = 0;
        int totalGems = 0;
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            MasteryProgress mastery = new MasteryProgress();
            for (int win = 0; win < 30; win++) {
                MasteryProgress.Gain gain = mastery.recordVictory(weapon.id, 1);
                totalCredits += gain.creditsReward() - (gain.biomeRankAfter() > gain.biomeRankBefore() ? 260 : 0);
                totalGems += gain.gemsReward() - (gain.biomeRankAfter() > gain.biomeRankBefore() ? 3 : 0);
            }
        }
        assertEquals(WeaponCatalog.all().length * MasteryProgress.MAX_RANK * 180, totalCredits);
        assertEquals(WeaponCatalog.all().length * MasteryProgress.MAX_RANK * 2, totalGems);
        assertTrue(totalGems <= 100, "weapon mastery gem budget drifted too high");
    }

    @Test void biomeMasteryBudgetIsSmallAndOneTime() {
        int credits = EnvironmentBiomeRules.Biome.values().length * MasteryProgress.MAX_RANK * 260;
        int gems = EnvironmentBiomeRules.Biome.values().length * MasteryProgress.MAX_RANK * 3;
        assertEquals(3900, credits);
        assertEquals(45, gems);
    }

    @Test void persistenceIdentifiersStaySafeAndUnique() {
        java.util.HashSet<String> ids = new java.util.HashSet<>();
        for (WeaponDefinition weapon : WeaponCatalog.all()) {
            assertTrue(weapon.id.matches("[a-z0-9_]+"), weapon.id);
            assertTrue(ids.add(weapon.id), "duplicate weapon mastery id: " + weapon.id);
        }
        assertEquals(9, ids.size());
        assertEquals(3, EnvironmentBiomeRules.Biome.values().length);
    }
}
