package com.deadlinezero.game.fx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Test;

final class DamageNumberTest {
    @Test void displayTextIsRoundedAndCachedAtSpawn() {
        DamageNumber number = new DamageNumber().spawn(1f, 2f, 12.6f, false, Color.WHITE);
        assertEquals("13", number.text);

        number.update(.1f);
        assertEquals("13", number.text);
    }

    @Test void displayTextClampsToAtLeastOne() {
        DamageNumber number = new DamageNumber().spawn(0f, 0f, .2f, false, Color.WHITE);
        assertEquals("1", number.text);
    }
}
