package net.melaircraft.seraph.demo;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.component.Text;
import net.melaircraft.seraph.display.decoration.Border;
import net.melaircraft.seraph.display.decoration.animation.Matrix;
import net.melaircraft.seraph.display.decoration.animation.Snow;
import net.melaircraft.seraph.display.layout.Book;
import net.melaircraft.seraph.display.layout.Pane;
import net.melaircraft.seraph.display.layout.Stack;
import net.melaircraft.seraph.display.output.SwingGUI;
import net.melaircraft.seraph.fonts.BDFFontFactory;
import net.melaircraft.seraph.fonts.BitmapFont;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDemoApp {
    public static void main(String[] args) throws IOException {
        SwingGUI gui = new SwingGUI(128, 32);

        Stack stack = new Stack(gui);
        Displayable top = stack.addLayer();

        Stack stack2 = new Stack(top);
        Displayable top2 = stack2.addLayer();
        Displayable bottom2 = stack2.addLayer();

        BitmapFont bitmapFont = BDFFontFactory.load(new FileInputStream("contrib/c64-8.bdf"));
        PixelColour teal = new PixelColour(0, 128, 128);

        Text text = new Text(top2, bitmapFont, teal, Text.Justification.CENTER, Text.Alignment.MIDDLE, getTime());
        Text text2 = new Text(bottom2, bitmapFont, teal, Text.Justification.CENTER, Text.Alignment.TOP, "COMMODORE 64");

        Displayable bottom = stack.addLayer();
        Book book = new Book(bottom, true);

        Displayable one = book.addPage();
        Pane paneOne = new Pane(one, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderOne = new Border(paneOne, new PixelColour(100, 255, 100), Border.Side.BOTTOM);
        Matrix matrix = new Matrix(borderOne);

        Displayable two = book.addPage();
        Pane paneTwo = new Pane(two, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderTwo = new Border(paneTwo, new PixelColour(150, 150, 150), Border.Side.BOTTOM);
        Snow snow = new Snow(borderTwo, 40);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> text.setText(getTime()), 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(snow, 0, 300, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(matrix, 0, 75, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(book::nextPage, 5000, 5000, TimeUnit.MILLISECONDS);
    }

    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
