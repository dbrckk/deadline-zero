package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Maps the compact bootstrap art sheet to the production naming contract.
 * Final atlas regions always take priority; this catalog only fills missing authored entries.
 */
public final class BootstrapArtCatalog {
    static final int TILE = 64;
    static final int SHEET_COLUMNS = 32;
    static final int FRAMES_PER_DIRECTION = 15;

    private static final int REX_DIRECTIONAL_BASE = 16;
    private static final int SHAMBLER_DIRECTIONAL_BASE = 136;
    private static final int RUNNER_DIRECTIONAL_BASE = 256;

    private BootstrapArtCatalog() { }

    public static boolean supports(String key) { return directionalBase(key) >= 0 || tileIndex(key) >= 0; }

    public static TextureRegion region(Texture texture, String key) {
        int directional = directionalBase(key);
        int tile = directional >= 0 ? directional : tileIndex(key);
        return regionByTile(texture, tile);
    }

    /** Selects an authored bootstrap animation frame when the vertical slice has a directional set. */
    public static TextureRegion animatedRegion(Texture texture, String key, float stateTime, float frameDuration, boolean loop) {
        int base = directionalBase(key);
        if (base < 0) return region(texture, key);
        int count = directionalFrameCount(key);
        if (count <= 1) return regionByTile(texture, base);
        int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
        return regionByTile(texture, base + frame);
    }

    private static TextureRegion regionByTile(Texture texture, int tile) {
        if (texture == null || tile < 0) return null;
        int x = (tile % SHEET_COLUMNS) * TILE;
        int y = (tile / SHEET_COLUMNS) * TILE;
        return new TextureRegion(texture, x, y, TILE, TILE);
    }

    /** First frame tile for REX, Shambler and Runner eight-way animation sets. */
    static int directionalBase(String key) {
        if (key == null || key.isBlank()) return -1;
        int actorBase;
        String rest;
        if (key.startsWith("survivor/rex/")) {
            actorBase = REX_DIRECTIONAL_BASE;
            rest = key.substring("survivor/rex/".length());
        } else if (key.startsWith("enemy/shambler/")) {
            actorBase = SHAMBLER_DIRECTIONAL_BASE;
            rest = key.substring("enemy/shambler/".length());
        } else if (key.startsWith("enemy/runner/")) {
            actorBase = RUNNER_DIRECTIONAL_BASE;
            rest = key.substring("enemy/runner/".length());
        } else {
            return -1;
        }

        int slash = rest.indexOf('/');
        if (slash <= 0 || slash == rest.length() - 1) return -1;
        int direction = directionIndex(rest.substring(0, slash));
        if (direction < 0) return -1;
        int motionOffset = motionOffset(rest.substring(slash + 1));
        if (motionOffset < 0) return -1;
        return actorBase + direction * FRAMES_PER_DIRECTION + motionOffset;
    }

    static int directionalFrameCount(String key) {
        if (directionalBase(key) < 0) return 0;
        int slash = key.lastIndexOf('/');
        if (slash < 0 || slash == key.length() - 1) return 0;
        return switch (key.substring(slash + 1)) {
            case "idle" -> 2;
            case "run" -> 4;
            case "attack" -> 3;
            case "hit" -> 2;
            case "death" -> 4;
            default -> 0;
        };
    }

    private static int directionIndex(String token) {
        return switch (token) {
            case "n" -> 0;
            case "ne" -> 1;
            case "e" -> 2;
            case "se" -> 3;
            case "s" -> 4;
            case "sw" -> 5;
            case "w" -> 6;
            case "nw" -> 7;
            default -> -1;
        };
    }

    private static int motionOffset(String motion) {
        return switch (motion) {
            case "idle" -> 0;
            case "run" -> 2;
            case "attack" -> 6;
            case "hit" -> 9;
            case "death" -> 11;
            default -> -1;
        };
    }

    /** Returns a bootstrap tile index, or -1 when the key is outside the fallback contract. */
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

        if (key.equals("weapon/ar9")) return 11;
        if (key.equals("weapon/scattergun")) return 12;
        if (key.equals("weapon/rail_rifle")) return 13;
        if (key.equals("weapon/inferno_smg")) return 12;
        if (key.equals("weapon/cryo_lance")) return 13;
        if (key.equals("weapon/arc_carbine")) return 14;
        if (key.equals("weapon/breacher")) return 11;

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
