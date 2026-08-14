package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class Direction8Test {
    @Test void cardinalAndDiagonalVectorsMapToStableDirections() {
        assertEquals(Direction8.E, Direction8.fromVector(1f, 0f, Direction8.N));
        assertEquals(Direction8.NE, Direction8.fromVector(1f, 1f, Direction8.E));
        assertEquals(Direction8.N, Direction8.fromVector(0f, 1f, Direction8.E));
        assertEquals(Direction8.NW, Direction8.fromVector(-1f, 1f, Direction8.E));
        assertEquals(Direction8.W, Direction8.fromVector(-1f, 0f, Direction8.E));
        assertEquals(Direction8.SW, Direction8.fromVector(-1f, -1f, Direction8.E));
        assertEquals(Direction8.S, Direction8.fromVector(0f, -1f, Direction8.E));
        assertEquals(Direction8.SE, Direction8.fromVector(1f, -1f, Direction8.E));
    }

    @Test void stationaryOrInvalidVectorsPreservePreviousFacing() {
        assertEquals(Direction8.NW, Direction8.fromVector(0f, 0f, Direction8.NW));
        assertEquals(Direction8.S, Direction8.fromVector(Float.NaN, 1f, Direction8.S));
        assertEquals(Direction8.E, Direction8.fromVector(0f, 0f, null));
    }

    @Test void directionalAtlasPrefixIsCanonical() {
        assertEquals("survivor/rex/nw/run",
            GameArt.directionalPrefix("survivor/rex", Direction8.NW, GameArt.Motion.RUN));
        assertEquals("enemy/brute/e/attack",
            GameArt.directionalPrefix("enemy/brute", null, GameArt.Motion.ATTACK));
    }
}
