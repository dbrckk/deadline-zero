package com.deadlinezero.game.visual;

import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.abilities.DroneDoctrine;
import com.deadlinezero.game.entities.Player;

/** Builds a compact, allocation-bounded HUD summary of the run's active build identity. */
public final class ActiveBuildStatus {
    private ActiveBuildStatus() {}

    public static void fill(Player player, String[] out) {
        if (out == null || out.length < 2) throw new IllegalArgumentException("out");
        out[0] = null;
        out[1] = null;
        if (player == null) return;
        AbilityLoadout a = player.abilities;
        int index = 0;

        DroneDoctrine doctrine = a.droneDoctrine();
        if (doctrine == DroneDoctrine.HUNTER) out[index++] = "hud.build.hunter";
        else if (doctrine == DroneDoctrine.SENTINEL) out[index++] = "hud.build.sentinel";

        String synergy = primarySynergy(a);
        if (synergy != null && index < out.length) out[index++] = synergy;

        String protocol = primaryEvolvedProtocol(player);
        if (protocol != null && index < out.length) out[index] = protocol;
    }

    static String primaryEvolvedProtocol(Player player) {
        if (player.protocols.rhythmEvolved()) return "hud.build.rhythmAccelerator";
        if (player.protocols.killchainEvolved()) return "hud.build.killchainOvercharge";
        if (player.protocols.reactionEvolved()) return "hud.build.reactionCascade";
        return null;
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
