package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CanvasTest {
    private final Buffer buffer = new Buffer(6, 5);
    private final Buffer filledH = new Buffer(3, 1);
    private final Buffer filledV = new Buffer(1, 3);

    public CanvasTest() {
        filledH.setPixel(0, 0, PixelColour.RED);
        filledH.setPixel(1, 0, PixelColour.GREEN);
        filledH.setPixel(2, 0, PixelColour.BLUE);

        filledV.setPixel(0, 0, PixelColour.RED);
        filledV.setPixel(0, 1, PixelColour.GREEN);
        filledV.setPixel(0, 2, PixelColour.BLUE);
    }

    @Test
    public void testNoScrollLeftJustificationTopItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.LEFT, Canvas.Alignment.TOP, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 0));
        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));
    }

    @Test
    public void testNoScrollCentreJustificationTopItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.CENTER, Canvas.Alignment.TOP, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(1, 0));
        assertEquals(PixelColour.RED, buffer.getActualPixel(4, 0));
    }

    @Test
    public void testNoScrollRightJustificationTopItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.RIGHT, Canvas.Alignment.TOP, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(2, 0));
        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 0));
    }

    @Test
    public void testNoScrollLeftJustificationMiddleItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.LEFT, Canvas.Alignment.MIDDLE, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 1));
        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));
    }

    @Test
    public void testNoScrollCentreJustificationMiddleItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.CENTER, Canvas.Alignment.MIDDLE, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(1, 1));
        assertEquals(PixelColour.RED, buffer.getActualPixel(4, 0));
    }

    @Test
    public void testNoScrollRightJustificationMiddleItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.RIGHT, Canvas.Alignment.MIDDLE, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(2, 1));
        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 0));
    }

    @Test
    public void testNoScrollLeftJustificationBottomItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.LEFT, Canvas.Alignment.BOTTOM, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 2));
        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));
    }

    @Test
    public void testNoScrollCentreJustificationBottomItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.CENTER, Canvas.Alignment.BOTTOM, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(1, 2));
        assertEquals(PixelColour.RED, buffer.getActualPixel(4, 0));
    }

    @Test
    public void testNoScrollRightJustificationBottomItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.RIGHT, Canvas.Alignment.BOTTOM, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(2, 2));
        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 0));
    }

    @Test
    public void testNoScrollLeftJustificationTopItemAlignmentMiddleGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.RIGHT, Canvas.Alignment.TOP, Canvas.Alignment.MIDDLE);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(2, 1));
        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 1));
    }

    @Test
    public void testNoScrollLeftJustificationTopItemAlignmentBottomGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Justification.RIGHT, Canvas.Alignment.TOP, Canvas.Alignment.BOTTOM);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(2, 2));
        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 2));
    }


    @Test
    public void testLeftScrollTopItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Direction.LEFT, Canvas.Alignment.TOP, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 0));
        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));

        canvas.run();
        canvas.run();
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 0));

        canvas.run();

        assertEquals(PixelColour.BLACK, buffer.getActualPixel(0, 0));

        canvas.run();
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(5, 0));
    }

    @Test
    public void testRightScrollTopItemAlignmentTopGroupAlignment() {
        Canvas canvas = new Canvas(buffer, Canvas.Direction.RIGHT, Canvas.Alignment.TOP, Canvas.Alignment.TOP);
        canvas.append(filledH);
        canvas.append(filledV);
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 0));
        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));

        canvas.run();
        canvas.run();
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(3, 0));

        canvas.run();
        canvas.run();
        canvas.run();

        assertEquals(PixelColour.BLACK, buffer.getActualPixel(5, 0));

        canvas.run();
        canvas.run();
        canvas.run();
        canvas.run();

        assertEquals(PixelColour.RED, buffer.getActualPixel(0, 0));
    }
}