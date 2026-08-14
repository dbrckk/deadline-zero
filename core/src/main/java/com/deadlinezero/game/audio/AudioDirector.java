package com.deadlinezero.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;

/** Resilient production audio gateway. Missing files degrade to safe fallbacks or silence instead of breaking the game. */
public final class AudioDirector {
    public enum Cue { SHOT, CRIT, HIT, KILL, BOSS_HIT, BOSS_PHASE, BOSS_KILL, DASH, LEVEL_UP, UI_SELECT, UI_BACK }

    private static AudioDirector active;
    private final ObjectMap<Cue, Sound> sounds = new ObjectMap<>();
    private final AudioCueLimiter limiter = new AudioCueLimiter();
    private Music combatMusic;
    private float master = 1f;
    private float sfx = .85f;
    private float music = .65f;

    public AudioDirector() {
        active = this;
        load(Cue.SHOT, "audio/sfx/shot.ogg");
        load(Cue.CRIT, "audio/sfx/crit.ogg");
        load(Cue.HIT, "audio/sfx/hit.ogg");
        load(Cue.KILL, "audio/sfx/kill.ogg");
        load(Cue.BOSS_HIT, "audio/sfx/boss_hit.ogg");
        load(Cue.BOSS_PHASE, "audio/sfx/boss_phase.ogg");
        load(Cue.BOSS_KILL, "audio/sfx/boss_kill.ogg");
        load(Cue.DASH, "audio/sfx/dash.ogg");
        load(Cue.LEVEL_UP, "audio/sfx/level_up.ogg");
        load(Cue.UI_SELECT, "audio/sfx/ui_select.ogg");
        load(Cue.UI_BACK, "audio/sfx/ui_back.ogg");
        FileHandle musicFile = Gdx.files.internal("audio/music/combat.ogg");
        if (musicFile.exists()) {
            combatMusic = Gdx.audio.newMusic(musicFile);
            combatMusic.setLooping(true);
            applyMusicVolume();
        }
    }

    private void load(Cue cue, String path) {
        FileHandle file = Gdx.files.internal(path);
        if (file.exists()) sounds.put(cue, Gdx.audio.newSound(file));
    }

    static Cue fallbackCue(Cue cue) {
        return cue == Cue.BOSS_PHASE ? Cue.BOSS_HIT : null;
    }

    private Sound resolveSound(Cue cue) {
        Sound sound = sounds.get(cue);
        if (sound != null) return sound;
        Cue fallback = fallbackCue(cue);
        return fallback == null ? null : sounds.get(fallback);
    }

    public static void playGlobal(Cue cue) {
        if (active != null) active.play(cue);
    }

    public static void playGlobal(Cue cue, float pitch, float pan) {
        if (active != null) active.play(cue, pitch, pan);
    }

    public void play(Cue cue) {
        Sound sound = resolveSound(cue);
        if (sound == null || !limiter.allow(cue, TimeUtils.nanoTime())) return;
        sound.play(master * sfx);
    }

    public void play(Cue cue, float pitch, float pan) {
        Sound sound = resolveSound(cue);
        if (sound == null || !limiter.allow(cue, TimeUtils.nanoTime())) return;
        sound.play(master * sfx, Math.max(.5f, Math.min(2f, pitch)), Math.max(-1f, Math.min(1f, pan)));
    }

    public void startCombatMusic() {
        if (combatMusic != null && !combatMusic.isPlaying()) combatMusic.play();
    }

    public void stopCombatMusic() {
        if (combatMusic != null) combatMusic.stop();
    }

    public void setVolumes(float master, float sfx, float music) {
        this.master = clamp01(master);
        this.sfx = clamp01(sfx);
        this.music = clamp01(music);
        applyMusicVolume();
    }

    private void applyMusicVolume() {
        if (combatMusic != null) combatMusic.setVolume(master * music);
    }

    private float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }

    public void dispose() {
        if (active == this) active = null;
        limiter.reset();
        for (Sound sound : sounds.values()) sound.dispose();
        sounds.clear();
        if (combatMusic != null) combatMusic.dispose();
    }
}
