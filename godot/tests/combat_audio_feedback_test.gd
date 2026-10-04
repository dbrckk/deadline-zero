extends SceneTree

func _init() -> void:
    var profiles := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]
    for profile in profiles:
        var authored := DZCombatAudio.authored_shot_stream(profile)
        var routed := DZCombatAudio.shot_stream(profile)
        assert(authored != null)
        assert(routed == authored)
        assert(routed.get_length() > 0.15)
        assert(routed.get_length() < 0.60)

    var impact_cases := [
        [false, false, false],
        [true, false, false],
        [false, true, false],
        [false, false, true]
    ]
    var impact_lengths := []
    for case in impact_cases:
        var authored := DZCombatAudio.authored_impact_stream(bool(case[0]), bool(case[1]), bool(case[2]))
        var routed := DZCombatAudio.impact_stream(bool(case[0]), bool(case[1]), bool(case[2]))
        assert(authored != null)
        assert(routed == authored)
        assert(routed.get_length() > 0.12)
        assert(routed.get_length() < 0.75)
        impact_lengths.append(routed.get_length())

    assert(impact_lengths[0] < impact_lengths[1])
    assert(impact_lengths[1] < impact_lengths[2])
    assert(impact_lengths[2] < impact_lengths[3])

    var boss := DZCombatAudio.boss_stinger()
    assert(boss != null)
    assert(boss.mix_rate == 44100)
    assert(boss.format == AudioStreamWAV.FORMAT_16_BITS)
    assert(not boss.stereo)

    var player_source := FileAccess.get_file_as_string("res://scripts/Player.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    assert(player_source.contains("for voice_index in range(3)"))
    assert(player_source.contains("shot_audio_voices"))
    assert(main_source.contains("for voice_index in range(4)"))
    assert(main_source.contains("impact_audio_voices"))
    print("combat audio authored routing test passed")
    quit()
