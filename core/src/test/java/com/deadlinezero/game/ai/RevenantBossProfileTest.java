package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class RevenantBossProfileTest {
    @Test void alternatesAfterStageFour() {
        assertFalse(RevenantBossProfile.useForStage(3));
        assertTrue(RevenantBossProfile.useForStage(4));
        assertFalse(RevenantBossProfile.useForStage(5));
        assertTrue(RevenantBossProfile.useForStage(6));
    }

    @Test void tradesDurabilityForPressure() {
        assertTrue(RevenantBossProfile.HP_MULTIPLIER < 1f);
        assertTrue(RevenantBossProfile.SPEED_MULTIPLIER > 1f);
        assertTrue(RevenantBossProfile.DAMAGE_MULTIPLIER > 1f);
        assertTrue(RevenantBossProfile.PHASE3_CHARGE_COOLDOWN < RevenantBossProfile.PHASE2_CHARGE_COOLDOWN);
        assertTrue(RevenantBossProfile.PHASE3_SUMMON_COOLDOWN < RevenantBossProfile.PHASE2_SUMMON_COOLDOWN);
    }

    @Test void revenantRecoversChargeEarlier() {
        BossCombatRuntime standard = new BossCombatRuntime(false);
        BossCombatRuntime revenant = new BossCombatRuntime(true);
        standard.update(5f, 2);
        revenant.update(5f, 2);
        assertTrue(standard.consumeCharge(2));
        assertTrue(revenant.consumeCharge(2));
        standard.update(3.5f, 2);
        revenant.update(3.5f, 2);
        assertFalse(standard.consumeCharge(2));
        assertTrue(revenant.consumeCharge(2));
    }
}
