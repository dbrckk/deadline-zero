package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.SurvivorCatalog;

/** Functional survivor roster screen before final art pass. */
public final class SurvivorScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private int index;
    private String status = "";

    public SurvivorScreen(DeadlineZeroGame game) {
        this.game = game;
        SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
        for (int i = 0; i < values.length; i++) if (values[i] == game.profile.selectedSurvivor) index = i;
    }

    @Override public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.values()[index];
        boolean unlocked = game.profile.survivors.unlocked(survivor);
        int level = game.profile.survivors.level(survivor);
        long xp = game.profile.survivors.xp(survivor);
        long next = game.profile.survivors.xpForNext(survivor);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .05f, .075f, .96f);
        shapes.rect(w * .12f, h * .22f, w * .76f, h * .55f);
        shapes.setColor(unlocked ? .08f : .08f, unlocked ? .55f : .12f, unlocked ? .72f : .15f, 1f);
        shapes.rect(w * .31f, h * .255f, w * .38f, 58f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.25f); font.setColor(Color.WHITE);
        font.draw(batch, "SURVIVORS", 0, h * .88f, w, Align.center, false);
        font.getData().setScale(.88f); font.setColor(unlocked ? Color.CYAN : Color.DARK_GRAY);
        font.draw(batch, survivor.displayName.toUpperCase(), 0, h * .68f, w, Align.center, false);
        font.getData().setScale(.58f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, survivor.role + "   Lv." + level, 0, h * .61f, w, Align.center, false);
        font.draw(batch, "HP x" + fmt(survivor.hpMultiplier) + "   DMG x" + fmt(survivor.weaponMultiplier) + "   SPD x" + fmt(survivor.speedMultiplier), 0, h * .53f, w, Align.center, false);
        font.draw(batch, "Crit +" + Math.round(survivor.critBonus * 100f) + "%   Ability +" + Math.round(survivor.abilityBonus * 100f) + "%", 0, h * .47f, w, Align.center, false);
        font.draw(batch, "XP " + xp + " / " + next, 0, h * .40f, w, Align.center, false);
        font.setColor(unlocked ? Color.WHITE : Color.SCARLET);
        font.draw(batch, unlocked ? (game.profile.selectedSurvivor == survivor ? "SELECTED" : "SELECT [ENTER]") : unlockText(survivor), w * .31f, h * .255f + 37f, w * .38f, Align.center, false);
        font.setColor(Color.GRAY);
        font.draw(batch, "← / → browse    ESC back", 0, h * .15f, w, Align.center, false);
        if (!status.isEmpty()) { font.setColor(Color.CYAN); font.draw(batch, status, 0, h * .10f, w, Align.center, false); }
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
        if (!game.profile.selectSurvivor(survivor)) { status = "Survivor locked"; return; }
        status = survivor.displayName + " selected";
        game.saveProfile();
    }

    private String unlockText(SurvivorCatalog.Survivor survivor) {
        return switch (survivor) {
            case REX -> "DEFAULT";
            case NYX -> "Unlock: Account Lv.3";
            case BASTION -> "Unlock: Reach Stage 3";
            case VOLT -> "Unlock: Reach Stage 5";
            case WRAITH -> "Unlock: Account Lv.8 or Stage 7";
        };
    }

    private String fmt(float value) { return String.format("%.2f", value); }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
