package com.deadlinezero.game.services;

/** Signals that a configured cloud provider requires an interactive account sign-in. */
public final class CloudAuthenticationRequiredException extends Exception {
    public CloudAuthenticationRequiredException() {
        super("Cloud provider authentication is required");
    }
}
