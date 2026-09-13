package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class ProfileStoreBackupTest {
    private static final String PREFS = "deadline-zero-profile-v1";
    private static HeadlessApplication app;

    @BeforeAll static void startGdx() {
        app = new HeadlessApplication(new ApplicationAdapter() {}, new HeadlessApplicationConfiguration());
    }

    @AfterAll static void stopGdx() {
        if (app != null) app.exit();
    }

    @Test void typePoisonedBackupRollsBackToOriginalReadableProfile() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.clear();
        prefs.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);
        prefs.putInteger("accountLevel", 7);
        prefs.putLong("credits", 321L);
        prefs.flush();

        Map<String, Object> poisoned = new HashMap<>(prefs.get());
        poisoned.put("accountLevel", "not-an-integer");
        String backup = ProfileBackupCodec.encode(poisoned);

        assertThrows(RuntimeException.class, () -> ProfileStore.importBackup(backup));

        assertEquals(7, prefs.getInteger("accountLevel", -1));
        assertEquals(321L, prefs.getLong("credits", -1L));
        assertEquals(7, ProfileStore.load().accountLevel);
    }
}
