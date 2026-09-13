package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

final class FrostColossusBossProfileTest {
    @Test void tuningRemainsHeavyAndBounded() {
        assertTrue(FrostColossusBossProfile.HP_MULTIPLIER >= 1.30f && FrostColossusBossProfile.HP_MULTIPLIER <= 1.55f);
        assertTrue(FrostColossusBossProfile.SPEED_MULTIPLIER >= .70f && FrostColossusBossProfile.SPEED_MULTIPLIER < 1f);
        assertTrue(FrostColossusBossProfile.DAMAGE_MULTIPLIER >= 1.10f && FrostColossusBossProfile.DAMAGE_MULTIPLIER <= 1.30f);
        assertTrue(FrostColossusBossProfile.PHASE3_CHARGE_COOLDOWN < FrostColossusBossProfile.PHASE2_CHARGE_COOLDOWN);
        assertTrue(FrostColossusBossProfile.PHASE3_SUMMON_COOLDOWN < FrostColossusBossProfile.PHASE2_SUMMON_COOLDOWN);
        assertTrue(FrostColossusBossProfile.PHASE3_SUMMON_COUNT > FrostColossusBossProfile.PHASE2_SUMMON_COUNT);
        assertTrue(FrostColossusBossProfile.ENRAGE_EXPLOSION_RADIUS >= 2.5f);
    }
}
