package com.deadlinezero.game.services;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Small thread-safe guard for asynchronous operations that must never overlap.
 * A caller that successfully begins an operation owns the gate until end() is called.
 */
public final class SingleFlightGate {
    private final AtomicBoolean active = new AtomicBoolean(false);

    public boolean tryBegin() {
        return active.compareAndSet(false, true);
    }

    public void end() {
        active.set(false);
    }

    public boolean active() {
        return active.get();
    }
}
