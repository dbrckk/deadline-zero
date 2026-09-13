package com.deadlinezero.game.meta;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Deterministic, checksummed serialization for complete libGDX Preferences profile backups. */
public final class ProfileBackupCodec {
    private static final String MAGIC = "DZPROFILE1";
    private static final int MAX_BACKUP_CHARS = 512_000;

    private ProfileBackupCodec() {}

    public static String encode(Map<String, ?> values) {
        if (values == null) throw new IllegalArgumentException("values");
        List<Map.Entry<String, ?>> entries = new ArrayList<>(values.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getKey));

        StringBuilder payload = new StringBuilder();
        for (Map.Entry<String, ?> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key == null || value == null) continue;
            char type = typeOf(value);
            payload.append(type).append('\t')
                .append(b64(key)).append('\t')
                .append(b64(String.valueOf(value))).append('\n');
        }

        String body = payload.toString();
        String backup = MAGIC + "\n" + sha256(body) + "\n" + body;
        if (backup.length() > MAX_BACKUP_CHARS) throw new IllegalArgumentException("profile backup exceeds size limit");
        return backup;
    }

    public static Map<String, Object> decode(String backup) {
        if (backup == null || backup.isBlank()) throw new IllegalArgumentException("backup");
        if (backup.length() > MAX_BACKUP_CHARS) throw new IllegalArgumentException("profile backup exceeds size limit");

        int first = backup.indexOf('\n');
        int second = first < 0 ? -1 : backup.indexOf('\n', first + 1);
        if (first < 0 || second < 0 || !MAGIC.equals(backup.substring(0, first))) {
            throw new IllegalArgumentException("invalid profile backup header");
        }

        String expectedHash = backup.substring(first + 1, second);
        String body = backup.substring(second + 1);
        if (!sha256(body).equals(expectedHash)) throw new IllegalArgumentException("profile backup checksum mismatch");

        Map<String, Object> values = new LinkedHashMap<>();
        if (body.isEmpty()) return values;
        for (String line : body.split("\n")) {
            if (line.isEmpty()) continue;
            String[] parts = line.split("\t", -1);
            if (parts.length != 3 || parts[0].length() != 1) throw new IllegalArgumentException("invalid profile backup entry");
            String key = fromB64(parts[1]);
            if (key.isBlank() || values.containsKey(key)) throw new IllegalArgumentException("invalid or duplicate profile key");
            String raw = fromB64(parts[2]);
            values.put(key, parse(parts[0].charAt(0), raw));
        }
        return values;
    }

    public static int schemaVersion(Map<String, ?> values) {
        if (values == null) return ProfileSchema.LEGACY_UNVERSIONED;
        Object raw = values.get(ProfileSchema.VERSION_KEY);
        return raw instanceof Number n ? ProfileSchema.sanitizedVersion(n.intValue()) : ProfileSchema.LEGACY_UNVERSIONED;
    }

    public static boolean supportedByCurrentSchema(Map<String, ?> values) {
        return schemaVersion(values) <= ProfileSchema.CURRENT_VERSION;
    }

    private static char typeOf(Object value) {
        if (value instanceof Boolean) return 'b';
        if (value instanceof Integer) return 'i';
        if (value instanceof Long) return 'l';
        if (value instanceof Float) return 'f';
        if (value instanceof String) return 's';
        throw new IllegalArgumentException("unsupported profile value type: " + value.getClass().getName());
    }

    private static Object parse(char type, String raw) {
        try {
            return switch (type) {
                case 'b' -> {
                    if (!"true".equals(raw) && !"false".equals(raw)) throw new IllegalArgumentException("invalid boolean");
                    yield Boolean.parseBoolean(raw);
                }
                case 'i' -> Integer.parseInt(raw);
                case 'l' -> Long.parseLong(raw);
                case 'f' -> {
                    float value = Float.parseFloat(raw);
                    if (!Float.isFinite(value)) throw new IllegalArgumentException("invalid float");
                    yield value;
                }
                case 's' -> raw;
                default -> throw new IllegalArgumentException("unsupported profile value type");
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid profile value", e);
        }
    }

    private static String b64(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String fromB64(String value) {
        try {
            return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid base64 profile value", e);
        }
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder out = new StringBuilder(digest.length * 2);
            for (byte b : digest) out.append(String.format("%02x", b));
            return out.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}
