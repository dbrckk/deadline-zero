package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class DirectionalBootstrapLazyLoadTest {
    @Test void creationAndKeyLookupDoNotRequireGraphicsAllocation() {
        DirectionalBootstrapArt art = assertDoesNotThrow(DirectionalBootstrapArt::create);
        try {
            assertTrue(art.supports("survivor/rex/e/run"));
            assertTrue(art.supports("enemy/brute/nw/death"));
            assertTrue(art.supports("boss/harvester/s/attack"));
        } finally {
            assertDoesNotThrow(art::dispose);
        }
    }
}
