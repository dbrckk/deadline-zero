package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

final class ProfileMigrationDocumentationTest {
    @Test void documentedSchemaVersionMatchesRuntimeSchema() throws Exception {
        Path root = repositoryRoot();
        String contract = Files.readString(root.resolve("docs/PROFILE_MIGRATIONS.md"), StandardCharsets.UTF_8);

        assertTrue(contract.contains("- Current version: \`" + ProfileSchema.CURRENT_VERSION + "\`"),
            "PROFILE_MIGRATIONS.md must track ProfileSchema.CURRENT_VERSION");
        assertTrue(contract.contains("Version \`1 -> 2\`"));
        assertTrue(contract.contains("Version \`2 -> 3\`"));
        assertTrue(contract.contains("newer unsupported schema remains untouched"));
    }

    private static Path repositoryRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("docs/PROFILE_MIGRATIONS.md"))
                && Files.isRegularFile(candidate.resolve("settings.gradle"))) {
                return candidate;
            }
        }
        throw new IllegalStateException("Unable to locate repository root from " + current);
    }
}
