package com.deadlinezero.game.services;

import com.deadlinezero.game.meta.ProfileBackupCodec;
import com.deadlinezero.game.meta.ProfileStore;

/** Explicit cloud backup operations. Conflict policy stays user/UX controlled instead of silently overwriting progress. */
public final class CloudSaveService {
    public enum DownloadResult { APPLIED, EMPTY_REMOTE, REJECTED_NEWER_SCHEMA }

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

    public DownloadResult downloadAndReplaceLocal() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return DownloadResult.EMPTY_REMOTE;
        return ProfileStore.importBackup(remote.payload()) ? DownloadResult.APPLIED : DownloadResult.REJECTED_NEWER_SCHEMA;
    }
}
