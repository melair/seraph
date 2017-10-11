package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Greyscale implements DestinationDisplay {
    private final DestinationDisplay parent;

    public Greyscale(DestinationDisplay parent) {
        this.parent = parent;
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
        parent.setPixel(x, y, pixelColour.greyscale());
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
    }
}
