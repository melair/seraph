package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.PixelColour;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ImageTest {
    @Test
    public void testImage() throws IOException {
        PixelColour expectedColour = new PixelColour(0xff, 0xee, 0xdd);

        BufferedImage bufferedImage = ImageIO.read(TextTest.class.getResourceAsStream("/testpixel.png"));

        Image image = new Image(bufferedImage);

        assertEquals(1, image.getWidth());
        assertEquals(1, image.getHeight());
        assertEquals(expectedColour, image.getPixel(0, 0));
    }
}