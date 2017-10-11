package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

public class Greyscale extends Buffer {
    private final Displayable parent;

    public Greyscale(Displayable parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void setActualPixel(int x, int y, PixelColour pixelColour) {
        super.setActualPixel(x, y, pixelColour);
        setParentPixelScaled(x, y, pixelColour);
    }

    private void setParentPixelScaled(int x, int y, PixelColour pixelColour) {
        parent.setPixel(x, y, pixelColour.greyscale());
    }
}
