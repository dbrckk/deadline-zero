package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;
import org.junit.jupiter.api.Test;

final class ProtocolUpgradeGuidanceTest {
    private Player fresh() {
        RunLoadoutContext.end();
        return new Player(0f, 0f);
    }

    @Test void baseProtocolChoicesShowUnlockGuidance() {
        Player p = fresh();
        assertEquals("combat.protocolGuidance.unlock",
            ProtocolUpgradeGuidance.key(p, Upgrade.RHYTHM_DRIVER));
        assertEquals("combat.protocolGuidance.unlock",
            ProtocolUpgradeGuidance.key(p, Upgrade.KILLCHAIN_CAPACITOR));
        assertEquals("combat.protocolGuidance.unlock",
            ProtocolUpgradeGuidance.key(p, Upgrade.REACTION_CORE));
    }

    @Test void evolutionChoiceShowsEvolutionGuidanceAfterBase() {
        Player p = fresh();
        Upgrade.RHYTHM_DRIVER.apply(p);
        assertEquals("combat.protocolGuidance.evolution",
            ProtocolUpgradeGuidance.key(p, Upgrade.RHYTHM_ACCELERATOR));
    }

    @Test void evolutionGuidanceDisappearsAfterEvolution() {
        Player p = fresh();
        Upgrade.KILLCHAIN_CAPACITOR.apply(p);
        Upgrade.KILLCHAIN_OVERCHARGE.apply(p);
        assertNull(ProtocolUpgradeGuidance.key(p, Upgrade.KILLCHAIN_OVERCHARGE));
    }

    @Test void nonProtocolUpgradeHasNoProtocolGuidance() {
        assertNull(ProtocolUpgradeGuidance.key(fresh(), Upgrade.DAMAGE));
    }
}
