package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreyscaleTest {
    Displayable store = new Buffer(new Null(4, 2));

    @Test
    public void testDimensionsPassThrough() {
        Displayable greyscale = new Greyscale(store);

        assertEquals(store.getWidth(), greyscale.getWidth());
        assertEquals(store.getHeight(), greyscale.getHeight());
    }

    @Test
    public void testGreyscaleWriteThroughBrightWhite() {
        Displayable greyscale = new Greyscale(store);

        greyscale.setPixel(0, 0, PixelColour.WHITE);

        assertEquals(255, store.getPixel(0, 0).getRed());
        assertEquals(255, greyscale.getPixel(0, 0).getRed());

        assertEquals(255, store.getPixel(0, 0).getGreen());
        assertEquals(255, greyscale.getPixel(0, 0).getGreen());

        assertEquals(255, store.getPixel(0, 0).getBlue());
        assertEquals(255, greyscale.getPixel(0, 0).getBlue());
    }

    @Test
    public void testGreyscaleWriteThroughDimRed() {
        Displayable greyscale = new Greyscale(store);

        greyscale.setPixel(0, 0, new PixelColour(60, 0, 0));

        assertEquals(13, store.getPixel(0, 0).getRed());
        assertEquals(60, greyscale.getPixel(0, 0).getRed());

        assertEquals(13, store.getPixel(0, 0).getGreen());
        assertEquals(0, greyscale.getPixel(0, 0).getGreen());

        assertEquals(13, store.getPixel(0, 0).getBlue());
        assertEquals(0, greyscale.getPixel(0, 0).getBlue());
    }
}