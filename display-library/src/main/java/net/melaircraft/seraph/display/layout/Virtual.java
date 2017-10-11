package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.InvalidVirtualSizeException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Null;

public class Virtual implements FullDisplay {
    private final DestinationDisplay parent;
    private final FullDisplay canvas;

    private final boolean wrap;

    private int viewportX = 0;
    private int viewportY = 0;

    public Virtual(DestinationDisplay parent, int width, int height, boolean wrap) {
        if (width < parent.getWidth() || height < parent.getHeight()) {
            throw new InvalidVirtualSizeException("Virtual display can not be smaller then parent display.");
        }

        this.parent = parent;
        this.canvas = new Buffer(new Null(width, height));
        this.wrap = wrap;
    }

    public void moveViewport(int x, int y) {
        viewportX = x;
        viewportY = y;

        for (int xI = 0; xI < parent.getWidth(); xI++) {
            for (int yI = 0; yI < parent.getHeight(); yI++) {
                int canvasX = viewportX + xI;
                int canvasY = viewportY + yI;

                PixelColour colour = PixelColour.BLACK;

                if (wrap) {
                    canvasX = canvasX % canvas.getWidth();
                    canvasY = canvasY % canvas.getHeight();
                }

                if (canvasX < canvas.getWidth() && canvasY < canvas.getHeight()) {
                    colour = canvas.getPixel(canvasX, canvasY);
                }

                parent.setPixel(xI, yI, colour);
            }
        }
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        canvas.setPixel(x, y, pixelColour);

        int adjustX = x - viewportX;
        int adjustY = y - viewportY;

        if (adjustX >= 0 && adjustX < parent.getWidth() && adjustY >= 0 && adjustY < parent.getHeight()) {
            parent.setPixel(adjustX, adjustY, pixelColour);
        } else {
            adjustX += parent.getWidth();
            adjustY += parent.getHeight();

            if (adjustX >= 0 && adjustX < parent.getWidth() && adjustY >= 0 && adjustY < parent.getHeight()) {
                parent.setPixel(adjustX, adjustY, pixelColour);
            }
        }
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        return canvas.getPixel(x, y);
    }
}
