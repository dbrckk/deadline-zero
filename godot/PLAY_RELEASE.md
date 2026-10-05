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

The real Play upload keystore must never be committed to this repository.

The manual `Godot Play Signed Release` workflow expects these protected GitHub secrets:

- `PLAY_UPLOAD_KEYSTORE_BASE64` — base64-encoded upload keystore bytes;
- `PLAY_UPLOAD_KEY_ALIAS` — upload-key alias;
- `PLAY_UPLOAD_KEY_PASSWORD` — keystore/key password used by the Godot Android exporter.

The workflow materializes the keystore only inside the ephemeral runner, exports the signed AAB, validates package/ABI/signature evidence, records the certificate details and uploads the release artifact. Signed release jobs are serialized so two production builds cannot run concurrently.

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
