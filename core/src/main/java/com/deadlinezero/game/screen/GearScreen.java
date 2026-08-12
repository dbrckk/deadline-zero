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
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.EquipmentService;
import com.deadlinezero.game.meta.EquipmentUpgradeService;
import com.deadlinezero.game.meta.PlayerProfile;

/** Functional pre-art gear management screen. */
public final class GearScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private int index;

    public GearScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        int size = game.profile.inventory.size();
        if (size > 0) index = Math.max(0, Math.min(index, size - 1)); else index = 0;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .04f, .06f, 1f); shapes.rect(20, 95, w - 40, h - 145);
        shapes.setColor(.04f, .09f, .12f, 1f); shapes.rect(35, h * .31f, w - 70, h * .32f);
        shapes.setColor(.06f, .55f, .75f, 1f); shapes.rect(35, 32, w * .25f, 48);
        shapes.setColor(.16f, .42f, .25f, 1f); shapes.rect(w * .37f, 32, w * .25f, 48);
        shapes.setColor(.45f, .3f, .08f, 1f); shapes.rect(w * .69f, 32, w * .25f, 48);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.15f); font.setColor(Color.WHITE);
        font.draw(batch, "GEAR", 0, h - 38, w, Align.center, false);
        font.getData().setScale(.58f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "POWER x" + String.format("%.3f", game.profile.aggregatePowerMultiplier()), 0, h - 72, w, Align.center, false);

        if (size == 0) {
            font.draw(batch, "No equipment yet. Complete runs to obtain drops.", 0, h * .52f, w, Align.center, false);
        } else {
            EquipmentItem item = game.profile.inventory.items().get(index);
            EquipmentItem equipped = game.profile.equipped(item.slot);
            boolean isEquipped = equipped != null && equipped.id.equals(item.id);
            font.getData().setScale(.82f); font.setColor(rarityColor(item.rarity));
            font.draw(batch, item.name, 0, h * .58f, w, Align.center, false);
            font.getData().setScale(.55f); font.setColor(Color.WHITE);
            font.draw(batch, "Slot " + item.slot + "   Lv." + item.level + "   Bonus +" + Math.round(item.powerBonus * 1000f) / 10f + "%", 0, h * .51f, w, Align.center, false);
            font.setColor(isEquipped ? Color.LIME : Color.LIGHT_GRAY);
            font.draw(batch, isEquipped ? "EQUIPPED" : "UNEQUIPPED", 0, h * .45f, w, Align.center, false);
            font.setColor(Color.GOLD);
            font.draw(batch, "Upgrade: " + EquipmentUpgradeService.cost(item) + " credits", 0, h * .39f, w, Align.center, false);
            font.setColor(Color.GRAY);
            font.draw(batch, (index + 1) + " / " + size + "   ← → select", 0, h * .25f, w, Align.center, false);
        }

        font.getData().setScale(.48f); font.setColor(Color.WHITE);
        font.draw(batch, "ESC / BACK", 35, 61, w * .25f, Align.center, false);
        font.draw(batch, "EQUIP [E]", w * .37f, 61, w * .25f, Align.center, false);
        font.draw(batch, "UPGRADE [U]", w * .69f, 61, w * .25f, Align.center, false);
        batch.end();
    }

    private void handleInput() {
        int size = game.profile.inventory.size();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
        if (size == 0) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) index = (index - 1 + size) % size;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) index = (index + 1) % size;
        EquipmentItem item = game.profile.inventory.items().get(index);
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            EquipmentItem current = game.profile.equipped(item.slot);
            if (current != null && current.id.equals(item.id)) EquipmentService.unequip(game.profile, item.slot);
            else EquipmentService.equip(game.profile, item.id);
            game.saveProfile();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U) && EquipmentService.upgrade(game.profile, item.id)) game.saveProfile();
    }

    private Color rarityColor(EquipmentItem.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> Color.LIGHT_GRAY;
            case RARE -> Color.CYAN;
            case EPIC -> Color.VIOLET;
            case LEGENDARY -> Color.GOLD;
            case MYTHIC -> Color.MAGENTA;
        };
    }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
