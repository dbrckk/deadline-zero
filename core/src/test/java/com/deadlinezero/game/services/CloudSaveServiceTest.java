package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
