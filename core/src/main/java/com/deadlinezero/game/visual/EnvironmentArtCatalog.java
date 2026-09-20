package com.deadlinezero.game.visual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Canonical production naming for biome-specific environment art.
 *
 * <p>The runtime always prefers these atlas regions and then falls back to the existing generic
 * environment keys/bootstrap art. This lets production art land biome-by-biome without making
 * incomplete packs crash or visually disappear.</p>
 */
public final class EnvironmentArtCatalog {
    private static final String ROOT = "environment/";

    private EnvironmentArtCatalog() { }

    public static String biomeToken(EnvironmentBiomeRules.Biome biome) {
        EnvironmentBiomeRules.Biome safe = biome == null
            ? EnvironmentBiomeRules.Biome.QUARANTINE_YARD : biome;
        return safe.name().toLowerCase(java.util.Locale.ROOT);
    }

    public static String productionKey(EnvironmentBiomeRules.Biome biome, String genericKey) {
        if (genericKey == null || !genericKey.startsWith(ROOT)) return genericKey;
        return ROOT + biomeToken(biome) + "/" + genericKey.substring(ROOT.length());
    }

    public static List<String> productionKeys(EnvironmentBiomeRules.Biome biome) {
        List<String> keys = new ArrayList<>(BootstrapEnvironmentArt.KEYS.length);
        for (String generic : BootstrapEnvironmentArt.KEYS) keys.add(productionKey(biome, generic));
        return Collections.unmodifiableList(keys);
    }

    public static List<String> allProductionKeys() {
        List<String> keys = new ArrayList<>(
            EnvironmentBiomeRules.Biome.values().length * BootstrapEnvironmentArt.KEYS.length);
        for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
            keys.addAll(productionKeys(biome));
        }
        return Collections.unmodifiableList(keys);
    }
}
