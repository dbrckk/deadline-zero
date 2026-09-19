package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

final class BillingReleaseContractTest {
    @Test void releaseDocsContainEveryShippingBillingProductId() throws Exception {
        Path root = repositoryRoot();
        String playRelease = Files.readString(root.resolve("docs/PLAY_RELEASE.md"), StandardCharsets.UTF_8);
        String playConsole = Files.readString(root.resolve("play/store/PLAY_CONSOLE.md"), StandardCharsets.UTF_8);

        for (String productId : BillingService.PRODUCTS) {
            assertTrue(playRelease.contains(productId),
                () -> "PLAY_RELEASE.md missing shipping Billing product: " + productId);
            assertTrue(playConsole.contains(productId),
                () -> "PLAY_CONSOLE.md missing shipping Billing product: " + productId);
        }
    }

    private static Path repositoryRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("docs/PLAY_RELEASE.md"))
                && Files.isRegularFile(candidate.resolve("play/store/PLAY_CONSOLE.md"))) {
                return candidate;
            }
        }
        throw new IllegalStateException("Unable to locate repository root from " + current);
    }
}
