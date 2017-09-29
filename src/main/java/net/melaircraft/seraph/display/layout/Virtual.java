package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.Buffer;
import net.melaircraft.seraph.display.output.Null;

public class Virtual implements Displayable {
    private final Displayable parent;
    private final Displayable canvas;

    private int viewportX = 0;
    private int viewportY = 0;

    public Virtual(Displayable parent, int width, int height) {
        this.parent = parent;
        this.canvas = new Buffer(new Null(width, height));
    }

    public void moveViewport(int x, int y) {
        viewportX = x;
        viewportY = y;

        for (int xI = 0; xI < parent.getWidth(); xI++) {
            for (int yI = 0; yI < parent.getHeight(); yI++) {
                int canvasX = viewportX + xI;
                int canvasY = viewportY + yI;

                PixelColour colour = PixelColour.BLACK;

                if (canvasX < canvas.getWidth() && canvasY < canvas.getHeight()) {
                    colour = canvas.getPixel(canvasX, canvasY);
                }

                parent.setPixel(xI, yI, colour);
            }
        }
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }

    @Override
    public void setPixel(int x, int y, int r, int g, int b) {
        canvas.setPixel(x, y, r, g, b);

        int adjustX = x - viewportX;
        int adjustY = y - viewportY;

        if (adjustX >= 0 && adjustX < parent.getWidth() && adjustY >= 0 && adjustY < parent.getHeight()) {
            parent.setPixel(x, y, r, g, b);
        }
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        return canvas.getPixel(x, y);
    }
}
