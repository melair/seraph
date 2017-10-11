package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotateTest {
    final int originalWidth = 3;
    final int originalHeight = 2;
    FullDisplay store = new Buffer(new Null(originalWidth, originalHeight));
    PixelColour pixelColour = PixelColour.RED;

    @Test
    public void testRotationZeroDoesNotSwapDimensions() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_0);

        assertEquals(originalHeight, underTest.getHeight());
        assertEquals(originalWidth, underTest.getWidth());
    }

    @Test
    public void testRotationNinetyDoesSwapDimensions() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_90);

        assertEquals(originalWidth, underTest.getHeight());
        assertEquals(originalHeight, underTest.getWidth());
    }

    @Test
    public void testRotationOneEightyDoesNotSwapDimensions() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_180);

        assertEquals(originalHeight, underTest.getHeight());
        assertEquals(originalWidth, underTest.getWidth());
    }

    @Test
    public void testRotationTwoSeventyDoesSwapDimensions() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_270);

        assertEquals(originalWidth, underTest.getHeight());
        assertEquals(originalHeight, underTest.getWidth());
    }

    @Test
    public void testRotationZeroPixel() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_0);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 0));
    }

    @Test
    public void testRotationNinetyPixel() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_90);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(2, 0));
    }

    @Test
    public void testRotationOneEightyPixel() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_180);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(2, 1));
    }

    @Test
    public void testRotationTwoSeventyPixel() {
        DestinationDisplay underTest = new Rotate(store, Rotate.Rotation.ROTATE_270);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 1));
    }
}