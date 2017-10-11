package net.melaircraft.seraph.display.layout;

import net.melaircraft.seraph.display.DestinationDisplay;
import net.melaircraft.seraph.display.FullDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.buffer.Buffer;
import net.melaircraft.seraph.display.output.Null;

import java.util.ArrayList;
import java.util.List;

public class Book {
    static final PixelColour ACTIVE_PAGE_COLOUR = new PixelColour(255, 255, 255);
    static final PixelColour INACTIVE_PAGE_COLOUR = new PixelColour(32, 32, 32);

    private final DestinationDisplay parent;
    private final List<Page> pages = new ArrayList<>();
    private final boolean pagination;
    private int currentPage = 0;

    public Book(DestinationDisplay parent, boolean pagination) {
        this.parent = parent;
        this.pagination = pagination;
    }

    public FullDisplay addPage() {
        Page page = new Page(this);
        pages.add(page);

        drawPagination();

        return page;
    }

    protected void setPagePixel(Page page, int x, int y, PixelColour pixelColour) {
        if (pages.indexOf(page) == currentPage) {
            boolean allowDraw = true;

            if (pagination) {
                if (y == (parent.getHeight() - 1)) {
                    if (x >= (parent.getWidth() - pages.size())) {
                        allowDraw = false;
                    }
                }
            }

            if (allowDraw) {
                parent.setPixel(x, y, pixelColour);
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

        for (int x = 0; x < parent.getWidth(); x++) {
            for (int y = 0; y < parent.getHeight(); y++) {
                PixelColour colour = page.getPixel(x, y);
                parent.setPixel(x, y, colour);
            }
        }

        drawPagination();
    }

    private void drawPagination() {
        if (pagination) {
            int y = parent.getHeight() - 1;
            int x = parent.getWidth() - pages.size();

            for (int i = 0; i < pages.size(); i++) {
                PixelColour colour = INACTIVE_PAGE_COLOUR;

                if (i == currentPage) {
                    colour = ACTIVE_PAGE_COLOUR;
                }

                parent.setPixel(x + i, y, colour);
            }
        }
    }

    static class Page extends Buffer {
        private final Book book;

        public Page(Book book) {
            super(new Null(book.parent.getWidth(), book.parent.getHeight()));
            this.book = book;
        }

        @Override
        protected void setActualPixel(int x, int y, PixelColour pixelColour) {
            super.setActualPixel(x, y, pixelColour);
            book.setPagePixel(this, x, y, pixelColour);
        }
    }
}
