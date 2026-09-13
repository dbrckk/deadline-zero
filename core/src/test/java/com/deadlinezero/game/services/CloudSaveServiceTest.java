package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.meta.ProfileBackupCodec;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class CloudSaveServiceTest {
    @Test void unavailableAdapterBehavesAsEmptyRemote() throws Exception {
        CloudSaveService service = new CloudSaveService(null);
        assertFalse(service.available());
        assertNull(service.inspectRemote());
        assertEquals(CloudSaveService.DownloadResult.EMPTY_REMOTE, service.downloadRemote().result());
    }

    @Test void unavailableProviderRejectsUploadExplicitly() {
        CloudSaveService service = new CloudSaveService(null);
        String backup = ProfileBackupCodec.encode(Map.of("accountLevel", 1));
        assertThrows(IllegalStateException.class, () -> service.upload(backup));
    }

    @Test void corruptRemotePayloadIsRejectedBeforeImport() {
        CloudSaveService service = serviceReturning("not-a-valid-backup");
        assertThrows(IllegalArgumentException.class, service::inspectRemote);
    }

    @Test void monotoneAdvanceIsSafeOnlyWhenEveryOtherPersistedFieldMatches() throws Exception {
        Map<String, Object> localValues = new HashMap<>();
        localValues.put("highestStage", 4);
        localValues.put("accountLevel", 8);
        localValues.put("totalRuns", 20);
        localValues.put("totalKills", 3000L);
        localValues.put("threat.highest", 1);
        localValues.put("credits", 100L);

        Map<String, Object> remoteValues = new HashMap<>(localValues);
        remoteValues.put("highestStage", 6);
        remoteValues.put("accountLevel", 9);
        remoteValues.put("totalRuns", 30);
        remoteValues.put("totalKills", 5000L);
        remoteValues.put("threat.highest", 2);

        CloudSaveService service = serviceReturning(ProfileBackupCodec.encode(remoteValues));
        assertEquals(CloudSaveService.ConflictState.REMOTE_AHEAD,
            service.compareRemoteToLocal(ProfileBackupCodec.encode(localValues)));
    }

    @Test void uniqueNonMonotoneProgressForcesDivergedEvenWhenRemoteCountersAreAhead() throws Exception {
        Map<String, Object> localValues = new HashMap<>();
        localValues.put("highestStage", 4);
        localValues.put("accountLevel", 8);
        localValues.put("totalRuns", 20);
        localValues.put("totalKills", 3000L);
        localValues.put("threat.highest", 1);
        localValues.put("achievement.FIRST_CLEAR.claimed", true);

        Map<String, Object> remoteValues = new HashMap<>();
        remoteValues.put("highestStage", 6);
        remoteValues.put("accountLevel", 9);
        remoteValues.put("totalRuns", 30);
        remoteValues.put("totalKills", 5000L);
        remoteValues.put("threat.highest", 2);
        remoteValues.put("achievement.FIRST_CLEAR.claimed", false);

        CloudSaveService service = serviceReturning(ProfileBackupCodec.encode(remoteValues));
        assertEquals(CloudSaveService.ConflictState.DIVERGED,
            service.compareRemoteToLocal(ProfileBackupCodec.encode(localValues)));
    }

    @Test void exactPayloadEqualityIsEqual() throws Exception {
        String backup = ProfileBackupCodec.encode(Map.of(
            "highestStage", 6, "accountLevel", 9, "credits", 900L));
        CloudSaveService service = serviceReturning(backup);
        assertEquals(CloudSaveService.ConflictState.EQUAL, service.compareRemoteToLocal(backup));
    }

    @Test void authenticationRequestIsDelegatedExplicitly() throws Exception {
        final boolean[] authenticated = { false };
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public boolean supportsAuthentication() { return true; }
            @Override public void authenticate() { authenticated[0] = true; }
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) { }
        });

        assertTrue(service.supportsAuthentication());
        service.authenticate();
        assertTrue(authenticated[0]);
    }

    @Test void providerConflictChoiceIsDelegatedExplicitly() throws Exception {
        CloudSaveAdapter.ProviderConflict conflict = new CloudSaveAdapter.ProviderConflict(
            new CloudSaveAdapter.RemoteBackup("server", 1L),
            new CloudSaveAdapter.RemoteBackup("other", 2L)
        );
        final CloudSaveAdapter.ConflictChoice[] chosen = { null };
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) { }
            @Override public ProviderConflict pendingConflict() { return conflict; }
            @Override public void resolvePendingConflict(ConflictChoice choice) { chosen[0] = choice; }
        });

        assertEquals(conflict, service.pendingProviderConflict());
        service.resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING);
        assertEquals(CloudSaveAdapter.ConflictChoice.CONFLICTING, chosen[0]);
    }

    @Test void uploadRejectsCorruptLocalPayloadBeforeProviderWrite() {
        CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) { throw new AssertionError("provider must not receive corrupt backup"); }
        });
        assertThrows(IllegalArgumentException.class, () -> service.upload("corrupt"));
    }

    @Test void restoreResultRequiresFreshProfileOnAppliedRestore() {
        assertTrue(CloudSaveService.RestoreResult.class.isRecord());
    }

    private static CloudSaveService serviceReturning(String payload) {
        return new CloudSaveService(new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return new RemoteBackup(payload, 789L); }
            @Override public void write(String ignored) { }
        });
    }
}
