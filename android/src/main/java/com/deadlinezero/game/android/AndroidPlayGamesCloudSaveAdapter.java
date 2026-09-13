package com.deadlinezero.game.android;

import android.app.Activity;
import android.os.Looper;
import com.deadlinezero.game.services.CloudSaveAdapter;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Tasks;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/** Google Play Games v2 Saved Games backend. Calls must run off the Android main thread. */
public final class AndroidPlayGamesCloudSaveAdapter implements CloudSaveAdapter {
    private static final String SNAPSHOT_NAME = "deadline_zero_profile";
    private static final long TIMEOUT_SECONDS = 20L;
    private final Activity activity;

    public AndroidPlayGamesCloudSaveAdapter(Activity activity) {
        if (activity == null) throw new IllegalArgumentException("activity");
        this.activity = activity;
    }

    @Override public RemoteBackup read() throws Exception {
        requireWorkerThread();
        if (!authenticated()) return null;

        Snapshot snapshot = open(false);
        if (snapshot == null) return null;
        try {
            byte[] bytes = snapshot.getSnapshotContents().readFully();
            String payload = new String(bytes, StandardCharsets.UTF_8);
            long modifiedAt = snapshot.getMetadata().getLastModifiedTimestamp();
            PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
            return payload.isBlank() ? null : new RemoteBackup(payload, modifiedAt);
        } catch (Exception e) {
            PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
            throw e;
        }
    }

    @Override public void write(String payload) throws Exception {
        requireWorkerThread();
        if (payload == null || payload.isBlank()) throw new IllegalArgumentException("payload");
        if (!authenticated()) throw new IllegalStateException("Play Games authentication required");

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

    private Snapshot open(boolean createIfMissing) throws Exception {
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
            throw new IllegalStateException("Play Games cloud snapshot conflict requires explicit resolution");
        }
        return result.getData();
    }

    private boolean authenticated() throws Exception {
        var result = Tasks.await(
            PlayGames.getGamesSignInClient(activity).isAuthenticated(),
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        );
        return result != null && result.isAuthenticated();
    }

    private static void requireWorkerThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cloud save must not block the Android main thread");
        }
    }
}
