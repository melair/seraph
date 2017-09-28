package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public abstract class CheckedDisplayable implements Displayable {
    protected abstract void setActualPixel(int x, int y, int r, int g, int b);

    protected abstract PixelColour getActualPixel(int x, int y);

    @Override
    public void setPixel(int x, int y, int r, int g, int b) {
        checkBounds(x, y);
        checkColours(r, g, b);
        setActualPixel(x, y, r, g, b);
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        checkBounds(x, y);
        return getActualPixel(x, y);
    }

    private void checkBounds(int x, int y) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new NonExistentPixelException("The coordinates (" + x + "," + y + ") do not exist on this display.");
        }
    }

    private void checkColours(int r, int g, int b) {
        if (r < 0 || r >= 256 || g < 0 || g >= 256 || b < 0 || b >= 256) {
            throw new InvalidPixelColourException("The colour (" + r + "," + g + "," + b +") is invalid, all values must be 0-255.");
        }
    }
}
