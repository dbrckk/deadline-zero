package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.*;

import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class CombatProtocolStateTest {
    @Test void rhythmEmpowersEverySixthVolley() {
        CombatProtocolState state = new CombatProtocolState();
        state.enableRhythm();
        for (int i = 0; i < 5; i++) assertEquals(1f, state.onVolley().damageMultiplier(), .0001f);
        var proc = state.onVolley();
        assertTrue(proc.forcedCrit());
        assertEquals(1.30f, proc.damageMultiplier(), .0001f);
        assertEquals(1f, state.onVolley().damageMultiplier(), .0001f);
    }

    @Test void killchainArmsAndConsumesNextVolley() {
        CombatProtocolState state = new CombatProtocolState();
        state.enableKillchain();
        for (int i = 0; i < 7; i++) state.onKill();
        assertFalse(state.killchainArmed());
        state.onKill();
        assertTrue(state.killchainArmed());
        var proc = state.onVolley();
        assertEquals(1.45f, proc.damageMultiplier(), .0001f);
        assertEquals(1, proc.bonusPenetration());
        assertFalse(state.killchainArmed());
    }

    @Test void simultaneousProcsCombineWithoutUnboundedStacking() {
        CombatProtocolState state = new CombatProtocolState();
        state.enableRhythm();
        state.enableKillchain();
        for (int i = 0; i < 5; i++) state.onVolley();
        for (int i = 0; i < 8; i++) state.onKill();
        var proc = state.onVolley();
        assertEquals(1.75f, proc.damageMultiplier(), .0001f);
        assertTrue(proc.forcedCrit());
        assertEquals(1, proc.bonusPenetration());
    }

    @Test void reactionCoreOnlyAmplifiesRealElementReactions() {
        CombatProtocolState state = new CombatProtocolState();
        state.enableReactionCore();
        assertEquals(0f, state.reactionBonus(100f, Enemy.ElementReaction.NONE), .0001f);
        assertEquals(35f, state.reactionBonus(100f, Enemy.ElementReaction.OVERLOAD), .0001f);
        assertEquals(0f, state.reactionBonus(-5f, Enemy.ElementReaction.THERMAL_SHOCK), .0001f);
    }
}
