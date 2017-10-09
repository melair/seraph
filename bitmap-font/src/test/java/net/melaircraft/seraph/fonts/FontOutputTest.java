package net.melaircraft.seraph.fonts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FontOutputTest {
    @Test
    public void testDimensions() {
        FontOutput fontOutput = new FontOutput(5, 10);

        assertEquals(5, fontOutput.getWidth());
        assertEquals(10, fontOutput.getHeight());
    }

    @Test
    public void testSetAndGet() {
        FontOutput fontOutput = new FontOutput(5, 10);
        fontOutput.set(3, 3);
        assertTrue(fontOutput.getBitmap()[3][3]);
    }
}