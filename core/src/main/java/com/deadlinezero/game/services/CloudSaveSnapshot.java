package com.deadlinezero.game.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Immutable cloud-save payload with deterministic integrity metadata. */
public final class CloudSaveSnapshot {
    public final int schemaVersion;
    public final long revision;
    public final long updatedAtEpochMillis;
    public final String deviceId;
    public final String payload;
    public final String sha256;

    private CloudSaveSnapshot(int schemaVersion, long revision, long updatedAtEpochMillis,
                              String deviceId, String payload, String sha256) {
        if (schemaVersion < 0) throw new IllegalArgumentException("schemaVersion");
        if (revision < 0L) throw new IllegalArgumentException("revision");
        if (updatedAtEpochMillis < 0L) throw new IllegalArgumentException("updatedAtEpochMillis");
        if (deviceId == null || deviceId.isBlank()) throw new IllegalArgumentException("deviceId");
        if (payload == null) throw new IllegalArgumentException("payload");
        if (!isSha256(sha256)) throw new IllegalArgumentException("sha256");

        this.schemaVersion = schemaVersion;
        this.revision = revision;
        this.updatedAtEpochMillis = updatedAtEpochMillis;
        this.deviceId = deviceId.trim();
        this.payload = payload;
        this.sha256 = sha256.toLowerCase();
    }

    public static CloudSaveSnapshot create(int schemaVersion, long revision, long updatedAtEpochMillis,
                                           String deviceId, String payload) {
        return new CloudSaveSnapshot(schemaVersion, revision, updatedAtEpochMillis,
            deviceId, payload, digest(payload));
    }

    public static CloudSaveSnapshot restore(int schemaVersion, long revision, long updatedAtEpochMillis,
                                            String deviceId, String payload, String sha256) {
        return new CloudSaveSnapshot(schemaVersion, revision, updatedAtEpochMillis,
            deviceId, payload, sha256);
    }

    public boolean integrityValid() {
        return sha256.equals(digest(payload));
    }

    private static String digest(String payload) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            StringBuilder out = new StringBuilder(64);
            for (byte value : bytes) out.append(String.format("%02x", value & 0xff));
            return out.toString();
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 unavailable", impossible);
        }
    }

    private static boolean isSha256(String value) {
        if (value == null || value.length() != 64) return false;
        for (int i = 0; i < value.length(); i++) {
            char c = Character.toLowerCase(value.charAt(i));
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'))) return false;
        }
        return true;
    }
}
