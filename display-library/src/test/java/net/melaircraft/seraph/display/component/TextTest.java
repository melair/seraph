package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.fonts.BDFFontFactory;
import net.melaircraft.seraph.fonts.BitmapFont;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class TextTest {
    @Test
    public void testTextBeingSetTopLeft() throws IOException {
        InputStream inputStream = TextTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        PixelColour colour = PixelColour.RED;

        Text text = new Text(font, colour, "A");

        assertEquals(PixelColour.RED, text.getPixel(3, 4));
        assertEquals(PixelColour.RED, text.getPixel(4, 4));
        assertEquals(PixelColour.RED, text.getPixel(2, 5));
        assertEquals(PixelColour.RED, text.getPixel(5, 5));
    }
}