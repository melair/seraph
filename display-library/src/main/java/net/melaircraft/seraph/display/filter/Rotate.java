package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Rotate implements DestinationDisplay {
    private final DestinationDisplay destination;
    private final Rotation rotation;

    public Rotate(DestinationDisplay destination, Rotation rotation) {
        this.destination = destination;
        this.rotation = rotation;
    }

    @Override
    public int getWidth() {
        return rotation.shouldSwapDimension() ? destination.getHeight() : destination.getWidth();
    }

    @Override
    public int getHeight() {
        return rotation.shouldSwapDimension() ? destination.getWidth() : destination.getHeight();
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        int translatedX = translateX(x, y);
        int translatedY = translateY(x, y);

        destination.setPixel(translatedX, translatedY, pixelColour);
    }

    private int translateX(int x, int y) {
        switch(rotation) {
            default:
                return x;
            case ROTATE_90:
                return (destination.getWidth() - 1) - y;
            case ROTATE_180:
                return (destination.getWidth() - 1) - x;
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
                return (destination.getHeight() - 1) - y;
            case ROTATE_270:
                return (destination.getHeight() - 1) - x;
        }
    }

    public enum Rotation {
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
