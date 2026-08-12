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
import com.deadlinezero.game.meta.ChestService;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.PlayerProfile;

/** Functional economy shell. IAP packs will be layered on top of this screen later. */
public final class ShopScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private String status = "Choose a supply crate";

    public ShopScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .05f, .075f, .95f);
        shapes.rect(w * .08f, h * .30f, w * .36f, h * .36f);
        shapes.rect(w * .56f, h * .30f, w * .36f, h * .36f);
        shapes.setColor(.10f, .65f, .82f, 1f);
        shapes.rect(w * .12f, h * .34f, w * .28f, 54f);
        shapes.setColor(.55f, .25f, .95f, 1f);
        shapes.rect(w * .60f, h * .34f, w * .28f, 54f);
        shapes.end();

        PlayerProfile p = game.profile;
        batch.begin();
        font.getData().setScale(1.25f); font.setColor(Color.WHITE);
        font.draw(batch, "SUPPLY SHOP", 0, h * .84f, w, Align.center, false);
        font.getData().setScale(.65f);
        font.setColor(Color.GOLD); font.draw(batch, "CREDITS " + p.currency(PlayerProfile.Currency.CREDITS), 24, h - 32);
        font.setColor(Color.CYAN); font.draw(batch, "GEMS " + p.currency(PlayerProfile.Currency.GEMS), w - 180, h - 32);

        font.setColor(Color.WHITE);
        font.draw(batch, "FIELD CRATE", w * .08f, h * .58f, w * .36f, Align.center, false);
        font.draw(batch, "ELITE CRATE", w * .56f, h * .58f, w * .36f, Align.center, false);
        font.getData().setScale(.48f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Standard equipment roll\n" + ChestService.CREDIT_CHEST_COST + " credits", w * .10f, h * .50f, w * .32f, Align.center, true);
        font.draw(batch, "Best of 3 boss-tier rolls\n" + ChestService.GEM_CHEST_COST + " gems", w * .58f, h * .50f, w * .32f, Align.center, true);
        font.setColor(Color.WHITE);
        font.draw(batch, "[1] OPEN", w * .12f, h * .34f + 34f, w * .28f, Align.center, false);
        font.draw(batch, "[2] OPEN", w * .60f, h * .34f + 34f, w * .28f, Align.center, false);
        font.setColor(Color.GRAY); font.draw(batch, status, 0, h * .20f, w, Align.center, false);
        font.draw(batch, "ESC • BACK TO BASE", 0, 42, w, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) open(false);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) open(true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.showMenu();
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (y >= h * .30f && y <= h * .66f) {
                if (x < w * .5f) open(false); else open(true);
            }
        }
    }

    private void open(boolean premium) {
        EquipmentItem item = premium ? ChestService.openGemChest(game.profile) : ChestService.openCreditChest(game.profile);
        if (item == null) {
            status = game.profile.inventory.full() ? "Inventory full" : "Not enough currency";
            return;
        }
        status = "Obtained: " + item.name + " Lv." + item.level;
        game.saveProfile();
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
