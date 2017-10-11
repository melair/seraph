package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

public class Greyscale extends Buffer {
    private final DestinationDisplay parent;

    public Greyscale(DestinationDisplay parent) {
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
