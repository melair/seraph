package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Greyscale implements DestinationDisplay {
    private final DestinationDisplay destination;

    public Greyscale(DestinationDisplay destination) {
        this.destination = destination;
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
        destination.setPixel(x, y, pixelColour.greyscale());
    }

    @Override
    public int getWidth() {
        return destination.getWidth();
    }

    @Override
    public int getHeight() {
        return destination.getHeight();
    }
}
