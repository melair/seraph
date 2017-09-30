package net.melaircraft.seraph.display.output;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BufferTest {
    @Test
    public void testInitialisationSetsParentToBlack() {
        Displayable store = mock(Displayable.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        Buffer buffer = new Buffer(store);

        verify(store).setPixel(0, 0, 0, 0, 0);
        verify(store).setPixel(1, 0, 0, 0, 0);
    }

    @Test
    public void testInitialisationWithBlock() {
        Displayable store = mock(Displayable.class);
        when(store.getWidth()).thenReturn(4);
        when(store.getHeight()).thenReturn(2);

        int imgWidth = 3;
        int imgHeight = 1;

        PixelColour [][] img = new PixelColour[imgWidth][imgHeight];
        img[0][0] = PixelColour.RED;
        img[1][0] = PixelColour.GREEN;
        img[2][0] = PixelColour.BLUE;

        Buffer buffer = new Buffer(store, img, imgWidth, imgHeight);

        verify(store).setPixel(0, 0, 255, 0, 0);
        verify(store).setPixel(0, 1, 0, 0, 0);
        verify(store).setPixel(1, 0, 0, 255, 0);
        verify(store).setPixel(1, 1, 0, 0, 0);
        verify(store).setPixel(2, 0, 0, 0, 255);
        verify(store).setPixel(2, 1, 0, 0, 0);
        verify(store).setPixel(3, 0, 0, 0, 0);
        verify(store).setPixel(3, 1, 0, 0, 0);
    }
}