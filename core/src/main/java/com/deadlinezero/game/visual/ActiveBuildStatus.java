package com.deadlinezero.game.visual;

import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.abilities.DroneDoctrine;
import com.deadlinezero.game.entities.Player;

/** Builds a compact, allocation-bounded HUD summary of the run's active build identity. */
public final class ActiveBuildStatus {
    private ActiveBuildStatus() {}

    public static String[] keys(Player player) {
        String[] out = new String[2];
        if (player == null) return out;
        AbilityLoadout a = player.abilities;
        int index = 0;

        DroneDoctrine doctrine = a.droneDoctrine();
        if (doctrine == DroneDoctrine.HUNTER) out[index++] = "hud.build.hunter";
        else if (doctrine == DroneDoctrine.SENTINEL) out[index++] = "hud.build.sentinel";

        String synergy = primarySynergy(a);
        if (synergy != null && index < out.length) out[index] = synergy;
        return out;
    }

    static String primarySynergy(AbilityLoadout a) {
        if (a.hasStormBladeSynergy()) return "hud.build.stormBlade";
        if (a.hasTeslaEvolution()) return "hud.build.arcReactor";
        if (a.hasCryoMissileEvolution()) return "hud.build.cryoBarrage";
        if (a.hasSuperconductorSynergy()) return "hud.build.superconductor";
        if (a.hasTargetNetworkSynergy()) return "hud.build.targetNetwork";
        if (a.hasPermafrostBladeSynergy()) return "hud.build.permafrostBlades";
        return null;
    }
}
