package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.layout.Pane;

import java.util.Arrays;
import java.util.List;

public class Border implements DestinationDisplay {
    private final DestinationDisplay pane;

    public Border(DestinationDisplay destination, PixelColour colour, Side... sides) {
        List<Side> sideList = Arrays.asList(sides);

        int offsetX = sideList.contains(Side.LEFT) ? 1 : 0;
        int offsetY = sideList.contains(Side.TOP) ? 1 : 0;

        int reductionW = sideList.contains(Side.LEFT) ? 1 : 0;
        reductionW += sideList.contains(Side.RIGHT) ? 1 : 0;

        int reductionH = sideList.contains(Side.TOP) ? 1 : 0;
        reductionH += sideList.contains(Side.BOTTOM) ? 1 : 0;

        this.pane = new Pane(destination, offsetX, offsetY, destination.getWidth() - reductionW, destination.getHeight() - reductionH);

        if (sideList.contains(Side.TOP)) {
            for (int x = 0; x < destination.getWidth(); x++) {
                destination.setPixel(x, 0, colour);
            }
        }

        if (sideList.contains(Side.BOTTOM)) {
            for (int x = 0; x < destination.getWidth(); x++) {
                destination.setPixel(x, (destination.getHeight() - 1), colour);
            }
        }

        if (sideList.contains(Side.LEFT)) {
            for (int y = 0; y < destination.getHeight(); y++) {
                destination.setPixel(0, y, colour);
            }
        }

        if (sideList.contains(Side.RIGHT)) {
            for (int y = 0; y < destination.getHeight(); y++) {
                destination.setPixel((destination.getWidth() - 1), y, colour);
            }
        }
    }

    @Override
    public int getWidth() {
        return pane.getWidth();
    }

    @Override
    public int getHeight() {
        return pane.getHeight();
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
        pane.setPixel(x, y, pixelColour);
    }

    public enum Side {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }
}
