package com.deadlinezero.game.android;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import com.deadlinezero.game.services.ThermalService;

/** Android thermal-status bridge. Polling is cheap and avoids lifecycle-sensitive listener bookkeeping. */
public final class AndroidThermalService implements ThermalService {
    private final PowerManager powerManager;

    public AndroidThermalService(Activity activity) {
        powerManager = activity == null ? null
            : (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
    }

    @Override public Level level() {
        if (powerManager == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return Level.UNKNOWN;
        return switch (powerManager.getCurrentThermalStatus()) {
            case PowerManager.THERMAL_STATUS_NONE -> Level.NORMAL;
            case PowerManager.THERMAL_STATUS_LIGHT -> Level.LIGHT;
            case PowerManager.THERMAL_STATUS_MODERATE -> Level.MODERATE;
            case PowerManager.THERMAL_STATUS_SEVERE -> Level.SEVERE;
            case PowerManager.THERMAL_STATUS_CRITICAL, PowerManager.THERMAL_STATUS_EMERGENCY,
                 PowerManager.THERMAL_STATUS_SHUTDOWN -> Level.CRITICAL;
            default -> Level.UNKNOWN;
        };
    }
}
