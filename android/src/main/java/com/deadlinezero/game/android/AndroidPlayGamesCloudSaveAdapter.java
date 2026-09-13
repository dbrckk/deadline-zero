package com.deadlinezero.game.android;

import android.app.Activity;
import android.os.Looper;
import com.deadlinezero.game.services.CloudAuthenticationRequiredException;
import com.deadlinezero.game.services.CloudProviderConflictException;
import com.deadlinezero.game.services.CloudSaveAdapter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.GamesClientStatusCodes;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Tasks;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/** Google Play Games v2 Saved Games backend. Calls must run off the Android main thread. */
public final class AndroidPlayGamesCloudSaveAdapter implements CloudSaveAdapter {
    private static final String SNAPSHOT_NAME = "deadline_zero_profile";
    private static final long TIMEOUT_SECONDS = 20L;

    private final Activity activity;
    private SnapshotsClient.SnapshotConflict pendingNativeConflict;
    private ProviderConflict pendingPublicConflict;

    public AndroidPlayGamesCloudSaveAdapter(Activity activity) {
        if (activity == null) throw new IllegalArgumentException("activity");
        this.activity = activity;
    }

    @Override public boolean supportsAuthentication() { return true; }

    @Override public void authenticate() throws Exception {
        requireWorkerThread();
        var result = Tasks.await(
            PlayGames.getGamesSignInClient(activity).signIn(),
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        );
        if (result == null || !result.isAuthenticated()) throw new CloudAuthenticationRequiredException();
    }

    @Override public RemoteBackup read() throws Exception {
        requireWorkerThread();
        ensureAuthenticated();

        Snapshot snapshot = open(false);
        if (snapshot == null) return null;
        try {
            RemoteBackup backup = toRemoteBackup(snapshot);
            PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
            return backup.payload().isBlank() ? null : backup;
        } catch (Exception e) {
            PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
            throw e;
        }
    }

    @Override public void write(String payload) throws Exception {
        requireWorkerThread();
        if (payload == null || payload.isBlank()) throw new IllegalArgumentException("payload");
        ensureAuthenticated();

        Snapshot snapshot = open(true);
        if (snapshot == null) throw new IllegalStateException("Unable to open Play Games snapshot");
        byte[] bytes = payload.getBytes(StandardCharsets.UTF_8);
        if (!snapshot.getSnapshotContents().writeBytes(bytes)) {
            PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
            throw new IllegalStateException("Unable to write Play Games snapshot contents");
        }

        SnapshotMetadataChange metadata = new SnapshotMetadataChange.Builder()
            .setDescription("DEADLINE ZERO profile backup")
            .build();
        Tasks.await(
            PlayGames.getSnapshotsClient(activity).commitAndClose(snapshot, metadata),
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        );
    }

    @Override public ProviderConflict pendingConflict() {
        return pendingPublicConflict;
    }

    @Override public void resolvePendingConflict(ConflictChoice choice) throws Exception {
        requireWorkerThread();
        if (choice == null) throw new IllegalArgumentException("choice");
        SnapshotsClient.SnapshotConflict nativeConflict = pendingNativeConflict;
        if (nativeConflict == null || pendingPublicConflict == null) {
            throw new IllegalStateException("No Play Games snapshot conflict is pending");
        }

        Snapshot chosen = choice == ConflictChoice.SERVER
            ? nativeConflict.getSnapshot()
            : nativeConflict.getConflictingSnapshot();

        SnapshotsClient.DataOrConflict<Snapshot> result = Tasks.await(
            PlayGames.getSnapshotsClient(activity).resolveConflict(nativeConflict.getConflictId(), chosen),
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        );

        if (result.isConflict()) {
            captureConflict(result.getConflict());
            throw new CloudProviderConflictException(pendingPublicConflict);
        }

        Snapshot resolved = result.getData();
        clearConflict();
        if (resolved != null) PlayGames.getSnapshotsClient(activity).discardAndClose(resolved);
    }

    private Snapshot open(boolean createIfMissing) throws Exception {
        try {
            SnapshotsClient.DataOrConflict<Snapshot> result = Tasks.await(
                PlayGames.getSnapshotsClient(activity).open(
                    SNAPSHOT_NAME,
                    createIfMissing,
                    SnapshotsClient.RESOLUTION_POLICY_MANUAL
                ),
                TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            );
            if (result == null) return null;
            if (result.isConflict()) {
                captureConflict(result.getConflict());
                throw new CloudProviderConflictException(pendingPublicConflict);
            }
            clearConflict();
            return result.getData();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (!createIfMissing
                && cause instanceof ApiException api
                && api.getStatusCode() == GamesClientStatusCodes.SNAPSHOT_NOT_FOUND) {
                clearConflict();
                return null;
            }
            throw e;
        }
    }

    private void captureConflict(SnapshotsClient.SnapshotConflict conflict) throws Exception {
        if (conflict == null) throw new IllegalStateException("Play Games returned an empty conflict");
        pendingNativeConflict = conflict;
        pendingPublicConflict = new ProviderConflict(
            toRemoteBackup(conflict.getSnapshot()),
            toRemoteBackup(conflict.getConflictingSnapshot())
        );
    }

    private void clearConflict() {
        pendingNativeConflict = null;
        pendingPublicConflict = null;
    }

    private static RemoteBackup toRemoteBackup(Snapshot snapshot) throws Exception {
        if (snapshot == null || snapshot.getSnapshotContents() == null) {
            throw new IllegalStateException("Play Games conflict snapshot is missing contents");
        }
        byte[] bytes = snapshot.getSnapshotContents().readFully();
        String payload = new String(bytes, StandardCharsets.UTF_8);
        long modifiedAt = snapshot.getMetadata().getLastModifiedTimestamp();
        return new RemoteBackup(payload, modifiedAt);
    }

    private void ensureAuthenticated() throws Exception {
        var result = Tasks.await(
            PlayGames.getGamesSignInClient(activity).isAuthenticated(),
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        );
        if (result == null || !result.isAuthenticated()) throw new CloudAuthenticationRequiredException();
    }

    private static void requireWorkerThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cloud save must not block the Android main thread");
        }
    }
}
