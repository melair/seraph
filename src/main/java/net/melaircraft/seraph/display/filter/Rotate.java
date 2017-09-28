package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

public class Rotate implements Displayable {
    private final Displayable parent;
    private final Rotation rotation;

    public Rotate(Displayable parent, Rotation rotation) {
        this.parent = parent;
        this.rotation = rotation;
    }

    @Override
    public int getWidth() {
        return rotation.shouldSwapDimension() ? parent.getHeight() : parent.getWidth();
    }

    @Override
    public int getHeight() {
        return rotation.shouldSwapDimension() ? parent.getWidth() : parent.getHeight();
    }

    @Override
    public void setPixel(int x, int y, int r, int g, int b) {
        int translatedX = translateX(x, y);
        int translatedY = translateY(x, y);

        parent.setPixel(translatedX, translatedY, r, g, b);
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        int translatedX = translateX(x, y);
        int translatedY = translateY(x, y);

        return parent.getPixel(translatedX, translatedY);
    }

    private int translateX(int x, int y) {
        switch(rotation) {
            default:
                return x;
            case ROTATE_90:
                return (parent.getWidth() - 1) - y;
            case ROTATE_180:
                return (parent.getWidth() - 1) - y;
            case ROTATE_270:
                return y;
        }
    }

    private int translateY(int x, int y) {
        switch(rotation) {
            default:
                return y;
            case ROTATE_90:
                return x;
            case ROTATE_180:
                return (parent.getHeight() - 1) - x;
            case ROTATE_270:
                return (parent.getHeight() - 1) - x;
        }
    }

    enum Rotation {
        ROTATE_0(false),
        ROTATE_90(true),
        ROTATE_180(false),
        ROTATE_270(true);

        private final boolean swapDimensions;

        Rotation(boolean swapDimensions) {
            this.swapDimensions = swapDimensions;
        }

        public boolean shouldSwapDimension() {
            return swapDimensions;
        }
    }
}
