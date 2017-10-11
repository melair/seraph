package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrightnessTest {
    Displayable store = new Buffer(new Null(4, 2));

    @Test
    public void testDimensionsPassThrough() {
        Displayable brightness = new Brightness(store, 1.0F);

        assertEquals(store.getWidth(), brightness.getWidth());
        assertEquals(store.getHeight(), brightness.getHeight());
    }

    @Test
    public void testBrightnessInitialSet() {
        Displayable brightness = new Brightness(store, 0.5F);

        brightness.setPixel(0, 0, PixelColour.WHITE);

        assertEquals(128, store.getPixel(0, 0).getRed());
        assertEquals(255, brightness.getPixel(0, 0).getRed());

        assertEquals(128, store.getPixel(0, 0).getGreen());
        assertEquals(255, brightness.getPixel(0, 0).getGreen());

        assertEquals(128, store.getPixel(0, 0).getBlue());
        assertEquals(255, brightness.getPixel(0, 0).getBlue());
    }

    @Test
    public void testBrightnessSecondSet() {
        Brightness brightness = new Brightness(store, 0.5F);

        brightness.setPixel(0, 0, PixelColour.WHITE);

        brightness.setBrightness(0.25F);

        assertEquals(64, store.getPixel(0, 0).getRed());
        assertEquals(255, brightness.getPixel(0, 0).getRed());

        assertEquals(64, store.getPixel(0, 0).getGreen());
        assertEquals(255, brightness.getPixel(0, 0).getGreen());

        assertEquals(64, store.getPixel(0, 0).getBlue());
        assertEquals(255, brightness.getPixel(0, 0).getBlue());
    }
}