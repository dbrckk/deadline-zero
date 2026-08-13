package com.deadlinezero.game.visual;

import java.util.WeakHashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;

/** Draws authored character art with event-driven attacks and independent per-entity state clocks. */
public final class CharacterSpriteRenderer {
    private static final class Clock {
        GameArt.Motion motion;
        float time;
        int bossPhase = -1;
        float phasePulse;
    }

    private final GameArt art;
    private final WeakHashMap<Object, Clock> clocks = new WeakHashMap<>();
    private float frameDelta;

    public CharacterSpriteRenderer(GameArt art) { this.art = art; }

    public void update(float dt) { frameDelta = Math.max(0f, dt); }

    public boolean authoredAvailable() { return art.authoredAvailable(); }

    public void draw(SpriteBatch batch, Player player, Array<Enemy> enemies) {
        if (!art.authoredAvailable()) return;
        batch.begin();
        drawPlayer(batch, player);
        for (Enemy enemy : enemies) if (enemy.alive) drawEnemy(batch, enemy);
        batch.end();
    }

    private void drawPlayer(SpriteBatch batch, Player player) {
        var survivor = RunLoadoutContext.survivor();
        GameArt.Motion motion;
        if (!player.alive) {
            motion = GameArt.Motion.DEATH;
        } else if (player.visualHitTimer > 0f) {
            motion = GameArt.Motion.HIT;
        } else if (playerAttackWindow(survivor)) {
            motion = GameArt.Motion.ATTACK;
        } else {
            motion = player.velocity.len2() > .04f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;
        }

        Clock clock = clock(player, motion);
        ArtProfileCatalog.CharacterProfile profile = ArtProfileCatalog.survivor(survivor);
        TextureRegion region = art.survivor(survivor, motion, clock.time);
        float h = profile.height();
        float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
        float w = h * aspect;
        float facing = player.velocity.x < -.02f ? -1f : 1f;
        batch.setColor(1f, 1f, 1f, player.invulnerable() ? .78f : 1f);
        drawFacing(batch, region, player.position.x, player.position.y - profile.footOffset(), w, h, facing);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private boolean playerAttackWindow(com.deadlinezero.game.meta.SurvivorCatalog.Survivor survivor) {
        float frame = AnimationProfileCatalog.survivor(survivor).attack();
        float window = Math.max(.085f, Math.min(.18f, frame * 2.5f));
        return CombatVisualEvents.playerShotAgeSeconds() <= window;
    }

    private void drawEnemy(SpriteBatch batch, Enemy enemy) {
        GameArt.Motion motion;
        if (enemy.hitFlash > .22f || enemy.attack.state() == EnemyState.STUNNED) {
            motion = GameArt.Motion.HIT;
        } else if (enemy.attack.state() == EnemyState.ATTACKING || enemy.attack.state() == EnemyState.TELEGRAPHING || enemy.tacticalTelegraph()) {
            motion = GameArt.Motion.ATTACK;
        } else {
            motion = enemy.velocity.len2() > .025f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;
        }

        Clock clock = clock(enemy, motion);
        ArtProfileCatalog.CharacterProfile profile = ArtProfileCatalog.enemy(enemy.type);
        TextureRegion region = art.enemy(enemy.type, motion, clock.time);
        float h = profile.height();
        float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
        float w = h * aspect;
        float flash = Math.min(1f, Math.max(0f, enemy.hitFlash));
        float facing = enemy.velocity.x < -.02f ? -1f : 1f;

        float r = 1f;
        float g = 1f - flash * .22f;
        float b = 1f - flash * .22f;
        float scale = 1f;

        if (enemy.type != Enemy.Type.BOSS) {
            switch (enemy.variant) {
                case SWIFT -> {
                    r *= .72f;
                    g *= .92f;
                    b *= 1.00f;
                    scale *= .94f;
                }
                case ARMORED -> {
                    r *= .82f;
                    g *= .86f;
                    b *= .94f;
                    scale *= 1.10f;
                }
                case FERAL -> {
                    r *= 1.00f;
                    g *= .62f;
                    b *= .56f;
                    scale *= 1.04f;
                }
                default -> { }
            }
        }

        if (enemy.tacticalTelegraph()) {
            float pulse = .5f + .5f * MathUtils.sin(enemy.variantTime * 32f);
            if (enemy.pendingTactic() == Enemy.Tactic.STRAFE) {
                r = MathUtils.lerp(r, .50f, .34f + pulse * .18f);
                g = MathUtils.lerp(g, .92f, .34f + pulse * .18f);
                b = MathUtils.lerp(b, 1f, .42f + pulse * .22f);
                scale *= 1f + pulse * .025f;
            } else if (enemy.pendingTactic() == Enemy.Tactic.CHARGE) {
                r = MathUtils.lerp(r, 1f, .40f + pulse * .22f);
                g = MathUtils.lerp(g, .48f, .28f + pulse * .16f);
                b = MathUtils.lerp(b, .22f, .24f + pulse * .14f);
                scale *= 1.035f + pulse * .045f;
            }
        }

        if (enemy.type == Enemy.Type.BOSS && enemy.bossPhases != null) {
            int phase = enemy.bossPhases.phase();
            if (clock.bossPhase < 0) clock.bossPhase = phase;
            else if (phase != clock.bossPhase) {
                clock.bossPhase = phase;
                clock.phasePulse = .42f;
            }

            if (phase == 2) {
                g *= .88f;
                b *= .82f;
                scale = 1.025f;
            } else if (phase >= 3) {
                g *= .70f;
                b *= .78f;
                scale = 1.055f;
            }

            if (clock.phasePulse > 0f) {
                float pulse = MathUtils.clamp(clock.phasePulse / .42f, 0f, 1f);
                float wave = MathUtils.sin(pulse * MathUtils.PI);
                scale *= 1f + wave * .075f;
                g = MathUtils.lerp(g, .92f, wave * .35f);
                b = MathUtils.lerp(b, 1f, wave * .45f);
            }
        }

        batch.setColor(r, g, b, 1f);
        drawFacing(batch, region, enemy.position.x, enemy.position.y - profile.footOffset(), w * scale, h * scale, facing);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private Clock clock(Object actor, GameArt.Motion motion) {
        Clock clock = clocks.get(actor);
        if (clock == null) {
            clock = new Clock();
            clock.motion = motion;
            clocks.put(actor, clock);
            return clock;
        }
        clock.phasePulse = Math.max(0f, clock.phasePulse - frameDelta);
        if (clock.motion != motion) {
            clock.motion = motion;
            clock.time = 0f;
        } else {
            clock.time += frameDelta;
        }
        return clock;
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
