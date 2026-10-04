extends SceneTree

const EXPECTED := [
    "res://assets/audio/authored/weapon_vanguard.ogg",
    "res://assets/audio/authored/weapon_scatter.ogg",
    "res://assets/audio/authored/weapon_rail.ogg",
    "res://assets/audio/authored/weapon_inferno.ogg",
    "res://assets/audio/authored/weapon_cryo.ogg",
    "res://assets/audio/authored/weapon_arc.ogg",
    "res://assets/audio/authored/impact_hit.ogg",
    "res://assets/audio/authored/impact_critical.ogg",
    "res://assets/audio/authored/impact_kill.ogg",
    "res://assets/audio/authored/impact_boss.ogg"
]

func _init() -> void:
    for path in EXPECTED:
        if not ResourceLoader.exists(path):
            push_error("Missing authored audio asset: %s" % path)
            quit(1)
            return
        var stream := load(path) as AudioStream
        if stream == null or stream.get_length() <= 0.10:
            push_error("Invalid authored audio stream: %s" % path)
            quit(1)
            return

    var readme := FileAccess.get_file_as_string("res://assets/audio/authored/README.md")
    if not readme.contains("No third-party samples") or not readme.contains("20261004"):
        push_error("Authored audio provenance contract is incomplete")
        quit(1)
        return

    print("Deadline Zero authored audio assets: OK")
    quit(0)
