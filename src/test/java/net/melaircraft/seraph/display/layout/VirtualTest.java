package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VirtualTest {
    Displayable store = new Buffer(new Null(4, 4));

    @Test
    public void testDimensions() {
        Displayable virtual = new Virtual(store, 8, 8);

        assertEquals(8, virtual.getWidth());
        assertEquals(8, virtual.getHeight());
    }

    @Test
    public void testSettingAndGettingOnCanvasOutsideParent() {
        Displayable virtual = new Virtual(store, 8, 8);
        PixelColour colour = new PixelColour(255, 0, 0);

        virtual.setPixel(7, 7, colour);
        assertEquals(colour, virtual.getPixel(7, 7));
    }

    @Test
    public void testSettingInSideViewPortUpdatesStore() {
        Displayable virtual = new Virtual(store, 8, 8);
        PixelColour colour = new PixelColour(255, 0, 0);

        virtual.setPixel(0, 0, colour);
        assertEquals(colour, store.getPixel(0, 0));
    }

    @Test
    public void testSettingOutSideViewPortDoesNotError() {
        Displayable virtual = new Virtual(store, 8, 8);
        PixelColour colour = new PixelColour(255, 0, 0);

        virtual.setPixel(5, 5, colour);
    }

    @Test
    public void testViewPort() {
        Displayable store = new Buffer(new Null(6, 6));
        Virtual virtual = new Virtual(store, 8, 8);
        PixelColour colour = new PixelColour(255, 0, 0);

        virtual.setPixel(5, 5, colour);

        assertEquals(colour, store.getPixel(5, 5));
        virtual.moveViewport(5, 5);

        assertEquals(colour, store.getPixel(0, 0));
        assertNotEquals(colour, store.getPixel(5, 5));
    }
}