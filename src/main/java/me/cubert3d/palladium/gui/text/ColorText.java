package me.cubert3d.palladium.gui.text;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/17/2021"
)

public final class ColorText {

    private final String string;
    private final int textColor;
    private final int backgroundColor;

    public ColorText(String string) {
        this(string, Colors.WHITE, Colors.BACKGROUND_LAVENDER);
    }

    public ColorText(String string, int textColor) {
        this(string, textColor, Colors.BACKGROUND_LAVENDER);
    }

    public ColorText(String string, int textColor, int backgroundColor) {
        this.string = string;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public final String getString() {
        return string;
    }

    public final int getTextColor() {
        return textColor;
    }

    public final int getBackgroundColor() {
        return backgroundColor;
    }
}
