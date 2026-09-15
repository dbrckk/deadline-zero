package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.progression.UpgradeRarity;

/** Centralized visual language for UI and combat. Keeps colors consistent and art-swappable. */
public final class VisualTheme {
    public static final Color BG = new Color(.008f, .013f, .021f, 1f);
    public static final Color PANEL = new Color(.025f, .041f, .060f, .96f);
    public static final Color PANEL_ALT = new Color(.035f, .061f, .082f, .96f);
    public static final Color CYAN = new Color(.08f, .80f, 1f, 1f);
    public static final Color CYAN_SOFT = new Color(.18f, .63f, .78f, 1f);
    public static final Color GOLD = new Color(1f, .72f, .16f, 1f);
    public static final Color RED = new Color(1f, .16f, .12f, 1f);
    public static final Color GREEN = new Color(.20f, 1f, .58f, 1f);
    public static final Color VIOLET = new Color(.70f, .27f, 1f, 1f);
    public static final Color TEXT = new Color(.94f, .97f, 1f, 1f);
    public static final Color MUTED = new Color(.53f, .62f, .70f, 1f);
    public static final Color DIVIDER = new Color(.12f, .22f, .29f, .8f);

    private VisualTheme() {}

    public static Color semantic(Color standard, Color deuteranopia, Color protanopia, Color tritanopia) {
        AccessibilitySettings.ColorVisionMode mode = AccessibilitySettings.active().colorVisionMode;
        return switch (mode) {
            case DEUTERANOPIA -> deuteranopia;
            case PROTANOPIA -> protanopia;
            case TRITANOPIA -> tritanopia;
            default -> standard;
        };
    }

    public static Color danger() {
        return semantic(RED, new Color(1f, .55f, .08f, 1f), new Color(1f, .62f, .10f, 1f), new Color(.92f, .18f, .58f, 1f));
    }

    public static Color positive() {
        return semantic(GREEN, new Color(.10f, .72f, 1f, 1f), new Color(.08f, .68f, 1f, 1f), new Color(.20f, .88f, .55f, 1f));
    }

    public static Color accent() {
        return semantic(CYAN, new Color(.12f, .78f, 1f, 1f), new Color(.14f, .76f, 1f, 1f), new Color(.18f, .92f, .62f, 1f));
    }

    public static Color equipmentRarity(EquipmentItem.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> new Color(.72f, .76f, .80f, 1f);
            case RARE -> semantic(new Color(.14f, .65f, 1f, 1f), new Color(.12f, .72f, 1f, 1f), new Color(.12f, .72f, 1f, 1f), new Color(.12f, .82f, .58f, 1f));
            case EPIC -> semantic(new Color(.66f, .30f, 1f, 1f), new Color(.86f, .38f, .88f, 1f), new Color(.82f, .38f, .92f, 1f), new Color(.88f, .34f, .72f, 1f));
            case LEGENDARY -> semantic(new Color(1f, .63f, .08f, 1f), new Color(1f, .68f, .08f, 1f), new Color(1f, .70f, .10f, 1f), new Color(.95f, .55f, .18f, 1f));
            case MYTHIC -> semantic(new Color(1f, .18f, .48f, 1f), new Color(.98f, .32f, .68f, 1f), new Color(.96f, .34f, .72f, 1f), new Color(.72f, .34f, 1f, 1f));
        };
    }

    public static Color upgradeRarity(UpgradeRarity rarity) {
        return switch (rarity) {
            case COMMON -> new Color(.74f, .79f, .84f, 1f);
            case RARE -> new Color(.12f, .68f, 1f, 1f);
            case EPIC -> new Color(.67f, .30f, 1f, 1f);
            case LEGENDARY -> new Color(1f, .68f, .08f, 1f);
        };
    }
}
