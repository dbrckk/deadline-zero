package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Maps the compact legacy bootstrap art sheet to the production naming contract.
 * Final atlas regions and the generated bootstrap textures take priority.
 */
public final class BootstrapArtCatalog {
    static final int TILE = 64;
    static final int SHEET_COLUMNS = 4;

    private BootstrapArtCatalog() { }

    public static boolean supports(String key) { return tileIndex(key) >= 0; }

    public static TextureRegion region(Texture texture, String key) {
        int tile = tileIndex(key);
        if (texture == null || tile < 0) return null;
        int x = (tile % SHEET_COLUMNS) * TILE;
        int y = (tile / SHEET_COLUMNS) * TILE;
        if (x + TILE > texture.getWidth() || y + TILE > texture.getHeight()) return null;
        return new TextureRegion(texture, x, y, TILE, TILE);
    }

    /** Returns a 4x4 sheet tile index, or -1 when the key is outside the bootstrap contract. */
    static int tileIndex(String key) {
        if (key == null || key.isBlank()) return -1;

        if (key.startsWith("survivor/rex/")) return 0;
        if (key.startsWith("survivor/nyx/")) return 1;
        if (key.startsWith("survivor/bastion/")) return 2;
        if (key.startsWith("survivor/volt/")) return 3;
        if (key.startsWith("survivor/wraith/")) return 10;

        if (key.startsWith("enemy/shambler/")) return 4;
        if (key.startsWith("enemy/runner/")) return 5;
        if (key.startsWith("enemy/brute/")) return 6;
        if (key.startsWith("enemy/ranged/")) return 7;
        if (key.startsWith("enemy/elite/")) return 8;
        if (key.startsWith("enemy/shielded/")) return 6;
        if (key.startsWith("enemy/regenerator/")) return 4;
        if (key.startsWith("enemy/phantom/")) return 10;
        if (key.startsWith("enemy/boss/")) return 9;
        if (key.startsWith("boss/alpha/")) return 9;
        if (key.startsWith("boss/revenant/")) return 10;
        if (key.startsWith("boss/warden/")) return 6;
        if (key.startsWith("boss/harvester/")) return 12;

        if (key.equals("weapon/ar9")) return 11;
        if (key.equals("weapon/scattergun")) return 12;
        if (key.equals("weapon/rail_rifle")) return 13;
        if (key.equals("weapon/inferno_smg")) return 12;
        if (key.equals("weapon/cryo_lance")) return 13;
        if (key.equals("weapon/arc_carbine")) return 14;
        if (key.equals("weapon/breacher")) return 11;
        if (key.equals("weapon/ion_needle")) return 14;
        if (key.equals("weapon/cinder_cannon")) return 12;

        if (key.equals("fx/muzzle_fire") || key.equals("fx/impact_fire") || key.equals("fx/boss_explosion")) return 12;
        if (key.equals("fx/impact_frost") || key.equals("fx/dash") || key.equals("fx/impact_energy")) return 13;
        if (key.equals("fx/impact_shock") || key.equals("fx/level_up")) return 14;
        if (key.equals("fx/impact_kill") || key.equals("fx/legendary_overdrive")
            || key.equals("fx/legendary_singularity") || key.equals("fx/legendary_apex")) return 10;

        if (key.equals("environment/decal/crack_a")) return 7;
        if (key.equals("environment/decal/blood_a")) return 9;
        if (key.equals("environment/prop/barrier_a")) return 15;
        return -1;
    }
}
