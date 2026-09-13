package com.deadlinezero.game.services;

/** Provider-neutral cloud persistence boundary. Platform modules may bind Google Play Games or another backend. */
public interface CloudSaveAdapter {
    record RemoteBackup(String payload, long modifiedAtEpochMillis) {}
    record ProviderConflict(RemoteBackup server, RemoteBackup conflicting) {}
    enum ConflictChoice { SERVER, CONFLICTING }

    default boolean available() { return true; }
    default boolean supportsAuthentication() { return false; }
    default void authenticate() throws Exception { }
    RemoteBackup read() throws Exception;
    void write(String payload) throws Exception;
    default ProviderConflict pendingConflict() { return null; }
    default void resolvePendingConflict(ConflictChoice choice) throws Exception {
        throw new IllegalStateException("Cloud provider does not expose a resolvable conflict");
    }

    static CloudSaveAdapter unavailable() {
        return new CloudSaveAdapter() {
            @Override public boolean available() { return false; }
            @Override public RemoteBackup read() { return null; }
            @Override public void write(String payload) {
                throw new IllegalStateException("Cloud save provider is unavailable");
            }
        };
    }
}
