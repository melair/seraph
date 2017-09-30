package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.CheckedDisplayable;
import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;

import java.util.ArrayList;
import java.util.List;

public class Stack extends CheckedDisplayable {
    private final Displayable parent;
    private final List<Layer> layers = new ArrayList<>();

    public Stack(Displayable parent) {
        this.parent = parent;
    }

    @Override
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        throw new NonExistentPixelException("No direct access to pixels via a Stack");
    }

    @Override
    protected PixelColour getActualPixel(int x, int y) {
        throw new NonExistentPixelException("No direct access to pixels via a Stack");
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
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
            super(new Null(stack.getWidth(), stack.getHeight()));
            this.stack = stack;
        }

        @Override
        protected void setActualPixel(int x, int y, int r, int g, int b) {
            super.setActualPixel(x, y, r, g, b);
            stack.resolvePixel(x, y);
        }
    }
}
