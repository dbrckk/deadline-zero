package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deadlinezero.game.meta.ProfileBackupCodec;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class CloudSaveServiceTest {
    @Test void unavailableAdapterBehavesAsEmptyRemote() throws Exception {
        CloudSaveService service = new CloudSaveService(null);
        assertNull(service.inspectRemote());
        assertEquals(CloudSaveService.DownloadResult.EMPTY_REMOTE, service.downloadAndReplaceLocal());
    }

    @Test void corruptRemotePayloadIsRejectedBeforeImport() {
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return new RemoteBackup("not-a-valid-backup", 123L); }
            @Override public void write(String payload) { }
        });
        assertThrows(IllegalArgumentException.class, service::inspectRemote);
    }
    @Test void explicitCompareUsesCallerProvidedLocalSnapshot() throws Exception {
        String local = ProfileBackupCodec.encode(Map.of(
            "highestStage", 4, "accountLevel", 8, "totalRuns", 20, "totalKills", 3000L, "threat.highest", 1));
        String remote = ProfileBackupCodec.encode(Map.of(
            "highestStage", 6, "accountLevel", 9, "totalRuns", 30, "totalKills", 5000L, "threat.highest", 2));
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return new RemoteBackup(remote, 456L); }
            @Override public void write(String payload) { }
        });

        assertEquals(CloudSaveService.ConflictState.REMOTE_AHEAD, service.compareRemoteToLocal(local));
    }

    @Test void uploadRejectsCorruptLocalPayloadBeforeProviderWrite() {
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) { throw new AssertionError("provider must not receive corrupt backup"); }
        });

        assertThrows(IllegalArgumentException.class, () -> service.upload("corrupt"));
    }
}
