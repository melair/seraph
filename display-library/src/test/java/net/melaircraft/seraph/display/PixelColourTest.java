package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PixelColourTest {
    @Test(expected = InvalidPixelColourException.class)
    public void testRedPixelConstructionLower() {
        new PixelColour(-1, 0, 0);
    }

    @Test(expected = InvalidPixelColourException.class)
    public void testRedPixelConstructionHigher() {
        new PixelColour(256, 0, 0);
    }

    @Test(expected = InvalidPixelColourException.class)
    public void testGreenPixelConstructionLower() {
        new PixelColour(0, -1, 0);
    }

    @Test(expected = InvalidPixelColourException.class)
    public void testGreenPixelConstructionHigher() {
        new PixelColour( 0, 256, 0);
    }

    @Test(expected = InvalidPixelColourException.class)
    public void testBluePixelConstructionLower() {
        new PixelColour(0, 0, -1);
    }

    @Test(expected = InvalidPixelColourException.class)
    public void testBluePixelConstructionHigher() {
        new PixelColour( 0, 0, 256);
    }

    @Test
    public void testMultiplicationFactor() {
        PixelColour original = new PixelColour(50, 100, 150);
        PixelColour newColour = original.multiply(0.5);

        assertEquals(25, newColour.getRed());
        assertEquals(50, newColour.getGreen());
        assertEquals(75, newColour.getBlue());
    }

    @Test
    public void testMultiplicationColour() {
        PixelColour original = new PixelColour(64, 64, 64);
        PixelColour mixedIn = new PixelColour(127, 127, 127);
        PixelColour newColour = original.multiply(mixedIn);

        assertEquals(32, newColour.getRed());
        assertEquals(32, newColour.getGreen());
        assertEquals(32, newColour.getBlue());
    }

    @Test
    public void testIntegerColourCreation() {
        int c = (128 << 16) + (64 << 8) + 32;

        PixelColour colour = new PixelColour(c);

        assertEquals(128, colour.getRed());
        assertEquals(64, colour.getGreen());
        assertEquals(32, colour.getBlue());

        assertEquals(c, colour.toInteger());
    }

}