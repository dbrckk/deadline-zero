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
import com.deadlinezero.game.meta.ConsumablePurchaseDelivery;
import com.deadlinezero.game.meta.EquipmentDropTable;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.PurchaseGrantService;
import com.deadlinezero.game.services.AdsService;
import com.deadlinezero.game.services.BillingService;

/** Functional economy shell with soft, premium, rewarded and Play Billing offers. */
public final class ShopScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private String status = "Choose a supply crate";
    private boolean consumableRestoreRequested;

    public ShopScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        if (PurchaseGrantService.syncPermanent(game.profile, game.services.billing)) game.saveProfile();
        if (!consumableRestoreRequested) {
            consumableRestoreRequested = true;
            game.services.billing.restoreConsumables(this::deliverConsumable);
        }
        syncBillingStatus();

        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .05f, .075f, .95f);
        shapes.rect(w * .05f, h * .36f, w * .27f, h * .30f);
        shapes.rect(w * .365f, h * .36f, w * .27f, h * .30f);
        shapes.rect(w * .68f, h * .36f, w * .27f, h * .30f);
        shapes.setColor(.10f, .65f, .82f, 1f); shapes.rect(w * .075f, h * .39f, w * .22f, 48f);
        shapes.setColor(.55f, .25f, .95f, 1f); shapes.rect(w * .39f, h * .39f, w * .22f, 48f);
        shapes.setColor(.16f, .78f, .48f, 1f); shapes.rect(w * .705f, h * .39f, w * .22f, 48f);
        shapes.setColor(.03f, .04f, .065f, .98f); shapes.rect(w * .05f, h * .10f, w * .90f, h * .20f);
        shapes.end();

        PlayerProfile p = game.profile;
        batch.begin();
        font.getData().setScale(1.2f); font.setColor(Color.WHITE);
        font.draw(batch, "SUPPLY SHOP", 0, h * .86f, w, Align.center, false);
        font.getData().setScale(.62f);
        font.setColor(Color.GOLD); font.draw(batch, "CREDITS " + p.currency(PlayerProfile.Currency.CREDITS), 24, h - 30);
        font.setColor(Color.CYAN); font.draw(batch, "GEMS " + p.currency(PlayerProfile.Currency.GEMS), w - 180, h - 30);

        font.setColor(Color.WHITE);
        font.draw(batch, "FIELD", w * .05f, h * .59f, w * .27f, Align.center, false);
        font.draw(batch, "ELITE", w * .365f, h * .59f, w * .27f, Align.center, false);
        font.draw(batch, "DAILY", w * .68f, h * .59f, w * .27f, Align.center, false);
        font.getData().setScale(.42f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Standard roll\n" + ChestService.CREDIT_CHEST_COST + " credits", w * .065f, h * .52f, w * .24f, Align.center, true);
        font.draw(batch, "Best of 3\n" + ChestService.GEM_CHEST_COST + " gems", w * .38f, h * .52f, w * .24f, Align.center, true);
        String daily = p.daily.rewardedChestClaimed ? "Claimed today" : "Watch rewarded ad\n1 free roll";
        font.draw(batch, daily, w * .695f, h * .52f, w * .24f, Align.center, true);
        font.setColor(Color.WHITE);
        font.draw(batch, "[1] OPEN", w * .075f, h * .39f + 31f, w * .22f, Align.center, false);
        font.draw(batch, "[2] OPEN", w * .39f, h * .39f + 31f, w * .22f, Align.center, false);
        font.draw(batch, "[3] FREE", w * .705f, h * .39f + 31f, w * .22f, Align.center, false);

        font.getData().setScale(.38f);
        font.setColor(Color.GOLD);
        font.draw(batch, "[4] STARTER PACK  •  5000 Credits + 250 Gems + 2 boss-tier gear", w * .07f, h * .255f, w * .86f, Align.left, false);
        font.setColor(Color.CYAN);
        font.draw(batch, "[5] 250 GEMS     [6] 1200 GEMS", w * .07f, h * .205f, w * .86f, Align.left, false);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "[7] REMOVE ADS FOREVER" + (p.removeAdsPurchased ? "  • OWNED" : ""), w * .07f, h * .155f, w * .86f, Align.left, false);

        font.setColor(Color.GRAY); font.draw(batch, status, 0, 62, w, Align.center, false);
        font.draw(batch, "ESC • BACK TO BASE", 0, 28, w, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) open(false);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) open(true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) openRewarded();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) purchase(BillingService.STARTER_PACK);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) purchase(BillingService.GEMS_SMALL);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) purchase(BillingService.GEMS_LARGE);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) purchase(BillingService.REMOVE_ADS);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.showMenu();
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (y >= h * .36f && y <= h * .66f) {
                if (x < w * .34f) open(false);
                else if (x < w * .66f) open(true);
                else openRewarded();
            }
        }
    }

    private void syncBillingStatus() {
        BillingService.State state = game.services.billing.state();
        if (state == BillingService.State.PURCHASE_PENDING) {
            String product = game.services.billing.activeProductId();
            status = product.isBlank()
                ? "Google Play payment pending • delivery is automatic after approval"
                : "Payment pending for " + product + " • delivery is automatic after approval";
        } else if (state == BillingService.State.CONNECTING) {
            status = "Connecting to Google Play...";
        } else if (state == BillingService.State.UNAVAILABLE) {
            status = "Google Play billing unavailable";
        } else if (state == BillingService.State.PURCHASE_IN_PROGRESS) {
            status = "Waiting for Google Play purchase confirmation...";
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

    private void purchase(String productId) {
        if (BillingService.REMOVE_ADS.equals(productId) && game.profile.removeAdsPurchased) { status = "Ad-free already owned"; return; }
        if (BillingService.STARTER_PACK.equals(productId) && game.profile.starterPackGranted) { status = "Starter Pack already claimed"; return; }
        if (game.services.billing.state() == BillingService.State.PURCHASE_PENDING) {
            status = "A Google Play payment is already pending";
            return;
        }
        status = "Opening Google Play purchase...";
        if (BillingService.isConsumable(productId)) {
            game.services.billing.purchaseWithReceipt(productId, this::deliverConsumable,
                () -> status = "Purchase cancelled or unavailable");
            return;
        }
        game.services.billing.purchase(productId, () -> {
            boolean granted = PurchaseGrantService.grant(game.profile, productId);
            if (granted) {
                game.saveProfile();
                status = "Purchase delivered";
            } else status = "Purchase already delivered";
        }, () -> status = "Purchase cancelled or unavailable");
    }

    private void deliverConsumable(BillingService.PurchaseReceipt receipt) {
        final boolean[] granted = {false};
        granted[0] = ConsumablePurchaseDelivery.deliver(game.profile, game.services.billing, receipt,
            game::saveProfile,
            () -> status = granted[0] ? "Purchase delivered" : "Recovered purchase finalized",
            () -> status = "Purchase saved; Google Play finalization pending");
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
