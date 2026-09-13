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
        upload(ProfileStore.exportBackup());
    }

    public void upload(String localBackup) throws Exception {
        ProfileBackupCodec.decode(localBackup);
        adapter.write(localBackup);
    }

    public CloudSaveAdapter.RemoteBackup inspectRemote() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = adapter.read();
        if (remote == null || remote.payload() == null || remote.payload().isBlank()) return null;
        ProfileBackupCodec.decode(remote.payload()); // checksum/type validation before UI displays it as usable.
        return remote;
    }

    public ConflictState compareRemoteToLocal() throws Exception {
        return compareRemoteToLocal(ProfileStore.exportBackup());
    }

    public ConflictState compareRemoteToLocal(String localBackup) throws Exception {
        var localValues = ProfileBackupCodec.decode(localBackup);
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return ConflictState.LOCAL_AHEAD;
        var remoteValues = ProfileBackupCodec.decode(remote.payload());

        if (localValues.equals(remoteValues)) return ConflictState.EQUAL;

        ProfileBackupSummary local = ProfileBackupSummary.from(localValues);
        ProfileBackupSummary cloud = ProfileBackupSummary.from(remoteValues);
        if (local.dominates(cloud)) return ConflictState.LOCAL_AHEAD;
        if (cloud.dominates(local)) return ConflictState.REMOTE_AHEAD;
        return ConflictState.DIVERGED;
    }

    public DownloadResult downloadAndReplaceLocal() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return DownloadResult.EMPTY_REMOTE;
        return applyRemote(remote.payload());
    }

    public DownloadResult applyRemote(String remoteBackup) {
        return ProfileStore.importBackup(remoteBackup) ? DownloadResult.APPLIED : DownloadResult.REJECTED_NEWER_SCHEMA;
    }
}
