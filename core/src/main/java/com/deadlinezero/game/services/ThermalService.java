package com.deadlinezero.game.services;

/** Platform-neutral thermal pressure bridge used to protect sustained mobile performance. */
public interface ThermalService {
    enum Level {
        UNKNOWN(120),
        NORMAL(120),
        LIGHT(120),
        MODERATE(90),
        SEVERE(60),
        CRITICAL(60);

        public final int fpsCeiling;

        Level(int fpsCeiling) {
            this.fpsCeiling = fpsCeiling;
        }
    }

    Level level();

    static ThermalService noOp() {
        return () -> Level.UNKNOWN;
    }
}
