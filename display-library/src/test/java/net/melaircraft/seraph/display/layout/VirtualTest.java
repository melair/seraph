package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidVirtualSizeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VirtualTest {
    private final Buffer store = new Buffer(4, 4);

    @Test (expected = InvalidVirtualSizeException.class)
    public void testVirtualSmallerThenParent() {
        DestinationDisplay virtual = new Virtual(store, 2, 2, false);
    }

    @Test
    public void testDimensions() {
        DestinationDisplay virtual = new Virtual(store, 8, 8, false);

        assertEquals(8, virtual.getWidth());
        assertEquals(8, virtual.getHeight());
    }

    @Test
    public void testSettingInSideViewPortUpdatesStore() {
        DestinationDisplay virtual = new Virtual(store, 8, 8, false);
        PixelColour colour = PixelColour.RED;

        virtual.setPixel(0, 0, colour);
        assertEquals(colour, store.getPixel(0, 0));
    }

    @Test
    public void testSettingOutSideViewPortDoesNotError() {
        DestinationDisplay virtual = new Virtual(store, 8, 8, false);
        PixelColour colour = PixelColour.RED;

        virtual.setPixel(5, 5, colour);
    }

    @Test
    public void testViewPort() {
        FullDisplay store = new Buffer(6, 6);
        Virtual virtual = new Virtual(store, 8, 8, false);
        PixelColour colour = PixelColour.RED;

        virtual.setPixel(5, 5, colour);

        assertEquals(colour, store.getPixel(5, 5));
        virtual.moveViewport(5, 5);

        assertEquals(colour, store.getPixel(0, 0));
        assertNotEquals(colour, store.getPixel(5, 5));
    }

    @Test
    public void testSettingAfterViewPort() {
        Virtual virtual = new Virtual(store, 8, 8, false);

        virtual.moveViewport(2, 2);

        PixelColour colour = PixelColour.RED;

        virtual.setPixel(2, 2, colour);
        assertEquals(colour, store.getPixel(0, 0));
    }

    @Test
    public void testWrappingMoving() {
        Virtual virtual = new Virtual(store, 4, 4, true);

        virtual.setPixel(0, 0, PixelColour.RED);
        virtual.moveViewport(2, 2);

        assertEquals(PixelColour.RED, store.getPixel(2,2));
    }

    @Test
    public void testWrappingSettingPixel() {
        Virtual virtual = new Virtual(store, 4, 4, true);

        virtual.moveViewport(2, 2);
        virtual.setPixel(0, 0, PixelColour.RED);

        assertEquals(PixelColour.RED, store.getPixel(2,2));
    }

}