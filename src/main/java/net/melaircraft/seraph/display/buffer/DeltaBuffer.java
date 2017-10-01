package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

public class DeltaBuffer {
    private final Displayable parent;

    private PixelColour[][] current;
    private PixelColour[][] previous;
    
    private final int width;
    private final int height;

    public DeltaBuffer(Displayable parent) {
        this.parent = parent;

        width = parent.getWidth();
        height = parent.getHeight();
        
        current = new PixelColour[width][height];
        previous = new PixelColour[width][height];

        clearCurrent();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                previous[x][y] = PixelColour.BLACK;
            }
        }
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        current[x][y] = new PixelColour(r, g, b);
    }

    public void setPixel(int x, int y, PixelColour pixelColour) {
        setPixel(x, y, pixelColour.getRed(), pixelColour.getGreen(), pixelColour.getBlue());
    }

    public void clearCurrent() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                current[x][y] = PixelColour.BLACK;
            }
        }
    }

    public void sendDelta() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!previous[x][y].equals(current[x][y])) {
                    parent.setPixel(x, y, current[x][y].getRed(), current[x][y].getGreen(), current[x][y].getBlue());
                    previous[x][y] = current[x][y];
                }
            }
        }
    }
}
