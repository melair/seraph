package net.melaircraft.seraph.display.component;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.fonts.BitmapFont;

public class Text {
    private final Displayable parent;
    private final BitmapFont bitmapFont;
    private final Justification justification;
    private final Alignment alignment;
    private String content;

    public Text(Displayable parent, BitmapFont bitmapFont, Justification justification, Alignment alignment, String content) {
        this.parent = parent;
        this.bitmapFont = bitmapFont;
        this.justification = justification;
        this.alignment = alignment;
        this.content = content;
    }

    public enum Justification {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum Alignment {
        TOP,
        MIDDLE,
        BOTTOM
    }
}
