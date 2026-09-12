package com.deadlinezero.game.services;

public final class CloudSaveConflictResolver {
    public enum Decision { USE_LOCAL, USE_REMOTE, IDENTICAL }
    private CloudSaveConflictResolver() {}

    public static Decision resolve(CloudSaveSnapshot local, CloudSaveSnapshot remote) {
        if (local == null) return remote == null ? Decision.IDENTICAL : requireValid(remote, Decision.USE_REMOTE);
        if (remote == null) return requireValid(local, Decision.USE_LOCAL);
        requireValid(local, Decision.USE_LOCAL);
        requireValid(remote, Decision.USE_REMOTE);
        if (local.schemaVersion == remote.schemaVersion && local.payload.equals(remote.payload)) {
            return Decision.IDENTICAL;
        }
        if (local.revision != remote.revision) return local.revision > remote.revision ? Decision.USE_LOCAL : Decision.USE_REMOTE;
        if (local.updatedAtEpochMillis != remote.updatedAtEpochMillis) {
            return local.updatedAtEpochMillis > remote.updatedAtEpochMillis ? Decision.USE_LOCAL : Decision.USE_REMOTE;
        }
        int schema = Integer.compare(local.schemaVersion, remote.schemaVersion);
        if (schema != 0) return schema > 0 ? Decision.USE_LOCAL : Decision.USE_REMOTE;
        return local.sha256.compareTo(remote.sha256) >= 0 ? Decision.USE_LOCAL : Decision.USE_REMOTE;
    }

    private static Decision requireValid(CloudSaveSnapshot snapshot, Decision decision) {
        if (!snapshot.integrityValid()) throw new IllegalArgumentException("Cloud save integrity check failed");
        return decision;
    }
}
