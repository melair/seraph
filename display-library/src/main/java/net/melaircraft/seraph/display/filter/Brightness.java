package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

public class Brightness implements DestinationDisplay {
    private final DestinationDisplay parent;
    private final Buffer buffer;
    private float brightness;

    public Brightness(DestinationDisplay parent, float brightness) {
        this.parent = parent;
        this.buffer = new Buffer(parent.getWidth(), parent.getHeight());
        this.brightness = brightness;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        for (int x = 0; x < parent.getWidth(); x++) {
            for (int y = 0; y < parent.getHeight(); y++) {
                PixelColour colour = buffer.getPixel(x, y);
                setPixel(x, y, colour);
            }
        }
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
        buffer.setPixel(x, y, pixelColour);
        parent.setPixel(x, y, pixelColour.multiply(brightness));
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
    }
}
