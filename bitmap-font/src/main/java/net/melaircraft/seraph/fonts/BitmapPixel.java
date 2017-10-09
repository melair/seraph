package net.melaircraft.seraph.fonts;

public class BitmapPixel {
    private final int x;
    private final int y;
    private final boolean set;

    public BitmapPixel(int x, int y, boolean set) {
        this.x = x;
        this.y = y;
        this.set = set;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSet() {
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapPixel that = (BitmapPixel) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
