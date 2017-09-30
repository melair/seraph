package net.melaircraft.seraph.display;

public final class PixelColour {
    public static final PixelColour BLACK = new PixelColour(0, 0, 0);
    public static final PixelColour RED = new PixelColour(255, 0, 0);
    public static final PixelColour GREEN = new PixelColour(0, 255, 0);
    public static final PixelColour BLUE = new PixelColour(0, 0, 255);
    public static final PixelColour WHITE = new PixelColour(255, 255, 255);

    private final int r;
    private final int g;
    private final int b;

    public PixelColour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PixelColour that = (PixelColour) o;

        if (r != that.r) return false;
        if (g != that.g) return false;
        return b == that.b;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }

    @Override
    public String toString() {
        return "PixelColour{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
