package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrightnessTest {
    private FullDisplay store = new Buffer(4, 2);

    @Test
    public void testDimensionsPassThrough() {
        DestinationDisplay brightness = new Brightness(store, 1.0F);

        assertEquals(store.getWidth(), brightness.getWidth());
        assertEquals(store.getHeight(), brightness.getHeight());
    }

    @Test
    public void testBrightnessInitialSet() {
        Brightness brightness = new Brightness(store, 0.5F);

        brightness.setPixel(0, 0, PixelColour.WHITE);

        assertEquals(128, store.getPixel(0, 0).getRed());
        assertEquals(128, store.getPixel(0, 0).getGreen());
        assertEquals(128, store.getPixel(0, 0).getBlue());
    }

    @Test
    public void testBrightnessSecondSet() {
        Brightness brightness = new Brightness(store, 0.5F);

        brightness.setPixel(0, 0, PixelColour.WHITE);

        brightness.setBrightness(0.25F);

        assertEquals(64, store.getPixel(0, 0).getRed());
        assertEquals(64, store.getPixel(0, 0).getGreen());
        assertEquals(64, store.getPixel(0, 0).getBlue());
    }
}