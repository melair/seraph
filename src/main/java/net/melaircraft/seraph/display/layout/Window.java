package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidWindowLocationException;

public class Window extends CheckedDisplayable {
    private final Displayable parent;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public Window(Displayable parent, int offsetX, int offsetY, int width, int height) throws InvalidWindowLocationException {
        this.parent = parent;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        if (offsetX + width >= parent.getWidth() || offsetY + height >= parent.getHeight()) {
            throw new InvalidWindowLocationException("Can not place a window of " + width + "," + height + " at " + offsetX + "," + offsetY + " when parent is only " + parent.getWidth() + "," + parent.getHeight() + ".");
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
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        parent.setPixel(x + offsetX, y + offsetY, r, g, b);
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        return parent.getPixel(x + offsetX, y + offsetY);
    }
}
