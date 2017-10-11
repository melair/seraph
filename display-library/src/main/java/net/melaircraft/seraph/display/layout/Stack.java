package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

import java.util.ArrayList;
import java.util.List;

public class Stack implements Buffer.BufferCallback {
    private final DestinationDisplay parent;
    private final List<Layer> layers = new ArrayList<>();

    public Stack(DestinationDisplay parent) {
        this.parent = parent;
    }

    public DestinationDisplay addLayer() {
        Layer layer = new Layer(this);
        layers.add(layer);
        return layer;
    }

    @Override
    public void updated(int x, int y, PixelColour colour) {
        PixelColour resolvedColour = layers.stream()
                .map((d) -> d.getPixel(x, y))
                .filter((c) -> !c.equals(PixelColour.BLACK))
                .findFirst().orElse(PixelColour.BLACK);

        parent.setPixel(x, y, resolvedColour);
    }

    static class Layer implements FullDisplay {
        private final Buffer buffer;
        private final int width;
        private final int height;

        public Layer(Stack stack) {
            this.width = stack.parent.getWidth();
            this.height = stack.parent.getHeight();
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
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }
    }
}
