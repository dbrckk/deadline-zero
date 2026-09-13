package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

    @Test void duplicateKeysAreRejected() throws Exception {
        String valid = ProfileBackupCodec.encode(Map.of("credits", 123L));
        int first = valid.indexOf('\n');
        int second = valid.indexOf('\n', first + 1);
        String body = valid.substring(second + 1);
        String duplicateBody = body + body;
        String duplicate = valid.substring(0, first + 1) + sha256(duplicateBody) + "\n" + duplicateBody;

        assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.decode(duplicate));
    }

    @Test void unsupportedValueTypesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.encode(Map.of("bad", 1.0d)));
    }
    private static String sha256(String value) throws Exception {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder out = new StringBuilder(digest.length * 2);
        for (byte b : digest) out.append(String.format("%02x", b));
        return out.toString();
    }
}
