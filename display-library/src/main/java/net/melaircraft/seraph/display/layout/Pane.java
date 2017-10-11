package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPaneLocationException;

public class Pane implements CheckedDestinationDisplay {
    private final DestinationDisplay parent;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public Pane(DestinationDisplay parent, int offsetX, int offsetY, int width, int height) throws InvalidPaneLocationException {
        this.parent = parent;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        if (offsetX + width > parent.getWidth() || offsetY + height > parent.getHeight()) {
            throw new InvalidPaneLocationException("Can not place a window of " + width + "," + height + " at " + offsetX + "," + offsetY + " when parent is only " + parent.getWidth() + "," + parent.getHeight() + ".");
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
        parent.setPixel(x + offsetX, y + offsetY, pixelColour);
    }
}
