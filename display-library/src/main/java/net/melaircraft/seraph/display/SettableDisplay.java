package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface SettableDisplay extends HasSize {
    void setPixel(int x, int y, int r, int g, int b) throws NonExistentPixelException, InvalidPixelColourException;

    default void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        setPixel(x, y, pixelColour.getRed(), pixelColour.getGreen(), pixelColour.getBlue());
    }
}
