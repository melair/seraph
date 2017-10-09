package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.DeltaBuffer;
import net.melaircraft.seraph.fonts.BitmapCharacter;
import net.melaircraft.seraph.fonts.BitmapFont;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Text {
    private final Displayable parent;
    private final BitmapFont bitmapFont;
    private final PixelColour colour;
    private final Justification justification;
    private final Alignment alignment;
    private final DeltaBuffer deltaBuffer;

    public Text(Displayable parent, BitmapFont bitmapFont, PixelColour colour, Justification justification, Alignment alignment, String initialContent) {
        this.parent = parent;
        this.bitmapFont = bitmapFont;
        this.colour = colour;
        this.justification = justification;
        this.alignment = alignment;
        this.deltaBuffer = new DeltaBuffer(parent);

        setText(initialContent);
    }

    public void setText(String content) {
        List<BitmapCharacter> characters = content.chars()
                .mapToObj(i -> (char)i)
                .map(bitmapFont::getCharacter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        deltaBuffer.clearCurrent();

        if (characters.size() > 0) {
            int lowestX = 0;
            int highestX = 0;

            int lowestY = Integer.MAX_VALUE;
            int highestY = 0;

            int positionX = 0;

            for (BitmapCharacter character : characters) {
                int xDiff = character.getBitmapWidth() + character.getBitmapOffsetX();

                lowestX = Math.min(lowestX, character.getBitmapOffsetX() + positionX);
                highestX = Math.max(highestX, xDiff + positionX);
                positionX += xDiff;

                int yOffset = character.getBitmapOffsetY() * -1;

                lowestY = Math.min(lowestY, yOffset);
                highestY = Math.max(highestY, character.getBitmapHeight());
            }

            int dX = 0;
            int dY = 0;

            switch (justification) {
                case CENTER:
                    dX = (parent.getWidth() - (highestX - lowestX)) / 2;
                    break;
                case RIGHT:
                    dX = parent.getWidth() - highestX;
                    break;
            }

            switch (alignment) {
                case MIDDLE:
                    dY = (parent.getHeight() - (highestY - lowestY)) / 2;
                    break;
                case BOTTOM:
                    dY = parent.getHeight() - highestY;
                    break;
            }

            for (BitmapCharacter character : characters) {
                for (int x = 0; x < character.getBitmapWidth(); x++) {
                    for (int y = 0; y < character.getBitmapHeight(); y++) {
                        if (dX + x >= 0 && dX + x < parent.getWidth() && dY + y >= 0 && dY + y < parent.getHeight()) {
                            if (character.getBitmap()[x][y]) {
                                deltaBuffer.setPixel(dX + x, dY + y, colour);
                            }
                        }
                    }
                }

                dX += character.getBitmapWidth();
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
