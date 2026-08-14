package com.deadlinezero.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;

/** Resilient production audio gateway. Missing files degrade to safe fallbacks or silence instead of breaking the game. */
public final class AudioDirector {
    public enum Cue { SHOT, CRIT, HIT, KILL, BOSS_HIT, BOSS_PHASE, BOSS_KILL, DASH, LEVEL_UP, UI_SELECT, UI_BACK, SINGULARITY, ION_OVERCHARGE, CINDER_OVERHEAT }

    private static AudioDirector active;
    private final ObjectMap<Cue, Sound> sounds = new ObjectMap<>();
    private final ObjectMap<MusicProfileSelector.Profile, Music> combatMusic = new ObjectMap<>();
    private final AudioCueLimiter limiter = new AudioCueLimiter();
    private Music activeCombatMusic;
    private int suspensionDepth;
    private boolean resumeAfterSuspension;
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
        load(Cue.SINGULARITY, "audio/sfx/singularity.ogg");
        load(Cue.ION_OVERCHARGE, "audio/sfx/ion_overcharge.ogg");
        load(Cue.CINDER_OVERHEAT, "audio/sfx/cinder_overheat.ogg");
        for (MusicProfileSelector.Profile profile : MusicProfileSelector.Profile.values()) loadMusic(profile, MusicProfileSelector.assetPath(profile));
    }

    private void load(Cue cue, String path) {
        FileHandle file = Gdx.files.internal(path);
        if (file.exists()) sounds.put(cue, Gdx.audio.newSound(file));
    }

    private void loadMusic(MusicProfileSelector.Profile profile, String path) {
        FileHandle file = Gdx.files.internal(path);
        if (!file.exists()) return;
        Music track = Gdx.audio.newMusic(file);
        track.setLooping(true);
        track.setVolume(master * music);
        combatMusic.put(profile, track);
    }

    static Cue fallbackCue(Cue cue) {
        return switch (cue) {
            case BOSS_PHASE -> Cue.BOSS_HIT;
            case SINGULARITY, ION_OVERCHARGE -> Cue.CRIT;
            case CINDER_OVERHEAT -> Cue.BOSS_HIT;
            default -> null;
        };
    }

    private Sound resolveSound(Cue cue) {
        Sound sound = sounds.get(cue);
        if (sound != null) return sound;
        Cue fallback = fallbackCue(cue);
        return fallback == null ? null : sounds.get(fallback);
    }

    private Music resolveMusic(MusicProfileSelector.Profile profile) {
        Music track = combatMusic.get(profile);
        if (track != null) return track;
        return combatMusic.get(MusicProfileSelector.Profile.SURVIVAL);
    }

    public static void playGlobal(Cue cue) { if (active != null) active.play(cue); }
    public static void playGlobal(Cue cue, float pitch, float pan) { if (active != null) active.play(cue, pitch, pan); }

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

    public void startCombatMusic() { startCombatMusic(1); }
    public void startCombatMusic(int stage) {
        Music next = resolveMusic(MusicProfileSelector.forStage(stage));
        if (next == null) return;
        if (activeCombatMusic != null && activeCombatMusic != next && activeCombatMusic.isPlaying()) activeCombatMusic.stop();
        activeCombatMusic = next;
        activeCombatMusic.setVolume(master * music);
        if (suspensionDepth > 0) {
            resumeAfterSuspension = true;
            if (activeCombatMusic.isPlaying()) activeCombatMusic.pause();
            return;
        }
        resumeAfterSuspension = false;
        if (!activeCombatMusic.isPlaying()) activeCombatMusic.play();
    }

    public void stopCombatMusic() {
        if (activeCombatMusic != null) activeCombatMusic.stop();
        activeCombatMusic = null;
        resumeAfterSuspension = false;
    }

    public void suspend() {
        if (suspensionDepth == 0 && activeCombatMusic != null) {
            resumeAfterSuspension = activeCombatMusic.isPlaying();
            if (resumeAfterSuspension) activeCombatMusic.pause();
        }
        suspensionDepth++;
    }

    public void resume() {
        if (suspensionDepth <= 0) return;
        suspensionDepth--;
        if (suspensionDepth > 0 || !resumeAfterSuspension || activeCombatMusic == null) return;
        resumeAfterSuspension = false;
        activeCombatMusic.setVolume(master * music);
        if (!activeCombatMusic.isPlaying()) activeCombatMusic.play();
    }

    public void setVolumes(float master, float sfx, float music) {
        this.master = normalizeVolume(master);
        this.sfx = normalizeVolume(sfx);
        this.music = normalizeVolume(music);
        applyMusicVolume();
    }

    private void applyMusicVolume() {
        float volume = master * music;
        for (Music track : combatMusic.values()) track.setVolume(volume);
    }

    static float normalizeVolume(float value) {
        if (!Float.isFinite(value)) return 0f;
        return Math.max(0f, Math.min(1f, value));
    }

    public void dispose() {
        if (active == this) active = null;
        limiter.reset();
        for (Sound sound : sounds.values()) sound.dispose();
        sounds.clear();
        for (Music track : combatMusic.values()) track.dispose();
        combatMusic.clear();
        activeCombatMusic = null;
        suspensionDepth = 0;
        resumeAfterSuspension = false;
    }
}
