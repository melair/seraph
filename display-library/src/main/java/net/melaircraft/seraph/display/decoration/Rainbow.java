package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.Displayable;

public class Rainbow {
    private final Displayable parent;

    public Rainbow(Displayable parent) {
        this.parent = parent;

        double xInterval = (Math.PI * 2) / ((double) parent.getWidth());
        double yInterval = 4 / ((double) parent.getHeight());

        for (int x = 0; x < parent.getWidth(); x++) {
            for (int y = 0; y < parent.getHeight(); y++) {
                int red = (int) Math.round(Math.sin((xInterval * x) + 2 + (yInterval * y)) * 127 + 128);
                int green = (int) Math.round(Math.sin((xInterval * x) + 0 + (yInterval * y)) * 127 + 128);
                int blue = (int) Math.round(Math.sin((xInterval * x) + 4 + (yInterval * y)) * 127 + 128);

                parent.setPixel(x, y, red, green, blue);
            }
        }
    }
}
