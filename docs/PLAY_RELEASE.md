# Google Play release procedure

Use this path only for a bundle intended for Play Console upload. Normal CI verification intentionally uses test AdMob IDs and does not require an upload keystore.

## Required production values

Set production AdMob IDs with Gradle properties:

- `admobAppId` using the app-ID form `ca-app-pub-################~##########`
- `admobRewardedId` using the ad-unit form `ca-app-pub-################/##########`

Set upload signing with either Gradle properties or the equivalent environment variables:

- `releaseStoreFile` / `DEADLINE_ZERO_KEYSTORE`
- `releaseStorePassword` / `DEADLINE_ZERO_STORE_PASSWORD`
- `releaseKeyAlias` / `DEADLINE_ZERO_KEY_ALIAS`
- `releaseKeyPassword` / `DEADLINE_ZERO_KEY_PASSWORD`

Never commit keystores, passwords, aliases, production ad IDs, or generated credential files.

## Versioning

Update `appVersion` and increment `appVersionCode` in `gradle.properties` before every Play upload. Google Play requires every uploaded artifact to use a version code greater than the previous uploaded version. The release gate accepts version codes from `1` through `2100000000`.

## Build command

From the repository root, run:

```bash
gradle :android:bundlePlayRelease \
  -PadmobAppId=ca-app-pub-################~########## \
  -PadmobRewardedId=ca-app-pub-################/##########
```

The signing values may be supplied through the environment variables above instead of command-line properties.

`bundlePlayRelease` is the single production gate. It automatically runs:

1. `:core:test`
2. `:android:lintRelease`
3. authored production-asset validation
4. AdMob production-ID validation
5. upload-keystore/signing validation
6. Play version metadata validation
7. `:android:bundleRelease`

The command refuses to produce a Play bundle when production AdMob IDs, authored assets, upload signing, or valid version metadata are missing.

The resulting signed Android App Bundle is produced under `android/build/outputs/bundle/release/`.

## Pre-upload checks

Before Play Console upload:

1. Build with `gradle :android:bundlePlayRelease`; do not bypass this task with a direct `bundleRelease` for a production upload.
2. Verify the AAB is signed with the intended upload key.
3. Confirm consent/privacy flows on a clean install.
4. Confirm rewarded ads use production placement IDs on an internal-test build.
5. Confirm Remove Ads purchase, restore, acknowledgement, and app restart behavior through a Play license-test account.
6. Confirm Starter Pack and gem consumables deliver exactly once across process death and retry.
7. Confirm pause/resume, audio focus, background/foreground, rotation lock, fullscreen-ad lifecycle, and process recreation behavior on physical Android hardware.
8. Confirm the four Play Billing products match the source catalog exactly: `remove_ads_lifetime`, `starter_pack_01`, `gems_250`, `gems_1200`.
9. Confirm the Play listing privacy-policy URL matches the in-app privacy destination.
10. Keep the upload keystore backed up securely outside the repository.
