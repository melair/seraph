package net.melaircraft.seraph.display.output;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.PixelColour;

public final class Null extends CheckedDisplayable {
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
    protected void setActualPixel(int x, int y, int r, int g, int b) {
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        return PixelColour.BLACK;
    }
}