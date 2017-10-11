package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlipTest {
    private FullDisplay store = new Buffer(4, 4);
    private PixelColour pixelColour = PixelColour.BLUE;

    @Test
    public void testNoFlip() {
        DestinationDisplay underTest = new Flip(store);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 0));
    }

    @Test
    public void testHorizontalFlip() {
        DestinationDisplay underTest = new Flip(store, Flip.Direction.HORIZONTAL);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(3, 0));
    }


    @Test
    public void testVerticalFlip() {
        DestinationDisplay underTest = new Flip(store, Flip.Direction.VERTICAL);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 3));
    }

    @Test
    public void testBothFlip() {
        DestinationDisplay underTest = new Flip(store, Flip.Direction.HORIZONTAL, Flip.Direction.VERTICAL);

        underTest.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(3, 3));
    }
}