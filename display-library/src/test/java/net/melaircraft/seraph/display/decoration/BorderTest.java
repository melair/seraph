package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorderTest {
    Displayable store = new Buffer(new Null(6, 6));
    PixelColour colour = PixelColour.RED;

    @Test
    public void testBordersOnAllSidesDimensions() {
        Displayable border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        assertEquals(store.getWidth() - 2, border.getWidth());
        assertEquals(store.getHeight() - 2, border.getHeight());
    }

    @Test
    public void testPixelWriteThrough() {
        Displayable border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);
        PixelColour dot = PixelColour.GREEN;

        border.setPixel(0, 0, dot);
        border.setPixel(3, 3, dot);

        assertEquals(dot, store.getPixel(1, 1));
        assertEquals(dot, store.getPixel(4, 4));
        assertEquals(dot, border.getPixel(0, 0));
        assertEquals(dot, border.getPixel(3, 3));
    }

    @Test
    public void testBorders() {
        Displayable border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        assertEquals(colour, store.getPixel(0, 1));
        assertEquals(colour, store.getPixel(1, 0));
        assertEquals(colour, store.getPixel(0, 5));
        assertEquals(colour, store.getPixel(5, 0));
    }
}