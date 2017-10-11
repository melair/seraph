package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import net.melaircraft.seraph.fonts.BDFFontFactory;
import net.melaircraft.seraph.fonts.BitmapFont;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class TextTest {
    @Test
    public void testTextBeingSetTopLeft() throws IOException {
        Buffer store = new Buffer(new Null(32, 32));

        InputStream inputStream = TextTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        PixelColour colour = PixelColour.RED;

        Text text = new Text(store, font, colour, Text.Justification.LEFT, Text.Alignment.TOP, "A");

        assertEquals(PixelColour.RED, store.getPixel(3, 4));
        assertEquals(PixelColour.RED, store.getPixel(4, 4));
        assertEquals(PixelColour.RED, store.getPixel(2, 5));
        assertEquals(PixelColour.RED, store.getPixel(5, 5));
    }

    @Test
    public void testTextBeingSetCentreMiddle() throws IOException {
        Buffer store = new Buffer(new Null(32, 32));

        InputStream inputStream = TextTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        PixelColour colour = PixelColour.RED;

        Text text = new Text(store, font, colour, Text.Justification.CENTER, Text.Alignment.MIDDLE, "A");

        assertEquals(PixelColour.RED, store.getPixel(15, 12));
        assertEquals(PixelColour.RED, store.getPixel(16, 12));
        assertEquals(PixelColour.RED, store.getPixel(14, 13));
        assertEquals(PixelColour.RED, store.getPixel(17, 13));
    }

    @Test
    public void testTextBeingSetBottomRight() throws IOException {
        Buffer store = new Buffer(new Null(32, 32));

        InputStream inputStream = TextTest.class.getResourceAsStream("/testfont.bdf");
        BitmapFont font = BDFFontFactory.load(inputStream);
        inputStream.close();

        PixelColour colour = PixelColour.RED;

        Text text = new Text(store, font, colour, Text.Justification.RIGHT, Text.Alignment.BOTTOM, "A");

        assertEquals(PixelColour.RED, store.getPixel(27, 20));
        assertEquals(PixelColour.RED, store.getPixel(28, 20));
        assertEquals(PixelColour.RED, store.getPixel(26, 21));
        assertEquals(PixelColour.RED, store.getPixel(29, 21));
    }
}