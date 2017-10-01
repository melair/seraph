package net.melaircraft.seraph.display.output;

import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class SwingGUI extends Buffer {
    private final int pixelSize = 10;
    private final int pixelSpace = 2;

    private final JFrame frame;

    public SwingGUI(int width, int height) {
        super(new Null(width, height));

        frame = new JFrame("Seraph Swing GUI");
        int pixelWidth = (width * (pixelSize + pixelSpace)) + pixelSpace;
        int pixelHeight = (height * (pixelSize + pixelSpace)) + pixelSpace;

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        PixelFrame pixelFrame = new PixelFrame(pixelWidth, pixelHeight);
        frame.add(pixelFrame);
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    protected void setActualPixel(int x, int y, int r, int g, int b) {
        super.setActualPixel(x, y, r, g, b);
        frame.repaint();
    }

    class PixelFrame extends JPanel {
        public PixelFrame(int pixelWidth, int pixelHeight) {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(pixelWidth, pixelHeight));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            drawPixels(g);
        }

        private void drawPixels(Graphics g) {
            for (int x = 0; x < SwingGUI.this.getWidth(); x++) {
                for (int y = 0; y < SwingGUI.this.getHeight(); y++) {
                    g.setColor(Color.DARK_GRAY);

                    int xD = pixelSpace + ((pixelSize + pixelSpace) * x);
                    int yD = pixelSpace + ((pixelSize + pixelSpace) * y);

                    g.drawRect(xD, yD, pixelSize, pixelSize);

                    PixelColour pixelColour = getActualPixel(x, y);
                    g.setColor(new Color(pixelColour.getRed(), pixelColour.getGreen(), pixelColour.getBlue()));
                    g.fillRect(xD + 1, yD + 1, pixelSize - 1, pixelSize - 1 );
                }
            }
        }
    }
}
