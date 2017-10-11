package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

public class Buffer extends CheckedDisplayable {
    private final Displayable parent;
    private final PixelColour[][] pixels;

    public Buffer(Displayable parent) {
        this.parent = parent;
        pixels = new PixelColour[parent.getWidth()][parent.getHeight()];

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
              pixels[x][y] = PixelColour.BLACK;
            }
        }
    }

    @Override
    protected void setActualPixel(int x, int y, PixelColour pixelColour) {
        pixels[x][y] = pixelColour;
        parent.setPixel(x, y, pixelColour);
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        return pixels[x][y];
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
    }
}
