package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.layout.Pane;

import java.util.Arrays;
import java.util.List;

public class Border extends CheckedDisplayable {
    private final Displayable pane;

    public Border(Displayable parent, PixelColour colour, Side... sides) {
        List<Side> sideList = Arrays.asList(sides);

        int offsetX = sideList.contains(Side.LEFT) ? 1 : 0;
        int offsetY = sideList.contains(Side.TOP) ? 1 : 0;

        int reductionW = sideList.contains(Side.LEFT) ? 1 : 0;
        reductionW += sideList.contains(Side.RIGHT) ? 1 : 0;

        int reductionH = sideList.contains(Side.TOP) ? 1 : 0;
        reductionH += sideList.contains(Side.BOTTOM) ? 1 : 0;

        this.pane = new Pane(parent, offsetX, offsetY, parent.getWidth() - reductionW, parent.getHeight() - reductionH);

        if (sideList.contains(Side.TOP)) {
            for (int x = 0; x < parent.getWidth(); x++) {
                parent.setPixel(x, 0, colour);
            }
        }

        if (sideList.contains(Side.BOTTOM)) {
            for (int x = 0; x < parent.getWidth(); x++) {
                parent.setPixel(x, (parent.getHeight() - 1), colour);
            }
        }

        if (sideList.contains(Side.LEFT)) {
            for (int y = 0; y < parent.getHeight(); y++) {
                parent.setPixel(0, y, colour);
            }
        }

        if (sideList.contains(Side.RIGHT)) {
            for (int y = 0; y < parent.getHeight(); y++) {
                parent.setPixel((parent.getWidth() - 1), y, colour);
            }
        }
    }

    @Override
    protected void setActualPixel(int x, int y, PixelColour pixelColour) {
        pane.setPixel(x, y, pixelColour);
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        return pane.getPixel(x, y);
    }

    @Override
    public int getWidth() {
        return pane.getWidth();
    }

    @Override
    public int getHeight() {
        return pane.getHeight();
    }

    public enum Side {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }
}
