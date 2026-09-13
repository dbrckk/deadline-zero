package com.deadlinezero.game.services;

import com.deadlinezero.game.meta.ProfileBackupCodec;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.ProfileBackupSummary;
import com.deadlinezero.game.meta.EntitlementStore;
import com.deadlinezero.game.meta.PlayerProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Explicit cloud backup operations. Conflict policy stays user/UX controlled instead of silently overwriting progress. */
public final class CloudSaveService {
    public enum DownloadResult { APPLIED, EMPTY_REMOTE, REJECTED_NEWER_SCHEMA }
    public enum ConflictState { EQUAL, LOCAL_AHEAD, REMOTE_AHEAD, DIVERGED }
    public record RestoreOutcome(DownloadResult result, PlayerProfile profile) {}
    private static final Set<String> MONOTONE_KEYS = Set.of(
        "highestStage", "accountLevel", "totalRuns", "totalKills", "threat.highest"
    );

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

        // Only rank snapshots when every non-monotone field is identical. Currency, inventory,
        // achievements, receipts, loadout, daily/weekly state, etc. must never be discarded
        // merely because one side has larger lifetime counters.
        if (!nonMonotoneState(localValues).equals(nonMonotoneState(remoteValues))) {
            return ConflictState.DIVERGED;
        }

        ProfileBackupSummary local = ProfileBackupSummary.from(localValues);
        ProfileBackupSummary cloud = ProfileBackupSummary.from(remoteValues);
        if (local.dominates(cloud)) return ConflictState.LOCAL_AHEAD;
        if (cloud.dominates(local)) return ConflictState.REMOTE_AHEAD;
        return ConflictState.DIVERGED;
    }

    public RestoreOutcome downloadAndReplaceLocal() throws Exception {
        CloudSaveAdapter.RemoteBackup remote = inspectRemote();
        if (remote == null) return new RestoreOutcome(DownloadResult.EMPTY_REMOTE, null);
        return applyRemote(remote.payload());
    }

    public RestoreOutcome applyRemote(String remoteBackup) {
        if (!ProfileStore.importBackup(remoteBackup)) {
            return new RestoreOutcome(DownloadResult.REJECTED_NEWER_SCHEMA, null);
        }
        PlayerProfile restored = ProfileStore.load();
        // Store-owned entitlements are device/store authoritative and must not be restored from cloud.
        EntitlementStore.loadInto(restored);
        return new RestoreOutcome(DownloadResult.APPLIED, restored);
    }

    private static Map<String, Object> nonMonotoneState(Map<String, Object> values) {
        Map<String, Object> copy = new HashMap<>(values);
        for (String key : MONOTONE_KEYS) copy.remove(key);
        return copy;
    }
}
