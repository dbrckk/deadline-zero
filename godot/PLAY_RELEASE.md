# Godot Play release contract

The Godot runtime now has a dedicated Google Play export preset named `Android Play Release`.

## Mechanical release configuration

- Package ID: `com.deadlinezero.game`
- Display name: `Deadline: Zero`
- Version code: `1`
- Version name: `0.1.0`
- Minimum SDK: 26
- Target SDK: 36
- Distribution format: Android App Bundle (AAB)
- Production ABI: arm64-v8a only
- Gradle export: required/enabled
- Backup: disabled
- Immersive/edge-to-edge: enabled

The `Godot Play AAB Verify` workflow installs the Godot Gradle build template at CI time, signs the bundle with an ephemeral CI-only keystore, and checks the package ID, AAB signature, native ABI contents and checksum.

## Production signing rule

The real Play upload keystore must never be committed to this repository. Production export must supply the release keystore path, alias and password through protected release secrets/environment variables.

## Launcher identity

The Play preset now uses authored project-controlled launcher assets:

- main launcher icon;
- adaptive foreground;
- adaptive background;
- adaptive monochrome.

The Android launcher identity is therefore part of the automated export contract rather than a manual release blocker.

## Remaining visual release gate

Do **not** publish the AAB as a final Play release until the separate Google Play listing assets have been visually reviewed and promoted:

- 512×512 Store icon;
- 1024×500 feature graphic;
- final 1920×1080 gameplay screenshots.

These Store-listing graphics are intentionally reviewed separately from the Android launcher resources.
