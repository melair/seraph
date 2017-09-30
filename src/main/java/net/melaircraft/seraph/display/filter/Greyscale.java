package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.output.Buffer;

public class Greyscale extends Buffer {
    private final Displayable parent;

    public Greyscale(Displayable parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        super.setActualPixel(x, y, r, g, b);
        setParentPixelScaled(x, y, r, g, b);
    }

    private void setParentPixelScaled(int x, int y, int r, int g, int b) {
        int grey = (int) Math.round((0.2125 * r) + (0.7154 * g) + (0.0721 * b));
        parent.setPixel(x, y, grey, grey, grey);
    }
}
