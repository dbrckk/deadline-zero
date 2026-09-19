package com.deadlinezero.game.abilities;

/** Detects which ability synergy became active after an upgrade without duplicating synergy rules. */
public final class AbilitySynergyUnlockDetector {
    public enum Synergy {
        NONE,
        ARC_REACTOR,
        CRYO_BARRAGE,
        SUPERCONDUCTOR,
        TARGET_NETWORK,
        PERMAFROST_BLADES,
        STORM_BLADE
    }

    public record Snapshot(boolean arcReactor, boolean cryoBarrage, boolean superconductor,
                           boolean targetNetwork, boolean permafrostBlades, boolean stormBlade) {}

    private AbilitySynergyUnlockDetector() {}

    public static Snapshot snapshot(AbilityLoadout a) {
        return new Snapshot(
            a.hasTeslaEvolution(),
            a.hasCryoMissileEvolution(),
            a.hasSuperconductorSynergy(),
            a.hasTargetNetworkSynergy(),
            a.hasPermafrostBladeSynergy(),
            a.hasStormBladeSynergy());
    }

    public static Synergy newlyActivated(Snapshot before, AbilityLoadout after) {
        if (before == null || after == null) return Synergy.NONE;
        if (!before.stormBlade() && after.hasStormBladeSynergy()) return Synergy.STORM_BLADE;
        if (!before.arcReactor() && after.hasTeslaEvolution()) return Synergy.ARC_REACTOR;
        if (!before.cryoBarrage() && after.hasCryoMissileEvolution()) return Synergy.CRYO_BARRAGE;
        if (!before.superconductor() && after.hasSuperconductorSynergy()) return Synergy.SUPERCONDUCTOR;
        if (!before.targetNetwork() && after.hasTargetNetworkSynergy()) return Synergy.TARGET_NETWORK;
        if (!before.permafrostBlades() && after.hasPermafrostBladeSynergy()) return Synergy.PERMAFROST_BLADES;
        return Synergy.NONE;
    }

    public static String hudKey(Synergy synergy) {
        if (synergy == null) return null;
        return switch (synergy) {
            case ARC_REACTOR -> "hud.synergyUnlocked.arcReactor";
            case CRYO_BARRAGE -> "hud.synergyUnlocked.cryoBarrage";
            case SUPERCONDUCTOR -> "hud.synergyUnlocked.superconductor";
            case TARGET_NETWORK -> "hud.synergyUnlocked.targetNetwork";
            case PERMAFROST_BLADES -> "hud.synergyUnlocked.permafrostBlades";
            case STORM_BLADE -> "hud.synergyUnlocked.stormBlade";
            default -> null;
        };
    }
}
