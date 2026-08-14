# Google Play release procedure

Use this path only for a bundle intended for Play Console upload. Normal CI verification intentionally uses test AdMob IDs and does not require an upload keystore.

## Required production values

Set production AdMob IDs with Gradle properties:

- `admobAppId`
- `admobRewardedId`

Set upload signing with either Gradle properties or the equivalent environment variables:

- `releaseStoreFile` / `DEADLINE_ZERO_KEYSTORE`
- `releaseStorePassword` / `DEADLINE_ZERO_STORE_PASSWORD`
- `releaseKeyAlias` / `DEADLINE_ZERO_KEY_ALIAS`
- `releaseKeyPassword` / `DEADLINE_ZERO_KEY_PASSWORD`

Never commit keystores, passwords, aliases, production ad IDs, or generated credential files.

## Versioning

Update `appVersion` and increment `appVersionCode` in `gradle.properties` before every Play upload. Google Play requires every uploaded artifact to use a version code greater than the previous uploaded version.

## Build command

From the repository root, run:

```bash
gradle :android:bundlePlayRelease \
  -PadmobAppId=ca-app-pub-<production-app-id> \
  -PadmobRewardedId=ca-app-pub-<production-rewarded-id>
```

The signing values may be supplied through the environment variables above instead of command-line properties. `bundlePlayRelease` refuses to run when production AdMob IDs, the keystore, signing credentials, or version metadata are missing.

The resulting signed Android App Bundle is produced under `android/build/outputs/bundle/release/`.

## Pre-upload checks

Before Play Console upload:

1. Confirm `gradle :core:test` passes.
2. Confirm `gradle :android:lintRelease` passes.
3. Build with `gradle :android:bundlePlayRelease`.
4. Verify the AAB is signed with the intended upload key.
5. Confirm consent/privacy flows on a clean install.
6. Confirm rewarded ads use production placement IDs on an internal-test build.
7. Confirm Remove Ads purchase, restore, acknowledgement, and app restart behavior through a Play license-test account.
8. Confirm pause/resume, audio focus, background/foreground, rotation lock, and process recreation behavior on physical Android hardware.
9. Confirm the Play listing privacy-policy URL matches the in-app privacy destination.
10. Keep the upload keystore backed up securely outside the repository.
