package net.melaircraft.seraph.display.filter;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.MismatchedDisplaySizeException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Tee implements Displayable {
    private final Collection<Displayable> allParents = new HashSet<>();
    private final Displayable first;

    public Tee(Displayable... parents) {
        for (int i = 1; i < parents.length; i++) {
            if (parents[0].getWidth() != parents[i].getWidth() || parents[0].getHeight() != parents[i].getHeight()) {
                System.out.println(parents[0].getWidth() + " != " + parents[i].getWidth());
                System.out.println(parents[0].getHeight() + " != " + parents[i].getHeight());
                throw new MismatchedDisplaySizeException("All parents must have the same display dimensions!");
            }
        }

        Collections.addAll(allParents, parents);
        first = parents[0];
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
    public void setPixel(int x, int y, int r, int g, int b) {
        allParents.forEach((d) -> d.setPixel(x, y, r, g, b));
    }

    @Override
    public PixelColour getPixel(int x, int y) {
        return first.getPixel(x, y);
    }
}
