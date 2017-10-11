package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidPaneLocationException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaneTest {
    Displayable store = new Buffer(new Null(10, 10));

    @Test
    public void testWidthHeight() {
        Displayable pane = new Pane(store, 2, 2, 4, 2);

        assertEquals(4, pane.getWidth());
        assertEquals(2, pane.getHeight());
    }

    @Test(expected = InvalidPaneLocationException.class)
    public void testInvalidPlacementX() {
        Displayable pane = new Pane(store, 8, 2, 4, 2);
    }

    @Test(expected = InvalidPaneLocationException.class)
    public void testInvalidPlacementY() {
        Displayable pane = new Pane(store, 0, 9, 4, 2);
    }

    @Test
    public void testRemappedCoordinates() {
        Displayable pane = new Pane(store, 2, 2, 4, 2);
        PixelColour colour = PixelColour.BLUE;

        pane.setPixel(0, 0, colour);

        assertEquals(colour, store.getPixel(2, 2));
        assertEquals(colour, pane.getPixel(0, 0));
    }

    @Test(expected = NonExistentPixelException.class)
    public void testPixelOutSidepane() {
        Displayable pane = new Pane(store, 2, 2, 4, 2);

        pane.setPixel(10, 10, PixelColour.BLACK);
    }
}