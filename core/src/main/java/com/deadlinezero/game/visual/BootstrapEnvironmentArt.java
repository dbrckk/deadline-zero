package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/** Compact deterministic environment art generated once at startup. */
public final class BootstrapEnvironmentArt implements Disposable {
    static final int TILE = 64;
    static final int COLUMNS = 4;
    static final String[] KEYS = {
        "environment/floor/concrete_a",
        "environment/floor/concrete_b",
        "environment/floor/concrete_c",
        "environment/floor/hazard_a",
        "environment/decal/crack_a",
        "environment/decal/blood_a",
        "environment/decal/scorch_a",
        "environment/prop/barrier_a",
        "environment/prop/debris_a",
        "environment/prop/debris_b",
        "environment/prop/wall_a",
        "environment/prop/wall_b",
        "environment/prop/crate_a",
        "environment/prop/beacon_a"
    };

    private final Texture texture;
    private final TextureRegion[] regions;

    private BootstrapEnvironmentArt(Texture texture) {
        this.texture = texture;
        this.regions = new TextureRegion[KEYS.length];
        for (int i = 0; i < KEYS.length; i++) {
            int x = (i % COLUMNS) * TILE;
            int y = (i / COLUMNS) * TILE;
            regions[i] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static BootstrapEnvironmentArt create() {
        int rows = (KEYS.length + COLUMNS - 1) / COLUMNS;
        Pixmap p = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        try {
            for (int i = 0; i < KEYS.length; i++) drawTile(p, i);
            Texture texture = new Texture(p);
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return new BootstrapEnvironmentArt(texture);
        } finally {
            p.dispose();
        }
    }

    public boolean supports(String key) { return indexOf(key) >= 0; }

    public TextureRegion region(String key) {
        int index = indexOf(key);
        return index < 0 ? null : regions[index];
    }

    static int indexOf(String key) {
        if (key == null) return -1;
        for (int i = 0; i < KEYS.length; i++) if (KEYS[i].equals(key)) return i;
        return -1;
    }

    private static void drawTile(Pixmap p, int tile) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        switch (tile) {
            case 0, 1, 2 -> drawConcrete(p, ox, oy, tile);
            case 3 -> drawHazard(p, ox, oy);
            case 4 -> drawCrack(p, ox, oy);
            case 5 -> drawBlood(p, ox, oy);
            case 6 -> drawScorch(p, ox, oy);
            case 7 -> drawBarrier(p, ox, oy);
            case 8, 9 -> drawDebris(p, ox, oy, tile - 8);
            case 10, 11 -> drawWall(p, ox, oy, tile - 10);
            case 12 -> drawCrate(p, ox, oy);
            case 13 -> drawBeacon(p, ox, oy);
            default -> { }
        }
    }

    private static void drawConcrete(Pixmap p, int ox, int oy, int variant) {
        float base = .105f + variant * .008f;
        p.setColor(base, base + .018f, base + .028f, 1f);
        p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.15f, .18f, .20f, .70f);
        p.drawRectangle(ox, oy, TILE - 1, TILE - 1);
        p.setColor(.07f, .09f, .11f, .55f);
        p.drawLine(ox + 8 + variant * 5, oy + 18, ox + 25 + variant * 3, oy + 16);
        p.drawLine(ox + 25 + variant * 3, oy + 16, ox + 32, oy + 28 + variant * 2);
        p.setColor(.22f, .25f, .27f, .28f);
        p.fillCircle(ox + 49 - variant * 7, oy + 12 + variant * 9, 2);
        p.fillCircle(ox + 16 + variant * 11, oy + 48 - variant * 5, 1);
    }

    private static void drawHazard(Pixmap p, int ox, int oy) {
        p.setColor(.095f, .105f, .11f, 1f);
        p.fillRectangle(ox, oy, TILE, TILE);
        for (int x = -32; x < 96; x += 16) {
            p.setColor(.72f, .48f, .08f, .82f);
            p.fillTriangle(ox + x, oy + TILE, ox + x + 8, oy + TILE, ox + x + 40, oy);
            p.setColor(.15f, .13f, .08f, .55f);
            p.drawLine(ox + x + 8, oy + TILE, ox + x + 40, oy);
        }
    }

    private static void drawCrack(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.03f, .035f, .04f, .88f);
        p.drawLine(ox + 10, oy + 12, ox + 27, oy + 28);
        p.drawLine(ox + 27, oy + 28, ox + 48, oy + 19);
        p.drawLine(ox + 27, oy + 28, ox + 35, oy + 50);
        p.drawLine(ox + 20, oy + 22, ox + 15, oy + 37);
    }

    private static void drawBlood(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.34f, .025f, .035f, .72f);
        p.fillCircle(ox + 30, oy + 34, 13);
        p.fillCircle(ox + 44, oy + 28, 7);
        p.fillCircle(ox + 18, oy + 44, 5);
        p.setColor(.62f, .045f, .055f, .36f);
        p.fillCircle(ox + 26, oy + 30, 6);
    }

    private static void drawScorch(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.015f, .012f, .010f, .68f);
        p.fillCircle(ox + 32, oy + 33, 18);
        p.setColor(.20f, .075f, .025f, .42f);
        p.drawCircle(ox + 32, oy + 33, 14);
        p.drawCircle(ox + 32, oy + 33, 17);
    }

    private static void drawBarrier(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.055f, .065f, .075f, .72f); p.fillRectangle(ox + 6, oy + 46, 52, 9);
        p.setColor(.20f, .23f, .25f, 1f); p.fillRectangle(ox + 7, oy + 18, 50, 29);
        p.setColor(.08f, .10f, .12f, 1f); p.fillRectangle(ox + 11, oy + 22, 42, 21);
        p.setColor(.88f, .56f, .06f, 1f);
        for (int x = 12; x < 53; x += 12) p.fillRectangle(ox + x, oy + 24, 6, 17);
        p.setColor(.34f, .38f, .40f, 1f); p.drawRectangle(ox + 7, oy + 18, 50, 29);
    }

    private static void drawDebris(Pixmap p, int ox, int oy, int variant) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.12f, .14f, .15f, .92f);
        p.fillRectangle(ox + 12, oy + 35, 19, 9);
        p.fillRectangle(ox + 34, oy + 24 + variant * 5, 16, 8);
        p.setColor(.30f, .33f, .34f, .85f);
        p.drawLine(ox + 8, oy + 49, ox + 27, oy + 21);
        p.drawLine(ox + 30, oy + 52, ox + 52, oy + 37);
        p.setColor(.74f, .46f, .07f, .78f);
        p.fillRectangle(ox + 20 + variant * 10, oy + 19, 4, 15);
    }

    private static void drawWall(Pixmap p, int ox, int oy, int variant) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.055f, .065f, .078f, .98f); p.fillRectangle(ox + 2, oy + 12, 60, 42);
        p.setColor(.16f, .19f, .22f, 1f); p.fillRectangle(ox + 5, oy + 15, 54, 34);
        p.setColor(.08f, .095f, .11f, 1f); p.fillRectangle(ox + 8, oy + 19, 48, 24);
        p.setColor(.31f, .34f, .36f, .86f);
        p.drawRectangle(ox + 5, oy + 15, 54, 34);
        p.drawLine(ox + 32, oy + 16, ox + 32, oy + 48);
        p.setColor(variant == 0 ? .12f : .72f, variant == 0 ? .62f : .22f, variant == 0 ? .74f : .08f, .88f);
        p.fillRectangle(ox + 10, oy + 22, 4, 17);
        p.fillRectangle(ox + 50, oy + 22, 4, 17);
    }

    private static void drawCrate(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.16f, .18f, .19f, .92f); p.fillRectangle(ox + 10, oy + 16, 44, 38);
        p.setColor(.30f, .33f, .34f, 1f); p.drawRectangle(ox + 10, oy + 16, 44, 38);
        p.drawLine(ox + 14, oy + 20, ox + 50, oy + 50);
        p.drawLine(ox + 50, oy + 20, ox + 14, oy + 50);
        p.setColor(.82f, .50f, .06f, .92f); p.fillRectangle(ox + 27, oy + 31, 10, 5);
    }

    private static void drawBeacon(Pixmap p, int ox, int oy) {
        p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
        p.setColor(.07f, .08f, .09f, .95f); p.fillRectangle(ox + 27, oy + 25, 10, 28);
        p.setColor(.24f, .28f, .31f, 1f); p.fillRectangle(ox + 22, oy + 49, 20, 6);
        p.setColor(.10f, .68f, .82f, .90f); p.fillCircle(ox + 32, oy + 20, 8);
        p.setColor(.68f, .95f, 1f, .72f); p.fillCircle(ox + 32, oy + 20, 3);
    }

    @Override public void dispose() { texture.dispose(); }
}
