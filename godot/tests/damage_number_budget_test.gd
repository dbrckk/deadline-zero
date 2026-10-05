extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var enemy := DZEnemy.new()
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    for index in range(DZEnemy.MAX_DAMAGE_NUMBERS):
        var filler := Label3D.new()
        filler.name = "DamageNumberBudgetFiller_%d" % index
        filler.add_to_group("damage_numbers")
        root.add_child(filler)

    var baseline := get_nodes_in_group("damage_numbers").size()
    if baseline != DZEnemy.MAX_DAMAGE_NUMBERS:
        push_error("Damage number budget fixture did not reach configured cap")
        quit(1)
        return

    enemy._spawn_damage_number(12.0, false, false)
    if get_nodes_in_group("damage_numbers").size() != baseline:
        push_error("Ordinary damage number bypassed mobile clutter budget")
        quit(1)
        return

    enemy._spawn_damage_number(24.0, true, false)
    if get_nodes_in_group("damage_numbers").size() != baseline + 1:
        push_error("Critical damage number was incorrectly dropped at budget cap")
        quit(1)
        return

    var readable_critical: Label3D
    for node in get_nodes_in_group("damage_numbers"):
        if node is Label3D and str(node.name).begins_with("DamageNumber_"):
            readable_critical = node as Label3D
            break
    if readable_critical == null or readable_critical.pixel_size < 0.0067 or readable_critical.font_size < 44:
        push_error("Critical damage number is below phone-scale readability contract")
        quit(1)
        return

    enemy._spawn_damage_number(60.0, false, true)
    if get_nodes_in_group("damage_numbers").size() != baseline + 2:
        push_error("Kill damage number was incorrectly dropped at budget cap")
        quit(1)
        return

    print("Deadline Zero damage-number budget: OK")
    quit(0)
