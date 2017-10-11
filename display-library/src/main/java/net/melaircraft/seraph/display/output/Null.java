package net.melaircraft.seraph.display.output;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;

public final class Null implements CheckedDestinationDisplay {
    private final int width;
    private final int height;

    public Null(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {

    }
}