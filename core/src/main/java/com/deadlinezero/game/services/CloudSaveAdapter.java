package com.deadlinezero.game.services;

/** Provider-neutral cloud persistence boundary. Platform modules may bind Google Play Games or another backend. */
public interface CloudSaveAdapter {
    record RemoteBackup(String payload, long modifiedAtEpochMillis) {}

    RemoteBackup read() throws Exception;
    void write(String payload) throws Exception;

    static CloudSaveAdapter unavailable() {
        return new CloudSaveAdapter() {
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) { }
        };
    }
}
