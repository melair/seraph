package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;

public class DeltaBuffer {
    private final DestinationDisplay parent;

    private final PixelColour[][] current;
    private final PixelColour[][] previous;
    
    private final int width;
    private final int height;

    public DeltaBuffer(DestinationDisplay parent) {
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

    public void setPixel(int x, int y, PixelColour pixelColour) {
        current[x][y] = pixelColour;
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
                    parent.setPixel(x, y, current[x][y]);
                    previous[x][y] = current[x][y];
                }
            }
        }
    }
}
