# Deadline: Zero — Play Data Safety contract

This file is the repository-side source of truth for the Google Play Data Safety questionnaire. Re-check every item against the exact SDK versions and Play Console declarations before each production submission.

## App-owned data

Deadline: Zero stores gameplay progression, settings, onboarding state and local run state on-device. The app does not intentionally send those local gameplay records to a developer-operated backend.

Purchase entitlement cache is excluded from Android cloud backup/device transfer and must be restored from Google Play Billing rather than trusted as a backed-up local entitlement.

## Third-party platform services present in the Android build

### Google Mobile Ads SDK

Purpose: advertising / rewarded ads.

Release requirement:
- production AdMob identifiers;
- Google UMP consent flow before ad requests where required;
- Play Data Safety answers must reflect the current Google Mobile Ads SDK data disclosures.

### Google User Messaging Platform (UMP)

Purpose: consent/privacy choices for advertising.

Release requirement:
- consent information refreshed at launch;
- privacy-options entry exposed when required;
- Play Console declarations must reflect the current UMP SDK behavior.

### Google Play Billing

Purpose: in-app purchases and entitlement restoration.

Release requirement:
- purchases are verified through the Play Billing flow;
- local entitlement cache is not authoritative;
- Play Data Safety answers must reflect current Billing SDK behavior.

### Google Play Games Services

Purpose: platform game services when configured.

Release requirement:
- production Play Games application ID;
- declarations must reflect the exact Play Games features enabled in the shipping build.

### Google Play In-App Review

Purpose: optional store-managed review prompt after a meaningful successful run.

Release requirement:
- the app must not infer whether the player actually submitted a rating or review;
- prompt eligibility remains app-controlled, while display/quota behavior is controlled by Google Play;
- re-check the current Play In-App Review library disclosure against the exact shipping SDK version.

## Release checklist

- [ ] Re-check the current Google Mobile Ads SDK Data Safety disclosure.
- [ ] Re-check the current UMP SDK disclosure.
- [ ] Re-check Google Play Billing disclosure.
- [ ] Re-check Google Play Games Services disclosure and enabled features.
- [ ] Re-check Google Play In-App Review disclosure and shipping SDK version.
- [ ] Confirm the public privacy policy describes the same data flows.
- [ ] Confirm Play Console Data Safety answers match the shipping binary, not an older release.
- [ ] Confirm no new analytics, crash-reporting, account, cloud-save or attribution SDK was added without updating this contract.
- [ ] Confirm sensitive local entitlement data remains excluded from Android backup.
- [ ] Archive the final Play Console answers with the release evidence.

## Change-control rule

Any dependency or feature that changes collection, sharing, processing purpose, retention, account handling, advertising identifiers, diagnostics, location, personal information or financial/purchase data requires this file and the Play Console Data Safety form to be reviewed before release.
