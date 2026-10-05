# Deadline: Zero — Play visual approval evidence

Visual review of the generated Google Play candidates for commit:

`84db34d138b0456fd99c42bd3f86cf9e52120f5d`

## CI evidence

Workflow: `Godot Play Screenshots`

Run: `37313591608`

### Gameplay screenshot artifact

- Artifact ID: `11347480087`
- Name: `deadline-zero-play-screenshot-candidates-84db34d138b0456fd99c42bd3f86cf9e52120f5d`
- SHA-256 digest: `af4fef95fe1b5f01b563b0c78c47f5ae00f8ca1a63f55589b3f38bd0e71fab9e`
- Contains five real 1920×1080 runtime screenshots.

### Branding artifact

- Artifact ID: `11347270321`
- Name: `deadline-zero-play-branding-candidates-84db34d138b0456fd99c42bd3f86cf9e52120f5d`
- SHA-256 digest: `3031807250fb6041a32a27848e58ff7604dde24074b5c5dcfe9f2c32ca888aca`
- Contains the 512×512 icon candidate and 1024×500 feature-graphic candidate.

## Visual review

### Icon candidate — approved as Store source

- Strong cyan/orange silhouette at small size.
- No text dependency.
- Clear shield/lightning identity.
- Dark background gives reliable edge contrast.
- Consistent with the authored Android launcher identity.

### Feature graphic — approved as Store source

- Product name is immediately readable.
- Player-versus-horde composition is clear.
- Cyan/orange lighting matches the in-game visual language.
- Uses actual authored game characters rather than unrelated marketing art.
- No ranking, award, price, download-count, or unavailable-feature claims.

### Gameplay screenshots

1. **First playable — approved.** Shows the real arena, HUD, enemies and projectile readability.
2. **Mid-run pressure — approved.** Demonstrates mixed archetypes, telegraphs and close-pressure combat.
3. **Boss encounter — approved.** Boss repositioning keeps Revenant Prime readable below the boss HUD while retaining the live boss-health presentation.
4. **Upgrade choice — approved.** Clearly communicates the roguelite three-choice upgrade loop with actual runtime UI.
5. **Run end — approved.** Shows the real end-state hierarchy, run summary and redeploy action.

## Promotion rule

These visuals are approved as the source set for Play listing assets. For the final uploaded release, regenerate the same asset set from the exact production candidate commit and verify its artifact digest before upload.

The final Google Play upload must use assets generated from the exact candidate AAB commit if later visual/runtime changes alter any screenshot or branding scene.

## Remaining manual Store action

The approved files still need to be downloaded from the release-candidate artifact (or regenerated on the exact final commit) and uploaded to Play Console. Approval does not imply that Play Console upload has already occurred.
