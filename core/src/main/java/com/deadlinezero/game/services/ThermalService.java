package com.deadlinezero.game.services;

/** Platform-neutral thermal pressure bridge used to protect sustained mobile performance. */
public interface ThermalService {
    enum Level {
        UNKNOWN(120, 1.00f),
        NORMAL(120, 1.00f),
        LIGHT(120, .92f),
        MODERATE(90, .76f),
        SEVERE(60, .58f),
        CRITICAL(60, .46f);

        public final int fpsCeiling;
        public final float fxCeiling;

        Level(int fpsCeiling, float fxCeiling) {
            this.fpsCeiling = fpsCeiling;
            this.fxCeiling = fxCeiling;
        }
    }

    Level level();

    static ThermalService noOp() {
        return () -> Level.UNKNOWN;
    }
}
