package net.melaircraft.seraph.fonts;

import net.melaircraft.seraph.fonts.BitmapCharacter;
import net.melaircraft.seraph.fonts.BitmapFont;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BitmapFontTest {
    @Test
    public void testGettingUnknownCharacter() {
        BitmapFont font = new BitmapFont();

        Optional<BitmapCharacter> optional = font.getCharacter('A');
        assertFalse(optional.isPresent());
    }

    @Test
    public void testGettingKnownCharacter() {
        BitmapFont font = new BitmapFont();
        BitmapCharacter character = new BitmapCharacter(null, 0, 0, 0, 0);
        font.addCharacter('A', character);

        Optional<BitmapCharacter> optional = font.getCharacter('A');
        assertTrue(optional.isPresent());
        assertEquals(character, optional.get());
    }
}