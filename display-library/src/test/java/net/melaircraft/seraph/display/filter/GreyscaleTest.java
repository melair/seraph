package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreyscaleTest {
    private final FullDisplay store = new Buffer(4, 2);

    @Test
    public void testDimensionsPassThrough() {
        DestinationDisplay greyscale = new Greyscale(store);

        assertEquals(store.getWidth(), greyscale.getWidth());
        assertEquals(store.getHeight(), greyscale.getHeight());
    }

    @Test
    public void testGreyscaleWriteThroughBrightWhite() {
        DestinationDisplay greyscale = new Greyscale(store);

        PixelColour colour = PixelColour.WHITE;
        greyscale.setPixel(0, 0, colour);

        assertEquals(colour, store.getPixel(0, 0));

    }

    @Test
    public void testGreyscaleWriteThroughDimRed() {
        DestinationDisplay greyscale = new Greyscale(store);

        PixelColour colour = new PixelColour(60, 0, 0);
        greyscale.setPixel(0, 0, colour);

        assertEquals(new PixelColour(13, 13, 13), store.getPixel(0, 0));
    }
}