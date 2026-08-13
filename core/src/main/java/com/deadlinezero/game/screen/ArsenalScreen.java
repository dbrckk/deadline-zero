package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.WeaponProgression;
import com.deadlinezero.game.visual.VisualTheme;

/** Production-shaped weapon selection screen with persistent unlock-aware loadout choice. */
public final class ArsenalScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private int focus;

    public ArsenalScreen(DeadlineZeroGame game) {
        this.game = game;
        WeaponDefinition selected = WeaponCatalog.byId(game.profile.selectedWeaponId);
        WeaponDefinition[] all = WeaponCatalog.all();
        for (int i = 0; i < all.length; i++) if (all[i].id.equals(selected.id)) focus = i;
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        WeaponDefinition[] all = WeaponCatalog.all();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(18, h - 88, w - 36, 58);
        float cardW = (w - 56f) / 2f;
        float cardH = Math.min(116f, (h - 180f) / 4f);
        float top = h - 126f;
        for (int i = 0; i < all.length; i++) {
            int row = i / 2, col = i % 2;
            float x = 20f + col * (cardW + 16f);
            float y = top - row * (cardH + 10f) - cardH;
            boolean selected = all[i].id.equals(game.profile.selectedWeaponId);
            boolean unlocked = WeaponProgression.unlocked(game.profile, all[i]);
            if (selected) shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .20f);
            else if (i == focus) shapes.setColor(VisualTheme.PANEL_ALT);
            else shapes.setColor(VisualTheme.PANEL);
            shapes.rect(x, y, cardW, cardH);
            if (selected) { shapes.setColor(VisualTheme.CYAN); shapes.rect(x, y, 4f, cardH); }
            if (!unlocked) { shapes.setColor(0f, 0f, 0f, .38f); shapes.rect(x, y, cardW, cardH); }
        }
        shapes.end();

        batch.begin();
        font.getData().setScale(1.2f); font.setColor(VisualTheme.TEXT);
        font.draw(batch, "ARSENAL", 28, h - 48);
        font.getData().setScale(.48f); font.setColor(VisualTheme.MUTED);
        font.draw(batch, "SELECT YOUR STARTING WEAPON  •  FREE PROGRESSION UNLOCKS", 28, h - 70);

        for (int i = 0; i < all.length; i++) drawCard(all[i], i, cardW, cardH, top, w, h);

        WeaponDefinition weapon = all[MathUtils.clamp(focus, 0, all.length - 1)];
        float dps = weapon.damage * weapon.projectileCount / Math.max(.05f, weapon.fireInterval);
        font.getData().setScale(.48f); font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, "DPS " + Math.round(dps) + "   FIRE " + String.format(java.util.Locale.US, "%.2fs", weapon.fireInterval)
            + "   CRIT " + Math.round(weapon.critChance * 100f) + "%   PEN " + weapon.penetration
            + "   ELEMENT " + weapon.element.name(), 24, 48);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "A/D OR ←/→  •  ENTER TO SELECT  •  ESC/BACK TO BASE", 24, 26);
        batch.end();

        handleInput(w, h, all, cardW, cardH, top);
    }

    private void drawCard(WeaponDefinition weapon, int i, float cardW, float cardH, float top, float w, float h) {
        int row = i / 2, col = i % 2;
        float x = 20f + col * (cardW + 16f);
        float y = top - row * (cardH + 10f) - cardH;
        boolean selected = weapon.id.equals(game.profile.selectedWeaponId);
        boolean unlocked = WeaponProgression.unlocked(game.profile, weapon);
        float dps = weapon.damage * weapon.projectileCount / Math.max(.05f, weapon.fireInterval);

        font.getData().setScale(.58f);
        font.setColor(unlocked ? VisualTheme.TEXT : VisualTheme.MUTED);
        font.draw(batch, weapon.displayName.toUpperCase(), x + 14f, y + cardH - 18f);
        font.getData().setScale(.43f);
        font.setColor(elementColor(weapon));
        font.draw(batch, weapon.element.name() + "  •  " + Math.round(dps) + " DPS", x + 14f, y + cardH - 42f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, Math.round(weapon.damage) + " DMG   " + weapon.projectileCount + "x SHOT   "
            + Math.round(weapon.knockback * 10f) / 10f + " KB", x + 14f, y + cardH - 64f);
        if (selected) {
            font.setColor(VisualTheme.CYAN); font.draw(batch, "EQUIPPED", x + 14f, y + 18f);
        } else if (unlocked) {
            font.setColor(i == focus ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
            font.draw(batch, i == focus ? "SELECT" : "AVAILABLE", x + 14f, y + 18f);
        } else {
            font.setColor(VisualTheme.GOLD);
            int level = WeaponProgression.unlockAccountLevel(weapon);
            font.draw(batch, "LOCKED  •  ACCOUNT LV " + level, x + 14f, y + 18f);
        }
    }

    private Color elementColor(WeaponDefinition weapon) {
        return switch (weapon.element) {
            case FIRE -> Color.ORANGE;
            case FROST -> VisualTheme.CYAN;
            case SHOCK -> VisualTheme.VIOLET;
            default -> VisualTheme.CYAN_SOFT;
        };
    }

    private void handleInput(float w, float h, WeaponDefinition[] all, float cardW, float cardH, float top) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) focus = Math.max(0, focus - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) focus = Math.min(all.length - 1, focus + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) focus = Math.max(0, focus - 2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) focus = Math.min(all.length - 1, focus + 2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) select(all[focus]);

        if (!Gdx.input.justTouched()) return;
        float tx = Gdx.input.getX(), ty = h - Gdx.input.getY();
        for (int i = 0; i < all.length; i++) {
            int row = i / 2, col = i % 2;
            float x = 20f + col * (cardW + 16f);
            float y = top - row * (cardH + 10f) - cardH;
            if (tx >= x && tx <= x + cardW && ty >= y && ty <= y + cardH) {
                focus = i;
                select(all[i]);
                return;
            }
        }
    }

    private void select(WeaponDefinition weapon) {
        if (!WeaponProgression.unlocked(game.profile, weapon)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            return;
        }
        if (game.profile.selectWeapon(weapon)) {
            ProfileStore.save(game.profile);
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
        }
    }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
