package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatVisualEventsProtocolTest {
    @Test void protocolCuePublishesAndResets() {
        CombatVisualEvents.reset();
        assertEquals(CombatVisualEvents.ProtocolCue.NONE, CombatVisualEvents.protocolCue());
        long before = CombatVisualEvents.protocolSerial();

        CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.RHYTHM);
        assertEquals(CombatVisualEvents.ProtocolCue.RHYTHM, CombatVisualEvents.protocolCue());
        assertEquals(before + 1, CombatVisualEvents.protocolSerial());
        assertTrue(CombatVisualEvents.protocolAgeSeconds() < 1f);

        CombatVisualEvents.reset();
        assertEquals(CombatVisualEvents.ProtocolCue.NONE, CombatVisualEvents.protocolCue());
        assertEquals(0L, CombatVisualEvents.protocolSerial());
    }

    @Test void noneCueDoesNotPublish() {
        CombatVisualEvents.reset();
        CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.NONE);
        assertEquals(0L, CombatVisualEvents.protocolSerial());
    }

    @Test void synergyUnlockPublishesAndResets() {
        CombatVisualEvents.reset();
        CombatVisualEvents.markSynergy("hud.synergyUnlocked.arcReactor");
        assertEquals("hud.synergyUnlocked.arcReactor", CombatVisualEvents.synergyKey());
        assertEquals(1L, CombatVisualEvents.synergySerial());
        assertTrue(CombatVisualEvents.synergyAgeSeconds() < 1f);

        CombatVisualEvents.reset();
        assertEquals(0L, CombatVisualEvents.synergySerial());
        assertEquals(null, CombatVisualEvents.synergyKey());
    }
}
