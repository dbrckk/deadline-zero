package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

final class EnvironmentArtCatalogTest {
    @Test void productionKeysAreUniqueAcrossAllFiveBiomes() {
        var all = EnvironmentArtCatalog.allProductionKeys();
        assertEquals(70, all.size());
        assertEquals(70, new HashSet<>(all).size());
    }

    @Test void preservesTheGenericSlotShapeUnderBiomePrefix() {
        String key = EnvironmentArtCatalog.productionKey(
            EnvironmentBiomeRules.Biome.CINDER_FOUNDRY,
            "environment/prop/crate_a");
        assertEquals("environment/cinder_foundry/prop/crate_a", key);
    }

    @Test void everyBiomeDefinesTheFullFourteenSlotPack() {
        for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
            var keys = EnvironmentArtCatalog.productionKeys(biome);
            assertEquals(BootstrapEnvironmentArt.KEYS.length, keys.size());
            String prefix = "environment/" + EnvironmentArtCatalog.biomeToken(biome) + "/";
            assertTrue(keys.stream().allMatch(key -> key.startsWith(prefix)));
        }
    }
}
