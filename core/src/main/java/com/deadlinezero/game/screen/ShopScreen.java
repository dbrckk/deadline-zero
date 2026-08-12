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
import com.deadlinezero.game.meta.EquipmentDropTable;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.services.AdsService;

/** Functional economy shell with soft, premium and daily rewarded supply crates. */
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
        shapes.rect(w * .05f, h * .31f, w * .27f, h * .35f);
        shapes.rect(w * .365f, h * .31f, w * .27f, h * .35f);
        shapes.rect(w * .68f, h * .31f, w * .27f, h * .35f);
        shapes.setColor(.10f, .65f, .82f, 1f); shapes.rect(w * .075f, h * .35f, w * .22f, 54f);
        shapes.setColor(.55f, .25f, .95f, 1f); shapes.rect(w * .39f, h * .35f, w * .22f, 54f);
        shapes.setColor(.16f, .78f, .48f, 1f); shapes.rect(w * .705f, h * .35f, w * .22f, 54f);
        shapes.end();

        PlayerProfile p = game.profile;
        batch.begin();
        font.getData().setScale(1.25f); font.setColor(Color.WHITE);
        font.draw(batch, "SUPPLY SHOP", 0, h * .84f, w, Align.center, false);
        font.getData().setScale(.65f);
        font.setColor(Color.GOLD); font.draw(batch, "CREDITS " + p.currency(PlayerProfile.Currency.CREDITS), 24, h - 32);
        font.setColor(Color.CYAN); font.draw(batch, "GEMS " + p.currency(PlayerProfile.Currency.GEMS), w - 180, h - 32);

        font.setColor(Color.WHITE);
        font.draw(batch, "FIELD", w * .05f, h * .58f, w * .27f, Align.center, false);
        font.draw(batch, "ELITE", w * .365f, h * .58f, w * .27f, Align.center, false);
        font.draw(batch, "DAILY", w * .68f, h * .58f, w * .27f, Align.center, false);
        font.getData().setScale(.44f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Standard roll\n" + ChestService.CREDIT_CHEST_COST + " credits", w * .065f, h * .50f, w * .24f, Align.center, true);
        font.draw(batch, "Best of 3\n" + ChestService.GEM_CHEST_COST + " gems", w * .38f, h * .50f, w * .24f, Align.center, true);
        String daily = p.daily.rewardedChestClaimed ? "Claimed today" : "Watch rewarded ad\n1 free roll";
        font.draw(batch, daily, w * .695f, h * .50f, w * .24f, Align.center, true);
        font.setColor(Color.WHITE);
        font.draw(batch, "[1] OPEN", w * .075f, h * .35f + 34f, w * .22f, Align.center, false);
        font.draw(batch, "[2] OPEN", w * .39f, h * .35f + 34f, w * .22f, Align.center, false);
        font.draw(batch, "[3] FREE", w * .705f, h * .35f + 34f, w * .22f, Align.center, false);
        font.setColor(Color.GRAY); font.draw(batch, status, 0, h * .20f, w, Align.center, false);
        font.draw(batch, "ESC • BACK TO BASE", 0, 42, w, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) open(false);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) open(true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) openRewarded();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.showMenu();
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (y >= h * .31f && y <= h * .66f) {
                if (x < w * .34f) open(false);
                else if (x < w * .66f) open(true);
                else openRewarded();
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

    private void openRewarded() {
        if (game.profile.daily.rewardedChestClaimed) { status = "Daily crate already claimed"; return; }
        if (game.profile.inventory.full()) { status = "Inventory full"; return; }
        status = "Loading rewarded supply...";
        game.services.ads.showRewarded(AdsService.Reward.BONUS_CHEST, () -> {
            if (game.profile.daily.rewardedChestClaimed || game.profile.inventory.full()) return;
            EquipmentItem item = EquipmentDropTable.roll(game.profile.selectedStage, false);
            game.profile.inventory.add(item);
            game.profile.daily.rewardedChestClaimed = true;
            status = "Free crate: " + item.name + " Lv." + item.level;
            game.saveProfile();
            game.services.ads.preload();
        }, () -> status = "Rewarded ad unavailable");
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
