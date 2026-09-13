package com.deadlinezero.game.services;

import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.ProfileBackupCodec;
import com.deadlinezero.game.meta.ProfileBackupSummary;
import com.deadlinezero.game.meta.ProfileStore;
import java.util.HashMap;
import java.util.Map;

/** Explicit cloud backup operations. Conflict policy stays user/UX controlled instead of silently overwriting progress. */
public final class CloudSaveService {
    public enum DownloadResult { APPLIED, EMPTY_REMOTE, REJECTED_NEWER_SCHEMA }
    public enum ConflictState { EQUAL, LOCAL_AHEAD, REMOTE_AHEAD, DIVERGED }
    public record RestoreResult(DownloadResult result, PlayerProfile profile) {}

    private final CloudSaveAdapter adapter;

    public CloudSaveService(CloudSaveAdapter adapter) {
        this.adapter = adapter == null ? CloudSaveAdapter.unavailable() : adapter;
    }

    public void uploadLocal() throws Exception {
        upload(ProfileStore.exportBackup());
    }

    public void upload(String localBackup) throws Exception {
        ProfileBackupCodec.decode(localBackup);
        adapter.write(localBackup);
    }

    public CloudSaveAdapter.RemoteBackup inspectRemote() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = adapter.read();
        if (remote == null || remote.payload() == null || remote.payload().isBlank()) return null;
        ProfileBackupCodec.decode(remote.payload());
        return remote;
    }

    public ConflictState compareRemoteToLocal() throws Exception {
        return compareRemoteToLocal(ProfileStore.exportBackup());
    }

    public ConflictState compareRemoteToLocal(String localBackup) throws Exception {
        Map<String, Object> localValues = ProfileBackupCodec.decode(localBackup);
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return ConflictState.LOCAL_AHEAD;
        Map<String, Object> remoteValues = ProfileBackupCodec.decode(remote.payload());

        if (localValues.equals(remoteValues)) return ConflictState.EQUAL;

        Map<String, Object> localNonMonotone = withoutMonotone(localValues);
        Map<String, Object> remoteNonMonotone = withoutMonotone(remoteValues);
        if (!localNonMonotone.equals(remoteNonMonotone)) return ConflictState.DIVERGED;

        ProfileBackupSummary local = ProfileBackupSummary.from(localValues);
        ProfileBackupSummary cloud = ProfileBackupSummary.from(remoteValues);
        if (local.dominates(cloud)) return ConflictState.LOCAL_AHEAD;
        if (cloud.dominates(local)) return ConflictState.REMOTE_AHEAD;
        return ConflictState.DIVERGED;
    }

    public RestoreResult downloadRemote() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return new RestoreResult(DownloadResult.EMPTY_REMOTE, null);
        return applyRemote(remote.payload());
    }

    public RestoreResult applyRemote(String remoteBackup) {
        PlayerProfile restored = ProfileStore.importBackup(remoteBackup);
        if (restored == null) return new RestoreResult(DownloadResult.REJECTED_NEWER_SCHEMA, null);
        // The store only returns after a complete typed reload succeeds. Callers must replace their
        // active in-memory profile before any subsequent save.
        return new RestoreResult(DownloadResult.APPLIED, restored);
    }

    private static Map<String, Object> withoutMonotone(Map<String, Object> source) {
        Map<String, Object> copy = new HashMap<>(source);
        for (String key : ProfileBackupSummary.MONOTONE_KEYS) copy.remove(key);
        return copy;
    }
}
