# Deadline: Zero — Play Data Safety contract

This file is the repository-side source of truth for the Google Play Data Safety questionnaire. The declaration must always match the exact AAB being submitted.

## Current Godot release candidate

The current Godot runtime stores audio/settings state locally on-device and does not intentionally send gameplay records to a developer-operated backend.

No Google Mobile Ads, UMP, Play Billing, Play Games Services, In-App Review, analytics, attribution, account, cloud-save, or crash-reporting SDK is currently integrated into the Godot project or its Play AAB export configuration.

The current Godot export preset requests no custom Android permissions. Re-check the final merged manifest before submission because engine/runtime templates may contribute platform permissions.

## App-owned data

Current Godot-owned persistent state is limited to local settings such as master and SFX volume. Run progression is session-local in the current release candidate.

No developer-operated account system or remote gameplay-profile service is present in the current Godot runtime.

## Legacy libGDX services

The sections below document services used by the older libGDX Android runtime. They are **not present in the current Godot release candidate** and must not be copied into Play Console declarations for a Godot AAB unless those SDKs are deliberately reintroduced.

### Google Mobile Ads SDK

Legacy runtime only. Not integrated in the current Godot release candidate.

If reintroduced, production AdMob identifiers, consent behavior, and current SDK Data Safety disclosures must be reviewed before release.

### Google User Messaging Platform (UMP)

Legacy runtime only. Not integrated in the current Godot release candidate.

If advertising/UMP is reintroduced, consent refresh and privacy-options behavior must be validated against the exact shipping SDK.

### Google Play Billing

Legacy runtime only. Not integrated in the current Godot release candidate.

If purchases are reintroduced, entitlement verification, acknowledgement, restore, refund/revocation behavior, and Data Safety declarations must be reviewed.

### Google Play Games Services

Legacy runtime only. Not integrated in the current Godot release candidate.

If Play Games is reintroduced, the production application ID and exact enabled features must be reflected in the Play Console declaration.

### Google Play In-App Review

Legacy runtime only. Not integrated in the current Godot release candidate.

If reintroduced, review prompting must remain non-blocking and the app must not infer whether a rating was actually submitted.

## Release checklist

- [ ] Inspect the exact Godot AAB merged manifest and dependencies before Play submission.
- [ ] Confirm no network/data SDK was added since this contract was last reviewed.
- [ ] Confirm Play Console Data Safety answers match the Godot AAB, not the legacy libGDX build.
- [ ] Confirm the public privacy policy describes the same current data flows.
- [ ] Re-check local backup behavior for settings and any future entitlement data.
- [ ] Archive the final Play Console answers with the release evidence.

## Change-control rule

Any dependency or feature that changes collection, sharing, processing purpose, retention, account handling, advertising identifiers, diagnostics, location, personal information, or financial/purchase data requires this file and the Play Console Data Safety form to be reviewed before release.
