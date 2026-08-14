package com.deadlinezero.game.visual;

import java.util.WeakHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;

/** Optional authored combat audio. Missing files are treated as silent fallbacks, never build blockers. */
public final class CombatAudioLayer implements Disposable {
    private final Sound shot = load("audio/combat/shot.ogg");
    private final Sound dash = load("audio/combat/dash.ogg");
    private final Sound levelUp = load("audio/combat/level_up.ogg");
    private final Sound legendaryOverdrive = load("audio/combat/legendary_overdrive.ogg");
    private final Sound legendarySingularity = load("audio/combat/legendary_singularity.ogg");
    private final Sound legendaryApex = load("audio/combat/legendary_apex.ogg");
    private final Sound bossAlpha = load("audio/combat/boss_alpha_intro.ogg");
    private final Sound bossRevenant = load("audio/combat/boss_revenant_intro.ogg");
    private final Sound bossPhase = load("audio/combat/boss_phase.ogg");

    private final WeakHashMap<Enemy, Integer> bossPhases = new WeakHashMap<>();
    private long seenShotSerial;
    private long seenDashSerial;
    private long seenLevelUpSerial;
    private boolean seenOverdrive;
    private boolean seenSingularity;
    private boolean seenApex;

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

        for (Enemy enemy : enemies) {
            if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossCombat == null || enemy.bossPhases == null) continue;
            Integer previous = bossPhases.get(enemy);
            int phase = enemy.bossPhases.phase();
            if (previous == null) {
                bossPhases.put(enemy, phase);
                play(enemy.bossCombat.revenant() ? bossRevenant : bossAlpha, .78f);
            } else if (phase != previous) {
                bossPhases.put(enemy, phase);
                play(bossPhase, .72f);
            }
        }
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
    }

    private void dispose(Sound sound) {
        if (sound != null) sound.dispose();
    }
}
