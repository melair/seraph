package net.melaircraft.seraph.display.decoration.animation;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class Matrix implements Runnable {
    private final Displayable parent;
    private final Collection<Droplet> droplets = new HashSet<>();
    private final Random random = new Random();

    public Matrix(Displayable parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        droplets.removeIf(Droplet::tick);

        if (random.nextBoolean()) {
            int x = random.nextInt(parent.getWidth());

            if (droplets.stream().allMatch((d) -> d.isClear(x))) {
                droplets.add(new Droplet(x));
            }
        }
    }

    class Droplet {
        private final int x;
        private int y = 0;
        private final int length;

        Droplet(int x) {
            this.x = x;
            this.length = (int) Math.round((parent.getHeight() * (1.0 - (0.2 * random.nextFloat()))));

            draw();
        }

        private void draw() {
            for (int yI = y; yI >= 0; yI--) {
                if (yI < parent.getHeight()) {
                    int pos = y - yI;
                    int remaining = length - pos;

                    PixelColour colour;

                    if (pos == 0) {
                        colour = new PixelColour(255, 255, 255);
                    } else if (pos == 1) {
                        colour = new PixelColour(200, 255, 200);
                    } else if (pos == 2) {
                        colour = new PixelColour(150, 255, 150);
                    } else if (pos == 3) {
                        colour = new PixelColour(50, 255, 50);
                    } else if (remaining > 10) {
                        int green = 200 + (int) Math.round(random.nextFloat() * 55.0);
                        colour = new PixelColour(0, green, 0);
                    } else if (remaining <= 1) {
                        colour = new PixelColour(0, 0, 0);
                    } else {
                        int green = (int) Math.round((remaining / 10.0) * 255);
                        colour = new PixelColour(0, green, 0);
                    }

                    parent.setPixel(x, yI, colour);
                }
            }
        }

        boolean isClear(int x) {
            return this.x != x || (y - length) >= 10;
        }

        boolean tick() {
            y++;

            if (y - length >= parent.getHeight()) {
                return true;
            } else {
                draw();
                return false;
            }
        }
    }
}
