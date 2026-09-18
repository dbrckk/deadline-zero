package com.deadlinezero.game.perf;

import com.deadlinezero.game.services.ThermalService;

/** Single source of truth for thermal performance ceilings used by runtime FPS and FX budgets. */
public final class ThermalBudgetPolicy {
    private ThermalBudgetPolicy() {}

    public static int allowedFps(int userTarget, ThermalService.Level level) {
        ThermalService.Level safe = level == null ? ThermalService.Level.UNKNOWN : level;
        return Math.min(normalizeUserTarget(userTarget), safe.fpsCeiling);
    }

    public static float fxCeiling(ThermalService.Level level) {
        ThermalService.Level safe = level == null ? ThermalService.Level.UNKNOWN : level;
        return safe.fxCeiling;
    }

    private static int normalizeUserTarget(int target) {
        if (target >= 120) return 120;
        if (target >= 90) return 90;
        return 60;
    }
}
