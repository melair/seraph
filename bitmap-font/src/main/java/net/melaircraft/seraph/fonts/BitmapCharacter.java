package net.melaircraft.seraph.fonts;

public class BitmapCharacter {
    private final boolean[][] bitmap;

    private final int bitmapWidth;
    private final int bitmapHeight;

    private final int bitmapOffsetX;
    private final int bitmapOffsetY;

    private final int characterWidth;
    private final int characterHeight;

    public BitmapCharacter(boolean[][] bitmap, int bitmapWidth, int bitmapHeight, int bitmapOffsetX, int bitmapOffsetY, int characterWidth, int characterHeight) {
        this.bitmap = bitmap;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.bitmapOffsetX = bitmapOffsetX;
        this.bitmapOffsetY = bitmapOffsetY;
        this.characterWidth = characterWidth;
        this.characterHeight = characterHeight;
    }

    public boolean[][] getBitmap() {
        return bitmap;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int getBitmapHeight() {
        return bitmapHeight;
    }

    public int getBitmapOffsetX() {
        return bitmapOffsetX;
    }

    public int getBitmapOffsetY() {
        return bitmapOffsetY;
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public int getCharacterHeight() {
        return characterHeight;
    }
}
