package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedFullDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScaleTest {
    CheckedFullDisplay store = new Buffer(new Null(10, 10));

    @Test
    public void testDimensionsFactorOne() {
        DestinationDisplay scale = new Scale(store, 1);

        assertEquals(10, scale.getWidth());
        assertEquals(10, scale.getHeight());
    }

    @Test
    public void testDimensionsFactorTwo() {
        DestinationDisplay scale = new Scale(store, 2);

        assertEquals(5, scale.getWidth());
        assertEquals(5, scale.getHeight());
    }

    @Test
    public void testDimensionsFactorThree() {
        DestinationDisplay scale = new Scale(store, 3);

        assertEquals(3, scale.getWidth());
        assertEquals(3, scale.getHeight());
    }

    @Test
    public void testSetPixelscaleTwo() {
        DestinationDisplay scale = new Scale(store, 2);
        PixelColour colour = PixelColour.RED;

        scale.setPixel(1, 1, colour);

        assertEquals(colour, store.getPixel(2,2));
        assertEquals(colour, store.getPixel(3,2));
        assertEquals(colour, store.getPixel(2,3));
        assertEquals(colour, store.getPixel(3,3));
    }
}
