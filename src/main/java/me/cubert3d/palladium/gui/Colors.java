package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class Colors {

    public static final int COLOR_BLACK = 0x00000000;
    public static final int COLOR_WHITE = 0x00FFFFFF;
    public static final int COLOR_LAVENDER = 0x00D3C7FF;
    public static final int BACKGROUND_COLOR_WHITE = 0x6FAFAFB0;
    public static final int BACKGROUND_COLOR_LAVENDER = 0x6F6D5197;

    public static int buildColor(int transparency, int red, int green, int blue) {

        transparency = Math.max(0x0, transparency);
        transparency = Math.min(transparency, 0xFF);
        transparency *= 0x01000000;

        red = Math.max(0x0, red);
        red = Math.min(red, 0xFF);
        red *= 0x00010000;

        green = Math.max(0x0, green);
        green = Math.min(green, 0xFF);
        green *= 0x00000100;

        blue = Math.max(0x0, blue);
        blue = Math.min(blue, 0xFF);

        return transparency + red + green + blue;
    }
}
