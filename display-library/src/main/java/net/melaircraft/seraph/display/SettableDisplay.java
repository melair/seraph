package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface SettableDisplay extends HasSize {
    void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException;
}
