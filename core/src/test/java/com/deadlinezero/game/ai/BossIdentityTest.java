package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

final class BossIdentityTest {
    @Test void preservesEarlyAlphaAndExistingRevenantStages() {
        assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(1));
        assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(4));
        assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(5));
        assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(6));
    }

    @Test void introducesWardenOnLateOddCycleWithoutCollidingWithRevenant() {
        assertEquals(BossIdentity.WARDEN, BossIdentity.forStage(7));
        assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(8));
        assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(9));
        assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(10));
        assertEquals(BossIdentity.WARDEN, BossIdentity.forStage(11));
    }

    @Test void introducesFrostColossusAtCryogenicMilestones() {
        assertEquals(6, BossIdentity.values().length);
        assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(35));
        assertEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(40));
        assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(45));
        assertEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(50));
        assertNotEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(39));
    }
}
