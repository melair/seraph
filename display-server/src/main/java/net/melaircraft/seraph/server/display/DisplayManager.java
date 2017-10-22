package net.melaircraft.seraph.server.display;

import net.melaircraft.seraph.display.output.seraph.SeraphProtocol;
import net.melaircraft.seraph.server.config.model.DisplayConfiguration;
import net.melaircraft.seraph.server.exceptions.DisplayManagerException;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DisplayManager {
    private final SeraphProtocol seraphProtocol;

    public DisplayManager(DisplayConfiguration displayConfiguration, ScheduledExecutorService scheduledExecutorService) {
        InetAddress inetAddress;

        try {
            inetAddress = InetAddress.getByName(displayConfiguration.getHostname());
        } catch (UnknownHostException e) {
            throw new DisplayManagerException("Could not look up hostname of Seraph server.", e);
        }

        try {
            seraphProtocol = new SeraphProtocol(displayConfiguration.getWidth(), displayConfiguration.getHeight(), inetAddress, displayConfiguration.getPort(), displayConfiguration.getMTU());
        } catch (SocketException e) {
            throw new DisplayManagerException("Could not open datagram socket.", e);
        }

        int interval = 1000 / displayConfiguration.getFrameRate();
        scheduledExecutorService.scheduleAtFixedRate(seraphProtocol, 0, interval, TimeUnit.MILLISECONDS);
    }
}
