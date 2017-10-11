package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.DeltaBuffer;
import net.melaircraft.seraph.fonts.BitmapFont;
import net.melaircraft.seraph.fonts.FontOutput;

public class Text {
    private final DestinationDisplay destination;
    private final BitmapFont bitmapFont;
    private final PixelColour colour;
    private final Justification justification;
    private final Alignment alignment;
    private final DeltaBuffer deltaBuffer;

    public Text(DestinationDisplay destination, BitmapFont bitmapFont, PixelColour colour, Justification justification, Alignment alignment, String initialContent) {
        this.destination = destination;
        this.bitmapFont = bitmapFont;
        this.colour = colour;
        this.justification = justification;
        this.alignment = alignment;
        this.deltaBuffer = new DeltaBuffer(destination);

        setText(initialContent);
    }

    public void setText(String content) {
        FontOutput fontOutput = bitmapFont.render(content);

        deltaBuffer.clearCurrent();

        int dX = 0;
        int dY = 0;

        switch (justification) {
            case CENTER:
                dX = (destination.getWidth() - fontOutput.getWidth()) / 2;
                break;
            case RIGHT:
                dX = destination.getWidth() - fontOutput.getWidth();
                break;
        }

        switch (alignment) {
            case MIDDLE:
                dY = (destination.getHeight() - fontOutput.getHeight()) / 2;
                break;
            case BOTTOM:
                dY = destination.getHeight() - fontOutput.getHeight();
                break;
        }

        boolean[][] bitmap = fontOutput.getBitmap();

        for (int x = 0; x < fontOutput.getWidth(); x++) {
            for (int y = 0; y < fontOutput.getHeight(); y++) {
                if (bitmap[x][y]) {
                    deltaBuffer.setPixel(x + dX, y + dY, colour);
                }
            }
        }

        deltaBuffer.sendDelta();
    }

    public enum Justification {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum Alignment {
        TOP,
        MIDDLE,
        BOTTOM
    }
}
