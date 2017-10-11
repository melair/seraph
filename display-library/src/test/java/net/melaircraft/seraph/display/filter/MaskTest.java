package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaskTest {
    private FullDisplay store = new Buffer(1, 1);

    @Test
    public void testThatSettingAPixelWithoutAMaskWontDoAnything() {
        Mask mask = new Mask(store);

        mask.setPixel(0, 0, PixelColour.RED);

        assertEquals(PixelColour.BLACK, store.getPixel(0, 0));
    }

    @Test
    public void testThatSettingAPixelAfterTheMaskWorks() {
        Mask mask = new Mask(store);

        mask.getMask().setPixel(0, 0, PixelColour.WHITE);
        mask.setPixel(0, 0, PixelColour.RED);

        assertEquals(new PixelColour(54, 54, 54), store.getPixel(0, 0));
    }

    @Test
    public void testThatSettingAPixelBeforeTheMaskWorks() {
        Mask mask = new Mask(store);

        mask.setPixel(0, 0, PixelColour.RED);
        mask.getMask().setPixel(0, 0, PixelColour.WHITE);

        assertEquals(new PixelColour(54, 54, 54), store.getPixel(0, 0));
    }
}