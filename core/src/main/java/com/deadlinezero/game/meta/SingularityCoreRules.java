package com.deadlinezero.game.meta;

/** Deterministic combat signature unlocked by the Threat 20 Zero-Day Singularity Core. */
public final class SingularityCoreRules {
    public static final String CORE_ID = "threat_20_core";
    public static final int SHOT_INTERVAL = 6;
    public static final float SPLASH_DAMAGE_MULTIPLIER = .35f;
    public static final float SPLASH_RADIUS = 2.4f;
    public static final float PULL_STRENGTH = 1.65f;

    private SingularityCoreRules() {}

    public static boolean equipped(PlayerProfile profile) {
        if (profile == null) return false;
        EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
        return core != null && CORE_ID.equals(core.id);
    }

    /** Sequence is one-based: 6, 12, 18... become Singularity shots. */
    public static boolean markedShot(long sequence) {
        return sequence > 0L && sequence % SHOT_INTERVAL == 0L;
    }
}
