package net.melaircraft.seraph.display;

import net.melaircraft.seraph.display.exception.InvalidPixelColourException;

public final class PixelColour {
    public static final PixelColour BLACK = new PixelColour(0, 0, 0);
    public static final PixelColour RED = new PixelColour(255, 0, 0);
    public static final PixelColour GREEN = new PixelColour(0, 255, 0);
    public static final PixelColour BLUE = new PixelColour(0, 0, 255);
    public static final PixelColour WHITE = new PixelColour(255, 255, 255);

    private final int r;
    private final int g;
    private final int b;

    public PixelColour(int r, int g, int b) throws InvalidPixelColourException {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new InvalidPixelColourException("Invalid colour specified!");
        }

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

    public PixelColour multiply(double factor) {
        int rN = (int) Math.round(r * factor);
        int gN = (int) Math.round(g * factor);
        int bN = (int) Math.round(b * factor);

        int rL = Math.max(0, Math.min(255, rN));
        int gL = Math.max(0, Math.min(255, gN));
        int bL = Math.max(0, Math.min(255, bN));

        return new PixelColour(rL, gL, bL);
    }

    public PixelColour greyscale() {
        int grey = (int) Math.round((0.2125 * r) + (0.7154 * g) + (0.0721 * b));
        return new PixelColour(grey, grey, grey);
    }

    public PixelColour multiply(PixelColour pixelColour) {
        double rD = r / 255.0;
        double gD = g / 255.0;
        double bD = b / 255.0;

        int rN = (int) Math.round(pixelColour.getRed() * rD);
        int gN = (int) Math.round(pixelColour.getGreen() * gD);
        int bN = (int) Math.round(pixelColour.getBlue() * bD);

        int rL = Math.max(0, Math.min(255, rN));
        int gL = Math.max(0, Math.min(255, gN));
        int bL = Math.max(0, Math.min(255, bN));

        return new PixelColour(rL, gL, bL);
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
