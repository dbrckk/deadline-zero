extends SceneTree

const EXPECTED := [
    "res://assets/audio/authored/weapon_vanguard.wav",
    "res://assets/audio/authored/weapon_scatter.wav",
    "res://assets/audio/authored/weapon_rail.wav",
    "res://assets/audio/authored/weapon_inferno.wav",
    "res://assets/audio/authored/weapon_cryo.wav",
    "res://assets/audio/authored/weapon_arc.wav",
    "res://assets/audio/authored/impact_hit.wav",
    "res://assets/audio/authored/impact_critical.wav",
    "res://assets/audio/authored/impact_kill.wav",
    "res://assets/audio/authored/impact_boss.wav",
    "res://assets/audio/authored/boss_stinger.wav",
    "res://assets/audio/authored/music_run_loop.wav",
    "res://assets/audio/authored/music_pressure_layer.wav",
    "res://assets/audio/authored/ui_level_up.wav",
    "res://assets/audio/authored/ui_upgrade_confirm.wav",
    "res://assets/audio/authored/ui_pause_toggle.wav",
    "res://assets/audio/authored/ui_game_over.wav"
]

func _init() -> void:
    for path in EXPECTED:
        if not ResourceLoader.exists(path):
            push_error("Missing generated authored audio asset: %s" % path)
            quit(1)
            return
        var stream := load(path) as AudioStream
        if stream == null or stream.get_length() <= 0.10:
            push_error("Invalid generated authored audio stream: %s" % path)
            quit(1)
            return

    var music := DZCombatAudio.run_music_stream()
    if music == null or music.get_length() < 11.5:
        push_error("Authored run music is missing or too short")
        quit(1)
        return
    if music is AudioStreamWAV and (music as AudioStreamWAV).loop_mode != AudioStreamWAV.LOOP_FORWARD:
        push_error("Authored run music is not configured for forward looping")
        quit(1)
        return

    var pressure := DZCombatAudio.pressure_music_stream()
    if pressure == null or pressure.get_length() < 11.5:
        push_error("Authored pressure music is missing or too short")
        quit(1)
        return

    for key in ["level_up", "upgrade_confirm", "pause_toggle", "game_over"]:
        var cue := DZCombatAudio.ui_stream(key)
        if cue == null or cue.get_length() < 0.14:
            push_error("Authored UI/progression cue is missing or too short: %s" % key)
            quit(1)
            return

    var boss := DZCombatAudio.boss_stinger()
    if boss == null or boss.get_length() < 2.0:
        push_error("Authored boss stinger is missing or too short")
        quit(1)
        return

    var readme := FileAccess.get_file_as_string("res://assets/audio/authored/README.md")
    if not readme.contains("No third-party samples") or not readme.contains("20261004"):
        push_error("Authored audio provenance contract is incomplete")
        quit(1)
        return

    print("Deadline Zero generated authored audio assets: OK")
    quit(0)
