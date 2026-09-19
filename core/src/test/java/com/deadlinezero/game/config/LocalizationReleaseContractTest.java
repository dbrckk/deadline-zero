package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

final class LocalizationReleaseContractTest {
    @Test void releaseContractMatchesRepositoryLocalizationArchitecture() throws Exception {
        Path root = repositoryRoot();
        String contract = Files.readString(root.resolve("play/store/LOCALIZATION.md"), StandardCharsets.UTF_8);

        assertTrue(Files.isRegularFile(root.resolve("assets/i18n/messages.properties")));
        assertTrue(Files.isRegularFile(root.resolve(
            "core/src/main/java/com/deadlinezero/game/config/Localization.java")));
        assertTrue(Files.isRegularFile(root.resolve(
            "core/src/test/java/com/deadlinezero/game/config/LocalizationCatalogGuardTest.java")));

        assertTrue(contract.contains("repository-level localization architecture"));
        assertTrue(contract.contains("centralized English catalog"));
        assertTrue(contract.contains("English-only"));
        assertTrue(contract.contains("[x] Centralized translatable string catalog exists for core UI."));
        assertTrue(contract.contains("[ ] Runtime locale selection is implemented."));
        assertFalse(contract.contains("no repository-level i18n/localization resource system"));
    }

    private static Path repositoryRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("play/store/LOCALIZATION.md"))
                && Files.isRegularFile(candidate.resolve("assets/i18n/messages.properties"))) {
                return candidate;
            }
        }
        throw new IllegalStateException("Unable to locate repository root from " + current);
    }
}
