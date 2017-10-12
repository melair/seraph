package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.CheckedFullDisplay;
import net.melaircraft.seraph.display.PixelColour;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Buffer implements CheckedFullDisplay {
    private final PixelColour[][] pixels;
    private final Set<DisplayCallback> callbacks = new HashSet<>();
    private final int width;
    private final int height;

    public Buffer(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new PixelColour[width][height];

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
              pixels[x][y] = PixelColour.BLACK;
            }
        }
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        pixels[x][y] = pixelColour;
        notifyCallbacks(x, y, pixelColour);
    }

    @Override
    public PixelColour getActualPixel(int x, int y) {
        return pixels[x][y];
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
    public Collection<DisplayCallback> getCallbacks() {
        return callbacks;
    }
}
