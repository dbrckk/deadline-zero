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
import com.deadlinezero.game.meta.ThreatMilestoneRewardCatalog;
import com.deadlinezero.game.meta.ThreatSetBonusRules;

/** Functional pre-art gear management screen. */
public final class GearScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private int index;
    private String status = "";

    public GearScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        int size = game.profile.inventory.size();
        if (size > 0) index = Math.max(0, Math.min(index, size - 1)); else index = 0;
        int ascensionPieces = ThreatSetBonusRules.equippedPieces(game.profile);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .04f, .06f, 1f); shapes.rect(20, 95, w - 40, h - 145);
        shapes.setColor(.04f, .09f, .12f, 1f); shapes.rect(35, h * .31f, w - 70, h * .32f);
        shapes.setColor(.06f, .55f, .75f, 1f); shapes.rect(35, 32, w * .20f, 48);
        shapes.setColor(.16f, .42f, .25f, 1f); shapes.rect(w * .30f, 32, w * .20f, 48);
        shapes.setColor(.45f, .3f, .08f, 1f); shapes.rect(w * .55f, 32, w * .20f, 48);
        shapes.setColor(.42f, .16f, .52f, 1f); shapes.rect(w * .79f, 32, w * .17f, 48);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.15f); font.setColor(Color.WHITE);
        font.draw(batch, "GEAR", 0, h - 38, w, Align.center, false);
        font.getData().setScale(.58f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "POWER x" + String.format("%.3f", game.profile.aggregatePowerMultiplier()), 0, h - 72, w, Align.center, false);
        font.getData().setScale(.40f);
        font.setColor(ascensionPieces >= 2 ? Color.GOLD : Color.GRAY);
        font.draw(batch, ThreatSetBonusRules.summary(ascensionPieces), 0, h - 96, w, Align.center, false);

        if (size == 0) {
            font.draw(batch, "No equipment yet. Complete runs to obtain drops.", 0, h * .52f, w, Align.center, false);
        } else {
            EquipmentItem item = game.profile.inventory.items().get(index);
            EquipmentItem equipped = game.profile.equipped(item.slot);
            boolean isEquipped = equipped != null && equipped.id.equals(item.id);
            boolean ascensionExclusive = ThreatMilestoneRewardCatalog.isExclusiveId(item.id);
            float itemScore = EquipmentService.score(item);
            float equippedScore = EquipmentService.score(equipped);
            float scoreDelta = itemScore - equippedScore;

            font.getData().setScale(.82f); font.setColor(rarityColor(item.rarity));
            font.draw(batch, item.name, 0, h * .59f, w, Align.center, false);
            font.getData().setScale(.48f);
            font.setColor(ascensionExclusive ? Color.GOLD : Color.LIGHT_GRAY);
            font.draw(batch, ascensionExclusive ? "ASCENSION EXCLUSIVE • MYTHIC MILESTONE REWARD" : item.rarity.name(),
                0, h * .555f, w, Align.center, false);
            font.getData().setScale(.55f); font.setColor(Color.WHITE);
            font.draw(batch, "Slot " + item.slot + "   Lv." + item.level + "   Bonus +" + Math.round(item.powerBonus * 1000f) / 10f + "%", 0, h * .52f, w, Align.center, false);
            font.setColor(isEquipped ? Color.LIME : Color.LIGHT_GRAY);
            font.draw(batch, isEquipped ? "EQUIPPED" : "UNEQUIPPED", 0, h * .465f, w, Align.center, false);

            if (!isEquipped) {
                font.setColor(scoreDelta >= 0f ? Color.LIME : Color.SCARLET);
                String compare = equipped == null ? "No item equipped in this slot" :
                    String.format("Compared to equipped: %+.1f%% score", equippedScore <= 0f ? 100f : (scoreDelta / equippedScore) * 100f);
                font.draw(batch, compare, 0, h * .425f, w, Align.center, false);
            }

            font.setColor(Color.GOLD);
            font.draw(batch, "Upgrade: " + EquipmentUpgradeService.cost(item) + " credits", 0, h * .375f, w, Align.center, false);
            font.setColor(Color.GRAY);
            font.draw(batch, (index + 1) + " / " + size + "   ← → select   F = fuse 3 matching", 0, h * .25f, w, Align.center, false);
        }

        if (!status.isEmpty()) {
            font.getData().setScale(.44f); font.setColor(Color.CYAN);
            font.draw(batch, status, 0, 105, w, Align.center, false);
        }

        font.getData().setScale(.44f); font.setColor(Color.WHITE);
        font.draw(batch, "BACK", 35, 61, w * .20f, Align.center, false);
        font.draw(batch, "EQUIP [E]", w * .30f, 61, w * .20f, Align.center, false);
        font.draw(batch, "UPGRADE [U]", w * .55f, 61, w * .20f, Align.center, false);
        font.draw(batch, "FUSE [F]", w * .79f, 61, w * .17f, Align.center, false);
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
            status = "Loadout updated";
            game.saveProfile();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            if (EquipmentService.upgrade(game.profile, item.id)) {
                status = "Equipment upgraded";
                game.saveProfile();
            } else status = "Upgrade unavailable or insufficient credits";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) fuseSelected(item);
    }

    private void fuseSelected(EquipmentItem selected) {
        if (selected.rarity == EquipmentItem.Rarity.MYTHIC) { status = "Mythic gear cannot be fused further"; return; }
        EquipmentItem second = null, third = null;
        for (EquipmentItem candidate : game.profile.inventory.items()) {
            if (candidate.id.equals(selected.id) || candidate.slot != selected.slot || candidate.rarity != selected.rarity) continue;
            if (second == null) second = candidate;
            else { third = candidate; break; }
        }
        if (second == null || third == null) { status = "Need 3 items with same slot and rarity"; return; }
        EquipmentItem merged = EquipmentService.mergeThree(game.profile, selected.id, second.id, third.id);
        if (merged == null) { status = "Fusion failed"; return; }
        status = "Created " + merged.rarity + " " + merged.name;
        index = Math.max(0, game.profile.inventory.size() - 1);
        game.saveProfile();
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
