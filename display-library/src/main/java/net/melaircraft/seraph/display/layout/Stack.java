package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.SourceDisplay;
import net.melaircraft.seraph.display.buffer.Buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Stack implements SourceDisplay.DisplayCallback {
    private final DestinationDisplay destination;
    private final List<Layer> layers = new ArrayList<>();

    public Stack(DestinationDisplay destination) {
        this.destination = destination;
    }

    public DestinationDisplay addLayer() {
        Layer layer = new Layer(this);
        layers.add(layer);
        return layer;
    }

    @Override
    public void notification(SourceDisplay display, int x, int y, PixelColour colour) {
        PixelColour resolvedColour = layers.stream()
                .map((d) -> d.getPixel(x, y))
                .filter((c) -> !c.equals(PixelColour.BLACK))
                .findFirst().orElse(PixelColour.BLACK);

        destination.setPixel(x, y, resolvedColour);
    }

    static class Layer implements FullDisplay {
        private final Buffer buffer;
        private final int width;
        private final int height;
        private Collection<DisplayCallback> callbacks = new HashSet<>();

        Layer(Stack stack) {
            this.width = stack.destination.getWidth();
            this.height = stack.destination.getHeight();
            buffer = new Buffer(width, height);
            buffer.registerCallback(stack);
        }

        @Override
        public void setPixel(int x, int y, PixelColour pixelColour) {
            buffer.setPixel(x, y, pixelColour);
        }

        @Override
        public PixelColour getPixel(int x, int y) {
            return buffer.getPixel(x, y);
        }

        @Override
        public Collection<DisplayCallback> getCallbacks() {
            return callbacks;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }
    }
}
