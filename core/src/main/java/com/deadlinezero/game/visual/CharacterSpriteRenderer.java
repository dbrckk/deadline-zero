package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;

/** Draws authored character art when present. Procedural shapes remain available underneath as fallback/debug. */
public final class CharacterSpriteRenderer {
    private final GameArt art;
    private float stateTime;

    public CharacterSpriteRenderer(GameArt art) { this.art = art; }

    public void update(float dt) { stateTime += Math.max(0f, dt); }

    public boolean authoredAvailable() { return art.authoredAvailable(); }

    public void draw(SpriteBatch batch, Player player, Array<Enemy> enemies) {
        if (!art.authoredAvailable()) return;
        batch.begin();
        drawPlayer(batch, player);
        for (Enemy enemy : enemies) if (enemy.alive) drawEnemy(batch, enemy);
        batch.end();
    }

    private void drawPlayer(SpriteBatch batch, Player player) {
        GameArt.Motion motion;
        if (!player.alive) motion = GameArt.Motion.DEATH;
        else if (player.visualHitTimer > 0f) motion = GameArt.Motion.HIT;
        else motion = player.velocity.len2() > .04f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;

        TextureRegion region = art.survivor(RunLoadoutContext.survivor(), motion, stateTime);
        float h = 1.65f;
        float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
        float w = h * aspect;
        float facing = player.velocity.x < -.02f ? -1f : 1f;
        batch.setColor(1f, 1f, 1f, player.invulnerable() ? .78f : 1f);
        drawFacing(batch, region, player.position.x, player.position.y - .58f, w, h, facing);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void drawEnemy(SpriteBatch batch, Enemy enemy) {
        GameArt.Motion motion;
        if (enemy.hitFlash > .22f || enemy.attack.state() == EnemyState.STUNNED) {
            motion = GameArt.Motion.HIT;
        } else if (enemy.attack.state() == EnemyState.ATTACKING || enemy.attack.state() == EnemyState.TELEGRAPHING) {
            motion = GameArt.Motion.ATTACK;
        } else {
            motion = enemy.velocity.len2() > .025f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;
        }

        TextureRegion region = art.enemy(enemy.type, motion, stateTime + enemy.position.x * .07f + enemy.position.y * .05f);
        float h = Math.max(.95f, enemy.radius * (enemy.type == Enemy.Type.BOSS ? 3.1f : 2.55f));
        float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
        float w = h * aspect;
        float flash = Math.min(1f, Math.max(0f, enemy.hitFlash));
        float facing = enemy.velocity.x < -.02f ? -1f : 1f;
        batch.setColor(1f, 1f - flash * .22f, 1f - flash * .22f, 1f);
        drawFacing(batch, region, enemy.position.x, enemy.position.y - enemy.radius * .72f, w, h, facing);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void drawFacing(SpriteBatch batch, TextureRegion region, float centerX, float y, float width, float height, float facing) {
        float x = centerX - width * .5f;
        if (facing >= 0f) {
            batch.draw(region, x, y, width, height);
        } else {
            batch.draw(region,
                x, y,
                width * .5f, height * .5f,
                width, height,
                -1f, 1f,
                0f);
        }
    }
}
