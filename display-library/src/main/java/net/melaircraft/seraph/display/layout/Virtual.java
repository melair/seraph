package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.InvalidVirtualSizeException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Virtual implements DestinationDisplay {
    private final DestinationDisplay destination;
    private final Buffer canvas;

    private final boolean wrap;

    private int viewportX = 0;
    private int viewportY = 0;

    public Virtual(DestinationDisplay destination, int width, int height, boolean wrap) {
        if (width < destination.getWidth() || height < destination.getHeight()) {
            throw new InvalidVirtualSizeException("Virtual display can not be smaller then destination display.");
        }

        this.destination = destination;
        this.canvas = new Buffer(width, height);
        this.wrap = wrap;
    }

    public void moveViewport(int x, int y) {
        viewportX = x;
        viewportY = y;

        for (int xI = 0; xI < destination.getWidth(); xI++) {
            for (int yI = 0; yI < destination.getHeight(); yI++) {
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

                destination.setPixel(xI, yI, colour);
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

        if (adjustX >= 0 && adjustX < destination.getWidth() && adjustY >= 0 && adjustY < destination.getHeight()) {
            destination.setPixel(adjustX, adjustY, pixelColour);
        } else {
            adjustX += destination.getWidth();
            adjustY += destination.getHeight();

            if (adjustX >= 0 && adjustX < destination.getWidth() && adjustY >= 0 && adjustY < destination.getHeight()) {
                destination.setPixel(adjustX, adjustY, pixelColour);
            }
        }
    }
}
