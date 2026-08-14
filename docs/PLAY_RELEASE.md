# Google Play release procedure

Use this path only for a bundle intended for Play Console upload. Normal CI verification intentionally uses test AdMob IDs and does not require an upload keystore.

## Required production values

Set production AdMob IDs with Gradle properties:

- `admobAppId` using the app-ID form `ca-app-pub-################~##########`
- `admobRewardedId` using the ad-unit form `ca-app-pub-################/##########`

Set the public privacy-policy URL with:

- `privacyPolicyUrl` using the real public HTTPS URL configured in Play Console

The same URL is compiled into the Android app and exposed from the Settings screen. Placeholder, `example.com`, non-HTTPS and blank values are rejected by the Play release gate.

Set upload signing with either Gradle properties or the equivalent environment variables:

- `releaseStoreFile` / `DEADLINE_ZERO_KEYSTORE`
- `releaseStorePassword` / `DEADLINE_ZERO_STORE_PASSWORD`
- `releaseKeyAlias` / `DEADLINE_ZERO_KEY_ALIAS`
- `releaseKeyPassword` / `DEADLINE_ZERO_KEY_PASSWORD`

Never commit keystores, passwords, aliases, production ad IDs, or generated credential files.

## Play Store graphics

Place final authored listing exports under `play/store/` as defined in `docs/STORE_RELEASE.md` and `play/store/README.md`.

You can validate only the listing graphics with:

```bash
gradle :android:verifyPlayStoreAssets
```

The production gate requires the Play icon, feature graphic and at least three recommendation-grade 16:9 gameplay screenshots. Temporary mockups must not use the final filenames.

## Versioning

Update `appVersion` and increment `appVersionCode` in `gradle.properties` before every Play upload. Google Play requires every uploaded artifact to use a version code greater than the previous uploaded version. The release gate accepts version codes from `1` through `2100000000`.

## Build command

From the repository root, run:

```bash
gradle :android:bundlePlayRelease \
  -PadmobAppId=ca-app-pub-################~########## \
  -PadmobRewardedId=ca-app-pub-################/########## \
  -PprivacyPolicyUrl=https://your-domain.example/privacy
```

Replace the example privacy URL above with the real production URL. The signing values may be supplied through the environment variables above instead of command-line properties.

`bundlePlayRelease` is the single production gate. It automatically runs:

1. `:core:test`
2. `:android:lintRelease`
3. authored runtime production-asset validation
4. Play Store icon / feature graphic / screenshot validation
5. AdMob production-ID validation
6. public HTTPS privacy-policy validation
7. upload-keystore/signing validation
8. Play version metadata validation
9. `:android:bundleRelease`

The command refuses to produce a Play bundle when production AdMob IDs, the public privacy-policy URL, authored runtime assets, final Store graphics, upload signing, or valid version metadata are missing.

The resulting signed Android App Bundle is produced under `android/build/outputs/bundle/release/`.

## Pre-upload checks

Before Play Console upload:

1. Build with `gradle :android:bundlePlayRelease`; do not bypass this task with a direct `bundleRelease` for a production upload.
2. Verify the AAB is signed with the intended upload key.
3. Confirm consent/privacy flows on a clean install.
4. Confirm Settings > Privacy policy opens the exact public URL entered in Play Console.
5. Confirm rewarded ads use production placement IDs on an internal-test build.
6. Confirm Remove Ads purchase, restore, acknowledgement, and app restart behavior through a Play license-test account.
7. Confirm Starter Pack and gem consumables deliver exactly once across process death and retry.
8. Confirm pause/resume, audio focus, background/foreground, rotation lock, fullscreen-ad lifecycle, and process recreation behavior on physical Android hardware.
9. Confirm the four Play Billing products match the source catalog exactly: `remove_ads_lifetime`, `starter_pack_01`, `gems_250`, `gems_1200`.
10. Confirm the Play listing privacy-policy URL matches the in-app privacy destination and the Data safety declaration covers production SDK behavior.
11. Confirm all graphics and listing metadata satisfy `docs/STORE_RELEASE.md`.
12. Keep the upload keystore backed up securely outside the repository.
