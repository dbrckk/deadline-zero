package com.deadlinezero.game.services;

import com.deadlinezero.game.meta.ProfileBackupCodec;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.ProfileBackupSummary;

/** Explicit cloud backup operations. Conflict policy stays user/UX controlled instead of silently overwriting progress. */
public final class CloudSaveService {
    public enum DownloadResult { APPLIED, EMPTY_REMOTE, REJECTED_NEWER_SCHEMA }
    public enum ConflictState { EQUAL, LOCAL_AHEAD, REMOTE_AHEAD, DIVERGED }

    private final CloudSaveAdapter adapter;

    public CloudSaveService(CloudSaveAdapter adapter) {
        this.adapter = adapter == null ? CloudSaveAdapter.unavailable() : adapter;
    }

    public void uploadLocal() throws Exception {
        adapter.write(ProfileStore.exportBackup());
    }

    public CloudSaveAdapter.RemoteBackup inspectRemote() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = adapter.read();
        if (remote == null || remote.payload() == null || remote.payload().isBlank()) return null;
        ProfileBackupCodec.decode(remote.payload()); // checksum/type validation before UI displays it as usable.
        return remote;
    }

    public ConflictState compareRemoteToLocal() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return ConflictState.LOCAL_AHEAD;
        ProfileBackupSummary local = ProfileBackupSummary.from(ProfileBackupCodec.decode(ProfileStore.exportBackup()));
        ProfileBackupSummary cloud = ProfileBackupSummary.from(ProfileBackupCodec.decode(remote.payload()));
        if (local.equals(cloud)) return ConflictState.EQUAL;
        if (local.dominates(cloud)) return ConflictState.LOCAL_AHEAD;
        if (cloud.dominates(local)) return ConflictState.REMOTE_AHEAD;
        return ConflictState.DIVERGED;
    }

    public DownloadResult downloadAndReplaceLocal() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return DownloadResult.EMPTY_REMOTE;
        return ProfileStore.importBackup(remote.payload()) ? DownloadResult.APPLIED : DownloadResult.REJECTED_NEWER_SCHEMA;
    }
}
