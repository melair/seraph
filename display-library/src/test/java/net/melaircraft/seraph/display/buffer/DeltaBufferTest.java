package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DeltaBufferTest {
    @Test
    public void testDeltaBufferNothingDone() {
        FullDisplay store = mock(FullDisplay.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        DeltaBuffer deltaBuffer = new DeltaBuffer(store);

        reset(store);
        deltaBuffer.sendDelta();
        verifyZeroInteractions(store);
    }

    @Test
    public void testDeltaToBlack() {
        FullDisplay store = mock(FullDisplay.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        DeltaBuffer deltaBuffer = new DeltaBuffer(store);

        reset(store);
        deltaBuffer.setPixel(0, 0, PixelColour.BLACK);
        deltaBuffer.sendDelta();
        verifyZeroInteractions(store);
    }

    @Test
    public void testDeltaToRed() {
        FullDisplay store = mock(FullDisplay.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        DeltaBuffer deltaBuffer = new DeltaBuffer(store);

        reset(store);
        deltaBuffer.setPixel(0, 0, PixelColour.RED);
        deltaBuffer.sendDelta();
        verify(store).setPixel(0, 0, PixelColour.RED);
    }

    @Test
    public void testDeltaClearDelta() {
        FullDisplay store = mock(FullDisplay.class);
        when(store.getWidth()).thenReturn(2);
        when(store.getHeight()).thenReturn(1);

        DeltaBuffer deltaBuffer = new DeltaBuffer(store);

        deltaBuffer.setPixel(0, 0, PixelColour.RED);
        deltaBuffer.sendDelta();

        reset(store);
        deltaBuffer.clearCurrent();
        deltaBuffer.sendDelta();

        verify(store).setPixel(0, 0, PixelColour.BLACK);
    }
}