package com.deadlinezero.game.services;

/** Signals that the remote cloud snapshot changed after the user inspected it. */
public final class CloudRemoteChangedException extends Exception {
    public CloudRemoteChangedException() {
        super("Cloud snapshot changed; refresh before retrying");
    }
}
