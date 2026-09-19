package com.deadlinezero.game.progression;

import com.deadlinezero.game.entities.Player;

/** Presentation-only guidance for the two-stage combat protocol build paths. */
public final class ProtocolUpgradeGuidance {
    private ProtocolUpgradeGuidance() {}

    public static String key(Player player, Upgrade upgrade) {
        if (player == null || upgrade == null) return null;
        return switch (upgrade) {
            case RHYTHM_DRIVER, KILLCHAIN_CAPACITOR, REACTION_CORE ->
                "combat.protocolGuidance.unlock";
            case RHYTHM_ACCELERATOR ->
                player.protocols.rhythmEnabled() && !player.protocols.rhythmEvolved()
                    ? "combat.protocolGuidance.evolution" : null;
            case KILLCHAIN_OVERCHARGE ->
                player.protocols.killchainEnabled() && !player.protocols.killchainEvolved()
                    ? "combat.protocolGuidance.evolution" : null;
            case REACTION_CASCADE ->
                player.protocols.reactionEnabled() && !player.protocols.reactionEvolved()
                    ? "combat.protocolGuidance.evolution" : null;
            default -> null;
        };
    }
}
