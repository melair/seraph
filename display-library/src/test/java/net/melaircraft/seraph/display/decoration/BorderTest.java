package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.CheckedFullDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorderTest {
    CheckedFullDisplay store = new Buffer(new Null(6, 6));
    PixelColour colour = PixelColour.RED;

    @Test
    public void testBordersOnAllSidesDimensions() {
        DestinationDisplay border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        assertEquals(store.getWidth() - 2, border.getWidth());
        assertEquals(store.getHeight() - 2, border.getHeight());
    }

    @Test
    public void testPixelWriteThrough() {
        DestinationDisplay border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);
        PixelColour dot = PixelColour.GREEN;

        border.setPixel(0, 0, dot);
        border.setPixel(3, 3, dot);

        assertEquals(dot, store.getPixel(1, 1));
        assertEquals(dot, store.getPixel(4, 4));
    }

    @Test
    public void testBorders() {
        DestinationDisplay border = new Border(store, colour, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        assertEquals(colour, store.getPixel(0, 1));
        assertEquals(colour, store.getPixel(1, 0));
        assertEquals(colour, store.getPixel(0, 5));
        assertEquals(colour, store.getPixel(5, 0));
    }
}