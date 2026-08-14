package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
