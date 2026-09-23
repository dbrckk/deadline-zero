extends SceneTree

func _init() -> void:
    var vanguard := DZCombatAudio.shot_stream("vanguard")
    var scatter := DZCombatAudio.shot_stream("scatter")
    var rail := DZCombatAudio.shot_stream("rail")
    var hit := DZCombatAudio.impact_stream(false, false, false)
    var critical := DZCombatAudio.impact_stream(true, false, false)
    var boss := DZCombatAudio.boss_stinger()

    assert(vanguard != null)
    assert(scatter != null)
    assert(rail != null)
    assert(hit != null)
    assert(critical != null)
    assert(boss != null)
    assert(vanguard.mix_rate == 22050)
    assert(hit.mix_rate == 22050)
    assert(vanguard.data.size() > 1000)
    assert(scatter.data.size() > vanguard.data.size())
    assert(boss.data.size() > critical.data.size())
    print("combat audio feedback test passed")
    quit()
