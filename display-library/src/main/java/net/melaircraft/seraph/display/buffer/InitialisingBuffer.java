package net.melaircraft.seraph.display.buffer;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

public class InitialisingBuffer extends Buffer {
    public InitialisingBuffer(Displayable parent) {
        this(parent, null, 0, 0);
    }

    public InitialisingBuffer(Displayable parent, PixelColour[][] img, int imgWidth, int imgHeight) {
        super(parent);

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (x < imgWidth && y < imgHeight) {
                    PixelColour colour = img[x][y];
                    setActualPixel(x, y, colour);
                } else {
                    setActualPixel(x, y, PixelColour.BLACK);
                }
            }
        }
    }
}
