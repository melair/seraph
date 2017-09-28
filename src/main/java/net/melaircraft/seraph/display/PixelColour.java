package net.melaircraft.seraph.display;

public final class PixelColour {
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
}
