package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Player;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class WeaponLegendaryPresentationTest {
    @Test void everyWeaponFamilyLegendaryHasDistinctPresentation() {
        Set<String> labels = new HashSet<>();
        for (WeaponLegendaryPresentation.Style style : WeaponLegendaryPresentation.Style.values()) {
            if (style == WeaponLegendaryPresentation.Style.NONE) continue;
            assertTrue(labels.add(style.label), "duplicate legendary presentation label: " + style.label);
            assertTrue(style.r >= 0f && style.r <= 1f);
            assertTrue(style.g >= 0f && style.g <= 1f);
            assertTrue(style.b >= 0f && style.b <= 1f);
        }
        assertEquals(9, labels.size());
    }

    @Test void stateRoutesAllNineWeaponFamilies() {
        assertStyle(WeaponLegendaryPresentation.Style.VANGUARD, p -> p.legendary.grantVanguardProtocol());
        assertStyle(WeaponLegendaryPresentation.Style.SCATTER, p -> p.legendary.grantScatterMaelstrom());
        assertStyle(WeaponLegendaryPresentation.Style.RAIL, p -> p.legendary.grantRailPhaseLance());
        assertStyle(WeaponLegendaryPresentation.Style.INFERNO, p -> p.legendary.grantInfernoPyroclasm());
        assertStyle(WeaponLegendaryPresentation.Style.CRYO, p -> p.legendary.grantCryoPrism());
        assertStyle(WeaponLegendaryPresentation.Style.ARC, p -> p.legendary.grantArcOverload());
        assertStyle(WeaponLegendaryPresentation.Style.BREACHER, p -> p.legendary.grantBreacherRupture());
        assertStyle(WeaponLegendaryPresentation.Style.ION, p -> p.legendary.grantIonCascade());
        assertStyle(WeaponLegendaryPresentation.Style.CINDER, p -> p.legendary.grantCinderFurnace());
    }

    @Test void genericLegendaryDoesNotPretendToBeWeaponFamilyPerk() {
        Player p = new Player(0f, 0f);
        p.legendary.grantOverdrive();
        assertEquals(WeaponLegendaryPresentation.Style.NONE, WeaponLegendaryPresentation.style(p));
        assertNotEquals("OVERDRIVE", WeaponLegendaryPresentation.Style.VANGUARD.label);
    }

    private static void assertStyle(WeaponLegendaryPresentation.Style expected, Grant grant) {
        Player p = new Player(0f, 0f);
        grant.apply(p);
        assertEquals(expected, WeaponLegendaryPresentation.style(p));
    }

    private interface Grant { void apply(Player player); }
}
