package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.DestinationDisplay;
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

    private final List<SourceDisplay> displays = new ArrayList<>();

    public Ticker(DestinationDisplay destinationDisplay, Direction scrollDirection, Alignment itemAlignment, Alignment groupAlignment) {
        this.scrollDirection = scrollDirection;
        this.justification = Justification.LEFT;
        this.itemAlignment = itemAlignment;
        this.groupAlignment = groupAlignment;
        this.deltaBuffer = new DeltaBuffer(destinationDisplay);
    }

    public Ticker(DestinationDisplay destinationDisplay, Justification justification, Alignment itemAlignment, Alignment groupAlignment) {
        this.scrollDirection = Direction.NONE;
        this.justification = justification;
        this.itemAlignment = itemAlignment;
        this.groupAlignment = groupAlignment;
        this.deltaBuffer = new DeltaBuffer(destinationDisplay);
    }

    public void append(SourceDisplay source) {
        displays.add(source);
        redraw();
    }

    public void insert(int position, SourceDisplay source) {
        displays.add(position, source);
        redraw();
    }

    public void replace(int position, SourceDisplay source) {
        displays.remove(position);
        displays.add(position, source);
        redraw();
    }

    public void remove(SourceDisplay source) {
        displays.remove(source);
        redraw();
    }

    private void redraw() {

    }

    @Override
    public void notification(SourceDisplay display, int x, int y, PixelColour colour) {

    }

    @Override
    public void run() {
        redraw();
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
