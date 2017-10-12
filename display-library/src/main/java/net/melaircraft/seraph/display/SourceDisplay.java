package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.NonExistentPixelException;

import java.util.Collection;

public interface SourceDisplay extends HasSize {
    PixelColour getPixel(int x, int y) throws NonExistentPixelException;

    default void registerCallback(DisplayCallback displayCallback) {
        getCallbacks().add(displayCallback);
    }

    default void notifyCallbacks(int x, int y, PixelColour colour) {
        getCallbacks().forEach((c) -> c.notification(this, x, y, colour));
    }

    Collection<DisplayCallback> getCallbacks();

    interface DisplayCallback {
        void notification(SourceDisplay display, int x, int y, PixelColour colour);
    }
}
