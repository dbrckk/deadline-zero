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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.WeaponProgression;
import com.deadlinezero.game.meta.WeaponSynergyRules;
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
        float detailH = 112f;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(18, h - 88, w - 36, 58);
        float cardW = (w - 56f) / 2f;
        float cardH = Math.min(102f, (h - 290f) / 4f);
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
        shapes.setColor(VisualTheme.PANEL); shapes.rect(20, 42, w - 40, detailH);
        drawStatBars(all[MathUtils.clamp(focus, 0, all.length - 1)], WeaponCatalog.byId(game.profile.selectedWeaponId), 34f, 56f, w * .46f, 76f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.2f); font.setColor(VisualTheme.TEXT); font.draw(batch, "ARSENAL", 28, h - 48);
        font.getData().setScale(.48f); font.setColor(VisualTheme.MUTED);
        font.draw(batch, "SELECT YOUR STARTING WEAPON  •  FREE PROGRESSION UNLOCKS", 28, h - 70);
        for (int i = 0; i < all.length; i++) drawCard(all[i], i, cardW, cardH, top);
        WeaponDefinition weapon = all[MathUtils.clamp(focus, 0, all.length - 1)];
        WeaponDefinition equipped = WeaponCatalog.byId(game.profile.selectedWeaponId);
        drawDetailText(weapon, equipped, w);
        drawAuthoredPreview(weapon, w, detailH);
        batch.end();
        handleInput(w, h, all, cardW, cardH, top);
    }

    private void drawCard(WeaponDefinition weapon, int i, float cardW, float cardH, float top) {
        int row = i / 2, col = i % 2;
        float x = 20f + col * (cardW + 16f);
        float y = top - row * (cardH + 10f) - cardH;
        boolean selected = weapon.id.equals(game.profile.selectedWeaponId);
        boolean unlocked = WeaponProgression.unlocked(game.profile, weapon);
        float dps = paperDps(weapon);
        font.getData().setScale(.56f); font.setColor(unlocked ? VisualTheme.TEXT : VisualTheme.MUTED);
        font.draw(batch, weapon.displayName.toUpperCase(), x + 14f, y + cardH - 17f);
        font.getData().setScale(.41f); font.setColor(elementColor(weapon));
        font.draw(batch, role(weapon) + "  •  " + weapon.element.name(), x + 14f, y + cardH - 38f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, Math.round(dps) + " DPS   " + Math.round(weapon.damage) + " DMG   " + weapon.projectileCount + "x", x + 14f, y + cardH - 59f);
        if (selected) { font.setColor(VisualTheme.CYAN); font.draw(batch, "EQUIPPED", x + 14f, y + 17f); }
        else if (unlocked) { font.setColor(i == focus ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED); font.draw(batch, i == focus ? "SELECT" : "AVAILABLE", x + 14f, y + 17f); }
        else { font.setColor(VisualTheme.GOLD); font.draw(batch, "LOCKED  •  ACCOUNT LV " + WeaponProgression.unlockAccountLevel(weapon), x + 14f, y + 17f); }
    }

    private void drawDetailText(WeaponDefinition weapon, WeaponDefinition equipped, float w) {
        float dps = paperDps(weapon), equippedDps = paperDps(equipped), x = w * .50f;
        font.getData().setScale(.50f); font.setColor(VisualTheme.TEXT); font.draw(batch, weapon.displayName.toUpperCase(), x, 132f);
        font.getData().setScale(.42f); font.setColor(elementColor(weapon)); font.draw(batch, role(weapon) + "  •  " + weapon.element.name(), x, 111f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "DPS " + Math.round(dps) + deltaText(dps - equippedDps) + "   FIRE " + String.format(java.util.Locale.US, "%.2fs", weapon.fireInterval) + "   CRIT " + Math.round(weapon.critChance * 100f) + "%", x, 90f);
        font.draw(batch, "PEN " + weapon.penetration + deltaText(weapon.penetration - equipped.penetration) + "   KB " + oneDecimal(weapon.knockback) + deltaText(weapon.knockback - equipped.knockback) + "   SHOTS " + weapon.projectileCount + deltaText(weapon.projectileCount - equipped.projectileCount), x, 70f);
        font.setColor(VisualTheme.CYAN_SOFT); font.draw(batch, description(weapon), x, 50f, w * .45f, Align.left, true);
        WeaponSynergyRules.Synergy synergy = WeaponSynergyRules.resolve(game.profile.selectedSurvivor, weapon);
        if (synergy != WeaponSynergyRules.Synergy.NONE) {
            font.setColor(VisualTheme.GOLD); font.draw(batch, "SYNERGY • " + synergy.displayName, x, 31f);
        }
        font.setColor(VisualTheme.MUTED); font.draw(batch, "A/D OR ←/→  •  ENTER SELECT  •  ESC/BACK BASE", 24, 25);
    }

    private void drawStatBars(WeaponDefinition weapon, WeaponDefinition equipped, float x, float y, float width, float height) {
        float[] values = { MathUtils.clamp(paperDps(weapon) / 240f, 0f, 1f), MathUtils.clamp((1f / weapon.fireInterval) / 8f, 0f, 1f), MathUtils.clamp(weapon.penetration / 5f, 0f, 1f), MathUtils.clamp(weapon.knockback / 5f, 0f, 1f) };
        float[] base = { MathUtils.clamp(paperDps(equipped) / 240f, 0f, 1f), MathUtils.clamp((1f / equipped.fireInterval) / 8f, 0f, 1f), MathUtils.clamp(equipped.penetration / 5f, 0f, 1f), MathUtils.clamp(equipped.knockback / 5f, 0f, 1f) };
        float barW = width - 54f;
        for (int i = 0; i < values.length; i++) {
            float yy = y + i * (height / 4f);
            shapes.setColor(.06f, .08f, .10f, 1f); shapes.rect(x + 54f, yy, barW, 6f);
            shapes.setColor(VisualTheme.MUTED.r, VisualTheme.MUTED.g, VisualTheme.MUTED.b, .65f); shapes.rect(x + 54f, yy, barW * base[i], 6f);
            shapes.setColor(VisualTheme.CYAN); shapes.rect(x + 54f, yy, barW * values[i], 3f);
        }
    }

    private void drawAuthoredPreview(WeaponDefinition weapon, float w, float detailH) {
        if (game.art == null || !game.art.authoredAvailable()) return;
        TextureRegion region = game.art.regionOrNull("weapon/" + weapon.id);
        if (region == null) return;
        float maxW = w * .16f, maxH = detailH - 28f, aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
        float drawW = maxW, drawH = drawW / Math.max(.01f, aspect);
        if (drawH > maxH) { drawH = maxH; drawW = drawH * aspect; }
        batch.setColor(Color.WHITE); batch.draw(region, w * .30f - drawW * .5f, 55f, drawW, drawH);
    }

    private float paperDps(WeaponDefinition weapon) { return weapon.damage * weapon.projectileCount / Math.max(.05f, weapon.fireInterval); }

    private String role(WeaponDefinition weapon) {
        return switch (weapon.id) {
            case "scattergun" -> "CLOSE BURST";
            case "rail_rifle" -> "PRECISION PIERCER";
            case "inferno_smg" -> "RAPID BURN";
            case "cryo_lance" -> "CONTROL";
            case "arc_carbine" -> "CHAIN CONTROL";
            case "breacher" -> "HEAVY BREACH";
            case "ion_needle" -> "CAPACITOR PRECISION";
            case "cinder_cannon" -> "THERMAL ARTILLERY";
            default -> "BALANCED RIFLE";
        };
    }

    private String description(WeaponDefinition weapon) {
        return switch (weapon.id) {
            case "scattergun" -> "Wide close-range burst with strong stagger. Best when kiting dense packs.";
            case "rail_rifle" -> "Slow precision rifle with extreme penetration and high critical ceiling.";
            case "inferno_smg" -> "Very high cadence FIRE weapon built to stack pressure across moving hordes.";
            case "cryo_lance" -> "FROST-focused rifle trading raw DPS for safer spacing and crowd control.";
            case "arc_carbine" -> "SHOCK carbine optimized for chained hits and clustered targets.";
            case "breacher" -> "Nine-projectile blast with brutal knockback, limited by range and reload cadence.";
            case "ion_needle" -> "Every 5th projectile overcharges: guaranteed critical, bonus penetration and impact. VOLT/NYX unlock signature synergies.";
            case "cinder_cannon" -> "Every 4th shell vents stored heat for +55% payload, extra penetration and knockback. BASTION unlocks Siege Furnace.";
            default -> "Reliable all-round rifle with stable damage, cadence and accuracy for every stage.";
        };
    }

    private String deltaText(float delta) { if (Math.abs(delta) < .05f) return ""; return delta > 0f ? "  +" + Math.round(delta) : "  " + Math.round(delta); }
    private String deltaText(int delta) { if (delta == 0) return ""; return delta > 0 ? "  +" + delta : "  " + delta; }
    private String oneDecimal(float value) { return String.format(java.util.Locale.US, "%.1f", value); }
    private Color elementColor(WeaponDefinition weapon) { return switch (weapon.element) { case FIRE -> Color.ORANGE; case FROST -> VisualTheme.CYAN; case SHOCK -> VisualTheme.VIOLET; default -> VisualTheme.CYAN_SOFT; }; }

    private void handleInput(float w, float h, WeaponDefinition[] all, float cardW, float cardH, float top) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) focus = Math.max(0, focus - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) focus = Math.min(all.length - 1, focus + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) focus = Math.max(0, focus - 2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) focus = Math.min(all.length - 1, focus + 2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) select(all[focus]);
        if (!Gdx.input.justTouched()) return;
        float tx = Gdx.input.getX(), ty = h - Gdx.input.getY();
        for (int i = 0; i < all.length; i++) {
            int row = i / 2, col = i % 2; float x = 20f + col * (cardW + 16f); float y = top - row * (cardH + 10f) - cardH;
            if (tx >= x && tx <= x + cardW && ty >= y && ty <= y + cardH) { focus = i; select(all[i]); return; }
        }
    }

    private void select(WeaponDefinition weapon) {
        if (!WeaponProgression.unlocked(game.profile, weapon)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); return; }
        if (game.profile.selectWeapon(weapon)) { ProfileStore.save(game.profile); AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); }
    }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
