package net.melaircraft.seraph.demo;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.decoration.Border;
import net.melaircraft.seraph.display.filter.Rotate;
import net.melaircraft.seraph.display.filter.Tee;
import net.melaircraft.seraph.display.layout.Book;
import net.melaircraft.seraph.display.layout.Pane;
import net.melaircraft.seraph.display.output.SwingGUI;

public class SimpleDemoApp {
    public static void main(String[] args) throws InterruptedException {
        SwingGUI guiOne = new SwingGUI(128, 32);
        SwingGUI guiTwo = new SwingGUI(128, 32);

        Tee tee = new Tee(guiOne, new Rotate(guiTwo, Rotate.Rotation.ROTATE_180));

        Book book = new Book(tee, true);

        Displayable one = book.addPage();
        Pane paneOne = new Pane(one, 0, 0, tee.getWidth(), tee.getHeight() - 1);
        Border borderOne = new Border(paneOne, PixelColour.RED, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        Displayable two = book.addPage();
        Pane paneTwo = new Pane(two, 0, 0, tee.getWidth(), tee.getHeight() - 1);
        Border borderTwo = new Border(paneTwo, PixelColour.GREEN, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        Displayable three = book.addPage();
        Pane paneThree = new Pane(three, 0, 0, tee.getWidth(), tee.getHeight() - 1);
        Border borderThree = new Border(paneThree, PixelColour.BLUE, Border.Side.TOP, Border.Side.LEFT, Border.Side.RIGHT, Border.Side.BOTTOM);

        while(true) {
            Thread.sleep(2500);
            book.nextPage();
        }
    }
}
