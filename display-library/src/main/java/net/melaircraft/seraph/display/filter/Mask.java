package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Null;

public class Mask implements Displayable {
    private final Displayable mask;
    private final Displayable northBuffer;
    private final Displayable parent;

    public Mask(Displayable parent) {
        northBuffer = new Buffer(new Null(parent.getWidth(), parent.getHeight()));
        mask = new MaskLayer(new Null(parent.getWidth(), parent.getHeight()));
        this.parent = parent;
    }

    public Displayable getMask() {
        return mask;
    }

    private void maskUpdate(int x, int y) {
        outputPixelToParent(x, y);
    }


    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
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
        parent.setPixel(x, y, output);
    }

    @Override
    public PixelColour getPixel(int x, int y) throws NonExistentPixelException {
        return northBuffer.getPixel(x, y);
    }

    class MaskLayer extends Buffer {
        public MaskLayer(Displayable parent) {
            super(parent);
        }

        @Override
        protected void setActualPixel(int x, int y, PixelColour pixelColour) {
            super.setActualPixel(x, y, pixelColour);
            maskUpdate(x, y);
        }
    }
}
