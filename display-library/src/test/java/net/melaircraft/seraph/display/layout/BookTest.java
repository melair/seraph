package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;
import net.melaircraft.seraph.display.output.Null;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BookTest {
    Displayable store = new Buffer(new Null(4, 2));

    @Test
    public void testDimensionsArePassedThrough() {
        Displayable book = new Book(store, true);

        assertEquals(store.getHeight(), book.getHeight());
        assertEquals(store.getWidth(), book.getWidth());
    }

    @Test(expected = NonExistentPixelException.class)
    public void testDirectSetThrowsException() {
        Displayable book = new Book(store, true);
        book.setPixel(0, 0, 0, 0, 0);
    }

    @Test(expected = NonExistentPixelException.class)
    public void testDirectGetThrowsException() {
        Displayable book = new Book(store, true);
        book.getPixel(0, 0);
    }

    @Test
    public void testPageIsGivenOut() {
        Book book = new Book(store, true);
        Displayable page = book.addPage();

        assertEquals(store.getWidth(), page.getWidth());
        assertEquals(store.getHeight(), page.getHeight());
    }

    @Test
    public void testFirstPageWritesToParentIfActive() {
        Book book = new Book(store, true);
        Displayable page = book.addPage();

        PixelColour pixelColour = PixelColour.RED;
        page.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 0));
    }

    @Test
    public void testSecondPageWritesOnNextPage() {
        Book book = new Book(store, true);
        Displayable one = book.addPage();
        Displayable two = book.addPage();

        PixelColour pixelColour = PixelColour.RED;
        two.setPixel(0, 0, pixelColour);

        assertNotEquals(pixelColour, store.getPixel(0, 0));
        book.nextPage();
        assertEquals(pixelColour, store.getPixel(0, 0));
    }

    @Test
    public void testTwoPageCycle() {
        Book book = new Book(store, true);
        Displayable one = book.addPage();
        Displayable two = book.addPage();

        PixelColour pixelColour = PixelColour.RED;
        one.setPixel(0, 0, pixelColour);

        assertEquals(pixelColour, store.getPixel(0, 0));
        book.nextPage();
        assertNotEquals(pixelColour, store.getPixel(0, 0));
        book.nextPage();
        assertEquals(pixelColour, store.getPixel(0, 0));
    }

    @Test
    public void testPaginationForOnePage() {
        Book book = new Book(store, true);
        Displayable one = book.addPage();

        assertEquals(Book.ACTIVE_PAGE_COLOUR, store.getPixel(3, 1));
    }

    @Test
    public void testPaginationForTwoPage() {
        Book book = new Book(store, true);
        Displayable one = book.addPage();
        Displayable two = book.addPage();

        assertEquals(Book.ACTIVE_PAGE_COLOUR, store.getPixel(2, 1));
        assertEquals(Book.INACTIVE_PAGE_COLOUR, store.getPixel(3, 1));

        book.nextPage();

        assertEquals(Book.INACTIVE_PAGE_COLOUR, store.getPixel(2, 1));
        assertEquals(Book.ACTIVE_PAGE_COLOUR, store.getPixel(3, 1));
    }

    @Test
    public void testPaginationCanNotBeOverwritten() {
        Book book = new Book(store, true);
        Displayable page = book.addPage();

        PixelColour pixelColour = PixelColour.RED;
        page.setPixel(3, 1, pixelColour);

        assertNotEquals(pixelColour, store.getPixel(3, 1));
    }

    @Test
    public void testNoPagination() {
        Book book = new Book(store, false);

        Displayable page = book.addPage();

        PixelColour pixelColour = PixelColour.RED;
        page.setPixel(3, 1, pixelColour);

        assertEquals(pixelColour, store.getPixel(3, 1));

        book.addPage();

        assertEquals(pixelColour, store.getPixel(3, 1));
    }
}