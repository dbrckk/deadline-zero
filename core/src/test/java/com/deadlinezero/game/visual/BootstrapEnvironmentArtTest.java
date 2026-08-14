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
        String[] required = {
            "environment/floor/concrete_a",
            "environment/floor/concrete_b",
            "environment/floor/concrete_c",
            "environment/floor/hazard_a",
            "environment/decal/crack_a",
            "environment/decal/blood_a",
            "environment/decal/scorch_a",
            "environment/prop/barrier_a",
            "environment/prop/debris_a",
            "environment/prop/debris_b",
            "environment/prop/wall_a",
            "environment/prop/wall_b",
            "environment/prop/crate_a",
            "environment/prop/beacon_a"
        };
        for (String key : required) assertTrue(BootstrapEnvironmentArt.indexOf(key) >= 0, key);
    }

    @Test void floorVariationIsStableAndCoversAllConcreteTiles() {
        boolean[] seen = new boolean[3];
        for (int y = -6; y < 6; y++) {
            for (int x = -10; x < 10; x++) {
                int first = EnvironmentRenderer.floorVariant(x, y);
                int second = EnvironmentRenderer.floorVariant(x, y);
                assertEquals(first, second);
                assertTrue(first >= 0 && first < 3);
                seen[first] = true;
            }
        }
        assertTrue(seen[0] && seen[1] && seen[2]);
    }

    @Test void beaconPulseIsDeterministicAndAlwaysNormalized() {
        boolean sawLow = false;
        boolean sawHigh = false;
        for (int i = 0; i <= 200; i++) {
            float time = i * .05f;
            float first = EnvironmentRenderer.beaconPulse(time);
            float second = EnvironmentRenderer.beaconPulse(time);
            assertEquals(first, second, 0f);
            assertTrue(first >= 0f && first <= 1f, "pulse=" + first);
            sawLow |= first < .1f;
            sawHigh |= first > .9f;
        }
        assertTrue(sawLow && sawHigh);
    }
}
