package com.deadlinezero.game.audio;

/** Deterministic combat music profile selection from run stage. */
public final class MusicProfileSelector {
    public enum Profile { SURVIVAL, PRESSURE, APEX }

    private MusicProfileSelector() { }

    public static Profile forStage(int stage) {
        int safeStage = Math.max(1, stage);
        if (safeStage >= 7) return Profile.APEX;
        if (safeStage >= 4) return Profile.PRESSURE;
        return Profile.SURVIVAL;
    }

    public static String assetPath(Profile profile) {
        if (profile == null) profile = Profile.SURVIVAL;
        return switch (profile) {
            case SURVIVAL -> "audio/music/combat.ogg";
            case PRESSURE -> "audio/music/combat_pressure.ogg";
            case APEX -> "audio/music/combat_apex.ogg";
        };
    }
}