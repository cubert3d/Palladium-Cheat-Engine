package me.cubert3d.palladium.util.render;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        description = "Contains the information of a color, in the form of float variables.",
        authors = "cubert3d",
        date = "6/23/2021",
        type = ClassType.MISC
)

/*
Used with rendering methods where floats, instead of ints, are required
for the color.
 */

public final class Color4F {

    public static final Color4F WHITE = new Color4F(1.0F, 1.0F, 1.0F, 1.0F);
    public static final Color4F RED = new Color4F(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Color4F GREEN = new Color4F(0.0F, 1.0F, 0.0F, 1.0F);
    public static final Color4F BLUE = new Color4F(0.0F, 0.0F, 1.0F, 1.0F);
    public static final Color4F YELLOW = new Color4F(1.0F, 0.9F, 0.1F, 1.0F);

    private final float red, green, blue, alpha;

    public Color4F(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
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
