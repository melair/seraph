package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface CheckedDestinationDisplay extends DestinationDisplay {
    void setActualPixel(int x, int y, PixelColour pixelColour);

    @Override
    default void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new NonExistentPixelException("The coordinates (" + x + "," + y + ") do not exist on this display.");
        }

        setActualPixel(x, y, pixelColour);
    }
}
