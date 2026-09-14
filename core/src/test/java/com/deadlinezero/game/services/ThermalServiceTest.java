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
        assertEquals(1.00f, ThermalService.Level.NORMAL.fxCeiling, .0001f);
        assertEquals(.92f, ThermalService.Level.LIGHT.fxCeiling, .0001f);
        assertEquals(.76f, ThermalService.Level.MODERATE.fxCeiling, .0001f);
        assertEquals(.58f, ThermalService.Level.SEVERE.fxCeiling, .0001f);
        assertEquals(.46f, ThermalService.Level.CRITICAL.fxCeiling, .0001f);
    }

    @Test void noOpIsNonRestrictive() {
        assertEquals(ThermalService.Level.UNKNOWN, ThermalService.noOp().level());
    }
}
