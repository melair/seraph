package net.melaircraft.seraph.fonts;

public class FontOutput {
    private final int width;
    private final int height;
    private final boolean[][] bitmap;

    public FontOutput(int width, int height) {
        this.width = width;
        this.height = height;
        this.bitmap = new boolean[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    void set(int x, int y) {
        bitmap[x][y] = true;
    }

    public boolean[][] getBitmap() {
        return bitmap;
    }
}
