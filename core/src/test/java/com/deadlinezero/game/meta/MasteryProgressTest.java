package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

final class MasteryProgressTest {
    @Test void weaponRanksFollowPermanentVictoryThresholds() {
        MasteryProgress mastery = new MasteryProgress();
        assertEquals(0, mastery.weaponRank(WeaponCatalog.AR9.id));
        MasteryProgress.Gain first = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        assertEquals(1, first.weaponRankAfter());
        assertTrue(first.rankedUp());
        assertEquals(180, first.creditsReward() - 260);
        assertEquals(2, first.gemsReward() - 3);

        mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        MasteryProgress.Gain third = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
        assertEquals(2, third.weaponRankAfter());
        assertEquals(0, mastery.winsForNextWeaponRank(WeaponCatalog.AR9.id) - 4);
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
