package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
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
        FullDisplay one = new Null(2, 1);
        FullDisplay two = new Null(1, 1);

        DestinationDisplay tee = new Tee(one, two);
    }

    @Test
    public void testDimensionsPassThrough() {
        FullDisplay one = new Null(1, 1);

        DestinationDisplay tee = new Tee(one);

        assertEquals(one.getWidth(), tee.getWidth());
        assertEquals(one.getHeight(), tee.getHeight());
    }

    @Test
    public void testSetPixelToAll() {
        FullDisplay one = mock(FullDisplay.class);
        when(one.getHeight()).thenReturn(1);
        when(one.getWidth()).thenReturn(1);

        FullDisplay two = mock(FullDisplay.class);
        when(two.getHeight()).thenReturn(1);
        when(two.getWidth()).thenReturn(1);

        DestinationDisplay tee = new Tee(one, two);

        tee.setPixel(0, 0, PixelColour.WHITE);

        verify(one).setPixel(0, 0, PixelColour.WHITE);
        verify(two).setPixel(0, 0, PixelColour.WHITE);
    }
}