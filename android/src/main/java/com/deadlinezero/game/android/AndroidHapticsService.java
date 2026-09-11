package com.deadlinezero.game.android;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import com.deadlinezero.game.services.HapticsService;

/** Android haptic implementation with short event-specific pulses. */
public final class AndroidHapticsService implements HapticsService {
    private final Activity activity;

    public AndroidHapticsService(Activity activity) {
        this.activity = activity;
    }

    @Override public void dash() {
        pulse(18L, 72);
    }

    @Override public void damage() {
        pulse(32L, 150);
    }

    @Override public void bossKill() {
        pulse(55L, 210);
    }

    private void pulse(long durationMs, int amplitude) {
        if (activity == null || durationMs <= 0L) return;
        activity.runOnUiThread(() -> {
            Vibrator vibrator = vibrator();
            if (vibrator == null || !vibrator.hasVibrator()) return;
            vibrator.vibrate(VibrationEffect.createOneShot(durationMs, amplitude));
        });
    }

    private Vibrator vibrator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager manager = (VibratorManager) activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            return manager == null ? null : manager.getDefaultVibrator();
        }
        return (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }
}
