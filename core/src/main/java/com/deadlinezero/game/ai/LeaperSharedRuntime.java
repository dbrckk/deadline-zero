package com.deadlinezero.game.ai;

/** Shared run-local LEAPER runtime used by simulation and presentation hooks. */
public final class LeaperSharedRuntime {
    private static final LeaperRuntime INSTANCE = new LeaperRuntime();

    private LeaperSharedRuntime() { }

    public static LeaperRuntime get() { return INSTANCE; }
}
