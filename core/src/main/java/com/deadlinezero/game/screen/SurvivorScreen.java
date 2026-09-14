package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.visual.GameArt;
import com.deadlinezero.game.visual.VisualTheme;

/** Functional survivor roster screen with authored-art support and procedural fallback. */
public final class SurvivorScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private int index;
    private String status = "";
    private float artTime;

    public SurvivorScreen(DeadlineZeroGame game) {
        this.game = game;
        SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
        for (int i = 0; i < values.length; i++) if (values[i] == game.profile.selectedSurvivor) index = i;
    }

    @Override public void render(float delta) {
        artTime += Math.max(0f, delta);
        handleInput();
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.values()[index];
        boolean unlocked = game.profile.survivors.unlocked(survivor);
        int level = game.profile.survivors.level(survivor);
        long xp = game.profile.survivors.xp(survivor);
        long next = game.profile.survivors.xpForNext(survivor);
        float progress = next <= 0 ? 0f : Math.min(1f, xp / (float)next);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(w * .10f, h * .18f, w * .80f, h * .62f);
        shapes.setColor(VisualTheme.PANEL_ALT); shapes.rect(w * .15f, h * .36f, w * .70f, h * .29f);
        shapes.setColor(unlocked ? VisualTheme.CYAN : VisualTheme.RED); shapes.rect(w * .31f, h * .235f, w * .38f, 58f);
        shapes.setColor(.04f, .07f, .09f, 1f); shapes.rect(w * .24f, h * .325f, w * .52f, 9f);
        shapes.setColor(VisualTheme.VIOLET); shapes.rect(w * .24f, h * .325f, w * .52f * progress, 9f);
        if (!game.art.authoredAvailable()) {
            shapes.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
            shapes.circle(w * .20f, h * .705f, Math.min(w, h) * .045f, 28);
        }
        shapes.end();

        batch.begin();
        if (game.art.authoredAvailable()) {
            TextureRegion portrait = game.art.survivor(survivor, GameArt.Motion.IDLE, artTime);
            float ph = Math.min(h * .19f, 170f);
            float pw = ph * portrait.getRegionWidth() / (float)Math.max(1, portrait.getRegionHeight());
            batch.setColor(unlocked ? Color.WHITE : new Color(.38f, .42f, .46f, 1f));
            batch.draw(portrait, w * .20f - pw * .5f, h * .62f, pw, ph);
            batch.setColor(Color.WHITE);
        }

        font.getData().setScale(1.18f); font.setColor(VisualTheme.TEXT);
        font.draw(batch, t("survivor.title"), 0, h * .89f, w, Align.center, false);
        font.getData().setScale(.82f); font.setColor(unlocked ? VisualTheme.CYAN : VisualTheme.MUTED);
        font.draw(batch, survivor.displayName.toUpperCase(), 0, h * .70f, w, Align.center, false);
        font.getData().setScale(.52f); font.setColor(VisualTheme.MUTED);
        font.draw(batch, f("survivor.roleLevel", survivor.role.toUpperCase(), level), 0, h * .645f, w, Align.center, false);

        font.setColor(VisualTheme.TEXT);
        font.draw(batch, f("survivor.stats", fmt(survivor.hpMultiplier), fmt(survivor.weaponMultiplier), fmt(survivor.speedMultiplier)), 0, h * .56f, w, Align.center, false);
        font.draw(batch, f("survivor.combatStats", Math.round(survivor.critBonus * 100f), Math.round(survivor.abilityBonus * 100f)), 0, h * .505f, w, Align.center, false);
        font.setColor(VisualTheme.VIOLET);
        font.draw(batch, f("survivor.xp", xp, next), 0, h * .39f, w, Align.center, false);

        font.setColor(unlocked ? Color.WHITE : Color.LIGHT_GRAY);
        font.draw(batch, unlocked ? (game.profile.selectedSurvivor == survivor ? t("survivor.selected") : t("survivor.select")) : unlockText(survivor), w * .31f, h * .235f + 37f, w * .38f, Align.center, false);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, t("survivor.footer"), 0, h * .125f, w, Align.center, false);
        if (!status.isEmpty()) { font.setColor(VisualTheme.CYAN); font.draw(batch, status, 0, h * .08f, w, Align.center, false); }
        batch.end();
    }

    private void handleInput() {
        SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) index = (index - 1 + values.length) % values.length;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) index = (index + 1) % values.length;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) select(values[index]);
        if (Gdx.input.justTouched()) select(values[index]);
    }

    private void select(SurvivorCatalog.Survivor survivor) {
        if (!game.profile.selectSurvivor(survivor)) { status = t("survivor.locked"); return; }
        status = f("survivor.selectedStatus", survivor.displayName);
        game.saveProfile();
    }

    private String unlockText(SurvivorCatalog.Survivor survivor) {
        return switch (survivor) {
            case REX -> t("survivor.default");
            case NYX -> t("survivor.unlockNyx");
            case BASTION -> t("survivor.unlockBastion");
            case VOLT -> t("survivor.unlockVolt");
            case WRAITH -> t("survivor.unlockWraith");
        };
    }

    private String fmt(float value) { return String.format("%.2f", value); }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
