extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)

    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if hud.game_over_panel == null:
        push_error("Game-over panel was not created")
        quit(1)
        return
    if hud.game_over_panel.visible:
        push_error("Game-over panel should start hidden")
        quit(1)
        return

    hud.show_game_over(37, 8, 154.0)

    if not hud.game_over_panel.visible:
        push_error("Game-over panel did not become visible")
        quit(1)
        return
    if hud.game_over_summary == null:
        push_error("Game-over summary label is missing")
        quit(1)
        return
    if hud.game_over_summary.text != "LEVEL 8   •   KILLS 37   •   02:34":
        push_error("Unexpected game-over summary: %s" % hud.game_over_summary.text)
        quit(1)
        return

    var restart_button := hud.game_over_panel.find_child("RestartButton", true, false) as Button
    if restart_button == null:
        push_error("Redeploy button is missing")
        quit(1)
        return

    var restart_state := {"signaled": false}
    hud.restart_requested.connect(func() -> void:
        restart_state["signaled"] = true
    )
    restart_button.pressed.emit()
    await process_frame

    if not bool(restart_state["signaled"]):
        push_error("Redeploy button did not emit restart_requested")
        quit(1)
        return

    print("Deadline Zero run-end UX: OK")
    quit(0)
