package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.MismatchedDisplaySizeException;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeeTest {
    @Test(expected = MismatchedDisplaySizeException.class)
    public void testAddingParentsWithDifferingSizesFails() {
        Displayable one = new Null(2, 1);
        Displayable two = new Null(1, 1);

        Displayable tee = new Tee(one, two);
    }

    @Test
    public void testDimensionsPassThrough() {
        Displayable one = new Null(1, 1);

        Displayable tee = new Tee(one);

        assertEquals(one.getWidth(), tee.getWidth());
        assertEquals(one.getHeight(), tee.getHeight());
    }

    @Test
    public void testSetPixelToAll() {
        Displayable one = mock(Displayable.class);
        when(one.getHeight()).thenReturn(1);
        when(one.getWidth()).thenReturn(1);

        Displayable two = mock(Displayable.class);
        when(two.getHeight()).thenReturn(1);
        when(two.getWidth()).thenReturn(1);

        Displayable tee = new Tee(one, two);

        tee.setPixel(0, 0, 1, 1, 1);

        verify(one).setPixel(0, 0, 1, 1, 1);
        verify(two).setPixel(0, 0, 1, 1, 1);
    }

    @Test
    public void testGetPixelPassThrough() {
        Displayable store = new Buffer(new Null(1, 1));

        Displayable tee = new Tee(store);

        PixelColour colour = PixelColour.RED;

        tee.setPixel(0, 0, colour);
        assertEquals(colour, tee.getPixel(0, 0));
    }
}