package com.deadlinezero.game.visual;

import java.util.WeakHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.world.BiomeEnemyRoster;

/** Optional authored combat audio. Missing files are treated as silent fallbacks, never build blockers. */
public final class CombatAudioLayer implements Disposable {
    private static final float BIOME_CUE_RANGE2 = 12f * 12f;

    private final Sound shot = load("audio/combat/shot.ogg");
    private final Sound dash = load("audio/combat/dash.ogg");
    private final Sound levelUp = load("audio/combat/level_up.ogg");
    private final Sound legendaryOverdrive = load("audio/combat/legendary_overdrive.ogg");
    private final Sound legendarySingularity = load("audio/combat/legendary_singularity.ogg");
    private final Sound legendaryApex = load("audio/combat/legendary_apex.ogg");
    private final Sound bossAlpha = load("audio/combat/boss_alpha_intro.ogg");
    private final Sound bossRevenant = load("audio/combat/boss_revenant_intro.ogg");
    private final Sound bossPhase = load("audio/combat/boss_phase.ogg");
    private final Sound forgeHound = load("audio/combat/forge_hound_attack.ogg");
    private final Sound cinderGunner = load("audio/combat/cinder_gunner_attack.ogg");
    private final Sound slagGuard = load("audio/combat/slag_guard_attack.ogg");
    private final Sound phaseStalker = load("audio/combat/phase_stalker_attack.ogg");
    private final Sound staticSeer = load("audio/combat/static_seer_attack.ogg");
    private final Sound nullWard = load("audio/combat/null_ward_attack.ogg");

    private final WeakHashMap<Enemy, Integer> bossPhases = new WeakHashMap<>();
    private final WeakHashMap<Enemy, EnemyState> enemyStates = new WeakHashMap<>();
    private long seenShotSerial;
    private long seenDashSerial;
    private long seenLevelUpSerial;
    private boolean seenOverdrive;
    private boolean seenSingularity;
    private boolean seenApex;

    public CombatAudioLayer() {
        AudioManifest.validate();
    }

    public void update(Player player, Array<Enemy> enemies) {
        long shotSerial = CombatVisualEvents.playerShotSerial();
        if (shotSerial != seenShotSerial) {
            seenShotSerial = shotSerial;
            play(shot, .32f);
        }

        long dashSerial = CombatVisualEvents.dashSerial();
        if (dashSerial != seenDashSerial) {
            seenDashSerial = dashSerial;
            play(dash, .50f);
        }

        long levelSerial = CombatVisualEvents.levelUpSerial();
        if (levelSerial != seenLevelUpSerial) {
            seenLevelUpSerial = levelSerial;
            play(levelUp, .62f);
        }

        if (player.legendary.hasOverdrive() && !seenOverdrive) {
            seenOverdrive = true;
            play(legendaryOverdrive, .82f);
        }
        if (player.legendary.hasSingularity() && !seenSingularity) {
            seenSingularity = true;
            play(legendarySingularity, .82f);
        }
        if (player.legendary.hasApex() && !seenApex) {
            seenApex = true;
            play(legendaryApex, .88f);
        }

        Enemy biomeCueEnemy = null;
        BiomeEnemyRoster.Identity biomeCueIdentity = BiomeEnemyRoster.Identity.NONE;
        float biomeCueDistance2 = BIOME_CUE_RANGE2;

        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;
            if (enemy.type == Enemy.Type.BOSS && enemy.bossCombat != null && enemy.bossPhases != null) {
                Integer previous = bossPhases.get(enemy);
                int phase = enemy.bossPhases.phase();
                if (previous == null) {
                    bossPhases.put(enemy, phase);
                    play(enemy.bossCombat.revenant() ? bossRevenant : bossAlpha, .78f);
                } else if (phase != previous) {
                    bossPhases.put(enemy, phase);
                    play(bossPhase, .72f);
                }
                continue;
            }

            EnemyState state = enemy.attack.state();
            EnemyState previousState = enemyStates.put(enemy, state);
            boolean enteredAttack = state == EnemyState.ATTACKING && previousState != EnemyState.ATTACKING;
            if (!enteredAttack) continue;

            BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), enemy.type);
            if (identity == BiomeEnemyRoster.Identity.NONE) continue;
            float distance2 = player.position.dst2(enemy.position);
            if (distance2 < biomeCueDistance2) {
                biomeCueDistance2 = distance2;
                biomeCueEnemy = enemy;
                biomeCueIdentity = identity;
            }
        }

        if (biomeCueEnemy != null) playBiomeCue(biomeCueIdentity, biomeCueDistance2);
    }

    private void playBiomeCue(BiomeEnemyRoster.Identity identity, float distance2) {
        Sound sound = switch (identity) {
            case FORGE_HOUND -> forgeHound;
            case CINDER_GUNNER -> cinderGunner;
            case SLAG_GUARD -> slagGuard;
            case PHASE_STALKER -> phaseStalker;
            case STATIC_SEER -> staticSeer;
            case NULL_WARD -> nullWard;
            default -> null;
        };
        float distance = (float)Math.sqrt(Math.max(0f, distance2));
        float proximity = Math.max(0f, Math.min(1f, 1f - distance / 12f));
        play(sound, .16f + proximity * .28f);
    }

    private Sound load(String path) {
        try {
            if (!Gdx.files.internal(path).exists()) return null;
            return Gdx.audio.newSound(Gdx.files.internal(path));
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    private void play(Sound sound, float volume) {
        if (sound != null) sound.play(volume);
    }

    @Override public void dispose() {
        dispose(shot);
        dispose(dash);
        dispose(levelUp);
        dispose(legendaryOverdrive);
        dispose(legendarySingularity);
        dispose(legendaryApex);
        dispose(bossAlpha);
        dispose(bossRevenant);
        dispose(bossPhase);
        dispose(forgeHound);
        dispose(cinderGunner);
        dispose(slagGuard);
        dispose(phaseStalker);
        dispose(staticSeer);
        dispose(nullWard);
    }

    private void dispose(Sound sound) {
        if (sound != null) sound.dispose();
    }
}
