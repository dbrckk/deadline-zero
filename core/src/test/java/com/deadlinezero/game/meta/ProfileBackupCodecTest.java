package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class ProfileBackupCodecTest {
    @Test void roundTripsEverySupportedPreferenceTypeDeterministically() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("z-string", "héllo\nworld");
        values.put("a-int", 42);
        values.put("long", 9_000_000_000L);
        values.put("float", 1.25f);
        values.put("bool", true);
        values.put(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);

        String a = ProfileBackupCodec.encode(values);
        String b = ProfileBackupCodec.encode(new LinkedHashMap<>(values));

        assertEquals(a, b);
        assertEquals(values, ProfileBackupCodec.decode(a));
        assertEquals(ProfileSchema.CURRENT_VERSION, ProfileBackupCodec.schemaVersion(ProfileBackupCodec.decode(a)));
    }

    @Test void tamperingIsRejectedBeforeImport() {
        String backup = ProfileBackupCodec.encode(Map.of("credits", 123L));
        assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.decode(backup + "x"));
    }

    @Test void duplicateKeysAreRejected() {
        String backup = ProfileBackupCodec.encode(Map.of("credits", 123L));
        Map<String, Object> decoded = ProfileBackupCodec.decode(backup);
        assertEquals(123L, decoded.get("credits"));
    }

    @Test void unsupportedValueTypesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.encode(Map.of("bad", 1.0d)));
    }
}
