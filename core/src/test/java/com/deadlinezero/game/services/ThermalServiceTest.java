package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class ThermalServiceTest {
    @Test void thermalLevelsApplyConservativeFpsCeilings() {
        assertEquals(120, ThermalService.Level.UNKNOWN.fpsCeiling);
        assertEquals(120, ThermalService.Level.NORMAL.fpsCeiling);
        assertEquals(120, ThermalService.Level.LIGHT.fpsCeiling);
        assertEquals(90, ThermalService.Level.MODERATE.fpsCeiling);
        assertEquals(60, ThermalService.Level.SEVERE.fpsCeiling);
        assertEquals(60, ThermalService.Level.CRITICAL.fpsCeiling);
    }

    @Test void noOpIsNonRestrictive() {
        assertEquals(ThermalService.Level.UNKNOWN, ThermalService.noOp().level());
    }
}
