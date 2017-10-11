package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public interface CheckedSourceDisplay extends SourceDisplay {
    PixelColour getActualPixel(int x, int y);

    @Override
    default PixelColour getPixel(int x, int y) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new NonExistentPixelException("The coordinates (" + x + "," + y + ") do not exist on this display.");
        }

        return getActualPixel(x, y);
    }
}
