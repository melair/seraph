package net.melaircraft.seraph.display.decoration;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;

public class Rainbow {
    public Rainbow(DestinationDisplay destination) {
        double xInterval = (Math.PI * 2) / ((double) destination.getWidth());
        double yInterval = 4 / ((double) destination.getHeight());

        for (int x = 0; x < destination.getWidth(); x++) {
            for (int y = 0; y < destination.getHeight(); y++) {
                int red = (int) Math.round(Math.sin((xInterval * x) + 2 + (yInterval * y)) * 127 + 128);
                int green = (int) Math.round(Math.sin((xInterval * x) + 0 + (yInterval * y)) * 127 + 128);
                int blue = (int) Math.round(Math.sin((xInterval * x) + 4 + (yInterval * y)) * 127 + 128);

                destination.setPixel(x, y, new PixelColour(red, green, blue));
            }
        }
    }
}
