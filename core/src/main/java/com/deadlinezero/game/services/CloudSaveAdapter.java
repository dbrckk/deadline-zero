package com.deadlinezero.game.services;

/** Provider-neutral cloud-save boundary. Platform modules own transport; core owns policy. */
public interface CloudSaveAdapter {
    enum Error { UNAVAILABLE, AUTH_REQUIRED, NETWORK, RATE_LIMITED, REMOTE_REJECTED, CORRUPT_REMOTE, UNKNOWN }

    interface LoadCallback {
        void onLoaded(CloudSaveSnapshot snapshot);
        void onNotFound();
        void onError(Error error);
    }

    interface SaveCallback {
        void onSaved(CloudSaveSnapshot snapshot);
        void onConflict(CloudSaveSnapshot remote);
        void onError(Error error);
    }

    boolean available();
    void load(LoadCallback callback);
    void save(CloudSaveSnapshot snapshot, SaveCallback callback);
}
