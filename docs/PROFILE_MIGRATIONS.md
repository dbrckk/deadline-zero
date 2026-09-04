# Player profile schema and migration policy

`ProfileStore` persists the long-term player account in the libGDX Preferences namespace `deadline-zero-profile-v1`.

The namespace name is historical and is not the schema version. The authoritative schema marker is the integer key `schema.version`.

## Current schema

- Current version: `1`
- Legacy profiles with no `schema.version` key are interpreted as version `0`.
- Version `0 -> 1` is intentionally lossless: the legacy field layout already matches v1, so migration only stamps the explicit version marker.

## Migration rules

1. Migrations run before persisted fields are consumed by gameplay.
2. Migrations are ordered one version at a time.
3. Every migration must be idempotent.
4. A migration may add, rename or normalize fields, but must not silently discard recoverable progression.
5. Existing normalization/sanitization remains separate from schema migration.
6. Every new schema version must add regression fixtures covering the previous supported version.

## Forward-version / downgrade policy

A save with `schema.version > CURRENT_VERSION` was created by a newer application build. An older build must not rewrite that save.

`ProfileSchema.migrate` therefore rejects the unsupported future schema and `ProfileStore` disables profile writes for the remainder of that loaded session. The profile can still be read using safe/default accessors where compatible, but the persisted payload remains untouched until the user runs a compatible/newer application version.

This is deliberately fail-safe against downgrade data loss.

## Corrupt schema marker

A negative schema version is treated as legacy/unversioned (`0`) and migrated through the normal pipeline. Other persisted fields continue to pass through the existing deterministic sanitization/normalization logic.

## Required tests for future migrations

Whenever `CURRENT_VERSION` is incremented, add tests that demonstrate at minimum:

- representative currency and account progression survives;
- inventory/equipment and exclusive capacity survive;
- purchase/receipt replay protection survives;
- survivor/mastery/daily progression survives;
- corrupt values are sanitized deterministically;
- applying the migration more than once produces the same final state;
- a newer unsupported schema remains untouched.
