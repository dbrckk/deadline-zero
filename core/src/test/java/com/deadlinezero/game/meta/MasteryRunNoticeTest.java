package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.visual.EnvironmentBiomeRules;

final class MasteryRunNoticeTest {
    @AfterEach void clear() { MasteryRunNotice.clear(); }

    @Test void capturesOnlyRanksThatActuallyAdvanced() {
        MasteryProgress.Gain gain = new MasteryProgress.Gain(1, 2, 2, 2, 180, 2);
        MasteryRunNotice.capture(gain, "VX Rail Rifle", EnvironmentBiomeRules.Biome.NULL_SECTOR);
        MasteryRunNotice.Notice notice = MasteryRunNotice.current();
        assertTrue(notice.visible());
        assertTrue(notice.weaponRankedUp());
        assertEquals(2, notice.weaponRank());
        assertEquals(0, notice.biomeRank());
        assertEquals(180, notice.creditsReward());
        assertEquals(2, notice.gemsReward());
    }

    @Test void nonRankVictoryClearsStaleNotice() {
        MasteryRunNotice.capture(new MasteryProgress.Gain(0, 1, 0, 1, 440, 5),
            "AR-9 Vanguard", EnvironmentBiomeRules.Biome.QUARANTINE_YARD);
        MasteryRunNotice.capture(new MasteryProgress.Gain(1, 1, 1, 1, 0, 0),
            "AR-9 Vanguard", EnvironmentBiomeRules.Biome.QUARANTINE_YARD);
        assertNull(MasteryRunNotice.current());
    }
}
