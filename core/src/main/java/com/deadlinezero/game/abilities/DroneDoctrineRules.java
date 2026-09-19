package com.deadlinezero.game.abilities;

/** Pure doctrine tuning shared by runtime and tests. */
public final class DroneDoctrineRules {
    private DroneDoctrineRules() {}

    public static float damageMultiplier(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.HUNTER ? 1.30f
            : doctrine == DroneDoctrine.SENTINEL ? .88f
            : 1f;
    }

    public static float secondaryTargetMultiplier(DroneDoctrine doctrine, boolean targetNetwork) {
        if (doctrine == DroneDoctrine.HUNTER) return targetNetwork ? .82f : .72f;
        return targetNetwork ? .62f : 0f;
    }

    public static float interceptionRange(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.SENTINEL ? 3.4f : 0f;
    }
}
