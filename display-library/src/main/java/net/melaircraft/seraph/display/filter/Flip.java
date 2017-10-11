package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Flip implements DestinationDisplay {
    private final FullDisplay parent;
    private final Set<Direction> flipDirections;

    public Flip(FullDisplay parent, Direction... directions) {
        this.parent = parent;

        this.flipDirections = new HashSet<>();
        Collections.addAll(flipDirections, directions);
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
        int translatedX = flipDirections.contains(Direction.HORIZONTAL) ? ((getWidth() - 1) - x) : x;
        int translatedY = flipDirections.contains(Direction.VERTICAL) ? ((getHeight() - 1) - y) : y;

        parent.setPixel(translatedX, translatedY, pixelColour);
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }
}
