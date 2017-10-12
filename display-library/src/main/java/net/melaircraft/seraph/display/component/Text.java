package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.CheckedSourceDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.SourceDisplay;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.fonts.BitmapFont;
import net.melaircraft.seraph.fonts.FontOutput;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Text implements CheckedSourceDisplay {
    private final PixelColour colour;
    private final FontOutput fontOutput;

    public Text(BitmapFont bitmapFont, PixelColour colour, String content) {
        this.colour = colour;
        this.fontOutput = bitmapFont.render(content);
    }

    @Override
    public PixelColour getActualPixel(int x, int y) {
        return fontOutput.getBitmap()[x][y] ? colour : PixelColour.BLACK;
    }

    @Override
    public Collection<DisplayCallback> getCallbacks() {
        return Collections.emptySet();
    }

    @Override
    public int getWidth() {
        return fontOutput.getWidth();
    }

    @Override
    public int getHeight() {
        return fontOutput.getHeight();
    }
}
