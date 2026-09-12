package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

final class CloudSaveConflictResolverTest {
    @Test void higherRevisionWins() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 2, 1000L, "a", "local");
        CloudSaveSnapshot remote = CloudSaveSnapshot.create(1, 1, 9000L, "b", "remote");
        assertEquals(CloudSaveConflictResolver.Decision.USE_LOCAL, CloudSaveConflictResolver.resolve(local, remote));
    }

    @Test void newerTimestampBreaksRevisionTie() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 2, 1000L, "a", "local");
        CloudSaveSnapshot remote = CloudSaveSnapshot.create(1, 2, 2000L, "b", "remote");
        assertEquals(CloudSaveConflictResolver.Decision.USE_REMOTE, CloudSaveConflictResolver.resolve(local, remote));
    }

    @Test void newerRevisionWinsEvenWhenPayloadMatches() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 2, 1000L, "a", "same");
        CloudSaveSnapshot remote = CloudSaveSnapshot.create(1, 3, 2000L, "b", "same");
        assertEquals(CloudSaveConflictResolver.Decision.USE_REMOTE, CloudSaveConflictResolver.resolve(local, remote));
    }

    @Test void exactSnapshotIsIdentical() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 2, 1000L, "a", "same");
        CloudSaveSnapshot remote = CloudSaveSnapshot.restore(
            local.schemaVersion, local.revision, local.updatedAtEpochMillis, local.deviceId, local.payload, local.sha256);
        assertEquals(CloudSaveConflictResolver.Decision.IDENTICAL, CloudSaveConflictResolver.resolve(local, remote));
    }

    @Test void nullSideUsesValidSnapshot() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 1, 1000L, "a", "payload");
        assertEquals(CloudSaveConflictResolver.Decision.USE_LOCAL, CloudSaveConflictResolver.resolve(local, null));
        assertEquals(CloudSaveConflictResolver.Decision.USE_REMOTE, CloudSaveConflictResolver.resolve(null, local));
        assertEquals(CloudSaveConflictResolver.Decision.IDENTICAL, CloudSaveConflictResolver.resolve(null, null));
    }

    @Test void tamperedRevisionInvalidatesIntegrity() {
        CloudSaveSnapshot original = CloudSaveSnapshot.create(1, 2, 1000L, "a", "payload");
        CloudSaveSnapshot tampered = CloudSaveSnapshot.restore(1, 999, 1000L, "a", "payload", original.sha256);
        assertFalse(tampered.integrityValid());
        assertThrows(IllegalArgumentException.class,
            () -> CloudSaveConflictResolver.resolve(original, tampered));
        assertThrows(IllegalArgumentException.class,
            () -> CloudSaveConflictResolver.resolve(tampered, original));
    }

    @Test void schemaIsPartOfConflictOrdering() {
        CloudSaveSnapshot local = CloudSaveSnapshot.create(1, 2, 1000L, "a", "same");
        CloudSaveSnapshot remote = CloudSaveSnapshot.create(2, 2, 1000L, "a", "same");
        assertEquals(CloudSaveConflictResolver.Decision.USE_REMOTE, CloudSaveConflictResolver.resolve(local, remote));
    }

    @Test void zeroSchemaIsRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> CloudSaveSnapshot.create(0, 0, 0L, "a", "payload"));
    }
}
