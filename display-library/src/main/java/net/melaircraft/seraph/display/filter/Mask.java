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
    public void setPixel(int x, int y, int r, int g, int b) throws NonExistentPixelException, InvalidPixelColourException {
        northBuffer.setPixel(x, y, r, g, b);
        outputPixelToParent(x, y);
    }

    private void outputPixelToParent(int x, int y) {
        PixelColour northPixel = northBuffer.getPixel(x, y);

        int r = northPixel.getRed();
        int g = northPixel.getBlue();
        int b = northPixel.getBlue();

        double intensity = ((0.2125 * r) + (0.7154 * g) + (0.0721 * b)) / 255;

        PixelColour maskPixel = mask.getPixel(x, y);

        r = (int) Math.round(intensity * maskPixel.getRed());
        g = (int) Math.round(intensity * maskPixel.getGreen());
        b = (int) Math.round(intensity * maskPixel.getBlue());

        parent.setPixel(x, y, r, g, b);
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
        protected void setActualPixel(int x, int y, int r, int g, int b) {
            super.setActualPixel(x, y, r, g, b);
            maskUpdate(x, y);
        }
    }
}
