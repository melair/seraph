package net.melaircraft.seraph.demo;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.decoration.Border;
import net.melaircraft.seraph.display.decoration.animation.Matrix;
import net.melaircraft.seraph.display.decoration.animation.Snow;
import net.melaircraft.seraph.display.layout.Book;
import net.melaircraft.seraph.display.layout.Pane;
import net.melaircraft.seraph.display.output.SwingGUI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDemoApp {
    public static void main(String[] args) throws InterruptedException {
        SwingGUI gui = new SwingGUI(128, 32);

        Book book = new Book(gui, true);

        Displayable one = book.addPage();
        Pane paneOne = new Pane(one, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderOne = new Border(paneOne, new PixelColour(100, 255, 100), Border.Side.BOTTOM);
        Matrix matrix = new Matrix(borderOne);

        Displayable two = book.addPage();
        Pane paneTwo = new Pane(two, 0, 0, gui.getWidth(), gui.getHeight() - 1);
        Border borderTwo = new Border(paneTwo, new PixelColour(150, 150, 150), Border.Side.BOTTOM);
        Snow snow = new Snow(borderTwo, 40);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(snow, 0, 300, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(matrix, 0, 100, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(book::nextPage, 5000, 5000, TimeUnit.MILLISECONDS);
    }
}
