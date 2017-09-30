package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

public class Brightness extends Buffer {
    private final Displayable parent;
    private float brightness;

    public Brightness(Displayable parent, float brightness) {
        super(parent);
        this.parent = parent;
        this.brightness = brightness;
    }

    @Override
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        super.setActualPixel(x, y, r, g, b);
        setParentPixelScaled(x, y, r, g, b);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        for (int x = 0; x < parent.getWidth(); x++) {
            for (int y = 0; y < parent.getHeight(); y++) {
                PixelColour colour = getActualPixel(x, y);
                setParentPixelScaled(x, y, colour.getRed(), colour.getGreen(), colour.getBlue());
            }
        }
    }

    private void setParentPixelScaled(int x, int y, int r, int g, int b) {
        int rX = Math.min(Math.round(r * brightness), 255);
        int gX = Math.min(Math.round(g * brightness), 255);
        int bX = Math.min(Math.round(b * brightness), 255);

        parent.setPixel(x, y, rX, gX, bX);
    }
}
