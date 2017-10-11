package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Mask implements DestinationDisplay, Buffer.BufferCallback {
    private final Buffer mask;
    private final Buffer northBuffer;
    private final DestinationDisplay destination;

    public Mask(DestinationDisplay destination) {
        northBuffer = new Buffer(destination.getWidth(), destination.getHeight());

        mask = new Buffer(destination.getWidth(), destination.getHeight());
        mask.registerCallback(this);

        this.destination = destination;
    }

    public DestinationDisplay getMask() {
        return mask;
    }

    @Override
    public int getWidth() {
        return destination.getWidth();
    }

    @Override
    public int getHeight() {
        return destination.getHeight();
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        northBuffer.setPixel(x, y, pixelColour);
        outputPixelToParent(x, y);
    }

    private void outputPixelToParent(int x, int y) {
        PixelColour northPixel = northBuffer.getPixel(x, y);
        PixelColour maskPixel = mask.getPixel(x, y);

        PixelColour output = northPixel.greyscale().multiply(maskPixel);
        destination.setPixel(x, y, output);
    }

    @Override
    public void updated(int x, int y, PixelColour colour) {
        outputPixelToParent(x, y);
    }
}
