package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private static final PixelColour ACTIVE_PAGE_COLOUR = new PixelColour(255, 255, 255);
    private static final PixelColour INACTIVE_PAGE_COLOUR = new PixelColour(32, 32, 32);

    private final DestinationDisplay destination;
    private final List<Page> pages = new ArrayList<>();
    private final boolean pagination;

    private int currentPage = 0;

    public Book(DestinationDisplay destination, boolean pagination) {
        this.destination = destination;
        this.pagination = pagination;
    }

    public DestinationDisplay addPage() {
        Page page = new Page(this);
        pages.add(page);

        drawPagination();

        return page;
    }

    protected void setPagePixel(Page page, int x, int y, PixelColour pixelColour) {
        if (pages.indexOf(page) == currentPage) {
            boolean allowDraw = true;

            if (pagination) {
                if (y == (destination.getHeight() - 1)) {
                    if (x >= (destination.getWidth() - pages.size())) {
                        allowDraw = false;
                    }
                }
            }

            if (allowDraw) {
                destination.setPixel(x, y, pixelColour);
            }
        }
    }

    public void nextPage() {
        if (pages.size() == 0) {
            return;
        }

        if (currentPage >= (pages.size() -1)) {
            currentPage = 0;
        } else {
            currentPage++;
        }

        Page page = pages.get(currentPage);

        for (int x = 0; x < destination.getWidth(); x++) {
            for (int y = 0; y < destination.getHeight(); y++) {
                PixelColour colour = page.getPixel(x, y);
                destination.setPixel(x, y, colour);
            }
        }

        drawPagination();
    }

    private void drawPagination() {
        if (pagination) {
            int y = destination.getHeight() - 1;
            int x = destination.getWidth() - pages.size();

            for (int i = 0; i < pages.size(); i++) {
                PixelColour colour = INACTIVE_PAGE_COLOUR;

                if (i == currentPage) {
                    colour = ACTIVE_PAGE_COLOUR;
                }

                destination.setPixel(x + i, y, colour);
            }
        }
    }

    static class Page implements FullDisplay, Buffer.BufferCallback {
        private final Book book;
        private final Buffer buffer;

        public Page(Book book) {
            this.book = book;
            this.buffer = new Buffer(book.destination.getWidth(), book.destination.getHeight());
            buffer.registerCallback(this);
        }

        @Override
        public void setPixel(int x, int y, PixelColour pixelColour) throws NonExistentPixelException {
            buffer.setPixel(x, y, pixelColour);
        }

        @Override
        public PixelColour getPixel(int x, int y) throws NonExistentPixelException {
            return buffer.getPixel(x, y);
        }

        @Override
        public int getWidth() {
            return buffer.getWidth();
        }

        @Override
        public int getHeight() {
            return buffer.getHeight();
        }

        @Override
        public void updated(int x, int y, PixelColour colour) {
            book.setPagePixel(this, x, y, colour);
        }
    }
}
