package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.HasSize;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.SourceDisplay;
import net.melaircraft.seraph.display.buffer.DeltaBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Canvas implements SourceDisplay.DisplayCallback, Runnable {
    private final Direction scrollDirection;
    private final Justification justification;
    private final Alignment itemAlignment;
    private final Alignment groupAlignment;
    private final DeltaBuffer deltaBuffer;

    private final int parentHeight;
    private final int parentWidth;

    private int currentWidth = 0;
    private int currentHeight = 0;

    private int currentPos = 0;

    private final List<SourceDisplay> displays = new ArrayList<>();

    public Canvas(DestinationDisplay destinationDisplay, Direction scrollDirection, Alignment itemAlignment, Alignment groupAlignment) {
        this.parentWidth = destinationDisplay.getWidth();
        this.parentHeight = destinationDisplay.getHeight();

        this.scrollDirection = scrollDirection;
        this.justification = Justification.LEFT;
        this.itemAlignment = itemAlignment;
        this.groupAlignment = groupAlignment;
        this.deltaBuffer = new DeltaBuffer(destinationDisplay);

        recalculateSize();
    }

    public Canvas(DestinationDisplay destinationDisplay, Justification justification, Alignment itemAlignment, Alignment groupAlignment) {
        this.parentWidth = destinationDisplay.getWidth();
        this.parentHeight = destinationDisplay.getHeight();

        this.scrollDirection = Direction.NONE;
        this.justification = justification;
        this.itemAlignment = itemAlignment;
        this.groupAlignment = groupAlignment;
        this.deltaBuffer = new DeltaBuffer(destinationDisplay);

        recalculateSize();
    }

    public void append(SourceDisplay source) {
        displays.add(source);
        recalculateSize();
    }

    public void insert(int position, SourceDisplay source) {
        displays.add(position, source);
        recalculateSize();
    }

    public void replace(int position, SourceDisplay source) {
        displays.remove(position);
        displays.add(position, source);
        recalculateSize();
    }

    public void remove(SourceDisplay source) {
        displays.remove(source);
        recalculateSize();
    }

    private void recalculateSize() {
        currentHeight = displays.stream().mapToInt(HasSize::getHeight).max().orElse(0);
        currentWidth = displays.stream().mapToInt(HasSize::getWidth).sum();
    }

    @Override
    public void notification(SourceDisplay display, int x, int y, PixelColour colour) {
        redraw();
    }

    @Override
    public void run() {
        redraw();

        switch (scrollDirection) {
            case LEFT:
                currentPos++;

                if (currentPos > currentWidth) {
                    currentPos = 0 - parentWidth;
                }
                break;

            case RIGHT:
                currentPos--;

                if (currentPos < -parentWidth) {
                    currentPos = parentWidth;
                }
                break;
        }
    }

    private void redraw() {
        int xGroupStart = 0;

        switch (justification) {
            case CENTER:
                xGroupStart = (parentWidth - currentWidth) / 2;
                break;
            case RIGHT:
                xGroupStart = parentWidth - currentWidth;
        }

        int yGroupOffset = 0;

        switch (groupAlignment) {
            case MIDDLE:
                yGroupOffset = (parentHeight - currentHeight) / 2;
                break;

            case BOTTOM:
                yGroupOffset = parentHeight - currentHeight;
                break;
        }

        deltaBuffer.clearCurrent();

        int displayX = xGroupStart - currentPos;

        Iterator<SourceDisplay> iterator = displays.iterator();
        SourceDisplay source = iterator.hasNext() ? iterator.next() : null;
        int sourceX = 0;

        while (displayX < parentWidth) {
            if (source != null && sourceX >= source.getWidth()) {
                source = iterator.hasNext() ? iterator.next() : null;
                sourceX = 0;
            }

            if (displayX >= 0) {
                for (int y = 0; y < parentHeight; y++) {
                    deltaBuffer.setPixel(displayX, y, PixelColour.BLACK);
                }

                if (source != null) {
                    int ySourceOffset = 0;

                    switch (itemAlignment) {
                        case MIDDLE:
                            ySourceOffset = (currentHeight - source.getHeight()) / 2;
                            break;
                        case BOTTOM:
                            ySourceOffset = currentHeight - source.getHeight();
                            break;
                    }

                    for (int sourceY = 0; sourceY < source.getHeight(); sourceY++) {
                        deltaBuffer.setPixel(displayX, sourceY + yGroupOffset + ySourceOffset, source.getPixel(sourceX, sourceY));
                    }
                }
            }

            displayX++;
            sourceX++;
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

    public enum Direction {
        LEFT,
        RIGHT,
        NONE
    }
}
