extends SceneTree

func _init() -> void:
    # Validate synthesis metadata only. Reading AudioStreamWAV.data from a headless
    # Godot process has proven disproportionately slow in CI and does not add
    # meaningful coverage over construction + duration/profile checks.
    var profiles := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]
    var durations := []
    for profile in profiles:
        var stream := DZCombatAudio.shot_stream(profile)
        assert(stream != null)
        assert(stream.mix_rate == 22050)
        assert(stream.format == AudioStreamWAV.FORMAT_16_BITS)
        assert(not stream.stereo)
        assert(stream.get_length() > 0.04)
        assert(stream.get_length() < 0.12)
        durations.append(stream.get_length())

    var hit := DZCombatAudio.impact_stream(false, false, false)
    var critical := DZCombatAudio.impact_stream(true, false, false)
    var killed := DZCombatAudio.impact_stream(false, true, false)
    var boss_hit := DZCombatAudio.impact_stream(false, false, true)
    var boss := DZCombatAudio.boss_stinger()

    for stream in [hit, critical, killed, boss_hit, boss]:
        assert(stream != null)
        assert(stream.mix_rate == 22050)
        assert(stream.format == AudioStreamWAV.FORMAT_16_BITS)
        assert(not stream.stereo)

    assert(hit.get_length() < critical.get_length())
    assert(critical.get_length() < killed.get_length())
    assert(killed.get_length() < boss_hit.get_length())
    assert(boss_hit.get_length() < boss.get_length())
    assert(durations[1] > durations[0])
    var player_source := FileAccess.get_file_as_string("res://scripts/Player.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    assert(player_source.contains("for voice_index in range(3)"))
    assert(player_source.contains("shot_audio_voices"))
    assert(main_source.contains("for voice_index in range(4)"))
    assert(main_source.contains("impact_audio_voices"))
    print("combat audio feedback test passed")
    quit()
