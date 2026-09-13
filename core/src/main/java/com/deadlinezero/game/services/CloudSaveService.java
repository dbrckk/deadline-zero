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
    public record Comparison(String localBackup, CloudSaveAdapter.RemoteBackup remote, ConflictState state) {}

    private final CloudSaveAdapter adapter;

    public CloudSaveService(CloudSaveAdapter adapter) {
        this.adapter = adapter == null ? CloudSaveAdapter.unavailable() : adapter;
    }

    public boolean available() { return adapter.available(); }
    public boolean supportsAuthentication() { return adapter.supportsAuthentication(); }
    public void authenticate() throws Exception { adapter.authenticate(); }
    public CloudSaveAdapter.ProviderConflict pendingProviderConflict() { return adapter.pendingConflict(); }

    public void resolveProviderConflict(CloudSaveAdapter.ConflictChoice choice) throws Exception {
        if (choice == null) throw new IllegalArgumentException("choice");
        adapter.resolvePendingConflict(choice);
    }

    public void uploadLocal() throws Exception {
        upload(ProfileStore.exportBackup());
    }

    public void upload(String localBackup) throws Exception {
        ProfileBackupCodec.decode(localBackup);
        adapter.write(localBackup);
    }

    public void uploadIfUnchanged(Comparison comparison) throws Exception {
        if (comparison == null) throw new IllegalArgumentException("comparison");
        String currentLocal = ProfileStore.exportBackup();
        if (!currentLocal.equals(comparison.localBackup())) {
            throw new IllegalStateException("Local profile changed; refresh before uploading");
        }
        ProfileBackupCodec.decode(currentLocal);
        adapter.writeIfUnchanged(currentLocal, comparison.remote());
    }

    public CloudSaveAdapter.RemoteBackup inspectRemote() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = adapter.read();
        if (remote == null || remote.payload() == null || remote.payload().isBlank()) return null;
        ProfileBackupCodec.decode(remote.payload());
        return remote;
    }

    public Comparison inspectAgainstLocal() throws Exception {
        String localBackup = ProfileStore.exportBackup();
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        return new Comparison(localBackup, remote, classify(localBackup, remote));
    }

    public ConflictState compareRemoteToLocal() throws Exception {
        return inspectAgainstLocal().state();
    }

    public ConflictState compareRemoteToLocal(String localBackup) throws Exception {
        return classify(localBackup, inspectRemote());
    }

    public ConflictState classify(String localBackup, CloudSaveAdapter.RemoteBackup remote) {
        Map<String, Object> localValues = ProfileBackupCodec.decode(localBackup);
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

    public CloudSaveAdapter.RemoteBackup revalidateRemote(Comparison comparison) throws Exception {
        if (comparison == null) throw new IllegalArgumentException("comparison");
        if (!ProfileStore.exportBackup().equals(comparison.localBackup())) {
            throw new IllegalStateException("Local profile changed; refresh before continuing");
        }
        CloudSaveAdapter.RemoteBackup current = inspectRemote();
        if (!sameRemote(current, comparison.remote())) throw new CloudRemoteChangedException();
        return current;
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

    public static boolean sameRemote(CloudSaveAdapter.RemoteBackup a, CloudSaveAdapter.RemoteBackup b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.modifiedAtEpochMillis() == b.modifiedAtEpochMillis()
            && java.util.Objects.equals(a.payload(), b.payload());
    }
}
