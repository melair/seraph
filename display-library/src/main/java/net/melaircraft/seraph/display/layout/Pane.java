package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPaneLocationException;

public class Pane implements CheckedDestinationDisplay {
    private final DestinationDisplay destination;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public Pane(DestinationDisplay destination, int offsetX, int offsetY, int width, int height) throws InvalidPaneLocationException {
        this.destination = destination;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        if (offsetX + width > destination.getWidth() || offsetY + height > destination.getHeight()) {
            throw new InvalidPaneLocationException("Can not place a window of " + width + "," + height + " at " + offsetX + "," + offsetY + " when destination is only " + destination.getWidth() + "," + destination.getHeight() + ".");
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        destination.setPixel(x + offsetX, y + offsetY, pixelColour);
    }
}
