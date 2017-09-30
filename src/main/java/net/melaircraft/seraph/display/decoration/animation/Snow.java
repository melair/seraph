package net.melaircraft.seraph.display.decoration.animation;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class Snow {
    private static final PixelColour[] SNOW_COLOUR = { new PixelColour(255, 255, 255), new PixelColour(200, 200, 200), new PixelColour(225, 225, 225) };

    private final Displayable parent;
    private final int maxSnow;
    private final Random random = new Random();
    private final Collection<Flake> flakes = new HashSet<>();

    public Snow(Displayable parent, int maxSnow) {
        this.parent = parent;
        this.maxSnow = maxSnow;
    }

    public void tick() {
        flakes.removeIf(Flake::tick);

        if (flakes.size() < maxSnow && random.nextInt(5) < 4) {
            flakes.add(new Flake(random.nextInt(parent.getWidth())));
        }
    }

    class Flake {
        private final int x;
        private int y = 0;

        Flake(int x) {
            this.x = x;

            parent.setPixel(x, y, getColour());
        }

        boolean tick() {
            parent.setPixel(x, y, PixelColour.BLACK);

            y++;

            if (y < parent.getHeight()) {
                parent.setPixel(x, y, getColour());
            } else {
                return true;
            }

            return false;
        }

        private PixelColour getColour() {
            return SNOW_COLOUR[random.nextInt(SNOW_COLOUR.length)];
        }
    }
}
