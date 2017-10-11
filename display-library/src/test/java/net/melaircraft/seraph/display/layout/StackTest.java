package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {
    FullDisplay store = new Buffer(new Null(4, 2));

    @Test
    public void testLayerIsGivenOut() {
        Stack stack = new Stack(store);
        FullDisplay page = stack.addLayer();

        assertEquals(store.getWidth(), page.getWidth());
        assertEquals(store.getHeight(), page.getHeight());
    }

    @Test
    public void testLayerBuffers() {
        Stack stack = new Stack(store);
        FullDisplay page = stack.addLayer();

        page.setPixel(0, 0, PixelColour.RED);
        assertEquals(PixelColour.RED, page.getPixel(0,0));
    }

    @Test
    public void testLayerOneOverwritesLayerTwo() {
        Stack stack = new Stack(store);
        FullDisplay one = stack.addLayer();
        FullDisplay two = stack.addLayer();

        two.setPixel(0, 0, PixelColour.RED);
        assertEquals(PixelColour.RED, store.getPixel(0,0));
        one.setPixel(0, 0, PixelColour.BLUE);
        assertEquals(PixelColour.BLUE, store.getPixel(0,0));
    }

    @Test
    public void testLayerRemoval() {
        Stack stack = new Stack(store);
        FullDisplay one = stack.addLayer();
        FullDisplay two = stack.addLayer();

        two.setPixel(0, 0, PixelColour.RED);
        one.setPixel(0, 0, PixelColour.BLUE);

        assertEquals(PixelColour.BLUE, store.getPixel(0,0));
        one.setPixel(0, 0, PixelColour.BLACK);
        assertEquals(PixelColour.RED, store.getPixel(0,0));
        two.setPixel(0, 0, PixelColour.BLACK);
        assertEquals(PixelColour.BLACK, store.getPixel(0,0));
    }
}