package net.melaircraft.seraph.fonts;

public class BitmapCharacter {
    private final boolean[][] bitmap;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public BitmapCharacter(boolean[][] bitmap, int width, int height, int offsetX, int offsetY) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public boolean[][] getBitmap() {
        return bitmap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
