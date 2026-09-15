package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Repository-level localization guardrail.
 *
 * Fails CI when a static UI localization lookup references a missing key, the English catalog
 * contains duplicate keys, a MessageFormat placeholder sequence is malformed, or a screen
 * reintroduces a direct user-facing literal in BitmapFont.draw(). Dynamic content families are
 * resolved through stable enum/id-derived keys and are therefore exempt from static orphan checks.
 */
final class LocalizationCatalogGuardTest {
    private static final Pattern STATIC_LOOKUP = Pattern.compile(
        "(?:\\bt|\\bf|i18n\\.text|i18n\\.format)\\(\\s*\"([^\"]+)\""
    );
    private static final Pattern DIRECT_FONT_LITERAL = Pattern.compile(
        "font\\.draw\\(\\s*batch\\s*,\\s*\"([^\"]*)\""
    );
    private static final Set<String> ALLOWED_PRESENTATION_LITERALS = Set.of("‹", "›", "", " • ");
    private static final List<String> DYNAMIC_PREFIXES = List.of(
        "upgrade.", "legendary.", "weapon.", "survivor.", "achievement.",
        "mastery.rank.", "biome.", "runModifier.", "equipment.", "recovery.",
        "boss.", "encounter."
    );

    @Test void catalogHasUniqueKeysAndValidPlaceholderSequences() throws Exception {
        Catalog catalog = loadCatalog();
        assertTrue(catalog.duplicates.isEmpty(), "Duplicate i18n keys: " + catalog.duplicates);

        List<String> malformed = new ArrayList<>();
        for (Map.Entry<String, String> entry : catalog.values.entrySet()) {
            try {
                new MessageFormat(entry.getValue());
            } catch (IllegalArgumentException exception) {
                malformed.add(entry.getKey() + " -> " + exception.getMessage());
            }
        }
        assertTrue(malformed.isEmpty(), "Malformed i18n MessageFormat patterns: " + malformed);
    }

    @Test void everyStaticLocalizationLookupExistsInCatalog() throws Exception {
        Catalog catalog = loadCatalog();
        Set<String> referenced = staticReferencedKeys();
        Set<String> missing = new LinkedHashSet<>(referenced);
        missing.removeAll(catalog.values.keySet());
        assertTrue(missing.isEmpty(), "Missing i18n keys referenced by UI code: " + missing);
    }

    @Test void screensDoNotReintroduceDirectUserFacingFontLiterals() throws Exception {
        Path root = repositoryRoot();
        List<String> violations = new ArrayList<>();
        for (Path source : presentationSources(root)) {
            String content = Files.readString(source, StandardCharsets.UTF_8);
            Matcher matcher = DIRECT_FONT_LITERAL.matcher(content);
            while (matcher.find()) {
                String literal = matcher.group(1);
                if (ALLOWED_PRESENTATION_LITERALS.contains(literal)) continue;
                if (literal.isBlank()) continue;
                violations.add(root.relativize(source) + " -> \"" + literal + "\"");
            }
        }
        assertTrue(violations.isEmpty(),
            "User-facing font literals must use localization keys: " + violations);
    }

    @Test void catalogOrphanAuditStaysVisibleWithoutBlockingDynamicContent() throws Exception {
        Catalog catalog = loadCatalog();
        Set<String> referenced = staticReferencedKeys();
        List<String> clearOrphans = catalog.values.keySet().stream()
            .filter(key -> !referenced.contains(key))
            .filter(key -> DYNAMIC_PREFIXES.stream().noneMatch(key::startsWith))
            .sorted()
            .toList();

        // Diagnostic only: static orphan detection is intentionally non-blocking because some
        // strings are reached indirectly by runtime state. It remains visible in test reports.
        if (!clearOrphans.isEmpty()) {
            System.out.println("I18N_ORPHAN_AUDIT " + clearOrphans.size() + " candidate(s): " + clearOrphans);
        }
        assertFalse(catalog.values.isEmpty(), "Localization catalog must not be empty");
    }

    private static Set<String> staticReferencedKeys() throws IOException {
        Path root = repositoryRoot();
        Set<String> keys = new LinkedHashSet<>();
        for (Path source : presentationSources(root)) {
            String content = Files.readString(source, StandardCharsets.UTF_8);
            Matcher matcher = STATIC_LOOKUP.matcher(content);
            while (matcher.find()) keys.add(matcher.group(1));
        }
        return keys;
    }

    private static List<Path> presentationSources(Path root) throws IOException {
        List<Path> result = new ArrayList<>();
        Path screens = root.resolve("core/src/main/java/com/deadlinezero/game/screen");
        try (Stream<Path> stream = Files.walk(screens)) {
            stream.filter(path -> path.toString().endsWith(".java")).forEach(result::add);
        }
        result.add(root.resolve("core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java"));
        result.add(root.resolve("core/src/main/java/com/deadlinezero/game/meta/RunShareText.java"));
        return result;
    }

    private static Catalog loadCatalog() throws IOException {
        Path file = repositoryRoot().resolve("assets/i18n/messages.properties");
        Map<String, String> values = new LinkedHashMap<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String raw : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            String line = raw.strip();
            if (line.isEmpty() || line.startsWith("#") || line.startsWith("!")) continue;
            int split = firstUnescapedSeparator(line);
            if (split <= 0) continue;
            String key = line.substring(0, split).strip();
            String value = line.substring(split + 1).strip();
            if (values.putIfAbsent(key, value) != null) duplicates.add(key);
        }
        return new Catalog(values, duplicates);
    }

    private static int firstUnescapedSeparator(String line) {
        boolean escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (escaped) { escaped = false; continue; }
            if (ch == '\\') { escaped = true; continue; }
            if (ch == '=' || ch == ':') return i;
        }
        return -1;
    }

    private static Path repositoryRoot() {
        Path current = Path.of("").toAbsolutePath().normalize();
        for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("assets/i18n/messages.properties"))
                && Files.isDirectory(candidate.resolve("core/src/main/java"))) return candidate;
        }
        throw new IllegalStateException("Unable to locate repository root from " + current);
    }

    private record Catalog(Map<String, String> values, Set<String> duplicates) { }
}
