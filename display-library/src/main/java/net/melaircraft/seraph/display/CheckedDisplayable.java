package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public abstract class CheckedDisplayable implements FullDisplay {
    protected abstract void setActualPixel(int x, int y, PixelColour pixelColour);

    protected abstract PixelColour getActualPixel(int x, int y);

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        checkBounds(x, y);
        setActualPixel(x, y, pixelColour);
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
}
