package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

public class Scaling extends CheckedDisplayable {
    private final Displayable parent;

    private final int factor;

    public Scaling(Displayable parent, int factor) {
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
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        int baseX = x * factor;
        int baseY = y * factor;

        for (int xI = baseX; xI < baseX + factor; xI++) {
            for (int yI = baseY; yI < baseY + factor; yI++) {
                parent.setPixel(xI, yI, r, g, b);
            }
        }
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        return parent.getPixel(x * factor, y * factor);
    }
}
