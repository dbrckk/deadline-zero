package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

final class MasteryProgressTest {
    @Test void weaponAndBiomeRanksFollowPermanentVictoryThresholds() {
        MasteryProgress mastery = new MasteryProgress();
        assertEquals(0, mastery.weaponRank(WeaponCatalog.AR9.id));
        MasteryProgress.Gain first = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        assertEquals(1, first.weaponRankAfter());
        assertEquals(1, first.biomeRankAfter());
        assertTrue(first.rankedUp());
        assertEquals(440, first.creditsReward());
        assertEquals(5, first.gemsReward());

        mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        MasteryProgress.Gain third = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        assertEquals(2, third.weaponRankAfter());
        assertEquals(2, third.biomeRankAfter());
        assertEquals(440, third.creditsReward());
        assertEquals(5, third.gemsReward());
        assertEquals(4, mastery.winsForNextWeaponRank(WeaponCatalog.AR9.id));
    }

    @Test void prestigeTitlesAreStableAndClamped() {
        assertEquals("UNTRAINED", MasteryProgress.rankTitle(-5));
        assertEquals("INITIATE", MasteryProgress.rankTitle(1));
        assertEquals("SPECIALIST", MasteryProgress.rankTitle(2));
        assertEquals("VETERAN", MasteryProgress.rankTitle(3));
        assertEquals("ELITE", MasteryProgress.rankTitle(4));
        assertEquals("ASCENDANT", MasteryProgress.rankTitle(5));
        assertEquals("ASCENDANT", MasteryProgress.rankTitle(99));
    }

    @Test void masterySeparatesWeaponsAndBiomes() {
        MasteryProgress mastery = new MasteryProgress();
        mastery.recordVictory(WeaponCatalog.CINDER_CANNON.id, 12);
        assertEquals(1, mastery.weaponWins(WeaponCatalog.CINDER_CANNON.id));
        assertEquals(0, mastery.weaponWins(WeaponCatalog.AR9.id));
        assertEquals(1, mastery.biomeWins(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY));
        assertEquals(0, mastery.biomeWins(EnvironmentBiomeRules.Biome.NULL_SECTOR));
    }

    @Test void noRepeatedRankRewardBetweenThresholds() {
        MasteryProgress mastery = new MasteryProgress();
        mastery.recordVictory(WeaponCatalog.AR9.id, 20);
        MasteryProgress.Gain second = mastery.recordVictory(WeaponCatalog.AR9.id, 20);
        assertFalse(second.rankedUp());
        assertEquals(0, second.creditsReward());
        assertEquals(0, second.gemsReward());
    }

    @Test void maxRankIsBoundedAndSanitized() {
        MasteryProgress mastery = new MasteryProgress();
        mastery.setWeaponWins(WeaponCatalog.AR9.id, Integer.MAX_VALUE);
        mastery.setBiomeWins(EnvironmentBiomeRules.Biome.NULL_SECTOR, -10);
        assertEquals(MasteryProgress.MAX_RANK, mastery.weaponRank(WeaponCatalog.AR9.id));
        assertEquals(0, mastery.winsForNextWeaponRank(WeaponCatalog.AR9.id));
        assertEquals(0, mastery.biomeWins(EnvironmentBiomeRules.Biome.NULL_SECTOR));
    }
}
