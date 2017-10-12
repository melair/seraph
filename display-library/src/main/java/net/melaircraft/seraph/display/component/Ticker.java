package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.HasSize;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.SourceDisplay;
import net.melaircraft.seraph.display.buffer.DeltaBuffer;

import java.util.ArrayList;
import java.util.List;

public class Ticker implements SourceDisplay.DisplayCallback, Runnable {
    private final Direction scrollDirection;
    private final Justification justification;
    private final Alignment itemAlignment;
    private final Alignment groupAlignment;
    private final DeltaBuffer deltaBuffer;

    private final int parentWidth;

    private int currentWidth = 0;
    private int currentHeight = 0;

    private int currentPos = 0;

    private final List<SourceDisplay> displays = new ArrayList<>();

    public Ticker(DestinationDisplay destinationDisplay, Direction scrollDirection, Alignment itemAlignment, Alignment groupAlignment) {
        this.parentWidth = destinationDisplay.getWidth();

        this.scrollDirection = scrollDirection;
        this.justification = Justification.LEFT;
        this.itemAlignment = itemAlignment;
        this.groupAlignment = groupAlignment;
        this.deltaBuffer = new DeltaBuffer(destinationDisplay);

        recalculateSize();
    }

    public Ticker(DestinationDisplay destinationDisplay, Justification justification, Alignment itemAlignment, Alignment groupAlignment) {
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
        switch (scrollDirection) {
            case LEFT:
                currentPos++;

                if (currentPos > currentWidth + parentWidth) {
                    currentPos = 0 - parentWidth;
                }
                break;

            case RIGHT:
                currentPos--;

                if (currentPos < -parentWidth) {
                    currentPos = currentWidth + parentWidth;
                }
                break;
        }

        if (scrollDirection == Direction.NONE) {
            return;
        }

        redraw();
    }

    private void redraw() {
        deltaBuffer.clearCurrent();

        // Calculate New Window

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
