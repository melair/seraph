package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.MismatchedDisplaySizeException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Tee implements DestinationDisplay {
    private final Collection<DestinationDisplay> allDestinations = new HashSet<>();
    private final DestinationDisplay first;

    public Tee(DestinationDisplay... destinations) {
        for (int i = 1; i < destinations.length; i++) {
            if (destinations[0].getWidth() != destinations[i].getWidth() || destinations[0].getHeight() != destinations[i].getHeight()) {
                throw new MismatchedDisplaySizeException("All parents must have the same display dimensions!");
            }
        }

        Collections.addAll(allDestinations, destinations);
        first = destinations[0];
    }

    @Override
    public int getWidth() {
        return first.getWidth();
    }

    @Override
    public int getHeight() {
        return first.getHeight();
    }

    @Override
    public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException, InvalidPixelColourException {
        allDestinations.forEach((d) -> d.setPixel(x, y, pixelColour));
    }
}
