package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;

public class Mask extends Buffer {
    private final Displayable mask;

    public Mask(Displayable parent) {
        super(parent);

        mask = new MaskLayer(new Null(parent.getWidth(), parent.getHeight()));
    }

    public Displayable getMask() {
        return mask;
    }

    private void update(int x, int y) {
        PixelColour colour = getActualPixel(x, y);
        setActualPixel(x, y, colour.getRed(), colour.getGreen(), colour.getBlue());
    }
    
    @Override
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        double intensity = ((0.2125 * r) + (0.7154 * g) + (0.0721 * b)) / 255;
        PixelColour maskPixel = mask.getPixel(x, y);

        r = (int) Math.round(intensity * maskPixel.getRed());
        g = (int) Math.round(intensity * maskPixel.getGreen());
        b = (int) Math.round(intensity * maskPixel.getBlue());

        super.setActualPixel(x, y, r, g, b);
    }

    class MaskLayer extends Buffer {
        public MaskLayer(Displayable parent) {
            super(parent);
        }

        @Override
        protected void setActualPixel(int x, int y, int r, int g, int b) {
            super.setActualPixel(x, y, r, g, b);
            update(x, y);
        }
    }
}
