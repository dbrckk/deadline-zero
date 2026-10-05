extends SceneTree

func _initialize() -> void:
    var project_source := FileAccess.get_file_as_string("res://project.godot")
    var preset_source := FileAccess.get_file_as_string("res://export_presets.cfg")

    var required_project := [
        'config/name="Deadline: Zero"',
        'window/handheld/orientation=4',
        'renderer/rendering_method="mobile"'
    ]
    for token in required_project:
        if not project_source.contains(token):
            push_error("Android Play project contract missing: %s" % token)
            quit(1)
            return

    var required_release := [
        'name="Android Play Release"',
        'gradle_build/use_gradle_build=true',
        'gradle_build/export_format=1',
        'gradle_build/min_sdk="26"',
        'gradle_build/target_sdk="36"',
        'architectures/armeabi-v7a=false',
        'architectures/arm64-v8a=true',
        'architectures/x86=false',
        'architectures/x86_64=false',
        'version/code=1',
        'version/name="0.1.0"',
        'package/unique_name="com.deadlinezero.game"',
        'package/name="Deadline: Zero"',
        'package/signed=true',
        'package/show_as_launcher_app=true',
        'launcher_icons/main_192x192="res://assets/ui/deadline_zero_icon.svg"',
        'launcher_icons/adaptive_foreground_432x432="res://assets/ui/deadline_zero_adaptive_foreground.svg"',
        'launcher_icons/adaptive_background_432x432="res://assets/ui/deadline_zero_adaptive_background.svg"',
        'launcher_icons/adaptive_monochrome_432x432="res://assets/ui/deadline_zero_adaptive_monochrome.svg"',
        'user_data_backup/allow=false'
    ]
    for token in required_release:
        if not preset_source.contains(token):
            push_error("Android Play export contract missing: %s" % token)
            quit(1)
            return

    for icon_path in [
        "res://assets/ui/deadline_zero_icon.svg",
        "res://assets/ui/deadline_zero_adaptive_foreground.svg",
        "res://assets/ui/deadline_zero_adaptive_background.svg",
        "res://assets/ui/deadline_zero_adaptive_monochrome.svg"
    ]:
        if not ResourceLoader.exists(icon_path):
            push_error("Android launcher icon asset missing: %s" % icon_path)
            quit(1)
            return

    if not preset_source.contains('name="Android Debug"') or not preset_source.contains('package/unique_name="com.deadlinezero.godot"'):
        push_error("Debug package must remain isolated from final Play application ID")
        quit(1)
        return

    print("Deadline Zero Android Play export contract: OK")
    quit(0)
