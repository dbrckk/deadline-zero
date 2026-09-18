package com.deadlinezero.game.perf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deadlinezero.game.services.ThermalService;
import org.junit.jupiter.api.Test;

final class ThermalBudgetPolicyTest {
    @Test void userTargetWinsWhenBelowThermalCeiling() {
        assertEquals(60, ThermalBudgetPolicy.allowedFps(60, ThermalService.Level.NORMAL));
        assertEquals(90, ThermalBudgetPolicy.allowedFps(90, ThermalService.Level.LIGHT));
    }

    @Test void thermalPressureCapsHighFrameRateTargets() {
        assertEquals(90, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.MODERATE));
        assertEquals(60, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.SEVERE));
        assertEquals(60, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.CRITICAL));
    }

    @Test void unsupportedUserTargetsNormalizeToSupportedTiers() {
        assertEquals(60, ThermalBudgetPolicy.allowedFps(75, ThermalService.Level.NORMAL));
        assertEquals(90, ThermalBudgetPolicy.allowedFps(100, ThermalService.Level.NORMAL));
        assertEquals(120, ThermalBudgetPolicy.allowedFps(144, ThermalService.Level.NORMAL));
    }

    @Test void fxCeilingTracksThermalLevel() {
        assertEquals(1.00f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.NORMAL), .0001f);
        assertEquals(.92f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.LIGHT), .0001f);
        assertEquals(.76f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.MODERATE), .0001f);
        assertEquals(.58f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.SEVERE), .0001f);
        assertEquals(.46f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.CRITICAL), .0001f);
    }

    @Test void nullThermalLevelFailsOpenToUnknownPolicy() {
        assertEquals(120, ThermalBudgetPolicy.allowedFps(120, null));
        assertEquals(1.00f, ThermalBudgetPolicy.fxCeiling(null), .0001f);
    }
}
