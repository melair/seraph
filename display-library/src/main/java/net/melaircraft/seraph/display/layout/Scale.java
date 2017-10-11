package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;

public class Scale implements CheckedDestinationDisplay {
    private final DestinationDisplay parent;

    private final int factor;

    public Scale(DestinationDisplay parent, int factor) {
        this.parent = parent;
        this.factor = factor;
    }

    @Override
    public int getWidth() {
        return parent.getWidth() / factor;
    }

    @Override
    public int getHeight() {
        return parent.getHeight() / factor;
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        int baseX = x * factor;
        int baseY = y * factor;

        for (int xI = baseX; xI < baseX + factor; xI++) {
            for (int yI = baseY; yI < baseY + factor; yI++) {
                parent.setPixel(xI, yI, pixelColour);
            }
        }
    }
}
