package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class CharacterSpriteFacingTest {
    @Test
    void attackFacingUsesAimVectorOverMovement() {
        assertEquals(Direction8.N,
            CharacterSpriteRenderer.resolvePlayerFacing(1f, 0f, 0f, 4f, true, Direction8.E));
        assertEquals(Direction8.SW,
            CharacterSpriteRenderer.resolvePlayerFacing(0f, 1f, -3f, -3f, true, Direction8.N));
    }

    @Test
    void nonAttackFacingStillUsesMovement() {
        assertEquals(Direction8.W,
            CharacterSpriteRenderer.resolvePlayerFacing(-2f, 0f, 0f, 4f, false, Direction8.E));
    }

    @Test
    void attackWithoutTargetKeepsMovementOrPreviousFacing() {
        assertEquals(Direction8.SE,
            CharacterSpriteRenderer.resolvePlayerFacing(2f, -2f, 0f, 0f, true, Direction8.N));
        assertEquals(Direction8.NW,
            CharacterSpriteRenderer.resolvePlayerFacing(0f, 0f, 0f, 0f, true, Direction8.NW));
    }
}
