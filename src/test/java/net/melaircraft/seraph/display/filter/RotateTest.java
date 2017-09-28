package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotateTest {
    final int originalWidth = 3;
    final int originalHeight = 2;
    Displayable store = new Buffer(new Null(originalWidth, originalHeight));
    PixelColour pixelColour = new PixelColour(128, 128, 128);

    @Test
    public void testRotationZeroDoesNotSwapDimensions() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_0);

        assertEquals(originalHeight, underTest.getHeight());
        assertEquals(originalWidth, underTest.getWidth());
    }

    @Test
    public void testRotationNinetyDoesSwapDimensions() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_90);

        assertEquals(originalWidth, underTest.getHeight());
        assertEquals(originalHeight, underTest.getWidth());
    }

    @Test
    public void testRotationOneEightyDoesNotSwapDimensions() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_180);

        assertEquals(originalHeight, underTest.getHeight());
        assertEquals(originalWidth, underTest.getWidth());
    }

    @Test
    public void testRotationTwoSeventyDoesSwapDimensions() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_270);

        assertEquals(originalWidth, underTest.getHeight());
        assertEquals(originalHeight, underTest.getWidth());
    }

    @Test
    public void testRotationZeroPixel() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_0);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 0));
        assertEquals(pixelColour, underTest.getPixel(0, 0));
    }

    @Test
    public void testRotationNinetyPixel() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_90);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(2, 0));
        assertEquals(pixelColour, underTest.getPixel(0, 0));
    }

    @Test
    public void testRotationOneEightyPixel() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_180);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(2, 1));
        assertEquals(pixelColour, underTest.getPixel(0, 0));
    }

    @Test
    public void testRotationTwoSeventyPixel() {
        Displayable underTest = new Rotate(store, Rotate.Rotation.ROTATE_270);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 1));
        assertEquals(pixelColour, underTest.getPixel(0, 0));
    }
}