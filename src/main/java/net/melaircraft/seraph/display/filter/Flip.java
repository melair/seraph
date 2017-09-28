package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Flip implements Displayable {
    private final Displayable parent;
    private final Set<Direction> flipDirections;

    public Flip(Displayable parent, Direction... directions) {
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
        return parent.getWidth();
    }

    @Override
    public void setPixel(int x, int y, int r, int g, int b) {
        int translatedX = flipDirections.contains(Direction.HORIZONTAL) ? ((getWidth() - 1) - x) : x;
        int translatedY = flipDirections.contains(Direction.VERTICAL) ? ((getHeight() - 1) - y) : y;

        parent.setPixel(translatedX, translatedY, r, g, b);
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        int translatedX = flipDirections.contains(Direction.HORIZONTAL) ? ((getWidth() - 1) - x) : x;
        int translatedY = flipDirections.contains(Direction.VERTICAL) ? ((getHeight() - 1) - y) : y;

        return parent.getPixel(translatedX, translatedY);
    }

    enum Direction {
        HORIZONTAL,
        VERTICAL
    }
}
