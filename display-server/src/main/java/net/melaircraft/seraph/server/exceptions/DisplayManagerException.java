package net.melaircraft.seraph.server.exceptions;

public final class DisplayManagerException extends RuntimeException {
    public DisplayManagerException(String message) {
        super(message);
    }

    public DisplayManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
