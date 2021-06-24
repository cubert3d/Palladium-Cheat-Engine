package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "6/23/2021"
)

/*
Contains the information of a color, in the form of float variables.
Used with rendering methods where floats, instead of ints, are required
for the color.
 */

public final class ColorF {

    public static final ColorF WHITE = new ColorF(1.0F, 1.0F, 1.0F, 1.0F);
    public static final ColorF RED = new ColorF(1.0F, 0.0F, 0.0F, 1.0F);
    public static final ColorF BLUE = new ColorF(0.0F, 1.0F, 0.0F, 1.0F);
    public static final ColorF GREEN = new ColorF(0.0F, 0.0F, 1.0F, 1.0F);

    private final float red, blue, green, alpha;

    public ColorF(float red, float blue, float green, float alpha) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.alpha = alpha;
    }

    public float getRed() {
        return red;
    }

    public float getBlue() {
        return blue;
    }

    public float getGreen() {
        return green;
    }

    public float getAlpha() {
        return alpha;
    }
}
