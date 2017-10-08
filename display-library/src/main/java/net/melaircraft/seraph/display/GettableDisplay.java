package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface GettableDisplay {
    PixelColour getPixel(int x, int y) throws NonExistentPixelException;
}
