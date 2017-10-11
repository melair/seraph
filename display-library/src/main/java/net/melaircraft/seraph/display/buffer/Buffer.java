package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.CheckedFullDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.Null;

public class Buffer implements CheckedFullDisplay {
    private final DestinationDisplay parent;
    private final PixelColour[][] pixels;

    public Buffer(int width, int height) {
        this(new Null(width, height));
    }

    @Deprecated
    public Buffer(DestinationDisplay parent) {
        this.parent = parent;
        pixels = new PixelColour[parent.getWidth()][parent.getHeight()];

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
              pixels[x][y] = PixelColour.BLACK;
            }
        }
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        pixels[x][y] = pixelColour;
        parent.setPixel(x, y, pixelColour);
    }

    @Override
    public PixelColour getActualPixel(int x, int y) {
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
