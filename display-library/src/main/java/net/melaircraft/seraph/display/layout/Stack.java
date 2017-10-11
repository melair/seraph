package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    private final Displayable parent;
    private final List<Layer> layers = new ArrayList<>();

    public Stack(Displayable parent) {
        this.parent = parent;
    }

    protected void resolvePixel(int x, int y) {
        PixelColour colour = layers.stream()
                .map((d) -> d.getPixel(x, y))
                .filter((c) -> !c.equals(PixelColour.BLACK))
                .findFirst().orElse(PixelColour.BLACK);

        parent.setPixel(x, y, colour);
    }

    public Displayable addLayer() {
        Layer layer = new Layer(this);
        layers.add(layer);
        return layer;
    }

    static class Layer extends Buffer {
        private final Stack stack;

        public Layer(Stack stack) {
            super(new Null(stack.parent.getWidth(), stack.parent.getHeight()));
            this.stack = stack;
        }

        @Override
        protected void setActualPixel(int x, int y, PixelColour pixelColour) {
            super.setActualPixel(x, y, pixelColour);
            stack.resolvePixel(x, y);
        }
    }
}
