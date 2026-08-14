package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BootstrapEnvironmentArtTest {
    @Test void environmentKeysAreUniqueAndAddressable() {
        for (int i = 0; i < BootstrapEnvironmentArt.KEYS.length; i++) {
            String key = BootstrapEnvironmentArt.KEYS[i];
            assertEquals(i, BootstrapEnvironmentArt.indexOf(key), key);
            for (int j = i + 1; j < BootstrapEnvironmentArt.KEYS.length; j++) {
                assertTrue(!key.equals(BootstrapEnvironmentArt.KEYS[j]), "Duplicate environment key: " + key);
            }
        }
    }

    @Test void sheetFitsConservativeMobileTextureLimits() {
        int rows = (BootstrapEnvironmentArt.KEYS.length + BootstrapEnvironmentArt.COLUMNS - 1)
            / BootstrapEnvironmentArt.COLUMNS;
        assertTrue(BootstrapEnvironmentArt.COLUMNS * BootstrapEnvironmentArt.TILE <= 2048);
        assertTrue(rows * BootstrapEnvironmentArt.TILE <= 2048);
    }

    @Test void requiredSetDressingIsPresent() {
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/floor/concrete_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/floor/concrete_b") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/floor/concrete_c") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/floor/hazard_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/decal/crack_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/decal/blood_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/decal/scorch_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/prop/barrier_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/prop/debris_a") >= 0);
        assertTrue(BootstrapEnvironmentArt.indexOf("environment/prop/debris_b") >= 0);
    }
}
