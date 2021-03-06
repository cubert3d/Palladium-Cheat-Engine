package me.cubert3d.palladium.gui.text;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "4/9/2021",
        type = ClassType.UTILITY
)

public final class Colors {

    // TEXT COLORS

    public static final int BLACK = 0xFF000000;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int LAVENDER = 0xFFD3C7FF;
    public static final int HIGHLIGHT = 0xFFFFF69E;

    // BACKGROUND COLORS

    public static final int BACKGROUND_WHITE = 0x6FFFFFFF;
    public static final int BACKGROUND_GRAY = 0x6FAFAFB0;
    public static final int BACKGROUND_LAVENDER = 0x6F6D5197;
    public static final int BACKGROUND_GREEN = 0x6F28A12C;
    public static final int BACKGROUND_RED = 0x6F9C2828;
    public static final int BACKGROUND_BLUE = 0x6F2844A1;
    public static final int BACKGROUND_YELLOW = 0x6FFFEC99;
    public static final int BACKGROUND_PINK = 0x6FFFB9F2;

    public static int of(int red, int green, int blue, int alpha) {

        alpha = Math.max(0x0, alpha);
        alpha = Math.min(alpha, 0xFF);
        alpha *= 0x01000000;

        red = Math.max(0x0, red);
        red = Math.min(red, 0xFF);
        red *= 0x00010000;

        green = Math.max(0x0, green);
        green = Math.min(green, 0xFF);
        green *= 0x00000100;

        blue = Math.max(0x0, blue);
        blue = Math.min(blue, 0xFF);

        return alpha + red + green + blue;
    }
}
