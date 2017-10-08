package net.melaircraft.seraph.demo;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
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
        SwingGUI gui = new SwingGUI(128, 32);

        Tee tee = new Tee(new Brightness(seraphProtocol, 0.4F), gui);

        Stack stack = new Stack(tee);
        Displayable top = stack.addLayer();

        Mask mask = new Mask(top);
        new Rainbow(mask.getMask());

        Stack stack2 = new Stack(mask);
        Displayable top2 = stack2.addLayer();
        Displayable bottom2 = stack2.addLayer();

        BitmapFont bitmapFont = BDFFontFactory.load(new FileInputStream("contrib/zevv-peep-iso8859-1-07x14.bdf"));
        Text text = new Text(top2, bitmapFont, PixelColour.WHITE, Text.Justification.CENTER, Text.Alignment.BOTTOM, getTime());
        Text text2 = new Text(bottom2, bitmapFont, PixelColour.WHITE, Text.Justification.CENTER, Text.Alignment.TOP, "Time is");

        Displayable bottom = stack.addLayer();
        Book book = new Book(bottom, true);

        Displayable one = book.addPage();
        Pane paneOne = new Pane(one, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderOne = new Border(paneOne, new PixelColour(100, 255, 100), Border.Side.BOTTOM);
        Matrix matrix = new Matrix(new Brightness(borderOne, 0.25F));

        Displayable two = book.addPage();
        Pane paneTwo = new Pane(two, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderTwo = new Border(paneTwo, new PixelColour(150, 150, 150), Border.Side.BOTTOM);
        Snow snow = new Snow(borderTwo, 40);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(seraphProtocol, 0, 50, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> text.setText(getTime()), 1, 1, TimeUnit.SECONDS);
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
