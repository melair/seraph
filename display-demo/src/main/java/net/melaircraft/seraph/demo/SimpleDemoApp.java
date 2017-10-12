package net.melaircraft.seraph.demo;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.component.Canvas;
import net.melaircraft.seraph.display.component.Text;
import net.melaircraft.seraph.display.decoration.Border;
import net.melaircraft.seraph.display.decoration.Rainbow;
import net.melaircraft.seraph.display.decoration.animation.Matrix;
import net.melaircraft.seraph.display.decoration.animation.Snow;
import net.melaircraft.seraph.display.filter.Brightness;
import net.melaircraft.seraph.display.filter.Mask;
import net.melaircraft.seraph.display.filter.Tee;
import net.melaircraft.seraph.display.layout.Book;
import net.melaircraft.seraph.display.layout.Pane;
import net.melaircraft.seraph.display.layout.Stack;
import net.melaircraft.seraph.display.output.SwingGUI;
import net.melaircraft.seraph.display.output.seraph.SeraphProtocol;
import net.melaircraft.seraph.fonts.BDFFontFactory;
import net.melaircraft.seraph.fonts.BitmapFont;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDemoApp {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByAddress(new byte[] { 10, 10, 8, 70 });
        SeraphProtocol seraphProtocol = new SeraphProtocol(128, 32, inetAddress, 7777, 1450);
        DestinationDisplay gui = new SwingGUI(128, 32);

        DestinationDisplay tee = new Tee(new Brightness(seraphProtocol, 0.4F), gui);

        Stack stack = new Stack(tee);
        DestinationDisplay top = stack.addLayer();

        Mask mask = new Mask(top);
        new Rainbow(mask.getMask());

        Stack stack2 = new Stack(mask);
        DestinationDisplay top2 = stack2.addLayer();
        DestinationDisplay bottom2 = stack2.addLayer();

        BitmapFont bitmapFont = BDFFontFactory.load(new FileInputStream("contrib/gohufont-11.bdf"));
        Canvas canvas = new Canvas(top2, Canvas.Direction.LEFT, Canvas.Alignment.MIDDLE, Canvas.Alignment.TOP);
        canvas.append(new Text(bitmapFont, PixelColour.WHITE, "I am the very model of a modern Major-General / I've information vegetable, animal, and mineral"));

        Canvas canvas2 = new Canvas(bottom2, Canvas.Direction.RIGHT, Canvas.Alignment.MIDDLE, Canvas.Alignment.BOTTOM);
        Buffer buffer = new Buffer(11, 9);

        buffer.setPixel(2, 0, PixelColour.WHITE);
        buffer.setPixel(3, 0, PixelColour.WHITE);
        buffer.setPixel(4, 0, PixelColour.WHITE);
        buffer.setPixel(5, 0, PixelColour.WHITE);
        buffer.setPixel(6, 0, PixelColour.WHITE);

        buffer.setPixel(1, 1, PixelColour.WHITE);
        buffer.setPixel(2, 1, PixelColour.WHITE);
        buffer.setPixel(3, 1, PixelColour.WHITE);
        buffer.setPixel(5, 1, PixelColour.WHITE);
        buffer.setPixel(6, 1, PixelColour.WHITE);
        buffer.setPixel(7, 1, PixelColour.WHITE);

        buffer.setPixel(0, 2, PixelColour.WHITE);
        buffer.setPixel(1, 2, PixelColour.WHITE);
        buffer.setPixel(2, 2, PixelColour.WHITE);
        buffer.setPixel(3, 2, PixelColour.WHITE);
        buffer.setPixel(5, 2, PixelColour.WHITE);
        buffer.setPixel(6, 2, PixelColour.WHITE);
        buffer.setPixel(7, 2, PixelColour.WHITE);
        buffer.setPixel(8, 2, PixelColour.WHITE);

        buffer.setPixel(0, 3, PixelColour.WHITE);
        buffer.setPixel(1, 3, PixelColour.WHITE);
        buffer.setPixel(2, 3, PixelColour.WHITE);
        buffer.setPixel(3, 3, PixelColour.WHITE);
        buffer.setPixel(5, 3, PixelColour.WHITE);
        buffer.setPixel(6, 3, PixelColour.WHITE);
        buffer.setPixel(7, 3, PixelColour.WHITE);
        buffer.setPixel(8, 3, PixelColour.WHITE);

        buffer.setPixel(0, 4, PixelColour.WHITE);
        buffer.setPixel(1, 4, PixelColour.WHITE);
        buffer.setPixel(2, 4, PixelColour.WHITE);
        buffer.setPixel(3, 4, PixelColour.WHITE);
        buffer.setPixel(7, 4, PixelColour.WHITE);
        buffer.setPixel(8, 4, PixelColour.WHITE);

        buffer.setPixel(0, 5, PixelColour.WHITE);
        buffer.setPixel(1, 5, PixelColour.WHITE);
        buffer.setPixel(2, 5, PixelColour.WHITE);
        buffer.setPixel(3, 5, PixelColour.WHITE);
        buffer.setPixel(4, 5, PixelColour.WHITE);
        buffer.setPixel(5, 5, PixelColour.WHITE);
        buffer.setPixel(6, 5, PixelColour.WHITE);
        buffer.setPixel(7, 5, PixelColour.WHITE);
        buffer.setPixel(8, 5, PixelColour.WHITE);

        buffer.setPixel(0, 6, PixelColour.WHITE);
        buffer.setPixel(1, 6, PixelColour.WHITE);
        buffer.setPixel(2, 6, PixelColour.WHITE);
        buffer.setPixel(3, 6, PixelColour.WHITE);
        buffer.setPixel(4, 6, PixelColour.WHITE);
        buffer.setPixel(5, 6, PixelColour.WHITE);
        buffer.setPixel(6, 6, PixelColour.WHITE);
        buffer.setPixel(7, 6, PixelColour.WHITE);
        buffer.setPixel(8, 6, PixelColour.WHITE);

        buffer.setPixel(1, 7, PixelColour.WHITE);
        buffer.setPixel(2, 7, PixelColour.WHITE);
        buffer.setPixel(3, 7, PixelColour.WHITE);
        buffer.setPixel(4, 7, PixelColour.WHITE);
        buffer.setPixel(5, 7, PixelColour.WHITE);
        buffer.setPixel(6, 7, PixelColour.WHITE);
        buffer.setPixel(7, 7, PixelColour.WHITE);

        buffer.setPixel(2, 8, PixelColour.WHITE);
        buffer.setPixel(3, 8, PixelColour.WHITE);
        buffer.setPixel(4, 8, PixelColour.WHITE);
        buffer.setPixel(5, 8, PixelColour.WHITE);
        buffer.setPixel(6, 8, PixelColour.WHITE);

        canvas2.append(new Buffer(0, 11));
        canvas2.append(buffer);
        canvas2.append(new Text(bitmapFont, PixelColour.WHITE, "--:--:--"));

        DestinationDisplay bottom = stack.addLayer();
        Book book = new Book(bottom, true);

        DestinationDisplay one = book.addPage();
        Pane paneOne = new Pane(one, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        DestinationDisplay borderOne = new Border(paneOne, new PixelColour(100, 255, 100), Border.Side.BOTTOM);
        Matrix matrix = new Matrix(new Brightness(borderOne, 0.25F));

        DestinationDisplay two = book.addPage();
        Pane paneTwo = new Pane(two, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        DestinationDisplay borderTwo = new Border(paneTwo, new PixelColour(150, 150, 150), Border.Side.BOTTOM);
        Snow snow = new Snow(borderTwo, 40);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(seraphProtocol, 0, 50, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(canvas, 0, 100, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(canvas2, 0, 50, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(() -> canvas2.replace(2, new Text(bitmapFont, PixelColour.WHITE, getTime())), 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(snow, 0, 300, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(matrix, 0, 50, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(book::nextPage, 5000, 5000, TimeUnit.MILLISECONDS);
    }

    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
