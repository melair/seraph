package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {
    private FullDisplay store = new Buffer(4, 2);

    @Test
    public void testLayerIsGivenOut() {
        Stack stack = new Stack(store);
        DestinationDisplay page = stack.addLayer();

        assertEquals(store.getWidth(), page.getWidth());
        assertEquals(store.getHeight(), page.getHeight());
    }

    @Test
    public void testLayerBuffers() {
        Stack stack = new Stack(store);
        DestinationDisplay page = stack.addLayer();

        page.setPixel(0, 0, PixelColour.RED);
        assertEquals(PixelColour.RED, store.getPixel(0,0));
    }

    @Test
    public void testLayerOneOverwritesLayerTwo() {
        Stack stack = new Stack(store);
        DestinationDisplay one = stack.addLayer();
        DestinationDisplay two = stack.addLayer();

        two.setPixel(0, 0, PixelColour.RED);
        assertEquals(PixelColour.RED, store.getPixel(0,0));
        one.setPixel(0, 0, PixelColour.BLUE);
        assertEquals(PixelColour.BLUE, store.getPixel(0,0));
    }

    @Test
    public void testLayerRemoval() {
        Stack stack = new Stack(store);
        DestinationDisplay one = stack.addLayer();
        DestinationDisplay two = stack.addLayer();

        two.setPixel(0, 0, PixelColour.RED);
        one.setPixel(0, 0, PixelColour.BLUE);

        assertEquals(PixelColour.BLUE, store.getPixel(0,0));
        one.setPixel(0, 0, PixelColour.BLACK);
        assertEquals(PixelColour.RED, store.getPixel(0,0));
        two.setPixel(0, 0, PixelColour.BLACK);
        assertEquals(PixelColour.BLACK, store.getPixel(0,0));
    }
}