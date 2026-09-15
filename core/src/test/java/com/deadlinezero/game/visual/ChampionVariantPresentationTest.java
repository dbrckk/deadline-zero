package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class ChampionVariantPresentationTest {
    @Test
    void championBadgesAreShortUniqueAndNonColorSemanticCues() {
        assertEquals("", ChampionVariantPresentation.badge(Enemy.Variant.NORMAL));

        Set<String> badges = new HashSet<>();
        for (Enemy.Variant variant : Enemy.Variant.values()) {
            if (variant == Enemy.Variant.NORMAL) continue;
            String badge = ChampionVariantPresentation.badge(variant);
            assertFalse(badge.isBlank(), variant + " must have a visible badge");
            assertTrue(badge.length() <= 2, variant + " badge must stay readable at phone scale");
            assertTrue(badges.add(badge), variant + " badge must be unique");
        }
        assertEquals(8, badges.size());
    }
}
