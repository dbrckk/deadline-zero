package com.deadlinezero.game.services;

/** Signals that the cloud provider itself has two unresolved snapshot versions. */
public final class CloudProviderConflictException extends Exception {
    private final CloudSaveAdapter.ProviderConflict conflict;

    public CloudProviderConflictException(CloudSaveAdapter.ProviderConflict conflict) {
        super("Cloud provider has an unresolved snapshot conflict");
        if (conflict == null) throw new IllegalArgumentException("conflict");
        this.conflict = conflict;
    }

    public CloudSaveAdapter.ProviderConflict conflict() { return conflict; }
}
