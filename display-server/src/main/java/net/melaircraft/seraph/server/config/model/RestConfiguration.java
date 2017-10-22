package net.melaircraft.seraph.server.config.model;

public class RestConfiguration {
    private String host = "localhost";
    private int port = 8000;
    private boolean enabled = true;
    private boolean allowWrite = true;

    public RestConfiguration() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getPort() {
        return port;
    }

    public boolean isAllowWrite() {
        return allowWrite;
    }

    public String getHost() {
        return host;
    }
}
