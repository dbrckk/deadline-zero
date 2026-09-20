package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive economy shell with soft, premium, rewarded and Play Billing offers. */
public final class ShopScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle[] chestCards;
    private Rectangle[] purchaseButtons;
    private String status;
    private boolean consumableRestoreRequested;
    private float visualTime;

    public ShopScreen(DeadlineZeroGame game) {
        this.game = game;
        this.status = t("shop.choose");
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle c = layout.content();
        Rectangle chestArea = new Rectangle(c.x, c.y + c.height * .38f, c.width, c.height * .62f);
        chestCards = MetaLayout.columns(chestArea, 3, 18f);
        Rectangle purchaseArea = new Rectangle(c.x, c.y, c.width, c.height * .31f);
        purchaseButtons = MetaLayout.columns(purchaseArea, 4, 14f);
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        if (PurchaseGrantService.syncPermanent(game.profile, game.services.billing)) game.saveProfile();
        if (!consumableRestoreRequested) {
            consumableRestoreRequested = true;
            game.services.billing.restoreConsumables(this::deliverConsumable);
        }
        syncBillingStatus();

        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);
        PlayerProfile p = game.profile;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        for (int i = 0; i < chestCards.length; i++) {
            Rectangle r = chestCards[i];
            boolean disabled = i == 2 && p.daily.rewardedChestClaimed;
            UiRenderer.card(shapes, r.x, r.y, r.width, r.height, i == 2 && !disabled, false);
            drawChestChrome(shapes, r, i, disabled);
            Rectangle button = chestButton(r);
            UiRenderer.button(shapes, button.x, button.y, button.width, button.height,
                disabled ? UiRenderer.ButtonState.DISABLED : i == 2 ? UiRenderer.ButtonState.SELECTED : UiRenderer.ButtonState.NORMAL);
        }
        for (int i = 0; i < purchaseButtons.length; i++) {
            Rectangle r = purchaseButtons[i];
            String productId = purchaseProductId(i);
            boolean owned = (i == 0 && p.starterPackGranted) || (i == 3 && p.removeAdsPurchased);
            boolean enabled = game.services.offers.current().enabled(productId);
            boolean featured = game.services.offers.current().featured(productId);
            UiRenderer.button(shapes, r.x, r.y, r.width, r.height,
                owned || !enabled ? UiRenderer.ButtonState.DISABLED
                    : featured ? UiRenderer.ButtonState.SELECTED
                    : UiRenderer.ButtonState.NORMAL);
            drawOfferChrome(shapes, r, i, owned || !enabled, featured);
        }
        shapes.end();

        batch.begin();
        drawHeader(p);
        drawChestCards(p);
        drawPurchaseRow(p);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, status, layout.footer().x + 20f, layout.footer().y + layout.footer().height * .56f,
            layout.footer().width - 40f, Align.center, true);
        batch.end();

        handleInput();
    }

    private void drawChestChrome(ShapeRenderer shapes, Rectangle r, int index, boolean disabled) {
        Color accent = switch (index) {
            case 1 -> VisualTheme.VIOLET;
            case 2 -> VisualTheme.positive();
            default -> VisualTheme.CYAN_SOFT;
        };
        float alpha = disabled ? .18f : index == 2 ? .72f : .52f;
        shapes.setColor(accent.r, accent.g, accent.b, alpha);
        shapes.rect(r.x + 5f, r.y + r.height - 7f, Math.max(0f, r.width - 10f), 4f);
        shapes.setColor(accent.r, accent.g, accent.b, disabled ? .035f : .065f);
        shapes.rect(r.x + 8f, r.y + 8f, Math.max(0f, r.width - 16f), Math.max(0f, r.height - 16f));

        float emblemRadius = Math.min(r.width, r.height) * .07f;
        float cx = r.x + r.width * .5f;
        float cy = r.y + r.height * .72f;
        shapes.setColor(accent.r, accent.g, accent.b, disabled ? .10f : .18f);
        shapes.circle(cx, cy, emblemRadius * 1.7f, 28);
        shapes.setColor(accent.r, accent.g, accent.b, disabled ? .26f : .76f);
        shapes.circle(cx, cy, emblemRadius, 24);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .92f);
        shapes.circle(cx, cy, emblemRadius * .48f, 20);
    }

    private void drawOfferChrome(ShapeRenderer shapes, Rectangle r, int index,
                                 boolean disabled, boolean featured) {
        Color accent = switch (index) {
            case 0 -> VisualTheme.GOLD;
            case 3 -> VisualTheme.CYAN_SOFT;
            default -> VisualTheme.accent();
        };
        if (disabled) {
            shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .24f);
            shapes.rect(r.x + 6f, r.y + r.height - 5f, Math.max(0f, r.width - 12f), 2f);
            return;
        }

        shapes.setColor(accent.r, accent.g, accent.b, featured ? .88f : .42f);
        shapes.rect(r.x + 6f, r.y + r.height - (featured ? 6f : 4f),
            Math.max(0f, r.width - 12f), featured ? 4f : 2f);
        if (featured) {
            shapes.setColor(accent.r, accent.g, accent.b, .08f);
            shapes.rect(r.x + 7f, r.y + 7f, Math.max(0f, r.width - 14f), Math.max(0f, r.height - 14f));
        }
    }

    private void drawHeader(PlayerProfile p) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
            layout.back().width - 16f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("shop.title"), metrics.safeLeft() + 140f, metrics.headerBottom() + 55f,
            metrics.contentWidth() - 520f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("shop.credits", p.currency(PlayerProfile.Currency.CREDITS)), metrics.safeRight() - 360f,
            metrics.headerBottom() + 49f, 170f, Align.right, false);
        font.setColor(VisualTheme.accent());
        font.draw(batch, f("shop.gems", p.currency(PlayerProfile.Currency.GEMS)), metrics.safeRight() - 174f,
            metrics.headerBottom() + 49f, 160f, Align.right, false);
    }

    private void drawChestCards(PlayerProfile p) {
        String[] titles = {t("shop.field"), t("shop.elite"), t("shop.daily")};
        String[] descriptions = {
            f("shop.standardRoll", ChestService.CREDIT_CHEST_COST),
            f("shop.bestOf3", ChestService.GEM_CHEST_COST),
            p.daily.rewardedChestClaimed ? t("shop.claimedToday") : t("shop.freeRoll")
        };
        String[] buttons = {t("shop.open1"), t("shop.open2"), t("shop.free3")};
        for (int i = 0; i < chestCards.length; i++) {
            Rectangle r = chestCards[i];
            font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
            font.setColor(i == 0 ? VisualTheme.CYAN_SOFT : i == 1 ? VisualTheme.VIOLET : VisualTheme.positive());
            font.draw(batch, titles[i], r.x + 18f, r.y + r.height - 28f, r.width - 36f, Align.center, false);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.TEXT_DIM);
            font.draw(batch, descriptions[i], r.x + 26f, r.y + r.height * .57f, r.width - 52f, Align.center, true);
            Rectangle b = chestButton(r);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(i == 2 && p.daily.rewardedChestClaimed ? VisualTheme.MUTED : VisualTheme.TEXT_STRONG);
            font.draw(batch, buttons[i], b.x + 8f, b.y + b.height * .61f, b.width - 16f, Align.center, false);
        }
    }

    private void drawPurchaseRow(PlayerProfile p) {
        String[] labels = {
            t("shop.starterPack"),
            t("shop.gemPacks") + " • S",
            t("shop.gemPacks") + " • L",
            f("shop.removeAds", p.removeAdsPurchased ? t("shop.owned") : "")
        };
        for (int i = 0; i < purchaseButtons.length; i++) {
            Rectangle r = purchaseButtons[i];
            boolean owned = (i == 0 && p.starterPackGranted) || (i == 3 && p.removeAdsPurchased);
            String productId = purchaseProductId(i);
            boolean enabled = game.services.offers.current().enabled(productId);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(owned || !enabled ? VisualTheme.MUTED : i == 0 ? VisualTheme.GOLD : i == 3 ? VisualTheme.CYAN_SOFT : VisualTheme.TEXT_STRONG);
            font.draw(batch, labels[i], r.x + 10f, r.y + r.height * .63f, r.width - 20f, Align.center, true);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(owned || !enabled ? VisualTheme.MUTED : VisualTheme.TEXT_DIM);
            String sublabel = owned ? t("shop.owned") : !enabled ? t("shop.billingUnavailable") : "PLAY BILLING";
            font.draw(batch, sublabel, r.x + 10f, r.y + 22f, r.width - 20f, Align.center, false);
        }
    }

    private Rectangle chestButton(Rectangle card) {
        return new Rectangle(card.x + 24f, card.y + 22f, card.width - 48f, Math.max(60f, card.height * .22f));
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) open(false);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) open(true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) openRewarded();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) purchase(BillingService.STARTER_PACK);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) purchase(BillingService.GEMS_SMALL);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) purchase(BillingService.GEMS_LARGE);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) purchase(BillingService.REMOVE_ADS);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.back().contains(touch)) { game.showMenu(); return; }
        for (int i = 0; i < chestCards.length; i++) {
            if (!chestCards[i].contains(touch)) continue;
            if (i == 0) open(false);
            else if (i == 1) open(true);
            else openRewarded();
            return;
        }
        for (int i = 0; i < purchaseButtons.length; i++) {
            if (!purchaseButtons[i].contains(touch)) continue;
            switch (i) {
                case 0 -> purchase(BillingService.STARTER_PACK);
                case 1 -> purchase(BillingService.GEMS_SMALL);
                case 2 -> purchase(BillingService.GEMS_LARGE);
                case 3 -> purchase(BillingService.REMOVE_ADS);
                default -> { }
            }
            return;
        }
    }

    private void syncBillingStatus() {
        BillingService.State state = game.services.billing.state();
        if (state == BillingService.State.PURCHASE_PENDING) {
            String product = game.services.billing.activeProductId();
            status = product.isBlank() ? t("shop.paymentPending") : f("shop.paymentPendingProduct", product);
        } else if (state == BillingService.State.CONNECTING) status = t("shop.connecting");
        else if (state == BillingService.State.UNAVAILABLE) status = t("shop.billingUnavailable");
        else if (state == BillingService.State.PURCHASE_IN_PROGRESS) status = t("shop.purchaseConfirm");
    }

    private void open(boolean premium) {
        EquipmentItem item = premium ? ChestService.openGemChest(game.profile) : ChestService.openCreditChest(game.profile);
        if (item == null) { status = game.profile.inventory.full() ? t("shop.inventoryFull") : t("shop.notEnough"); return; }
        status = f("shop.obtained", item.name, item.level);
        game.saveProfile();
    }

    private void openRewarded() {
        if (game.profile.daily.rewardedChestClaimed) { status = t("shop.dailyClaimed"); return; }
        if (game.profile.inventory.full()) { status = t("shop.inventoryFull"); return; }
        status = t("shop.loadingReward");
        game.services.ads.showRewarded(AdsService.Reward.BONUS_CHEST, () -> {
            if (game.profile.daily.rewardedChestClaimed || game.profile.inventory.full()) return;
            EquipmentItem item = EquipmentDropTable.roll(game.profile.selectedStage, false);
            game.profile.inventory.add(item);
            game.profile.daily.rewardedChestClaimed = true;
            status = f("shop.freeCrate", item.name, item.level);
            game.saveProfile();
            game.services.ads.preload();
        }, () -> status = t("shop.rewardUnavailable"));
    }

    private void purchase(String productId) {
        if (!game.services.offers.current().enabled(productId)) { status = t("shop.billingUnavailable"); return; }
        if (BillingService.REMOVE_ADS.equals(productId) && game.profile.removeAdsPurchased) { status = t("shop.adFreeOwned"); return; }
        if (BillingService.STARTER_PACK.equals(productId) && game.profile.starterPackGranted) { status = t("shop.starterClaimed"); return; }
        if (game.services.billing.state() == BillingService.State.PURCHASE_PENDING) { status = t("shop.paymentAlreadyPending"); return; }
        status = t("shop.openingPurchase");
        if (BillingService.isConsumable(productId)) {
            game.services.billing.purchaseWithReceipt(productId, this::deliverConsumable, () -> status = t("shop.purchaseCancelled"));
            return;
        }
        game.services.billing.purchase(productId, () -> {
            boolean granted = PurchaseGrantService.grant(game.profile, productId);
            if (granted) { game.saveProfile(); status = t("shop.purchaseDelivered"); }
            else status = t("shop.purchaseAlreadyDelivered");
        }, () -> status = t("shop.purchaseCancelled"));
    }

    private String purchaseProductId(int index) {
        return switch (index) {
            case 0 -> BillingService.STARTER_PACK;
            case 1 -> BillingService.GEMS_SMALL;
            case 2 -> BillingService.GEMS_LARGE;
            case 3 -> BillingService.REMOVE_ADS;
            default -> "";
        };
    }

    private void deliverConsumable(BillingService.PurchaseReceipt receipt) {
        ConsumablePurchaseDelivery.deliver(game.profile, game.services.billing, receipt, game::saveProfile,
            granted -> status = granted ? t("shop.purchaseDelivered") : t("shop.recoveredFinalized"),
            () -> status = t("shop.purchaseFinalizationPending"));
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
