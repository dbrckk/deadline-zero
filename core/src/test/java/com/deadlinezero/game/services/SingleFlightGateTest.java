package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class SingleFlightGateTest {
    @Test void rejectsOverlappingOperationsUntilReleased() {
        SingleFlightGate gate = new SingleFlightGate();

        assertTrue(gate.tryBegin());
        assertTrue(gate.active());
        assertFalse(gate.tryBegin());

        gate.end();
        assertFalse(gate.active());
        assertTrue(gate.tryBegin());
    }

    @Test void endIsIdempotent() {
        SingleFlightGate gate = new SingleFlightGate();

        gate.end();
        gate.end();

        assertFalse(gate.active());
        assertTrue(gate.tryBegin());
    }
}
