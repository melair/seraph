package net.melaircraft.seraph.server.config.model;

public class DisplayConfiguration {
    private String hostname;
    private int port;
    private int width;
    private int height;
    private int mtu = 1450;
    private int framerate = 20;

    public DisplayConfiguration() {
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMTU() {
        return mtu;
    }

    public int getFrameRate() {
        return framerate;
    }
}
