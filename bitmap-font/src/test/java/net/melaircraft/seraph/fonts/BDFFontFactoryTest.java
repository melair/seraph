package net.melaircraft.seraph.fonts;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BDFFontFactoryTest {
    @Test
    public void testLoadingFont() throws IOException {
        InputStream inputStream = BDFFontFactoryTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        Optional<BitmapCharacter> optional = font.getCharacter('A');

        assertTrue(optional.isPresent());

        BitmapCharacter character = optional.get();

        assertEquals(8, character.getBitmapWidth());
        assertEquals(16, character.getBitmapHeight());

        assertEquals(0, character.getBitmapOffsetX());
        assertEquals(-2, character.getBitmapOffsetY());

        assertEquals(8, character.getCharacterWidth());
        assertEquals(0, character.getCharacterHeight());

        boolean[][] bitmap = character.getBitmap();

        boolean[][] expectedBitmap = {
                { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false },
                { false, false, false, false, false, true,  true,  false, false, true,  false, false, false, false, false, false },
                { false, false, false, false, true,  false, false, false, false, true,  false, false, false, false, false, false },
                { false, false, false, false, true,  false, false, false, false, true,  false, false, false, false, false, false },
                { false, false, false, false, false, true,  true,  false, false, true,  false, false, false, false, false, false },
                { false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false },
                { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false }
        };

        assertArrayEquals(expectedBitmap, bitmap);
    }
}