package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidWindowLocationException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WindowTest {
    Displayable store = new Buffer(new Null(10, 10));

    @Test
    public void testWidthHeight() {
        Displayable window = new Window(store, 2, 2, 4, 2);

        assertEquals(4, window.getWidth());
        assertEquals(2, window.getHeight());
    }

    @Test(expected = InvalidWindowLocationException.class)
    public void testInvalidPlacementX() {
        Displayable window = new Window(store, 8, 2, 4, 2);
    }

    @Test(expected = InvalidWindowLocationException.class)
    public void testInvalidPlacementY() {
        Displayable window = new Window(store, 0, 9, 4, 2);
    }

    @Test
    public void testRemappedCoordinates() {
        Displayable window = new Window(store, 2, 2, 4, 2);
        PixelColour colour = new PixelColour(255, 0, 0);

        window.setPixel(0, 0, colour);

        assertEquals(colour, store.getPixel(2, 2));
        assertEquals(colour, window.getPixel(0, 0));
    }

    @Test(expected = NonExistentPixelException.class)
    public void testPixelOutSideWindow() {
        Displayable window = new Window(store, 2, 2, 4, 2);

        window.setPixel(10, 10, 0, 0, 0);
    }
}