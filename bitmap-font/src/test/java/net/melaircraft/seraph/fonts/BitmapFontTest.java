package net.melaircraft.seraph.fonts;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
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
        BitmapCharacter character = new BitmapCharacter(null, 0, 0, 0, 0, 0, 0);
        font.addCharacter('A', character);

        Optional<BitmapCharacter> optional = font.getCharacter('A');
        assertTrue(optional.isPresent());
        assertEquals(character, optional.get());
    }

    @Test
    public void testRender() throws IOException {
        InputStream inputStream = BDFFontFactoryTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        FontOutput output = font.render(("A A"));
        boolean[][] bitmap = output.getBitmap();

        assertTrue(bitmap[3][4]);
        assertTrue(bitmap[4][4]);
        assertTrue(bitmap[19][4]);
        assertTrue(bitmap[20][4]);

        assertTrue(bitmap[1][13]);
        assertTrue(bitmap[6][13]);
        assertTrue(bitmap[17][13]);
        assertTrue(bitmap[22][13]);
    }
}