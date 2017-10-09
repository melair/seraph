package net.melaircraft.seraph.fonts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BitmapPixelTest {
    @Test
    public void testConstructor() {
        BitmapPixel pixel = new BitmapPixel(1, 2, true);

        assertEquals(1, pixel.getX());
        assertEquals(2, pixel.getY());
        assertTrue(pixel.isSet());

        BitmapPixel pixelTwo = new BitmapPixel(1, 2, false);
        assertTrue(pixel.equals(pixelTwo));

        assertEquals(pixel.hashCode(), pixelTwo.hashCode());
    }
}