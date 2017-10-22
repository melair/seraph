package net.melaircraft.seraph.server.display;

import net.melaircraft.seraph.server.config.model.DisplayConfiguration;
import net.melaircraft.seraph.server.exceptions.DisplayManagerException;
import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DisplayManagerTest {
    @Test(expected = DisplayManagerException.class)
    public void testInvalidHostnameThrowsException() {
        DisplayConfiguration displayConfiguration = mock(DisplayConfiguration.class);
        when(displayConfiguration.getHostname()).thenReturn(" ");
        new DisplayManager(displayConfiguration, null);
    }

    @Test
    public void testDisplayIsScheduled() {
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);

        DisplayConfiguration displayConfiguration = mock(DisplayConfiguration.class);
        when(displayConfiguration.getHostname()).thenReturn("localhost");
        when(displayConfiguration.getPort()).thenReturn(7777);
        when(displayConfiguration.getWidth()).thenReturn(128);
        when(displayConfiguration.getHeight()).thenReturn(64);

        int frameRate = 20;
        long interval = 1000 / 20;

        when(displayConfiguration.getFrameRate()).thenReturn(frameRate);

        new DisplayManager(displayConfiguration, executorService);
        verify(executorService).scheduleAtFixedRate(any(), eq(0L), eq(interval), eq(TimeUnit.MILLISECONDS));
    }
}