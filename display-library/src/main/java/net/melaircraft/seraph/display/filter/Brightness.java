package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

public class Brightness extends Buffer {
    private final DestinationDisplay parent;
    private float brightness;

    public Brightness(DestinationDisplay parent, float brightness) {
        super(parent);
        this.parent = parent;
        this.brightness = brightness;
    }

    @Override
    protected void setActualPixel(int x, int y, PixelColour pixelColour) {
        super.setActualPixel(x, y, pixelColour);
        setParentPixelScaled(x, y, pixelColour);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        for (int x = 0; x < parent.getWidth(); x++) {
            for (int y = 0; y < parent.getHeight(); y++) {
                PixelColour colour = getActualPixel(x, y);
                setParentPixelScaled(x, y, colour);
            }
        }
    }

    private void setParentPixelScaled(int x, int y, PixelColour pixelColour) {
        parent.setPixel(x, y, pixelColour.multiply(brightness));
    }
}
