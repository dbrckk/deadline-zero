package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.util.Pools;
import org.junit.jupiter.api.Test;

final class MobileRuntimeBudgetTest {
    @Test void hardCapsStayWithinMobileSafetyBudget() {
        assertTrue(GameConfig.MAX_ENEMIES <= 420);
        assertTrue(GameConfig.MAX_PROJECTILES <= 768);
        assertTrue(Pools.MAX_HOSTILE_PROJECTILES <= 384);
        assertTrue(Pools.MAX_HOMING_MISSILES <= 96);
        assertTrue(Pools.MAX_IMPACTS <= 192);
        assertTrue(Pools.MAX_DAMAGE_NUMBERS <= 192);
        assertTrue(Pools.MAX_ARCS <= 96);
        assertTrue(Pools.MAX_DEATH_FX <= 72);
    }

    @Test void budgetsKeepEnoughHeadroomForEndgameBuilds() {
        assertTrue(GameConfig.MAX_ENEMIES >= 300);
        assertTrue(GameConfig.MAX_PROJECTILES >= 600);
        assertTrue(Pools.MAX_HOSTILE_PROJECTILES >= 256);
        assertTrue(Pools.MAX_DAMAGE_NUMBERS >= 128);
    }
}
