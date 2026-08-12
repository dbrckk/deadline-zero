package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.abilities.AbilitySystem;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.ImpactFx;
import com.deadlinezero.game.input.VirtualStick;
import com.deadlinezero.game.progression.Upgrade;
import com.deadlinezero.game.progression.UpgradeSelector;
import com.deadlinezero.game.services.AdsService;
import com.deadlinezero.game.util.Pools;
import com.deadlinezero.game.world.SpatialHash;
import com.deadlinezero.game.world.WaveDirector;

public final class GameScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final OrthographicCamera cam = new OrthographicCamera(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final Player player = new Player(0, 0);
    private final Array<Enemy> enemies = new Array<>(false, GameConfig.MAX_ENEMIES);
    private final Array<Enemy> collisionCandidates = new Array<>(false, 32);
    private final Pools pools = new Pools();
    private final WaveDirector director = new WaveDirector();
    private final SpatialHash spatial = new SpatialHash(2.2f);
    private final VirtualStick stick = new VirtualStick();
    private final Vector2 aim = new Vector2();
    private final Vector2 shotVelocity = new Vector2();
    private final Matrix4 hudMatrix = new Matrix4();
    private final AbilitySystem abilitySystem;
    private float accumulator, fireTimer, contactTimer, cameraShake;
    private boolean choosingUpgrade, gameOver, revived, bossKilledThisRun, settling;
    private final Upgrade[] choices = new Upgrade[3];

    public GameScreen(DeadlineZeroGame game) {
        this.game = game;
        this.abilitySystem = new AbilitySystem(player, enemies, pools, this::onEnemyKilled);
        float gearPower = game.profile == null ? 1f : game.profile.aggregatePowerMultiplier();
        player.weapon.damage *= gearPower;
        cam.position.set(0, 0, 0);
        cam.update();
        font.getData().setScale(.75f);
    }

    @Override public void render(float delta) {
        delta = Math.min(delta, .05f);
        accumulator += delta;
        while (accumulator >= GameConfig.FIXED_STEP) {
            if (!choosingUpgrade && !gameOver) update(GameConfig.FIXED_STEP);
            accumulator -= GameConfig.FIXED_STEP;
        }
        draw();
        handleOverlayInput();
    }

    private void update(float dt) {
        director.update(dt);
        fireTimer -= dt;
        contactTimer -= dt;
        Vector2 move = stick.update(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        player.velocity.set(move).scl(player.moveSpeed);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.canDash() && move.len2() > .08f) {
            player.position.mulAdd(move, 4.8f);
            player.triggerDash();
            cameraShake = Math.max(cameraShake, .12f);
            impact(player.position.x, player.position.y, .9f, .16f, Color.CYAN);
        }
        player.position.mulAdd(player.velocity, dt);
        player.position.x = MathUtils.clamp(player.position.x, -31, 31);
        player.position.y = MathUtils.clamp(player.position.y, -17, 17);

        if (director.shouldSpawn() && enemies.size < GameConfig.MAX_ENEMIES) {
            spawnEnemy();
            director.onSpawn();
        }

        Enemy target = nearestEnemy();
        if (target != null && fireTimer <= 0f) {
            fire(target);
            fireTimer = player.weapon.fireInterval;
        }

        updateEnemies(dt);
        spatial.rebuild(enemies);
        abilitySystem.update(dt);
        updatePlayerProjectiles(dt);
        updateHostileProjectiles(dt);

        for (ImpactFx f : pools.impacts) if (f.active) { f.life -= dt; if (f.life <= 0f) f.active = false; }
        for (DamageNumber n : pools.damageNumbers) n.update(dt);
        for (int i = enemies.size - 1; i >= 0; i--) if (!enemies.get(i).alive) enemies.removeIndex(i);

        cam.position.x += MathUtils.random(-1f, 1f) * cameraShake;
        cam.position.y += MathUtils.random(-1f, 1f) * cameraShake;
        cameraShake = Math.max(0f, cameraShake - dt * 2.7f);
        cam.position.x = MathUtils.lerp(cam.position.x, 0f, .08f);
        cam.position.y = MathUtils.lerp(cam.position.y, 0f, .08f);
        cam.update();
    }

    private void updateEnemies(float dt) {
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = player.position.x - e.position.x;
            float dy = player.position.y - e.position.y;
            float len2 = dx * dx + dy * dy;
            float distance = (float)Math.sqrt(Math.max(len2, .0001f));
            e.updateAi(dt, distance);

            if (e.type == Enemy.Type.BOSS && e.bossCombat != null && e.bossPhases != null) {
                int phase = e.bossPhases.phase();
                if (e.bossCombat.consumeCharge(phase)) {
                    cameraShake = Math.max(cameraShake, .16f);
                    impact(e.position.x, e.position.y, 2.8f, .24f, Color.SCARLET);
                }
                if (e.bossCombat.consumeSummon(phase)) spawnBossMinions(e, phase);
                if (e.bossCombat.consumeEnragePulse(phase)) bossEnragePulse(e);
            }

            if (len2 > .0001f) {
                float inv = 1f / distance;
                float speed = e.effectiveSpeed();
                float direction = 1f;
                if (e.type == Enemy.Type.RANGED && distance < e.attack.archetype().preferredRange) direction = -0.65f;
                if (e.attack.state() == EnemyState.TELEGRAPHING || e.attack.state() == EnemyState.RECOVERING) speed *= 0.22f;
                e.velocity.set(dx * inv * speed * direction, dy * inv * speed * direction).add(e.impulse);
            }
            e.position.mulAdd(e.velocity, dt);

            if (e.attack.consumeAttack()) resolveEnemyAttack(e);

            boolean aliveBeforeStatus = e.alive;
            e.updateStatus(dt);
            if (aliveBeforeStatus && !e.alive) onEnemyKilled(e);
            float rr = player.radius + e.radius;
            if (e.alive && e.type != Enemy.Type.RANGED && len2 < rr * rr && contactTimer <= 0f) {
                damagePlayer(e.contactDamage, .35f);
                contactTimer = .28f;
            }
        }
    }

    private void spawnBossMinions(Enemy boss, int phase) {
        int count = phase >= 3 ? 6 : 3;
        for (int i = 0; i < count && enemies.size < GameConfig.MAX_ENEMIES; i++) {
            float angle = i * (MathUtils.PI2 / count) + MathUtils.random(-.18f, .18f);
            float dist = 2.6f + MathUtils.random(0f, 1.2f);
            float x = boss.position.x + MathUtils.cos(angle) * dist;
            float y = boss.position.y + MathUtils.sin(angle) * dist;
            float scale = 1f + director.elapsed() / 210f;
            Enemy.Type type = (i % 3 == 0 && phase >= 3) ? Enemy.Type.RANGED : Enemy.Type.RUNNER;
            Enemy minion = type == Enemy.Type.RANGED
                ? new Enemy(type, x, y, 68f * scale, 2.2f, .42f, 12f, 10)
                : new Enemy(type, x, y, 30f * scale, 4.35f, .34f, 8f, 5);
            enemies.add(minion);
            impact(x, y, .65f, .18f, Color.VIOLET);
        }
        cameraShake = Math.max(cameraShake, .12f);
    }

    private void bossEnragePulse(Enemy boss) {
        int shots = 20;
        for (int i = 0; i < shots; i++) {
            spawnHostileShot(boss.position.x, boss.position.y, i * (360f / shots), 8.2f,
                boss.contactDamage * .65f, .24f, i % 4 == 0, 2.0f);
        }
        impact(boss.position.x, boss.position.y, 5.1f, .34f, Color.MAGENTA);
        cameraShake = Math.max(cameraShake, .38f);
    }

    private void updatePlayerProjectiles(float dt) {
        for (Projectile p : pools.projectiles) {
            if (!p.active) continue;
            p.position.mulAdd(p.velocity, dt);
            p.life -= dt;
            if (p.life <= 0f) { p.active = false; continue; }
            spatial.query(p.position.x, p.position.y, 1.35f, collisionCandidates);
            for (Enemy e : collisionCandidates) {
                if (!e.alive || e == p.lastHit) continue;
                float rr = p.radius + e.radius;
                if (p.position.dst2(e.position) > rr * rr) continue;
                boolean wasAlive = e.alive;
                e.damage(p.damage);
                e.hitFlash = 1f;
                e.applyElement(p.element, p.damage);
                damageNumber(e.position.x, e.position.y + e.radius, p.damage, p.critical,
                    p.critical ? Color.GOLD : Color.WHITE);
                float vlen = p.velocity.len();
                if (vlen > .001f) e.addImpulse(p.velocity.x / vlen * p.knockback, p.velocity.y / vlen * p.knockback);
                impact(p.position.x, p.position.y, p.critical ? .6f : .38f, .12f, p.critical ? Color.GOLD : Color.CYAN);
                if (p.element == DamageElement.SHOCK && e.alive) chainShock(e, p.damage * .42f, 3);
                if (wasAlive && !e.alive) onEnemyKilled(e);
                p.lastHit = e;
                if (p.penetrationRemaining > 0) p.penetrationRemaining--; else p.active = false;
                break;
            }
        }
    }

    private void updateHostileProjectiles(float dt) {
        for (EnemyProjectile p : pools.hostileProjectiles) {
            if (!p.active) continue;
            p.position.mulAdd(p.velocity, dt);
            p.life -= dt;
            if (p.life <= 0f) { p.active = false; continue; }
            float rr = p.radius + player.radius;
            if (p.position.dst2(player.position) <= rr * rr) {
                p.active = false;
                if (p.explosive) {
                    impact(p.position.x, p.position.y, p.explosionRadius, .32f, Color.ORANGE);
                    if (p.position.dst2(player.position) <= p.explosionRadius * p.explosionRadius) damagePlayer(p.damage, .48f);
                } else damagePlayer(p.damage, .22f);
            }
        }
    }

    private void resolveEnemyAttack(Enemy e) {
        aim.set(player.position).sub(e.position);
        if (aim.len2() < .0001f) aim.set(1f, 0f); else aim.nor();
        if (e.type == Enemy.Type.RANGED) {
            spawnHostileShot(e.position.x, e.position.y, aim.angleDeg(), 8.5f, e.contactDamage, .18f, false, 0f);
            return;
        }
        if (e.type == Enemy.Type.BOSS) {
            int phase = e.bossPhases == null ? 1 : e.bossPhases.phase();
            if (phase == 1) {
                for (int i = -2; i <= 2; i++) spawnHostileShot(e.position.x, e.position.y, aim.angleDeg() + i * 11f, 7.2f, e.contactDamage * .72f, .22f, false, 0f);
            } else if (phase == 2) {
                for (int i = 0; i < 10; i++) spawnHostileShot(e.position.x, e.position.y, i * 36f, 6.4f, e.contactDamage * .62f, .22f, false, 0f);
            } else {
                for (int i = 0; i < 14; i++) spawnHostileShot(e.position.x, e.position.y, i * (360f / 14f), 6.8f, e.contactDamage * .58f, .24f, i % 3 == 0, 2.2f);
            }
            cameraShake = Math.max(cameraShake, .18f + phase * .05f);
        }
    }

    private void spawnHostileShot(float x, float y, float angle, float speed, float damage, float radius, boolean explosive, float explosionRadius) {
        EnemyProjectile p = pools.hostileProjectile();
        if (p == null) return;
        shotVelocity.set(speed, 0f).setAngleDeg(angle);
        p.spawn(x, y, shotVelocity.x, shotVelocity.y, damage, radius, 4.5f, explosive, explosionRadius);
    }

    private void chainShock(Enemy source, float damage, int maxChains) {
        Enemy current = source;
        for (int chain = 0; chain < maxChains; chain++) {
            Enemy nearest = null;
            float best = 3.4f * 3.4f;
            for (Enemy candidate : enemies) {
                if (!candidate.alive || candidate == current || candidate == source) continue;
                float d2 = current.position.dst2(candidate.position);
                if (d2 < best) { best = d2; nearest = candidate; }
            }
            if (nearest == null) break;
            boolean wasAlive = nearest.alive;
            nearest.damage(damage);
            nearest.applyElement(DamageElement.SHOCK, damage);
            damageNumber(nearest.position.x, nearest.position.y + nearest.radius, damage, false, Color.CYAN);
            impact(nearest.position.x, nearest.position.y, .62f, .16f, Color.CYAN);
            if (wasAlive && !nearest.alive) onEnemyKilled(nearest);
            current = nearest;
            damage *= .78f;
        }
    }

    private void damagePlayer(float damage, float shake) {
        float hpBefore = player.hp;
        player.damage(damage);
        if (player.hp == hpBefore) return;
        cameraShake = Math.max(cameraShake, shake);
        impact(player.position.x, player.position.y, 1.1f, .18f, Color.RED);
        damageNumber(player.position.x, player.position.y + player.radius, damage, false, Color.SCARLET);
        if (!player.alive) gameOver = true;
    }

    private void damageNumber(float x, float y, float value, boolean critical, Color color) {
        DamageNumber n = pools.damageNumber();
        if (n != null) n.spawn(x, y, value, critical, color);
    }

    private void onEnemyKilled(Enemy e) {
        if (e.type == Enemy.Type.BOSS) bossKilledThisRun = true;
        director.onKill();
        if (player.addXp(e.xpValue)) prepareUpgrade();
        impact(e.position.x, e.position.y, e.radius * 2.3f, .28f, new Color(.2f, 1f, .65f, 1f));
    }

    private void spawnEnemy() {
        Enemy.Type t = director.chooseType();
        float angle = MathUtils.random(MathUtils.PI2);
        float dist = t == Enemy.Type.BOSS ? 15f : MathUtils.random(13f, 19f);
        float x = player.position.x + MathUtils.cos(angle) * dist;
        float y = player.position.y + MathUtils.sin(angle) * dist;
        float scale = 1f + director.elapsed() / 180f;
        Enemy e = switch (t) {
            case RUNNER -> new Enemy(t, x, y, 28 * scale, 4.2f, .34f, 8, 6);
            case BRUTE -> new Enemy(t, x, y, 145 * scale, 1.6f, .72f, 18, 15);
            case RANGED -> new Enemy(t, x, y, 72 * scale, 2.15f, .42f, 13, 12);
            case ELITE -> new Enemy(t, x, y, 420 * scale, 2.1f, 1.05f, 28, 42);
            case BOSS -> new Enemy(t, x, y, 2200 * scale, 1.35f, 1.65f, 24, 280);
            default -> new Enemy(t, x, y, 52 * scale, 2.55f, .46f, 10, 8);
        };
        enemies.add(e);
        if (t == Enemy.Type.BOSS) director.onBossSpawned();
    }

    private Enemy nearestEnemy() {
        Enemy best = null;
        float bestD = Float.MAX_VALUE;
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = e.position.x - player.position.x;
            float dy = e.position.y - player.position.y;
            float d = dx * dx + dy * dy;
            if (d < bestD) { bestD = d; best = e; }
        }
        return best;
    }

    private void fire(Enemy target) {
        aim.set(target.position).sub(player.position).nor();
        float base = aim.angleDeg();
        int count = player.weapon.projectileCount;
        for (int i = 0; i < count; i++) {
            float spread = (i - (count - 1) / 2f) * player.weapon.spreadDegrees;
            shotVelocity.set(player.weapon.projectileSpeed, 0f).setAngleDeg(base + spread);
            boolean crit = MathUtils.random() < player.weapon.critChance;
            Projectile p = pools.projectile();
            if (p != null) p.spawn(player.position.x, player.position.y, shotVelocity.x, shotVelocity.y,
                player.weapon.damage * (crit ? player.weapon.critMultiplier : 1f), crit,
                player.weapon.penetration, player.weapon.knockback, player.weapon.element);
        }
        cameraShake = Math.max(cameraShake, .035f);
    }

    private void impact(float x, float y, float s, float d, Color c) {
        ImpactFx f = pools.impact();
        if (f != null) f.spawn(x, y, s, d, c);
    }

    private void prepareUpgrade() {
        choosingUpgrade = true;
        UpgradeSelector.fillChoices(player, choices);
    }

    private void finishRun() {
        if (settling) return;
        settling = true;
        int stage = game.profile == null ? 1 : Math.max(1, game.profile.highestStage);
        game.finishRun(director.kills(), director.elapsed(), bossKilledThisRun, stage);
    }

    private void draw() {
        Gdx.gl.glClearColor(.015f, .022f, .03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapes.setProjectionMatrix(cam.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .04f, .052f, 1f); shapes.rect(-40, -24, 80, 48);
        shapes.setColor(.06f, .14f, .17f, .55f);
        for (int x = -40; x < 40; x += 2) shapes.rect(x, -24, .02f, 48);
        for (int y = -24; y < 24; y += 2) shapes.rect(-40, y, 80, .02f);
        for (ImpactFx f : pools.impacts) if (f.active) {
            float a = f.life / f.maxLife;
            shapes.setColor(f.color.r, f.color.g, f.color.b, a * .55f);
            shapes.circle(f.position.x, f.position.y, f.size * (1f - a * .45f), 18);
        }
        for (Projectile p : pools.projectiles) if (p.active) {
            shapes.setColor(p.critical ? Color.GOLD : Color.CYAN);
            shapes.circle(p.position.x, p.position.y, p.critical ? .16f : .11f, 12);
        }
        for (EnemyProjectile p : pools.hostileProjectiles) if (p.active) {
            shapes.setColor(p.explosive ? Color.ORANGE : Color.SCARLET);
            shapes.circle(p.position.x, p.position.y, p.radius, 14);
        }
        for (HomingMissile m : pools.homingMissiles) if (m.active) {
            shapes.setColor(m.element == DamageElement.FROST ? Color.CYAN : Color.ORANGE);
            shapes.circle(m.position.x, m.position.y, m.radius * 1.35f, 10);
            float len = m.velocity.len();
            if (len > .001f) {
                float tx = m.position.x - m.velocity.x / len * .55f;
                float ty = m.position.y - m.velocity.y / len * .55f;
                shapes.rectLine(m.position.x, m.position.y, tx, ty, .08f);
            }
        }
        drawAbilityObjects();
        for (Enemy e : enemies) {
            Color c = switch (e.type) {
                case RUNNER -> new Color(.95f, .35f, .25f, 1f);
                case BRUTE -> new Color(.6f, .12f, .16f, 1f);
                case RANGED -> new Color(.95f, .62f, .16f, 1f);
                case ELITE -> new Color(.85f, .18f, .8f, 1f);
                case BOSS -> new Color(.95f, .08f, .06f, 1f);
                default -> new Color(.35f, .75f, .42f, 1f);
            };
            if (e.attack.state() == EnemyState.TELEGRAPHING) {
                shapes.setColor(1f, .12f, .04f, .20f);
                shapes.circle(e.position.x, e.position.y, e.type == Enemy.Type.BOSS ? 4.4f : 1.1f, 28);
            }
            if (e.type == Enemy.Type.BOSS && e.bossCombat != null && e.bossCombat.charging()) {
                shapes.setColor(1f, .15f, .05f, .18f);
                shapes.circle(e.position.x, e.position.y, 2.4f, 28);
            }
            if (e.hitFlash > 0f) c = Color.WHITE;
            shapes.setColor(c); shapes.circle(e.position.x, e.position.y, e.radius, 20);
            shapes.setColor(.1f, .1f, .1f, .8f); shapes.rect(e.position.x - e.radius, e.position.y + e.radius + .12f, e.radius * 2f, .07f);
            shapes.setColor(Color.RED); shapes.rect(e.position.x - e.radius, e.position.y + e.radius + .12f, e.radius * 2f * (e.hp / e.maxHp), .07f);
        }
        shapes.setColor(player.invulnerable() ? Color.WHITE : new Color(.12f, .85f, 1f, 1f));
        shapes.circle(player.position.x, player.position.y, player.radius, 24);
        shapes.end();
        drawCombatText();
        drawHud();
    }

    private void drawAbilityObjects() {
        float angle = abilitySystem.runtime().orbitalAngle;
        if (player.abilities.unlocked(AbilityType.DRONE)) {
            shapes.setColor(Color.LIME);
            shapes.circle(player.position.x + MathUtils.cosDeg(angle + 180f) * 1.8f,
                player.position.y + MathUtils.sinDeg(angle + 180f) * 1.8f, .20f, 12);
        }
        if (player.abilities.unlocked(AbilityType.ORBITAL_BLADE)) {
            float orbit = 2f + player.abilities.level(AbilityType.ORBITAL_BLADE) * .12f;
            shapes.setColor(Color.GOLD);
            shapes.rect(player.position.x + MathUtils.cosDeg(angle) * orbit - .12f,
                player.position.y + MathUtils.sinDeg(angle) * orbit - .34f, .24f, .68f);
        }
    }

    private void drawCombatText() {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        font.getData().setScale(.034f);
        for (DamageNumber n : pools.damageNumbers) {
            if (!n.active) continue;
            float alpha = MathUtils.clamp(n.life / n.maxLife, 0f, 1f);
            font.setColor(n.color.r, n.color.g, n.color.b, alpha);
            font.getData().setScale(n.critical ? .050f : .034f);
            font.draw(batch, Integer.toString(Math.max(1, Math.round(n.value))), n.x - .45f, n.y, .9f, Align.center, false);
        }
        batch.end();
        font.getData().setScale(.75f);
    }

    private void drawHud() {
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        hudMatrix.setToOrtho2D(0, 0, w, h);
        shapes.setProjectionMatrix(hudMatrix);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.02f, .03f, .04f, .8f); shapes.rect(24, h - 58, w * .32f, 22);
        shapes.setColor(.08f, .8f, .95f, 1f); shapes.rect(28, h - 54, (w * .32f - 8) * (player.hp / player.maxHp), 14);
        shapes.setColor(.02f, .03f, .04f, .8f); shapes.rect(w * .34f, h - 58, w * .32f, 22);
        shapes.setColor(.55f, .25f, 1f, 1f); shapes.rect(w * .34f + 4, h - 54, (w * .32f - 8) * (player.xp / (float)player.xpNext), 14);
        shapes.end();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "HP " + (int)player.hp + " / " + (int)player.maxHp, 32, h - 64);
        font.draw(batch, "LV " + player.level, w * .34f + 8, h - 64);
        font.draw(batch, "KILLS  " + director.kills(), w - 190, h - 42);
        font.draw(batch, String.format("%02d:%02d", (int)director.elapsed() / 60, (int)director.elapsed() % 60), w - 190, h - 68);
        font.draw(batch, "DASH " + (player.canDash() ? "READY" : String.format("%.1f", player.dashTimer)), 28, 48);
        if (choosingUpgrade) drawUpgradeText(w, h);
        if (gameOver) drawGameOverText(w, h);
        batch.end();
    }

    private void drawUpgradeText(float w, float h) {
        font.getData().setScale(1.3f); font.setColor(Color.WHITE);
        font.draw(batch, "PROTOCOL UPGRADE", 0, h * .76f, w, Align.center, false);
        font.getData().setScale(.72f);
        for (int i = 0; i < 3; i++) {
            float x = w * (.17f + i * .33f);
            font.setColor(Color.CYAN); font.draw(batch, "[" + (i + 1) + "] " + choices[i].title, x - 120, h * .52f, 240, Align.center, false);
            font.setColor(Color.LIGHT_GRAY); font.draw(batch, choices[i].rarity.name() + " • " + choices[i].description, x - 120, h * .45f, 240, Align.center, true);
        }
        font.getData().setScale(.75f);
    }

    private void drawGameOverText(float w, float h) {
        font.getData().setScale(1.5f); font.setColor(Color.WHITE);
        font.draw(batch, "SIGNAL LOST", 0, h * .62f, w, Align.center, false);
        font.getData().setScale(.72f);
        font.draw(batch, revived ? "TAP / ENTER FOR RESULTS" : "TAP TO REVIVE (rewarded ad)  •  ENTER FOR RESULTS", 0, h * .45f, w, Align.center, false);
        font.getData().setScale(.75f);
    }

    private void handleOverlayInput() {
        if (choosingUpgrade) {
            int idx = -1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) idx = 0;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) idx = 1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) idx = 2;
            if (Gdx.input.justTouched()) idx = Math.min(2, (int)(Gdx.input.getX() / (float)Gdx.graphics.getWidth() * 3));
            if (idx >= 0) { choices[idx].apply(player); choosingUpgrade = false; }
        }
        if (gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                finishRun();
                return;
            }
            if (Gdx.input.justTouched()) {
                if (!revived) game.services.ads.showRewarded(AdsService.Reward.REVIVE, () -> {
                    player.alive = true;
                    player.hp = player.maxHp * .45f;
                    gameOver = false;
                    revived = true;
                    game.services.ads.preload();
                }, this::finishRun);
                else finishRun();
            }
        }
        if (!gameOver && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) finishRun();
    }

    @Override public void resize(int width, int height) {
        cam.viewportWidth = GameConfig.WORLD_WIDTH;
        cam.viewportHeight = GameConfig.WORLD_WIDTH * ((float)height / width);
        cam.update();
    }

    @Override public void dispose() {
        shapes.dispose();
        batch.dispose();
        font.dispose();
    }
}
