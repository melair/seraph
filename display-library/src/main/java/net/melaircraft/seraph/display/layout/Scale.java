package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;

public class Scale implements CheckedDestinationDisplay {
    private final DestinationDisplay destination;

    private final int factor;

    public Scale(DestinationDisplay destination, int factor) {
        this.destination = destination;
        this.factor = factor;
    }

    @Override
    public int getWidth() {
        return destination.getWidth() / factor;
    }

    @Override
    public int getHeight() {
        return destination.getHeight() / factor;
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        int baseX = x * factor;
        int baseY = y * factor;

        for (int xI = baseX; xI < baseX + factor; xI++) {
            for (int yI = baseY; yI < baseY + factor; yI++) {
                destination.setPixel(xI, yI, pixelColour);
            }
        }
    }
}
