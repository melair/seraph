package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface GettableDisplay extends HasSize {
    PixelColour getPixel(int x, int y) throws NonExistentPixelException;
}
