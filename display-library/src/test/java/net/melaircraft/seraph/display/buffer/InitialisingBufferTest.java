package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InitialisingBufferTest {
    @Test
    public void testInitialisationSetsParentToBlack() {
        Displayable store = mock(Displayable.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        InitialisingBuffer buffer = new InitialisingBuffer(store);

        verify(store).setPixel(0, 0, PixelColour.BLACK);
        verify(store).setPixel(1, 0, PixelColour.BLACK);
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

        InitialisingBuffer buffer = new InitialisingBuffer(store, img, imgWidth, imgHeight);

        verify(store).setPixel(0, 0, PixelColour.RED);
        verify(store).setPixel(0, 1, PixelColour.BLACK);
        verify(store).setPixel(1, 0, PixelColour.GREEN);
        verify(store).setPixel(1, 1, PixelColour.BLACK);
        verify(store).setPixel(2, 0, PixelColour.BLUE);
        verify(store).setPixel(2, 1, PixelColour.BLACK);
        verify(store).setPixel(3, 0, PixelColour.BLACK);
        verify(store).setPixel(3, 1, PixelColour.BLACK);
    }
}