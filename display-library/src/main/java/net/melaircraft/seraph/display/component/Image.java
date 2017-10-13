package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.CheckedSourceDisplay;
import net.melaircraft.seraph.display.PixelColour;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;

public class Image implements CheckedSourceDisplay {
    private final BufferedImage bufferedImage;

    public Image(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public PixelColour getActualPixel(int x, int y) {
        int colourInt = bufferedImage.getRGB(x, y);
        return new PixelColour(colourInt);
    }

    @Override
    public Collection<DisplayCallback> getCallbacks() {
        return Collections.emptySet();
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }
}
