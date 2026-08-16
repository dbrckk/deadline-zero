package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/** Dedicated animated VFX fallback for Null-sector boss pressure and telegraphs. */
public final class NullBootstrapVfxArt implements Disposable {
    static final int TILE = 64;
    static final int COLUMNS = 8;
    static final int FRAMES_PER_EFFECT = 8;
    private static final String[] ROOTS = {
        "fx/null_archon_aura",
        "fx/null_archon_portal",
        "fx/null_archon_fracture"
    };
    static final int TOTAL_TILES = ROOTS.length * FRAMES_PER_EFFECT;

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private NullBootstrapVfxArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < regions.length; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static NullBootstrapVfxArt create() {
        int rows = (TOTAL_TILES + COLUMNS - 1) / COLUMNS;
        Pixmap p = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        p.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int effect = 0; effect < ROOTS.length; effect++) {
                for (int frame = 0; frame < FRAMES_PER_EFFECT; frame++) drawFrame(p, effect, frame, effect * FRAMES_PER_EFFECT + frame);
            }
            Texture texture = new Texture(p);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return new NullBootstrapVfxArt(texture);
        } finally {
            p.dispose();
        }
    }

    public boolean supports(String key) { return firstTile(key) >= 0; }

    public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
        int first = firstTile(key);
        if (first < 0) return null;
        int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = loop ? raw % FRAMES_PER_EFFECT : Math.min(FRAMES_PER_EFFECT - 1, raw);
        return regions[first + frame];
    }

    static int firstTile(String key) {
        if (key == null) return -1;
        for (int i = 0; i < ROOTS.length; i++) if (ROOTS[i].equals(key)) return i * FRAMES_PER_EFFECT;
        return -1;
    }

    private static void drawFrame(Pixmap p, int effect, int frame, int tile) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int cx = ox + TILE / 2;
        int cy = oy + TILE / 2;
        float t = frame / 7f;
        switch (effect) {
            case 0 -> drawAura(p, cx, cy, frame, t);
            case 1 -> drawPortal(p, cx, cy, frame, t);
            case 2 -> drawFracture(p, cx, cy, frame, t);
            default -> { }
        }
    }

    private static void drawAura(Pixmap p, int cx, int cy, int frame, float t) {
        int outer = 20 + (frame % 4) * 2;
        p.setColor(.48f, .34f, 1f, .72f);
        p.drawCircle(cx, cy, outer);
        p.drawCircle(cx, cy, outer - 6);
        p.setColor(.28f, .72f, 1f, .70f);
        int spokes = 8;
        for (int i = 0; i < spokes; i++) {
            double a = i * Math.PI * 2.0 / spokes + t * 1.8;
            int x1 = cx + (int)(Math.cos(a) * 10);
            int y1 = cy + (int)(Math.sin(a) * 10);
            int x2 = cx + (int)(Math.cos(a) * (outer + 6));
            int y2 = cy + (int)(Math.sin(a) * (outer + 6));
            p.drawLine(x1, y1, x2, y2);
        }
        p.setColor(.82f, .72f, 1f, .82f);
        p.fillCircle(cx, cy, 2 + frame % 2);
    }

    private static void drawPortal(Pixmap p, int cx, int cy, int frame, float t) {
        int radius = 8 + Math.round(t * 20f);
        p.setColor(.34f, .22f, .88f, .88f);
        p.drawCircle(cx, cy, radius);
        p.drawCircle(cx, cy, Math.max(3, radius - 7));
        p.setColor(.52f, .86f, 1f, .82f);
        for (int i = 0; i < 10; i++) {
            double a = i * Math.PI / 5.0 - t * 2.4;
            int r1 = Math.max(4, radius - 5);
            int r2 = radius + 6;
            p.drawLine(cx + (int)(Math.cos(a) * r1), cy + (int)(Math.sin(a) * r1),
                cx + (int)(Math.cos(a + .18) * r2), cy + (int)(Math.sin(a + .18) * r2));
        }
        p.setColor(.08f, .06f, .18f, .86f);
        p.fillCircle(cx, cy, Math.max(2, radius - 9));
    }

    private static void drawFracture(Pixmap p, int cx, int cy, int frame, float t) {
        int reach = 9 + Math.round(t * 20f);
        p.setColor(.72f, .58f, 1f, 1f - t * .25f);
        for (int i = 0; i < 6; i++) {
            double a = i * Math.PI / 3.0 + frame * .11;
            int mx = cx + (int)(Math.cos(a) * reach * .45f);
            int my = cy + (int)(Math.sin(a) * reach * .45f);
            int ex = cx + (int)(Math.cos(a) * reach);
            int ey = cy + (int)(Math.sin(a) * reach);
            p.drawLine(cx, cy, mx, my);
            p.drawLine(mx, my, ex, ey);
            double branch = a + (i % 2 == 0 ? .42 : -.42);
            p.drawLine(mx, my, mx + (int)(Math.cos(branch) * reach * .35f), my + (int)(Math.sin(branch) * reach * .35f));
        }
        p.setColor(.28f, .78f, 1f, .85f);
        p.drawCircle(cx, cy, 5 + frame);
    }

    @Override public void dispose() { texture.dispose(); }
}
