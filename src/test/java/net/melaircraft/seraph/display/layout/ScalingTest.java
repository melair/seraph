package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScalingTest {
    Displayable store = new Buffer(new Null(10, 10));

    @Test
    public void testDimensionsFactorOne() {
        Displayable scaling = new Scaling(store, 1);

        assertEquals(10, scaling.getWidth());
        assertEquals(10, scaling.getHeight());
    }

    @Test
    public void testDimensionsFactorTwo() {
        Displayable scaling = new Scaling(store, 2);

        assertEquals(5, scaling.getWidth());
        assertEquals(5, scaling.getHeight());
    }

    @Test
    public void testDimensionsFactorThree() {
        Displayable scaling = new Scaling(store, 3);

        assertEquals(3, scaling.getWidth());
        assertEquals(3, scaling.getHeight());
    }

    @Test
    public void testSetPixelScalingTwo() {
        Displayable scaling = new Scaling(store, 2);
        PixelColour colour = new PixelColour(255, 0, 0);

        scaling.setPixel(1, 1, colour);

        assertEquals(colour, store.getPixel(2,2));
        assertEquals(colour, store.getPixel(3,2));
        assertEquals(colour, store.getPixel(2,3));
        assertEquals(colour, store.getPixel(3,3));
    }

    @Test
    public void testGetPixelScalingTwo() {
        Displayable scaling = new Scaling(store, 2);
        PixelColour colour = new PixelColour(255, 0, 0);

        scaling.setPixel(1, 1, colour);

        assertEquals(colour, scaling.getPixel(1, 1));
    }
}
