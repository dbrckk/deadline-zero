package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;
import com.deadlinezero.game.entities.Projectile;

/**
 * Pure presentation routing for player projectiles.
 *
 * The combat simulation owns damage/cadence. This class only gives each weapon family a recognizable
 * phone-scale silhouette: trail length/width, core scale, impact scale and an optional accent style.
 */
public final class PlayerProjectilePresentation {
    public enum Style {
        VANGUARD,
        SCATTER,
        RAIL,
        INFERNO,
        CRYO,
        ARC,
        BREACHER,
        ION,
        CINDER,
        TEMPEST,
        WHITEOUT,
        PHOENIX
    }

    public record Profile(Style style, Color color, Color accent,
                          float trailLength, float trailWidth, float alpha,
                          float coreScale, float impactScale, boolean signature) { }

    private static final Color PALE_CYAN = new Color(.78f, .96f, 1f, 1f);
    private static final Color ICE = new Color(.52f, .90f, 1f, 1f);
    private static final Color HOT = new Color(1f, .36f, .06f, 1f);
    private static final Color EMBER = new Color(1f, .74f, .20f, 1f);
    private static final Color ARC = new Color(.55f, .42f, 1f, 1f);
    private static final Color BREACH = new Color(1f, .30f, .12f, 1f);

    private PlayerProjectilePresentation() { }

    public static Profile profile(Projectile projectile) {
        if (projectile == null) return forWeapon("ar9", DamageElement.KINETIC, false, WeaponSignatureRuntime.Kind.NONE);
        return forWeapon(WeaponSignatureRuntime.weaponId(), projectile.element, projectile.critical,
            projectile.weaponSignatureKind);
    }

    static Profile forWeapon(String weaponId, DamageElement element, boolean critical,
                             WeaponSignatureRuntime.Kind signatureKind) {
        String id = weaponId == null ? "ar9" : weaponId.toLowerCase(java.util.Locale.ROOT);
        Style style;
        Color color;
        Color accent;
        float length;
        float width;
        float alpha;
        float core;
        float impact;

        switch (id) {
            case "scattergun" -> {
                style = Style.SCATTER; color = VisualTheme.GOLD; accent = EMBER;
                length = .38f; width = .095f; alpha = .58f; core = 1.10f; impact = 1.12f;
            }
            case "rail_rifle" -> {
                style = Style.RAIL; color = PALE_CYAN; accent = Color.WHITE;
                length = 1.48f; width = .036f; alpha = .90f; core = .84f; impact = 1.26f;
            }
            case "inferno_smg" -> {
                style = Style.INFERNO; color = Color.ORANGE; accent = HOT;
                length = .68f; width = .070f; alpha = .72f; core = 1.00f; impact = 1.08f;
            }
            case "cryo_lance" -> {
                style = Style.CRYO; color = ICE; accent = Color.WHITE;
                length = .86f; width = .076f; alpha = .76f; core = 1.12f; impact = 1.16f;
            }
            case "arc_carbine" -> {
                style = Style.ARC; color = VisualTheme.CYAN; accent = ARC;
                length = .90f; width = .054f; alpha = .78f; core = .96f; impact = 1.12f;
            }
            case "breacher" -> {
                style = Style.BREACHER; color = BREACH; accent = VisualTheme.GOLD;
                length = .48f; width = .125f; alpha = .68f; core = 1.18f; impact = 1.22f;
            }
            case "ion_needle" -> {
                style = Style.ION; color = VisualTheme.CYAN; accent = ARC;
                length = 1.18f; width = .038f; alpha = .84f; core = .84f; impact = 1.10f;
            }
            case "cinder_cannon" -> {
                style = Style.CINDER; color = Color.ORANGE; accent = HOT;
                length = .86f; width = .145f; alpha = .86f; core = 1.28f; impact = 1.34f;
            }
            case "tempest_burst" -> {
                style = Style.TEMPEST; color = VisualTheme.CYAN; accent = VisualTheme.VIOLET;
                length = .92f; width = .052f; alpha = .80f; core = .96f; impact = 1.12f;
            }
            case "whiteout_shard" -> {
                style = Style.WHITEOUT; color = ICE; accent = PALE_CYAN;
                length = .76f; width = .084f; alpha = .78f; core = 1.10f; impact = 1.16f;
            }
            case "phoenix_repeater" -> {
                style = Style.PHOENIX; color = EMBER; accent = HOT;
                length = .88f; width = .064f; alpha = .84f; core = 1.00f; impact = 1.14f;
            }
            default -> {
                style = Style.VANGUARD;
                color = switch (element == null ? DamageElement.KINETIC : element) {
                    case FIRE -> Color.ORANGE;
                    case FROST -> ICE;
                    case SHOCK -> VisualTheme.VIOLET;
                    default -> critical ? VisualTheme.GOLD : VisualTheme.CYAN;
                };
                accent = PALE_CYAN;
                length = .58f; width = .055f; alpha = .50f; core = 1f; impact = 1f;
            }
        }

        WeaponSignatureRuntime.Kind sig = signatureKind == null ? WeaponSignatureRuntime.Kind.NONE : signatureKind;
        boolean signature = sig != WeaponSignatureRuntime.Kind.NONE;
        switch (sig) {
            case ION_OVERCHARGE -> {
                style = Style.ION; color = VisualTheme.CYAN; accent = ARC;
                length = 1.46f; width = .105f; alpha = .94f; core = 1.32f; impact = 1.45f;
            }
            case CINDER_OVERHEAT -> {
                style = Style.CINDER; color = Color.ORANGE; accent = HOT;
                length = 1.24f; width = .150f; alpha = .92f; core = 1.42f; impact = 1.55f;
            }
            case TEMPEST_SURGE -> {
                style = Style.TEMPEST; color = PALE_CYAN; accent = VisualTheme.VIOLET;
                length = 1.42f; width = .078f; alpha = .94f; core = 1.24f; impact = 1.42f;
            }
            case WHITEOUT_SHATTER -> {
                style = Style.WHITEOUT; color = PALE_CYAN; accent = Color.WHITE;
                length = 1.08f; width = .125f; alpha = .92f; core = 1.42f; impact = 1.48f;
            }
            case PHOENIX_IGNITION -> {
                style = Style.PHOENIX; color = VisualTheme.GOLD; accent = HOT;
                length = 1.20f; width = .096f; alpha = .94f; core = 1.30f; impact = 1.46f;
            }
            case NONE -> { }
        }

        if (critical) {
            alpha = Math.min(1f, alpha + .08f);
            core *= 1.08f;
            impact *= 1.10f;
        }
        return new Profile(style, color, accent, length, width, alpha, core, impact, signature);
    }
}
