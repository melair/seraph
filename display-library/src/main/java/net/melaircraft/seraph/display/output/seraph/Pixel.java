package net.melaircraft.seraph.display.output.seraph;

import net.melaircraft.seraph.display.PixelColour;

class Pixel implements Comparable<Pixel> {
    private final int x;
    private final int y;
    private final PixelColour pixelColour;

    Pixel(int x, int y, PixelColour pixelColour) {
        this.x = x;
        this.y = y;
        this.pixelColour = pixelColour;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PixelColour getPixelColour() {
        return pixelColour;
    }

    @Override
    public int compareTo(Pixel o) {
        int diffY = getY() - o.getY();

        if (diffY == 0) {
            return getX() - o.getX();
        } else {
            return diffY;
        }
    }
}
