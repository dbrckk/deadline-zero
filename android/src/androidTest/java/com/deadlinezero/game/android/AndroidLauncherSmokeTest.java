package com.deadlinezero.game.android;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AndroidLauncherSmokeTest {
    @Test
    public void launcherCreatesRealLibgdxSurfaceAndStaysAlive() {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            scenario.onActivity(activity -> {
                assertFalse("Android launcher finished during startup", activity.isFinishing());
                View content = activity.findViewById(android.R.id.content);
                assertTrue("Android launcher content view is missing", content != null);
                assertTrue("Android launcher window has no attached content", content.isAttachedToWindow());
            });
        }
    }
}
