package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class ProfileSchemaTest {
    @Test void legacyProfileIsStampedWithoutLosingExistingValues() {
        MemoryStore store = new MemoryStore();
        store.values.put("credits", 12500);
        store.values.put("inventory.count", 120);
        store.values.put("purchase.receipt.count", 7);
        store.values.put("survivor.REX.level", 12);

        assertTrue(ProfileSchema.migrate(store));

        assertEquals(ProfileSchema.CURRENT_VERSION, store.getInteger(ProfileSchema.VERSION_KEY, -1));
        assertEquals(12500, store.getInteger("credits", -1));
        assertEquals(120, store.getInteger("inventory.count", -1));
        assertEquals(7, store.getInteger("purchase.receipt.count", -1));
        assertEquals(12, store.getInteger("survivor.REX.level", -1));
        assertEquals(1, store.flushes);
    }

    @Test void currentSchemaMigrationIsIdempotent() {
        MemoryStore store = new MemoryStore();
        store.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);
        store.putInteger("gems", 999);

        assertTrue(ProfileSchema.migrate(store));
        assertTrue(ProfileSchema.migrate(store));

        assertEquals(ProfileSchema.CURRENT_VERSION, store.getInteger(ProfileSchema.VERSION_KEY, -1));
        assertEquals(999, store.getInteger("gems", -1));
        assertEquals(0, store.flushes);
    }

    @Test void newerSchemaIsPreservedAndRejectedForWrites() {
        MemoryStore store = new MemoryStore();
        store.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION + 3);
        store.putInteger("credits", 777);

        assertFalse(ProfileSchema.migrate(store));

        assertEquals(ProfileSchema.CURRENT_VERSION + 3, store.getInteger(ProfileSchema.VERSION_KEY, -1));
        assertEquals(777, store.getInteger("credits", -1));
        assertEquals(0, store.flushes);
    }

    @Test void corruptNegativeVersionIsTreatedAsLegacy() {
        MemoryStore store = new MemoryStore();
        store.putInteger(ProfileSchema.VERSION_KEY, -42);
        store.putInteger("accountLevel", 5);

        assertTrue(ProfileSchema.migrate(store));

        assertEquals(ProfileSchema.CURRENT_VERSION, store.getInteger(ProfileSchema.VERSION_KEY, -1));
        assertEquals(5, store.getInteger("accountLevel", -1));
        assertEquals(1, store.flushes);
    }

    private static final class MemoryStore implements ProfileSchema.Store {
        final Map<String, Integer> values = new HashMap<>();
        int flushes;

        @Override public int getInteger(String key, int defaultValue) {
            return values.getOrDefault(key, defaultValue);
        }

        @Override public void putInteger(String key, int value) {
            values.put(key, value);
        }

        @Override public void flush() {
            flushes++;
        }
    }
}
